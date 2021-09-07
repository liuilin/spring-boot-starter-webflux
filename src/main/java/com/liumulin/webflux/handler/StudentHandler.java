package com.liumulin.webflux.handler;

import com.liumulin.webflux.model.Student;
import com.liumulin.webflux.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * 不过，需要注意的是，该处理器类并不需要实现这个 HandlerFunction 接口。
 * HandlerFunction 接口只要是用于定义处理器函数的，而非让实现的。
 *
 * @author liuqiang
 * @since 2021-09-07
 */
@Component
public class StudentHandler {
    @Autowired
    private StudentRepository repository;

    // 查询所有
    public Mono<ServerResponse> findAllHandle(ServerRequest req) {
        return ServerResponse
                // 指定响应码为 200
                .ok()
                // 指定请求体中的内容类型为 UTF8 编码的 JSON 数据
                .contentType(MediaType.APPLICATION_JSON)
                // 参数为 Publisher，而 findALL() 返回值为 Flux，即 Publisher
                // 参数二为 Publisher 的泛型
                .body(repository.findAll(), Student.class);
    }

    public Mono<ServerResponse> saveHandle(ServerRequest req) {
        // 从请求中获取要添加的数据
        Mono<Student> studentMono = req.bodyToMono(Student.class);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(repository.saveAll(studentMono), Student.class);
    }

    // 根据id删除逻辑：首先根据id进行查询，若可以查出，则删除后返回200，否则返回404
    public Mono<ServerResponse> deleteHandle(ServerRequest req) {
        // 从请求中的路径变量中获取 id
        String id = req.pathVariable("id");

        return repository
                // 首先根据 id 进行查询
                .findById(id)
                // 对于查询到的结果先执行删除操作，再返回 200 响应码
                .flatMap(stu -> repository.delete(stu)
                        // build() 方法可以将 bodyBuilder 对象转化为 Mono
                        .then(ServerResponse.ok().build())
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
