package com.egnify.University_demo.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.egnify.University_demo.Adapter.stu_final_mrks_adapter;
import com.egnify.University_demo.R;
import com.egnify.University_demo.pojos.attribute_obj;
import com.egnify.University_demo.pojos.fnal_mrks_info;
import com.egnify.University_demo.utils.p_MyCustomTextView_bold;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class stu_final_marks extends AppCompatActivity {

    ArrayList<HashMap<String, String>> sub_arr = new ArrayList<>();
    ArrayList<ArrayList<fnal_mrks_info>> mid_sub_arr = new ArrayList<>();
    private ArrayList<attribute_obj> filelist;
    private String previous_sem_detials;
    private p_MyCustomTextView_bold total_m;
    private p_MyCustomTextView_bold present_m;
    private p_MyCustomTextView_bold absent_m;
    private p_MyCustomTextView_bold s_per_m;
    private ImageView back_icon;
    private int working_subjects;
    private int passed_subs;
    private String a_overall_;
    private JSONObject previous_sem;
    private RecyclerView rv_f_meks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_final_marks);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            previous_sem_detials = extras.getString("previous_sem_detials");
        }
        rv_f_meks = (RecyclerView) findViewById(R.id.rv_f_meks);
        rv_f_meks.setLayoutManager(new LinearLayoutManager(this));
        total_m = (p_MyCustomTextView_bold) findViewById(R.id.total_m);
        present_m = (p_MyCustomTextView_bold) findViewById(R.id.present_m);
        absent_m = (p_MyCustomTextView_bold) findViewById(R.id.absent_m);
        s_per_m = (p_MyCustomTextView_bold) findViewById(R.id.s_per_m);
        back_icon = (ImageView) findViewById(R.id.back_icon);
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
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    public class parse_json extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            try {
                JSONObject obj = new JSONObject(previous_sem_detials);
                int success = obj.getInt("success");
                if (success == 1) {

                    working_subjects = obj.getInt("Total_subjects");
                    passed_subs = obj.getInt("passed_subjects");
                    a_overall_ = obj.getString("pass_percentage");
                    JSONArray arr = obj.getJSONArray("previous_sem");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject Sub_details = arr.getJSONObject(i);
                        HashMap<String, String> del_map = new HashMap<>();
                        del_map.put("pass_percentage", Sub_details.getString("pass_percentage"));
                        del_map.put("sem_name", Sub_details.getString("sem_name"));
                        sub_arr.add(del_map);
                        JSONArray mid_arrr = Sub_details.getJSONArray("subjects");
                        ArrayList<fnal_mrks_info> l_mid_arr = new ArrayList<>();
                        for (int j = 0; j < mid_arrr.length(); j++) {
                            JSONObject jobj = mid_arrr.getJSONObject(j);
                            fnal_mrks_info mid_pojo = new fnal_mrks_info();
                            mid_pojo.setSubject_name(jobj.getString("subject_name"));
                            mid_pojo.setShort_name(jobj.getString("short_name"));
                            mid_pojo.setFaculty_name(jobj.getString("Faculty_name"));
                            mid_pojo.setFaculty_id(jobj.getString("Faculty_id"));
                            mid_pojo.setInternal_marks(jobj.getString("Internal_marks"));
                            mid_pojo.setExternal_marks(jobj.getString("External_marks"));
                            mid_pojo.setStatus(jobj.getString("status"));
                            mid_pojo.setPass_yr(jobj.getString("pass_yr"));
                            l_mid_arr.add(mid_pojo);

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
            total_m.setText(String.valueOf(working_subjects));
            present_m.setText(String.valueOf(passed_subs));
            int abs = working_subjects - passed_subs;
            absent_m.setText(String.valueOf(abs));

            s_per_m.setText(String.valueOf(a_overall_));

            Double a_overall_per = Double.parseDouble(a_overall_);
            if (a_overall_per > 75) {
                s_per_m.setTextColor(getResources().getColor(R.color.a2));
            } else if (a_overall_per >= 65) {
                s_per_m.setTextColor(getResources().getColor(R.color.b1));
            } else {
                s_per_m.setTextColor(getResources().getColor(R.color.red));
            }
            stu_final_mrks_adapter adapter = new stu_final_mrks_adapter(stu_final_marks.this, sub_arr, mid_sub_arr);
            rv_f_meks.setAdapter(adapter);

        }
    }

}
