package com.example.sqlite;

import java.io.Serializable;

public class Dto1  {
    int id;
    String tipo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Dto1() {
        this.id = id;
        this.tipo = tipo;
    }
}
