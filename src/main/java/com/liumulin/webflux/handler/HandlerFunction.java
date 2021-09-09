package com.liumulin.webflux.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author liuqiang
 * @since 2021-09-07
 */
@FunctionalInterface
public interface HandlerFunction<T extends ServerResponse> {
    /**
     * Handle the given request
     *
     * @param request the request to handle
     * @return the response
     */
    Mono<T> handle(ServerRequest request);
}
