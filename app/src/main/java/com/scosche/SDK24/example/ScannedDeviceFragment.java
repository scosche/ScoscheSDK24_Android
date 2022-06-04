package com.scosche.SDK24.example;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.scosche.sdk24.example.R;
import com.scosche.sdk24.RhythmDevice;

import java.util.ArrayList;
import java.util.List;

public class ScannedDeviceFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private static ScannedDeviceRecyclerViewAdapter adapter;

    public ScannedDeviceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_scanneddevice_list, container, false);

        recyclerView = view.findViewById(R.id.scanneddevices);
        recyclerView.setAdapter(new ScannedDeviceRecyclerViewAdapter(new ArrayList<RhythmDevice>(), mListener, ((MainActivity) getActivity()).getSdk(), ((MainActivity) getActivity()), ((MainActivity) getActivity())));

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    public List<RhythmDevice> getDevices() {
        adapter = ((ScannedDeviceRecyclerViewAdapter) recyclerView.getAdapter());
        return adapter.getDevices();
    }

    public interface OnListFragmentInteractionListener {
    }

    public void handleBluetoothDevice(RhythmDevice device) {
        adapter = ((ScannedDeviceRecyclerViewAdapter) recyclerView.getAdapter());
        if (adapter.addDevice(device)) {
            adapter.notifyDataSetChanged();
        }
    }

    public void removeDevice(RhythmDevice device) {

        for (RhythmDevice rhythmDevice : adapter.getDevices()) {
            if (rhythmDevice.getName().equals(device.getName())) {
                adapter.getDevices().remove(rhythmDevice);
                adapter.notifyDataSetChanged();
            }
        }
    }
}