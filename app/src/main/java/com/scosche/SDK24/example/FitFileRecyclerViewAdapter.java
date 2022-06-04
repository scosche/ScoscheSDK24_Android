package com.scosche.SDK24.example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.scosche.sdk24.example.R;
import com.scosche.sdk24.BluetoothHelper;
import com.scosche.sdk24.FitFileContent;
import com.scosche.sdk24.ScoscheSDK24;

import java.util.List;

public class FitFileRecyclerViewAdapter extends RecyclerView.Adapter<FitFileRecyclerViewAdapter.ViewHolder> {
    private List<FitFileContent.FitFileInfo> mItems;
    private Context context;
    private ScoscheSDK24 sdk;

    private TextView downloadItem;

    FitFileRecyclerViewAdapter(List<FitFileContent.FitFileInfo> items, Context context, ScoscheSDK24 sdk) {
        mItems = items;
        this.sdk = sdk;
        this.context = context;
    }

    @Override
    public FitFileRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View fitFileView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fit_file, parent, false);
        return new ViewHolder(fitFileView);
    }

    @Override
    public void onBindViewHolder(final FitFileRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = mItems.get(position);
        holder.mIdView.setText(BluetoothHelper.getcurrentConnectedDevice().getName() + "-" + holder.mItem.metaDateInt + ".fit");
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        FitFileContent.FitFileInfo mItem;
        public final TextView mIdView;
        public final TextView mDownloadView;
        public final TextView mDeleteView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.workoutFileInfo);
            mDownloadView = view.findViewById(R.id.download);
            mDownloadView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sdk.downloadFitFile(mItem);
                    downloadItem = mDownloadView;
                }
            });

            mDeleteView = view.findViewById(R.id.delete);
            mDeleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sdk.deleteFitFile(mItem);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }

    public void addItem(FitFileContent.FitFileInfo item) {

        mItems.add(item);
    }

    public void clearItems() {
        mItems.clear();
    }

    public void test(int percent) {
        downloadItem.setText(percent + "%");
    }

}
