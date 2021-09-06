package com.liumulin.webflux.controller;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

// 定义订阅者
public class MySubscriber implements Subscriber<Integer> {
    // 声明订阅关系
    private Subscription subscription;

    // 当订阅关系建立时，该方法会被发布者自动调用，
    // 发布者会将订阅令牌传入
    @Override
    public void onSubscribe(Subscription s) {
        // 保存传入的订阅令牌
        this.subscription = s;
        // 设置一次请求它所要订阅消息的数量
        // “背压” 策略在这里体现
        this.subscription.request(3);
    }

    // 订阅者每接收一次订阅消息数据时，该方法会被发布者自动调用一次，
    // 因此有多少次数据接收，该方法就会被执行多少次
    @Override
    public void onNext(Integer item) {
        // 处理接收到的消息数据
        System.out.println("订阅者正在处理的消息数据：" + item);
        // 暂停一会儿线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 设置再次请求所要订阅消息的数量
        this.subscription.request(2);
    }

    // 当订阅、消费过程中出现异常时，该方法会被发布者自动调用
    @Override
    public void onError(Throwable t) {
        // 输出异常调用堆栈信息
        t.printStackTrace();
        // 取消订阅关系，不再接收消息
        this.subscription.cancel();
    }

    // 当发布者的所有消息全部正常处理完毕后，
    // 即当发布者关闭时，该方法会被发布者自动调用
    @Override
    public void onComplete() {
        System.out.println("发布者已关闭，订阅者将所有消息全部处理完成");
    }
}
