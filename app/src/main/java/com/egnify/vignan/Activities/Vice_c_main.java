package com.egnify.vignan.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.egnify.vignan.LoginActivity;
import com.egnify.vignan.R;
import com.egnify.vignan.helper.SQLiteHandler_emp_info;
import com.egnify.vignan.helper.SessionManager;
import com.egnify.vignan.model.app_url;
import com.egnify.vignan.utils.p_MyCustomTextView;
import com.egnify.vignan.utils.p_MyCustomTextView_bold;
import com.egnify.vignan.utils.p_MyCustomTextView_mbold;
import com.egnify.vignan.utils.p_MyCustomTextView_regular;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class Vice_c_main extends AppCompatActivity {
    ArrayList<HashMap<String, String>> allot = new ArrayList<>();
    private Picasso picasso;

    private SessionManager session;
    private SQLiteHandler_emp_info db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vice_c_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        picasso = Picasso.with(this);
        picasso.setLoggingEnabled(true);
        picasso.setIndicatorsEnabled(false);
        db = new SQLiteHandler_emp_info(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutUser();
        }
        HashMap<String, String> dept1 = new HashMap<>();
        dept1.put("name", "C.Srinivasa Kumar");
        dept1.put("dept", "CSE Department");
        dept1.put("total", "1543");
        dept1.put("alloted", "339");
        dept1.put("counselling", "245");
        dept1.put("research", "934");
        allot.add(dept1);
        HashMap<String, String> dept2 = new HashMap<>();
        dept2.put("name", "P.Padma Rao");
        dept2.put("dept", "ECE Department");
        dept2.put("total", "1810");
        dept2.put("alloted", "503");
        dept2.put("counselling", "235");
        dept2.put("research", "947");

        allot.add(dept2);
        HashMap<String, String> dept3 = new HashMap<>();
        dept3.put("name", "Avireni Srinivasulu");
        dept3.put("dept", "EEE Department");
        dept3.put("total", "1360");
        dept3.put("alloted", "700");
        dept3.put("counselling", "225");
        dept3.put("research", "530");
        allot.add(dept3);
        LinearLayout main_ll = (LinearLayout) findViewById(R.id.main_ll);
        LayoutInflater lf = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < allot.size(); i++)
        {
            View view = lf.inflate(R.layout.dept_card, null);
            final HashMap<String, String> details_map = allot.get(i);
            RelativeLayout main_click=(RelativeLayout) view.findViewById(R.id.main_click);
            CircularImageView hod_pic = (CircularImageView) view.findViewById(R.id.hod_pic);
            p_MyCustomTextView_regular dept_name = (p_MyCustomTextView_regular) view.findViewById(R.id.dept_name);
            p_MyCustomTextView hod_id = (p_MyCustomTextView) view.findViewById(R.id.hod_id);
            p_MyCustomTextView_bold points = (p_MyCustomTextView_bold) view.findViewById(R.id.points);
            p_MyCustomTextView_mbold alloted = (p_MyCustomTextView_mbold) view.findViewById(R.id.alloted);
            p_MyCustomTextView_mbold counselling = (p_MyCustomTextView_mbold) view.findViewById(R.id.counselling);
            p_MyCustomTextView_mbold research = (p_MyCustomTextView_mbold) view.findViewById(R.id.research);

            RelativeLayout circ_bg = (RelativeLayout) view.findViewById(R.id.circ_bg);
            View line = view.findViewById(R.id.line_bg);
            RelativeLayout banner_bg = (RelativeLayout) view.findViewById(R.id.banner_bg);
            if (i == 1) {
                circ_bg.setBackground(getResources().getDrawable(R.drawable.circle_second));
                line.setBackgroundColor(getResources().getColor(R.color.second_bg));
                banner_bg.setBackground(getResources().getDrawable(R.drawable.banner_bg1));

            } else if (i == 2) {
                circ_bg.setBackground(getResources().getDrawable(R.drawable.circle_third));
                line.setBackgroundColor(getResources().getColor(R.color.third_bg));
                banner_bg.setBackground(getResources().getDrawable(R.drawable.banner_bg3));
            }
            dept_name.setText(details_map.get("dept"));
            hod_id.setText("HOD: " + details_map.get("name"));
            points.setText(details_map.get("total"));
            alloted.setText(details_map.get("alloted"));
            counselling.setText(details_map.get("counselling"));
            research.setText(details_map.get("research"));
            String url = app_url.URL_IMAGES + "hod_" + (i + 1) + ".jpeg";
            picasso.load(url)
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.place_holder)
                    .into(hod_pic);
            final int final_i=i+1;
            main_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Vice_c_main.this, vc_dept_view.class);
                    i.putExtra("name",details_map.get("name"));
                    i.putExtra("dept_name",details_map.get("dept"));
                    i.putExtra("id",final_i);
                    startActivity(i);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                }


            });
            main_ll.addView(view);
        }
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

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
