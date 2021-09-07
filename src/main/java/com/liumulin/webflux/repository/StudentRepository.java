package com.liumulin.webflux.repository;

import com.liumulin.webflux.model.Student;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @author liuqiang
 * @since 2021-09-07
 */
@Repository
public interface StudentRepository extends ReactiveMongoRepository<Student, String> {

    /**
     * 根据年龄区间查询
     *
     * @param below 年龄下线
     * @param top   年龄上线
     * @return 学生
     */
    Flux<Student> findByAgeBetween(int below, int top);
}
