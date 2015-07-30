package com.grability.pruebatecnicagrability.entities;

import java.io.Serializable;

/**
 * Created by luiscarvajal on 7/28/15.
 */
public class SubCategory implements Serializable{
    private long _id;
    private String _nombre;
    private String _descripcion;
    private String _img;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }

    public String get_descripcion() {
        return _descripcion;
    }

    public void set_descripcion(String _descripcion) {
        this._descripcion = _descripcion;
    }

    public String get_img() {
        return _img;
    }

    public void set_img(String _img) {
        this._img = _img;
    }
}
