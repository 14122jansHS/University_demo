package com.egnify.University_demo.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.egnify.University_demo.Fragments.teaching.mid1_t;
import com.egnify.University_demo.Fragments.teaching.mid2_t;
import com.egnify.University_demo.Fragments.teaching.mid3_t;
import com.egnify.University_demo.Fragments.teaching.overall_t;
import com.egnify.University_demo.R;
import com.egnify.University_demo.pojos.attend_info;
import com.egnify.University_demo.pojos.attri_details;
import com.egnify.University_demo.pojos.attribute_obj;
import com.egnify.University_demo.pojos.stu_info;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class Academics extends AppCompatActivity {

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ArrayList<attribute_obj> filelist;
    ArrayList<HashMap<String, String>> metrics_overall = new ArrayList<>();
    ArrayList<ArrayList<HashMap<String, String>>> metrics_all_exams = new ArrayList<>();
    ArrayList<ArrayList<ArrayList<stu_info>>> stu_arr = new ArrayList<>();
    ArrayList<ArrayList<ArrayList<stu_info>>> mstu_arr = new ArrayList<>();
    ArrayList<attend_info> atten_arr = new ArrayList<>();
    ArrayList<attend_info> mrks_arr = new ArrayList<>();
    ArrayList<attri_details> attri_detail_arr = new ArrayList<>();
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academics);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_left));
       /* toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        filelist= (ArrayList<attribute_obj>)bundle.getSerializable("FILES_TO_SEND");

        //filelist = (ArrayList) getIntent().getSerializableExtra("FILES_TO_SEND");
        new backgroud_parse().execute();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_academics, menu);
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

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0)
            {
                attribute_obj l_obj=filelist.get(0);
                fragment = overall_t.newInstance(position + 1, metrics_all_exams,atten_arr,stu_arr,mstu_arr,mrks_arr);
            } else if (position == 1) {
                attribute_obj l_obj=filelist.get(0);
                fragment = mid1_t.newInstance(position + 1, atten_arr,stu_arr,mstu_arr,mrks_arr,attri_detail_arr);
            } else if (position == 2){
                attribute_obj l_obj=filelist.get(0);
                fragment = mid2_t.newInstance(position + 1, atten_arr, stu_arr, mstu_arr, mrks_arr, attri_detail_arr);
            }else {
                attribute_obj l_obj=filelist.get(0);
                fragment = mid3_t.newInstance(position + 1, atten_arr, stu_arr, mstu_arr, mrks_arr, attri_detail_arr);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "OVERVIEW";
                case 1:
                    return "MID 1";
                case 2:
                    return "MID 2";
                case 3:
                    return "MID 3";
            }
            return null;
        }
    }

    public class backgroud_parse extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                attribute_obj allo_obj=filelist.get(0);
                JSONArray jArr = new JSONArray(allo_obj.getMetrics());
                for (int i = 0; i < jArr.length(); i++) {
                    HashMap<String, String> metric_map = new HashMap<>();
                    ArrayList<HashMap<String, String>> metric_exam = new ArrayList<>();

                    if (i == 0) {
                        JSONObject jobj = jArr.getJSONObject(i);
                        String metric_name = jobj.getString("metric_name");
                        String alloted_total = jobj.getString("alloted_total");
                        String taken_total = jobj.getString("taken_total");
                        String total = jobj.getString("total");
                        double percentage = (Integer.parseInt(taken_total) * 100) / Integer.parseInt(alloted_total);
                        DecimalFormat df = new DecimalFormat("##");
                        String per = df.format(percentage);
                        metric_map.put("metric_name", metric_name);
                        metric_map.put("per", per);
                        metric_map.put("point", total);
                        JSONArray arr_all_det = jobj.getJSONArray("alloted_details");
                        for (int j = 0; j < arr_all_det.length(); j++) {
                            HashMap<String, String> metric_exm_map = new HashMap<>();
                            JSONObject l_all_obj = arr_all_det.getJSONObject(j);
                            attri_details at_detials = new attri_details();
                            at_detials.setAlloted_mid1(l_all_obj.getString("alloted_mid1"));
                            at_detials.setExam(l_all_obj.getString("exam"));
                            at_detials.setMarks(l_all_obj.getString("marks"));
                            at_detials.setTaken_mid1(l_all_obj.getString("taken_mid1"));
                            double percentag = (Integer.parseInt(l_all_obj.getString("taken_mid1")) * 100) / Integer.parseInt(l_all_obj.getString("alloted_mid1"));
                            DecimalFormat df2 = new DecimalFormat("##");
                            String per2 = df2.format(percentag);
                            metric_exm_map.put("metric_name", metric_name);
                            metric_exm_map.put("exam_name", l_all_obj.getString("exam"));
                            metric_exm_map.put("per", per2);
                            metric_exm_map.put("points", l_all_obj.getString("marks"));
                            metric_exam.add(metric_exm_map);
                            attri_detail_arr.add(at_detials);
                        }
                    } else if (i == 1)
                    {
                        ArrayList<ArrayList<stu_info>> std_info = new ArrayList<>();
                        JSONObject jobj = jArr.getJSONObject(i);
                        String metric_name = jobj.getString("metric_name");
                        String final_attendance_avg = jobj.getString("final_attendance_avg");
                        String total_points = jobj.getString("total_points");
                        metric_map.put("metric_name", metric_name);
                        metric_map.put("per", final_attendance_avg);
                        metric_map.put("point", total_points);
                        JSONArray arr_all_det = jobj.getJSONArray("attendance");
                        for (int j = 0; j < 3; j++)
                        {
                            HashMap<String, String> metric_exm_map = new HashMap<>();
                            attend_info at_detials = new attend_info();
                            ArrayList<stu_info> stu_larr = new ArrayList<>();
                            if (j < arr_all_det.length())
                            {
                                JSONObject l_all_obj = arr_all_det.getJSONObject(j);
                                at_detials.setExam(l_all_obj.getString("exam"));
                                at_detials.setClass_avg(l_all_obj.getString("class_avg"));
                                at_detials.setMarks(l_all_obj.getString("marks"));
                                metric_exm_map.put("metric_name", metric_name);
                                metric_exm_map.put("exam_name", l_all_obj.getString("exam"));
                                metric_exm_map.put("per", l_all_obj.getString("class_avg"));
                                metric_exm_map.put("points", l_all_obj.getString("marks"));
                                metric_exam.add(metric_exm_map);
                                JSONArray st_aobj = l_all_obj.getJSONArray("students");
                                for (int l = 0; l < st_aobj.length(); l++) {
                                    JSONObject stu_obj = st_aobj.getJSONObject(l);
                                    stu_info stu_info = new stu_info();
                                    stu_info.setAtt_mid1(stu_obj.getString("att_mid1"));
                                    stu_info.setStudent_name(stu_obj.getString("student_name"));
                                    stu_info.setStudent_id(stu_obj.getString("student_id"));
                                    stu_larr.add(stu_info);
                                }

                            } else {
                                if (j == 1) {
                                    at_detials.setExam("Mid 2");
                                } else {
                                    at_detials.setExam("Mid 3");
                                }
                                at_detials.setClass_avg("0");
                                at_detials.setMarks("0");
                                stu_larr.add(null);
                            }
                            std_info.add(stu_larr);
                            atten_arr.add(at_detials);

                        }
                        stu_arr.add(std_info);

                    } else if (i == 2)
                    {
                        ArrayList<ArrayList<stu_info>> std_info = new ArrayList<>();
                        JSONObject jobj = jArr.getJSONObject(i);
                        String metric_name = jobj.getString("metric_name");
                        String final_attendance_avg = jobj.getString("final_avg");
                        String total_points = jobj.getString("total_points");
                        metric_map.put("metric_name", metric_name);
                        metric_map.put("per", final_attendance_avg);
                        metric_map.put("point", total_points);
                        JSONArray arr_all_det = jobj.getJSONArray("exams");
                        for (int j = 0; j < arr_all_det.length(); j++) {
                            HashMap<String, String> metric_exm_map = new HashMap<>();
                            JSONObject l_all_obj = arr_all_det.getJSONObject(j);
                            attend_info at_detials = new attend_info();
                            at_detials.setExam(l_all_obj.getString("exam"));
                            at_detials.setClass_avg(l_all_obj.getString("class_avg"));
                            at_detials.setMarks(l_all_obj.getString("marks"));
                            metric_exm_map.put("metric_name", metric_name);
                            metric_exm_map.put("exam_name", l_all_obj.getString("exam"));
                            metric_exm_map.put("per", l_all_obj.getString("class_avg"));
                            metric_exm_map.put("points", l_all_obj.getString("marks"));
                            metric_exam.add(metric_exm_map);
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
                            std_info.add(stu_larr);
                            mrks_arr.add(at_detials);
                        }
                        mstu_arr.add(std_info);
                    }

                    metrics_overall.add(metric_map);
                    metrics_all_exams.add(metric_exam);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setOffscreenPageLimit(3);
            mViewPager.setAdapter(mSectionsPagerAdapter);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);
        }
    }
}
