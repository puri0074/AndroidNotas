package com.example.notas;

public class Curso {
    private String codigo;
    private String curso;
    private String carrera;

    public Curso(String codigo, String curso, String carrera) {
        this.codigo = codigo;
        this.curso = curso;
        this.carrera = carrera;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCurso() {
        return curso;
    }

    public String getCarrera() {
        return carrera;
    }

    @Override
    public String toString() {
        return "Código: " + codigo + ", Curso: " + curso + ", Carrera: " + carrera;
    }
}
