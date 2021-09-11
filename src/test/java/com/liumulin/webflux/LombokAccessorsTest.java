package com.liumulin.webflux;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liuqiang
 * @since 2021-09-11
 */
public class LombokAccessorsTest {

}

@Data
@Accessors(chain = true)
class User {
    private Integer id;
    private String name;
    private Integer age;

    public static void main(String[] args) {
        //开起chain=true后可以使用链式的set
        User lin = new User()
                .setName("Lin")
                .setAge(18); // 返回对象
        System.out.println(lin.name);
    }

}

@Data
@Accessors(fluent = true)
class User1 {
    private Integer id;
    private String name;
    private Integer age;

    public static void main(String[] args) {
        //fluent=true开启后默认chain=true，故这里也可以使用链式set
        User1 lin = new User1().name("shit").age(18); //不需要写set
        System.out.println(lin.name);
    }

}

@Data
@Accessors(prefix = "f")
class User2 {
    private String fName = "Hello, World!";

    public static void main(String[] args) {
        User2 user = new User2();
//        user.setName("Fuu"); //注意方法名
        System.out.println(user.getName());
    }

}
