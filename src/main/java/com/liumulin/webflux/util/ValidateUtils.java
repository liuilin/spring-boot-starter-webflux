package com.liumulin.webflux.util;

import com.liumulin.webflux.exception.StudentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Stream;

/**
 * 为什么要使用异步 servlet？同步 servlet 阻塞了什么
 * 首先后端才有同步和异步的概念，而对于浏览器来说所有的都是同步
 * 同步阻塞的是 tomcat 的 servlet 线程，使用异步之后，tomcat 的 servlet 线程就会立马返回。然后处理下一个请求
 * 所以它可以用来处理高并发，把异步耗时操作交由线程池去处理。
 *
 * @author liuqiang
 * @since 2021-09-07
 */
public class ValidateUtils {
    // TODO:无效姓名列表（这里是写死的，后期可以修改到 yml 中，用 nacos 动态获取）
    private static final String[] INVALID_NAMES = {"admin", "liu"};

    // 校验姓名
    public static void validateName(String fieldValue) {
        Stream.of(INVALID_NAMES)
                // 只要方法参数值与任一个无效姓名相匹配，则通过过滤，不匹配的将从流中删除
                .filter(fieldValue::equalsIgnoreCase)
                // 返回 Optional 容器对象。只要从流中找到任一个元素，就将其封装到 Optional 容器对象中
                .findAny()
                // 只要 Optional 容器对象中封装的数据不空就执行该方法
                .ifPresent(invalidName -> {
                    throw new StudentException("name", invalidName, "非法姓名");
                });
    }

    @ExceptionHandler
    public ResponseEntity<String> exHandler(StudentException ex) {
        String message = ex.getMessage();
        String errorField = ex.getErrorField();
        String errorValue = ex.getErrorValue();
        String exMsg = message + "[" + errorField + ":" + errorValue + "]";
        return new ResponseEntity<>(exMsg, HttpStatus.BAD_REQUEST);
    }
}
