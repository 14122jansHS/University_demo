package com.egnify.vignan.Fragments.teaching;


import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.egnify.vignan.Adapter.m_d_adapter;
import com.egnify.vignan.R;
import com.egnify.vignan.pojos.attend_info;
import com.egnify.vignan.pojos.attri_details;
import com.egnify.vignan.pojos.stu_info;
import com.egnify.vignan.utils.p_MyCustomTextView;
import com.egnify.vignan.utils.p_MyCustomTextView_mbold;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class mid2_t extends Fragment {
    private static String ARG_SECTION_NUMBER = "section";
    private int section;
    ArrayList<ArrayList<ArrayList<stu_info>>> stu_arr = new ArrayList<>();
    ArrayList<ArrayList<ArrayList<stu_info>>> mstu_arr = new ArrayList<>();
    ArrayList<attend_info> atten_arr = new ArrayList<>();
    ArrayList<attend_info> mrks_arr = new ArrayList<>();
    ArrayList<attri_details> attri_detail_arr = new ArrayList<>();
    private BarChart m_chart;
    private LinearLayout main_layout;
    private LinearLayout details_ll;
    private LinearLayout overview_ll;
    private LinearLayout ll_main;
    private LayoutInflater lf;
    private RecyclerView stu_row_holder;
    private m_d_adapter adapter;
    private ArrayList<stu_info> mid1_m_stu;
    private ArrayList<stu_info> mid1_a_stu;

    public mid2_t() {
        // Required empty public constructor
    }

    public static mid2_t newInstance(int i, ArrayList<attend_info> atten_arr, ArrayList<ArrayList<ArrayList<stu_info>>> stu_arr, ArrayList<ArrayList<ArrayList<stu_info>>> mstu_arr, ArrayList<attend_info> mrks_arr, ArrayList<attri_details> attri_detail_arr) {
        mid2_t fragment = new mid2_t();
        Bundle args = new Bundle();
        args.putSerializable("atten_arr", atten_arr);
        args.putSerializable("stu_arr", stu_arr);
        args.putSerializable("mstu_arr", mstu_arr);
        args.putSerializable("mrks_arr", mrks_arr);
        args.putSerializable("attri_detail_arr", attri_detail_arr);
        args.putInt(ARG_SECTION_NUMBER, i);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_mid1_t, container, false);
        atten_arr = (ArrayList) getArguments().getSerializable("atten_arr");
        mrks_arr = (ArrayList) getArguments().getSerializable("mrks_arr");
        attri_detail_arr = (ArrayList) getArguments().getSerializable("attri_detail_arr");
        stu_arr = (ArrayList) getArguments().getSerializable("stu_arr");
        mstu_arr = (ArrayList) getArguments().getSerializable("mstu_arr");

        //lldeclaration
        stu_row_holder=(RecyclerView) rootView.findViewById(R.id.stu_row_holder);
        stu_row_holder.setLayoutManager(new LinearLayoutManager(getActivity()));
        lf = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        m_chart = (BarChart) rootView.findViewById(R.id.chartview);
        ll_main = (LinearLayout) rootView.findViewById(R.id.a_row_holder);
        overview_ll = (LinearLayout) rootView.findViewById(R.id.overview_ll);
        details_ll = (LinearLayout) rootView.findViewById(R.id.details_ll);
        main_layout = (LinearLayout) rootView.findViewById(R.id.main_layout);
        final p_MyCustomTextView ob_tv = (p_MyCustomTextView) rootView.findViewById(R.id.ob_tv);
        final p_MyCustomTextView de_tv = (p_MyCustomTextView) rootView.findViewById(R.id.de_tv);
        final LinearLayout ov_click = (LinearLayout) rootView.findViewById(R.id.ov_click);
        final LinearLayout de_click = (LinearLayout) rootView.findViewById(R.id.de_click);
        de_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ov_click.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                ob_tv.setTextColor(getActivity().getResources().getColor(R.color.primary_text));
                de_click.setBackgroundColor(getActivity().getResources().getColor(R.color.red));
                de_tv.setTextColor(getActivity().getResources().getColor(R.color.white));
                overview_ll.setVisibility(View.GONE);
                details_ll.setVisibility(View.VISIBLE);

            }
        });
        ov_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ov_click.setBackgroundColor(getActivity().getResources().getColor(R.color.red));
                ob_tv.setTextColor(getActivity().getResources().getColor(R.color.white));
                de_click.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                de_tv.setTextColor(getActivity().getResources().getColor(R.color.primary_text));
                overview_ll.setVisibility(View.VISIBLE);
                details_ll.setVisibility(View.GONE);
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new setup_ui().execute();
            }
        }, 1000);
        return rootView;
    }

    public class setup_ui extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    setchart(m_chart);
                    set_table();

                }
            });

            return null;
        }
    }

    private void set_table() {
        ArrayList<ArrayList<stu_info>> l_stu_arr = stu_arr.get(0);
        ArrayList<ArrayList<stu_info>> m_stu_arr = mstu_arr.get(0);
        mid1_a_stu = l_stu_arr.get(1);
        mid1_m_stu=m_stu_arr.get(1);

        //mid2_stu = l_stu_arr.get(1);
       // mid3_stu = l_stu_arr.get(2);
        adapter = new m_d_adapter(getActivity(), mid1_a_stu, mid1_m_stu);
        stu_row_holder.setAdapter(adapter);
    }

    private void setchart(BarChart chart) {


        String[] labels = {"Alloted", "Attendance", "Marks"};

        ArrayList<BarEntry> entries = new ArrayList<>();
        attend_info atten_pojo = atten_arr.get(1);
        attend_info marks_pojo = mrks_arr.get(1);
        attri_details alot_pojo = attri_detail_arr.get(1);
        Double per = (Double.parseDouble(alot_pojo.getTaken_mid1()) + Double.parseDouble(alot_pojo.getTaken_mid1())) / 2;
        DecimalFormat df = new DecimalFormat("##.#");
        final Float fper = Float.valueOf(df.format(per));
        entries.add(new BarEntry(fper, 0));
        entries.add(new BarEntry(Float.parseFloat(atten_pojo.getClass_avg()), 1));
        entries.add(new BarEntry(Float.parseFloat(marks_pojo.getClass_avg()), 2));

        for (int j = 0; j <= 2; j++)
        {
            View add_row = lf.inflate(R.layout.row_t_m_o, null);
            p_MyCustomTextView_mbold metric_name = (p_MyCustomTextView_mbold) add_row.findViewById(R.id.metricname);
            p_MyCustomTextView_mbold per_tv = (p_MyCustomTextView_mbold) add_row.findViewById(R.id.per);
            p_MyCustomTextView_mbold point_tv = (p_MyCustomTextView_mbold) add_row.findViewById(R.id.point);
            if (j == 0) {
                metric_name.setText("Alloted Classes");
                per_tv.setText(String.valueOf(fper));
                point_tv.setText(alot_pojo.getMarks());
            } else if (j == 1) {
                metric_name.setText("Attendance");
                per_tv.setText(atten_pojo.getClass_avg());
                point_tv.setText(atten_pojo.getMarks());
            }
            if (j == 2) {
                metric_name.setText("Marks");
                per_tv.setText(marks_pojo.getClass_avg());
                point_tv.setText(marks_pojo.getMarks());
            }
            ll_main.addView(add_row);
        }

        BarDataSet dataset = new BarDataSet(entries, "# of Calls");
        Resources res = getResources();
        int gride_crl = getActivity().getResources().getColor(R.color.window_bg);
        dataset.setBarSpacePercent(50f);
        dataset.setValueTextSize(12);
        dataset.setColors(new int[]{getActivity().getResources().getColor(R.color.a2),
                getActivity().getResources().getColor(R.color.b1),
                getActivity().getResources().getColor(R.color.c1)});

        dataset.setValueFormatter(new MyValueFormatter());
        BarData data = new BarData(labels, dataset);
        chart.setData(data);
        chart.animateXY(2000, 2000);
        chart.setGridBackgroundColor(128);
        chart.setBorderColor(255);
        chart.getAxisRight().setEnabled(false);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(true);
        leftAxis.setAxisMaxValue(100);
        leftAxis.setAxisMinValue(0);
        leftAxis.setGridColor(gride_crl);
        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getLegend().setEnabled(false);
        chart.setPinchZoom(false);
        chart.setDescription("");
        chart.setTouchEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setAxisLineWidth(2);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        chart.invalidate();
    }

    public class MyValueFormatter implements ValueFormatter {
        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("##"); // use one decimal
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            String re=mFormat.format(value);
            return re+" %";
        }
    }
}
