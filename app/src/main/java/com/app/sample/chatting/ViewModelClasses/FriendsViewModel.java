package com.app.sample.chatting.ViewModelClasses;

import java.io.Serializable;

public class FriendsViewModel implements Serializable{


    public long Id;
    public String Name;
    public String Email;
    public String PhoneNo;
    public Boolean Flag;
    public int Status;

    public int getPhoto() {
        return Photo;
    }

    public void setPhoto(int photo) {
        Photo = photo;
    }

    public int Photo;

    public FriendsViewModel(long id, String name, int photo) {
        this.Id = id;
        this.Name = name;
        this.Photo = photo;
    }    public FriendsViewModel(String name, String email, int id, String phoneno, boolean flag, int status,int photo){
        this.Email=email;
        this.Flag=flag;
        this.Id=id;
        this.Name=name;
        this.PhoneNo=phoneno;
        this.Status=status;
        this.Photo=photo;
    }

    public FriendsViewModel() {

    }
    public FriendsViewModel(String name,int photo){
        this.Photo=photo;
        this.Name=name;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public Boolean getFlag() {
        return Flag;
    }

    public void setFlag(Boolean flag) {
        Flag = flag;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
