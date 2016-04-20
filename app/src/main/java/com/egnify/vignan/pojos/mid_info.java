package com.egnify.vignan.pojos;

import java.io.Serializable;

/**
 * Created by janardhanyerranagu on 4/1/16.
 */
public class mid_info implements Serializable {
    String exam_name;
    String marks;
    String max_marks;
    String attendance;
    String total_classes;
    String classes_present;

    public String getClasses_present() {
        return classes_present;
    }

    public void setClasses_present(String classes_present) {
        this.classes_present = classes_present;
    }

    public String getTotal_classes() {
        return total_classes;
    }

    public void setTotal_classes(String total_classes) {
        this.total_classes = total_classes;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getMax_marks() {
        return max_marks;
    }

    public void setMax_marks(String max_marks) {
        this.max_marks = max_marks;
    }

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }


}
