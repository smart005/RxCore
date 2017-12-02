package com.cloud.coretest.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author Gs
 * @Email:gs_12@foxmail.com
 * @CreateTime:2017/6/28
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

@Entity(nameInDb = "test")
public class TestBean {

    @Id(autoincrement = true)
    @Index
    @Property(nameInDb = "id")
    private Long id;
    @Property(nameInDb = "name")
    private String name;
    @Property(nameInDb = "age")
    private int age;
    @Property(nameInDb = "gender")
    private String gender;

    @Generated(hash = 557860391)
    public TestBean(Long id, String name, int age, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    @Generated(hash = 2087637710)
    public TestBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
