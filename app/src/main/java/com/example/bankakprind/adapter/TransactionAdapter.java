package com.example.bankakprind.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankakprind.R;
import com.example.bankakprind.helper.CurrencyRupiah;
import com.example.bankakprind.model.TransactionModel;

import java.util.ArrayList;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {

    private final Context context;
    private final ArrayList<TransactionModel> listTransaksi;
    private final OnTransaksiListener onTransaksiListener;

    public TransactionAdapter(Context context, ArrayList<TransactionModel> listTransaksi, OnTransaksiListener onTransaksiListener) {
        this.context = context;
        this.listTransaksi = listTransaksi;
        this.onTransaksiListener = onTransaksiListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaksi, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TransactionModel transaksi = listTransaksi.get(position);
        holder.tvWaktu.setText(String.valueOf(transaksi.getTanggal()));
        holder.tvJenisTransaksi.setText(transaksi.getJenisTransaksi());
        holder.tvUang.setText(CurrencyRupiah.format(transaksi.getUang()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTransaksiListener.onTransaksiClick(listTransaksi.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTransaksi.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvWaktu, tvUang, tvJenisTransaksi;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvWaktu = itemView.findViewById(R.id.tv_timestamp);
            tvJenisTransaksi = itemView.findViewById(R.id.tv_type);
            tvUang = itemView.findViewById(R.id.tv_uang);
        }
    }

    public interface OnTransaksiListener {
        void onTransaksiClick(TransactionModel transactionModel);
    }
}