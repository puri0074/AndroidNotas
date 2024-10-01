package com.example.notas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText edtCódigo, edtCurso, edtCarrera;
    Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        edtCódigo = findViewById(R.id.edtCódigo);
        edtCurso = findViewById(R.id.edtCurso);
        edtCarrera = findViewById(R.id.edtCarrera);
        btnAgregar = findViewById(R.id.btnAgregar);

        final DeveloperuBD developeruBD = new DeveloperuBD(getApplicationContext());

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Agregar el curso a la base de datos
                developeruBD.agregarCursos(
                        edtCódigo.getText().toString(),
                        edtCurso.getText().toString(),
                        edtCarrera.getText().toString()
                );

                // Mostrar mensaje de éxito
                Toast.makeText(getApplicationContext(), "SE AGREGÓ CORRECTAMENTE", Toast.LENGTH_SHORT).show();

                // Limpiar los campos de entrada
                edtCódigo.setText("");
                edtCurso.setText("");
                edtCarrera.setText("");

                // Navegar a la actividad de lista de estudiantes
                Intent intent = new Intent(MainActivity.this, ListaEstudiantesActivity.class);
                startActivity(intent);
            }
        });
    }
}
