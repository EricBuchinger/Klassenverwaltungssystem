package at.htl.organicer.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import at.htl.organicer.R;
import at.htl.organicer.activities.StartupActivity;
import at.htl.organicer.entities.Teacher;
import at.htl.organicer.recyclerview.adapters.EventAdapter;
import at.htl.organicer.recyclerview.adapters.TeacherAdapter;

public class WebUntisDemoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters

    private OnFragmentInteractionListener mListener;

    public WebUntisDemoFragment() {
        // Required empty public constructor
    }


    public static WebUntisDemoFragment newInstance(String param1) {
        WebUntisDemoFragment fragment = new WebUntisDemoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_web_untis_demo, container, false);


        RecyclerView rv_dataToShow = v.findViewById(R.id.rv_dataToShow);

        LinkedList<Teacher> allTeachers = new LinkedList<>();


        allTeachers = StartupActivity.dataHelper.getTeachers();

        TeacherAdapter adapter = new TeacherAdapter(allTeachers);

        rv_dataToShow.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_dataToShow.setLayoutManager(llm);
        rv_dataToShow.setAdapter(adapter);

        //TODO
        return v;
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
