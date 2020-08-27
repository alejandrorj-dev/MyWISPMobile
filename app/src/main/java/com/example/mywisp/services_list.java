package com.example.mywisp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.mywisp.entities.Services;
import com.example.mywisp.utilities.Tables;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class services_list extends AppCompatActivity {

    ListView ServicesListView;
    ArrayList<String> InformationList;
    ArrayList<Services> ServicesList;


    SQLiteHelperConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddServicesActivity = new Intent(services_list.this, add_service.class);
                startActivity(AddServicesActivity);
            }
        });

        connection = new SQLiteHelperConnection(getApplicationContext(), "mywisp", null, 1);

        ServicesListView = (ListView) findViewById(R.id.lvServices);

        ConsultServicesList();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, InformationList);

        ServicesListView.setAdapter(adapter);

        // This send the service information to service_details activity as an object
        ServicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                String information = "id: "+ServicesList.get(pos).getId()+"\n";
                information+="Nombre: "+ServicesList.get(pos).getName()+"\n";
                information+="Precio: "+ServicesList.get(pos).getPrice()+"\n";
                information+="Descripción: "+ServicesList.get(pos).getDescription();

                Services service = ServicesList.get(pos);

                Intent intent = new Intent(services_list.this, service_details.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("service", service);

                intent.putExtras(bundle);
                startActivity(intent);


            }
        });

    }

    // Consult services list from database
    private void ConsultServicesList() {
        SQLiteDatabase database = connection.getReadableDatabase();

        Services services = null;

        ServicesList = new ArrayList<Services>();

        Cursor cursor = database.rawQuery("SELECT * FROM "+ Tables.SERVICES_TABLE, null);

        while (cursor.moveToNext())
        {
            services.setId(cursor.getString(0));
            services.setName(cursor.getString(1));
            services.setPrice(cursor.getString(2));
            services.setDescription(cursor.getString(3));

            ServicesList.add(services);
        }

        fillInformationList();

        database.close();
    }

    // fill the InformationList ArrayList with the services list obtained
    private void fillInformationList() {
        InformationList = new ArrayList<String>();

        for (int i = 0; i < ServicesList.size(); i++)
        {
            InformationList.add(ServicesList.get(i).getId()+"\n"+ServicesList.get(i).getName()+"\n"+ServicesList.get(i).getPrice());
        }
    }
}