package com.example.user.proyecto_final.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.user.proyecto_final.R;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoticiaListaFragment extends Fragment {

    static final String TAG = NoticiaListaFragment.class.getName();

    public NoticiaListaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_noticia_lista, container, false);
        ListView lv = (ListView) v.findViewById(R.id.lista);
        lv.setAdapter(getAdapter());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.w(TAG, "setOnItemClickListener");
                HashMap<String, String> item=(HashMap<String, String>)adapterView.getAdapter().getItem(i);
                String titulo=item.get("noticia");

                FragmentTransaction trans = getFragmentManager()
                        .beginTransaction();
                NoticiaDetalleFragment myFragment=new NoticiaDetalleFragment();
                Bundle args = new Bundle();
                args.putString("titulo", titulo);
                myFragment.setArguments(args);
                trans.replace(R.id.root_frame_noticia, myFragment);
                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                trans.addToBackStack(null);
                trans.commit();
            }
        });
        return v;
    }
    private BaseAdapter getAdapter() {
        String[] from = new String[]{"noticia"};
        int[] to = new int[]{R.id.noticia};

        ArrayList<String[]> lista = new ArrayList<String[]>();
        lista.add(new String[]{"Arbitrios Agosto 2018"});
        lista.add(new String[]{"Obras Av. Colectora"});
        lista.add(new String[]{"Cierre Municipio 31/04/2018"});
        lista.add(new String[]{"Junta Vecinal"});
        ArrayList<HashMap<String, String>> eventos = new ArrayList<HashMap<String, String>>();

        for (String[] item : lista) {
            HashMap<String, String> datosEvento = new HashMap<String, String>();
            datosEvento.put("noticia", item[0]);
            eventos.add(datosEvento);
        }

        SimpleAdapter listadoAdapter = new SimpleAdapter(getActivity(), eventos, R.layout.item_list_noticia, from, to);
        return listadoAdapter;
    }
}
