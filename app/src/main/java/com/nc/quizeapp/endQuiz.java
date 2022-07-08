package com.nc.quizeapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.transition.CircularPropagation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class endQuiz extends Fragment {
        Button restart;
        TextView result_set;
    CircularProgressBar circularPropagation;

    public endQuiz() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view= inflater.inflate(R.layout.fragment_end_quiz, container, false);
        Bundle bundle=this.getArguments();
        circularPropagation=view.findViewById(R.id.circularProgressBar);
        result_set=view.findViewById(R.id.resulttext);
        restart=view.findViewById(R.id.restart);
        String score=String.valueOf(bundle.getInt("score"));
        String total=String.valueOf(bundle.getInt("total"));
        circularPropagation.setProgress(Integer.parseInt(score));
        result_set.setText(score+"/"+total);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getActivity(),MainActivity.class);
                startActivity(i);

            }
        });


        return  view;
    }
}