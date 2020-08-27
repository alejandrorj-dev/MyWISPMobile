package com.example.mywisp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mywisp.utilities.Tables;

public class add_customer_activity extends AppCompatActivity {

    EditText idField, fullnameField, municipalityField, departmentField, addressField, phoneField, emailField, remarksField;
    Spinner statusField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer_activity);

        idField = (EditText) findViewById(R.id.txtId);
        fullnameField = (EditText) findViewById(R.id.txtFullname);
        municipalityField = (EditText) findViewById(R.id.txtMunicipality);
        departmentField = (EditText) findViewById(R.id.txtDepartment);
        addressField = (EditText) findViewById(R.id.txtAddress);
        phoneField = (EditText) findViewById(R.id.txtPhone);
        emailField = (EditText) findViewById(R.id.txtEmail);
        statusField = (Spinner) findViewById(R.id.spStatus);
        remarksField = (EditText) findViewById(R.id.txtRemarks);

        String [] spStatusOptions = {"Activo", "Suspendido", "Retirado", "En mora"};

        ArrayAdapter <String> SpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spStatusOptions);

        statusField.setAdapter(SpinnerAdapter);

    }

    public void onClick(View view)
    {
        //addCustomers();
          addCustomersSQL();
    }

    private void addCustomersSQL() {
        SQLiteHelperConnection connection = new SQLiteHelperConnection(this, "mywisp", null, 1);
        SQLiteDatabase database = connection.getReadableDatabase();

        String query = "INSERT INTO "+Tables.CUSTOMERS_TABLE+"("+Tables.ID_FIELD+", "+Tables.FULLNAME_FIELD+", "+Tables.MUNICIPALITY_FIELD+", "+Tables.DEPARTMENT_FIELD+", "+Tables.ADDRESS_FIELD+", "+Tables.PHONE_FIELD+", "+Tables.EMAIL_FIELD+", "+Tables.STATUS_FIELD+", "+Tables.REMARKS_FIELD+") VALUES("+idField.getText().toString()+", '"+fullnameField.getText().toString()+"', '"+municipalityField.getText().toString()+"', '"+departmentField.getText().toString()+"', '"+addressField.getText().toString()+"', '"+phoneField.getText().toString()+"', '"+emailField.getText().toString()+"', '"+statusField.getSelectedItem().toString()+"', '"+remarksField.getText().toString()+"');";

        try
        {
            database.execSQL(query);
            Toast.makeText(getApplicationContext(), "El Cliente ha sido ingresado con éxito con el id "+idField.getText().toString(), Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), "El Cliente no pudo ser ingresado, ha ocurrido un error."+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }


        database.close();
    }

    private void addCustomers()
    {
        SQLiteHelperConnection connection = new SQLiteHelperConnection(this, "mywisp", null, 1);
        SQLiteDatabase database = connection.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(Tables.ID_FIELD, idField.getText().toString());
        values.put(Tables.FULLNAME_FIELD, fullnameField.getText().toString());
        values.put(Tables.MUNICIPALITY_FIELD,municipalityField.getText().toString());
        values.put(Tables.DEPARTMENT_FIELD, departmentField.getText().toString());
        values.put(Tables.ADDRESS_FIELD, addressField.getText().toString());
        values.put(Tables.PHONE_FIELD, phoneField.getText().toString());
        values.put(Tables.EMAIL_FIELD, emailField.getText().toString());
        values.put(Tables.STATUS_FIELD, statusField.getSelectedItem().toString());
        values.put(Tables.REMARKS_FIELD, remarksField.getText().toString());

        try
        {
            Long resultId = database.insert(Tables.CUSTOMERS_TABLE, Tables.ID_FIELD, values);
            Toast.makeText(getApplicationContext(), "Id Registro "+resultId, Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), "No se logró el registro del cliente, ha ocurrido un error.", Toast.LENGTH_SHORT).show();
        }

        database.close();

    }


}
