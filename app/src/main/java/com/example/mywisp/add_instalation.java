package com.example.mywisp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.example.mywisp.entities.Services;
import com.example.mywisp.utilities.Tables;

import java.util.ArrayList;
import java.util.Calendar;


public class add_instalation extends AppCompatActivity {

    EditText etDate, etMaterials, etPlace;
    Spinner spCustomer, spService;
    TextView tvCustomerName, tvServiceName;
    DatePickerDialog.OnDateSetListener setListener;

    SQLiteHelperConnection connection;

    ArrayList<String> CustomerList;
    ArrayList<Customer> CustomersArrayList;

    ArrayList<String> ServiceList;
    ArrayList<Services> ServicesArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instalation);
        connection = new SQLiteHelperConnection(this, "mywisp", null, 1);


        etDate = (EditText) findViewById(R.id.txtDateInstalation);
        etMaterials = (EditText) findViewById(R.id.txtMaterialsInstalation);
        etPlace = (EditText) findViewById(R.id.txtPlaceInstalation);
        spCustomer = (Spinner) findViewById(R.id.spCustomerInstalation);
        spService = (Spinner) findViewById(R.id.spServiceInstalation);
        tvCustomerName = (TextView) findViewById(R.id.tvNameService);
        tvServiceName = (TextView) findViewById(R.id.tvNameService);

        ArrayAdapter<CharSequence> CustomerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, CustomerList);
        spCustomer.setAdapter(CustomerAdapter);

        ArrayAdapter<CharSequence> ServiceAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ServiceList);
        spService.setAdapter(ServiceAdapter);

        spCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvCustomerName.setText(CustomersArrayList.get(position).getFullname());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvServiceName.setText(ServicesArrayList.get(position).getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(calendar.YEAR);
        final int month = calendar.get(calendar.MONTH);
        final int day = calendar.get(calendar.DAY_OF_MONTH);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(add_instalation.this, new DatePickerDialog.OnDateSetListener() {
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

        consultCustomersList();
        consultServicesList();

    }

    private void consultServicesList() {
        SQLiteDatabase database = connection.getReadableDatabase();

        Services service = null;
        ServicesArrayList = new ArrayList<Services>();

        Cursor cursor = database.rawQuery("SELECT * FROM "+Tables.SERVICES_TABLE, null);

        while (cursor.moveToNext())
        {
            service = new Services(null, null, null, null);
            service.setId(cursor.getString(0));
            service.setName(cursor.getString(1));
            service.setPrice(cursor.getString(2));
            service.setDescription(cursor.getString(3));

            ServicesArrayList.add(service);
        }

        fillServicesList();
    }

    private void fillServicesList() {
        ServiceList = new ArrayList<String>();
        ServiceList.add("Seleccione");

        for (int i = 0; i < ServicesArrayList.size(); i++)
        {
            ServiceList.add(ServicesArrayList.get(i).getId());
        }
    }

    private void consultCustomersList() {
        SQLiteDatabase database = connection.getReadableDatabase();

        Customer customer = null;
        CustomersArrayList = new ArrayList<Customer>();

        Cursor cursor = database.rawQuery("SELECT * FROM "+Tables.CUSTOMERS_TABLE, null);

        while (cursor.moveToNext())
        {
            customer = new Customer(null, null, null, null, null, null, null, null, null);
            customer.setId(cursor.getInt(0));
            customer.setFullname(cursor.getString(1));
            customer.setMunicipality(cursor.getString(2));
            customer.setDepartment(cursor.getString(3));
            customer.setAddress(cursor.getString(4));
            customer.setPhone(cursor.getString(5));
            customer.setEmail(cursor.getString(6));
            customer.setStatus(cursor.getString(7));
            customer.setRemarks(cursor.getString(8));

            CustomersArrayList.add(customer);

        }

        fillCustomersList();

    }

    private void fillCustomersList() {
        CustomerList = new ArrayList<String>();
        CustomerList.add("Seleccione");

        for (int i = 0; i < CustomersArrayList.size(); i++)
        {
            CustomerList.add(CustomersArrayList.get(i).getId().toString());
        }
    }

    public void onClick(View view)
    {
        addInstalationSQL();
    }

    private void addInstalationSQL() {

        SQLiteDatabase database = connection.getReadableDatabase();

        String query = "INSERT INTO "+ Tables.INSTALATIONS_TABLE+" ("+Tables.INSTALATIONS_DATE_FIELD+", "+Tables.INSTALATIONS_MATERIALS_FIELD+", "+Tables.INSTALATIONS_PLACE_FIELD+", "+Tables.INSTALATIONS_IDCUSTOMER_FIELD+", "+Tables.INSTALATIONS_IDSERVICE_FIELD+") VALUES('"+etDate.getText().toString()+"', '"+etMaterials.getText().toString()+"', '"+etPlace.getText().toString()+"', "+spCustomer.getSelectedItem().toString()+", "+spService.getSelectedItem().toString()+")";

        int idSpinnerCustomer = (int) spCustomer.getSelectedItemId();
        int idSpinnerService = (int) spService.getSelectedItemId();


        try
        {
            if ((idSpinnerCustomer != 0) && (idSpinnerService != 0))
            {
                database.execSQL(query);
                Toast.makeText(getApplicationContext(), "La instalación ha sido agregada exitosamente.", Toast.LENGTH_SHORT).show();
            }
            else
                {
                    Toast.makeText(getApplicationContext(), "Debe seleccionar un cliente y un plan de servicio.", Toast.LENGTH_SHORT).show();
                }

        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), "Falló al agregar la instalación, ha ocurrido un error.", Toast.LENGTH_SHORT).show();
        }

        database.close();

    }
}