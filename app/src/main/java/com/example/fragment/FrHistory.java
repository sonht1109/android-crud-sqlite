package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.CreateItemActivity;
import com.example.adapter.ListAdapter;
import com.example.db.SQLiteHelper;
import com.example.model.Item;
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

        getItems();

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(listAdapter);
        listAdapter.setItemListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Item item = listAdapter.getItems().get(position);
        Intent t = new Intent(getActivity(), CreateItemActivity.class);
        t.putExtra("item", item);
        startActivity(t);
    }

    private void getItems() {
        List<Item> items = sqLiteHelper.getAllItems();
        listAdapter.setItems(items);
    }

    @Override
    public void onResume() {
        super.onResume();
        getItems();
    }
}
