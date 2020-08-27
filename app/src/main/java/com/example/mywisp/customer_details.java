package com.example.mywisp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.mywisp.entities.Customer;

public class customer_details extends AppCompatActivity {

    TextView tvId, tvFullname, tvMunicipality, tvDepartment, tvAddress, tvPhone, tvEmail, tvStatus, tvRemarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        tvId = (TextView) findViewById(R.id.tvIdCustomerDetails);
        tvFullname = (TextView) findViewById(R.id.tvFullnameCustomerDetails);
        tvMunicipality = (TextView) findViewById(R.id.tvMunicipalityCustomerDetails);
        tvDepartment = (TextView) findViewById(R.id.tvDepartmentCustomerDetails);
        tvAddress = (TextView) findViewById(R.id.tvAddressCustomerDetails);
        tvPhone = (TextView) findViewById(R.id.tvPhoneCustomerDetails);
        tvEmail = (TextView) findViewById(R.id.tvEmailCustomerDetails);
        tvStatus = (TextView) findViewById(R.id.tvStatusCustomerDetails);
        tvRemarks = (TextView) findViewById(R.id.tvRemarksCustomerDetails);

        // This receive the information customer in customer_list activity as a Customer object
        // for assign to TextViews
        Bundle objectSent = getIntent().getExtras();
        Customer customer = null;

        if (objectSent != null)
        {
            customer = (Customer) objectSent.getSerializable("customer");
            tvId.setText(customer.getId().toString());
            tvFullname.setText(customer.getFullname().toString());
            tvMunicipality.setText(customer.getMunicipality().toString());
            tvDepartment.setText(customer.getDepartment().toString());
            tvAddress.setText(customer.getAddress().toString());
            tvPhone.setText(customer.getPhone().toString());
            tvEmail.setText(customer.getEmail().toString());
            tvStatus.setText(customer.getStatus().toString());
            tvRemarks.setText(customer.getRemarks().toString());
        }

    }
}