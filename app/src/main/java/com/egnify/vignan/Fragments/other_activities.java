package com.egnify.vignan.Fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.egnify.vignan.R;
import com.egnify.vignan.pojos.attribute_obj;
import com.egnify.vignan.pojos.resea;
import com.egnify.vignan.utils.p_MyCustomTextView_bold;
import com.egnify.vignan.utils.p_MyCustomTextView_regular;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class other_activities extends Fragment {

    private ArrayList<attribute_obj> filelist;
    ArrayList<resea> l_sub_topics = new ArrayList<>();
    List<String> list = new ArrayList<>();
    ArrayList<HashMap<String, String>> final_arr = new ArrayList<>();
    ArrayList<String> final_ids = new ArrayList<>();
    private LayoutInflater lf;
    private LinearLayout main_layout;

    private static String ARG_SECTION_NUMBER= "section_number";
    private attribute_obj allo_obj;
    View rootview;
    public other_activities() {
        // Required empty public constructor
    }
    public static other_activities newInstance(int sectionNumber, attribute_obj l_obj) {
        other_activities fragment = new other_activities();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putSerializable("other_activities", l_obj);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootview=inflater.inflate(R.layout.fragment_other_activities, container, false);
        attribute_obj l_obj = (attribute_obj) getArguments().getSerializable("other_activities");
        p_MyCustomTextView_bold over_p=(p_MyCustomTextView_bold) rootview.findViewById(R.id.over_p);
        lf=(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        main_layout=(LinearLayout) rootview.findViewById(R.id.main_layout);
        try {
            String over_all=l_obj.getFinal_points();
            String get_text=over_p.getText().toString();
            over_p.setText(get_text+" "+over_all);
            JSONArray arr_sub_topics = new JSONArray(l_obj.getMetrics());
            for (int j = 0; j < arr_sub_topics.length(); j++) {
                JSONObject object_sub_topics = arr_sub_topics.getJSONObject(j);
                resea rse = new resea();
                rse.setSub_topic_id(object_sub_topics.getString("sub_topic_id"));
                rse.setSub_topic_name(object_sub_topics.getString("sub_topic_name"));
                rse.setScore(object_sub_topics.getString("score"));
                list.add(object_sub_topics.getString("sub_topic_id"));
                l_sub_topics.add(rse);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        for (int l = 0; l < list.size(); l++) {
            resea l_sub_pojo = l_sub_topics.get(l);
            HashMap<String, String> f_s_topics = new HashMap<>();
            String id = list.get(l);
            if (!final_ids.contains(id)) {
                final_ids.add(id);
                int count = Collections.frequency(list, id);
                f_s_topics.put("count", String.valueOf(count));
                f_s_topics.put("Sub_topic_name", l_sub_pojo.getSub_topic_name());
                f_s_topics.put("Sub_topic_id", l_sub_pojo.getSub_topic_id());
                f_s_topics.put("Score", l_sub_pojo.getScore());
                final_arr.add(f_s_topics);
            }
        }
        new set_ui().execute();
        return rootview;
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
    }

    private void set_ui() {


        View sub_point_l=lf.inflate(R.layout.sub_point,null);
        p_MyCustomTextView_regular met_name=(p_MyCustomTextView_regular)sub_point_l.findViewById(R.id.met_name);
        met_name.setText("OTHER PROFESSIONAL ACTIVITES");
        LinearLayout a_row_holder=(LinearLayout)sub_point_l.findViewById(R.id.a_row_holder);

        for (int j=0;j<final_arr.size();j++)
        {
            HashMap<String,String> finalh_map =final_arr.get(j);
            View sub_row=lf.inflate(R.layout.sub_point_row, null);
            p_MyCustomTextView_regular stu_name=(p_MyCustomTextView_regular) sub_row.findViewById(R.id.stu_name);
            p_MyCustomTextView_regular count=(p_MyCustomTextView_regular) sub_row.findViewById(R.id.count);
            p_MyCustomTextView_regular marks=(p_MyCustomTextView_regular) sub_row.findViewById(R.id.marks);
            p_MyCustomTextView_regular total_tv=(p_MyCustomTextView_regular) sub_row.findViewById(R.id.total);

            stu_name.setText(finalh_map.get("Sub_topic_name"));
            int score=Integer.parseInt(finalh_map.get("Score"));
            if(score==0)
            {
                count.setText("0");
                marks.setText("0");
                total_tv.setText("0");
            }else
            {
                count.setText(finalh_map.get("count"));
                marks.setText(finalh_map.get("Score"));
                int total=Integer.parseInt(finalh_map.get("count"))*Integer.parseInt(finalh_map.get("Score"));
                total_tv.setText(String.valueOf(total));
            }


            a_row_holder.addView(sub_row);
        }
        main_layout.addView(sub_point_l);

    }
}
