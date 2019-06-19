package com.example.gmaps;

public class Person {
    private int id;
    private String nombre;
    private String direccion;
    private float lat;
    private float lng;

    public Person() {
    }

    public Person(int id, String nombre, String direccion, float lat, float lng) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
