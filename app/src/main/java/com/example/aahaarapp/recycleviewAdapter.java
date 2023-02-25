package com.example.aahaarapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class recycleviewAdapter extends RecyclerView.Adapter<recycleviewAdapter.MyViewHolder> {
    private final List<myItems> items;
    private final Context context;

    public recycleviewAdapter(List<myItems> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public recycleviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.historyitem,null));
    }

    @Override
    public void onBindViewHolder(@NonNull recycleviewAdapter.MyViewHolder holder, int position) {


        myItems myItems = items.get(position);
        holder.name.setText(myItems.getName());
        holder.food.setText(myItems.getFood());
     //   holder.phone.setText(myItems.getFood());
        holder.desc.setText(myItems.getDesc());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private  final TextView name,food,desc;
     //   private final  TextView phone;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.historyname);
            food = itemView.findViewById(R.id.historyfood);
          //  phone = itemView.findViewById(R.id.historyphone);
            desc = itemView.findViewById(R.id.historydesc);

        }
    }
}
