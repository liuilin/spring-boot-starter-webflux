package com.liumulin.webflux.controller;

import java.util.Random;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;


class MySubscriberTest {
    public static void main(String[] args) {
        SubmissionPublisher<Integer> publisher = null;

        try {
            // 定义发布者，发布的消息为 Integer 数据
            publisher = new SubmissionPublisher<>();
            // 定义订阅者，消费的消息为 Integer 数据
            MySubscriber subscriber = new MySubscriber();
            // 创建发布者与订阅者间的订阅关系
            publisher.subscribe((Flow.Subscriber<? super Integer>) subscriber);
            // 生产10条消息并发布
            for (int i = 0; i < 10; i++) {
                // submit() 是一个 block 方法，当发布过系统默认数量的消息后该方法阻塞。
                // 这样就通过订阅者控制了发布者发布消息的速度。“背压”策略在这里体现
                publisher.submit(new Random().nextInt(100));
            }
        } finally {
            // 关闭发布者
            publisher.close();
        }

        // 使主线程等待子线程 1000 毫秒，防止消息未被消费就退出的情况
        try {
            System.out.println("主线程开始等待");
            Thread.currentThread().join(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}