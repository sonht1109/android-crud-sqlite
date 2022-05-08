package com.example.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CreateItemActivity;
import com.example.R;
import com.example.adapter.ListAdapter;
import com.example.db.SQLiteHelper;
import com.example.model.Item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FrStat extends Fragment {

    private SearchView searchView;
    private Button btnFrom, btnTo, btnSearch;
    private Spinner sp;
    private RecyclerView rcv;
    private SQLiteHelper sqLiteHelper;
    private ListAdapter adapter;
    private final Calendar c = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_stat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = view.findViewById(R.id.stat_search);
        btnFrom = view.findViewById(R.id.stat_btn_from);
        btnTo = view.findViewById(R.id.stat_btn_to);
        btnSearch = view.findViewById(R.id.stat_btn_search);
        rcv = view.findViewById(R.id.stat_rcv);
        sp = view.findViewById(R.id.stat_sp_category);

        sp.setAdapter(new ArrayAdapter<String>(getContext(),
                R.layout.category_view_holder,
                getResources().getStringArray(R.array.item_categories)));

        sqLiteHelper = new SQLiteHelper(getContext());

        adapter = new ListAdapter();
        adapter.setItems(new ArrayList<>());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);

        String today = dateFormat.format(c.getTime());
        btnFrom.setText(today);
        btnTo.setText(today);

        btnFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOpenFromPicker();
            }
        });

        btnTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOpenToPicker();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearch();
            }
        });

        adapter.setItemListener(new ListAdapter.ItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                Item i = adapter.getItems().get(position);
                Intent t = new Intent(getActivity(), CreateItemActivity.class);
                t.putExtra("item", i);
                startActivity(t);
            }
        });

        String[] args = {"date desc"};
        String sql = "select * from items order by ?";
        List<Item> items = sqLiteHelper.searchItems(sql, args);
        adapter.setItems(items);
    }

    private void onOpenFromPicker() {
        int currentDay = c.get(Calendar.DAY_OF_MONTH);
        int currentMonth = c.get(Calendar.MONTH);
        int currentYear = c.get(Calendar.YEAR);
        DatePickerDialog dateDialog = new DatePickerDialog(getContext(), (view, year, month, day) -> {
            c.set(year, month, day);
            btnFrom.setText(dateFormat.format(c.getTime()));
        }, currentYear, currentMonth, currentDay);
        dateDialog.show();
    }

    private void onOpenToPicker() {
        int currentDay = c.get(Calendar.DAY_OF_MONTH);
        int currentMonth = c.get(Calendar.MONTH);
        int currentYear = c.get(Calendar.YEAR);
        DatePickerDialog dateDialog = new DatePickerDialog(getContext(), (view, year, month, day) -> {
            c.set(year, month, day);
            btnTo.setText(dateFormat.format(c.getTime()));
        }, currentYear, currentMonth, currentDay);
        dateDialog.show();
    }

    private void onSearch() {
        String from = btnFrom.getText().toString();
        String to = btnTo.getText().toString();
        String title = searchView.getQuery().toString();
        String cate = sp.getSelectedItem().toString();

        String[] args = {"%" + title + "%", cate, from, to, "date desc"};
        String sql = "select * from items where title like ? and category like ? and date between ? and ? order by ?";

        List<Item> items = sqLiteHelper.searchItems(sql, args);
        adapter.setItems(items);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
