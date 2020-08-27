package com.example.mywisp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywisp.entities.Instalation;
import com.example.mywisp.utilities.Tables;

public class instalation_details extends AppCompatActivity {

    SQLiteHelperConnection connection;

    TextView tvIdInstalation, tvDateInstalation, tvMaterialsInstalation, tvPlaceInstalation, tvIdService, tvNameService, tvPriceService, tvDescriptionService, tvIdCustomer, tvFullnameCustomer, tvPhoneCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instalation_details);

        connection = new SQLiteHelperConnection(getApplicationContext(), "mywisp", null, 1);

        tvIdInstalation = (TextView) findViewById(R.id.tvIdInstalationDetails);
        tvDateInstalation = (TextView) findViewById(R.id.tvDateInstalationDetails);
        tvMaterialsInstalation = (TextView) findViewById(R.id.tvMaterialsInstalationDetails);
        tvPlaceInstalation = (TextView) findViewById(R.id.tvPlaceInstalationDetails);

        tvIdService = (TextView) findViewById(R.id.tvIdServiceInstalationDetails);
        tvNameService = (TextView) findViewById(R.id.tvNameServiceInstalationDetails);
        tvPriceService = (TextView) findViewById(R.id.tvPriceServiceInstalationDetails);
        tvDescriptionService = (TextView) findViewById(R.id.tvDescriptionServiceInstalationDetails);

        tvIdCustomer = (TextView) findViewById(R.id.tvIdCustomerInstalationDetails);
        tvFullnameCustomer = (TextView) findViewById(R.id.tvFullnameCustomerInstalationDetails);
        tvPhoneCustomer = (TextView) findViewById(R.id.tvPhoneCustomerInstalationDetails);

        Bundle objectSent = getIntent().getExtras();
        Instalation instalation = null;

        if (objectSent != null)
        {
            instalation = (Instalation) objectSent.getSerializable("instalation");
            tvIdInstalation.setText(instalation.getId().toString());
            tvDateInstalation.setText(instalation.getDate().toString());
            tvMaterialsInstalation.setText(instalation.getMaterials().toString());
            tvPlaceInstalation.setText(instalation.getPlace().toString());

            consultCustomer(instalation.getIdCustomer());
            consultService(instalation.getIdService());
        }
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnUpdateInstalationActivity:
                sendInformationInstalation();
                break;
            case R.id.btnDeleteInstalation:
                deleteInstalationSQL();
                break;

        }
    }

    private void deleteInstalationSQL() {
        SQLiteDatabase database = connection.getWritableDatabase();

        String query = "DELETE FROM "+Tables.INSTALATIONS_TABLE+" WHERE "+Tables.INSTALATIONS_ID_FIELD+"="+tvIdInstalation.getText().toString();

        try
        {
            database.execSQL(query);
            Toast.makeText(getApplicationContext(), "La instalación se ha eliminado correctamente.", Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), "Falló al eliminar la instalación, ha ocurrido un error.", Toast.LENGTH_SHORT).show();
        }

        database.close();
    }

    private void sendInformationInstalation() {
        Instalation instalation = new Instalation(null, null, null, null, null, null);
        instalation.setId(tvIdInstalation.getText().toString());
        instalation.setDate(tvDateInstalation.getText().toString());
        instalation.setMaterials(tvMaterialsInstalation.getText().toString());
        instalation.setPlace(tvPlaceInstalation.getText().toString());
        instalation.setIdCustomer(tvIdCustomer.getText().toString());
        instalation.setIdService(tvIdService.getText().toString());

        Intent intent = new Intent(instalation_details.this, update_instalation.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("update_instalation", instalation);

        intent.putExtras(bundle);
        startActivity(intent);

    }

    private void consultService(String idService) {
        SQLiteDatabase database = connection.getReadableDatabase();

        String query = "SELECT * FROM "+Tables.SERVICES_TABLE+" WHERE "+Tables.INSTALATIONS_IDSERVICE_FIELD+"="+idService.toString();

        try
        {
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();

            tvIdService.setText(idService.toString());
            tvNameService.setText(cursor.getString(0));
            tvPriceService.setText(cursor.getString(1));
            tvDescriptionService.setText(cursor.getString(2));
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), "Falló al traer el plan de servicio asociado.", Toast.LENGTH_SHORT).show();
        }

        database.close();
    }

    private void consultCustomer(String idCustomer) {
        SQLiteDatabase database = connection.getReadableDatabase();

        String query = "SELECT "+ Tables.ID_FIELD+", "+Tables.FULLNAME_FIELD+", "+Tables.PHONE_FIELD+" FROM "+Tables.CUSTOMERS_TABLE+" WHERE "+Tables.ID_FIELD+"="+idCustomer;

        try
        {
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();

            tvIdCustomer.setText(idCustomer.toString());
            tvFullnameCustomer.setText(cursor.getString(0));
            tvPhoneCustomer.setText(cursor.getString(1));

        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), "Falló al traer información del cliente asociado.", Toast.LENGTH_SHORT).show();
            tvIdCustomer.setText("");
            tvFullnameCustomer.setText("");
            tvPhoneCustomer.setText("");
        }

        database.close();
    }
}