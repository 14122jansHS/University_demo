package com.egnify.University_demo.pojos;

import java.io.Serializable;

/**
 * Created by janardhanyerranagu on 3/28/16.
 */
public class fac_info implements Serializable {
    String faculty_score;
    String teacher_name;
    String mobile;
    String email;

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getFaculty_score() {
        return faculty_score;
    }

    public void setFaculty_score(String faculty_score) {
        this.faculty_score = faculty_score;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
