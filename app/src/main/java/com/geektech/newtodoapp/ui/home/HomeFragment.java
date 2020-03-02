package com.geektech.newtodoapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.geektech.newtodoapp.FormActivity;
import com.geektech.newtodoapp.R;
import com.geektech.newtodoapp.models.Work;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private WorkAdapter adapter;
    private List<Work> list;
AlertDialog alertDialog;


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
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnClick(int position) {
Work work=list.get(position);
                Intent intent=new Intent(getContext(), FormActivity.class);
                intent.putExtra("Work", work);
                startActivity(intent);
            }

            @Override
            public void OnLongClick(final int position) {
AlertDialog.Builder dialog=new AlertDialog.Builder(requireContext());
dialog.setTitle("Deleting").setMessage("Delete item?").setNegativeButton("No cancel", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
}).setPositiveButton("Yes of course", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
        App.getDataBase().workDao().delete(list.get(position));
    }
}).show();

            }
        });
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
