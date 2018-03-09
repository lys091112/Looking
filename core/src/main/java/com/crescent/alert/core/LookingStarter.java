package com.crescent.alert.core;

import com.crescent.alert.common.exception.InitializationException;
import com.crescent.alert.common.util.YmlConverter;
import com.crescent.alert.core.collector.CollectorFactory;
import com.crescent.alert.core.collector.consumer.kafka.KafkaWorker;
import com.crescent.alert.core.collector.consumer.Worker;
import com.crescent.alert.core.collector.producer.LookingProducer;
import com.crescent.alert.core.config.LookingConfig;
import com.crescent.alert.core.config.LookingConfig.ConsumerConfig;
import com.crescent.alert.core.config.PolicyInfo;
import com.crescent.alert.core.dispatch.DefaultEventDispatcher;
import com.crescent.alert.core.dispatch.EventDispatcher;
import com.crescent.alert.core.dispatch.handler.RuleHandlerProvider;
import com.crescent.alert.common.util.LookingThreadFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

@Slf4j
public class LookingStarter {

    private AtomicBoolean running = new AtomicBoolean(false);

    List<ExecutorService> executors = new ArrayList<>();

    List<Worker> workers = new ArrayList<>();

    @Setter
    private EventDispatcher dispatcher = new DefaultEventDispatcher(RuleHandlerProvider.defaultRuleHandler(null));

    private static LookingStarter starter = new LookingStarter();

    private LookingStarter() {
    }

    public static LookingStarter getInstance() {
        return starter;
    }

    public void start(LookingConfig lookingConfig) throws Exception {
        checkConfig(lookingConfig);

        if (!running.compareAndSet(false, true)) {
            log.warn("looking is running now!");
            return;
        }

        StateTransitionProvider.getInstance().init(loadPolicyInfo());

        // initial producer
        LookingProducer producer = CollectorFactory.createProducer(lookingConfig.getProducers());
        dispatcher = new DefaultEventDispatcher(RuleHandlerProvider.defaultRuleHandler(producer));

        // 这里需要保证的是，所属ruleId相同的event只能被同一个worker消费，保证不会有
        // 多线程竞争问题
        for (final ConsumerConfig info : lookingConfig.getConsumers()) {
            ExecutorService executor = Executors
                .newFixedThreadPool(info.getThreadNums(), new LookingThreadFactory("looking-" + info.getName()));
            List<Worker> singleWorkers = IntStream.range(0, info.getThreadNums()).mapToObj((int t) -> {
                    Worker worker = new KafkaWorker(info, dispatcher);
                    executor.submit(worker);
                    return worker;
                }
            ).collect(Collectors.toList());
            workers.addAll(singleWorkers);
            executors.add(executor);
        }
    }

    private void checkConfig(LookingConfig lookingConfig) {
        if (CollectionUtils.isEmpty(lookingConfig.getConsumers())) {
            throw new InitializationException("initialize consumer properties error, configs is empty!");
        }
        if (CollectionUtils.isEmpty(lookingConfig.getProducers())) {
            throw new InitializationException("initialize producers properties error, configs is empty!");
        }
    }

    private PolicyInfo loadPolicyInfo() throws IOException {
        return YmlConverter.config(new TypeReference<PolicyInfo>() {
        }, "policies.json");
    }

    public void stop() {
        if (!running.compareAndSet(true, false)) {
            log.warn("looking core hans been stopped!");
            return;
        }

        workers.forEach(worker -> {
            try {
                worker.close();
            } catch (InterruptedException e) {
                log.error("close worker error! workerName:{}, message:{}", worker.getName(), e.getMessage());
            }
        });

        executors.forEach(executor -> executor.shutdown());
    }

    public static void main(String[] args) throws Exception {
        LookingConfig lookingConfig = YmlConverter.config(new TypeReference<LookingConfig>() {
        }, "application.yml");

        LookingStarter.getInstance().start(lookingConfig);
        System.out.println(lookingConfig);
    }
}
