package com.example.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.model.Item;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "spend.db";
    private static int DB_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table items" +
                "(id integer primary key autoincrement," +
                "title text," +
                "category text, price real, date text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String orderBy = "date DESC";
        Cursor cursor = sqLiteDatabase.query("items", null, null, null, null, null, orderBy);
        while (cursor != null && cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String category = cursor.getString(2);
            double price = cursor.getDouble(3);
            String date = cursor.getString(4);
            items.add(new Item(id, title, category, date, price));
        }
        return items;
    }

    public long createItem(Item item) {
        ContentValues values = new ContentValues();
        values.put("title", item.getTitle());
        values.put("category", item.getCategory());
        values.put("price", item.getPrice());
        values.put("date", item.getDate());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("items", null, values);
    }

    public List<Item> getItemsByDate(String arg) {
        List<Item> items = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String clause = "date like ?";
        String[] args = {arg};

        Cursor cursor = sqLiteDatabase.query("items", null, clause, args, null, null, null);
        while (cursor != null && cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String category = cursor.getString(2);
            double price = cursor.getDouble(3);
            String date = cursor.getString(4);
            items.add(new Item(id, title, category, date, price));
        }
        return items;
    }
}
