package com.egnify.vignan.Fragments;


import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.egnify.vignan.R;
import com.egnify.vignan.model.app_url;
import com.egnify.vignan.pojos.attend_info;
import com.egnify.vignan.pojos.attribute_obj;
import com.egnify.vignan.pojos.stu_info;
import com.egnify.vignan.pojos.stu_marks;
import com.egnify.vignan.utils.p_MyCustomTextView;
import com.egnify.vignan.utils.p_MyCustomTextView_mbold;
import com.egnify.vignan.utils.p_MyCustomTextView_regular;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;


public class cons_academics extends Fragment {

    private LinearLayout ll_main;
    private LayoutInflater lf;
    private String final_attendance_avg;
    private String total_points;
    private String metric_name;
    ArrayList<ArrayList<stu_info>> stu_arr = new ArrayList<>();
    private View rootView;
    private String[] row_headings = {"Exams", "Academics avg(%)", "Points/Marks"};
    private LinearLayout overview_ll;
    private LinearLayout details_ll;
    private LineChart m_chart;
    ArrayList<attend_info> atten_arr = new ArrayList<>();
    private static String ARG_SECTION_NUMBER="arg";
    private attribute_obj allo_obj;
    private Picasso picasso;


    public cons_academics() {
        // Required empty public constructor
    }

    public static cons_academics newInstance(int sectionNumber, attribute_obj l_obj) {
        cons_academics fragment = new cons_academics();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putSerializable("cons_academics", l_obj);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_cons_academics, container, false);
        allo_obj = (attribute_obj) getArguments().getSerializable("cons_academics");
        m_chart = (LineChart) rootView.findViewById(R.id.line_chartview);
        ll_main = (LinearLayout) rootView.findViewById(R.id.row_holder);
        picasso = Picasso.with(getActivity());
        picasso.setLoggingEnabled(true);
        picasso.setIndicatorsEnabled(false);
        overview_ll = (LinearLayout) rootView.findViewById(R.id.overview_ll);
        details_ll = (LinearLayout) rootView.findViewById(R.id.details_ll);
        lf = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        atten_arr.clear();
        stu_arr.clear();
        try {
            JSONArray jArr = new JSONArray(allo_obj.getMetrics());
            JSONObject jobj = jArr.getJSONObject(1);
            metric_name = jobj.getString("metric_name");
            final_attendance_avg = jobj.getString("final_avg");
            total_points = jobj.getString("total_points");
            JSONArray arr_all_det = jobj.getJSONArray("exams");
            for (int j = 0; j < arr_all_det.length(); j++) {
                JSONObject l_all_obj = arr_all_det.getJSONObject(j);
                attend_info at_detials = new attend_info();
                at_detials.setExam(l_all_obj.getString("exam"));
                at_detials.setClass_avg(l_all_obj.getString("class_avg"));
                at_detials.setMarks(l_all_obj.getString("marks"));
                JSONArray st_aobj = l_all_obj.getJSONArray("students");
                ArrayList<stu_info> stu_larr = new ArrayList<>();
                for (int l = 0; l < st_aobj.length(); l++) {
                    JSONObject stu_obj = st_aobj.getJSONObject(l);

                    stu_info stu_info = new stu_info();
                    stu_info.setAtt_mid1(stu_obj.getString("marks_percent"));
                    stu_info.setStudent_name(stu_obj.getString("student_name"));
                    stu_info.setStudent_id(stu_obj.getString("student_id"));
                    stu_larr.add(stu_info);

                }
                stu_arr.add(stu_larr);
                atten_arr.add(at_detials);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        new setup_ui().execute();
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
        return  rootView;
    }
    private void set_det_table() {
        //ArrayList<ArrayList<stu_info>> stu_arr = new ArrayList<>();
        ArrayList<String> mid1 = new ArrayList<>();
        ArrayList<String> mid2 = new ArrayList<>();
        ArrayList<String> mid3 = new ArrayList<>();
        ArrayList<String> tot = new ArrayList<>();
        ArrayList<String> stu_name_a = new ArrayList<>();
        ArrayList<String> stu_id = new ArrayList<>();
        ArrayList<stu_marks>  std_info=new ArrayList<>();
        for (int i = 0; i < stu_arr.size(); i++) {
            ArrayList<stu_info> l_stu_arr = stu_arr.get(i);
            for (int j = 0; j < l_stu_arr.size(); j++) {
                if (i == 0) {
                    stu_info l_stu_info = l_stu_arr.get(j);
                    mid1.add(l_stu_info.getAtt_mid1());
                    stu_name_a.add(l_stu_info.getStudent_name());
                    stu_id.add(l_stu_info.getStudent_id());
                } else if (i == 1) {
                    stu_info l_stu_info = l_stu_arr.get(j);
                    mid2.add(l_stu_info.getAtt_mid1());
                } else {
                    stu_info l_stu_info = l_stu_arr.get(j);
                    mid3.add(l_stu_info.getAtt_mid1());

                }

            }


        }
        for (int t = 0; t < mid1.size(); t++) {
            stu_marks stu_pojo=new stu_marks();
            String stu_name=stu_name_a.get(t);
            String su_id=stu_id.get(t);
            int mrk1 = Integer.parseInt(mid1.get(t));
            int mrk2 = Integer.parseInt(mid2.get(t));
            int mrk3 = Integer.parseInt(mid3.get(t));

            int ma_mrk = mrk1 + mrk2 + mrk3;
            double tot_f = (ma_mrk * 100) / 300;
            DecimalFormat df = new DecimalFormat("##.#");
            tot.add(df.format(tot_f));
            stu_pojo.setStd_name(stu_name);
            stu_pojo.setStd_id(su_id);
            stu_pojo.setMid1(mrk1);
            stu_pojo.setMid2(mrk2);
            stu_pojo.setMid3(mrk3);
            stu_pojo.setTotal(tot_f);
            std_info.add(stu_pojo);
        }
        Collections.sort(std_info, new stu_marks.CompId());
        Collections.reverse(std_info);
        LinearLayout stu_row_holder = (LinearLayout) rootView.findViewById(R.id.stu_row_holder);
        for (int f = 0; f < std_info.size(); f++) {
            stu_marks stu_pojo=std_info.get(f);
            View stu_row = lf.inflate(R.layout.stu_row, null);
            p_MyCustomTextView_regular st_name = (p_MyCustomTextView_regular) stu_row.findViewById(R.id.stu_name);
            p_MyCustomTextView st_id = (p_MyCustomTextView) stu_row.findViewById(R.id.stu_id);
            LinearLayout marks_holder = (LinearLayout) stu_row.findViewById(R.id.marks_holder);
            CircularImageView stu_pic=(CircularImageView) stu_row.findViewById(R.id.stu_pic);
            String url= app_url.URL_IMAGES+"stu_"+(f+1)+".jpeg";
            picasso.load(url)
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.place_holder)
                    .into(stu_pic);
            marks_holder.setWeightSum(4f);
            for (int o = 0; o < 4; o++) {
                View stu_tv = lf.inflate(R.layout.ll_text_view, null);
                p_MyCustomTextView_mbold tv_v = (p_MyCustomTextView_mbold) stu_tv.findViewById(R.id.textview_ll);
                TableRow.LayoutParams params2 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
                tv_v.setLayoutParams(params2);
                tv_v.setTextColor(getResources().getColor(R.color.primary_text));
                if (o == 0)
                    tv_v.setText(String.valueOf(stu_pojo.getMid1()));
                else if (o == 1) {
                    tv_v.setText(String.valueOf(stu_pojo.getMid2()));
                } else if (o == 2) {
                    tv_v.setText(String.valueOf(stu_pojo.getMid3()));
                } else {
                    DecimalFormat df = new DecimalFormat("##.#");
                    tv_v.setText(df.format(stu_pojo.getTotal()));
                }
                marks_holder.addView(stu_tv);
            }
            st_name.setText(stu_pojo.getStd_name());
            st_id.setText(stu_pojo.getStd_id());
            stu_row_holder.addView(stu_row);
        }

    }

    public class setup_ui extends AsyncTask<Void, Void, Void> {
        //*@Override
        protected Void doInBackground(Void... params) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    setchart(m_chart);
                    set_table();
                    set_det_table();

                }
            });


            return null;
        }
    }

    private void setchart(LineChart chart) {


        String[] labels = {"MID 1", "MID 2", "MID 3"};

        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < atten_arr.size(); i++) {
            attend_info latt_arr = atten_arr.get(i);
            int ma_mrk = Integer.parseInt(latt_arr.getMarks());
            entries.add(new BarEntry(ma_mrk, i));
            labels[i] = latt_arr.getExam();
        }


        LineDataSet dataset = new LineDataSet(entries, "# of Calls");
        Resources res = getResources();
        int idss = R.color.a2;
        int color_l = res.getColor(idss);
        int gride_crl = res.getColor(R.color.window_bg);
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
        LineData data = new LineData(labels, dataset);
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

    public void set_table() {
        p_MyCustomTextView_mbold metric_name = (p_MyCustomTextView_mbold) rootView.findViewById(R.id.metric_name);
        p_MyCustomTextView_mbold total_scr = (p_MyCustomTextView_mbold) rootView.findViewById(R.id.tot_points);
        LinearLayout table_header = (LinearLayout) rootView.findViewById(R.id.table_header);
        LinearLayout a_row_holder = (LinearLayout) rootView.findViewById(R.id.a_row_holder);
        table_header.setWeightSum(3f);
        metric_name.setText("Class Academics");
        String tol = total_scr.getText().toString();
        total_scr.setText(tol + total_points);
        for (int tv = 0; tv < row_headings.length; tv++) {
            View tv_view = lf.inflate(R.layout.ll_text_view, null);
            p_MyCustomTextView_mbold tv_v = (p_MyCustomTextView_mbold) tv_view.findViewById(R.id.textview_ll);
            tv_v.setText(row_headings[tv]);
            TableRow.LayoutParams params2 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
            tv_v.setLayoutParams(params2);
            table_header.addView(tv_view);


        }
        for (int r = 0; r < atten_arr.size(); r++) {
            attend_info att_pojo = atten_arr.get(r);
            View add_row = lf.inflate(R.layout.row_template, null);
            LinearLayout tv_holder = (LinearLayout) add_row.findViewById(R.id.tv_holder);
            tv_holder.setWeightSum(3f);
            for (int l = 0; l < row_headings.length; l++) {
                View tv_view = lf.inflate(R.layout.ll_text_view, null);
                p_MyCustomTextView_mbold tv_v = (p_MyCustomTextView_mbold) tv_view.findViewById(R.id.textview_ll);
                TableRow.LayoutParams params2 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
                tv_v.setLayoutParams(params2);
                tv_v.setTextColor(getActivity().getResources().getColor(R.color.primary_text));
                if (l == 0) {
                    tv_v.setText(att_pojo.getExam());
                } else if (l == 1) {
                    tv_v.setText(att_pojo.getClass_avg());
                } else {
                    tv_v.setText(att_pojo.getMarks());
                }

                tv_holder.addView(tv_view);
            }

            a_row_holder.addView(add_row);
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
            return mFormat.format(value);


            // e.g. append a dollar-sign
        }
    }
}
