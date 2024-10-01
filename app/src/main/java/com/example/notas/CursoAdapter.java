package com.example.notas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;


import java.util.ArrayList;

public class CursoAdapter extends ArrayAdapter<Curso> {

    private final Context context;
    private final ArrayList<Curso> cursos;

    public CursoAdapter(Context context, ArrayList<Curso> cursos) {
        super(context, R.layout.item_curso, cursos);
        this.context = context;
        this.cursos = cursos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_curso, parent, false);
        }

        TextView textViewCurso = convertView.findViewById(R.id.textViewCurso);
        Button btnEliminar = convertView.findViewById(R.id.btnEliminar);
        Button btnEditar = convertView.findViewById(R.id.btnEditar);

        Curso curso = cursos.get(position);
        textViewCurso.setText("Código: " + curso.getCodigo() + ", Curso: " + curso.getCurso() + ", Carrera: " + curso.getCarrera());

        // Lógica para eliminar el curso
        btnEliminar.setOnClickListener(v -> {
            cursos.remove(position);
            notifyDataSetChanged();
            // Aquí también debes eliminar el curso de la base de datos
            DeveloperuBD developeruBD = new DeveloperuBD(context);
            developeruBD.eliminarCurso(curso.getCodigo());
        });

        // Lógica para editar el curso
        btnEditar.setOnClickListener(v -> {
            // Implementar la lógica para editar el curso, como abrir una nueva actividad
            Intent intent = new Intent(context, EditarCursoActivity.class);
            intent.putExtra("codigo", curso.getCodigo());
            intent.putExtra("curso", curso.getCurso());
            intent.putExtra("carrera", curso.getCarrera());
            context.startActivity(intent);
        });

        return convertView;
    }
}
