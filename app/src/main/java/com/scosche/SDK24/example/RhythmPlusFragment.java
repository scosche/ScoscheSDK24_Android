package com.scosche.SDK24.example;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scosche.sdk24.example.R;

public class RhythmPlusFragment extends Fragment {

    private TextView heartRateField, batteryField, firmwareVersionField;

    public RhythmPlusFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rhythm_plus, container, false);

        heartRateField = view.findViewById(R.id.heartRateField);
        batteryField = view.findViewById(R.id.batteryLevelField);
        firmwareVersionField = view.findViewById(R.id.firmwareVersionField);
        return view;
    }

    public void updateHeartRate(String heartRate) {
        heartRateField.setText(heartRate);
    }

    public void updateBattery(int batteryLevel) {
        batteryField.setText(String.valueOf(batteryLevel));
    }

    public void updateFirmwareVersion(String value) {
        firmwareVersionField.setText(value);
    }

}