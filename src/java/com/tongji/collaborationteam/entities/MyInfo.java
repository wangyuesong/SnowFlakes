/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tongji.collaborationteam.entities;

import java.util.ArrayList;


/**
 *
 * @author coodoo
 */


public class MyInfo {
    private int id;
    private String password;
    private String email;
    private String name;
    private String birthday;
    private String address1;
    private String address2;
    private String bir;
    public String getBir(){
        return bir;
    }
    public void setBir(String b){
        this.bir = b;
    }

    private String phone;
    private int[] notification_settings; 
    private ArrayList MessageList;
    private ArrayList ProjectList;
    
    
    
    public MyInfo(){
        password="";
        email="";
        name="";
        birthday="";
        address1="";
        address2="";
        phone="";
        bir = "";
        notification_settings = new int[15];
        MessageList = new ArrayList();
        ProjectList = new ArrayList();
    }
    
    public MyInfo(String name, String email, String password){
        this.name=name;
        this.email=email;
        this.password=password;
        
    }

    
    
    public MyInfo(String name, String email, String password, String birthday, String add1, String add2, String phone){
        this.name=name;
        this.email=email;
        this.password=password;
        if(!"".equals(birthday))
            this.birthday=birthday;
        if(!"".equals(add1))
            this.address1= add1;
        if(!"".equals(add2))
            this.address2= add2;
        if(!"".equals(phone))
            this.phone=phone;
    }

    public ArrayList getMessageList() {
        return MessageList;
    }

    public void setMessageList(ArrayList MessageList) {
        this.MessageList = MessageList;
    }

    public ArrayList getProjectList() {
        return ProjectList;
    }

    public void setProjectList(ArrayList ProjectList) {
        this.ProjectList = ProjectList;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int[] getNotification_settings() {
        return notification_settings;
    }

    public void setNotification_settings(int[] notification_settings) {
        this.notification_settings = notification_settings;
    }
    
}
