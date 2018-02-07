package at.htl.organicer.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.htl.organicer.R;


public class LoadingbarFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public LoadingbarFragment() {
        // Required empty public constructor
    }

    public static LoadingbarFragment newInstance() {
        LoadingbarFragment fragment = new LoadingbarFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_loadingbar, container, false);


        return v;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
