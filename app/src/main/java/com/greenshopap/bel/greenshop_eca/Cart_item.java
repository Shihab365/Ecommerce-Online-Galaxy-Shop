package com.greenshopap.bel.greenshop_eca;

public class Cart_item {
    String model,price,quantity;

    Cart_item()
    {

    }

    public Cart_item(String model, String price, String quantity) {
        this.model = model;
        this.price = price;
        this.quantity = quantity;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
