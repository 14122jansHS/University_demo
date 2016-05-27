package com.egnify.University_demo.pojos;

import java.io.Serializable;

/**
 * Created by janardhanyerranagu on 3/25/16.
 */
public class attribute_obj implements Serializable  {
    String name, metrics, final_points;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMetrics() {
        return metrics;
    }

    public void setMetrics(String metrics) {
        this.metrics = metrics;
    }

    public String getFinal_points() {
        return final_points;
    }

    public void setFinal_points(String final_points) {
        this.final_points = final_points;
    }
}
