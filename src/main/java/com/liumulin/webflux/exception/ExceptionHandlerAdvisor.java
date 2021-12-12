package com.liumulin.webflux.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

/**
 * 表示其为处理器切面，会被织入到处理器方法中
 *
 * @author liuqiang
 * @since 2021-09-07
 */
@ControllerAdvice
public class ExceptionHandlerAdvisor {

    @ExceptionHandler
    public ResponseEntity<String> exHandler(WebExchangeBindException ex) {
        return new ResponseEntity<>(exToStr(ex), HttpStatus.BAD_REQUEST);
    }

    /**
     * 添加切面类。对于该切面类的定义，需要注意以下几点：
     * 1. 由于 getFieldErrors()方法获取到的是一个 List 集合，所以 stream()后的流就是一个集合数
     * 据流，且集合元素为异常对象
     * 2. map()方法用于将集合数据流中的每一个元素，即异常对象映射为字符串形式：发生异
     * 常的属性名 加 指定的异常信息。
     * 3. map()的最终结果仍为一个集合流，只不过集合流中的元素由异常对象变为了字符串。
     * 但这里需要的最终结果不是字符串集合，而是一个字符串，所以需要使用 reduce()将集
     * 合流缩减为一个字符串。
     * 4. reduce()的第一个参数为默认值，即在流中元素为空时所使用的值，可以避免异常的抛
     * 出。第二个参数为 BinaryOperator，两个输入一个输出，且类型相同。由两个输入最终
     * 变为了一个输出，就达到了缩减 reduce 的效果了。
     */
    private String exToStr(WebExchangeBindException ex) {
        return ex.getFieldErrors()
                .stream()
                .map(e -> e.getField() + " ：" + e.getDefaultMessage())
                .reduce("", (s1, s2) -> s1 + "\n" + s2);
    }
}
