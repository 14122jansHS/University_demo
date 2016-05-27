package com.egnify.University_demo.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.egnify.University_demo.Adapter.home_hod_adapter;
import com.egnify.University_demo.LoginActivity;
import com.egnify.University_demo.Network.AppController;
import com.egnify.University_demo.R;
import com.egnify.University_demo.helper.SQLiteHandler_emp_info;
import com.egnify.University_demo.helper.SessionManager;
import com.egnify.University_demo.model.app_url;
import com.egnify.University_demo.pojos.attribute_obj;
import com.egnify.University_demo.pojos.fac_info;
import com.egnify.University_demo.utils.p_MyCustomTextView;
import com.egnify.University_demo.utils.p_MyCustomTextView_mbold;
import com.egnify.University_demo.utils.p_MyCustomTextView_regular;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.rey.material.app.ThemeManager;
import com.rey.material.widget.Button;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class new_home_hod extends AppCompatActivity {
    String[] pername = {"Teaching", "Conselling", "Research"};
    String[] color_code = {"#FF9E01", "#0269B0", "#39B53C", "#E52116", "#5b128c"};
    Picasso picasso;
    ArrayList<ArrayList<attribute_obj>> attri_arr = new ArrayList<>();
    ArrayList<fac_info> fact_info = new ArrayList<>();
    LinearLayout ll_retry;
    //private ProgressView bar_progress;
    p_MyCustomTextView_regular status_msg;
    private RecyclerView perf_card_holder;
    private LayoutInflater lf;
    private Button retry;
    private String over_p;
    private p_MyCustomTextView_mbold over_per;
    private p_MyCustomTextView_mbold fac_name;
    private String fac_nam;
    private Integer id;
    private home_hod_adapter adapter;
    private RelativeLayout top_bar;
    private SQLiteHandler_emp_info db;
    private SessionManager session;
    private String click;
    private RelativeLayout dummy_layout;

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.init(this, 1, 0, null);
        setContentView(R.layout.content_new_home_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new SQLiteHandler_emp_info(getApplicationContext());

        picasso = Picasso.with(this);
        picasso.setLoggingEnabled(true);
        picasso.setIndicatorsEnabled(false);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
            click = extras.getString("click");
        }
        DisplayMetrics metrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        DecimalFormat df = new DecimalFormat("##");
        float dp_h = (metrics.heightPixels * 160) / metrics.ydpi;
        int imgWidth = Integer.parseInt(df.format(dp_h * 0.50));

        dummy_layout = (RelativeLayout) findViewById(R.id.dummy_layout);
        dummy_layout.getLayoutParams().height = imgWidth;
        status_msg = (p_MyCustomTextView_regular) findViewById(R.id.update_tx);
        ll_retry = (LinearLayout) findViewById(R.id.ll_retry);
        retry = (Button) findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new get_details().execute();
            }
        });
        // ImageView back_icon = (ImageView) findViewById(R.id.back_icon);
        CircularImageView pro_pic = (CircularImageView) findViewById(R.id.pro_pic);
        fac_name = (p_MyCustomTextView_mbold) findViewById(R.id.fac_name);
        over_per = (p_MyCustomTextView_mbold) findViewById(R.id.ov_p);
        top_bar = (RelativeLayout) findViewById(R.id.top_bar);
        // bar_progress = (ProgressView) findViewById(R.id.bar_progress);
       /* back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/
        String url = app_url.URL_IMAGES + "hod_" + 1 + ".jpeg";
        picasso.load(url)
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(pro_pic);
        perf_card_holder = (RecyclerView) findViewById(R.id.perf_card_holder);
        perf_card_holder.setLayoutManager(new LinearLayoutManager(this));
        p_MyCustomTextView_regular designation = (p_MyCustomTextView_regular) findViewById(R.id.designation);
        designation.setText("HOD:CSE");
        lf = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        //  if (click.equals("0")) {
            String fileName = "Hod" + ".json";
            File root = new File(Environment.getExternalStorageDirectory(), "Vignan");
            File gpxfile = new File(root, fileName);
            if (gpxfile.exists()) {
             //   Toast.makeText(new_home_hod.this, "File Exist", Toast.LENGTH_SHORT).show();
                String jobj = loadJSONFromAsset(fileName);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(jobj);
                    ll_retry.setVisibility(View.GONE);
                    parse_json(jsonObject);
                    new get_details().execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
               // Toast.makeText(new_home_hod.this, "File Not Exist", Toast.LENGTH_SHORT).show();
                new get_details().execute();
            }
      /*  } else {
            new get_details().execute();
        }*/


    }

    public String loadJSONFromAsset(String filename) {
        String json = null;
        String ret = null;
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Vignan");
            File gpxfile = new File(root, filename);
            FileInputStream fin = new FileInputStream(gpxfile);
            ret = convertStreamToString(fin);
            // json = new String(ret, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private void parse_json(JSONObject jsonobject) {
        ll_retry.setVisibility(View.GONE);
        top_bar.setVisibility(View.GONE);
        perf_card_holder.setVisibility(View.VISIBLE);
        attri_arr.clear();
        fact_info.clear();
        try {
            int scs = jsonobject.getInt("success");
            if (scs == 1) {
                over_p = jsonobject.getString("dept_score");
                fac_nam = "C.Srinivasa Kumar";
                over_per.setText(over_p);
                fac_name.setText(fac_nam);
                // fac_name.setText(fac_nam);
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

            }
            set_ui();

        } catch (Exception e) {
            if (e.getMessage() != null) {
                //Toast.makeText(new_home_activity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
                Log.e("Error", "parsing error");
                e.printStackTrace();
               // ll_retry.setVisibility(View.VISIBLE);
                p_MyCustomTextView msg = (p_MyCustomTextView) findViewById(R.id.msg);
                msg.setText("Failed to fetch data!");
                //perf_card_holder.setVisibility(View.GONE);
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bell, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.m_bell:
                Intent intent = new Intent(new_home_hod.this, Feeds.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.stay);
                break;

        }
        return true;
    }

    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    public void set_ui() {
        ll_retry.setVisibility(View.GONE);

        // top_bar.setVisibility(View.GONE);
        int allotedclasses = 0;
        int counsellingstudents = 0;
        int ResearchatFacultyLevel = 0;
        int ResearchatHODlevel = 0;
        int OtherProfessionalActivites = 0;
        final ArrayList<HashMap<String, String>> f_list = new ArrayList<>();
        for (int f = 0; f < attri_arr.size(); f++) {
            fac_info fac_pojo = fact_info.get(f);
            HashMap<String, String> list_map = new HashMap<>();
            ArrayList<attribute_obj> atriss = attri_arr.get(f);
            list_map.put("fac_name", fac_pojo.getTeacher_name());
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
        int final_val = ResearchatFacultyLevel + ResearchatHODlevel + OtherProfessionalActivites;
        adapter = new home_hod_adapter(new_home_hod.this, String.valueOf(allotedclasses), String.valueOf(counsellingstudents), String.valueOf(final_val), pername, f_list);
        perf_card_holder.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        top_bar.setVisibility(View.GONE);
        //  Toast.makeText(new_home_hod.this, "UI Updated", Toast.LENGTH_SHORT).show();

    }

    public class get_details extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            top_bar.setVisibility(View.VISIBLE);
            // bar_progress.start();
            ll_retry.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url_hod = app_url.URL_DEPT + id;
            Log.d("url_hod", url_hod);
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(url_hod, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonobject) {

                    int d = Log.d("perf_cards", jsonobject.toString());
                    parse_json(jsonobject);
                    String fileName = "Hod" + ".json";//like 2016_01_12.txt
                    try {
                        //File root = new File(Environment.getExternalStorageDirectory()+File.separator+"Music_Folder", "Report Files");
                        File root = new File(Environment.getExternalStorageDirectory(), "Vignan");
                        if (!root.exists()) {
                            root.mkdirs();
                        }
                        File gpxfile = new File(root, fileName);
                        if (!gpxfile.exists()) {
                            gpxfile.delete();
                        }
                        FileWriter writer = new FileWriter(gpxfile, true);

                        writer.append(jsonobject.toString());
                        writer.flush();
                        writer.close();
                        //   Toast.makeText(new_home_hod.this, "Data has been written to Report File", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();

                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    //bar_progress.stop();
                    top_bar.setVisibility(View.VISIBLE);
                    status_msg.setText("No Internet Connection");
                    // ll_retry.setVisibility(View.VISIBLE);


                }
            });
            AppController.getInstance().addToRequestQueue(jsonObjReq);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


        }
    }
}
