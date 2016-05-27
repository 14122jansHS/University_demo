package com.egnify.University_demo.pojos;

import java.io.Serializable;

/**
 * Created by janardhanyerranagu on 3/28/16.
 */
public class new_stu_info implements Serializable {

    String Batch;
    String email_id;
    String Course;
    String student_name;
    String std_id;
    String present_year;
    String present_sem;
    String course;
    String phone_number;

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public String getPresent_sem() {
        return present_sem;
    }

    public void setPresent_sem(String present_sem) {
        this.present_sem = present_sem;
    }

    public String getPresent_year() {
        return present_year;
    }

    public void setPresent_year(String present_year) {
        this.present_year = present_year;
    }

    public String getStd_id() {
        return std_id;
    }

    public void setStd_id(String std_id) {
        this.std_id = std_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getBatch() {
        return Batch;
    }

    public void setBatch(String batch) {
        Batch = batch;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

}
