package com.egnify.University_demo.pojos;

import java.io.Serializable;

/**
 * Created by janardhanyerranagu on 3/26/16.
 */
public class attend_info implements Serializable {
    String exam;
    String class_avg;
    String marks;

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public String getClass_avg() {
        return class_avg;
    }

    public void setClass_avg(String class_avg) {
        this.class_avg = class_avg;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }
}
