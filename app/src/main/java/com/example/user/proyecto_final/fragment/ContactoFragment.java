package com.example.user.proyecto_final.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.proyecto_final.R;
import com.theartofdev.edmodo.cropper.CropImage;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactoFragment extends Fragment {


    public ContactoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contacto, container, false);
        Button capturarButton = v.findViewById(R.id.capturar);
        capturarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .start(getContext(), ContactoFragment.this);
            }
        });
        return v;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
