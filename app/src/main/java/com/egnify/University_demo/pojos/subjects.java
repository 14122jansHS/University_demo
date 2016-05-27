package com.egnify.University_demo.pojos;

import java.io.Serializable;

/**
 * Created by janardhanyerranagu on 4/1/16.
 */
public class subjects implements Serializable {
    String subject_name, short_name, Faculty_name, Faculty_id;

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getFaculty_id() {
        return Faculty_id;
    }

    public void setFaculty_id(String faculty_id) {
        Faculty_id = faculty_id;
    }

    public String getFaculty_name() {
        return Faculty_name;
    }

    public void setFaculty_name(String faculty_name) {
        Faculty_name = faculty_name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }


}
