package com.egnify.University_demo.Activities;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.egnify.University_demo.R;
import com.egnify.University_demo.pojos.mid_info;
import com.egnify.University_demo.pojos.subjects;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class stu_mid_marks extends AppCompatActivity {

    //private ExitActivityTransition exitTransition;
    ArrayList<subjects> sub_arr = new ArrayList<>();
    ArrayList<ArrayList<mid_info>> mid_sub_arr = new ArrayList<>();
    ArrayList<Integer> mid1 = new ArrayList<>();
    ArrayList<Integer> mid2 = new ArrayList<>();
    ArrayList<Integer> mid3 = new ArrayList<>();
    private String pre_sem_details;
    private int color_id;
    private ImageView back_icon;
    private FrameLayout main_fl;
    private LinearLayout upper_hiden;
    private RelativeLayout bar_content;
    private ScrollView bottom_layout;
    private LayoutInflater lf;
    private double per1;
    private double per2;
    private double per3;
    private DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_mid_marks);
        //  exitTransition = ActivityTransition.with(getIntent()).to(findViewById(R.id.testwise)).start(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            color_id = extras.getInt("color_id");
            pre_sem_details = extras.getString("pre_sem_details");
        }
        df = new DecimalFormat("##");
        back_icon = (ImageView) findViewById(R.id.back_icon);
        main_fl = (FrameLayout) findViewById(R.id.main_fl);
        upper_hiden = (LinearLayout) findViewById(R.id.upper_hiden);
        bar_content = (RelativeLayout) findViewById(R.id.bar_content);
        bottom_layout = (ScrollView) findViewById(R.id.bottom_layout);
        back_icon.postDelayed(new Runnable() {
            public void run() {
                bottom_layout.setVisibility(View.VISIBLE);
                upper_hiden.setVisibility(View.VISIBLE);
                bar_content.setVisibility(View.VISIBLE);
            }
        }, 500);
        new parse_json().execute();
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
//        layout_main.setVisibility(View.INVISIBLE);
      /*  bottom_layout.setVisibility(View.INVISIBLE);
        upper_hiden.setVisibility(View.INVISIBLE);

        bar_content.setVisibility(View.INVISIBLE);
        exitTransition.exit(this);*/
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    private void set_ui() {
        LineChart lchart = (LineChart) findViewById(R.id.line_chartview);
        setchart(lchart);
        LinearLayout row_holder = (LinearLayout) findViewById(R.id.row_holder);
        lf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < sub_arr.size(); i++) {
            subjects subjspojo = sub_arr.get(i);
            View row_t_o_o = lf.inflate(R.layout.row_stu_mid, null);
            p_MyCustomTextView_mbold metricname = (p_MyCustomTextView_mbold) row_t_o_o.findViewById(R.id.exam_name);
            p_MyCustomTextView_mbold mid1 = (p_MyCustomTextView_mbold) row_t_o_o.findViewById(R.id.alloted_cls);
            p_MyCustomTextView_mbold mid2 = (p_MyCustomTextView_mbold) row_t_o_o.findViewById(R.id.taken_cls);
            p_MyCustomTextView_mbold mid3 = (p_MyCustomTextView_mbold) row_t_o_o.findViewById(R.id.points);

            metricname.setText(subjspojo.getShort_name());
            ArrayList<mid_info> mid_info_arr = mid_sub_arr.get(i);
            mid_info mid_pojo = mid_info_arr.get(0);
            mid_info mid_pojo1 = mid_info_arr.get(1);
            mid_info mid_pojo2 = mid_info_arr.get(2);
            mid1.setText(mid_pojo.getMarks());
            mid2.setText(mid_pojo1.getMarks());
            mid3.setText(mid_pojo2.getMarks());
            /*int tot = ((Integer.parseInt(mid_pojo.getMarks())*100)/30) + ((Integer.parseInt(mid_pojo1.getMarks())*100)/30) + ((Integer.parseInt(mid_pojo2.getMarks())*100)/30);
            double per = tot / 3;
            DecimalFormat df = new DecimalFormat("##.#");
            total.setText(df.format(per) + "%");*/
            row_holder.addView(row_t_o_o);

        }
        View row_t_o_o = lf.inflate(R.layout.row_stu_mid, null);
        p_MyCustomTextView_mbold metricname = (p_MyCustomTextView_mbold) row_t_o_o.findViewById(R.id.exam_name);
        p_MyCustomTextView_mbold mid1 = (p_MyCustomTextView_mbold) row_t_o_o.findViewById(R.id.alloted_cls);
        p_MyCustomTextView_mbold mid2 = (p_MyCustomTextView_mbold) row_t_o_o.findViewById(R.id.taken_cls);
        p_MyCustomTextView_mbold mid3 = (p_MyCustomTextView_mbold) row_t_o_o.findViewById(R.id.points);
        metricname.setText("Percentage");
        mid1.setText(df.format(per1) + "%");
        mid2.setText(df.format(per2) + "%");
        mid3.setText(df.format(per3) + "%");
        row_holder.addView(row_t_o_o);
    }

    private void setchart(LineChart chart) {


        String[] labels = {"   MID 1", "MID 2", "MID 3   "};
        int tot_mid1 = 0;
        int tot_mid2 = 0;
        int tot_mid3 = 0;

        for (int i = 0; i < mid1.size(); i++) {
            tot_mid1 += mid1.get(i);
            tot_mid2 += mid2.get(i);
            tot_mid3 += mid3.get(i);
        }
        int max_mrk = 30 * mid1.size();
        per1 = (tot_mid1 * 100) / max_mrk;
        per2 = (tot_mid2 * 100) / max_mrk;
        per3 = (tot_mid3 * 100) / max_mrk;

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new BarEntry(Float.parseFloat(df.format(per1)), 0));
        entries.add(new BarEntry(Float.parseFloat(df.format(per2)), 1));
        entries.add(new BarEntry(Float.parseFloat(df.format(per3)), 2));
        LineDataSet dataset = new LineDataSet(entries, "# of Calls");
        Resources res = getResources();
        int idss = R.color.white;
        int color_l = res.getColor(idss);
        int gride_crl = res.getColor(R.color.tab_bg);
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
        dataset.setValueTextColor(res.getColor(R.color.white));
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
        chart.getXAxis().setAxisLineColor(res.getColor(R.color.white));
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setTextColor(res.getColor(R.color.white));
        chart.getXAxis().setAvoidFirstLastClipping(true);
        chart.invalidate();
    }

    public class parse_json extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                JSONObject obj = new JSONObject(pre_sem_details);
                int success = obj.getInt("success");
                if (success == 1) {
                    JSONObject present_sem = obj.getJSONObject("present_sem");
                    JSONArray arr = present_sem.getJSONArray("subjects");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject Sub_details = arr.getJSONObject(i);
                        subjects subjects = new subjects();
                        subjects.setSubject_name(Sub_details.getString("subject_name"));
                        subjects.setShort_name(Sub_details.getString("short_name"));
                        subjects.setFaculty_name(Sub_details.getString("Faculty_name"));
                        subjects.setFaculty_id(Sub_details.getString("Faculty_id"));
                        sub_arr.add(subjects);
                        JSONArray mid_arrr = Sub_details.getJSONArray("mids");
                        ArrayList<mid_info> l_mid_arr = new ArrayList<>();
                        for (int j = 0; j < mid_arrr.length(); j++) {
                            JSONObject jobj = mid_arrr.getJSONObject(j);
                            mid_info mid_pojo = new mid_info();
                            mid_pojo.setExam_name(jobj.getString("exam_name"));
                            mid_pojo.setMarks(jobj.getString("marks"));
                            mid_pojo.setAttendance(jobj.getString("attendance"));
                            mid_pojo.setTotal_classes(jobj.getString("total_classes"));
                            mid_pojo.setClasses_present(jobj.getString("classes_present"));
                            l_mid_arr.add(mid_pojo);
                            if (jobj.getString("exam_name").equals("MID 1")) {
                                mid1.add(Integer.parseInt(jobj.getString("marks")));
                            } else if (jobj.getString("exam_name").equals("MID 2")) {
                                mid2.add(Integer.parseInt(jobj.getString("marks")));
                            } else if (jobj.getString("exam_name").equals("MID 3")) {
                                mid3.add(Integer.parseInt(jobj.getString("marks")));
                            }
                        }
                        mid_sub_arr.add(l_mid_arr);
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            set_ui();
        }
    }

    public class MyValueFormatter implements ValueFormatter {

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
