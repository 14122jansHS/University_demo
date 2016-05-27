package com.egnify.University_demo.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.egnify.University_demo.Adapter.student_item_adapter;
import com.egnify.University_demo.R;
import com.egnify.University_demo.model.app_url;
import com.egnify.University_demo.pojos.new_stu_info;
import com.egnify.University_demo.utils.p_MyCustomTextView_bold;
import com.egnify.University_demo.utils.p_MyCustomTextView_regular;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class new_student extends AppCompatActivity {
    private static String[] data = new String[]{
            "Mid Marks", "Attendance", "Final Marks", "Fee Details"
    };
    private static String[] ids = new String[]{
            "test_wise", "performance_wise", "class_wise", "school_wise"
    };
    ArrayList<new_stu_info> stu_info_arr = new ArrayList<new_stu_info>();
    private Picasso picasso;
    private JSONObject pre_sem_details;
    private RecyclerView rv_menu;
    private p_MyCustomTextView_regular stu_id;
    private p_MyCustomTextView_regular course;
    private ImageView back_icon;
    private JSONObject previous_sem_detials;
    private ImageView img_bell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_student);
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        back_icon = (ImageView) findViewById(R.id.back_icon);
        img_bell = (ImageView) findViewById(R.id.img_bell);
        rv_menu = (RecyclerView) findViewById(R.id.rv_menu);
        rv_menu.setLayoutManager(new LinearLayoutManager(this));
        picasso = Picasso.with(this);
        picasso.setLoggingEnabled(true);
        picasso.setIndicatorsEnabled(false);
        Intent intent = getIntent();
        String id = intent.getStringExtra("stu_id");
        String name_s = intent.getStringExtra("stu_name");
        CircularImageView prof_pic = (CircularImageView) findViewById(R.id.prof_pic);
        p_MyCustomTextView_bold name = (p_MyCustomTextView_bold) findViewById(R.id.name);
        stu_id = (p_MyCustomTextView_regular) findViewById(R.id.stu_id);
        course = (p_MyCustomTextView_regular) findViewById(R.id.course);
        //set details
        name.setText(name_s);
        String url = app_url.URL_IMAGES + "stu_" + id + ".jpeg";
        picasso.load(url)
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(prof_pic);
        new parse_json().execute();
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        img_bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new_student.this, Feeds.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.stay);
            }
        });
    }

    public String loadJSONFromAsset(String state) {
        String json = null;
        try {
            InputStream is = getAssets().open(state + ".json");
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // hidden_panel.setVisibility(View.GONE);
       /* setui(info_layout, (int) getResources().getDimension(R.dimen.info_prof), (int) getResources().getDimension(R.dimen.card_height));
        setui(attend, (int) getResources().getDimension(R.dimen.attend), (int) getResources().getDimension(R.dimen.card_height));
        setui(mid_mrks, (int) getResources().getDimension(R.dimen.mid_mrks), (int) getResources().getDimension(R.dimen.card_height));
        setui(final_mrks, (int) getResources().getDimension(R.dimen.final_mrks), (int) getResources().getDimension(R.dimen.card_height));
        setui(fee_details, (int) getResources().getDimension(R.dimen.fee_details), (int) getResources().getDimension(R.dimen.card_height));
*/
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

    }

    public class parse_json extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                JSONObject obj = new JSONObject(loadJSONFromAsset("stu"));
                new_stu_info stu_info = new new_stu_info();
                stu_info.setStd_id(obj.getString("std_id"));
                stu_info.setStudent_name(obj.getString("student_name"));
                stu_info.setCourse(obj.getString("Course"));
                stu_info.setBatch(obj.getString("Batch"));
                stu_info.setPresent_year(obj.getString("present_year"));
                stu_info.setPresent_sem(obj.getString("present_sem"));
                stu_info.setPhone_number(obj.getString("phone_number"));
                stu_info.setEmail_id(obj.getString("email_id"));
                stu_info_arr.add(stu_info);
                pre_sem_details = obj.getJSONObject("present_sem_detials");
                previous_sem_detials = obj.getJSONObject("previous_sem_detials");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new_stu_info stu_info = stu_info_arr.get(0);
            stu_id.setText(stu_info.getStd_id());
            String course_s = stu_info.getCourse() + " " + stu_info.getPresent_year() + "-" + stu_info.getPresent_sem();
            course.setText(course_s);
            student_item_adapter adapter = new student_item_adapter(new_student.this, pre_sem_details, previous_sem_detials);
            rv_menu.setAdapter(adapter);
        }
    }
}
