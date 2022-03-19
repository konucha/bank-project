package com.example.bankakprind.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.bankakprind.AddTransaction;
import com.example.bankakprind.R;
import com.example.bankakprind.model.RekeningModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionFragment extends Fragment {
    public TransactionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param akun Parameter1, data akun rekening.
     * @return A new instance of fragment LikeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionFragment newInstance(RekeningModel akun) {
        TransactionFragment fragment = new TransactionFragment();
        Bundle args = new Bundle();
        args.putSerializable("akun", akun);
        fragment.setArguments(args);
        return fragment;
    }

    private RekeningModel akun;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            // TODO: Rename parameter arguments, choose names that match
            // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

            akun = (RekeningModel) getArguments().getSerializable("akun");
//            Log.d("ASUS", "IF get argument serializable " + akun.getNama());
        }
    }

    Button btnTabung, btnTarik;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);

        btnTabung = view.findViewById(R.id.btn_nabung);
        btnTarik = view.findViewById(R.id.btn_tarik);

        btnTabung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddTransaction.class);
                intent.putExtra("type", "nabung");
                intent.putExtra("akunRekening", akun);
                startActivity(intent);
            }
        });

        btnTarik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddTransaction.class);
                intent.putExtra("type", "tarik");
                intent.putExtra("akunRekening", akun);
                startActivity(intent);
            }
        });

        return view;
    }
}