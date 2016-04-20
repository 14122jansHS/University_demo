package com.egnify.vignan.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.albinmathew.transitions.ActivityTransition;
import com.albinmathew.transitions.ExitActivityTransition;
import com.egnify.vignan.Fragments.student_attend_subwise;
import com.egnify.vignan.R;
import com.egnify.vignan.utils.p_MyCustomTextView_bold;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class attendance extends AppCompatActivity {
    private static int[] color = new int[]{
            R.color.lred, R.color.lgreen, R.color.blue, R.color.c1
    };
    String pre_sem_details;
    private ExitActivityTransition exitTransition;
    private int color_id;
    private ImageView back_icon;
    private RelativeLayout layout_main;
    private FrameLayout main_fl;
    private CardView card_holder;
    private RelativeLayout bottom_layout;
    private TabLayout tabLayout;
    private RelativeLayout bar_content;
    private int working_days;
    private int present_days;
    private double a_overall_per;
    private String subjects_arr;
    private ViewPager viewPager;
    private p_MyCustomTextView_bold total_m;
    private p_MyCustomTextView_bold present_m;
    private p_MyCustomTextView_bold absent_m;
    private p_MyCustomTextView_bold s_per_m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_student);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            color_id = extras.getInt("color_id");
            pre_sem_details = extras.getString("pre_sem_details");

        }

        back_icon = (ImageView) findViewById(R.id.back_icon);
        main_fl = (FrameLayout) findViewById(R.id.main_fl);
        card_holder = (CardView) findViewById(R.id.card_holder);
        bottom_layout = (RelativeLayout) findViewById(R.id.bottom_layout);
        tabLayout = (TabLayout) findViewById(R.id.htab_tabs);
        bar_content = (RelativeLayout) findViewById(R.id.bar_content);
        RelativeLayout testwise = (RelativeLayout) findViewById(R.id.testwise);
        testwise.setBackgroundColor(getResources().getColor(color[color_id]));
        tabLayout.setBackgroundColor(getResources().getColor(color[color_id]));
        total_m = (p_MyCustomTextView_bold) findViewById(R.id.total_m);
        present_m = (p_MyCustomTextView_bold) findViewById(R.id.present_m);
        absent_m = (p_MyCustomTextView_bold) findViewById(R.id.absent_m);
        s_per_m = (p_MyCustomTextView_bold) findViewById(R.id.s_per_m);
        back_icon.postDelayed(new Runnable() {
            public void run() {
                bottom_layout.setVisibility(View.VISIBLE);
                card_holder.setVisibility(View.VISIBLE);
                bar_content.setVisibility(View.VISIBLE);
            }
        }, 1100);
        viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        new parse_json().execute();
        exitTransition = ActivityTransition.with(getIntent()).to(findViewById(R.id.testwise)).start(savedInstanceState);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new student_attend_subwise().newInstance(subjects_arr), "Subject Wise");
        adapter.addFrag(new student_attend_subwise().newInstance(subjects_arr), "Date Wise");
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

   /* public class DummyFragment extends Fragment {
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

            adapter = new SimpleRecyclerAdapter(attendance.this, list);
            recyclerView.setAdapter(adapter);

            return view;
        }
    }*/

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

    public class parse_json extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                JSONObject obj = new JSONObject(pre_sem_details);
                int success = obj.getInt("success");
                if (success == 1) {
                    JSONObject present_sem = obj.getJSONObject("present_sem");
                    working_days = present_sem.getInt("working_days");
                    present_days = present_sem.getInt("present_days");
                    String a_overall_ = present_sem.getString("a_overall_per");
                    a_overall_per = Double.parseDouble(a_overall_);
                    subjects_arr = present_sem.getJSONArray("subjects").toString();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            total_m.setText(String.valueOf(working_days));
            present_m.setText(String.valueOf(present_days));
            int abs = working_days - present_days;
            absent_m.setText(String.valueOf(abs));

            s_per_m.setText(String.valueOf(a_overall_per));
            if (a_overall_per > 75) {
                s_per_m.setTextColor(getResources().getColor(R.color.a2));
            } else if (a_overall_per >= 65) {
                s_per_m.setTextColor(getResources().getColor(R.color.b1));
            } else {
                s_per_m.setTextColor(getResources().getColor(R.color.red));
            }


            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }
    }
}
