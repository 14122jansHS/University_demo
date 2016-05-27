package com.egnify.University_demo.Adapter;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.egnify.University_demo.Activities.new_student;
import com.egnify.University_demo.R;
import com.egnify.University_demo.model.app_url;
import com.egnify.University_demo.pojos.stu_info;
import com.egnify.University_demo.utils.p_MyCustomTextView;
import com.egnify.University_demo.utils.p_MyCustomTextView_mbold;
import com.egnify.University_demo.utils.p_MyCustomTextView_regular;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class o_a_adapter extends RecyclerView.Adapter<o_a_adapter.CustomViewHolder> implements View.OnClickListener {
    ArrayList<stu_info> mid1_stu;
    ArrayList<stu_info> mid3_stu;
    ArrayList<stu_info> mid2_stu;
    private Activity mContext;
    private Picasso picasso;


    public o_a_adapter(FragmentActivity activity, ArrayList<stu_info> mid1_stu, ArrayList<stu_info> mid2_stu, ArrayList<stu_info> mid3_stu) {
        this.mContext = activity;
        this.mid1_stu = mid1_stu;
        this.mid2_stu = mid2_stu;
        this.mid3_stu = mid3_stu;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stu_row_o_a, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        picasso = Picasso.with(mContext);
        picasso.setLoggingEnabled(true);
        picasso.setIndicatorsEnabled(false);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, final int i) {
        final stu_info mid1_pojo = mid1_stu.get(i);
        stu_info mid2_pojo = mid2_stu.get(i);
        stu_info mid3_pojo = mid3_stu.get(i);
        String url = app_url.URL_IMAGES + "stu_" + (i + 1) + ".jpeg";
        picasso.load(url)
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(customViewHolder.stu_pic);
        customViewHolder.stu_name.setText(mid1_pojo.getStudent_name());
        customViewHolder.stu_id.setText(mid1_pojo.getStudent_id());
        customViewHolder.mid1.setText(mid1_pojo.getAtt_mid1());
        customViewHolder.mid2.setText(mid2_pojo.getAtt_mid1());
        customViewHolder.mid3.setText(mid3_pojo.getAtt_mid1());
        Double per = Double.valueOf(((Integer.parseInt(mid1_pojo.getAtt_mid1()) + Integer.parseInt(mid2_pojo.getAtt_mid1()) + Integer.parseInt(mid3_pojo.getAtt_mid1())) * 100) / 300);
        DecimalFormat df = new DecimalFormat("##.0");
        customViewHolder.total_avg.setText(df.format(per) + "%");

        customViewHolder.stu_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, new_student.class);
                Bundle bundle = new Bundle();
                //bundle.putSerializable("fac_details", f_list);
                intent.putExtra("stu_name", mid1_pojo.getStudent_name());
                intent.putExtra("stu_id", String.valueOf(i + 1));
                mContext.startActivity(intent);
                mContext.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != mid1_stu ? mid1_stu.size() : 0);
    }

    @Override
    public void onClick(View v) {

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        CircularImageView stu_pic;
        p_MyCustomTextView_regular stu_name;
        p_MyCustomTextView stu_id;
        p_MyCustomTextView_mbold mid1;
        p_MyCustomTextView_mbold mid2;
        p_MyCustomTextView_mbold mid3;
        p_MyCustomTextView_mbold total_avg;
        LinearLayout stu_row;

        public CustomViewHolder(View view) {
            super(view);
            this.stu_pic = (CircularImageView) view.findViewById(R.id.stu_pic);
            this.stu_name = (p_MyCustomTextView_regular) view.findViewById(R.id.stu_name);
            this.stu_id = (p_MyCustomTextView) view.findViewById(R.id.stu_id);
            this.mid1 = (p_MyCustomTextView_mbold) view.findViewById(R.id.m1);
            this.mid2 = (p_MyCustomTextView_mbold) view.findViewById(R.id.m2);
            this.mid3 = (p_MyCustomTextView_mbold) view.findViewById(R.id.m3);
            this.total_avg = (p_MyCustomTextView_mbold) view.findViewById(R.id.avg);
            this.stu_row = (LinearLayout) view.findViewById(R.id.stu_row);
        }
    }


}
