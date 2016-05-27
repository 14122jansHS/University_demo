package com.egnify.University_demo.pojos;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Comparator;

/**
 * Created by janardhanyerranagu on 4/1/16.
 */
public class stu_marks implements Serializable {
    String std_id,std_name;
    double total;
    int mid1,mid2,mid3;

    public String getStd_id() {
        return std_id;
    }

    public void setStd_id(String std_id) {
        this.std_id = std_id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getMid3() {
        return mid3;
    }

    public void setMid3(int mid3) {
        this.mid3 = mid3;
    }

    public int getMid2() {
        return mid2;
    }

    public void setMid2(int mid2) {
        this.mid2 = mid2;
    }

    public int getMid1() {
        return mid1;
    }

    public void setMid1(int mid1) {
        this.mid1 = mid1;
    }

    public String getStd_name() {
        return std_name;
    }

    public void setStd_name(String std_name) {
        this.std_name = std_name;
    }
    /*@Override
    public int compareTo(stu_marks employee) {
        double compareSalary = ((stu_marks) employee).getTotal();

        // ascending order
        // return (int) (this.salary - compareSalary);

        // descending order
        return (int) (compareSalary - this.total);
    }*/


    // Comparator
    public static class CompId implements Comparator<stu_marks> {


        @Override
        public int compare(stu_marks lhs, stu_marks rhs) {
            DecimalFormat df = new DecimalFormat("##");
            int l_hs=Integer.parseInt(df.format(lhs.total));
            int r_hs=Integer.parseInt(df.format(rhs.total));
            return l_hs - r_hs;
        }
    }



}
