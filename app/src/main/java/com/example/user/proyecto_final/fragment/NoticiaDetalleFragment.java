package com.example.user.proyecto_final.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.proyecto_final.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoticiaDetalleFragment extends Fragment {

    public NoticiaDetalleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_noticia_detalle, container, false);
        ((TextView)v.findViewById(R.id.titulo)).setText(getArguments().getString("titulo"));
        return v;
    }

}
