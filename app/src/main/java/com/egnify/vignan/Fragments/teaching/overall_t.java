package com.egnify.vignan.Fragments.teaching;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.egnify.vignan.R;
import com.egnify.vignan.pojos.attend_info;
import com.egnify.vignan.pojos.attri_details;
import com.egnify.vignan.pojos.attribute_obj;
import com.egnify.vignan.pojos.stu_info;
import com.egnify.vignan.utils.p_MyCustomTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class overall_t extends Fragment {
    private final static String TAG = "DashBoardActivity";
    private static String ARG_SECTION_NUMBER = "section number";
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private FrameLayout mContainerId;
    private p_MyCustomTextView ob_tv;
    private LinearLayout ov_click;
    private LinearLayout att_click;
    private p_MyCustomTextView att_tv;
    private LinearLayout mar_click;
    private p_MyCustomTextView mar_tv;
    private attribute_obj allo_obj;
    ArrayList<attri_details> attri_detail_arr = new ArrayList<>();
    ArrayList<HashMap<String, String>> metrics_overall = new ArrayList<>();
    ArrayList<ArrayList<HashMap<String, String>>> metrics_all_exams = new ArrayList<>();
    ArrayList<ArrayList<ArrayList<stu_info>>> stu_arr = new ArrayList<>();
    ArrayList<ArrayList<ArrayList<stu_info>>> mstu_arr = new ArrayList<>();
    ArrayList<attend_info> atten_arr = new ArrayList<>();
    ArrayList<attend_info> mrks_arr = new ArrayList<>();

    public overall_t() {
        // Required empty public constructor
    }

    public static Fragment newInstance(int i, ArrayList<ArrayList<HashMap<String, String>>> metrics_all_exams, ArrayList<attend_info> atten_arr, ArrayList<ArrayList<ArrayList<stu_info>>> stu_arr, ArrayList<ArrayList<ArrayList<stu_info>>> mstu_arr, ArrayList<attend_info> mrks_arr) {
        overall_t fragment = new overall_t();
        Bundle args = new Bundle();
        args.putSerializable("metrics_all_exams", metrics_all_exams);
        args.putSerializable("atten_arr", atten_arr);
        args.putSerializable("stu_arr", stu_arr);
        args.putSerializable("mstu_arr", mstu_arr);
        args.putSerializable("mrks_arr", mrks_arr);
        args.putInt(ARG_SECTION_NUMBER, i);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_overall_t, container, false);
        mContainerId = (FrameLayout) rootview.findViewById(R.id.containder);
        metrics_all_exams = (ArrayList) getArguments().getSerializable("metrics_all_exams");
        atten_arr = (ArrayList) getArguments().getSerializable("atten_arr");
        stu_arr = (ArrayList) getArguments().getSerializable("stu_arr");
        mstu_arr = (ArrayList) getArguments().getSerializable("mstu_arr");
        mrks_arr = (ArrayList) getArguments().getSerializable("mrks_arr");
        replaceFragment(mContainerId, new overall_o_t().newInstance(1, metrics_all_exams), TAG);
        //new backgroud_parse().execute();
        ov_click = (LinearLayout) rootview.findViewById(R.id.ov_click);
        ob_tv = (p_MyCustomTextView) rootview.findViewById(R.id.ob_tv);

        att_click = (LinearLayout) rootview.findViewById(R.id.att_click);
        att_tv = (p_MyCustomTextView) rootview.findViewById(R.id.att_tv);

        mar_click = (LinearLayout) rootview.findViewById(R.id.mar_click);
        mar_tv = (p_MyCustomTextView) rootview.findViewById(R.id.mar_tv);
        // clicking overview
        ov_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fragment fragment=new overall_o_t().newInstance(2);
                replaceFragment(mContainerId, new overall_o_t().newInstance(1, metrics_all_exams), TAG);
                ov_click.setBackgroundColor(getActivity().getResources().getColor(R.color.red));
                ob_tv.setTextColor(getActivity().getResources().getColor(R.color.white));
                att_click.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                att_tv.setTextColor(getActivity().getResources().getColor(R.color.primary_text));
                mar_click.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                mar_tv.setTextColor(getActivity().getResources().getColor(R.color.primary_text));
            }
        });
        // clicking overview
        att_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fragment fragment=new overall_o_t().newInstance(1);
                replaceFragment(mContainerId, new overall_m_t().newInstance(2, atten_arr, stu_arr), TAG);
                att_click.setBackgroundColor(getActivity().getResources().getColor(R.color.red));
                att_tv.setTextColor(getActivity().getResources().getColor(R.color.white));
                ov_click.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                ob_tv.setTextColor(getActivity().getResources().getColor(R.color.primary_text));
                mar_click.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                mar_tv.setTextColor(getActivity().getResources().getColor(R.color.primary_text));
            }
        });
        // clicking overview
        mar_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new overall_a_t().newInstance(3, mstu_arr, mrks_arr);
                replaceFragment(mContainerId, fragment, TAG);

                mar_click.setBackgroundColor(getActivity().getResources().getColor(R.color.red));
                mar_tv.setTextColor(getActivity().getResources().getColor(R.color.white));
                ov_click.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                ob_tv.setTextColor(getActivity().getResources().getColor(R.color.primary_text));
                att_click.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                att_tv.setTextColor(getActivity().getResources().getColor(R.color.primary_text));
            }
        });
        return rootview;

    }

    public void replaceFragment(FrameLayout lContainerId, Fragment fragment, String TAG) {

        try {
            Log.d("entered", "entred");
            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.containder, fragment, TAG);
            fragmentTransaction.addToBackStack(TAG);
            fragmentTransaction.commitAllowingStateLoss();

        } catch (Exception e) {
            // TODO: handle exception
        }

    }


}
