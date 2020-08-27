package com.example.mywisp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mywisp.entities.Customer;
import com.example.mywisp.utilities.Tables;

import java.util.ArrayList;

public class customers_list extends AppCompatActivity {

    ListView CustomersListView;
    ArrayList<String> InformationList;
    ArrayList<Customer> CustomerList;

    SQLiteHelperConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_list);

        connection = new SQLiteHelperConnection(getApplicationContext(), "mywisp", null, 1);

        CustomersListView = (ListView) findViewById(R.id.lvCustomers);

        ConsultCustomersList();

        ArrayAdapter adapater = new ArrayAdapter(this, android.R.layout.simple_list_item_1, InformationList);
        CustomersListView.setAdapter(adapater);

        //This send the customer information as an object to customer_details activity
        CustomersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                String information = "Id: "+CustomerList.get(pos).getId()+"\n";
                information+="Nombre completo: "+CustomerList.get(pos).getFullname()+"\n";
                information+="Estado: "+CustomerList.get(pos).getStatus()+"\n";

                Customer customer = CustomerList.get(pos);

                Intent intent = new Intent(customers_list.this, customer_details.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("customer", customer);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    //Consult customers list from database
    private void ConsultCustomersList() {
        SQLiteDatabase database = connection.getReadableDatabase();

        Customer customer = null;

        CustomerList = new ArrayList<Customer>();

        Cursor cursor = database.rawQuery("SELECT "+Tables.ID_FIELD+", "+Tables.FULLNAME_FIELD+", "+Tables.STATUS_FIELD+" FROM "+ Tables.CUSTOMERS_TABLE, null);

        while (cursor.moveToNext())
        {
            customer = new Customer(null, null, null, null, null, null, null, null, null);
            customer.setId(cursor.getInt(0));
            customer.setFullname(cursor.getString(1));
            customer.setStatus(cursor.getString(7));

            CustomerList.add(customer);

        }

        fillInformationList();

        database.close();

    }

    //fill the InformationList ArrayList with the customers list obtained
    private void fillInformationList() {
        InformationList = new ArrayList<String>();

        for (int i = 0; i < CustomerList.size(); i++)
        {
            InformationList.add(CustomerList.get(i).getId()+" - "+CustomerList.get(i).getFullname()+" - "+CustomerList.get(i).getStatus());
        }
    }


}