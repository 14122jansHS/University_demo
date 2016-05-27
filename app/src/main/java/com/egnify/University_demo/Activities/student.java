package com.egnify.University_demo.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.egnify.University_demo.Adapter.wise_adapter;
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

public class student extends AppCompatActivity {
    private static int[] color = new int[]{
            R.color.lred, R.color.b1, R.color.blue, R.color.c1
    };
    /*private RelativeLayout info_layout;
    private RelativeLayout attend;
    private RelativeLayout mid_mrks;
    private RelativeLayout fee_details;
    private RelativeLayout final_mrks;
    private RelativeLayout hidden_panel;
    private RelativeLayout main_panel;
    private RelativeLayout new_toolbar;*/
    ArrayList<new_stu_info> stu_info_arr = new ArrayList<new_stu_info>();
    private Picasso picasso;
    private JSONObject pre_sem_details;
    private RecyclerView rv_menu;
    private p_MyCustomTextView_regular stu_id;
    private p_MyCustomTextView_regular course;
    private ImageView img_bell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
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
        img_bell = (ImageView) findViewById(R.id.img_bell);
        String url = app_url.URL_IMAGES + "stu_" + id + ".jpeg";
        picasso.load(url)
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(prof_pic);
//Animatio of image
       /* final ImageView header = (ImageView) findViewById(R.id.image_view);
        final Animation zoomin = AnimationUtils.loadAnimation(this, R.anim.zoomin);
        final Animation zoomout = AnimationUtils.loadAnimation(this, R.anim.zoomout);
        header.startAnimation(zoomin);
        zoomin.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                header.startAnimation(zoomout);

            }
        });
        zoomout.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                header.startAnimation(zoomin);

            }
        });*/
        new parse_json().execute();
        img_bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(student.this, Feeds.class);
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

    public void bottom_up_ani(final RelativeLayout rlayout, final int top, final int height) {
        Animation slide = null;
        slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -5.0f);

        slide.setDuration(400);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        rlayout.startAnimation(slide);
        slide.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rlayout.getLayoutParams();
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlayout.getLayoutParams();
                params.topMargin = top;
                params.height = height;
                rlayout.setLayoutParams(params);
                //SlideToDown();
            }

        });
      /*  Animation a = new Animation()
        {

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlayout.getLayoutParams();
                params.topMargin = (int) (top * (1-interpolatedTime));
               // params.height = (int) (height * (interpolatedTime));
                rlayout.setLayoutParams(params);
            }
        };
        a.setDuration(500);
        rlayout.startAnimation(a);*/
    }

    public void setui(final RelativeLayout rlayout, final int top, final int height) {
      /*  Animation a = new Animation() {

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlayout.getLayoutParams();
                params.topMargin = (int) (top * interpolatedTime);
                params.height = (int) (height * interpolatedTime);
                rlayout.setLayoutParams(params);
            }
        };
        a.setDuration(1000);
        rlayout.startAnimation(a);*/
        Animation slide = null;
        slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 5.2f);

        slide.setDuration(400);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        rlayout.startAnimation(slide);

        slide.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

//              rl_footer.clearAnimation();
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) rlayout.getLayoutParams();
                // RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                // rlayout.getWidth(), rlayout.getHeight());
                params.topMargin = top;
                params.height = height;
                rlayout.setLayoutParams(params);
                // SlideToAbove();

            }

        });
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
            wise_adapter adapter = new wise_adapter(student.this, pre_sem_details);
            rv_menu.setAdapter(adapter);
        }
    }
}
