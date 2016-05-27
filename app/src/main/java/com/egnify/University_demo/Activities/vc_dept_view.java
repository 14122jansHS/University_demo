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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.egnify.University_demo.Fragments.vc_dept_detail;
import com.egnify.University_demo.Fragments.vc_dept_summary;
import com.egnify.University_demo.R;
import com.egnify.University_demo.model.app_url;
import com.egnify.University_demo.pojos.attribute_obj;
import com.egnify.University_demo.pojos.fac_info;
import com.egnify.University_demo.utils.p_MyCustomTextView;
import com.rey.material.app.ThemeManager;
import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class vc_dept_view extends AppCompatActivity {

    LinearLayout ll_retry;
    ArrayList<ArrayList<attribute_obj>> attri_arr = new ArrayList<>();
    ArrayList<fac_info> fact_info = new ArrayList<>();
    ArrayList<HashMap<String, String>> f_list;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private String over_p;
    private String fac_nam;
    private ProgressView mProgressView;
    private Button retry;
    private TabLayout tabLayout;
    private int id;
    private String hod_name;
    private LayoutInflater lf;
    private String dept_name;
    private ImageView img_bell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.init(this, 1, 0, null);
        setContentView(R.layout.activity_vc_dept_view);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
            hod_name = extras.getString("name");
            dept_name = extras.getString("dept_name");

        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(dept_name);
        ll_retry = (LinearLayout) findViewById(R.id.ll_retry);
        retry = (Button) findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new get_details().execute();
            }
        });
        mProgressView = (ProgressView) findViewById(R.id.login_progress);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        img_bell = (ImageView) findViewById(R.id.img_bell);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        new get_details().execute();
        img_bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(vc_dept_view.this, Feeds.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.stay);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_research_contri, menu);
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

    public void set_ui() {

        ll_retry.setVisibility(View.GONE);
        mViewPager.setVisibility(View.VISIBLE);

        int allotedclasses = 0;
        int counsellingstudents = 0;
        int ResearchatFacultyLevel = 0;
        int ResearchatHODlevel = 0;
        int OtherProfessionalActivites = 0;
        f_list = new ArrayList<>();
        for (int f = 0; f < attri_arr.size(); f++) {
            fac_info fac_pojo = fact_info.get(f);
            HashMap<String, String> list_map = new HashMap<>();
            ArrayList<attribute_obj> atriss = attri_arr.get(f);
            list_map.put("fac_name", fac_pojo.getTeacher_name());
            list_map.put("fac_score", fac_pojo.getFaculty_score());
            for (int l = 0; l < atriss.size(); l++) {
                attribute_obj at_pojo = atriss.get(l);
                if (l == 0) {
                    list_map.put("allotedclasses", at_pojo.getFinal_points());
                    allotedclasses += Integer.parseInt(at_pojo.getFinal_points());
                } else if (l == 1) {
                    list_map.put("counsellingstudents", at_pojo.getFinal_points());
                    counsellingstudents += Integer.parseInt(at_pojo.getFinal_points());
                } else if (l == 2) {

                    list_map.put("ResearchatFacultyLevel", at_pojo.getFinal_points());
                    ResearchatFacultyLevel += Integer.parseInt(at_pojo.getFinal_points());
                } else if (l == 3) {
                    list_map.put("ResearchatHODlevel", at_pojo.getFinal_points());
                    ResearchatHODlevel += Integer.parseInt(at_pojo.getFinal_points());
                } else {
                    list_map.put("OtherProfessionalActivites", at_pojo.getFinal_points());
                    OtherProfessionalActivites += Integer.parseInt(at_pojo.getFinal_points());
                }
            }
            f_list.add(list_map);
        }
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        mProgressView.stop();
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("dept_overall.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = vc_dept_summary.newInstance(position + 1, f_list);
            } else {
                fragment = vc_dept_detail.newInstance(position + 1, f_list);

            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Summary";
                case 1:
                    return "Details";
            }
            return null;
        }
    }

    public class get_details extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressView.start();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url_hod = app_url.URL_DEPT + id;
            Log.d("hod_url", url_hod);
            try {
                JSONObject jsonobject = new JSONObject(loadJSONFromAsset());
                int scs = jsonobject.getInt("success");
                if (scs == 1) {
                    over_p = jsonobject.getString("dept_score");
                    fac_nam = "C.Srinivasa Kumar";

                    JSONArray teachers = jsonobject.getJSONArray("teachers");
                    for (int j = 0; j < teachers.length(); j++) {
                        JSONObject tea_obj = teachers.getJSONObject(j);
                        ArrayList<attribute_obj> l_attri_arr = new ArrayList<>();
                        fac_info fac_pojo = new fac_info();
                        fac_pojo.setEmail(tea_obj.getString("email"));
                        fac_pojo.setTeacher_name(tea_obj.getString("teacher_name"));
                        fac_pojo.setFaculty_score(tea_obj.getString("faculty_score"));
                        JSONArray attribute = tea_obj.getJSONArray("attribute");

                        for (int i = 0; i < attribute.length(); i++) {
                            JSONObject jobj1 = attribute.getJSONObject(i);
                            attribute_obj atriss = new attribute_obj();
                            atriss.setName(jobj1.getString("name"));
                            atriss.setFinal_points(jobj1.getString("final_points"));
                            atriss.setMetrics(jobj1.getJSONArray("metric").toString());
                            l_attri_arr.add(atriss);
                        }
                        attri_arr.add(l_attri_arr);
                        fact_info.add(fac_pojo);
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressView.stop();
                            ll_retry.setVisibility(View.VISIBLE);
                            p_MyCustomTextView msg = (p_MyCustomTextView) findViewById(R.id.msg);
                            msg.setText("Try After Sometime!!!");
                        }
                    });

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        set_ui();
                    }

                });

                }catch(Exception e){
                    if (e.getMessage() != null) {
                        //Toast.makeText(new_home_activity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
                        Log.e("Error", "parsing error");
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProgressView.stop();
                                ll_retry.setVisibility(View.VISIBLE);
                                p_MyCustomTextView msg = (p_MyCustomTextView) findViewById(R.id.msg);
                                msg.setText("Failed to fetch data!");
                            }
                        });

                    }

                }

                return null;
            }

            @Override
            protected void onPostExecute (Void aVoid){
                super.onPostExecute(aVoid);


            }
        }

    }
