package com.example.mywisp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mywisp.utilities.Tables;

public class add_service extends AppCompatActivity {

    EditText txtName, txtPrice, txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        txtName = (EditText) findViewById(R.id.txtNameService);
        txtPrice = (EditText) findViewById(R.id.txtPriceService);
        txtDescription = (EditText) findViewById(R.id.txtDescriptionService);
    }

    public void onClick(View view)
    {
        AddServiceSQL();
    }

    private void AddServiceSQL()
    {
        SQLiteHelperConnection connection = new SQLiteHelperConnection(this, "mywisp", null, 1);
        SQLiteDatabase database = connection.getReadableDatabase();

        String query = "INSERT INTO "+ Tables.SERVICES_TABLE+"("+Tables.SERVICES_NAME_FIELD+", "+Tables.SERVICES_PRICE_FIELD+", "+Tables.SERVICES_DESCRIPTION_FIELD+") VALUES("+txtName.getText().toString()+", '"+txtPrice.getText().toString()+"', '"+txtDescription.getText().toString()+"')";

        try
        {
            database.execSQL(query);
            Toast.makeText(getApplicationContext(), "El plan de servicio ha sido ingresado exitosamente.", Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), "Falló al ingresar plan de servicio, ha ocurrido un error.", Toast.LENGTH_SHORT).show();
        }

        database.close();
    }
}