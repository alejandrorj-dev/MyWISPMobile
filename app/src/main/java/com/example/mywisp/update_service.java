package com.example.mywisp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mywisp.entities.Services;
import com.example.mywisp.utilities.Tables;

public class update_service extends AppCompatActivity {

    EditText txtNameService, txtPriceService, txtDescriptionService;
    String idService;

    SQLiteHelperConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_service);

        connection = new SQLiteHelperConnection(getApplicationContext(), "mywisp", null, 1);

        //txtIdService = (EditText) findViewById(R.id.txtId);
        txtNameService = (EditText) findViewById(R.id.txtNameUpdateService);
        txtPriceService = (EditText) findViewById(R.id.txtPriceUpdateService);
        txtDescriptionService = (EditText) findViewById(R.id.txtDescriptionUpdateService);

        Bundle objectSent = getIntent().getExtras();
        Services service = null;

        if (objectSent != null)
        {
            service = (Services) objectSent.getSerializable("service");
            idService = service.getId().toString();
            txtNameService.setText(service.getName().toString());
            txtPriceService.setText(service.getPrice().toString());
            txtDescriptionService.setText(service.getDescription().toString());
        }
    }

    public void onClick(View view)
    {
        updateServiceSQL();
    }

    private void updateServiceSQL() {
        SQLiteDatabase database = connection.getWritableDatabase();

        String query = "UPDATE "+ Tables.SERVICES_TABLE+" SET "+Tables.SERVICES_NAME_FIELD+"="+txtNameService.getText().toString()+", "+Tables.SERVICES_PRICE_FIELD+"="+txtPriceService.getText().toString()+", "+Tables.SERVICES_DESCRIPTION_FIELD+"="+txtDescriptionService.getText().toString()+" WHERE "+Tables.SERVICES_ID_FIELD+"="+idService;

        try
        {
            database.execSQL(query);
            Toast.makeText(getApplicationContext(), "El plan de servicio ha sido actualizado correctamente.", Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), "Falló al actualizar el plan de servicio, ha ocurrido un error.", Toast.LENGTH_SHORT).show();
        }

        database.close();
    }
}