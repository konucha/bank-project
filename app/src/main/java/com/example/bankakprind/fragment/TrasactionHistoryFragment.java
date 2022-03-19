package com.example.bankakprind.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bankakprind.DetailTransaction;
import com.example.bankakprind.R;
import com.example.bankakprind.adapter.TransactionAdapter;
import com.example.bankakprind.helper.DBRekening;
import com.example.bankakprind.model.RekeningModel;
import com.example.bankakprind.model.TransactionModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrasactionHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrasactionHistoryFragment extends Fragment {
    public TrasactionHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param Parameter akun rekening.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrasactionHistoryFragment newInstance(RekeningModel param) {
        TrasactionHistoryFragment fragment = new TrasactionHistoryFragment();
        Bundle args = new Bundle();
        args.putSerializable("akun", param);
        fragment.setArguments(args);
        return fragment;
    }

    private RekeningModel akun;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            akun = (RekeningModel) getArguments().getSerializable("akun");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction_history, container, false);
        recycleViewRun(view);
        return view;
    }

    RecyclerView recyclerView;
    ArrayList<TransactionModel> transactionModelArrayList;
    TransactionAdapter transactionAdapter;

    private void recycleViewRun(View view) {
        recyclerView = view.findViewById(R.id.recycle_history);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        transactionModelArrayList = new ArrayList<>();
        transactionAdapter = new TransactionAdapter(
                view.getContext(), transactionModelArrayList,
                new TransactionAdapter.OnTransaksiListener() {
                    @Override
                    public void onTransaksiClick(TransactionModel transactionModel) {

//                        Toast.makeText(getContext(), "test", Toast.LENGTH_SHORT).show();

                        Intent it = new Intent(view.getContext(), DetailTransaction.class);
                        it.putExtra("akun", akun);
                        it.putExtra("transaksi", transactionModel);
//                        Log.d("ASUS", "akun: " + transactionModel.getUang());

                        startActivity(it);
                    }
                }
        );
        recyclerView.setAdapter(transactionAdapter);


        // Query firebase
        // TODO Urutkan transaksi paling akhir  berada di atas
        Query reference = DBRekening.getInstance()
                .getReference("transactions")
                .child(String.valueOf(akun.getNoRekening()));

        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                transactionModelArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TransactionModel transaksi = dataSnapshot.getValue(TransactionModel.class);
                    transactionModelArrayList.add(transaksi);
                }

                // Ubah urutan menjaid descending, karena fitur di firebase tidak ditemukan wkwk
                Collections.reverse(transactionModelArrayList);
                transactionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}