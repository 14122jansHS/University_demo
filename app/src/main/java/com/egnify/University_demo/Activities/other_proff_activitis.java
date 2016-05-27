package com.egnify.University_demo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.egnify.University_demo.R;
import com.egnify.University_demo.pojos.attribute_obj;
import com.egnify.University_demo.pojos.resea;
import com.egnify.University_demo.utils.p_MyCustomTextView_regular;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class other_proff_activitis extends AppCompatActivity {

    ArrayList<resea> l_sub_topics = new ArrayList<>();
    List<String> list = new ArrayList<>();
    ArrayList<HashMap<String, String>> final_arr = new ArrayList<>();
    ArrayList<String> final_ids = new ArrayList<>();
    private ArrayList<attribute_obj> filelist;
    private LayoutInflater lf;
    private LinearLayout main_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_proff_activitis);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lf=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        main_layout=(LinearLayout) findViewById(R.id.main_layout);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        filelist= (ArrayList<attribute_obj>)bundle.getSerializable("FILES_TO_SEND");
        p_MyCustomTextView_regular over_p=(p_MyCustomTextView_regular) findViewById(R.id.over_p);
        attribute_obj l_obj=filelist.get(4);
        try {
            String over_all=l_obj.getFinal_points();
            String get_text=over_p.getText().toString();
            over_p.setText(get_text+" "+over_all);
            JSONArray arr_sub_topics = new JSONArray(l_obj.getMetrics());
            for (int j = 0; j < arr_sub_topics.length(); j++) {
                JSONObject object_sub_topics = arr_sub_topics.getJSONObject(j);
                resea rse = new resea();
                rse.setSub_topic_id(object_sub_topics.getString("sub_topic_id"));
                rse.setSub_topic_name(object_sub_topics.getString("sub_topic_name"));
                rse.setScore(object_sub_topics.getString("score"));
                list.add(object_sub_topics.getString("sub_topic_id"));
                l_sub_topics.add(rse);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

            for (int l = 0; l < list.size(); l++) {
                resea l_sub_pojo = l_sub_topics.get(l);
                HashMap<String, String> f_s_topics = new HashMap<>();
                String id = list.get(l);
                if (!final_ids.contains(id)) {
                    final_ids.add(id);
                    int count = Collections.frequency(list, id);
                    f_s_topics.put("count", String.valueOf(count));
                    f_s_topics.put("Sub_topic_name", l_sub_pojo.getSub_topic_name());
                    f_s_topics.put("Sub_topic_id", l_sub_pojo.getSub_topic_id());
                    f_s_topics.put("Score", l_sub_pojo.getScore());
                    final_arr.add(f_s_topics);
                }
            }
        new set_ui().execute();
    }

    private void set_ui() {


            View sub_point_l=lf.inflate(R.layout.sub_point,null);
            p_MyCustomTextView_regular met_name=(p_MyCustomTextView_regular)sub_point_l.findViewById(R.id.met_name);
            met_name.setText("OTHER PROFESSIONAL ACTIVITES");
            LinearLayout a_row_holder=(LinearLayout)sub_point_l.findViewById(R.id.a_row_holder);

            for (int j=0;j<final_arr.size();j++)
            {
                HashMap<String,String> finalh_map =final_arr.get(j);
                View sub_row=lf.inflate(R.layout.sub_point_row, null);
                p_MyCustomTextView_regular stu_name=(p_MyCustomTextView_regular) sub_row.findViewById(R.id.stu_name);
                p_MyCustomTextView_regular count=(p_MyCustomTextView_regular) sub_row.findViewById(R.id.count);
                p_MyCustomTextView_regular marks=(p_MyCustomTextView_regular) sub_row.findViewById(R.id.marks);
                stu_name.setText(finalh_map.get("Sub_topic_name"));
                count.setText(finalh_map.get("count"));
                marks.setText(finalh_map.get("Score"));
                a_row_holder.addView(sub_row);
            }
            main_layout.addView(sub_point_l);

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

    public class set_ui extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    set_ui();
                }
            });

            return null;
        }
    }

}
