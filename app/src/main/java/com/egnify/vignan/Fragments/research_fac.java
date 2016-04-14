package com.egnify.vignan.Fragments;

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
import android.widget.TextView;

import com.egnify.vignan.R;
import com.egnify.vignan.pojos.attribute_obj;
import com.egnify.vignan.pojos.resea;
import com.egnify.vignan.utils.p_MyCustomTextView_bold;
import com.egnify.vignan.utils.p_MyCustomTextView_mbold;
import com.egnify.vignan.utils.p_MyCustomTextView_regular;
import com.rey.material.widget.ProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by janardhanyerranagu on 3/27/16.
 */
public class research_fac extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    ArrayList<HashMap<String, String>> heading_arr = new ArrayList<>();
    private static final String ARG_SECTION_NUMBER = "section_number";
    private attribute_obj allo_obj;
    ArrayList<List<String>> main_list = new ArrayList<>();
    ArrayList<ArrayList<resea>> sub_topics = new ArrayList<>();
    ArrayList<ArrayList<HashMap<String, String>>> final_arr;
    private LayoutInflater lf;
    private LinearLayout main_layout;
    private ValueAnimator mAnimator;
    String[] color_code = {"#FF9E01", "#0269B0", "#39B53C", "#E52116", "#5b128c","#FF9E01", "#0269B0", "#39B53C", "#E52116", "#5b128c","#FF9E01", "#0269B0", "#39B53C", "#E52116", "#5b128c","#FF9E01", "#0269B0", "#39B53C", "#E52116", "#5b128c"};
    String[] color_code2 = {"#FFC05C", "#0286DE", "#83D884", "#EE5F58", "#8D1CD9","#FFC05C", "#0286DE", "#83D884", "#EE5F58", "#8D1CD9","#FFC05C", "#0286DE", "#83D884", "#EE5F58", "#8D1CD9","#FFC05C", "#0286DE", "#83D884", "#EE5F58", "#8D1CD9"};
    ProgressView progress_circle;
    public research_fac() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static research_fac newInstance(int sectionNumber, attribute_obj l_obj) {
        research_fac fragment = new research_fac();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putSerializable("reserach_fac", l_obj);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_research_fac, container, false);
        allo_obj = (attribute_obj) getArguments().getSerializable("reserach_fac");
        lf=(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        main_layout=(LinearLayout) rootView.findViewById(R.id.main_layout);
       // main_layout.setVisibility(View.GONE);
       // progress_circle=(ProgressView) rootView.findViewById(R.id.progress_circle);
      //  progress_circle.start();
        p_MyCustomTextView_bold over_p=(p_MyCustomTextView_bold) rootView.findViewById(R.id.over_p);
        JSONArray jArr = null;
       if( main_list.size()==0)
       {
        try {
            String over_all=allo_obj.getFinal_points();
            String get_text=over_p.getText().toString();
            over_p.setText(get_text+" "+over_all);
            jArr = new JSONArray(allo_obj.getMetrics());
            for (int i = 0; i < jArr.length(); i++)
            {
                ArrayList<resea> l_sub_topics = new ArrayList<>();
                List<String> list = new ArrayList<>();
                HashMap<String, String> head_map = new HashMap<>();
                JSONObject jobj = jArr.getJSONObject(i);
                head_map.put("name", jobj.getString("name"));
                head_map.put("points", jobj.getString("points"));
                JSONArray arr_sub_topics = jobj.getJSONArray("sub_topics");
                for (int j = 0; j < arr_sub_topics.length(); j++)
                {
                    JSONObject object_sub_topics = arr_sub_topics.getJSONObject(j);
                    resea rse = new resea();
                    rse.setSub_topic_id(object_sub_topics.getString("sub_topic_id"));
                    rse.setSub_topic_name(object_sub_topics.getString("sub_topic_name"));
                    rse.setScore(object_sub_topics.getString("score"));
                    list.add(object_sub_topics.getString("sub_topic_id"));
                    l_sub_topics.add(rse);
                }
                main_list.add(list);
                sub_topics.add(l_sub_topics);
                heading_arr.add(head_map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }}
         final_arr = new ArrayList<>();
        ArrayList<ArrayList<String>> final_ids = new ArrayList<>();
        for (int i = 0; i < main_list.size(); i++) {
            List<String> list = main_list.get(i);
            ArrayList<HashMap<String, String>> l_fina_arr = new ArrayList<>();
            ArrayList<String> l_final_ids = new ArrayList<>();
            ArrayList<resea> l_getsub_topic = sub_topics.get(i);
            for (int l = 0; l < list.size(); l++)
            {
                resea l_sub_pojo = l_getsub_topic.get(l);
                HashMap<String, String> f_s_topics = new HashMap<>();
                String id = list.get(l);
                if (!l_final_ids.contains(id)) {
                    l_final_ids.add(id);
                    int count = Collections.frequency(list, id);
                    f_s_topics.put("count", String.valueOf(count));
                    f_s_topics.put("Sub_topic_name", l_sub_pojo.getSub_topic_name());
                    f_s_topics.put("Sub_topic_id", l_sub_pojo.getSub_topic_id());
                    f_s_topics.put("Score", l_sub_pojo.getScore());
                    l_fina_arr.add(f_s_topics);
                }
            }
            final_arr.add(l_fina_arr);
            final_ids.add(l_final_ids);
        }
        String total_srg = null;
        for (int i=0;i<final_arr.size();i++) {
            String l_tot_sng=null;
            ArrayList<HashMap<String, String>> l_getsub_topic = final_arr.get(i);
            for (int j=0;j<l_getsub_topic.size();j++) {
                HashMap<String, String> f_s_topics = l_getsub_topic.get(j);
                String Sub_topic_id = f_s_topics.get("Sub_topic_id");
                String count = f_s_topics.get("count");
                l_tot_sng+="id"+Sub_topic_id+"  "+"count"+count+"\n";
            }
            total_srg+="main"+l_tot_sng+"\n";
        }
        //textView.setText(total_srg);
      new set_ui().execute();
        return rootView;
    }
public class set_ui extends AsyncTask<Void,Void,Void>
{

    @Override
    protected Void doInBackground(Void... params) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                set_ui();
            }
        });

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //main_layout.setVisibility(View.VISIBLE);
        //progress_circle.stop();
    }
}

    private void set_ui() {
        for(int s=0;s<heading_arr.size();s++)
        {
            HashMap<String,String> met_map=heading_arr.get(s);
            final View sub_point_l=lf.inflate(R.layout.expand_card, null);
            final ImageView arrow=(ImageView) sub_point_l.findViewById(R.id.arrow);
            final LinearLayout  mLinearLayout = (LinearLayout) sub_point_l.findViewById(R.id.expandable);
            final LinearLayout  mLinearLayoutHeader = (LinearLayout) sub_point_l.findViewById(R.id.header);
            LinearLayout table_header=(LinearLayout) sub_point_l.findViewById(R.id.table_header);
            RelativeLayout point_bg=(RelativeLayout) sub_point_l.findViewById(R.id.point_bg);
            final View b_line=sub_point_l.findViewById(R.id.b_line);
            String col = color_code[s];
            String col2 = color_code2[s];
            point_bg.setBackgroundColor(Color.parseColor(col));
            table_header.setBackgroundColor(Color.parseColor(col2));
            b_line.setBackgroundColor(Color.parseColor(col));
            final p_MyCustomTextView_regular met_name=(p_MyCustomTextView_regular)sub_point_l.findViewById(R.id.met_name);
            p_MyCustomTextView_mbold points=(p_MyCustomTextView_mbold) sub_point_l.findViewById(R.id.points);
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

                            mAnimator = slideAnimator(0, mLinearLayout.getMeasuredHeight(),mLinearLayout);
                            return true;
                        }
                    });


            mLinearLayoutHeader.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mLinearLayout.getVisibility()==View.GONE){
                        expand(mLinearLayout,arrow,met_name,b_line);
                    }else{
                        collapse(mLinearLayout,arrow,met_name,b_line);
                    }
                }
            });

            points.setText(met_map.get("points"));
            met_name.setText(met_map.get("name"));
           // met_name.setText(s + 1 + ". " + met_map.get("name"));
           // LinearLayout a_row_holder=(LinearLayout)sub_point_l.findViewById(R.id.expandable);
            ArrayList<HashMap<String, String>> l_fina=final_arr.get(s);
            for (int j=0;j<l_fina.size();j++)
            {
                HashMap<String,String> finalh_map =l_fina.get(j);
                View sub_row=lf.inflate(R.layout.sub_point_row, null);
                p_MyCustomTextView_regular stu_name=(p_MyCustomTextView_regular) sub_row.findViewById(R.id.stu_name);
                p_MyCustomTextView_regular count=(p_MyCustomTextView_regular) sub_row.findViewById(R.id.count);
                p_MyCustomTextView_regular marks=(p_MyCustomTextView_regular) sub_row.findViewById(R.id.marks);
                p_MyCustomTextView_regular total=(p_MyCustomTextView_regular) sub_row.findViewById(R.id.total);
                stu_name.setText(finalh_map.get("Sub_topic_name"));
                int sc=  Integer.parseInt(finalh_map.get("Score"));
                if(sc==0)
                {
                    count.setText("0");
                    marks.setText(finalh_map.get("Score"));
                    total.setText("0");
                }
                else
                {
                    count.setText(finalh_map.get("count"));
                    int l_c=Integer.parseInt(finalh_map.get("count"));
                    int l_s=Integer.parseInt(finalh_map.get("Score"));
                    marks.setText(finalh_map.get("Score"));
                    total.setText(String.valueOf((l_c*l_s)));
                }
                mLinearLayout.addView(sub_row);
            }
            main_layout.addView(sub_point_l);
        }
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

		mAnimator = slideAnimator(0, mLinearLayout.getMeasuredHeight(),mLinearLayout);
        b_line.setVisibility(View.GONE);

        mAnimator.start();
    }

    private void collapse(final LinearLayout mLinearLayout2, ImageView arrow, p_MyCustomTextView_regular met_name, final View b_line) {
        int finalHeight = mLinearLayout2.getHeight();
        arrow.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow));
        ValueAnimator mAnimator = slideAnimator(finalHeight, 0,mLinearLayout2);
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
}
