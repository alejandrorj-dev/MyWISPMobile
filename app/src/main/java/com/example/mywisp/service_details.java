package com.example.mywisp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywisp.entities.Customer;
import com.example.mywisp.entities.Services;
import com.example.mywisp.utilities.Tables;

import java.security.Provider;
import java.util.ArrayList;

public class service_details extends AppCompatActivity {

    TextView tvIdService, tvNameService, tvPriceService, tvDescriptionService;
    ArrayList<Services> ServiceList;

    SQLiteHelperConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        connection = new SQLiteHelperConnection(getApplicationContext(), "mywisp", null, 1);

        tvIdService = (TextView) findViewById(R.id.tvIdServiceDetails);
        tvNameService = (TextView) findViewById(R.id.tvNameServiceDetails);
        tvPriceService = (TextView) findViewById(R.id.tvDescriptionServiceDetails);
        tvDescriptionService = (TextView) findViewById(R.id.tvDescriptionServiceDetails);

        // This receive the service information sent from services_list activity
        Bundle objectSent = getIntent().getExtras();
        Services services = null;

        if(objectSent != null)
        {
            services = (Services) objectSent.getSerializable("service");
            tvIdService.setText(services.getId().toString());
            tvNameService.setText(services.getName().toString());
            tvPriceService.setText(services.getPrice().toString());
            tvDescriptionService.setText(services.getDescription().toString());
        }
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnUpdateService:
                sendInformationService();
                break;
            case R.id.btnDeleteService:
                deleteServiceSQL();
                break;
        }
    }

    private void deleteServiceSQL() {
        SQLiteDatabase database = connection.getWritableDatabase();

        String query = "DELETE FROM "+ Tables.SERVICES_TABLE+" WHERE "+Tables.SERVICES_ID_FIELD+"="+tvIdService.getText().toString();

        try
        {
            database.execSQL(query);
            Toast.makeText(getApplicationContext(), "El plan de servicio se eliminó correctamente.", Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), "Falló al eliminar el plan de servicio, ha ocurrido un error.", Toast.LENGTH_SHORT).show();
        }

        database.close();

    }

    private void sendInformationService()
    {
        Services services = new Services(null, null, null, null);
        services.setId(tvIdService.getText().toString());
        services.setName(tvNameService.getText().toString());
        services.setPrice(tvPriceService.getText().toString());
        services.setDescription(tvDescriptionService.getText().toString());

        Intent intent = new Intent(service_details.this, update_service.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("service", services);

        intent.putExtras(bundle);
        startActivity(intent);

    }
}