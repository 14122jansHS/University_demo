package com.egnify.University_demo.Fragments.teaching;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.egnify.University_demo.R;
import com.egnify.University_demo.utils.p_MyCustomTextView_mbold;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class overall_o_t extends Fragment {


    private static String ARG_SECTION_NUMBER = "section";
    ArrayList<ArrayList<HashMap<String, String>>> metrics_all_exams = new ArrayList<>();
    ArrayList<HashMap<String, String>> exam_arr = new ArrayList<>();
    DecimalFormat df = new DecimalFormat("##.0");
    private int section;
    private LineChart m_chart;
    private View rootview;
    private LayoutInflater lf;
    public overall_o_t() {
        // Required empty public constructor
    }

    public static overall_o_t newInstance(int sectionNumber, ArrayList<ArrayList<HashMap<String, String>>> metrics_all_exams) {
        overall_o_t fragment = new overall_o_t();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putSerializable("metrics_all_exams", metrics_all_exams);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_overall_o_t, container, false);

        // TextView textview=(TextView) rootview.findViewById(R.id.textview);
        section = getArguments().getInt(ARG_SECTION_NUMBER);
        metrics_all_exams = (ArrayList) getArguments().getSerializable("metrics_all_exams");
        m_chart = (LineChart) rootview.findViewById(R.id.line_chartview);
        setchart(m_chart);
        return rootview;
    }

    private void setchart(LineChart chart) {
        Resources res = getResources();
        int gride_crl = res.getColor(R.color.window_bg);
        LineData data = new LineData(getXAxisValues(), getDataSet());

        chart.setData(data);
        //chart.animateXY(2000, 2000);
        chart.setGridBackgroundColor(128);
        chart.setBorderColor(255);
        chart.setPadding(16, 16, 16, 16);
        chart.getAxisRight().setEnabled(false);
        XAxis botAxis = chart.getXAxis();
        botAxis.setTextSize(8);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(true);
        leftAxis.setAxisMaxValue(150);
        leftAxis.setAxisMinValue(0);
        leftAxis.setGridColor(gride_crl);
        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisLeft().setDrawLabels(false);
        // chart.getLegend().setEnabled(false);
        chart.setPinchZoom(false);
       // chart.set
        chart.setDescription("");
        chart.setTouchEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setAxisLineWidth(2);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setAvoidFirstLastClipping(true);
        chart.invalidate();
    }

    private ArrayList<LineDataSet> getDataSet() {
        ArrayList<LineDataSet> dataSets = null;
//        dataSets.clear();
        ArrayList<Entry> valueSet1 = new ArrayList<>();
        ArrayList<Entry> valueSet2 = new ArrayList<>();
        ArrayList<Entry> valueSet3 = new ArrayList<>();
        LinearLayout row_holder = (LinearLayout) rootview.findViewById(R.id.row_holder);
        LinearLayout row_holder2 = (LinearLayout) rootview.findViewById(R.id.row_holder2);
        lf = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < metrics_all_exams.size(); i++) {
            View row_t_o_o = lf.inflate(R.layout.row_t_o_o, null);
            View row_t_o_o2 = lf.inflate(R.layout.row_t_o_o, null);
            p_MyCustomTextView_mbold metricname = (p_MyCustomTextView_mbold) row_t_o_o.findViewById(R.id.metricname);
            p_MyCustomTextView_mbold mid1 = (p_MyCustomTextView_mbold) row_t_o_o.findViewById(R.id.mid1);
            p_MyCustomTextView_mbold mid2 = (p_MyCustomTextView_mbold) row_t_o_o.findViewById(R.id.mid2);
            p_MyCustomTextView_mbold mid3 = (p_MyCustomTextView_mbold) row_t_o_o.findViewById(R.id.mid3);
            p_MyCustomTextView_mbold total = (p_MyCustomTextView_mbold) row_t_o_o.findViewById(R.id.total);
            //2
            p_MyCustomTextView_mbold metricname2 = (p_MyCustomTextView_mbold) row_t_o_o2.findViewById(R.id.metricname);
            p_MyCustomTextView_mbold mid12 = (p_MyCustomTextView_mbold) row_t_o_o2.findViewById(R.id.mid1);
            p_MyCustomTextView_mbold mid22 = (p_MyCustomTextView_mbold) row_t_o_o2.findViewById(R.id.mid2);
            p_MyCustomTextView_mbold mid32 = (p_MyCustomTextView_mbold) row_t_o_o2.findViewById(R.id.mid3);
            p_MyCustomTextView_mbold total2 = (p_MyCustomTextView_mbold) row_t_o_o2.findViewById(R.id.total);
            if (i == 0) {
                metricname.setText("Alloted Classes");
                metricname2.setText("Alloted Classes");
            } else if (i == 1) {
                metricname.setText("Class Attendance");
                metricname2.setText("Class Attendance");
            } else if (i == 2) {
                metricname.setText("Class Marks");
                metricname2.setText("Class Marks");
            }

            exam_arr = metrics_all_exams.get(i);
            int tot_points = 0;
            int tot_per = 0;

            for (int j = 0; j < exam_arr.size(); j++) {
                HashMap<String, String> hashMap = exam_arr.get(j);
                float per = Float.parseFloat(hashMap.get("per"));
                int points = Integer.parseInt(hashMap.get("points"));
                tot_per += per;
                tot_points+=points;
                if (j == 0) {
                    mid1.setText(df.format(per));
                    mid12.setText(String.valueOf(points));
                } else if (j == 1) {
                    mid2.setText(df.format(per));
                    mid22.setText(String.valueOf(points));
                } else {
                    mid3.setText(df.format(per));
                    mid32.setText(String.valueOf(points));
                }
                BarEntry v1e1 = new BarEntry(per, j);
                if (i == 0) {// Jan
                    valueSet1.add(v1e1);
                } else if (i == 1) {
                    valueSet2.add(v1e1);
                } else if (i == 2) {
                    valueSet3.add(v1e1);
                }
            }
            Double perc= Double.valueOf(tot_per/3);

            total.setText(df.format(perc)+"%");
            total2.setText(String.valueOf(tot_points));
            row_holder2.addView(row_t_o_o2);
            row_holder.addView(row_t_o_o);

        }
        LineDataSet lineDataSet1 = new LineDataSet(valueSet1, "Alloted Class");
        set_data_format(lineDataSet1, R.color.a2);
        //lineDataSet1.set
        LineDataSet lineDataSet2 = new LineDataSet(valueSet2, "Attendance");
        set_data_format(lineDataSet2, R.color.b1);
        LineDataSet lineDataSet3 = new LineDataSet(valueSet3, "Marks");
        set_data_format(lineDataSet3, R.color.c1);

        dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet2);
        dataSets.add(lineDataSet3);
        return dataSets;
    }

    private void set_data_format(LineDataSet dataset, int idss) {
        Resources res = getResources();

        int color_l = res.getColor(idss);
        //dataset.
        dataset.setCircleSize(4);
        dataset.setColor(color_l);
        dataset.setCircleColor(color_l);
        dataset.setCircleColorHole(color_l);
        //dataset.setDrawCubic(true);
        //dataset.setDrawFilled(true);
        dataset.setFillColor(color_l);
        dataset.setLineWidth(2);
        dataset.setDrawCubic(true);
        dataset.setValueTextSize(10);
        dataset.setValueFormatter(new MyValueFormatter());
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Mid 1");
        xAxis.add("Mid 2");
        xAxis.add("Mid 3");
        return xAxis;
    }

    public class MyValueFormatter implements ValueFormatter {
        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("##"); // use one decimal
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            String ret = mFormat.format(value);
            return ret + "%";
        }
    }

}
