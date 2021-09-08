package com.liumulin.webflux.controller;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author liuqiang
 * @since 2021-09-07
 */
public class MethodReferenceDemo {
    public static void main(String[] args) {
        // Consumer<String> consumer = s -> System.out.println(s);
        // 如果只有一个函数体，并且函数参数与箭头左边的参数相同，那么就可以用方法引用
        Consumer<String> consumer = System.out::println;
        consumer.accept("fuck u...");

        // 静态方法的方法引用
        Consumer<Dog> consumer1 = Dog::bark;
        Dog dog = new Dog();
        consumer1.accept(dog);

        // 非静态方法（普通方法/成员方法）使用对象实例的方法引用
        Function<Integer, Integer> function = dog::eatFood;
        dog = null; // 此时后面不会报空指针，因为 java 是传值，而不是传引用。引用虽然置空了，但是值对象实例还在
        // 如果接口的输入参数和输出参数都相同，则可以修改为一元的函数接口
        // UnaryOperator<Integer> function = dog::eatFood;
        // 还可以进行泛型优化
        // IntUnaryOperator function = dog::eatFood;
        // System.out.println("还剩 " + function.applyAsInt(2) + " 狗粮");
        System.out.println("还剩 " + function.apply(2) + " 狗粮");


        // JDK 默认会把当前实例传入到非静态方法，参数名为 this，位置是第一个（上面等价于这个写法）
//    public int eatFood(Dog this,int eatNum) {
//        System.out.println("吃了 " + eatNum + " 狗粮");
//        this.num -= eatNum;
//        return this.num;
//    }
        // 所以实际上 eatFood 方法有两个输入参数，一个输出参数
        // 使用类名来引用方法
        BiFunction<Dog, Integer, Integer> biFunction = Dog::eatFood;
        System.out.println("还剩 " + biFunction.apply(dog, 2) + " 狗粮");

        // 空参构造函数的方法引用（可以理解为静态方法，因为不需要实例来调用）
        // 没有输入，而有一个输出（实例）的函数，就是 supplier
        Supplier<Dog> supplier = Dog::new;
        System.out.println("创建了新对象" + supplier.get());

        // 带参数的构造函数的方法引用（既有输入参数，也有输出参数）
        Function<String, Dog> function1 = Dog::new;
        System.out.println("创建了新对象" + function1.apply("元宝"));
    }
}

class Dog {
    private String name = "单身狗";
    private int num = 10;

    public Dog() {
    }

    public Dog(String name) {
        this.name = name;
    }

    // 狗叫了静态方法
    public static void bark(Dog dog) {
        System.out.println(dog + "叫了");
    }

    /**
     * 吃狗粮普通成员方法
     *
     * @param eatNum 吃了多少斤狗粮
     * @return 还剩多少斤
     */
    public int eatFood(int eatNum) {
        System.out.println("吃了 " + eatNum + " 狗粮");
        this.num -= eatNum;
        return this.num;
    }


    @Override
    public String toString() {
//        return "Dog{" +
//                "name='" + name + '\'' +
//                '}';
        return this.name;
    }
}