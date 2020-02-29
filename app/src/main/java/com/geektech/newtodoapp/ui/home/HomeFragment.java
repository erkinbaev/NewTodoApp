package com.geektech.newtodoapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.newtodoapp.App;
import com.geektech.newtodoapp.R;
import com.geektech.newtodoapp.models.Work;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private WorkAdapter adapter;
    private List<Work> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

       return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        list= new ArrayList<>();
        adapter=new WorkAdapter(list);
        recyclerView.setAdapter(adapter);
        App.getDataBase().workDao().getAll().observe(getViewLifecycleOwner(), new Observer<List<Work>>() {
            @Override
            public void onChanged(List<Work> works) {
list.clear();
list.addAll(works);
adapter.notifyDataSetChanged();
            }
        });


    }
}
