package com.example.mywisp.utilities;

public class Tables {

    //Constants

    //Customers Table
    public static final String CUSTOMERS_TABLE = "customers";
    public static final String ID_FIELD = "id";
    public static final String FULLNAME_FIELD = "fullname";
    public static final String MUNICIPALITY_FIELD = "municipality";
    public static final String DEPARTMENT_FIELD = "department";
    public static final String ADDRESS_FIELD = "address";
    public static final String PHONE_FIELD = "phone";
    public static final String EMAIL_FIELD = "email";
    public static final String STATUS_FIELD = "status";
    public static final String REMARKS_FIELD = "remarks";
    public static final String CREATE_CUSTOMERS_TABLE = "CREATE TABLE Customers("+ID_FIELD+" BIGINT(11), "+FULLNAME_FIELD+" TEXT, "+MUNICIPALITY_FIELD+" TEXT, "+DEPARTMENT_FIELD+" TEXT, "+ADDRESS_FIELD+" TEXT, "+PHONE_FIELD+" TEXT, "+EMAIL_FIELD+" TEXT, "+STATUS_FIELD+" TEXT, "+REMARKS_FIELD+" TEXT)";


    //Instalations Table
    public static final String INSTALATIONS_TABLE = "instalations";
    public static final String INSTALATIONS_ID_FIELD = "id";
    public static final String INSTALATIONS_DATE_FIELD = "date";
    public static final String INSTALATIONS_PLACE_FIELD = "place";
    public static final String INSTALATIONS_MATERIALS_FIELD = "materials";
    public static final String INSTALATIONS_IDCUSTOMER_FIELD = "id_customer";
    public static final String INSTALATIONS_IDSERVICE_FIELD = "id_service";

    public static final String CREATE_INSTALATIONS_TABLE = "CREATE TABLE "+INSTALATIONS_TABLE+" ("+INSTALATIONS_ID_FIELD+" INTEGER PRIMARY KEY AUTOINCREMENT, "+INSTALATIONS_DATE_FIELD+" TEXT, "+INSTALATIONS_MATERIALS_FIELD+" TEXT, "+INSTALATIONS_PLACE_FIELD+" TEXT, "+INSTALATIONS_IDCUSTOMER_FIELD+", INTEGER, "+INSTALATIONS_IDSERVICE_FIELD+" INTEGER)";


    //Services Table
    public static final String SERVICES_TABLE = "services";
    public static final String SERVICES_ID_FIELD = "id";
    public static final String SERVICES_NAME_FIELD = "name";
    public static final String SERVICES_PRICE_FIELD = "price";
    public static final String SERVICES_DESCRIPTION_FIELD = "description";

    public static final String CREATE_SERVICES_TABLE = "CREATE TABLE "+SERVICES_TABLE+" ("+SERVICES_ID_FIELD+", INTEGER PRIMARY KEY AUTOINCREMENT, "+SERVICES_NAME_FIELD+" TEXT, "+SERVICES_PRICE_FIELD+" FLOAT, "+SERVICES_DESCRIPTION_FIELD+" TEXT)";


}
