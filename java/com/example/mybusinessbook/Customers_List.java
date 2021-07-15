package com.example.mybusinessbook;

public class Customers_List {
    private String Name,Address,Mobileno;
//    private String Address;
//    private String Mobileno;

    public Customers_List(String name, String address, String mobileno) {
        Name = name;
        Address = address;
        Mobileno = mobileno;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMobileno() {
        return Mobileno;
    }

    public void setMobileno(String mobileno) {
        Mobileno = mobileno;
    }
}
