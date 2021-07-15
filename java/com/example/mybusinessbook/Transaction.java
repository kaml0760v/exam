package com.example.mybusinessbook;

public class Transaction {
    String Categoty,Amount,Date,Detials,type;

    public Transaction(String categoty, String amount, String date, String detials,String type) {
        this.type = type;
        Categoty = categoty;
        Amount = amount;
        Date = date;
        Detials = detials;
    }

    public String getCategoty() {
        return Categoty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCategoty(String categoty) {
        Categoty = categoty;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDetials() {
        return Detials;
    }

    public void setDetials(String detials) {
        Detials = detials;
    }
}
