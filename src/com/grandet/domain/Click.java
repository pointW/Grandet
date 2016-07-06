package com.grandet.domain;

import java.util.Date;

/**
 * Created by outen on 16/7/6.
 */
public class Click {
    private int id;
    private int productId;
    private Date date;
    private int times;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int time) {
        this.times = times;
    }
}
