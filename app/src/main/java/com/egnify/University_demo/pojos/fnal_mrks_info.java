package com.egnify.University_demo.pojos;

import java.io.Serializable;

/**
 * Created by janardhanyerranagu on 4/1/16.
 */
public class fnal_mrks_info implements Serializable {
    String status;
    String subject_name;
    String short_name;
    String Faculty_name;
    String Faculty_id;
    String Internal_marks;
    String External_marks;
    String pass_yr;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExternal_marks() {
        return External_marks;
    }

    public void setExternal_marks(String external_marks) {
        External_marks = external_marks;
    }

    public String getPass_yr() {
        return pass_yr;
    }

    public void setPass_yr(String pass_yr) {
        this.pass_yr = pass_yr;
    }

    public String getInternal_marks() {
        return Internal_marks;
    }

    public void setInternal_marks(String internal_marks) {
        Internal_marks = internal_marks;
    }

    public String getFaculty_name() {
        return Faculty_name;
    }

    public void setFaculty_name(String faculty_name) {
        Faculty_name = faculty_name;
    }

    public String getFaculty_id() {
        return Faculty_id;
    }

    public void setFaculty_id(String faculty_id) {
        Faculty_id = faculty_id;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }


}
