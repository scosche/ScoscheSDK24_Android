package com.scosche.SDK24.example;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scosche.sdk24.example.R;
import com.scosche.sdk24.FitFileContent;

import java.util.List;

public class FitFilesFragment extends Fragment {

    private RecyclerView recyclerView;
    private static FitFileRecyclerViewAdapter adapter;


    public FitFilesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fit_files, container, false);
        recyclerView = view.findViewById(R.id.fitFileRecycler);

        adapter = new FitFileRecyclerViewAdapter(FitFileContent.ITEMS, getContext(), ((MainActivity) getActivity()).getSdk());
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void displayFitFiles(List<FitFileContent.FitFileInfo> files) {

        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {

                adapter.clearItems();
                adapter.notifyDataSetChanged();

                for (FitFileContent.FitFileInfo file : files) {
                    adapter.addItem(file);
                }

                adapter.notifyDataSetChanged();
            }
        });

    }

    public FitFileRecyclerViewAdapter getAdapter() {
        return adapter;
    }
}