package com.egnify.University_demo.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.albinmathew.transitions.ActivityTransition;
import com.albinmathew.transitions.ExitActivityTransition;
import com.egnify.University_demo.Adapter.SimpleRecyclerAdapter;
import com.egnify.University_demo.R;
import com.egnify.University_demo.model.VersionModel;

import java.util.ArrayList;
import java.util.List;

public class mid_marks extends AppCompatActivity {
    private static int[] color = new int[]{
            R.color.lred, R.color.lgreen, R.color.blue, R.color.c1
    };
    private CardView card_holder;
    private RelativeLayout bottom_layout;
    private TabLayout tabLayout;
    private RelativeLayout bar_content;
    private ExitActivityTransition exitTransition;
    private int color_id;
    private ImageView back_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mid_marks);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            color_id = extras.getInt("color_id");
        }
        back_icon = (ImageView) findViewById(R.id.back_icon);
        card_holder = (CardView) findViewById(R.id.card_holder);
        bottom_layout = (RelativeLayout) findViewById(R.id.bottom_layout);
        tabLayout = (TabLayout) findViewById(R.id.htab_tabs);
        bar_content = (RelativeLayout) findViewById(R.id.bar_content);
        RelativeLayout testwise = (RelativeLayout) findViewById(R.id.testwise);
        testwise.setBackgroundColor(getResources().getColor(color[color_id]));
        tabLayout.setBackgroundColor(getResources().getColor(color[color_id]));
        //ActivityTransition.with(getIntent()).to(findViewById(R.id.toolbar)).start(savedInstanceState);
        back_icon.postDelayed(new Runnable() {
            public void run() {
                bottom_layout.setVisibility(View.VISIBLE);
                card_holder.setVisibility(View.VISIBLE);
                bar_content.setVisibility(View.VISIBLE);
            }
        }, 1100);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        setupViewPager(viewPager);


        tabLayout.setupWithViewPager(viewPager);
        exitTransition = ActivityTransition.with(getIntent()).to(findViewById(R.id.testwise)).start(savedInstanceState);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new DummyFragment(getResources().getColor(R.color.ripple_material_light)), "Subject Wise");
        adapter.addFrag(new DummyFragment(getResources().getColor(R.color.ripple_material_light)), "Date Wise");
        //adapter.addFrag(new DummyFragment(getResources().getColor(R.color.ripple_material_light)), "Extra Activities");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
//        layout_main.setVisibility(View.INVISIBLE);
        bottom_layout.setVisibility(View.INVISIBLE);
        card_holder.setVisibility(View.INVISIBLE);

        bar_content.setVisibility(View.INVISIBLE);
        exitTransition.exit(this);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    public class DummyFragment extends Fragment {
        int color;
        SimpleRecyclerAdapter adapter;

        public DummyFragment() {
        }

        @SuppressLint("ValidFragment")
        public DummyFragment(int color) {
            this.color = color;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dummy_fragment, container, false);

            final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dummyfrag_bg);
            frameLayout.setBackgroundColor(color);

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);

            List<String> list = new ArrayList<String>();
            for (int i = 0; i < VersionModel.data.length; i++) {
                list.add(VersionModel.data[i]);
            }

            adapter = new SimpleRecyclerAdapter(mid_marks.this, list);
            recyclerView.setAdapter(adapter);

            return view;
        }
    }
}
