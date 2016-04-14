package com.egnify.vignan.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.egnify.vignan.Activities.new_home_activity;
import com.egnify.vignan.R;
import com.egnify.vignan.model.app_url;
import com.egnify.vignan.utils.p_MyCustomTextView_mbold;
import com.egnify.vignan.utils.p_MyCustomTextView_regular;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by janardhanyerranagu on 3/29/16.
 */
public class vc_dept_summary extends Fragment {

    String[] color_code = {"#FF9E01", "#0269B0", "#39B53C", "#E52116", "#5b128c","#FF9E01", "#0269B0", "#39B53C", "#E52116", "#5b128c","#FF9E01", "#0269B0", "#39B53C", "#E52116", "#5b128c"};
    private static final String ARG_SECTION_NUMBER = "click_id";
    private ArrayList<HashMap<String, String>> filelist;
    private LayoutInflater lf;
    private int count;
    private LinearLayout main_layout;
    private Picasso picasso;
    public vc_dept_summary() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static vc_dept_summary newInstance(int number, ArrayList<HashMap<String, String>> filelist) {
        vc_dept_summary fragment = new vc_dept_summary();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, number);
        args.putSerializable("fac_sumaary", filelist);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fac_sumary_hod, container, false);
        filelist = (ArrayList<HashMap<String, String>>) getArguments().getSerializable("fac_sumaary");
        picasso = Picasso.with(getActivity());
        picasso.setLoggingEnabled(true);
        picasso.setIndicatorsEnabled(false);
        count = getArguments().getInt(ARG_SECTION_NUMBER);
        lf = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        main_layout=(LinearLayout) rootView.findViewById(R.id.main_layout);
        set_ui();
        return rootView;
    }
    public void set_ui() {

        for (int i = 0; i < filelist.size(); i++) {
            HashMap<String, String> fac_map = filelist.get(i);
            View view = lf.inflate(R.layout.overview_card_fac, null);
            LinearLayout main_lay = (LinearLayout) view.findViewById(R.id.main_ll);
            CircularImageView fac_pic=(CircularImageView) view.findViewById(R.id.fac_pic);
            p_MyCustomTextView_mbold marks = (p_MyCustomTextView_mbold) view.findViewById(R.id.marks);
            p_MyCustomTextView_regular fac_name = (p_MyCustomTextView_regular) view.findViewById(R.id.fac_name);
            fac_name.setText(fac_map.get("fac_name"));
            marks.setText(fac_map.get("fac_score"));

           /* if (count == 1) {
                marks.setText(fac_map.get("allotedclasses"));
            } else if (count == 2) {
                marks.setText(fac_map.get("counsellingstudents"));
            } else if (count == 3) {
                int total=Integer.parseInt(fac_map.get("ResearchatFacultyLevel"))+Integer.parseInt(fac_map.get("ResearchatHODlevel"));
                marks.setText(String.valueOf(total));
            } else {
                marks.setText(fac_map.get("OtherProfessionalActivites"));
            }*/
            final int finalI = i;
            main_lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), new_home_activity.class);
                    i.putExtra("type", "2");
                    i.putExtra("click", "1");
                    i.putExtra("id", finalI+1);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                }
            });
            String url= app_url.URL_IMAGES+"fac_"+(i+1)+".jpeg";
            picasso.load(url)
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.place_holder)
                    .into(fac_pic);
            RelativeLayout bg_col = (RelativeLayout) view.findViewById(R.id.bg_col);
            View line_col = view.findViewById(R.id.b_line);
            String col = color_code[i];
            line_col.setBackgroundColor(Color.parseColor(col));
            bg_col.setBackgroundColor(Color.parseColor(col));

            main_layout.addView(view);
        }
    }
}
