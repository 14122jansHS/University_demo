package com.egnify.University_demo.Fragments;


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

/**
 * A simple {@link Fragment} subclass.
 */
public class tu_his_ace extends Fragment {


    private static final String ARG_SECTION_NUMBER = "section_number";
    String[] subject = {"Telugu", "Egnlish", "Maths", "Science", "Hindi", "Social"};
    String[] six_tx = {"89", "74", "53", "29", "38", "55"};
    String[] seven_tx = {"76", "67", "78", "70", "55", "65"};
    String[] eight_tx = {"56", "65", "49", "78", "88", "45"};
    String[] nine_tx = {"41", "64", "53", "79", "68", "85"};
    String[] ten_tx = {"89", "85", "75", "69", "90", "85"};
    private View rootview;
    private LayoutInflater lf;

    public tu_his_ace() {
        // Required empty public constructor
    }

    public static tu_his_ace newInstance(int sectionNumber) {
        tu_his_ace fragment = new tu_his_ace();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_tu_his_ace, container, false);
        LinearLayout schling = (LinearLayout) rootview.findViewById(R.id.schling);
        LinearLayout inter = (LinearLayout) rootview.findViewById(R.id.inter);
        final View schl_line = rootview.findViewById(R.id.scl_line);
        final View inter_line = rootview.findViewById(R.id.inter_line);
        final LineChart line_chartview = (LineChart) rootview.findViewById(R.id.line_chartview);
        lf = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        String[] labls = {"6th", "7th", "8th", "9th", "10th"};
        int[] mrks = {65, 70, 79, 56, 90};
        setchart(line_chartview, labls, mrks);

        schling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                schl_line.setVisibility(View.VISIBLE);
                inter_line.setVisibility(View.GONE);
                String[] labls = {"6th", "7th", "8th", "9th", "10th"};
                int[] mrks = {65, 70, 79, 56, 90};
                setchart(line_chartview, labls, mrks);
            }
        });
        inter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                schl_line.setVisibility(View.GONE);
                inter_line.setVisibility(View.VISIBLE);
                String[] labls = {"Maths", "Physics", "Chemistry"};
                int[] mrks = {53, 70, 89};
                setchart(line_chartview, labls, mrks);
            }
        });
        set_table();
        return rootview;

    }

    private void set_table() {
        LinearLayout stu_row_holder = (LinearLayout) rootview.findViewById(R.id.row_holder_ace);
        for (int f = 0; f < subject.length; f++) {
            // stu_marks stu_pojo=std_info.get(f);
            View stu_row = lf.inflate(R.layout.row_ace, null);
            p_MyCustomTextView_mbold sub_name = (p_MyCustomTextView_mbold) stu_row.findViewById(R.id.sub_name);
            p_MyCustomTextView_mbold sixth = (p_MyCustomTextView_mbold) stu_row.findViewById(R.id.sixth);
            p_MyCustomTextView_mbold sventh = (p_MyCustomTextView_mbold) stu_row.findViewById(R.id.sventh);
            p_MyCustomTextView_mbold eight = (p_MyCustomTextView_mbold) stu_row.findViewById(R.id.eight);
            p_MyCustomTextView_mbold nine = (p_MyCustomTextView_mbold) stu_row.findViewById(R.id.nine);
            p_MyCustomTextView_mbold tenth = (p_MyCustomTextView_mbold) stu_row.findViewById(R.id.tenth);
            sub_name.setText(subject[f]);
            sixth.setText(six_tx[f]);
            sventh.setText(seven_tx[f]);
            eight.setText(eight_tx[f]);
            nine.setText(nine_tx[f]);
            tenth.setText(ten_tx[f]);
            stu_row_holder.addView(stu_row);

        }
    }

    private void setchart(LineChart chart, String[] labels, int[] mrks) {

        DecimalFormat df = new DecimalFormat("##");
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < mrks.length; i++) {
            entries.add(new BarEntry(Float.parseFloat(df.format(mrks[i])), i));
        }

        LineDataSet dataset = new LineDataSet(entries, "# of Calls");
        Resources res = getResources();
        int idss = R.color.colorAccent;
        int color_l = res.getColor(idss);
        int gride_crl = res.getColor(R.color.divider);
        dataset.setCircleSize(4);
        dataset.setColor(color_l);
        dataset.setCircleColor(color_l);
        dataset.setCircleColorHole(color_l);
        //dataset.setDrawCubic(true);
        //dataset.setDrawFilled(true);
        dataset.setFillColor(color_l);
        dataset.setLineWidth(2);
        dataset.setValueTextSize(12);
        dataset.setValueFormatter(new MyValueFormatter());
        dataset.setValueTextColor(res.getColor(R.color.primary_text));
        LineData data = new LineData(labels, dataset);
        chart.setData(data);
        chart.animateXY(2000, 2000);
        chart.setGridBackgroundColor(125);
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
        chart.getXAxis().setAxisLineColor(res.getColor(R.color.primary_text));
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setTextColor(res.getColor(R.color.primary_text));
        chart.getXAxis().setAvoidFirstLastClipping(true);
        chart.invalidate();
    }

    public static class MyValueFormatter implements ValueFormatter {

        private DecimalFormat mFormat;


        public MyValueFormatter() {
            mFormat = new DecimalFormat("##"); // use one decimal
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

            //String per = " " + mFormat.format(value);
            return mFormat.format(value) + "%";


            // e.g. append a dollar-sign
        }
    }

}
