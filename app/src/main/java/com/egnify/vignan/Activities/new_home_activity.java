package com.egnify.vignan.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.egnify.vignan.Adapter.home_fac_adapter;
import com.egnify.vignan.Adapter.home_hod_adapter;
import com.egnify.vignan.LoginActivity;
import com.egnify.vignan.Network.AppController;
import com.egnify.vignan.R;
import com.egnify.vignan.helper.SQLiteHandler_emp_info;
import com.egnify.vignan.helper.SessionManager;
import com.egnify.vignan.model.app_url;
import com.egnify.vignan.pojos.attribute_obj;
import com.egnify.vignan.utils.p_MyCustomTextView;
import com.egnify.vignan.utils.p_MyCustomTextView_mbold;
import com.egnify.vignan.utils.p_MyCustomTextView_regular;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.rey.material.app.ThemeManager;
import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;
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
import java.io.Serializable;
import java.util.ArrayList;

public class new_home_activity extends AppCompatActivity {
    String[] pername = {"Teaching", "Conselling", "Research"};
    String[] color_code = {"#FF9E01", "#0269B0", "#39B53C", "#E52116", "#5b128c"};
    Picasso picasso;

    private RecyclerView perf_card_holder;
    private LayoutInflater lf;
    ArrayList<attribute_obj> attri_arr = new ArrayList<>();
    LinearLayout ll_retry;
    private Button retry;
    private String over_p;
    private p_MyCustomTextView_mbold over_per;
    private p_MyCustomTextView_mbold fac_name;
    private String fac_nam;
    private Integer id = 1;
    private RelativeLayout top_bar;
    private ProgressView bar_progress;
    p_MyCustomTextView_regular status_msg;
    private home_fac_adapter adapter;

    private SessionManager session;
    private SQLiteHandler_emp_info db;
    private String click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.init(this, 1, 0, null);
        setContentView(R.layout.content_new_home_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        db = new SQLiteHandler_emp_info(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutUser();
        }
        picasso = Picasso.with(this);
        picasso.setLoggingEnabled(true);
        picasso.setIndicatorsEnabled(false);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
            click=extras.getString("click");
        }
        if(click.equals("1"))
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
        else
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
           // getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        }
        status_msg=(p_MyCustomTextView_regular) findViewById(R.id.update_tx);
        ll_retry = (LinearLayout) findViewById(R.id.ll_retry);
        retry = (Button) findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new get_details().execute();
            }
        });
        //ImageView back_icon = (ImageView) findViewById(R.id.back_icon);
        CircularImageView pro_pic = (CircularImageView) findViewById(R.id.pro_pic);
        fac_name = (p_MyCustomTextView_mbold) findViewById(R.id.fac_name);
        over_per = (p_MyCustomTextView_mbold) findViewById(R.id.ov_p);
        top_bar = (RelativeLayout) findViewById(R.id.top_bar);
        bar_progress = (ProgressView) findViewById(R.id.bar_progress);
       /*back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/
        String url = app_url.URL_IMAGES + "fac_" + (id) + ".jpeg";
        picasso.load(url)
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(pro_pic);

        perf_card_holder = (RecyclerView) findViewById(R.id.perf_card_holder);
        perf_card_holder.setLayoutManager(new LinearLayoutManager(this));
        lf = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        if(click.equals("0")) {
            String fileName = "Faculty" + ".json";
            File root = new File(Environment.getExternalStorageDirectory(), "Vignan");
            File gpxfile = new File(root, fileName);
            if (gpxfile.exists()) {
               // Toast.makeText(new_home_activity.this, "File Exist", Toast.LENGTH_SHORT).show();
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
                //Toast.makeText(new_home_activity.this, "File Not Exist", Toast.LENGTH_SHORT).show();
                new get_details().execute();
            }
        }
        else
        {
            new get_details().execute();
        }

    }
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
    public String loadJSONFromAsset(String filename) {
        String json = null;
        String ret=null;
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
    public class get_details extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            top_bar.setVisibility(View.VISIBLE);
            status_msg.setText("Data Updating.....");
            bar_progress.start();
            ll_retry.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url_fac = app_url.URL_GET_MAIN + id;
            Log.d("url_fac", url_fac);
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(url_fac, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonobject) {
                    int d = Log.d("perf_cards", jsonobject.toString());
                    parse_json(jsonobject);
                    String fileName = "Faculty" + ".json";//like 2016_01_12.txt
                    try {
                        //File root = new File(Environment.getExternalStorageDirectory()+File.separator+"Music_Folder", "Report Files");
                        File root = new File(Environment.getExternalStorageDirectory(), "Vignan");
                        if (!root.exists()) {
                            root.mkdirs();
                        }
                        File gpxfile = new File(root, fileName);
                        if (gpxfile.exists()) {
                            gpxfile.delete();
                        }
                        FileWriter writer = new FileWriter(gpxfile, true);
                        writer.append(jsonobject.toString());
                        writer.flush();
                        writer.close();
                        //Toast.makeText(new_home_activity.this, "Data has been written to Report File", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error", "parsing error");
                    error.printStackTrace();
                    status_msg.setText("No Internet Connection");
                   //perf_card_holder.setVisibility(View.GONE);
                    //ll_retry.setVisibility(View.VISIBLE);


                }
            });
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(jsonObjReq);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


        }
    }

    private void parse_json(JSONObject jsonobject) {

        if(attri_arr.size()>0)
        {
        attri_arr.clear();
            adapter.notifyDataSetChanged();
        }

        try {
            int scs = jsonobject.getInt("success");
            if (scs == 1) {
                over_p = jsonobject.getString("faculty_score");
                fac_nam = jsonobject.getString("teacher_name");
                over_per.setText(over_p);
                fac_name.setText(fac_nam);
                JSONArray attribute = jsonobject.getJSONArray("attribute");
                for (int i = 0; i < attribute.length(); i++) {
                    JSONObject jobj1 = attribute.getJSONObject(i);
                    attribute_obj atriss = new attribute_obj();
                    atriss.setName(jobj1.getString("name"));
                    atriss.setFinal_points(jobj1.getString("final_points"));
                    atriss.setMetrics(jobj1.getJSONArray("metric").toString());
                    attri_arr.add(atriss);
                }
            } else {

            }
            set_ui();

        } catch (Exception e) {
            if (e.getMessage() != null) {
                //Toast.makeText(new_home_activity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
                Log.e("Error", "parsing error");
                e.printStackTrace();
                ll_retry.setVisibility(View.VISIBLE);
                p_MyCustomTextView msg = (p_MyCustomTextView) findViewById(R.id.msg);
                msg.setText("Failed to fetch data!");
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fac_details_hod, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                //onBackPressed();
                logoutUser();
                break;
            case android.R.id.home:
                onBackPressed();
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
    public void set_ui() {
        
        ll_retry.setVisibility(View.GONE);
        perf_card_holder.setVisibility(View.VISIBLE);
        adapter = new home_fac_adapter(new_home_activity.this, attri_arr, pername);
        perf_card_holder.setAdapter(adapter);
        top_bar.setVisibility(View.GONE);
        Toast.makeText(new_home_activity.this, "UI Updated", Toast.LENGTH_SHORT).show();
    }
}
