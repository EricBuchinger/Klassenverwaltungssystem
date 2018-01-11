package at.htl.organicer.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

import at.htl.organicer.R;
import at.htl.organicer.activities.StartupActivity;
import at.htl.organicer.authentication.AuthenticationActivity;
import at.htl.organicer.entities.TimeUnit;
import at.htl.organicer.entities.Weekday;
import at.htl.organicer.recyclerview.adapters.TimeTableEntryAdapter;

import static at.htl.organicer.activities.StartupActivity.dataHelper;

public class StartUpFragmentPortrait extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StartUpFragmentPortrait() {
        // Required empty public constructor
    }


    public static StartUpFragmentPortrait newInstance(String param1, String param2) {
        StartUpFragmentPortrait fragment = new StartUpFragmentPortrait();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_start_up_fragment_portrait, container, false);

        //TODO
        RecyclerView rvTimeTableEntries = v.findViewById(R.id.rv_timeTableEntries);
        TextView tv_weekday = v.findViewById(R.id.tv_weekday);

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        Weekday today = Weekday.valueOf(new SimpleDateFormat("EEEE", Locale.GERMAN).format(date.getTime()));
        LinkedList<TimeUnit> timeUnitsOfToday = new LinkedList<>();

        for(int i = 0; i < dataHelper.getTimeGrids().size(); i++)
            if(dataHelper.getTimeGrids().get(i).getWeekday() == today) {
                timeUnitsOfToday = dataHelper.getTimeGrids().get(i).getTimeUnits();
                //SpannableString todayString = new SpannableString(dataHelper.getTimeGrids().get(i).getWeekday().toString());
                //todayString.setSpan(new AbsoluteSizeSpan(30), 0, todayString.length(), 0);

                //FIXME Tryhard RichTextView
                tv_weekday.setText(dataHelper.getTimeGrids().get(i).getWeekday().toString());
            }

        if(timeUnitsOfToday.size() == 0) throw new RuntimeException("Today is not found");


        // TODO except Sunday
        // if(today.toString() == "Sunday")


        FloatingActionButton fab_feature1 = v.findViewById(R.id.fab_feature1);
        fab_feature1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.container_main, new AddEventFragment(), null).addToBackStack("AddEventView").commit();
            }
        });
        FloatingActionButton fab_logout = v.findViewById(R.id.fab_logout);
        fab_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthenticationActivity.signOut();
                Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                startActivity(intent);
            }
        });

        TimeTableEntryAdapter adapter = new TimeTableEntryAdapter(timeUnitsOfToday);
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvTimeTableEntries.setLayoutManager(llm);
        rvTimeTableEntries.setAdapter(adapter);

        return v;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
