package com.liumulin.webflux.controller;

import com.liumulin.webflux.model.Student;
import com.liumulin.webflux.repository.StudentRepository;
import com.liumulin.webflux.util.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author liuqiang
 * @since 2021-09-07
 */

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // 以数组形式一次性返回数据
    @GetMapping("/all")
    public Flux<Student> getAll() {
        return studentRepository.findAll();
    }

    // 以 SSE 形式实时返回数据
    @GetMapping(value = "/sse/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Student> getAllSSE() {
        return studentRepository.findAll();
    }

    // 对于 Spring-Data-JPA 中的 save()方法需要注意，若参数对象的 id 属性为 Null，则 save()
    // 为添加操作，底层执行 insert into 语句；若参数对象的 id 属性不为 Null，则 save()为修改操
    // 作，底层执行 update 语句。
    @PostMapping("/save")
    public Mono<Student> saveStudent(@Valid @RequestBody Student student) {
        ValidateUtils.validateName(student.getName());
        return studentRepository.save(student);
    }

    // 修改处理器（提交 Form）
    @PostMapping("/save2")
    public Mono<Student> saveStudent2(@Valid Student student) {
        ValidateUtils.validateName(student.getName());
        return studentRepository.save(student);
    }

    // 无状态删除
    // 即指定的要删除的对象无论是否存在，其响应码均为 200，我们无法知道是否真正删除了数据。
    // 对于执行删除操作的处理器方法，其可以是没有返回值的。Spring-Data-JPA 的 deleteById()
    // 方法是没有返回值，但在 WebFlux 编程中需要将其包装为 Mono，但泛型为 Void
    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteStudent(@PathVariable("id") String id) {
        return studentRepository.deleteById(id);
    }

    /**
     * 有状态删除
     * 即指若删除的对象存在，且删除成功，则返回响应码 200，否则返回响应码 404。通过响应码就可以判断删除操作是否成功。
     * <p>
     * 编写该处理器的思路是：首先根据 id 对该对象进行查询，若存在则先将该查询结果删除，然后再将其映射为状态码 200；若不存在，则将查询结果映射为状态码 404。
     * <p>
     * 对于该处理器，需要注意以下几点：
     *   ResponseEntity 可以封装响应体中所携带的数据及响应码，其泛型用于指定携带数据的
     * 类型。若响应体中没有携带数据，则泛型为 Void。本例中要返回的 ResponseEntity 中仅
     * 封装了响应码不携带任何数据，所以泛型为 Void。响应码只能采用 HttpStatus 枚举类型
     * 常量表示，这是 ResponseEntity 的构造器所要求的。
     *   为什么做映射时使用 flatMap()，不使用 map()？首先这两个方法都是 Mono 的方法，不
     * 是 Stream 的方法，与 Stream 的两个同名方法无关，但均是做映射的。若需要对对象数
     * 据先执行操作后再做映射，则使用 flatMap()；若纯粹是一种数据映射，没有数据操作，
     * 则使用 map()。
     *   在 Mono 的访求中，对于没有返回值的方法，若想为其添加返回值，则可链式调用 then()
     * 反应式 Web 开发框架 WebFlux方法，由 then()方法返回想返回的值。对于本例，由于 Spring-Data-JPA 的 delete()方法没
     * 有返回值，所以这里使用 then()为其添加返回值。
     *
     * @param id
     * @return
     */
    @DeleteMapping("/del_stat/{id}")
    public Mono<ResponseEntity<Void>> deleteStatStudent(@PathVariable("id") String id) {
        return studentRepository.findById(id)
                .flatMap(stu -> studentRepository.delete(stu)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                ).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 思路：
     * 若修改成功，则返回修改后的对象数据；若指定的 id 对象不存在，则返回 404
     * flatMap() 的作用是将查询出的 stu 对象经过操作后修改到 DB，然后返回修改过的对象因为 Spring-Data-JPA 的 save() 方法返回的是修改成功后的对象
     * map() 的作用是将 stu 对象映 ResponseEntity 射为对象
     */
    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<Student>> updateStudent(@PathVariable("id") String id, @RequestBody Student student) {
        return studentRepository.findById(id)
                .flatMap(stu -> {
                    stu.setName(student.getName());
                    stu.setAge(student.getAge());
                    return studentRepository.save(stu);
                })
                .map(stu -> new ResponseEntity<>(stu, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getById/{id}")
    public Mono<ResponseEntity<Student>> getById(@PathVariable("id") String id) {
        return studentRepository.findById(id)
                .map(stu -> new ResponseEntity<>(stu, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 根据年龄上下限查询：一次性返回
    @GetMapping("/age/{below}/{top}")
    public Flux<Student> findByAgeBetween(
            @PathVariable("below") int below,
            @PathVariable("top") int top) {
        return studentRepository.findByAgeBetween(below, top);
    }

    @GetMapping(value = "/sse/age/{below}/{top}",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Student> findByAgeBetweenBySSE(
            @PathVariable("below") int below,
            @PathVariable("top") int top) {
        return studentRepository.findByAgeBetween(below, top);
    }

}