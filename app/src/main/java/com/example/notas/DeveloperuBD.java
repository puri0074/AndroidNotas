package com.example.notas;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DeveloperuBD extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "developeru.bd";
    private static final int VERSION_BD = 1;
    private static final String TABLA_CURSOS = "CREATE TABLE CURSOS(CODIGO TEXT PRIMARY KEY, CURSO TEXT, CARRERA TEXT)";

    public DeveloperuBD(@Nullable Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLA_CURSOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CURSOS");
        sqLiteDatabase.execSQL(TABLA_CURSOS);
    }

    public void agregarCursos(String codigo, String curso, String carrera) {
        SQLiteDatabase bd = getWritableDatabase();
        try {
            bd.execSQL("INSERT INTO CURSOS VALUES('" + codigo + "','" + curso + "','" + carrera + "')");
        } catch (Exception e) {
            e.printStackTrace(); // Imprime el error en la consola
        } finally {
            bd.close();
        }
    }

    public boolean actualizarCurso(String codigoOriginal, String nuevoCurso, String nuevaCarrera) {
        SQLiteDatabase bd = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("CURSO", nuevoCurso);
            contentValues.put("CARRERA", nuevaCarrera);

            int filasAfectadas = bd.update("CURSOS", contentValues, "CODIGO = ?", new String[]{codigoOriginal});
            return filasAfectadas > 0; // Devuelve true si se actualizó al menos una fila
        } catch (Exception e) {
            e.printStackTrace(); // Imprime el error en la consola
            return false; // Indica que hubo un error
        } finally {
            bd.close();
        }
    }

    public void eliminarCurso(String codigo) {
        SQLiteDatabase bd = getWritableDatabase();
        try {
            bd.execSQL("DELETE FROM CURSOS WHERE CODIGO = ?", new Object[]{codigo});
        } catch (Exception e) {
            e.printStackTrace(); // Imprime el error en la consola
        } finally {
            bd.close();
        }
    }
}
