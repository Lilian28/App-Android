package com.example.user.proyecto_final.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.user.proyecto_final.R;
import com.example.user.proyecto_final.model.Noticia;
import com.example.user.proyecto_final.service.NoticiaService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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
        setAdapter(lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.w(TAG, "setOnItemClickListener");
                HashMap<String, String> item = (HashMap<String, String>) adapterView.getAdapter().getItem(i);
                String titulo = item.get("noticia");
                String detalle = item.get("detalle");

                FragmentTransaction trans = getFragmentManager()
                        .beginTransaction();
                NoticiaDetalleFragment myFragment = new NoticiaDetalleFragment();
                Bundle args = new Bundle();
                args.putString("titulo", titulo);
                args.putString("detalle", detalle);
                myFragment.setArguments(args);
                trans.replace(R.id.root_frame_noticia, myFragment);
                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                trans.addToBackStack(null);
                trans.commit();
            }
        });
        return v;
    }

    private void setAdapter(final ListView lv) {
        final String[] from = new String[]{"noticia"};
        final int[] to = new int[]{R.id.noticia};

        //Inicializar retrofit para consumir el servicio web
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://aniux.dx.am/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final NoticiaService service = retrofit.create(NoticiaService.class);

        //LLamando al servicio web de manera asincrona (por detras, en otro hilo)
        service.getNoticias().enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {
                ArrayList<String[]> lista = new ArrayList<>();

                for (Noticia noticia : response.body())
                    lista.add(new String[]{noticia.getTitulo(), noticia.getDetalle()});

                ArrayList<HashMap<String, String>> eventos = new ArrayList<>();

                for (String[] item : lista) {
                    HashMap<String, String> datosEvento = new HashMap<String, String>();
                    datosEvento.put("noticia", item[0]);
                    datosEvento.put("detalle", item[1]);
                    eventos.add(datosEvento);
                }

                SimpleAdapter listadoAdapter = new SimpleAdapter(getActivity(), eventos, R.layout.item_list_noticia, from, to);
                lv.setAdapter(listadoAdapter);
            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {
                Log.e(getClass().getName(),"Error listando noticias",t);
                Toast.makeText(getContext(), "No pude traer las noticias :( ", Toast.LENGTH_SHORT).show();
            }
        });


    }


}
