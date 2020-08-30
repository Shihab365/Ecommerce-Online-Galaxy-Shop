package com.greenshopap.bel.greenshop_eca;

public class Shipping_order_item {

    public String shipFirst,shipLast,shipAddress1,shipAddress2,shipCity,shipZip,shipPhone;

    public Shipping_order_item()
    {

    }

    public Shipping_order_item(String shipFirst, String shipLast, String shipAddress1, String shipAddress2, String shipCity, String shipZip, String shipPhone) {
        this.shipFirst = shipFirst;
        this.shipLast = shipLast;
        this.shipAddress1 = shipAddress1;
        this.shipAddress2 = shipAddress2;
        this.shipCity = shipCity;
        this.shipZip = shipZip;
        this.shipPhone = shipPhone;
    }
}
