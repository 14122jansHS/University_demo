package com.egnify.vignan.Adapter;


import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egnify.vignan.R;
import com.egnify.vignan.model.app_url;
import com.egnify.vignan.pojos.stu_info;
import com.egnify.vignan.utils.p_MyCustomTextView;
import com.egnify.vignan.utils.p_MyCustomTextView_mbold;
import com.egnify.vignan.utils.p_MyCustomTextView_regular;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class m_d_adapter extends RecyclerView.Adapter<m_d_adapter.CustomViewHolder> implements View.OnClickListener {
    private Activity mContext;
    ArrayList<stu_info> mid_a_stu;
    ArrayList<stu_info> mid_m_stu;

    private Picasso picasso;

    public m_d_adapter(FragmentActivity activity, ArrayList<stu_info> mid1_a_stu, ArrayList<stu_info> mid1_m_stu) {
        this.mContext=activity;
        this.mid_a_stu=mid1_a_stu;
        this.mid_m_stu=mid1_m_stu;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stu_row_m_d, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        picasso = Picasso.with(mContext);
        picasso.setLoggingEnabled(true);
        picasso.setIndicatorsEnabled(false);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, final int i) {
        stu_info mida_pojo=mid_a_stu.get(i);
        stu_info midm_pojo=mid_m_stu.get(i);

        String url= app_url.URL_IMAGES+"stu_"+(i+1)+".jpeg";
        picasso.load(url)
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(customViewHolder.stu_pic);
        customViewHolder.stu_name.setText(mida_pojo.getStudent_name());
        customViewHolder.stu_id.setText(midm_pojo.getStudent_id());
        customViewHolder.mid_a.setText(mida_pojo.getAtt_mid1());
        customViewHolder.mid_m.setText(midm_pojo.getAtt_mid1());
    }

    @Override
    public int getItemCount() {
        return (null != mid_a_stu ? mid_a_stu.size() : 0);
    }

    @Override
    public void onClick(View v) {

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        CircularImageView stu_pic;
        p_MyCustomTextView_regular stu_name;
        p_MyCustomTextView stu_id;
        p_MyCustomTextView_mbold mid_a;
        p_MyCustomTextView_mbold mid_m;


        public CustomViewHolder(View view) {
            super(view);
            this.stu_pic=(CircularImageView) view.findViewById(R.id.stu_pic);
            this.stu_name=(p_MyCustomTextView_regular) view.findViewById(R.id.stu_name);
            this.stu_id=(p_MyCustomTextView) view.findViewById(R.id.stu_id);
            this.mid_a=(p_MyCustomTextView_mbold) view.findViewById(R.id.m1);
            this.mid_m=(p_MyCustomTextView_mbold) view.findViewById(R.id.m2);

        }
    }


}
