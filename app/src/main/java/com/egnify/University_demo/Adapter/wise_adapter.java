package com.egnify.University_demo.Adapter;


import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.albinmathew.transitions.ActivityTransitionLauncher;
import com.egnify.University_demo.Activities.attendance;
import com.egnify.University_demo.Activities.stu_mid_marks;
import com.egnify.University_demo.R;
import com.egnify.University_demo.utils.p_MyCustomTextView_mbold;

import org.json.JSONObject;


public class wise_adapter extends RecyclerView.Adapter<wise_adapter.CustomViewHolder> implements View.OnClickListener {
    private static int[] color = new int[]{
            R.color.lred, R.color.lgreen, R.color.blue, R.color.c1
    };
    private static String[] data = new String[]{
            "Mid Marks", "Attendance", "Final Marks", "Other Details"
    };
    private static int[] icon = new int[]{
            R.drawable.ic_poll_box, R.drawable.ic_clipboard_text, R.drawable.ic_chart_line, R.drawable.ic_action_coins
    };
    JSONObject pre_sem_details;
    private Activity mContext;

    public wise_adapter(FragmentActivity activity, JSONObject pre_sem_details) {
        this.mContext = activity;
        this.pre_sem_details = pre_sem_details;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wise_item, null);
        final CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, final int i) {
        customViewHolder.menu_text.setText(data[i]);
        customViewHolder.testwise.setBackgroundColor(mContext.getResources().getColor(color[i]));
        customViewHolder.image.setBackgroundDrawable(mContext.getResources().getDrawable(icon[i]));
        customViewHolder.testwise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    Intent intent = new Intent(mContext, stu_mid_marks.class);
                    intent.putExtra("color_id", i);
                    intent.putExtra("pre_sem_details", pre_sem_details.toString());
                    ActivityTransitionLauncher.with(mContext).from(v).launch(intent);

                } else if (i == 1) {
                    Intent intent = new Intent(mContext, attendance.class);
                    intent.putExtra("color_id", i);
                    intent.putExtra("pre_sem_details", pre_sem_details.toString());
                    ActivityTransitionLauncher.with(mContext).from(v).launch(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != data ? data.length : 0);
    }

    @Override
    public void onClick(View v) {

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout testwise;
        p_MyCustomTextView_mbold menu_text;
        ImageView image;

        public CustomViewHolder(View view) {
            super(view);
            this.image = (ImageView) view.findViewById(R.id.image);
            this.testwise = (RelativeLayout) view.findViewById(R.id.testwise);
            this.menu_text = (p_MyCustomTextView_mbold) view.findViewById(R.id.menu_text);
        }
    }


}
