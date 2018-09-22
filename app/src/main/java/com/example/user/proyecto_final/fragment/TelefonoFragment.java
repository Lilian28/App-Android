package com.example.user.proyecto_final.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.user.proyecto_final.R;
import com.example.user.proyecto_final.db.DbHelper;
import com.example.user.proyecto_final.db.dao.ContactoEmergenciaDao;
import com.example.user.proyecto_final.model.ContactoEmergencia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TelefonoFragment extends Fragment {

    private DbHelper helper;
    private ContactoEmergenciaDao contactoEmergenciaDao;
    ListView lv;

    public TelefonoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initDb();

        View v = inflater.inflate(R.layout.fragment_telefono, container, false);

        lv = v.findViewById(R.id.lista);
        lv.setAdapter(getAdapter());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, String> item = (HashMap<String, String>) adapterView.getAdapter().getItem(i);
                showDialog(item.get("telefono").toString());
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, String> item = (HashMap<String, String>) adapterView.getAdapter().getItem(i);
                final String nombre = item.get("nombre").toString();
                new AlertDialog.Builder(getContext())
                        .setMessage("Quieres eliminarlo?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                contactoEmergenciaDao.deleteByName(nombre);
                                lv.setAdapter(getAdapter());
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        FloatingActionButton addButton = v.findViewById(R.id.floatingActionButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewContactDialog();
            }
        });

        return v;
    }

    private void showNewContactDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Nuevo Contacto");

        LayoutInflater li = LayoutInflater.from(getContext());
        final View contactDialogView = li.inflate(R.layout.contact_dialog, null);

        builder.setView(contactDialogView);

        final EditText nombreInput = contactDialogView.findViewById(R.id.dialogNombreEditText);
        final EditText telefonoInput = contactDialogView.findViewById(R.id.dialogTelefonoEditText);

        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ContactoEmergencia contactoEmergencia = new ContactoEmergencia();
                contactoEmergencia.setNombre(nombreInput.getText().toString());
                contactoEmergencia.setTelefono(telefonoInput.getText().toString());
                contactoEmergenciaDao.save(contactoEmergencia);
                lv.setAdapter(getAdapter());
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void initDb() {
        helper = new DbHelper(getContext(), getResources());
        contactoEmergenciaDao = new ContactoEmergenciaDao(helper);
    }

    private void showDialog(final String telefono) {
        new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Aviso")
                .setMessage("¿Seguro que deseas llamar a " + telefono + "?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_CALL);

                        intent.setData(Uri.parse("tel:" + telefono));
                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(
                                    new String[]{Manifest.permission.CALL_PHONE},
                                    1);
                            return;
                        }
                        getContext().startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "Bien :). Ahora inténtalo nuevamente", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Que mal :(", Toast.LENGTH_LONG).show();
                }
            }
            return;
        }

    }

    private BaseAdapter getAdapter() {
        String[] from = new String[]{"nombre", "telefono"};
        int[] to = new int[]{R.id.nombre, R.id.telefono};

        List<ContactoEmergencia> contactos = contactoEmergenciaDao.getAll();

        ArrayList<HashMap<String, String>> eventos = new ArrayList<>();

        for (ContactoEmergencia item : contactos) {
            HashMap<String, String> datosEvento = new HashMap<>();
            datosEvento.put("nombre", item.getNombre());
            datosEvento.put("telefono", item.getTelefono());
            eventos.add(datosEvento);
        }


        SimpleAdapter listadoAdapter = new SimpleAdapter(getActivity(), eventos, R.layout.item_list_telefono, from, to);
        return listadoAdapter;
    }

    @Override
    public void onDestroy() {
        helper.close();
        super.onDestroy();
    }
}
