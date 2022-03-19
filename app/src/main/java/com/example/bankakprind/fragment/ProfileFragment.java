package com.example.bankakprind.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.bankakprind.EditAccount;
import com.example.bankakprind.R;
import com.example.bankakprind.model.RekeningModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param akun, : akun rekening.
     * @return A new instance of fragment ShopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(RekeningModel akun) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable("akun", akun);
        fragment.setArguments(args);
        return fragment;
    }

    RekeningModel akun;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            akun = (RekeningModel) getArguments().getSerializable("akun");
        }
    }


    TextView tvNama, tvNoKtp, tvAlamat;
    Button btnEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvNama = view.findViewById(R.id.tv_nama);
        tvNoKtp = view.findViewById(R.id.tv_noktp);
        tvAlamat = view.findViewById(R.id.tv_alamat);

        tvNama.setText(akun.getNama());
        tvNoKtp.setText(String.valueOf(akun.getNoKtp()));
        tvAlamat.setText(akun.getAlamat());

        btnEdit = view.findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getActivity(), EditAccount.class);
                it.putExtra("akun", akun);
                startActivity(it);
            }
        });
        return view;
    }
}