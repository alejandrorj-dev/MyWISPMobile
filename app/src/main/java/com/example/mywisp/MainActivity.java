package com.example.mywisp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteHelperConnection connection = new SQLiteHelperConnection(this, "mywisp", null, 1);
    }

    public void OpenAddCustomerActivity(View view)
    {
        Intent AddCustomerActivity = new Intent(this, add_customer_activity.class);
        startActivity(AddCustomerActivity);
    }

    public void OpenConsultCustomerActivity(View view)
    {
        Intent ConsultCustomerActivity = new Intent(this, consult_customer.class);
        startActivity(ConsultCustomerActivity);
    }

    public void OpenCustomersListActivity(View view)
    {
        Intent ListCustomersActivity = new Intent(this, customers_list.class);
        startActivity(ListCustomersActivity);
    }

    public void OpenListServicesActivity(View view)
    {
        Intent ListServicesActivity = new Intent(this, services_list.class);
        startActivity(ListServicesActivity);
    }

    public void OpenListInstalationsActivity(View view)
    {
        Intent ListInstalationActivity = new Intent(this, instalation_list.class);
        startActivity(ListInstalationActivity);
    }
}
