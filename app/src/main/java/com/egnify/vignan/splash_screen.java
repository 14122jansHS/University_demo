package com.egnify.vignan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.egnify.vignan.Activities.ScrollingActivity;
import com.egnify.vignan.Activities.Vice_c_main;
import com.egnify.vignan.Activities.new_home_activity;
import com.egnify.vignan.Activities.new_home_hod;
import com.egnify.vignan.Activities.sample_card;
import com.egnify.vignan.model.app_url;
import com.egnify.vignan.utils.p_MyCustomTextView_mbold;
import com.egnify.vignan.utils.p_MyCustomTextView_regular;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class splash_screen extends AppCompatActivity {

    private Animation moveToCenterAnim;
    private Animation moveToCenter;
    String[] names = {"Vice Chairman", "Head Of Department", "Faculty"};
    String[] color_code = {"#0269B0", "#39B53C", "#FF9E01"};
    Picasso picasso;
    private Animation RightSwipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        picasso = Picasso.with(this);
        picasso.setLoggingEnabled(true);
        picasso.setIndicatorsEnabled(false);

        final LinearLayout main_ll = (LinearLayout) findViewById(R.id.main_ll);

        moveToCenterAnim = AnimationUtils.loadAnimation(splash_screen.this, R.anim.translate1);

        moveToCenterAnim.setAnimationListener(new Animation.AnimationListener() {


            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                LayoutInflater lf = (LayoutInflater) splash_screen.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                for (int i = 0; i < names.length; i++) {
                    View view = lf.inflate(R.layout.user_card, null);
                    RelativeLayout bg_col = (RelativeLayout) view.findViewById(R.id.card_view);
                    CircularImageView profile_pic = (CircularImageView) view.findViewById(R.id.profile_pic);
                    p_MyCustomTextView_regular name = (p_MyCustomTextView_regular) view.findViewById(R.id.name);
                    bg_col.setBackgroundColor(Color.parseColor(color_code[i]));
                    //bg_col.setColorFilter(Color.parseColor(color_code[i]));
                   // bg_col.getBackground().setColorFilter(Color.parseColor(color_code[i]), PorterDuff.Mode.SRC_ATOP);
                    name.setText(names[i]);
                    if(names[i].equals("Vice Chairman"))
                    {
                        picasso.load(R.drawable.v_c)
                                .placeholder(R.drawable.place_holder)
                                .error(R.drawable.place_holder)
                                .into(profile_pic);

                    }
                    else if(names[i].equals("Head Of Department"))
                    {
                        String url= app_url.URL_IMAGES+"hod_"+1+".jpeg";
                        picasso.load(url)
                                .placeholder(R.drawable.place_holder)
                                .error(R.drawable.place_holder)
                                .into(profile_pic);
                    }
                    else
                    {
                        String url= app_url.URL_IMAGES+"fac_"+1+".jpeg";
                        picasso.load(url)
                                .placeholder(R.drawable.place_holder)
                                .error(R.drawable.place_holder)
                                .into(profile_pic);

                    }

                    final int finalI = i;
                    bg_col.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (names[finalI].equals("Faculty")) {
                                Intent i = new Intent(splash_screen.this, new_home_activity.class);
                               // Intent i = new Intent(splash_screen.this, new_home_activity.class);
                                i.putExtra("type","3");
                                i.putExtra("id",1);
                                startActivity(i);
                                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

                            } else  if (names[finalI].equals("Head Of Department")) {
                                Intent i = new Intent(splash_screen.this, new_home_hod.class);
                                i.putExtra("type","2");
                                i.putExtra("id",1);
                                startActivity(i);
                                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

                            }
                            else {
                                Intent i = new Intent(splash_screen.this, Vice_c_main.class);
                                i.putExtra("type","1");
                                i.putExtra("id",1);
                                startActivity(i);
                                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

                            }
                        }
                    });

                    main_ll.addView(view);
                    RightSwipe = AnimationUtils.loadAnimation(splash_screen.this, R.anim.pull_in_left);
                    TranslateAnimation animate = new TranslateAnimation(0,view.getWidth(),0,0);
                    animate.setDuration(10);
                    animate.setFillAfter(true);
                    view.startAnimation(RightSwipe);

                }


            }
        });

        ImageView imgLogo = (ImageView) findViewById(R.id.vu_logo);
        imgLogo.startAnimation(moveToCenterAnim);


    }
}
