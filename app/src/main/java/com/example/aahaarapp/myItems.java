package com.example.aahaarapp;

public final class myItems {
    private  String name,food,desc;
  //  private String phone;

    public myItems(String name,String food, String desc) {
        this.name = name;
        this.food = food;
        this.desc = desc;
    }
    //delete old constructor
    /*
    public myItems(String name,String food,String phone, String desc) {
        this.name = name;
        this.food = food;
        this.phone = phone;
        this.desc = desc;
    }
    public String getPhone() {
        return phone;
    }
*/

    public String getName() {
        return name;
    }

    public String getFood() {
        return food;
    }

    public String getDesc() {
        return desc;
    }
}
