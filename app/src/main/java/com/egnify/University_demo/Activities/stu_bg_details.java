package com.egnify.University_demo.Activities;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.egnify.University_demo.R;
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

public class stu_bg_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_bg_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinearLayout schling = (LinearLayout) findViewById(R.id.schling);
        LinearLayout inter = (LinearLayout) findViewById(R.id.inter);
        final View schl_line = findViewById(R.id.scl_line);
        final View inter_line = findViewById(R.id.inter_line);
        final LineChart line_chartview = (LineChart) findViewById(R.id.line_chartview);
        String[] labls = {"6th", "7th", "8th", "9th", "10th"};
        int[] mrks = {65, 70, 79, 56, 90};
        setchart(line_chartview, labls, mrks);
        ImageView acc_icon = (ImageView) findViewById(R.id.acc_icon);
        ImageView trof_icon = (ImageView) findViewById(R.id.trof_icon);
        ImageView flag_icon = (ImageView) findViewById(R.id.flag_icon);
        int color = Color.parseColor("#448AFF"); //The color u want
        flag_icon.setColorFilter(color);
        trof_icon.setColorFilter(color);
        acc_icon.setColorFilter(color);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_counselling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
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
