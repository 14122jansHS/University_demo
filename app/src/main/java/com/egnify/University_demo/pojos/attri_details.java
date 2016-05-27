package com.egnify.University_demo.pojos;

import java.io.Serializable;

/**
 * Created by janardhanyerranagu on 3/26/16.
 */
public class attri_details implements Serializable {
    String alloted_mid1, exam, taken_mid1, marks;

    public String getAlloted_mid1() {
        return alloted_mid1;
    }

    public void setAlloted_mid1(String alloted_mid1) {
        this.alloted_mid1 = alloted_mid1;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public String getTaken_mid1() {
        return taken_mid1;
    }

    public void setTaken_mid1(String taken_mid1) {
        this.taken_mid1 = taken_mid1;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }
}
