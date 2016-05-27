package com.egnify.University_demo.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egnify.University_demo.Adapter.attend_sub_adapter;
import com.egnify.University_demo.R;
import com.egnify.University_demo.pojos.mid_info;
import com.egnify.University_demo.pojos.subjects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class student_attend_subwise extends Fragment {

    String attendance_details;
    ArrayList<subjects> sub_arr = new ArrayList<>();
    ArrayList<ArrayList<mid_info>> mid_sub_arr = new ArrayList<>();
    private RecyclerView sub_rv;

    public student_attend_subwise() {
        // Required empty public constructor
    }

    public static student_attend_subwise newInstance(String attendance_details) {
        student_attend_subwise fragment = new student_attend_subwise();
        Bundle args = new Bundle();
        args.putSerializable("attendance_details", attendance_details);
        fragment.setArguments(args);
        return fragment;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root_view = inflater.inflate(R.layout.fragment_student_attend_subwise, container, false);
        attendance_details = (String) getArguments().getSerializable("attendance_details");
        sub_rv = (RecyclerView) root_view.findViewById(R.id.sub_rv);
        sub_rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        new parse_json().execute();
        return root_view;
    }

    public class parse_json extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                JSONArray arr = new JSONArray(attendance_details);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject Sub_details = arr.getJSONObject(i);
                    subjects subjects = new subjects();
                    subjects.setSubject_name(Sub_details.getString("subject_name"));
                    subjects.setShort_name(Sub_details.getString("short_name"));
                    subjects.setFaculty_name(Sub_details.getString("Faculty_name"));
                    subjects.setFaculty_id(Sub_details.getString("Faculty_id"));
                    sub_arr.add(subjects);
                    JSONArray mid_arrr = Sub_details.getJSONArray("mids");
                    ArrayList<mid_info> l_mid_arr = new ArrayList<>();
                    for (int j = 0; j < mid_arrr.length(); j++) {
                        JSONObject jobj = mid_arrr.getJSONObject(j);
                        mid_info mid_pojo = new mid_info();
                        mid_pojo.setExam_name(jobj.getString("exam_name"));
                        mid_pojo.setMarks(jobj.getString("marks"));
                        mid_pojo.setAttendance(jobj.getString("attendance"));
                        mid_pojo.setTotal_classes(jobj.getString("total_classes"));
                        mid_pojo.setClasses_present(jobj.getString("classes_present"));
                        l_mid_arr.add(mid_pojo);
                    }
                    mid_sub_arr.add(l_mid_arr);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            attend_sub_adapter sub_adapter = new attend_sub_adapter(getActivity(), mid_sub_arr, sub_arr);
            sub_rv.setAdapter(sub_adapter);
        }
    }
}
