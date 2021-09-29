package com.liumulin.webflux.test;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author liuqiang
 * @since 2021-09-28
 */
@Mapper
public interface OrderConvert {

    OrderConvert INSTANCE = Mappers.getMapper(OrderConvert.class);

    OrderModel convertOrder2OrderModel(Order order);

    public static void main(String[] args) {
        Order order = new Order();
        order.setOrderNo("222");
        OrderModel orderModel = OrderConvert.INSTANCE.convertOrder2OrderModel(order);
        System.out.println("orderModel = " + orderModel);
    }
}
