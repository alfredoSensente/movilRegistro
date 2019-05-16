package com.example.movilesregistro;

import android.net.Uri;

public class Student {
    String nombre;
    String edad;
    String carnet;
    String carrera;
    Uri foto;

    public Student(String nombre, String edad, String carnet, String carrera, Uri foto) {
        this.nombre = nombre;
        this.edad = edad;
        this.carnet = carnet;
        this.carrera = carrera;
        this.foto = foto;
    }
}
