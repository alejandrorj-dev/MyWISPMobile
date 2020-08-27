package com.example.mywisp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.mywisp.entities.Instalation;
import com.example.mywisp.utilities.Tables;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class instalation_list extends AppCompatActivity {

    EditText etSearchCriteria;

    ListView ListViewInstalation;

    ArrayList<Instalation> InstalationsList;
    ArrayList<String> InformationList;

    ArrayList<Instalation> InstalationsResult;
    ArrayList<String> InformationSearchingList;

    ArrayAdapter SearchAdapter;

    SQLiteHelperConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instalation_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        etSearchCriteria = (EditText) findViewById(R.id.etSearchInstalation);

        ListViewInstalation = (ListView) findViewById(R.id.lvInstalations);

        connection = new SQLiteHelperConnection(getApplicationContext(), "mywisp", null, 1);

        consultInstalationsList();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, InformationList);
        ListViewInstalation.setAdapter(adapter);


        ListViewInstalation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String information = "id: "+InstalationsList.get(position).getId()+"\n";
                information+="Fecha: "+InstalationsList.get(position).getDate()+"\n";
                information+="Materiales:"+InstalationsList.get(position).getMaterials()+"\n";
                information+="Lugar: "+InstalationsList.get(position).getPlace()+"\n";

                Instalation instalation = InstalationsList.get(position);

                Intent intent = new Intent(instalation_list.this, instalation_details.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("instalation", instalation);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void onClick(View view)
    {
        searchInstalationSQL(etSearchCriteria.getText().toString());
    }

    private void searchInstalationSQL(String Criteria) {
        SQLiteDatabase database = connection.getReadableDatabase();

        Instalation instalation = null;

        InstalationsResult = new ArrayList<Instalation>();

        String query = "SELECT * FROM "+Tables.INSTALATIONS_TABLE+" WHERE "+Tables.INSTALATIONS_ID_FIELD+"="+etSearchCriteria.getText().toString()+" OR "+Tables.INSTALATIONS_DATE_FIELD+" LIKE '"+etSearchCriteria.getText().toString()+"'% OR "+Tables.INSTALATIONS_MATERIALS_FIELD+" LIKE '"+etSearchCriteria.getText().toString()+"'% OR "+Tables.INSTALATIONS_PLACE_FIELD+" LIKE '"+etSearchCriteria.getText().toString()+"'% OR "+Tables.INSTALATIONS_IDCUSTOMER_FIELD+"="+etSearchCriteria.getText().toString()+" OR "+Tables.INSTALATIONS_IDSERVICE_FIELD+"="+etSearchCriteria.getText().toString();

        Cursor cursor = database.rawQuery(query, null);

        while (cursor.moveToNext())
        {
            instalation.setIdService(cursor.getString(0));
            instalation.setDate(cursor.getString(1));
            instalation.setMaterials(cursor.getString(2));
            instalation.setPlace(cursor.getString(3));
            instalation.setIdCustomer(cursor.getString(4));
            instalation.setIdService(cursor.getString(5));

            InstalationsResult.add(instalation);

        }

        fillInstalationsListViewSearchingResults();

        SearchAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, InformationSearchingList);
        ListViewInstalation.setAdapter(SearchAdapter);

        database.close();
    }

    private void fillInstalationsListViewSearchingResults() {
        InformationSearchingList = new ArrayList<String>();

        for (int i = 0; i < InstalationsResult.size(); i++)
        {
            InformationSearchingList.add(InstalationsResult.get(i).getId()+"\n"+InstalationsResult.get(i).getDate()+"\n"+InstalationsResult.get(0).getPlace());
        }

    }

    // This method consult the instalations list from database
    private void consultInstalationsList()
    {
        SQLiteDatabase database = connection.getReadableDatabase();

        Instalation instalations = null;

        InstalationsList = new ArrayList<Instalation>();

        Cursor cursor = database.rawQuery("SELECT * FROM "+ Tables.INSTALATIONS_TABLE, null);

        while (cursor.moveToNext())
        {
            instalations.setId(cursor.getString(0));
            instalations.setDate(cursor.getString(1));
            instalations.setMaterials(cursor.getString(2));
            instalations.setPlace(cursor.getString(3));
            instalations.setIdCustomer(cursor.getString(4));
            instalations.setIdService(cursor.getString(5));

            InstalationsList.add(instalations);
        }

        fillInstalationsListView();

        database.close();
    }

    // This method fill lvInstalations ListView
    private void fillInstalationsListView() {
        InformationList = new ArrayList<String>();

        for (int i = 0; i < InstalationsList.size(); i++)
        {
            InformationList.add(InstalationsList.get(i).getId()+"\n"+InstalationsList.get(i).getDate()+"\n"+InstalationsList.get(i).getPlace());
        }

    }


}