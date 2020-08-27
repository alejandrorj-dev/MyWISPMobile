package com.example.mywisp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywisp.entities.Customer;
import com.example.mywisp.entities.Instalation;
import com.example.mywisp.entities.Services;
import com.example.mywisp.utilities.Tables;

import java.util.ArrayList;
import java.util.Calendar;

public class update_instalation extends AppCompatActivity {

    SQLiteHelperConnection connection;

    EditText etDate, etMaterials, etPlace;
    Spinner spIdCustomer, spIdService;
    TextView tvIdInstalation, tvNameService, tvNameCustomer;

    ArrayList<String> CustomerList;
    ArrayList<Customer> CustomersArrayList;

    ArrayList<String> ServiceList;
    ArrayList<Services> ServicesArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_instalation);

        connection = new SQLiteHelperConnection(getApplicationContext(), "mywisp", null, 1);

        tvIdInstalation = (TextView) findViewById(R.id.tvIdInstalationUpdateInstalation);
        etDate = (EditText) findViewById(R.id.etDateUpdateInstalation);
        etMaterials = (EditText) findViewById(R.id.etMaterialsUpdateInstalation);
        etPlace = (EditText) findViewById(R.id.etPlaceUpateInstalation);
        spIdCustomer = (Spinner) findViewById(R.id.spIdCustomerUpdateInstalation);
        tvNameCustomer = (TextView) findViewById(R.id.tvNameCustomerUpdateInstalation);
        spIdService = (Spinner) findViewById(R.id.spIdServiceUpdateInstalation);
        tvNameService = (TextView) findViewById(R.id.tvNameServiceUpdateInstalation);

        ArrayAdapter<CharSequence> CustomerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, CustomerList);
        spIdCustomer.setAdapter(CustomerAdapter);

        ArrayAdapter<CharSequence> ServiceAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ServiceList);
        spIdService.setAdapter(ServiceAdapter);

        spIdCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvNameCustomer.setText(CustomersArrayList.get(position).getFullname());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spIdService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvNameService.setText(ServicesArrayList.get(position).getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Bundle ObjectSent = getIntent().getExtras();

        Instalation instalation = null;

        if (ObjectSent != null)
        {
            instalation = (Instalation) ObjectSent.getSerializable("update_instalation");
            tvIdInstalation.setText(instalation.getId());
            etDate.setText(instalation.getDate());
            etMaterials.setText(instalation.getMaterials());
            etPlace.setText(instalation.getPlace());

            ServiceList.add(0, instalation.getIdService());
            spIdService.setSelection(0);

            CustomerList.add(0, instalation.getIdCustomer());
            spIdCustomer.setSelection(0);


        }

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(calendar.YEAR);
        final int month = calendar.get(calendar.MONTH);
        final int day = calendar.get(calendar.DAY_OF_MONTH);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(update_instalation.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = day+"/"+month+"/"+year;
                        etDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        consultServicesList();
        consultCustomersList();
    }

    public void onClick(View view)
    {
        updateInstalationSQL();
    }

    private void updateInstalationSQL() {
        SQLiteDatabase database = connection.getWritableDatabase();

        String query = "UPDATE FROM "+Tables.INSTALATIONS_TABLE+" SET "+Tables.INSTALATIONS_DATE_FIELD+"='"+etDate.getText().toString()+"', "+Tables.INSTALATIONS_MATERIALS_FIELD+"='"+etMaterials.getText().toString()+"', "+Tables.INSTALATIONS_PLACE_FIELD+"='"+etPlace.getText().toString()+"', "+Tables.INSTALATIONS_IDSERVICE_FIELD+"="+spIdService.getSelectedItem()+", "+Tables.INSTALATIONS_IDCUSTOMER_FIELD+"="+spIdCustomer.getSelectedItem()+" WHERE "+Tables.INSTALATIONS_ID_FIELD+"="+tvIdInstalation.getText().toString();

        try
        {
           database.execSQL(query);
            Toast.makeText(getApplicationContext(), "La instalación se ha actualizado correctamente.", Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), "Falló al actualizar la instalación, ha ocurrido un error.", Toast.LENGTH_SHORT).show();
        }

        database.close();
    }

    private void consultServicesList() {
        SQLiteDatabase database = connection.getReadableDatabase();

        Services service = null;
        ServicesArrayList = new ArrayList<Services>();

        Cursor cursor = database.rawQuery("SELECT "+Tables.SERVICES_ID_FIELD+", "+Tables.SERVICES_NAME_FIELD+" FROM "+ Tables.SERVICES_TABLE, null);

        while (cursor.moveToNext())
        {
            service = new Services(null, null, null, null);
            service.setId(cursor.getString(0));
            service.setName(cursor.getString(1));

            ServicesArrayList.add(service);
        }

        fillServicesList();
    }

    private void fillServicesList() {
        ServiceList = new ArrayList<String>();

        for (int i = 1; i < ServicesArrayList.size(); i++)
        {
            ServiceList.add(ServicesArrayList.get(i).getId());
        }
    }

    private void consultCustomersList() {
        SQLiteDatabase database = connection.getReadableDatabase();

        Customer customer = null;
        CustomersArrayList = new ArrayList<Customer>();

        Cursor cursor = database.rawQuery("SELECT "+Tables.ID_FIELD+", "+Tables.FULLNAME_FIELD+" FROM "+Tables.CUSTOMERS_TABLE, null);

        while (cursor.moveToNext())
        {
            customer = new Customer(null, null, null, null, null, null, null, null, null);
            customer.setId(cursor.getInt(0));
            customer.setFullname(cursor.getString(1));

            CustomersArrayList.add(customer);

        }

        fillCustomersList();

    }

    private void fillCustomersList() {
        CustomerList = new ArrayList<String>();

        for (int i = 1; i < CustomersArrayList.size(); i++)
        {
            CustomerList.add(CustomersArrayList.get(i).getId().toString());
        }
    }
}