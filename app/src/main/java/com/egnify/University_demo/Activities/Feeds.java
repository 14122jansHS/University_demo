package com.egnify.University_demo.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.egnify.University_demo.Adapter.CatNamesRecyclerViewAdapter;
import com.egnify.University_demo.Network.AppController;
import com.egnify.University_demo.R;
import com.egnify.University_demo.gcm.GCMIntentService;
import com.egnify.University_demo.helper.SQLiteHandler;
import com.egnify.University_demo.pojos.FeedItem;
import com.egnify.University_demo.utils.Config;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Feeds extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private String TAG = Feeds.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private CatNamesRecyclerViewAdapter mCatNamesRecyclerViewAdapter;

    private List<FeedItem> feedItems = new ArrayList<>();
    //private String URL_FEED = "http://api.androidhive.info/feed/feed.json";
    private String URL_FEED = "http://egnify.com/vignan/connect/feeds/";
    private SQLiteHandler db;
    private String user_id;
    private ImageView img_close;

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

    public void forceCrash() {
        throw new RuntimeException("This is a crash");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("E-Notice Board");
        // toolbar.setTitleTextColor(getResources().getColor(R.color.primary_text));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeI
        // getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        user_id = "44";
        URL_FEED += user_id;
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerview);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        String fileName = "Feeds" + ".json";
        File root = new File(Environment.getExternalStorageDirectory(), "Vignan");
        File gpxfile = new File(root, fileName);
        if (gpxfile.exists()) {
            // Toast.makeText(Feeds.this, "File Exist", Toast.LENGTH_SHORT).show();
            String jobj = loadJSONFromAsset(fileName);
            Log.e("feeds_file", jobj);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jobj);
                parseJsonFeed(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            load_data();
        } else {
            Toast.makeText(Feeds.this, "File Not Exist", Toast.LENGTH_SHORT).show();
            load_data();
        }
        // We first check for cached request
       /* Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_FEED);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
        // making fresh volley request and getting json
        load_data();
       // }*/

        // Adding request to volley request queue


        // setupAdapter();

        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        // mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        load_data();
                        //setupAdapter();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2500);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Feeds.this, Submit.class);
                startActivity(intent);
            }
        });

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    String token = intent.getStringExtra("token");

                    //Toast.makeText(getApplicationContext(), "GCM registration token: " + token, Toast.LENGTH_LONG).show();

                } else if (intent.getAction().equals(Config.SENT_TOKEN_TO_SERVER)) {
                    // gcm registration id is stored in our server's MySQL

                    Toast.makeText(getApplicationContext(), "GCM registration token is stored in server!", Toast.LENGTH_LONG).show();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    Toast.makeText(getApplicationContext(), "Push notification is received!", Toast.LENGTH_LONG).show();
                }
            }
        };

        if (checkPlayServices()) {
            registerGCM();
        }
    }

    private void load_data() {
        Log.e("Entered Load_data", "TRUE");

        // making fresh volley request and getting json
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                URL_FEED, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, "Response: " + response.toString());
                if (response != null) {
                    String fileName = "Feeds" + ".json";//like 2016_01_12.txt
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

                        writer.append(response.toString());
                        writer.flush();
                        writer.close();
                        //   Toast.makeText(new_home_hod.this, "Data has been written to Report File", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                    parseJsonFeed(response);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(jsonReq);
    }

    // starting the service to register with GCM
    private void registerGCM() {
        Intent intent = new Intent(this, GCMIntentService.class);
        intent.putExtra("key", "register");
        startService(intent);
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported. Google Play Services not installed!");
                Toast.makeText(getApplicationContext(), "This device is not supported. Google Play Services not installed!", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                db.deleteUsers();
                AppController.getInstance().logout();
                break;
            case android.R.id.home:
                onBackPressed();

                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void setupAdapter() {
        Log.e("Adapter", "True");

        mCatNamesRecyclerViewAdapter = new CatNamesRecyclerViewAdapter(this, feedItems);
        mRecyclerView.setAdapter(mCatNamesRecyclerViewAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     */
    private void parseJsonFeed(JSONObject response) {
        feedItems.clear();
        Log.e("parseJsonFeed", "True");
        try {
            JSONArray feedArray = response.getJSONArray("feeds");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();
                item.setId(feedObj.getInt("fromid"));
                item.setName(feedObj.getString("fromname"));
                item.setCateg(feedObj.getString("category"));
                // Image might be null sometimes
                String image = feedObj.isNull("attachment") ? null : feedObj
                        .getString("attachment");
                item.setImge(image);
                item.setTitle(feedObj.getString("title"));
                item.setMsg(feedObj.getString("message"));
                item.setTimeStamp(feedObj.getString("date"));

                // url might be null sometimes
                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
                item.setUrl(feedUrl);

                feedItems.add(item);
            }

            // notify data changes to list adapater
            setupAdapter();
            //  mCatNamesRecyclerViewAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.slide_out);
    }


    public String loadJSONFromAsset(String filename) {
        Log.e("loadJSONFromAsset", "True");
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
}
