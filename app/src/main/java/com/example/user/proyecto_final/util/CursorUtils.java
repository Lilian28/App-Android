package com.example.user.proyecto_final.util;

import android.database.Cursor;

public final class CursorUtils {
    private CursorUtils() {
    }

    public static Object getValueFromCursor(Cursor cursor, String columname, Class clazz) {
        if (clazz.equals(String.class))
            return cursor.getString(cursor.getColumnIndexOrThrow(columname));
        else
            throw new RuntimeException("Ohh different type O_O");
    }
}
