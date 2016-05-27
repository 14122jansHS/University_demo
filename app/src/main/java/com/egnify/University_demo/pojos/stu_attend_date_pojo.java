package com.egnify.University_demo.pojos;

import java.io.Serializable;

/**
 * Created by janardhanyerranagu on 3/26/16.
 */
public class stu_attend_date_pojo implements Serializable {
    String from_date;
    String to_date;
    String percentage;
    String byvalue;

    public String getByvalue() {
        return byvalue;
    }

    public void setByvalue(String byvalue) {
        this.byvalue = byvalue;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

}
