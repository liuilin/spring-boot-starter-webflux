package com.liumulin.webflux.test;

import lombok.Data;
import org.bson.types.ObjectId;

/**
 * 支付订单
 *
 * @author liuqiang
 * @since 2021-09-28
 */
@Data
//@Builder
public class Order {

    /**
     * 下单用户id
     */
    private ObjectId userId;
    /**
     * 下单用户名
     */
    private String userName;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 数量合计
     */
    private Integer totalNum;

    /**
     * 金额合计
     */
    private Integer totalMoney;

    /**
     * 订单状态  1:初始化 2:待付款 3:进行中 4:已付款
     */
    private Integer status;

    /**
     * 订单来源：1:web 2:app 3:微信公众号 4:微信小程序 5:H5手机页面
     */
    private String sourceType;
}
