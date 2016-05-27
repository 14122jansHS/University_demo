package com.egnify.University_demo.Fragments;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.egnify.University_demo.R;
import com.egnify.University_demo.pojos.attri_details;
import com.egnify.University_demo.pojos.attribute_obj;
import com.egnify.University_demo.utils.p_MyCustomTextView_mbold;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.rey.material.widget.ProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class alloted_cls extends Fragment {


    private static final String ARG_SECTION_NUMBER = "section_number";
    private static attribute_obj allo_obj;
    //ArrayList<HashMap<String, String>> allo_details = new ArrayList<>();
    ArrayList<attri_details> attri_detail_arr = new ArrayList<>();
    private String metric_name;
    private String alloted_total;
    private String taken_total;
    private String total;
    private LinearLayout ll_main;
    private LayoutInflater lf;
    private ScrollView main_layout;
    private BarChart chart;
    private p_MyCustomTextView_mbold tot_points;
    private ProgressView mProgressView;

    public alloted_cls() {
        // Required empty public constructor
    }

    public static alloted_cls newInstance(int sectionNumber, attribute_obj l_obj) {
        alloted_cls fragment = new alloted_cls();
        Bundle args = new Bundle();
        args.putSerializable("allo_obj", l_obj);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_alloted_cls, container, false);
        main_layout=(ScrollView) rootView.findViewById(R.id.main_layout);
        mProgressView = (ProgressView) rootView.findViewById(R.id.login_progress);
         chart = (BarChart) rootView.findViewById(R.id.chart);
         ll_main=(LinearLayout) rootView.findViewById(R.id.row_holder);
         lf=(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        allo_obj = (attribute_obj) getArguments().getSerializable("allo_obj");
         tot_points=(p_MyCustomTextView_mbold) rootView.findViewById(R.id.tot_points);
        attri_detail_arr.clear();
        try {
            JSONArray jArr = new JSONArray(allo_obj.getMetrics());
            JSONObject jobj = jArr.getJSONObject(0);

            metric_name = jobj.getString("metric_name");
            alloted_total = jobj.getString("alloted_total");
            taken_total = jobj.getString("taken_total");
            total = jobj.getString("total");
            JSONArray arr_all_det = jobj.getJSONArray("alloted_details");
            for (int j = 0; j < arr_all_det.length(); j++) {
                JSONObject l_all_obj = arr_all_det.getJSONObject(j);
                attri_details at_detials = new attri_details();
                at_detials.setAlloted_mid1(l_all_obj.getString("alloted_mid1"));
                at_detials.setExam(l_all_obj.getString("exam"));
                at_detials.setMarks(l_all_obj.getString("marks"));
                at_detials.setTaken_mid1(l_all_obj.getString("taken_mid1"));
                attri_detail_arr.add(at_detials);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new set_ui().execute();
            }
        }, 1000);


        return rootView;
    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;
//        dataSets.clear();
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        for (int i = 0; i < attri_detail_arr.size(); i++) {
            attri_details loc_arr = attri_detail_arr.get(i);
            int alot = Integer.parseInt(loc_arr.getAlloted_mid1());
            BarEntry v1e1 = new BarEntry(alot, i); // Jan
            valueSet1.add(v1e1);
            int taken = Integer.parseInt(loc_arr.getTaken_mid1());
            BarEntry v2e1 = new BarEntry(taken, i); // Jan
            valueSet2.add(v2e1);
            // row
            View rowview = lf.inflate(R.layout.row_allot, null);
            p_MyCustomTextView_mbold exam_name = (p_MyCustomTextView_mbold) rowview.findViewById(R.id.exam_name);
            p_MyCustomTextView_mbold alloted_cls = (p_MyCustomTextView_mbold) rowview.findViewById(R.id.alloted_cls);
            p_MyCustomTextView_mbold taken_cls = (p_MyCustomTextView_mbold) rowview.findViewById(R.id.taken_cls);
            p_MyCustomTextView_mbold points = (p_MyCustomTextView_mbold) rowview.findViewById(R.id.points);
            exam_name.setText(loc_arr.getExam());
            alloted_cls.setText(loc_arr.getAlloted_mid1());
            taken_cls.setText(loc_arr.getTaken_mid1());
            points.setText(loc_arr.getMarks());
            ll_main.addView(rowview);

        }
        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Allocated Class");
        barDataSet1.setColor(Color.rgb(86, 199, 252));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Taken Class");
        barDataSet2.setColor(Color.rgb(255, 87, 34));

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("MID 1");
        xAxis.add("MID 2");
        xAxis.add("MID 3");

        return xAxis;
    }

public  class  set_ui extends AsyncTask<String,String,String>
{
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       // mProgressView.start();
    }

    @Override
    protected String doInBackground(String... params) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Resources res = getResources();
                int gride_crl = res.getColor(R.color.window_bg);
                String tol= (String) tot_points.getText();
                tot_points.setText(tol + total);
                BarData data = new BarData(getXAxisValues(), getDataSet());
                chart.setData(data);
                chart.setDescription("");
                data.setValueFormatter(new MyValueFormatter());
                chart.animateXY(2000, 2000);
                chart.setGridBackgroundColor(128);
                chart.setBorderColor(255);
                chart.getAxisRight().setEnabled(false);
                YAxis leftAxis = chart.getAxisLeft();
                leftAxis.setDrawAxisLine(false);
                leftAxis.setDrawGridLines(true);
                chart.getAxisRight().setDrawLabels(false);
                chart.getAxisLeft().setDrawLabels(false);
                chart.getAxisLeft().setGridColor(gride_crl);
                //chart.getLegend().setEnabled(false);
                chart.setPinchZoom(false);
                chart.setDescription("");
                chart.setTouchEnabled(false);
                chart.setDoubleTapToZoomEnabled(false);
                chart.getXAxis().setDrawGridLines(false);
                chart.getXAxis().setAxisLineWidth(2);
                chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                chart.setPadding(16, 16, 16, 16);
                chart.invalidate();
            }
        });

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        main_layout.setVisibility(View.VISIBLE);
        //mProgressView.stop();
    }
}

    public class MyValueFormatter implements ValueFormatter {

        private DecimalFormat mFormat;


        public MyValueFormatter() {
            mFormat = new DecimalFormat("##"); // use one decimal


        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

            String per = mFormat.format(value);
            return per;


            // e.g. append a dollar-sign
        }
    }
}
