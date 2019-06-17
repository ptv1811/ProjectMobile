package com.example.login;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private ArrayList<myItem> listItems;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    FoodAdapter(Context context,ArrayList<myItem> listItemsIn) {
        this.mInflater=LayoutInflater.from(context);
        this.listItems=listItemsIn;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =mInflater.inflate(R.layout.myitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        myItem foodItem = listItems.get(position);
        holder.nameFood.setText(foodItem.nameFood);
        holder.Location.setText(foodItem.Location);
        holder.PhoneNumber.setText(foodItem.phoneNumber);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameFood;
        TextView PhoneNumber;
        TextView Location;
        ViewHolder(View itemView) {
            super(itemView);
            nameFood = (TextView)itemView.findViewById(R.id.nameFood);
            Location=(TextView)itemView.findViewById(R.id.locationFood);
            PhoneNumber=(TextView)itemView.findViewById(R.id.phoneNumberFood);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    myItem getItem(int id)
    {
        return listItems.get(id);
    }
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
