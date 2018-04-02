package com.crescent.alert.core;

import com.crescent.alert.common.config.LookingConfig;
import com.crescent.alert.common.config.LookingConfig.ConsumerConfig;
import com.crescent.alert.common.config.PolicyInfo;
import com.crescent.alert.common.exception.InitializationException;
import com.crescent.alert.common.util.YmlConverter;
import com.crescent.alert.core.collector.CollectorFactory;
import com.crescent.alert.core.collector.consumer.LookingConsumer;
import com.crescent.alert.core.collector.consumer.NoEventConsumer;
import com.crescent.alert.core.dispatch.DefaultEventDispatcher;
import com.crescent.alert.core.dispatch.EventDispatcher;
import com.crescent.alert.core.dispatch.handler.RuleHandler;
import com.crescent.alert.core.dispatch.handler.RuleRawMetricHandler;
import com.crescent.alert.core.dispatch.provider.BaseEventsProvider;
import com.crescent.alert.core.dispatch.provider.EventProviderFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

@Slf4j
public class LookingStarter {

    private AtomicBoolean running = new AtomicBoolean(false);


    private ScheduledExecutorService noEventExecutor;

    @Setter
    private EventDispatcher dispatcher;


    private List<LookingConsumer> consumers;

    private AlertProvider alertProvider;

    public LookingStarter() {
    }

    public void start(LookingConfig lookingConfig) throws Exception {
        checkConfig(lookingConfig);

        if (!running.compareAndSet(false, true)) {
            log.warn("looking is running now!");
            return;
        }

        alertProvider = new AlertProvider(loadPolicyInfo(), CollectorFactory.createSender(lookingConfig.getProducers()));

        RuleHandler ruleHandler = new RuleRawMetricHandler(alertProvider, new EventProviderFactory() {
            @Override
            public BaseEventsProvider createEventProvider() {
                return createEventProvider(alertProvider, lookingConfig.getEventWindowType());
            }
        });

        // 创建数据分发器
        dispatcher = new DefaultEventDispatcher(alertProvider, ruleHandler);

        // 这里需要保证的是，所属ruleId相同的event只能被同一个worker消费，保证不会有多线程竞争问题
        consumers = CollectorFactory.createConsumers(lookingConfig.getConsumers(), dispatcher);

        noEventExecutor.scheduleAtFixedRate(new NoEventConsumer(dispatcher), 0, 60, TimeUnit.SECONDS);
    }

    private void checkConfig(LookingConfig lookingConfig) {
        if (CollectionUtils.isEmpty(lookingConfig.getConsumers())) {
            throw new InitializationException("Initialize consumer properties error, configs is empty!");
        }
        if (CollectionUtils.isEmpty(lookingConfig.getProducers())) {
            throw new InitializationException("Initialize producers properties error, configs is empty!");
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

        consumers.forEach(consumer -> consumer.close());
    }

    public static void main(String[] args) throws Exception {
        LookingConfig lookingConfig = YmlConverter.config(new TypeReference<LookingConfig>() {
        }, "application.yml");

        ConsumerConfig config = lookingConfig.getConsumers().get(0);
        System.out.println(config.getProperty());
        System.out.println(lookingConfig);
    }
}
