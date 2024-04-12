package com.example.btl_adr_nangcao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_adr_nangcao.Domain.Foods;
import com.example.btl_adr_nangcao.Domain.Sinhvien;
import com.example.btl_adr_nangcao.R;

import java.util.ArrayList;

public class SinhvienAdapter extends RecyclerView.Adapter<SinhvienAdapter.viewholder>{
    ArrayList<Sinhvien> items;
    Context context;

    public SinhvienAdapter(ArrayList<Sinhvien> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public SinhvienAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_sinhvien, parent, false);
        return new SinhvienAdapter.viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.masinhvien.setText(items.get(position).getMaSV());
        holder.tensinhvien.setText(items.get(position).getHotenSV());
        holder.diachi.setText(items.get(position).getDiachi());
        holder.ngaysinh.setText(items.get(position).getNgaysinh());
        holder.gioitinh.setText(items.get(position).getGioitinh());
        holder.email.setText(items.get(position).getEmail());
        holder.diemtrungbinh.setText(items.get(position).getDiemtrungbinh());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView masinhvien, tensinhvien, diachi, ngaysinh, gioitinh, email, diemtrungbinh;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            masinhvien = itemView.findViewById(R.id.masinhvien);
            tensinhvien = itemView.findViewById(R.id.tensinhvien);
            diachi = itemView.findViewById(R.id.diachi);
            ngaysinh = itemView.findViewById(R.id.ngaysinh);
            gioitinh = itemView.findViewById(R.id.gioitinh);
            email = itemView.findViewById(R.id.email);
            diemtrungbinh = itemView.findViewById(R.id.diemtrungbinh);

        }
    }
}
