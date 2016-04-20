package com.egnify.vignan.Adapter;


import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import com.egnify.vignan.R;
import com.egnify.vignan.pojos.mid_info;
import com.egnify.vignan.pojos.subjects;
import com.egnify.vignan.utils.p_MyCustomTextView_mbold;
import com.egnify.vignan.utils.p_MyCustomTextView_regular;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class attend_sub_adapter extends RecyclerView.Adapter<attend_sub_adapter.CustomViewHolder> implements View.OnClickListener {
    ArrayList<ArrayList<mid_info>> mid_sub_arr = new ArrayList<>();
    ArrayList<subjects> sub_arr = new ArrayList<>();
    int pStatus = 0;
    Handler handler = new Handler();
    private Activity mContext;
    private Picasso picasso;

    public attend_sub_adapter(FragmentActivity activity, ArrayList<ArrayList<mid_info>> mid_sub_arr, ArrayList<subjects> sub_arr) {
        this.mContext = activity;
        this.mid_sub_arr = mid_sub_arr;
        this.sub_arr = sub_arr;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.attendance_progress, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        picasso = Picasso.with(mContext);
        picasso.setLoggingEnabled(true);
        picasso.setIndicatorsEnabled(false);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, final int i) {
        subjects sub_pojo = sub_arr.get(i);
        customViewHolder.sub_name.setText(sub_pojo.getSubject_name());
        ArrayList<mid_info> l_mis_arr = mid_sub_arr.get(i);
        int total_working = 0;
        int present_cls = 0;
        double percentage = 0;
        for (int j = 0; j < l_mis_arr.size(); j++) {
            mid_info mid_pojo = l_mis_arr.get(j);
            total_working += Integer.parseInt(mid_pojo.getTotal_classes());
            present_cls += Integer.parseInt(mid_pojo.getClasses_present());
            percentage += Double.parseDouble(mid_pojo.getAttendance());
        }

        double per = (percentage / 3);
        DecimalFormat df = new DecimalFormat("##");
        String cus_val = "Present and Total Class: " + String.valueOf(present_cls) + "/" + String.valueOf(total_working);
        customViewHolder.value.setText(cus_val);
        customViewHolder.percentage.setText(df.format(per) + "%");
        pStatus = Integer.parseInt(df.format(per));
        Resources res = mContext.getResources();
        int per_i = Integer.parseInt(df.format(per));
        Drawable drawable = null;
        if (per_i > 75) {
            drawable = res.getDrawable(R.drawable.good_progress);
            customViewHolder.percentage.setTextColor(mContext.getResources().getColor(R.color.a1));
        } else if (per_i >= 65) {
            drawable = res.getDrawable(R.drawable.danger_zone_progress);
            customViewHolder.percentage.setTextColor(mContext.getResources().getColor(R.color.b1));
        } else {
            drawable = res.getDrawable(R.drawable.condonation_progress);
            customViewHolder.percentage.setTextColor(mContext.getResources().getColor(R.color.red));
        }


        customViewHolder.progressbar1.setProgress(pStatus);   // Main Progress// Secondary Progress
        customViewHolder.progressbar1.setProgressDrawable(drawable);
        ObjectAnimator animation = ObjectAnimator.ofInt(customViewHolder.progressbar1, "progress", 0, pStatus);
        animation.setDuration(1500);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

    }

    @Override
    public int getItemCount() {
        return (null != sub_arr ? sub_arr.size() : 0);
    }

    @Override
    public void onClick(View v) {

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        p_MyCustomTextView_mbold sub_name;
        p_MyCustomTextView_regular percentage;
        p_MyCustomTextView_regular value;
        ProgressBar progressbar1;

        public CustomViewHolder(View view) {
            super(view);
            this.sub_name = (p_MyCustomTextView_mbold) view.findViewById(R.id.sub_name);
            this.value = (p_MyCustomTextView_regular) view.findViewById(R.id.value);
            this.percentage = (p_MyCustomTextView_regular) view.findViewById(R.id.percentage);
            this.progressbar1 = (ProgressBar) view.findViewById(R.id.progressbar1);

        }
    }


}
