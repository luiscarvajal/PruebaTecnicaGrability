package com.grability.pruebatecnicagrability.services;

import android.app.IntentService;
import android.content.Intent;

import com.grability.pruebatecnicagrability.Sqlite.Helper;
import com.grability.pruebatecnicagrability.entities.Category;
import com.grability.pruebatecnicagrability.entities.SubCategory;

import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class SincService extends IntentService {
    public static final String REFRESH_DATA_INTENT = "servicio_de_descargas";

    public SincService() {
        super("SincService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Helper db = new Helper(this);

            if (db.getCategoryCount() == 0) {
                setDBTemp(db);
            }
            sendBroadcast(new Intent(SincService.REFRESH_DATA_INTENT));
        }
    }

    private void setDBTemp(Helper db) {
        for(int i=0;i<8;i++){
            Category category = new Category();
            category.set_id(i);
            category.set_nombre("Categoria" + i);
            category.set_descripcion("descripcion de la categoria - " + i);
            category.set_list_sub_category(new ArrayList<SubCategory>());
            for(int j =0;j<8;j++){
                SubCategory subCategory = new SubCategory();
                subCategory.set_id(j);
                subCategory.set_nombre("SubCategoria - "+j);
                subCategory.set_descripcion("Descripcion de la subCategoria - "+j);
                subCategory.set_img("root");
                category.get_list_sub_category().add(subCategory);
            }
            db.addCategory(category);
        }
    }
}
