package com.liumulin.webflux.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

/**
 * @author liuqiang
 * @since 2021-09-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderModel {

    /**
     * 订单号
     */
    private String orderNo;

//    /**
//     * 下单用户id
//     */
//    private ObjectId userId;
    /**
     * 下单用户名
     */
    private String userName;

}
