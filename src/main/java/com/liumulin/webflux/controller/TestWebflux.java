package com.liumulin.webflux.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.function.Function;

/**
 * @author liuqiang
 * @since 2021-09-06
 */
// 其实不需要知道接口的名字和方法，也不需要接口，
// 因为只需要知道参数和返回值就可以了。所以可以修改为 Function<BigDecimal,String>
//interface IMoneyFormat{
//    String format(BigDecimal money);
//}
class MyMoney {
    private BigDecimal money;

    public MyMoney(BigDecimal money) {
        this.money = money;
    }

    //    public void printMoney(IMoneyFormat moneyFormat){
//        System.out.println("我的金额为："+moneyFormat.format(this.money));
//    }
    // 修改后
    public void printMoney(Function<BigDecimal, String> moneyFormat) {
        System.out.println("我的金额为：" + moneyFormat.apply(this.money));
    }
}

public class TestWebflux {
    public static void main(String[] args) {
        MyMoney myMoney = new MyMoney(new BigDecimal(999999999));
        Function<BigDecimal, String> moneyFormat = i -> new DecimalFormat("#,###").format(i);

        // 还可以函数式接口链式调用
        myMoney.printMoney(moneyFormat.andThen(s -> " 人民币 " + s));
    }
}
