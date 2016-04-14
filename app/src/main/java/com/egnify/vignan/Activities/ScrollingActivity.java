package com.egnify.vignan.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.egnify.vignan.R;
import com.egnify.vignan.utils.p_MyCustomTextView_mbold;

public class ScrollingActivity extends AppCompatActivity {
String[] pername={"Allocated Class","Conselling Students", "Research Contribution at Faculty Level","Research Contribution at Faculty Level","Proffessional Activites"};
    String[] color_code={"#FF9E01","#0269B0", "#39B53C","#E52116","#5b128c"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_left));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitleEnabled(false);
        // collapsingToolbarLayout.setTitle("P.Srinivas");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    getSupportActionBar().setTitle("P.Srinivas");
                    isShow = true;
                } else if (isShow) {
                    getSupportActionBar().setTitle("");
                    isShow = false;
                }
            }
        });
        final ImageView header = (ImageView) findViewById(R.id.htab_header);
        final Animation zoomin = AnimationUtils.loadAnimation(this, R.anim.zoomin);
        final Animation zoomout = AnimationUtils.loadAnimation(this, R.anim.zoomout);
        // zoomout.setDuration(50000);
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
        });
        LinearLayout perf_card_holder=(LinearLayout) findViewById(R.id.perf_card_holder);
        LayoutInflater lf=(LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        for(int i=0;i<pername.length;i++)
        {
            View view=lf.inflate(R.layout.overview_card,null);
            RelativeLayout bg_col=(RelativeLayout) view.findViewById(R.id.bg_col);
            View line_col=view.findViewById(R.id.b_line);
            p_MyCustomTextView_mbold perf_name=(p_MyCustomTextView_mbold) view.findViewById(R.id.perf_name);
            String col=color_code[i];
            line_col.setBackgroundColor(Color.parseColor(col));
            bg_col.setBackgroundColor(Color.parseColor(col));
            perf_name.setText(pername[i]);
            perf_card_holder.addView(view);
        }

    }
}
