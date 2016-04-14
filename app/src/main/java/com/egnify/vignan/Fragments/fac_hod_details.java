package com.egnify.vignan.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.egnify.vignan.R;
import com.egnify.vignan.model.app_url;
import com.egnify.vignan.utils.p_MyCustomTextView;
import com.egnify.vignan.utils.p_MyCustomTextView_mbold;
import com.egnify.vignan.utils.p_MyCustomTextView_regular;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class fac_hod_details extends Fragment {

    private static final String ARG_SECTION_NUMBER = "click_id";
    private ArrayList<HashMap<String, String>> filelist;
    private LayoutInflater lf;
    private Picasso picasso;

    public fac_hod_details() {
        // Required empty public constructor
    }

    public static fac_hod_details newInstance(int number, ArrayList<HashMap<String, String>> filelist, int sectionNumber) {
        fac_hod_details fragment = new fac_hod_details();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putSerializable("fac_sumaary", filelist);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_fac_hod_details, container, false);
        filelist = (ArrayList<HashMap<String, String>>) getArguments().getSerializable("fac_sumaary");
        picasso = Picasso.with(getActivity());
        picasso.setLoggingEnabled(true);
        picasso.setIndicatorsEnabled(false);
        lf = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout stu_row_holder = (LinearLayout) rootview.findViewById(R.id.stu_row_holder);
        for (int f = 0; f < filelist.size(); f++) {
            HashMap<String, String> fac_pojo = filelist.get(f);
            View stu_row = lf.inflate(R.layout.fac_row, null);
            p_MyCustomTextView_regular st_name = (p_MyCustomTextView_regular) stu_row.findViewById(R.id.stu_name);
            p_MyCustomTextView st_id = (p_MyCustomTextView) stu_row.findViewById(R.id.stu_id);
            CircularImageView faac_pic=(CircularImageView) stu_row.findViewById(R.id.fac_pic);
            String url= app_url.URL_IMAGES+"fac_"+(f+1)+".jpeg";
            picasso.load(url)
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.place_holder)
                    .into(faac_pic);
            LinearLayout marks_holder = (LinearLayout) stu_row.findViewById(R.id.marks_holder);
            marks_holder.setWeightSum(4f);

            for (int o = 0; o <= 3; o++) {
                View stu_tv = lf.inflate(R.layout.ll_text_view, null);
                p_MyCustomTextView_mbold tv_v = (p_MyCustomTextView_mbold) stu_tv.findViewById(R.id.textview_ll);
                TableRow.LayoutParams params2 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
                tv_v.setLayoutParams(params2);
                tv_v.setTextColor(getResources().getColor(R.color.primary_text));
                if (o == 0)
                    tv_v.setText(fac_pojo.get("allotedclasses"));
                else if (o == 1) {
                    tv_v.setText(fac_pojo.get("counsellingstudents"));
                } else if (o == 2) {
                    int fi_val = Integer.parseInt(fac_pojo.get("ResearchatFacultyLevel")) +
                            Integer.parseInt(fac_pojo.get("ResearchatHODlevel")) +
                            Integer.parseInt(fac_pojo.get("OtherProfessionalActivites"));
                    tv_v.setText(String.valueOf(fi_val));

                } else if (o == 3) {
                    int totl = Integer.parseInt(fac_pojo.get("allotedclasses")) +
                            Integer.parseInt(fac_pojo.get("counsellingstudents")) +
                            Integer.parseInt(fac_pojo.get("ResearchatFacultyLevel")) +
                            Integer.parseInt(fac_pojo.get("ResearchatHODlevel")) +
                            Integer.parseInt(fac_pojo.get("OtherProfessionalActivites"));
                    tv_v.setText(String.valueOf(totl));

                }
                marks_holder.addView(stu_tv);
            }

            st_name.setText(fac_pojo.get("fac_name"));
            st_id.setText("Asst.Prof");
            stu_row_holder.addView(stu_row);
        }
        return rootview;
    }

}
