package com.example.btl_adr_nangcao.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.btl_adr_nangcao.Activity.ListFoodActivity;
import com.example.btl_adr_nangcao.Domain.Category;
import com.example.btl_adr_nangcao.Domain.Foods;
import com.example.btl_adr_nangcao.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewholder>{
    ArrayList<Category> items;
    Context context;

    public CategoryAdapter(ArrayList<Category> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.titleTxt.setText(items.get(position).getName());

        switch (position){
            case 0:{
                holder.picture.setBackgroundResource(R.drawable.cate_1_background);
                break;
            }
            case 1:{
                holder.picture.setBackgroundResource(R.drawable.cate_2_background);
                break;
            }
            case 2:{
                holder.picture.setBackgroundResource(R.drawable.cate_3_background);
                break;
            }
            case 3:{
                holder.picture.setBackgroundResource(R.drawable.cate_4_background);
                break;
            }
            case 4:{
                holder.picture.setBackgroundResource(R.drawable.cate_5_background);
                break;
            }
            case 5:{
                holder.picture.setBackgroundResource(R.drawable.cate_6_background);
                break;
            }
            case 6:{
                holder.picture.setBackgroundResource(R.drawable.cate_7_background);
                break;
            }
            case 7:{
                holder.picture.setBackgroundResource(R.drawable.cate_8_background);
                break;
            }
        }
        int drawableResourceId = context.getResources().getIdentifier(items.get(position).getImagePath(),"drawable",holder.itemView.getContext().getPackageName());


        Glide.with(context)
                .load(drawableResourceId)
                .into(holder.picture);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ListFoodActivity.class);
                intent.putExtra("CategoryId", items.get(position).getId());
                intent.putExtra("CategoryName", items.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(items != null){
            return items.size();
        }
        return 0;
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        ImageView picture;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.cateName);
            picture = itemView.findViewById(R.id.img_cate);
        }
    }
}
