package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.UpdateItemActivity;
import com.example.adapter.ListAdapter;
import com.example.db.SQLiteHelper;
import com.example.model.Item;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FrHistory extends Fragment implements ListAdapter.ItemListener {

    private ListAdapter listAdapter;
    private RecyclerView recyclerView;
    private SQLiteHelper sqLiteHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.history_rcv);
        listAdapter = new ListAdapter();
        sqLiteHelper = new SQLiteHelper(getContext());

        List<Item> items = sqLiteHelper.getAllItems();
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        sqLiteHelper.createItem(new Item(1, "title", "cate", dateFormat.format(d), 12323));
        listAdapter.setItems(items);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(listAdapter);
        listAdapter.setItemListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Item item = listAdapter.getItems().get(position);
        Intent t = new Intent(getActivity(), UpdateItemActivity.class);
        t.putExtra("item", item);
        startActivity(t);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Item> items = sqLiteHelper.getAllItems();
        listAdapter.setItems(items);
    }
}
