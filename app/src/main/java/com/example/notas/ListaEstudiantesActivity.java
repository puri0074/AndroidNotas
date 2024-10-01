package com.example.notas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListaEstudiantesActivity extends AppCompatActivity {

    ListView listViewEstudiantes;
    DeveloperuBD developeruBD;
    ArrayList<String> estudiantes;
    ArrayList<String> codigos; // Para almacenar los códigos y poder eliminarlos
    ArrayAdapter<String> adapter; // Definir el adaptador aquí para usarlo en onResume

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_estudiantes);

        listViewEstudiantes = findViewById(R.id.listViewEstudiantes);
        developeruBD = new DeveloperuBD(this);
        estudiantes = new ArrayList<>();
        codigos = new ArrayList<>();

        // Obtener los datos de la base de datos
        obtenerEstudiantes();

        // Configurar el adaptador para el ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, estudiantes);
        listViewEstudiantes.setAdapter(adapter);

        // Configurar el listener para mostrar opciones de eliminar o editar
        listViewEstudiantes.setOnItemClickListener((parent, view, position, id) -> {
            String codigo = codigos.get(position);
            String[] detalles = estudiantes.get(position).split(", "); // Divide los detalles para obtener el curso y la carrera
            String curso = detalles[1].split(": ")[1]; // Extrae el nombre del curso
            String carrera = detalles[2].split(": ")[1]; // Extrae el nombre de la carrera

            // Mostrar opciones de eliminar o editar
            mostrarOpciones(codigo, curso, carrera, position);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        obtenerEstudiantes(); // Obtener los estudiantes nuevamente
        adapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
    }

    private void obtenerEstudiantes() {
        estudiantes.clear(); // Limpiar la lista antes de cargar nuevos datos
        codigos.clear(); // Limpiar la lista de códigos antes de cargar nuevos datos
        Cursor cursor = developeruBD.getReadableDatabase().rawQuery("SELECT * FROM CURSOS", null);
        if (cursor.moveToFirst()) {
            do {
                String codigo = cursor.getString(cursor.getColumnIndex("CODIGO"));
                String curso = cursor.getString(cursor.getColumnIndex("CURSO"));
                String carrera = cursor.getString(cursor.getColumnIndex("CARRERA"));

                estudiantes.add("Código: " + codigo + ", Curso: " + curso + ", Carrera: " + carrera);
                codigos.add(codigo); // Guardar el código correspondiente
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void mostrarOpciones(String codigo, String curso, String carrera, int position) {
        String[] opciones = {"Editar", "Eliminar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona una opción")
                .setItems(opciones, (dialog, which) -> {
                    if (which == 0) { // Opción "Editar"
                        Intent intent = new Intent(ListaEstudiantesActivity.this, EditarCursoActivity.class);
                        intent.putExtra("codigo", codigo);
                        intent.putExtra("curso", curso);
                        intent.putExtra("carrera", carrera);
                        startActivity(intent);
                    } else if (which == 1) { // Opción "Eliminar"
                        mostrarDialogoEliminar(codigo, position);
                    }
                })
                .show();
    }

    private void mostrarDialogoEliminar(String codigo, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar Curso")
                .setMessage("¿Estás seguro de que deseas eliminar el curso con código: " + codigo + "?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    developeruBD.eliminarCurso(codigo); // Llamar al método para eliminar el curso de la BD
                    estudiantes.remove(position); // Eliminar de la lista
                    codigos.remove(position); // Eliminar de la lista de códigos
                    adapter.notifyDataSetChanged(); // Notificar al adaptador
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
