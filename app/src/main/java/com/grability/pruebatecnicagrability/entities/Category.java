package com.grability.pruebatecnicagrability.entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by luiscarvajal on 7/28/15.
 */
public class Category implements Serializable{
    private long _id;
    private String _nombre;
    private String _descripcion;
    private ArrayList<SubCategory> _list_sub_category;

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

    public ArrayList<SubCategory> get_list_sub_category() {
        return _list_sub_category;
    }

    public void set_list_sub_category(ArrayList<SubCategory> _list_sub_category) {
        this._list_sub_category = _list_sub_category;
    }
}
