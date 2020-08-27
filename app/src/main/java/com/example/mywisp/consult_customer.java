package com.example.mywisp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mywisp.utilities.Tables;

public class consult_customer extends AppCompatActivity {

    EditText idField, fullnameField, municipalityField, departmentField, addressField, phoneField, emailField, remarksField;
    Spinner spStatus;

    SQLiteHelperConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_customer);

        connection = new SQLiteHelperConnection(getApplicationContext(), "mywisp", null, 1);

        idField = (EditText) findViewById(R.id.txtId);
        fullnameField = (EditText) findViewById(R.id.txtFullname);
        municipalityField = (EditText) findViewById(R.id.txtMunicipality);
        departmentField = (EditText) findViewById(R.id.txtDepartment);
        addressField = (EditText) findViewById(R.id.txtAddress);
        phoneField = (EditText) findViewById(R.id.txtPhone);
        emailField = (EditText) findViewById(R.id.txtEmail);
        spStatus = (Spinner) findViewById(R.id.spStatus);
        remarksField = (EditText) findViewById(R.id.txtRemarks);

        String[] spStatusOptions = {"--", "Activo", "Suspendido", "Retirado", "En mora"};
        ArrayAdapter <String> SpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spStatusOptions);
        spStatus.setAdapter(SpinnerAdapter);
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnSearchCustomer:
                //consult();
                consultSQL();
                break;
            case R.id.btnUpdateCustomer:
                //updateCustomer();
                updateCustomerSQL();
                break;
            case R.id.btnDeleteCustomer:
                //deleteCustomer();
                deleteCustomerSQL();
                break;
        }
    }

    private void deleteCustomerSQL() {
        SQLiteDatabase database = connection.getWritableDatabase();

        String query = "DELETE FROM "+Tables.CUSTOMERS_TABLE+" WHERE "+Tables.ID_FIELD+"="+idField.getText().toString();

        try
        {
            database.execSQL(query);
            Toast.makeText(getApplicationContext(), "El cliente se eliminó correctamente.", Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), "Falló al eliminar el cliente, ha ocurrido un error.", Toast.LENGTH_SHORT).show();
        }

        database.close();
    }


    private void deleteCustomer() {
        SQLiteDatabase database = connection.getWritableDatabase();
        String[] parameters = {idField.getText().toString()};

        try
        {
            database.delete(Tables.CUSTOMERS_TABLE, Tables.ID_FIELD+"=?", parameters);
            Toast.makeText(getApplicationContext(), "El cliente se ha eliminado correctamente.", Toast.LENGTH_SHORT).show();
            idField.setText("");
            Cleaner();

        }
        catch (Exception ex)
        {
           Toast.makeText(getApplicationContext(), "Falló la eliminación, ha ocurrido un error.", Toast.LENGTH_SHORT).show();
        }

        database.close();

    }

    private void updateCustomerSQL() {
        SQLiteDatabase database = connection.getWritableDatabase();

        String query = "UPDATE "+Tables.CUSTOMERS_TABLE+" SET "+Tables.FULLNAME_FIELD+"='"+fullnameField.getText().toString()+"', "+Tables.MUNICIPALITY_FIELD+"='"+municipalityField.getText().toString()+"', "+Tables.DEPARTMENT_FIELD+"='"+departmentField.getText().toString()+"', "+Tables.ADDRESS_FIELD+"='"+addressField.getText().toString()+"', "+Tables.PHONE_FIELD+"='"+phoneField.getText().toString()+"', "+Tables.EMAIL_FIELD+"='"+emailField.getText().toString()+"', "+Tables.STATUS_FIELD+"='"+spStatus.getSelectedItem().toString()+"', "+Tables.REMARKS_FIELD+"='"+remarksField.getText().toString()+"' WHERE "+Tables.ID_FIELD+"="+idField.getText().toString();

        try
        {
            database.execSQL(query);
            Toast.makeText(getApplicationContext(), "El cliente se actualizó correctamente.", Toast.LENGTH_SHORT).show();

        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), "Falló al actualizar el cliente, ha ocurrido un error.", Toast.LENGTH_SHORT).show();
        }

        database.close();

    }

    private void updateCustomer() {
        SQLiteDatabase database = connection.getWritableDatabase();
        String[] parameters = {idField.getText().toString()};

        try
        {
            ContentValues values = new ContentValues();
            values.put(Tables.FULLNAME_FIELD, fullnameField.getText().toString());
            values.put(Tables.MUNICIPALITY_FIELD, municipalityField.getText().toString());
            values.put(Tables.DEPARTMENT_FIELD, departmentField.getText().toString());
            values.put(Tables.ADDRESS_FIELD, addressField.getText().toString());
            values.put(Tables.PHONE_FIELD, phoneField.getText().toString());
            values.put(Tables.EMAIL_FIELD, emailField.getText().toString());
            values.put(Tables.STATUS_FIELD, spStatus.getSelectedItem().toString());
            values.put(Tables.REMARKS_FIELD, remarksField.getText().toString());

            database.update(Tables.CUSTOMERS_TABLE, values, Tables.ID_FIELD+"=?", parameters);
            Toast.makeText(getApplicationContext(), "La actualización se realizó correctamente", Toast.LENGTH_SHORT).show();

        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), "Falló la actualización, ha ocurrido un error.", Toast.LENGTH_SHORT).show();
        }

        database.close();

    }

    private void consultSQL() {
        SQLiteDatabase database = connection.getReadableDatabase();
        String[] parameters = {idField.getText().toString()};

            try
            {
                Cursor cursor = database.rawQuery("SELECT "+Tables.FULLNAME_FIELD+", "+Tables.MUNICIPALITY_FIELD+", "+Tables.DEPARTMENT_FIELD+", "+Tables.ADDRESS_FIELD+", "+Tables.PHONE_FIELD+", "+Tables.EMAIL_FIELD+", "+Tables.STATUS_FIELD+", "+Tables.REMARKS_FIELD+" FROM "+Tables.CUSTOMERS_TABLE+" WHERE "+Tables.ID_FIELD+"=?", parameters);
                cursor.moveToFirst();
                fullnameField.setText(cursor.getString(0));
                municipalityField.setText(cursor.getString(1));
                departmentField.setText(cursor.getString(2));
                addressField.setText(cursor.getString(3));
                phoneField.setText(cursor.getString(4));
                emailField.setText(cursor.getString(5));

                switch (cursor.getString(6))
                {
                    case "Activo":
                        spStatus.setSelection(1);
                        break;
                    case "Suspendido":
                        spStatus.setSelection(2);
                        break;
                    case "Retirado":
                        spStatus.setSelection(3);
                        break;
                    case  "En mora":
                        spStatus.setSelection(4);
                        break;
                }

                remarksField.setText(cursor.getString(7));
                cursor.close();
            }
            catch (Exception ex)
            {
                Toast.makeText(getApplicationContext(), "No existe un cliente con ese id.", Toast.LENGTH_SHORT);
                Cleaner();
            }

    }

    private void consult() {
        SQLiteDatabase database = connection.getReadableDatabase();
        String[] parameters = {idField.getText().toString()};
        String[] fields = {Tables.FULLNAME_FIELD, Tables.MUNICIPALITY_FIELD, Tables.DEPARTMENT_FIELD, Tables.ADDRESS_FIELD, Tables.PHONE_FIELD, Tables.EMAIL_FIELD, Tables.STATUS_FIELD, Tables.REMARKS_FIELD};

        try
        {
            Cursor cursor = database.query(Tables.CUSTOMERS_TABLE, fields, Tables.ID_FIELD+"=?", parameters, null, null, null);
            cursor.moveToFirst();

            fullnameField.setText(cursor.getString(0));
            municipalityField.setText(cursor.getString(1));
            departmentField.setText(cursor.getString(2));
            addressField.setText(cursor.getString(3));
            phoneField.setText(cursor.getString(4));
            emailField.setText(cursor.getString(5));

            switch (cursor.getString(6))
            {
                case "Activo":
                    spStatus.setSelection(1);
                    break;
                case "Suspendido":
                    spStatus.setSelection(2);
                    break;
                case "Retirado":
                    spStatus.setSelection(3);
                    break;
                case  "En mora":
                    spStatus.setSelection(4);
                    break;
            }

            remarksField.setText(cursor.getString(7));

            cursor.close();

        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), "No existe un cliente con este id", Toast.LENGTH_SHORT).show();
            Cleaner();
        }


    }

    private void Cleaner() {

        fullnameField.setText("");
        municipalityField.setText("");
        departmentField.setText("");
        addressField.setText("");
        phoneField.setText("");
        emailField.setText("");
        spStatus.setSelection(0);
        remarksField.setText("");
    }
}
