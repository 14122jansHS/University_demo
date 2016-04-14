package com.egnify.vignan.Adapter;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.egnify.vignan.Activities.Academics;
import com.egnify.vignan.Activities.counselling;
import com.egnify.vignan.Activities.fac_details_hod;
import com.egnify.vignan.Activities.new_home_activity;
import com.egnify.vignan.Activities.new_home_hod;
import com.egnify.vignan.Activities.other_proff_activitis;
import com.egnify.vignan.Activities.research_contri;
import com.egnify.vignan.R;
import com.egnify.vignan.pojos.attribute_obj;
import com.egnify.vignan.utils.p_MyCustomTextView_mbold;

import java.util.ArrayList;
import java.util.HashMap;


public class home_fac_adapter extends RecyclerView.Adapter<home_fac_adapter.CustomViewHolder> implements View.OnClickListener {
    private Activity mContext;
    String[] color_code = {"#FF9E01", "#0269B0", "#39B53C", "#E52116", "#5b128c"};
    String[] pername;
    ArrayList<attribute_obj> attri_arr=new ArrayList<>();


    public home_fac_adapter(new_home_activity new_home_activity, ArrayList<attribute_obj> attri_arr, String[] pername) {
        this.mContext = new_home_activity;
        this.pername = pername;
        this.attri_arr=attri_arr;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.overview_card, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, final int i) {
        attribute_obj atriss = attri_arr.get(i);
        if (i == 2) {
            attribute_obj atriss1 = attri_arr.get(i);
            attribute_obj atriss2 = attri_arr.get(i + 1);
            int points = Integer.parseInt(atriss1.getFinal_points()) + Integer.parseInt(atriss2.getFinal_points());
            customViewHolder.marks.setText(String.valueOf(points));
        } else {
            customViewHolder.marks.setText(atriss.getFinal_points());
        }
        final int finalI = i;
        customViewHolder.main_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pername[finalI].equals("Teaching")) {
                    Intent i = new Intent(mContext, Academics.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("FILES_TO_SEND", attri_arr);
                    i.putExtras(bundle);
                    mContext.startActivity(i);
                    mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                } else if (pername[finalI].equals("Conselling")) {
                    Intent i = new Intent(mContext, counselling.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("FILES_TO_SEND", attri_arr);
                    i.putExtras(bundle);
                    mContext.startActivity(i);
                    mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                } else {
                    Intent i = new Intent(mContext, research_contri.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("FILES_TO_SEND", attri_arr);
                    i.putExtras(bundle);
                    mContext.startActivity(i);
                    mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                }
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
