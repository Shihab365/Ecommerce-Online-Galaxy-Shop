package com.greenshopap.bel.greenshop_eca;

public class Store_order_data {
    public String model,price,quantity,images;

    public Store_order_data()
    {

    }

    public Store_order_data(String model, String price,String quantity,String images) {
        this.model = model;
        this.price = price;
        this.quantity = quantity;
        this.images = images;
    }
}
