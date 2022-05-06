package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FrToday extends Fragment implements ListAdapter.ItemListener {

    private RecyclerView rcv;
    private TextView tvSum;
    private SQLiteHelper sqLiteHelper;
    private List<Item> items;
    private ListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_today, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv = view.findViewById(R.id.today_rcv);
        tvSum = view.findViewById(R.id.today_tv_sum);

        sqLiteHelper = new SQLiteHelper(getContext());
        adapter = new ListAdapter();

        getItems();

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(manager);

        adapter.setItemListener(this);
    }

    private void getItems() {
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        items = sqLiteHelper.getItemsByDate(dateFormat.format(d));
        adapter.setItems(items);

        tvSum.setText("Sum Today: " + getSum());
    }

    private double getSum() {
        double res = 0;
        for (Item i : items) {
            res += i.getPrice();
        }
        return res;
    }

    @Override
    public void onItemClick(View view, int position) {
        Item i = adapter.getItems().get(position);
        Intent t = new Intent(getActivity(), CreateItemActivity.class);
        t.putExtra("item", i);
        startActivity(t);
    }

    @Override
    public void onResume() {
        super.onResume();
        getItems();
    }
}
