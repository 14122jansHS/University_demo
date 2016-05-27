package com.egnify.University_demo.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.egnify.University_demo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class stu_hid_prodetails extends Fragment {


    private static final String ARG_SECTION_NUMBER = "section_number";

    public stu_hid_prodetails() {
        // Required empty public constructor
    }

    public static stu_hid_prodetails newInstance(int sectionNumber) {
        stu_hid_prodetails fragment = new stu_hid_prodetails();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_stu_hid_prodetails, container, false);
        ImageView acc_icon = (ImageView) rootview.findViewById(R.id.acc_icon);
        ImageView trof_icon = (ImageView) rootview.findViewById(R.id.trof_icon);
        ImageView flag_icon = (ImageView) rootview.findViewById(R.id.flag_icon);
        int color = Color.parseColor("#448AFF"); //The color u want
        flag_icon.setColorFilter(color);
        trof_icon.setColorFilter(color);
        acc_icon.setColorFilter(color);
        return rootview;
    }

}
