package com.crescent.alert.runner;

import com.crescent.alert.collector.kafka.config.LookingKafkaConsumerConfig;
import com.crescent.alert.common.config.LookingConfig;
import com.crescent.alert.common.config.LookingConfig.ConsumerConfig;
import com.crescent.alert.common.util.JsonObjectConverter;
import com.crescent.alert.common.util.YmlConverter;
import com.crescent.alert.core.collector.consumer.LookingConsumer;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.Iterator;
import java.util.ServiceLoader;

//@SpringBootApplication
public class Runner {

    public static void main(String[] args) throws IOException {
//        SpringApplication.run(Application.class, args);
        // 加载规则Manager

        // 启动数据消费

         // 对数据进行计算，并发送生成的事件
        ServiceLoader<LookingConsumer> consumer = ServiceLoader.load(LookingConsumer.class);
        Iterator<LookingConsumer> iterator = consumer.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getClass());
        }

        LookingConfig lookingConfig = YmlConverter.config(new TypeReference<LookingConfig>() {
        }, "application.yml");

//        LookingStarter.getInstance().start(lookingConfig);
        ConsumerConfig config = lookingConfig.getConsumers().get(0);

        String s = config.getProperty().toString();
        System.out.println(s);
        LookingKafkaConsumerConfig c = JsonObjectConverter.readValueWithObject(config.getProperty(),
            LookingKafkaConsumerConfig.class);
        System.out.println(c);
    }
}
