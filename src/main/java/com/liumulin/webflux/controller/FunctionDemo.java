package com.liumulin.webflux.controller;

import java.util.function.Consumer;
import java.util.function.IntPredicate;

/**
 * @author liuqiang
 * @since 2021-09-06
 */
public class FunctionDemo {
    public static void main(String[] args) {
        // 断言函数接口..
//        Predicate<Integer> predicate = i -> i > 0;
        // 和上面相同，优势是不用再写泛型.
        IntPredicate predicate = i -> i > 0;
        System.out.println(predicate.test(3));

        // 消费无返回值接口
        Consumer<String> consumer = System.out::println;
        consumer.accept("shit");
    }
}
