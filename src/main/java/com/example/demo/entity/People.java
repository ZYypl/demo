package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * com.example.demo.entity
 *
 * @author ypl
 * @create 2020-06-16 09:44
 */
@Table(name = "PEOPLE")
public class People implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id; //id

    @Column(name = "name")
    private String name; //用户名
    @Column(name = "address")
    private String address; //地址
    @Column(name = "phone")
    private String phone; //手机号
    @Column(name = "email")
    private String email; //手机号
    public People() {
    }

    public People(Integer id, String name, String address, String phone, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
