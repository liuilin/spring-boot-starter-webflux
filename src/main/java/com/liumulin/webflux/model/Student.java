package com.liumulin.webflux.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("t_student ")
public class Student {
    @Id
    private String id;
    private String name;
    private Integer age;
}
