package com.example;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.db.SQLiteHelper;
import com.example.model.Item;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateItemActivity extends AppCompatActivity {

    private Spinner sp;
    private EditText eTitle, ePrice;
    private Button btnDate, btnAdd, btnCancel, btnDelete;
    private SQLiteHelper sql;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        sp = findViewById(R.id.add_spinner_category);
        eTitle = findViewById(R.id.add_e_title);
        ePrice = findViewById(R.id.add_e_price);
        btnDate = findViewById(R.id.add_btn_date);
        btnAdd = findViewById(R.id.btn_add);
        btnCancel = findViewById(R.id.btn_cancel);
        btnDelete = findViewById(R.id.btn_delete);

        sql = new SQLiteHelper(this);

        sp.setAdapter(new ArrayAdapter<String>(this,
                R.layout.category_view_holder,
                getResources().getStringArray(R.array.item_categories)));

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOpenDateDialog();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAdd();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancel();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete();
            }
        });

        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("item");
        if (item == null) {
            btnDelete.setVisibility(View.GONE);
            btnAdd.setText("Update");
        } else {
            setItemIntoForm();
        }
    }

    private void onOpenDateDialog() {
        final Calendar c = Calendar.getInstance();
        int currentDay = c.get(Calendar.DAY_OF_MONTH);
        int currentMonth = c.get(Calendar.MONTH);
        int currentYear = c.get(Calendar.YEAR);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DatePickerDialog dateDialog = new DatePickerDialog(this, (view, year, month, day) -> {
            c.set(year, month, day);
            btnDate.setText(dateFormat.format(c.getTime()));
        }, currentYear, currentMonth, currentDay);
        dateDialog.show();
    }

    private void onAdd() {
        Item i = getItemFromForm();
        if (i != null) {
            if(item != null) {
                i.setId(item.getId());
                sql.updateItem(i);
            }
            else {
                sql.createItem(i);
            }
            finish();
        }
    }

    private void onCancel() {
        finish();
    }

    private void onDelete() {
        sql.deleteItem(item.getId());
        finish();
    }

    @Nullable
    private Item getItemFromForm() {
        try {
            String title = eTitle.getText().toString();
            String price = ePrice.getText().toString();
            String date = btnDate.getText().toString();
            String cate = sp.getSelectedItem().toString();
            return new Item(title, cate, date, Double.parseDouble(price));
        } catch (Exception e) {
            return null;
        }
    }

    private void setItemIntoForm() {
        eTitle.setText(item.getTitle());
        ePrice.setText(String.valueOf(item.getPrice()));
        btnDate.setText(item.getDate());
        for (int i = 0; i < sp.getCount(); i++) {
            if (item.getCategory().equals(sp.getItemAtPosition(i))) {
                sp.setSelection(i);
                break;
            }
        }
    }
}