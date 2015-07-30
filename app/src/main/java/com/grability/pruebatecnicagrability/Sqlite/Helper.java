package com.grability.pruebatecnicagrability.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.grability.pruebatecnicagrability.entities.Category;
import com.grability.pruebatecnicagrability.entities.SubCategory;

import java.util.ArrayList;

/**
 * Created by luiscarvajal on 7/28/15.
 */
public class Helper
        extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 8;

    // Database Name
    private static final String DATABASE_NAME = "pruebatecnicagrability";


    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CATEGORY.CREATE_TABLE);
        db.execSQL(SUBCATEGORY.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL(CATEGORY.DROP_TABLE);
        db.execSQL(SUBCATEGORY.DROP_TABLE);
        // Create tables again
        onCreate(db);
    }

    public void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CATEGORY.KEY_ID, category.get_id());
        values.put(CATEGORY.KEY_NAME, category.get_nombre());
        values.put(CATEGORY.KEY_DESCRIPCION, category.get_descripcion());

        // Inserting Row
        db.insert(CATEGORY.TABLE_NAME, null, values);

        for (SubCategory subCategory : category.get_list_sub_category()!=null?
                category.get_list_sub_category():new ArrayList<SubCategory>()) {
            ContentValues subContentValues = new ContentValues();
            subContentValues.put(SUBCATEGORY.KEY_ID, subCategory.get_id());
            subContentValues.put(SUBCATEGORY.KEY_NAME, subCategory.get_nombre());
            subContentValues.put(SUBCATEGORY.KEY_DESCRIPCION, subCategory.get_descripcion());
            subContentValues.put(SUBCATEGORY.KEY_IMAGEN,subCategory.get_img());
            subContentValues.put(SUBCATEGORY.KEY_CATEGORY_ID, category.get_id());
            db.insert(SUBCATEGORY.TABLE_NAME, null, subContentValues);
        }

        db.close(); // Closing database connection

        ArrayList<Category> lista = getAllCategory();
        Log.d("","");
    }

    public ArrayList<Category> getAllCategory() {
        ArrayList<Category> categoryList = new ArrayList<Category>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(false, CATEGORY.TABLE_NAME, new String[]{CATEGORY.KEY_ID,
                        CATEGORY.KEY_NAME, CATEGORY.KEY_DESCRIPCION},
                null, null, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.set_id(cursor.getLong(0));
                category.set_nombre(cursor.getString(1));
                category.set_descripcion(cursor.getString(2));
                category.set_list_sub_category(getSubCategory(category.get_id()));

                categoryList.add(category);
            } while (cursor.moveToNext());
        }

        return categoryList;
    }

    private ArrayList<SubCategory> getSubCategory(long id) {
        ArrayList<SubCategory> subCategoryList = new ArrayList<SubCategory>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(false, SUBCATEGORY.TABLE_NAME, new String[]{SUBCATEGORY.KEY_ID,
                        SUBCATEGORY.KEY_NAME, SUBCATEGORY.KEY_DESCRIPCION,SUBCATEGORY.KEY_IMAGEN},
                SUBCATEGORY.KEY_CATEGORY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                SubCategory subCategory = new SubCategory();
                subCategory.set_id(cursor.getLong(0));
                subCategory.set_nombre(cursor.getString(1));
                subCategory.set_descripcion(cursor.getString(2));
                subCategory.set_img(cursor.getString(3));
                subCategoryList.add(subCategory);
            } while (cursor.moveToNext());
        }
        return subCategoryList;
    }



    public int getCategoryCount() {
        String countQuery = "SELECT  * FROM " + CATEGORY.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int ret = cursor.getCount();
        cursor.close();
        return ret;
    }

    private static final class CATEGORY implements BaseColumns {

        private static final String TABLE_NAME = "category";

        private static final String KEY_ID ="_id";
        private static final String KEY_NAME = "name";
        private static final String KEY_DESCRIPCION = "enable";

        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_DESCRIPCION + " TEXT" + ")";

        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    private static final class SUBCATEGORY implements BaseColumns {
        private static final String TABLE_NAME = "SUB_CATEGORY";

        private static final String KEY_ID ="_id";
        private static final String KEY_NAME = "name";
        private static final String KEY_DESCRIPCION = "descripcion";
        private static final String KEY_IMAGEN = "imagen";
        private static final String KEY_CATEGORY_ID = "category_id";

        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " " +
                "INTEGER," + KEY_NAME + " TEXT," + KEY_DESCRIPCION +
                " TEXT,"+KEY_IMAGEN+" TEXT," + KEY_CATEGORY_ID + " INTEGER)";

        private static final String DROP_TABLE = " DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
