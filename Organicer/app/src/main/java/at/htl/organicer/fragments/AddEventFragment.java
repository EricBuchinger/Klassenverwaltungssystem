package at.htl.organicer.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

import at.htl.organicer.R;
import at.htl.organicer.database.FirebaseContext;
import at.htl.organicer.entities.Event;


//Created by eric on 11.01.2018
public class AddEventFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FloatingActionButton fab_AddNewEvent;
    private TextInputEditText txtInput_Subject;
    private TextInputEditText txtInput_EventName;
    private DatePicker datePicker;
    private Event event;
    private DatabaseReference mDatabase;
    private  static final String TAG = "AddEventFragment";


    public AddEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddEventFragment newInstance(String param1, String param2) {
        AddEventFragment fragment = new AddEventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        event = new Event();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_add_event, container, false);

        fab_AddNewEvent = v.findViewById(R.id.fab_AddEvent);
        txtInput_EventName = v.findViewById(R.id.txtInput_EventName);
        txtInput_Subject = v.findViewById(R.id.txtInput_Subject);
        datePicker = v.findViewById(R.id.dp_AddEvent);
        fab_AddNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventName = txtInput_EventName.getText().toString();
                String eventSubject = txtInput_Subject.getText().toString();
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year =  datePicker.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                Date date = calendar.getTime();

                if(eventName!=null && eventSubject!=null){
                    event.setName(eventName);
                    event.setSubject(eventSubject);
                    event.setDate(date);
                    try {
                        int id = FirebaseContext.getInstance().events.size()+1;
                        mDatabase.child("events").child(String.valueOf(String.valueOf(id))).setValue(event);
                        Toast.makeText(getContext(),"Event wurde erfolgreich erstellt",Toast.LENGTH_SHORT);
                        getFragmentManager().popBackStack();
                    }catch (Exception e){
                        Log.e(TAG,"Event konnte nicht in die Datenbank gespeichert werden!");
                    }

                }
              //Todo initialize
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



}
