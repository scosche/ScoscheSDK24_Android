package com.scosche.SDK24.example;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.scosche.sdk24.example.R;
import com.scosche.sdk24.Zone;

public class Rhythm24Fragment extends Fragment {

    private enum SportMode {
        BLANK("", -1), HEART_RATE_ONLY("Heart Rate Only", 0), RUNNING("Running", 1), CYCLING("Cycling", 2), SWIMMING("Swimming", 5),
        HRV("Heart Rate Variability", 255), DUATHLON("Duathlon", 253), TRIATHLON("Triathlon", 254);

        public String name;
        public int id;

        SportMode(String name, int id) {
            this.name = name;
            this.id = id;
        }
 
        public static SportMode fromId(int id) {
            for (SportMode s : SportMode.values()) {
                if (s.id == id) {
                    return s;
                }
            }
            return HEART_RATE_ONLY;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private TextView heartRateField, batteryField;
    private EditText zoneOneTwoBPM, zoneTwoThreeBPM, zoneThreeFourBPM, zoneFourFiveBPM;
    private Button readZonesButton, updateZonesButton, readSportModeButton, updateSportModeButton, viewFitFilesButton;
    private Spinner sportModeSpinner;

    public Rhythm24Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rhythm24, container, false);

        heartRateField = view.findViewById(R.id.heartRateField);
        batteryField = view.findViewById(R.id.batteryLevelField);

        sportModeSpinner = view.findViewById(R.id.sportModeSpinner);
        sportModeSpinner.setAdapter(new ArrayAdapter<SportMode>(getContext(), android.R.layout.simple_spinner_item, SportMode.values()));
        sportModeSpinner.setSelection(0);

        zoneOneTwoBPM = view.findViewById(R.id.zoneOneTwoBPM);
        zoneTwoThreeBPM = view.findViewById(R.id.zoneTwoThreeBPM);
        zoneThreeFourBPM = view.findViewById(R.id.zoneThreeFourBPM);
        zoneFourFiveBPM = view.findViewById(R.id.zoneFourFiveBPM);

        readZonesButton = view.findViewById(R.id.readZonesButton);
        readZonesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Zone zone = ((MainActivity) getActivity()).getSdk().getZoneValues();
                if (zone != null) {
                    zoneOneTwoBPM.setText(String.valueOf(zone.getZoneOneTwo()));
                    zoneTwoThreeBPM.setText(String.valueOf(zone.getZoneTwoThree()));
                    zoneThreeFourBPM.setText(String.valueOf(zone.getZoneThreeFour()));
                    zoneFourFiveBPM.setText(String.valueOf(zone.getZoneFourFive()));
                }
            }
        });

        updateZonesButton = view.findViewById(R.id.updateZonesButton);
        updateZonesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String zoneOneTwo = zoneOneTwoBPM.getText().toString();
                String zoneTwoThree = zoneTwoThreeBPM.getText().toString();
                String zoneThreeFour = zoneThreeFourBPM.getText().toString();
                String zoneFourFive = zoneFourFiveBPM.getText().toString();

                if ("".equals(zoneOneTwo) || "".equals(zoneTwoThree) || "".equals(zoneThreeFour) || "".equals(zoneFourFive)) {
                    Toast.makeText(getContext(), "Please enter all zone values.", Toast.LENGTH_LONG).show();
                } else {
                    short zoneOneTwoShort = Short.parseShort(zoneOneTwo);
                    short zoneTwoThreeShort = Short.parseShort(zoneTwoThree);
                    short zoneThreeFourShort = Short.parseShort(zoneThreeFour);
                    short zoneFourFiveShort = Short.parseShort(zoneFourFive);

                    if (zoneOneTwoShort > 250 || zoneTwoThreeShort > 250 || zoneThreeFourShort > 250 || zoneFourFiveShort > 250) {
                        Toast.makeText(getContext(), "Please enter valid zone numbers.", Toast.LENGTH_LONG).show();
                    } else if (zoneOneTwoShort > zoneTwoThreeShort || zoneTwoThreeShort > zoneThreeFourShort || zoneThreeFourShort > zoneFourFiveShort) {
                        Toast.makeText(getContext(), "Zones must be in order.", Toast.LENGTH_LONG).show();
                    } else {
                        Zone zone = new Zone(Short.parseShort(zoneOneTwo), Short.parseShort(zoneTwoThree), Short.parseShort(zoneThreeFour), Short.parseShort(zoneFourFive));
                        ((MainActivity) getActivity()).getSdk().updateZoneValues(zone);
                    }
                }
            }
        });

        readSportModeButton = view.findViewById(R.id.readSportModeButton);
        readSportModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sportMode = ((MainActivity) getActivity()).getSdk().getSportMode();
                SportMode sportModeEnum = SportMode.fromId(sportMode);

                for (int i = 0; i < sportModeSpinner.getCount(); i++) {
                    if (sportModeSpinner.getItemAtPosition(i).equals(sportModeEnum)) {
                        sportModeSpinner.setSelection(i);
                        break;
                    }
                }
            }
        });

        updateSportModeButton = view.findViewById(R.id.updateSportModeButton);
        updateSportModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = ((SportMode) sportModeSpinner.getSelectedItem()).id;
                if (value == -1) {
                    Toast.makeText(getContext(), "Please select a sport mode.", Toast.LENGTH_LONG).show();
                } else {
                    ((MainActivity) getActivity()).getSdk().updateSportMode(value);
                }
            }
        });

        viewFitFilesButton = view.findViewById(R.id.viewFitFilesButton);
        viewFitFilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((MainActivity) getActivity()).getSdk().getFitFiles();

                    Fragment fragment = FitFilesFragment.class.newInstance();
//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fragment, "FitFilesFragment").commit();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fragment, "FitFilesFragment").addToBackStack("FitFilesFragment").commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        return view;
    }

    public void updateHeartRate(String heartRate) {
        heartRateField.setText(heartRate);
    }

    public void updateBattery(int batteryLevel) {
        batteryField.setText(String.valueOf(batteryLevel));
    }

    public void updateZone(int zone) {

    }

    public void updateSportMode(int sportMode) {
//        sportModeField.setText(String.valueOf(sportMode));
    }
}