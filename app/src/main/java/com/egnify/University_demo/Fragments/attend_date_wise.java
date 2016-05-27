package com.egnify.University_demo.Fragments;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.egnify.University_demo.R;
import com.egnify.University_demo.pojos.attend_d_s_pojo;
import com.egnify.University_demo.pojos.stu_attend_date_pojo;
import com.egnify.University_demo.utils.p_MyCustomTextView_mbold;
import com.egnify.University_demo.utils.p_MyCustomTextView_regular;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class attend_date_wise extends Fragment {
    String[] color_code = {"#FF9E01", "#0269B0", "#39B53C", "#E52116", "#5b128c", "#FF9E01", "#0269B0", "#39B53C", "#E52116", "#5b128c", "#FF9E01", "#0269B0", "#39B53C", "#E52116", "#5b128c", "#FF9E01", "#0269B0", "#39B53C", "#E52116", "#5b128c"};
    String[] color_code2 = {"#FFC05C", "#0286DE", "#83D884", "#EE5F58", "#8D1CD9", "#FFC05C", "#0286DE", "#83D884", "#EE5F58", "#8D1CD9", "#FFC05C", "#0286DE", "#83D884", "#EE5F58", "#8D1CD9", "#FFC05C", "#0286DE", "#83D884", "#EE5F58", "#8D1CD9"};
    String attendance_details;
    ArrayList<stu_attend_date_pojo> dates_arr = new ArrayList<>();
    ArrayList<ArrayList<attend_d_s_pojo>> dates__sub_arr = new ArrayList<>();
    DecimalFormat df = new DecimalFormat("##.0");
    private LayoutInflater lf;
    private LinearLayout main_layout;
    private ValueAnimator mAnimator;

    public attend_date_wise() {
        // Required empty public constructor
    }

    public static attend_date_wise newInstance(String attendance_details) {
        attend_date_wise fragment = new attend_date_wise();
        Bundle args = new Bundle();
        args.putSerializable("attendance_details", attendance_details);
        fragment.setArguments(args);
        return fragment;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_attend_date_wise, container, false);
        attendance_details = (String) getArguments().getSerializable("attendance_details");
        lf = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        main_layout = (LinearLayout) rootview.findViewById(R.id.main_layout);
        new parse_json().execute();
        return rootview;
    }

    private void expand(LinearLayout mLinearLayout, ImageView arrow, p_MyCustomTextView_regular met_name, View b_line) {
        Log.d("expand", "entered");
        //set Visible
        mLinearLayout.setVisibility(View.VISIBLE);
        arrow.setImageDrawable(getResources().getDrawable(R.drawable.up_arrow));
        met_name.setSingleLine(false);
        // Remove and used in preDrawListener
        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mLinearLayout.measure(widthSpec, heightSpec);

        mAnimator = slideAnimator(0, mLinearLayout.getMeasuredHeight(), mLinearLayout);
        b_line.setVisibility(View.GONE);

        mAnimator.start();
    }

    private void collapse(final LinearLayout mLinearLayout2, ImageView arrow, p_MyCustomTextView_regular met_name, final View b_line) {
        int finalHeight = mLinearLayout2.getHeight();
        arrow.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow));
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

    public class parse_json extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                JSONArray arr = new JSONArray(attendance_details);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject Sub_details = arr.getJSONObject(i);
                    stu_attend_date_pojo subjects = new stu_attend_date_pojo();
                    subjects.setFrom_date(Sub_details.getString("from_date"));
                    subjects.setTo_date(Sub_details.getString("to_date"));
                    subjects.setPercentage(Sub_details.getString("percentage"));
                    subjects.setByvalue(Sub_details.getString("byvalue"));
                    dates_arr.add(subjects);
                    JSONArray mid_arrr = Sub_details.getJSONArray("subjects");
                    ArrayList<attend_d_s_pojo> l_mid_arr = new ArrayList<>();
                    for (int j = 0; j < mid_arrr.length(); j++) {
                        JSONObject jobj = mid_arrr.getJSONObject(j);
                        attend_d_s_pojo mid_pojo = new attend_d_s_pojo();
                        mid_pojo.setSubject_name(jobj.getString("subject_name"));
                        mid_pojo.setWorking(jobj.getString("working"));
                        mid_pojo.setPresent(jobj.getString("present"));
                        Double dff = Double.parseDouble(jobj.getString("avg"));
                        mid_pojo.setAvg(df.format(dff));
                        l_mid_arr.add(mid_pojo);
                    }
                    dates__sub_arr.add(l_mid_arr);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            set_ui();
        }

        public void set_ui() {
            for (int s = 0; s < dates_arr.size(); s++) {
                stu_attend_date_pojo met_map = dates_arr.get(s);
                final View sub_point_l = lf.inflate(R.layout.stu_date_expand_card, null);
                final ImageView arrow = (ImageView) sub_point_l.findViewById(R.id.arrow);
                final LinearLayout mLinearLayout = (LinearLayout) sub_point_l.findViewById(R.id.expandable);
                final LinearLayout mLinearLayoutHeader = (LinearLayout) sub_point_l.findViewById(R.id.header);
                LinearLayout table_header = (LinearLayout) sub_point_l.findViewById(R.id.table_header);
                RelativeLayout point_bg = (RelativeLayout) sub_point_l.findViewById(R.id.point_bg);

                final View b_line = sub_point_l.findViewById(R.id.b_line);
                String col = color_code[s];
                String col2 = color_code2[s];
                point_bg.setBackgroundColor(Color.parseColor(col));
                table_header.setBackgroundColor(Color.parseColor(col2));
                b_line.setBackgroundColor(Color.parseColor(col));
                final p_MyCustomTextView_regular met_name = (p_MyCustomTextView_regular) sub_point_l.findViewById(R.id.met_name);
                p_MyCustomTextView_mbold points = (p_MyCustomTextView_mbold) sub_point_l.findViewById(R.id.points);
                p_MyCustomTextView_regular byvalue = (p_MyCustomTextView_regular) sub_point_l.findViewById(R.id.byvalue);
                //Add onPreDrawListener
                mLinearLayout.getViewTreeObserver().addOnPreDrawListener(
                        new ViewTreeObserver.OnPreDrawListener() {
                            @Override
                            public boolean onPreDraw() {
                                mLinearLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                                mLinearLayout.setVisibility(View.GONE);

                                final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                                final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                                mLinearLayout.measure(widthSpec, heightSpec);

                                mAnimator = slideAnimator(0, mLinearLayout.getMeasuredHeight(), mLinearLayout);
                                return true;
                            }
                        });


                mLinearLayoutHeader.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (mLinearLayout.getVisibility() == View.GONE) {
                            expand(mLinearLayout, arrow, met_name, b_line);
                        } else {
                            collapse(mLinearLayout, arrow, met_name, b_line);
                        }
                    }
                });

                points.setText(met_map.getPercentage() + "%");
                met_name.setText(met_map.getFrom_date() + " To " + met_map.getTo_date());
                byvalue.setText(met_map.getByvalue());
                ArrayList<attend_d_s_pojo> l_fina = dates__sub_arr.get(s);
                for (int j = 0; j < l_fina.size(); j++) {
                    attend_d_s_pojo finalh_map = l_fina.get(j);
                    View sub_row = lf.inflate(R.layout.sub_point_row, null);
                    p_MyCustomTextView_regular stu_name = (p_MyCustomTextView_regular) sub_row.findViewById(R.id.stu_name);
                    p_MyCustomTextView_regular count = (p_MyCustomTextView_regular) sub_row.findViewById(R.id.count);
                    p_MyCustomTextView_regular marks = (p_MyCustomTextView_regular) sub_row.findViewById(R.id.marks);
                    p_MyCustomTextView_regular total = (p_MyCustomTextView_regular) sub_row.findViewById(R.id.total);
                    stu_name.setText(finalh_map.getSubject_name());
                    count.setText(finalh_map.getWorking());
                    marks.setText(finalh_map.getPresent());
                    //Double l_p=Double.parseDouble(finalh_map.getAvg());
                    total.setText(finalh_map.getAvg());
                    mLinearLayout.addView(sub_row);
                }
                main_layout.addView(sub_point_l);
            }
        }
    }
}
