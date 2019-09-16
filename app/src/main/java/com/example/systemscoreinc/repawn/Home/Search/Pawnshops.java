package com.example.systemscoreinc.repawn.Home.Search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.systemscoreinc.repawn.R;

public class Pawnshops extends Fragment {
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pawnshops, container, false);
        ((Search) getActivity()).getPawnshops(rootView);
        return rootView;
    }
}