package com.example.thuchanhv3;

public class Employee {
    private String id,name,contact,address,user_name,password,classRoom,admin_id;

    public Employee(){

    }

    public Employee(String id, String name, String contact, String address, String user_name, String password, String classRoom, String admin_id) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.user_name = user_name;
        this.password = password;
        this.classRoom = classRoom;
        this.admin_id = admin_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        admin_id = admin_id;
    }
}
