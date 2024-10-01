package com.example.notas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditarCursoActivity extends AppCompatActivity {
    private EditText edtCodigo, edtCurso, edtCarrera;
    private Button btnGuardar;
    private DeveloperuBD developeruBD;
    private String codigoOriginal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_curso);

        edtCodigo = findViewById(R.id.edtCodigo);
        edtCurso = findViewById(R.id.edtCurso);
        edtCarrera = findViewById(R.id.edtCarrera);
        btnGuardar = findViewById(R.id.btnGuardar);

        developeruBD = new DeveloperuBD(this);

        // Obtener los datos pasados desde ListaEstudiantesActivity
        codigoOriginal = getIntent().getStringExtra("codigo");
        String curso = getIntent().getStringExtra("curso");
        String carrera = getIntent().getStringExtra("carrera");

        // Llenar los campos con los datos del curso
        edtCodigo.setText(codigoOriginal);
        edtCurso.setText(curso);
        edtCarrera.setText(carrera);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nuevoCurso = edtCurso.getText().toString();
                String nuevaCarrera = edtCarrera.getText().toString();

                // Llamar al método actualizarCurso y verificar el resultado
                boolean actualizado = developeruBD.actualizarCurso(codigoOriginal, nuevoCurso, nuevaCarrera);
                if (actualizado) {
                    Toast.makeText(getApplicationContext(), "Curso actualizado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error al actualizar el curso", Toast.LENGTH_SHORT).show();
                }
                finish(); // Cerrar la actividad
            }
        });
    }
}
