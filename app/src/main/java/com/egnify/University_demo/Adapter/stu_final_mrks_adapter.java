package com.egnify.University_demo.Adapter;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.egnify.University_demo.Activities.stu_final_marks;
import com.egnify.University_demo.R;
import com.egnify.University_demo.pojos.fnal_mrks_info;
import com.egnify.University_demo.utils.p_MyCustomTextView_mbold;
import com.egnify.University_demo.utils.p_MyCustomTextView_regular;

import java.util.ArrayList;
import java.util.HashMap;


public class stu_final_mrks_adapter extends RecyclerView.Adapter<stu_final_mrks_adapter.CustomViewHolder> implements View.OnClickListener {
    String[] pername;
    String[] color_code = {"#FF9E01", "#0269B0", "#39B53C", "#E52116", "#5b128c", "#FF9E01", "#0269B0", "#39B53C", "#E52116", "#5b128c", "#FF9E01", "#0269B0", "#39B53C", "#E52116", "#5b128c", "#FF9E01", "#0269B0", "#39B53C", "#E52116", "#5b128c"};
    String[] color_code2 = {"#FFC05C", "#0286DE", "#83D884", "#EE5F58", "#8D1CD9", "#FFC05C", "#0286DE", "#83D884", "#EE5F58", "#8D1CD9", "#FFC05C", "#0286DE", "#83D884", "#EE5F58", "#8D1CD9", "#FFC05C", "#0286DE", "#83D884", "#EE5F58", "#8D1CD9"};
    ArrayList<HashMap<String, String>> attri_arr = new ArrayList<>();
    ArrayList<ArrayList<fnal_mrks_info>> pername_arr = new ArrayList<>();
    private Activity mContext;
    private ValueAnimator mAnimator;
    private LayoutInflater lf;

    public stu_final_mrks_adapter(stu_final_marks new_home_activity, ArrayList<HashMap<String, String>> attri_arr, ArrayList<ArrayList<fnal_mrks_info>> pername) {
        this.mContext = new_home_activity;
        this.attri_arr = attri_arr;
        this.pername_arr = pername;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fnl_mrk_expand_card, null);
        final CustomViewHolder viewHolder = new CustomViewHolder(view);
        lf = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewHolder.mLinearLayout.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        viewHolder.mLinearLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                        viewHolder.mLinearLayout.setVisibility(View.GONE);

                        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        viewHolder.mLinearLayout.measure(widthSpec, heightSpec);

                        mAnimator = slideAnimator(0, viewHolder.mLinearLayout.getMeasuredHeight(), viewHolder.mLinearLayout);
                        return true;
                    }
                });


        viewHolder.mLinearLayoutHeader.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (viewHolder.mLinearLayout.getVisibility() == View.GONE) {
                    expand(viewHolder.mLinearLayout, viewHolder.arrow, viewHolder.met_name, viewHolder.b_line);
                } else {
                    collapse(viewHolder.mLinearLayout, viewHolder.arrow, viewHolder.met_name, viewHolder.b_line);
                }
            }
        });
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, final int i) {
        String col = color_code[i];
        String col2 = color_code2[i];
        customViewHolder.point_bg.setBackgroundColor(Color.parseColor(col));
        customViewHolder.table_header.setBackgroundColor(Color.parseColor(col2));
        customViewHolder.b_line.setBackgroundColor(Color.parseColor(col));
        HashMap<String, String> det_map = attri_arr.get(i);
        customViewHolder.points.setText(det_map.get("pass_percentage") + "%");
        customViewHolder.met_name.setText(det_map.get("sem_name"));
        ArrayList<fnal_mrks_info> l_fina = pername_arr.get(i);
        for (int j = 0; j < l_fina.size(); j++) {
            fnal_mrks_info finalh_map = l_fina.get(j);
            View sub_row = lf.inflate(R.layout.row_fnal_stu, null);
            p_MyCustomTextView_regular stu_name = (p_MyCustomTextView_regular) sub_row.findViewById(R.id.sub_name);
            p_MyCustomTextView_regular inte = (p_MyCustomTextView_regular) sub_row.findViewById(R.id.inte);
            p_MyCustomTextView_regular ext = (p_MyCustomTextView_regular) sub_row.findViewById(R.id.ext);
            p_MyCustomTextView_regular status = (p_MyCustomTextView_regular) sub_row.findViewById(R.id.status);
            p_MyCustomTextView_regular pdate = (p_MyCustomTextView_regular) sub_row.findViewById(R.id.pdate);

            stu_name.setText(finalh_map.getSubject_name());
            inte.setText(finalh_map.getInternal_marks());
            ext.setText(finalh_map.getExternal_marks());
            status.setText(finalh_map.getStatus());
            pdate.setText(finalh_map.getPass_yr());
            customViewHolder.mLinearLayout.addView(sub_row);
        }

    }

    @Override
    public int getItemCount() {
        return (null != attri_arr ? attri_arr.size() : 0);
    }

    @Override
    public void onClick(View v) {

    }

    private void expand(LinearLayout mLinearLayout, ImageView arrow, p_MyCustomTextView_regular met_name, View b_line) {
        Log.d("expand", "entered");
        //set Visible
        mLinearLayout.setVisibility(View.VISIBLE);
        arrow.setImageDrawable(mContext.getResources().getDrawable(R.drawable.up_arrow));
        met_name.setSingleLine(false);
        // Remove and used in preDrawListener
        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mLinearLayout.measure(widthSpec, heightSpec);

        mAnimator = slideAnimator(0, mLinearLayout.getMeasuredHeight(), mLinearLayout);
        b_line.setVisibility(View.GONE);

        mAnimator.start();
    }

    private ValueAnimator slideAnimator(int start, int end, final LinearLayout mLinearLayout2) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);


        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = mLinearLayout2.getLayoutParams();
                layoutParams.height = value;
                mLinearLayout2.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    private void collapse(final LinearLayout mLinearLayout2, ImageView arrow, p_MyCustomTextView_regular met_name, final View b_line) {
        int finalHeight = mLinearLayout2.getHeight();
        arrow.setImageDrawable(mContext.getResources().getDrawable(R.drawable.down_arrow));
        ValueAnimator mAnimator = slideAnimator(finalHeight, 0, mLinearLayout2);
        met_name.setSingleLine(true);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                mLinearLayout2.setVisibility(View.GONE);
                b_line.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimator.start();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView arrow;
        LinearLayout mLinearLayout;
        LinearLayout mLinearLayoutHeader;
        LinearLayout table_header;
        RelativeLayout point_bg;
        View b_line;
        p_MyCustomTextView_regular met_name;
        p_MyCustomTextView_mbold points;

        public CustomViewHolder(View view) {
            super(view);
            this.arrow = (ImageView) view.findViewById(R.id.arrow);
            this.mLinearLayout = (LinearLayout) view.findViewById(R.id.expandable);
            this.mLinearLayoutHeader = (LinearLayout) view.findViewById(R.id.header);
            this.table_header = (LinearLayout) view.findViewById(R.id.table_header);
            this.point_bg = (RelativeLayout) view.findViewById(R.id.point_bg);
            this.b_line = view.findViewById(R.id.b_line);
            this.met_name = (p_MyCustomTextView_regular) view.findViewById(R.id.met_name);
            this.points = (p_MyCustomTextView_mbold) view.findViewById(R.id.points);

        }
    }

}
