package com.liumulin.webflux.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

/**
 * @author liuqiang
 * @since 2021-09-07
 */
@Configuration
public class StudentRouter {
    @Bean
    RouterFunction<ServerResponse> customRouter(StudentHandler handler) {
        return RouterFunctions
                // nest() 方法中用于定义各种路由规则即 uri 到处理器函数的映射关系
                .nest(RequestPredicates.path("/stu"),
                        // 查找路由：将 “/stu/all” 的 GET 请求路由到 findAllHandle() 方法
                        RouterFunctions.route(RequestPredicates.GET("/all"), handler::findAllHandle)
                                // 添加 save 方法路由，将 “/stu/save_all” 的 POST 请求映射到 saveHandle() 方法
                                .andRoute(RequestPredicates.POST("/save").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::saveHandle)
                );
    }

}
