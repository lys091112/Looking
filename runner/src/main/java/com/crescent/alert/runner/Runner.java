package com.crescent.alert.runner;

import com.crescent.alert.core.collector.consumer.LookingConsumer;
import java.util.Iterator;
import java.util.ServiceLoader;

//@SpringBootApplication
public class Runner {

    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
        // 加载规则Manager

        // 启动数据消费

        // 对数据进行计算，并发送生成的事件
        ServiceLoader<LookingConsumer> consumer = ServiceLoader.load(LookingConsumer.class);
        Iterator<LookingConsumer> iterator = consumer.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getClass());
        }
    }
}
