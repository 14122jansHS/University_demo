package com.egnify.University_demo.Fragments.teaching;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egnify.University_demo.Adapter.o_a_adapter;
import com.egnify.University_demo.R;
import com.egnify.University_demo.pojos.attend_info;
import com.egnify.University_demo.pojos.stu_info;
import com.egnify.University_demo.utils.p_MyCustomTextView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class overall_a_t extends Fragment {

    private static String ARG_SECTION_NUMBER="section";
    ArrayList<ArrayList<ArrayList<stu_info>>> stu_arr;
    ArrayList<attend_info> mrks_arr;
    DecimalFormat df = new DecimalFormat("##.#");
    private int section;
    private Picasso picasso;
    private ArrayList<stu_info> mid1_stu;
    private ArrayList<stu_info> mid3_stu;
    private ArrayList<stu_info> mid2_stu;
    private RecyclerView recyler_stu;
    private o_a_adapter adapter;
    private p_MyCustomTextView m1,m2,m3,avg;
    public overall_a_t() {
        // Required empty public constructor
    }

    public Fragment newInstance(int i, ArrayList<ArrayList<ArrayList<stu_info>>> stu_arr, ArrayList<attend_info> mrks_arr) {
        overall_a_t fragment = new overall_a_t();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, i);
        args.putSerializable("mrks_arr", mrks_arr);
        args.putSerializable("stu_arr", stu_arr);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_overall_m_t, container, false);
        recyler_stu = (RecyclerView) rootview.findViewById(R.id.recyler_stu);
        m1=(p_MyCustomTextView) rootview.findViewById(R.id.m1);
        m3=(p_MyCustomTextView) rootview.findViewById(R.id.m3);
        m2=(p_MyCustomTextView) rootview.findViewById(R.id.m2);
        avg=(p_MyCustomTextView) rootview.findViewById(R.id.avg);
        recyler_stu.setLayoutManager(new LinearLayoutManager(getActivity()));
        section = getArguments().getInt(ARG_SECTION_NUMBER);
        picasso = Picasso.with(getActivity());
        picasso.setLoggingEnabled(true);
        picasso.setIndicatorsEnabled(false);
        mrks_arr = (ArrayList) getArguments().getSerializable("mrks_arr");
        stu_arr = (ArrayList) getArguments().getSerializable("stu_arr");
        DecimalFormat DF=new DecimalFormat("##");
        double cls_svg=0;
        for(int j=0;j<mrks_arr.size();j++)
        {
            attend_info atten_pojo=mrks_arr.get(j);

            if(j==0)
            {
                double mm1=Double.parseDouble(atten_pojo.getClass_avg());
                m1.setText(DF.format(mm1));
                cls_svg+=Double.parseDouble(atten_pojo.getClass_avg());
            }
            else if(j==1)
            {
                double mm2=Double.parseDouble(atten_pojo.getClass_avg());
                m2.setText(DF.format(mm2));
                cls_svg+=Double.parseDouble(atten_pojo.getClass_avg());
            }
            else if(j==2)
            {
                double mm3=Double.parseDouble(atten_pojo.getClass_avg());
                m3.setText(DF.format(mm3));
                cls_svg+=Double.parseDouble(atten_pojo.getClass_avg());
                Double per= Double.valueOf((cls_svg*100)/300);

                avg.setText(DF.format(per) + ".0%");
            }

        }
        ArrayList<ArrayList<stu_info>> l_stu_arr = stu_arr.get(0);
        mid1_stu = l_stu_arr.get(0);
        mid2_stu = l_stu_arr.get(1);
        mid3_stu = l_stu_arr.get(2);
        adapter = new o_a_adapter(getActivity(), mid1_stu, mid2_stu, mid3_stu);
        recyler_stu.setAdapter(adapter);

        return rootview;

    }


}
