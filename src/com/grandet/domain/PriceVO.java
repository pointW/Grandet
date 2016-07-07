package com.grandet.domain;

import java.util.Date;

/**
 * Created by outen on 16/7/7.
 */
public class PriceVO {
    private double price;
    private Date date;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
