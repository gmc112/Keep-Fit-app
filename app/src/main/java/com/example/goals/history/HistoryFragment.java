package com.example.goals.history;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goals.R;

public class HistoryFragment extends Fragment {

    private HistoryViewModel viewModel;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.re_history);
        Context context = view.getContext();

        final HistoryListAdapter adapter = new HistoryListAdapter(context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        viewModel.getAllHistories()
                .observe(getViewLifecycleOwner(), adapter::setHistories);
        return view;
    }
}
