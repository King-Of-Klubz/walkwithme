package com.example.kennedy.walkwithmeapp;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoalFragment extends android.support.v4.app.Fragment{

    BarChart goalsChart;

    public GoalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_goal, container, false);
        goalsChart = (BarChart)v.findViewById(R.id.goalsBarChart);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(4000f,0));
        barEntries.add(new BarEntry(8000f,1));
        barEntries.add(new BarEntry(5000f,2));
        barEntries.add(new BarEntry(7000f,3));
        barEntries.add(new BarEntry(3000f,4));

        BarDataSet barDataSet = new BarDataSet(barEntries,"Steps");

        ArrayList<String> daysOfWeek = new ArrayList<>();

        daysOfWeek.add("Mon");
        daysOfWeek.add("Tue");
        daysOfWeek.add("Wed");
        daysOfWeek.add("Thur");
        daysOfWeek.add("Fri");


        BarData theData = new BarData(daysOfWeek,barDataSet);
        goalsChart.setData(theData);
        goalsChart.setScaleXEnabled(true);
        goalsChart.setScaleYEnabled(true);
        goalsChart.setDescription("");
        goalsChart.setDrawValueAboveBar(false);

        return v;
    }

}
