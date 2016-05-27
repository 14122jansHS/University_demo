package com.egnify.University_demo.Adapter;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.egnify.University_demo.Activities.fac_details_hod;
import com.egnify.University_demo.Activities.new_home_hod;
import com.egnify.University_demo.R;
import com.egnify.University_demo.utils.p_MyCustomTextView_mbold;

import java.util.ArrayList;
import java.util.HashMap;


public class home_hod_adapter extends RecyclerView.Adapter<home_hod_adapter.CustomViewHolder> implements View.OnClickListener {
    String[] color_code = {"#FF9E01", "#0269B0", "#39B53C", "#E52116", "#5b128c"};
    String allotedclasses, counsellingstudents, final_val;
    String[] pername;
    ArrayList<HashMap<String, String>> f_list;
    private Activity mContext;

    public home_hod_adapter(new_home_hod new_home_hod, String allotedclasses, String counsellingstudents, String final_val, String[] pername, ArrayList<HashMap<String, String>> f_list) {
        this.mContext = new_home_hod;
        this.allotedclasses = allotedclasses;
        this.final_val = final_val;
        this.counsellingstudents = counsellingstudents;
        this.pername = pername;
        this.f_list = f_list;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.overview_card, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, final int i) {
        if (i == 0) {
            customViewHolder.marks.setText(String.valueOf(allotedclasses));
        } else if (i == 1) {
            customViewHolder.marks.setText(String.valueOf(counsellingstudents));
        } else {
            //int final_val = ResearchatFacultyLevel + ResearchatHODlevel + OtherProfessionalActivites;
            customViewHolder.marks.setText(final_val);
        }

        final int finalI = i;
        customViewHolder.main_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                if (finalI == 0) {
                    count = 1;
                } else if (finalI == 1) {
                    count = 2;
                } else if (finalI == 2) {
                    count = 3;
                } else {
                    count = 4;
                }
                Intent i = new Intent(mContext, fac_details_hod.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("fac_details", f_list);
                bundle.putInt("id", count);
                i.putExtras(bundle);
                mContext.startActivity(i);
                mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        String col = color_code[i];
        customViewHolder.line_col.setBackgroundColor(Color.parseColor(col));
        customViewHolder.bg_col.setBackgroundColor(Color.parseColor(col));
        customViewHolder.perf_name.setText(pername[i]);


    }

    @Override
    public int getItemCount() {
        return (null != pername ? pername.length : 0);
    }

    @Override
    public void onClick(View v) {

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        p_MyCustomTextView_mbold marks;
        RelativeLayout bg_col;
        View line_col;
        p_MyCustomTextView_mbold perf_name;
        LinearLayout main_lay;

        public CustomViewHolder(View view) {
            super(view);
            this.main_lay = (LinearLayout) view.findViewById(R.id.main_ll);
            this.marks = (p_MyCustomTextView_mbold) view.findViewById(R.id.marks);
            this.bg_col = (RelativeLayout) view.findViewById(R.id.bg_col);
            this.line_col = view.findViewById(R.id.b_line);
            this.perf_name = (p_MyCustomTextView_mbold) view.findViewById(R.id.perf_name);
            this.main_lay = (LinearLayout) view.findViewById(R.id.main_ll);
        }
    }


}
