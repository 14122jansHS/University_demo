package com.egnify.University_demo.Adapter;


import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.egnify.University_demo.Activities.attendance;
import com.egnify.University_demo.Activities.stu_final_marks;
import com.egnify.University_demo.Activities.stu_his;
import com.egnify.University_demo.Activities.stu_mid_marks;
import com.egnify.University_demo.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;


public class student_item_adapter extends RecyclerView.Adapter<student_item_adapter.CustomViewHolder> implements View.OnClickListener {
    private static int[] color = new int[]{
            R.color.lred, R.color.lgreen, R.color.blue, R.color.c1
    };
    private static String[] data = new String[]{
            "Mid Marks", "Attendance", "Final Marks", "Background Details"
    };
    private static String[] ids = new String[]{
            "test_wise", "class_wise", "performance_wise", "bg_img"
    };
    private static int[] icon = new int[]{
            R.drawable.ic_poll_box, R.drawable.ic_clipboard_text, R.drawable.ic_chart_line, R.drawable.ic_action_coins
    };
    JSONObject pre_sem_details;
    JSONObject previous_sem_detials;
    private Activity mContext;

    public student_item_adapter(FragmentActivity activity, JSONObject pre_sem_details, JSONObject previous_sem_detials) {
        this.mContext = activity;
        this.pre_sem_details = pre_sem_details;
        this.previous_sem_detials = previous_sem_detials;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_item, null);
        final CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, final int i) {
        customViewHolder.menu_text.setText(data[i]);
        int resid = mContext.getResources().getIdentifier(ids[i], "drawable", mContext.getPackageName());
        Picasso.with(mContext).load(resid).into(customViewHolder.imageView);
        //customViewHolder.testwise.setBackgroundColor(mContext.getResources().getColor(color[i]));
        //customViewHolder.image.setBackgroundDrawable(mContext.getResources().getDrawable(icon[i]));
        customViewHolder.menu_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    Intent intent = new Intent(mContext, stu_mid_marks.class);
                    intent.putExtra("color_id", i);
                    intent.putExtra("pre_sem_details", pre_sem_details.toString());
                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    // ActivityTransitionLauncher.with(mContext).from(v).launch(intent);

                } else if (i == 1) {
                    Intent intent = new Intent(mContext, attendance.class);
                    intent.putExtra("color_id", i);
                    intent.putExtra("pre_sem_details", pre_sem_details.toString());
                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    //  ActivityTransitionLauncher.with(mContext).from(v).launch(intent);
                } else if (i == 2) {
                    Intent intent = new Intent(mContext, stu_final_marks.class);
                    intent.putExtra("color_id", i);
                    intent.putExtra("previous_sem_detials", previous_sem_detials.toString());
                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    //  ActivityTransitionLauncher.with(mContext).from(v).launch(intent);
                } else {
                    Intent intent = new Intent(mContext, stu_his.class);
                    intent.putExtra("color_id", i);
                    intent.putExtra("previous_sem_detials", previous_sem_detials.toString());
                    mContext.startActivity(intent);
                    mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    //  ActivityTransitionLauncher.with(mContext).from(v).launch(intent);
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

        ImageView imageView;
        Button menu_text;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.menu_text = (Button) view.findViewById(R.id.push_button);
        }
    }


}
