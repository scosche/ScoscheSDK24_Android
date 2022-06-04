package com.scosche.SDK24.example;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scosche.sdk24.example.R;
import com.scosche.sdk24.RhythmDevice;
import com.scosche.sdk24.RhythmSDKDeviceCallback;
import com.scosche.sdk24.RhythmSDKFitFileCallback;
import com.scosche.sdk24.ScoscheSDK24;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScannedDeviceRecyclerViewAdapter extends RecyclerView.Adapter<ScannedDeviceRecyclerViewAdapter.ViewHolder> {
    private List<RhythmDevice> devices;
    private final ScannedDeviceFragment.OnListFragmentInteractionListener mListener;
    private ScoscheSDK24 sdk;
    private RhythmSDKDeviceCallback callback;
    private RhythmSDKFitFileCallback fitFileCallback;

    public ScannedDeviceRecyclerViewAdapter(List<RhythmDevice> items, ScannedDeviceFragment.OnListFragmentInteractionListener listener, ScoscheSDK24 sdk, RhythmSDKDeviceCallback callback, RhythmSDKFitFileCallback fitFileCallback) {
        devices = items;
        mListener = listener;
        this.sdk = sdk;
        this.callback = callback;
        this.fitFileCallback = fitFileCallback;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_scanneddevice, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = devices.get(position);
        holder.mIdView.setText(devices.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }


    public boolean addDevice(RhythmDevice device) {
        if (device != null) {
            for (int i = 0; i < devices.size(); i++) {
                if (device.getName().equals(devices.get(i).getName())) {
                    return false;
                }
            }
            devices.add(device);
            return true;
        }
        return false;
    }

    public List<RhythmDevice> getDevices() {
        return devices;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mConnectView;
        public RhythmDevice mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.id);

            mConnectView = view.findViewById(R.id.connect);
            mConnectView.setText(R.string.connect_label);
            mView.setClickable(true);
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener && mItem != null) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        sdk.connectDevice(mItem, callback, fitFileCallback);
                    }
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mConnectView.getText() + "'";
        }
    }
}
