package com.liumulin.webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class SomeHandler {

    // 访问普通处理器
    @GetMapping("/common")
    public String commonDemo() {
        return "common response";
    }

    // 访问 mono 处理器
    // Mono 表示，处理器返回的数据为 0-1 个
    @GetMapping("/mono")
    public Mono<String> monoDemo() {
        return Mono.just("webflux-mono");
    }

    @GetMapping("/common2")
    public String commonDemo2() {
        log.info("start...");
        String something = doSomething("耗时操作");
        log.info("done...");
        return something;
    }

    @GetMapping("/mono2")
    public Mono<String> monoDemo2() {
        log.info("webflux-mono-start...");
        Mono<String> mono = Mono.fromSupplier(() -> doSomething("webflux-mono"));
        log.info("webflux-mono-stop...");
        return mono;
    }

    // 数组转 Flux
    // 而 Flux 表示，处理器返回的数据为 0-多个
    @GetMapping("/flux")
    public Flux<String> fluxDemo(@RequestParam String[] hobby) {
        Flux<String> stringFlux = Flux.fromArray(hobby);
        return stringFlux;
    }

    // 集合转 Flux
    @GetMapping("/flux2")
    public Flux<String> fluxDemo2(@RequestParam List<String> hobby) {
        Flux<String> stringFlux = Flux.fromStream(hobby.stream());
        return stringFlux;
    }

    // Flux 底层不会阻塞处理器执行
    @GetMapping("/flux3")
    public Flux<String> fluxDemo3(@RequestParam List<String> hobby) {
        log.info("flux-start");
        Flux<String> flux = Flux.fromStream(hobby.stream().map(i -> {
            // 暂停一会儿线程
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return " hobby:" + i;
        }));
        log.info("flux-stop");
        return flux;
    }

    @GetMapping("/flux4")
    public Flux<Tuple2<String, String>> fluxDemo4(@RequestParam List<String> hobby) {
        log.info("flux-start");
        Flux<String> flux = Flux.fromStream(hobby.stream().map(i -> {
            // 暂停一会儿线程
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return " hobby:" + i;
        }));
        Flux<String> flux2 = Flux.fromStream(hobby.stream().map(i -> {
            // 暂停一会儿线程
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return " hobby:" + i;
        }));
        Flux<Tuple2<String, String>> flux3 = flux.zipWith(flux2);
        log.info("flux-stop");
        return flux3;
    }



    public String doSomething(String msg) {
        // 暂停一会儿线程
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
