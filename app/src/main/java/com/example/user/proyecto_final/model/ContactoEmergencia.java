package com.example.user.proyecto_final.model;

import android.database.Cursor;
import android.provider.BaseColumns;

import com.example.user.proyecto_final.util.CursorUtils;

public class ContactoEmergencia implements BaseColumns {
    private String nombre;
    private String telefono;

    public static ContactoEmergencia fromCursor(Cursor cursor) {
        ContactoEmergencia contactoEmergencia = new ContactoEmergencia();
        contactoEmergencia.setNombre((String) CursorUtils.getValueFromCursor(cursor, "nombre", String.class));
        contactoEmergencia.setTelefono((String) CursorUtils.getValueFromCursor(cursor, "telefono", String.class));
        return contactoEmergencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
