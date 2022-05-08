package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.model.Item;
import com.example.CreateItemActivity;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemViewHolder> {

    private List<Item> items;
    private ItemListener itemListener;

    public ListAdapter() {
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_holder, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = items.get(position);
        if (item == null) return;
        holder.tvTitle.setText(item.getTitle());
        holder.tvCategory.setText(item.getCategory());
        holder.tvDate.setText(item.getDate());
        holder.tvPrice.setText(String.valueOf(item.getPrice()));
        holder.imgView.setImageResource(CreateItemActivity.IMAGES[item.getImg()]);
    }

    @Override
    public int getItemCount() {
        if (items == null) return 0;
        return items.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvCategory, tvDate, tvPrice;
        private ImageView imgView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.item_view_title);
            tvCategory = itemView.findViewById(R.id.item_view_category);
            tvDate = itemView.findViewById(R.id.item_view_date);
            tvPrice = itemView.findViewById(R.id.item_view_price);
            imgView = itemView.findViewById(R.id.item_view_img);
            itemView.setOnClickListener(v -> {
                if (itemView != null) {
                    itemListener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }
    }

    public interface ItemListener {
        void onItemClick(View view, int position);
    }
}
