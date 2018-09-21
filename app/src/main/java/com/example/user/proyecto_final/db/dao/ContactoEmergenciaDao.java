package com.example.user.proyecto_final.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.user.proyecto_final.db.DbHelper;
import com.example.user.proyecto_final.model.ContactoEmergencia;

import java.util.ArrayList;
import java.util.List;

public class ContactoEmergenciaDao {
    private DbHelper helper;

    public ContactoEmergenciaDao(DbHelper helper) {
        this.helper = helper;
    }

    public List<ContactoEmergencia> getAll() {
        List<ContactoEmergencia> contactos = new ArrayList<>();

        Cursor result = helper.getReadableDatabase()
                .query("ContactoEmergencia",
                        new String[]{"nombre", "telefono"},
                        null,
                        null,
                        null,
                        null,
                        "nombre");

        while (result.moveToNext())
            contactos.add(ContactoEmergencia.fromCursor(result));

        return contactos;
    }

    public void save(ContactoEmergencia contactoEmergencia) {
        ContentValues values = new ContentValues();
        values.put("nombre", contactoEmergencia.getNombre());
        values.put("telefono", contactoEmergencia.getTelefono());
        helper.getWritableDatabase().insert("ContactoEmergencia", null, values);
    }

    public void deleteByName(String nombre) {
        helper.getWritableDatabase()
                .delete("ContactoEmergencia", "nombre = ?", new String[]{nombre});
    }
}
