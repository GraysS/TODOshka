package com.example.ideath.todoshka.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ideath.todoshka.Other.ResourcesFragments;
import com.example.ideath.todoshka.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckFragment extends Fragment  {


    ResourcesFragments resourcesFragments;
    public CheckFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        resourcesFragments = new ResourcesFragments(inflater.getContext());
        return resourcesFragments.resources(true,inflater,container);
    }


    @Override
    public void onResume() {
        super.onResume();
        resourcesFragments.resume(true);
    }
}

