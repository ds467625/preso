package com.preso.samkyc.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.preso.samkyc.R;
import com.preso.samkyc.listerner.OnNextListerner;


public class OnBoardLastFragment extends Fragment {

    OnNextListerner onNextListerner;
    private Button nextButton;

    public OnBoardLastFragment() {
        // Required empty public constructor
    }

    public OnBoardLastFragment(OnNextListerner onNextListerner) {
        this.onNextListerner = onNextListerner;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_on_board_last, container, false);
        nextButton = view.findViewById(R.id.nextbutton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextListerner.onNext(true);
            }
        });

        return view;

    }
}