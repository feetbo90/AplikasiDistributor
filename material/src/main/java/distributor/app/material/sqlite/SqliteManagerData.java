package distributor.app.material.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import distributor.app.material.model.Contact;
import distributor.app.material.model.Customer;
import distributor.app.material.model.DataVariabel;
import distributor.app.material.model.Header;

/**
 * Created by feetbo on 11/1/2015.
 */
public class SqliteManagerData {

    public static final int VERSI_DATABASE= 1;
    public static final String NAMA_DATABASE = "dbDataProduk";
    public static final String NAMA_TABEL_PRODUK = "tbProduk";
    public static final String NAMA_TABEL_CUSTOMER = "tbCustomer";
    public static final String NAMA_TABEL_PLAFOND = "tbPlafond";
    public static final String FIELD_ID = "_id";
    // JSON Node names
    private static final String TAG_PRODUK = "produk";
    private static final String TAG_KODE = "kode";
    private static final String TAG_NAMA = "nama";
    private static final String TAG_GROUP_ID = "groupid";
    private static final String TAG_UNITB = "unitb";
    private static final String TAG_UNITM = "unitm";
    private static final String TAG_UNITK = "unitk";
    private static final String TAG_QTYB = "qtyb";
    private static final String TAG_QTYM = "qtym";
    private static final String TAG_QTYK = "qtyk";

    // JSON Node names
    private static final String TAG_HARGA = "harga";
    private static final String TAG_HARGA1 = "harga1";
    private static final String TAG_HARGA2 = "harga2";
    private static final String TAG_HARGA3 = "harga3";
    private static final String TAG_HARGA4 = "harga4";
    private static final String TAG_HARGA5 = "harga5";
    private static final String TAG_HARGA6 = "harga6";
    private static final String TAG_HARGA7 = "harga7";
    private static final String TAG_HARGA8 = "harga8";
    private static final String TAG_HARGA9 = "harga9";
    private static final String TAG_HARGA10 = "harga10";

    // JSON Node names
    private static final String TAG_BHARGA = "bharga";
    private static final String TAG_BHJUAL1 = "bhjual1";
    private static final String TAG_BHJUAL2 = "bhjual2";
    private static final String TAG_BHJUAL3 = "bhjual3";
    private static final String TAG_BHJUAL4 = "bhjual4";
    private static final String TAG_BHJUAL5 = "bhjual5";
    private static final String TAG_BHJUAL6 = "bhjual6";
    private static final String TAG_BHJUAL7 = "bhjual7";
    private static final String TAG_BHJUAL8 = "bhjual8";
    private static final String TAG_BHJUAL9 = "bhjual9";
    private static final String TAG_BHJUAL10 = "bhjual10";

    // JSON Node names
    private static final String TAG_TGLA = "tgla";
    private static final String TAG_TGL1A = "tgl1a";
    private static final String TAG_TGL2A = "tgl2a";
    private static final String TAG_TGL3A = "tgl3a";
    private static final String TAG_TGL4A = "tgl4a";
    private static final String TAG_TGL5A = "tgl5a";
    private static final String TAG_TGL6A = "tgl6a";
    private static final String TAG_TGL7A = "tgl7a";
    private static final String TAG_TGL8A = "tgl8a";
    private static final String TAG_TGL9A= "tgl9a";
    private static final String TAG_TGL10A = "tgl10a";

    // JSON Node names
    private static final String TAG_TGLB = "tglb";
    private static final String TAG_TGL1B = "tgl1b";
    private static final String TAG_TGL2B = "tgl2b";
    private static final String TAG_TGL3B = "tgl3b";
    private static final String TAG_TGL4B = "tgl4b";
    private static final String TAG_TGL5B = "tgl5b";
    private static final String TAG_TGL6B = "tgl6b";
    private static final String TAG_TGL7B = "tgl7b";
    private static final String TAG_TGL8B = "tgl8b";
    private static final String TAG_TGL9B = "tgl9b";
    private static final String TAG_TGL10B = "tgl10b";


    ////////////////////////////////////////////////////////////////////////////////////////////
    // Tabel data Customer
    ///////////////////////////////////////////////////////////////////////////////////////////
    private static final String TAG_CUSTID = "custid";
    private static final String TAG_NAMA_CUSTOMER = "nama";
    private static final String TAG_ALAMAT = "alamat";
    private static final String TAG_ALAMAT2 = "alamat2";
    private static final String TAG_ICJLNID = "ICJLNID";
    private static final String TAG_WEEK = "week";
    private static final String TAG_DAY = "day";
    private static final String TAG_ROUTEID = "routeid";
    private static final String TAG_CRLIMIT = "crlimit";

    ////////////////////////////////////////////////////////////////////////////////////////////
    // Tabel data Plafond
    ///////////////////////////////////////////////////////////////////////////////////////////
    private static final String TAG_CUSTID_PLAFOND = "custid";
    private static final String TAG_CRLIMIT_PLAFOND = "crlimit1";
    private static final String TAG_CRLIMIT2 = "crlimit2";


    /////////////////////////////////////////////////////////////////////////////////////////
    // Table Plafond
    ////////////////////////////////////////////////////////////////////////////////////////

    public static final String [] FIELD_TABEL_PLAFOND = {
            SqliteManagerData.FIELD_ID , SqliteManagerData.TAG_CUSTID_PLAFOND, SqliteManagerData.TAG_CRLIMIT_PLAFOND,
            SqliteManagerData.TAG_CRLIMIT2,
    };


    ////////////////////////////////////////////////////////////////////////////////////////
    // Table Customer
    ///////////////////////////////////////////////////////////////////////////////////////


    public static final String [] FIELD_TABEL_CUSTOMER = {
            SqliteManagerData.FIELD_ID , SqliteManagerData.TAG_CUSTID, SqliteManagerData.TAG_NAMA_CUSTOMER, SqliteManagerData.TAG_ALAMAT,
            SqliteManagerData.TAG_ALAMAT2, SqliteManagerData.TAG_ICJLNID, SqliteManagerData.TAG_ICJLNID,
            SqliteManagerData.TAG_WEEK, SqliteManagerData.TAG_DAY, SqliteManagerData.TAG_ROUTEID,
            SqliteManagerData.TAG_CRLIMIT,
    };

    ////////////////////////////////////////////////////////////////////////////////////////
    // Table Produk
    ///////////////////////////////////////////////////////////////////////////////////////

    public static final String[] FIELD_TABEL = {SqliteManagerData.FIELD_ID,
            SqliteManagerData.TAG_KODE,
            SqliteManagerData.TAG_NAMA,
            SqliteManagerData.TAG_GROUP_ID,
            SqliteManagerData.TAG_UNITB,
            SqliteManagerData.TAG_UNITM,
            SqliteManagerData.TAG_UNITK,
            SqliteManagerData.TAG_QTYB,
            SqliteManagerData.TAG_QTYM,
            SqliteManagerData.TAG_QTYK,
            SqliteManagerData.TAG_HARGA1,
            SqliteManagerData.TAG_HARGA2,
            SqliteManagerData.TAG_HARGA3,
            SqliteManagerData.TAG_HARGA4,
            SqliteManagerData.TAG_HARGA5,
            SqliteManagerData.TAG_HARGA6,
            SqliteManagerData.TAG_HARGA7,
            SqliteManagerData.TAG_HARGA8,
            SqliteManagerData.TAG_HARGA9,
            SqliteManagerData.TAG_HARGA10,
            SqliteManagerData.TAG_BHJUAL1,
            SqliteManagerData.TAG_BHJUAL2,
            SqliteManagerData.TAG_BHJUAL3,
            SqliteManagerData.TAG_BHJUAL4,
            SqliteManagerData.TAG_BHJUAL5,
            SqliteManagerData.TAG_BHJUAL6,
            SqliteManagerData.TAG_BHJUAL7,
            SqliteManagerData.TAG_BHJUAL8,
            SqliteManagerData.TAG_BHJUAL9,
            SqliteManagerData.TAG_BHJUAL10,
            SqliteManagerData.TAG_TGL1A,
            SqliteManagerData.TAG_TGL2A,
            SqliteManagerData.TAG_TGL3A,
            SqliteManagerData.TAG_TGL4A,
            SqliteManagerData.TAG_TGL5A,
            SqliteManagerData.TAG_TGL6A,
            SqliteManagerData.TAG_TGL7A,
            SqliteManagerData.TAG_TGL8A,
            SqliteManagerData.TAG_TGL9A,
            SqliteManagerData.TAG_TGL10A,
            SqliteManagerData.TAG_TGL1B,
            SqliteManagerData.TAG_TGL2B,
            SqliteManagerData.TAG_TGL3B,
            SqliteManagerData.TAG_TGL4B,
            SqliteManagerData.TAG_TGL5B,
            SqliteManagerData.TAG_TGL6B,
            SqliteManagerData.TAG_TGL7B,
            SqliteManagerData.TAG_TGL8B,
            SqliteManagerData.TAG_TGL9B,
            SqliteManagerData.TAG_TGL10B,

            //  SqliteManager.FIELD_TIPE_SALES
    };

    private Context crudContext;
    private SQLiteDatabase crudDatabase;
    private SqliteManagerHelper crudHelper;

    private static class SqliteManagerHelper extends SQLiteOpenHelper {
        private static final String BUAT_TABEL =
                "create table " + NAMA_TABEL_PRODUK + " (" +
                        SqliteManagerData.FIELD_ID + " integer primary key autoincrement, " +
                        SqliteManagerData.TAG_KODE + " text not null ," +
                        SqliteManagerData.TAG_NAMA + " text not null, " +
                        SqliteManagerData.TAG_GROUP_ID + " text not null, " +
                        SqliteManagerData.TAG_UNITB + " text not null, " +
                        SqliteManagerData.TAG_UNITM + " text not null, " +
                        SqliteManagerData.TAG_UNITK + " text not null," +
                        SqliteManagerData.TAG_QTYB + " text not null, " +
                        SqliteManagerData.TAG_QTYM + " text not null, " +
                        SqliteManagerData.TAG_QTYK + " text not null, " +
                        SqliteManagerData.TAG_HARGA1 + " text not null, " +
                        SqliteManagerData.TAG_HARGA2 + " text not null, " +
                        SqliteManagerData.TAG_HARGA3 + " text not null, " +
                        SqliteManagerData.TAG_HARGA4 + " text not null, " +
                        SqliteManagerData.TAG_HARGA5 + " text not null, " +
                        SqliteManagerData.TAG_HARGA6 + " text not null, " +
                        SqliteManagerData.TAG_HARGA7 + " text not null, " +
                        SqliteManagerData.TAG_HARGA8 + " text not null, " +
                        SqliteManagerData.TAG_HARGA9 + " text not null, " +
                        SqliteManagerData.TAG_HARGA10 + " text not null, " +
                        SqliteManagerData.TAG_BHJUAL1 + " text not null, " +
                        SqliteManagerData.TAG_BHJUAL2 + " text not null ," +
                        SqliteManagerData.TAG_BHJUAL3 + " text not null, " +
                        SqliteManagerData.TAG_BHJUAL4 + " text not null, " +
                        SqliteManagerData.TAG_BHJUAL5 + " text not null, " +
                        SqliteManagerData.TAG_BHJUAL6 + " text not null, " +
                        SqliteManagerData.TAG_BHJUAL7 + " text not null, " +
                        SqliteManagerData.TAG_BHJUAL8 + " text not null, " +
                        SqliteManagerData.TAG_BHJUAL9 + " text not null, " +
                        SqliteManagerData.TAG_BHJUAL10 + " text not null, " +
                        SqliteManagerData.TAG_TGL1A + " text not null, " +
                        SqliteManagerData.TAG_TGL2A + " text not null, " +
                        SqliteManagerData.TAG_TGL3A + " text not null, " +
                        SqliteManagerData.TAG_TGL4A + " text not null, " +
                        SqliteManagerData.TAG_TGL5A + " text not null, " +
                        SqliteManagerData.TAG_TGL6A + " text not null, " +
                        SqliteManagerData.TAG_TGL7A + " text not null, " +
                        SqliteManagerData.TAG_TGL8A + " text not null, " +
                        SqliteManagerData.TAG_TGL9A + " text not null, " +
                        SqliteManagerData.TAG_TGL10A + " text not null, " +
                        SqliteManagerData.TAG_TGL1B + " text not null, " +
                        SqliteManagerData.TAG_TGL2B + " text not null, " +
                        SqliteManagerData.TAG_TGL3B + " text not null, " +
                        SqliteManagerData.TAG_TGL4B + " text not null, " +
                        SqliteManagerData.TAG_TGL5B + " text not null, " +
                        SqliteManagerData.TAG_TGL6B + " text not null, " +
                        SqliteManagerData.TAG_TGL7B + " text not null, " +
                        SqliteManagerData.TAG_TGL8B + " text not null, " +
                        SqliteManagerData.TAG_TGL9B + " text not null, " +
                        SqliteManagerData.TAG_TGL10B + " text not null " +

                        //   SqliteManager.FIELD_TIPE_SALES + " text not null " +
                        ");";

        private static final String BUAT_TABEL_CUSTOMER =
                "create table " + NAMA_TABEL_CUSTOMER + " (" +
                        SqliteManagerData.FIELD_ID + " integer primary key autoincrement, " +
                        SqliteManagerData.TAG_CUSTID + " text not null ," +
                        SqliteManagerData.TAG_NAMA_CUSTOMER + " text not null, " +
                        SqliteManagerData.TAG_ALAMAT + " text not null, " +
                        SqliteManagerData.TAG_ALAMAT2 + " text not null, " +
                        SqliteManagerData.TAG_ICJLNID + " text not null, " +
                        SqliteManagerData.TAG_WEEK + " text not null," +
                        SqliteManagerData.TAG_DAY + " text not null, " +
                        SqliteManagerData.TAG_ROUTEID + " text not null, " +
                        SqliteManagerData.TAG_CRLIMIT + " text not null " +

                        //   SqliteManager.FIELD_TIPE_SALES + " text not null " +
                        ");";

        private static final String BUAT_TABEL_PLAFOND =
                "create table " + NAMA_TABEL_PLAFOND + " (" +
                        SqliteManagerData.FIELD_ID + " integer primary key autoincrement, " +
                        SqliteManagerData.TAG_CUSTID + " text not null ," +
                        SqliteManagerData.TAG_CRLIMIT_PLAFOND + " text not null ," +
                        SqliteManagerData.TAG_CRLIMIT2 + " text not null " +

                        //   SqliteManager.FIELD_TIPE_SALES + " text not null " +
                        ");";


        public SqliteManagerHelper(Context context) {
            super(context, NAMA_DATABASE, null, VERSI_DATABASE);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(BUAT_TABEL);
            database.execSQL(BUAT_TABEL_CUSTOMER);
            database.execSQL(BUAT_TABEL_PLAFOND);

        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {}
    }

    public SqliteManagerData(Context context) {
        crudContext = context;
    }

    public void bukaKoneksi() throws SQLException {
        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Log.d("masuk koneksi" ,"masuk");
    }

    public void tutupKoneksi() {
        crudHelper.close();
        crudHelper = null;
        crudDatabase = null;
    }


    public long insertDataCustomer(ContentValues values) {
        return crudDatabase.insert(NAMA_TABEL_CUSTOMER, null, values);
    }

    public boolean updateDataCustomer(long rowId, ContentValues values) {
        return crudDatabase.update(NAMA_TABEL_CUSTOMER, values,
                SqliteManagerData.FIELD_ID + "=" + rowId, null) > 0;
    }

    public boolean hapusDataCustomer(long rowId) {
        return crudDatabase.delete(NAMA_TABEL_CUSTOMER,
                SqliteManagerData.FIELD_ID + "=" + rowId, null) > 0;
    }


    public long insertDataPlafond(ContentValues values) {
        return crudDatabase.insert(NAMA_TABEL_PLAFOND, null, values);
    }

    public boolean updateDataPlafond(long rowId, ContentValues values) {
        return crudDatabase.update(NAMA_TABEL_PLAFOND, values,
                SqliteManagerData.FIELD_ID + "=" + rowId, null) > 0;
    }

    public boolean hapusDataPlafond(long rowId) {
        return crudDatabase.delete(NAMA_TABEL_PLAFOND,
                SqliteManagerData.FIELD_ID + "=" + rowId, null) > 0;
    }

    public long insertDataroduk(ContentValues values) {
        return crudDatabase.insert(NAMA_TABEL_PRODUK, null, values);
    }

    public boolean updateDataProduk(long rowId, ContentValues values) {
        return crudDatabase.update(NAMA_TABEL_PRODUK, values,
                SqliteManagerData.FIELD_ID + "=" + rowId, null) > 0;
    }

    public boolean hapusDataProduk(long rowId) {
        return crudDatabase.delete(NAMA_TABEL_PRODUK,
                SqliteManagerData.FIELD_ID + "=" + rowId, null) > 0;
    }

    public Cursor bacaDataProduk() {
        return crudDatabase.query(NAMA_TABEL_PRODUK,FIELD_TABEL,null, null, null, null,SqliteManager.FIELD_KODE_SALES + " DESC");

    }

    public Cursor bacaDataTerseleksi(long rowId) throws SQLException {
        Cursor cursor = crudDatabase.query(true, NAMA_TABEL_PRODUK,FIELD_TABEL,FIELD_ID + "=" + rowId,null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }


    /////////////////////////////////////////////////////////////////////////////////////////
    // Getting All Contacts PLafond
    ////////////////////////////////////////////////////////////////////////////////////////

    public List<Contact> getAllContactsPlafond(String username, String password, String crc) {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query

//        String selectQuery = "SELECT  * FROM " + NAMA_TABEL;
        String nilai = "ok";
        String selectQuery = "SELECT  * FROM " + NAMA_TABEL_PLAFOND + " ;" ;


        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setKode_Sales(cursor.getString(1));
                contact.setName(cursor.getString(3));
                contact.setPhoneNumber(cursor.getString(4));
                contact.setKode_Area(cursor.getString(5));
                contact.setCRC(cursor.getString(6));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////
    // Masukkan Data Plafond
    ///////////////////////////////////////////////////////////////////////////////////////////

    public ContentValues InsertDataPlafond(String custid, String crlimit, String crlimit2) {
        ContentValues values = new ContentValues();
        values.put(SqliteManagerData.TAG_CUSTID_PLAFOND, custid);
        values.put(SqliteManagerData.TAG_CRLIMIT_PLAFOND, crlimit);
        values.put(SqliteManagerData.TAG_CRLIMIT2, crlimit2);


        //values.put(SqliteManager.FIELD_TIPE_SALES, tipe_sales);
        return values;
    }


    public String ambilAlamatCustomer(String fakturpenjualan)
    {

        String selectQuery = "SELECT  * FROM " + NAMA_TABEL_CUSTOMER + " where " + TAG_CUSTID + " = '" + fakturpenjualan + "';";
        double total = 0;

        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        String alamat ="";
        if (cursor.moveToFirst()) {
            do {

              /*  contact.put("nama" , cursor.getString(POSISI_FAKTUR_PENJUALAN_DETAILS));
                contact.put("email" , cursor.getString(POSISI_KODE_PRODUK));
                contact.put("phone" , cursor.getString(POSISI_HARGA_PRODUK));*/


                //alamat = cursor.getString(3) + "," + cursor.getString(4);
                alamat = cursor.getString(2);
                // Log.d("jumlah total gross", ""+ cursor.getString(POSISI_JLH));
                // Adding contact to list

            } while (cursor.moveToNext());
        }
        return alamat;
    }

    public String ambilAlamatCustomer2(String fakturpenjualan)
    {

        String selectQuery = "SELECT  * FROM " + NAMA_TABEL_CUSTOMER + " where " + TAG_CUSTID + " = '" + fakturpenjualan + "';";
        double total = 0;

        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        String alamat ="";
        if (cursor.moveToFirst()) {
            do {

              /*  contact.put("nama" , cursor.getString(POSISI_FAKTUR_PENJUALAN_DETAILS));
                contact.put("email" , cursor.getString(POSISI_KODE_PRODUK));
                contact.put("phone" , cursor.getString(POSISI_HARGA_PRODUK));*/


                alamat = cursor.getString(3) + "," + cursor.getString(4);
                //alamat = cursor.getString(2);
                // Log.d("jumlah total gross", ""+ cursor.getString(POSISI_JLH));
                // Adding contact to list

            } while (cursor.moveToNext());
        }
        return alamat;
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    // Getting All Contacts Customer
    ////////////////////////////////////////////////////////////////////////////////////////

    public List<Contact> getAllContactsCustomer(String username, String password, String crc) {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query

//        String selectQuery = "SELECT  * FROM " + NAMA_TABEL;
        String nilai = "ok";
        String selectQuery = "SELECT  * FROM " + NAMA_TABEL_CUSTOMER + " ;" ;


        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setKode_Sales(cursor.getString(1));
                contact.setName(cursor.getString(3));
                contact.setPhoneNumber(cursor.getString(4));
                contact.setKode_Area(cursor.getString(5));
                contact.setCRC(cursor.getString(6));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////
    // Masukkan Data Customer
    ///////////////////////////////////////////////////////////////////////////////////////////

    public ContentValues InsertDataCustomer(String custid, String nama, String alamat, String alamat2, String icjlnid,
                                   String week, String day, String routeid, String crlimit) {
        ContentValues values = new ContentValues();
        values.put(SqliteManagerData.TAG_CUSTID, custid);
        values.put(SqliteManagerData.TAG_NAMA, nama);
        values.put(SqliteManagerData.TAG_ALAMAT, alamat);
        values.put(SqliteManagerData.TAG_ALAMAT2, alamat2);
        values.put(SqliteManagerData.TAG_ICJLNID, icjlnid);
        values.put(SqliteManagerData.TAG_WEEK, week);
        values.put(SqliteManagerData.TAG_DAY, day);
        values.put(SqliteManagerData.TAG_ROUTEID, routeid);
        values.put(SqliteManagerData.TAG_CRLIMIT, crlimit);


        //values.put(SqliteManager.FIELD_TIPE_SALES, tipe_sales);
        return values;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Input Data Customer ke ListView
    ///////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<HashMap<String, String>> getAllDataCustomerListView(){
        ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " + NAMA_TABEL_CUSTOMER + " ; ";
        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contact = new HashMap<String, String>();
                contact.put("nama" , cursor.getString(1));
                contact.put("email" , cursor.getString(2));
                contact.put("phone" , cursor.getString(3)) ;
                dataList.add(contact);
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();

        return dataList;

    }



    public boolean hapuscustomer()
    {
        String selectQuery = "DELETE  FROM " + NAMA_TABEL_CUSTOMER +";";
        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contact = new HashMap<String, String>();
// Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

return true;

    }

    public boolean hapusproduk()
    {
        String selectQuery = "DELETE  FROM " + NAMA_TABEL_PRODUK +";";
        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contact = new HashMap<String, String>();
// Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

return true;

    }

    public boolean hapusplafond()
    {
        String selectQuery = "DELETE  FROM " + NAMA_TABEL_PLAFOND +";";
        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contact = new HashMap<String, String>();
// Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }


return true;
    }




    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Input Data Untuk Routing
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public ArrayList<Header> getAllDatatoRouting(){

        ArrayList<Header> ProdukList = new ArrayList<Header>();
        Date dNow = new Date( );
        SimpleDateFormat ft =
                new SimpleDateFormat("E");

        System.out.println("Current Date: " + ft.format(dNow));
        String hari ="";
        if(ft.format(dNow).trim().equals("Sun")){
            hari = "D7";
        }else if(ft.format(dNow).trim().equals("Mon")){
            hari = "D1";
        }else if(ft.format(dNow).trim().equals("Tue")){
            hari = "D2";
        }else if(ft.format(dNow).trim().equals("Wed")){
            hari = "D3";
        }else if(ft.format(dNow).trim().equals("Thu")){
            hari = "D4";
        }else if(ft.format(dNow).trim().equals("Fri")){
            hari = "D5";
        }else if(ft.format(dNow).trim().equals("Sat")){
            hari = "D6";
        }



        String selectQuery = "SELECT  * FROM " + NAMA_TABEL_CUSTOMER +" where " +  TAG_DAY + " = '" +hari + "' ; ";
        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                Header customer = new Header(cursor.getString(1), cursor.getString(2), cursor.getString(3)
                         + ", " + cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(8)
                        );
                ProdukList.add(customer);

                /*contact.put(TAG_UNITM, cursor.getString(4)) ;
                contact.put(TAG_UNITK , cursor.getString(5)) ;
                contact.put(TAG_QTYB , cursor.getString(6)) ;
                contact.put(TAG_QTYM , cursor.getString(7)) ;
                contact.put(TAG_QTYK , cursor.getString(8)) ;
                contact.put(TAG_HARGA1 , cursor.getString(9)) ;
                contact.put(TAG_HARGA2 , cursor.getString(10)) ;
                contact.put(TAG_HARGA3 , cursor.getString(11)) ;
                contact.put(TAG_HARGA4 , cursor.getString(12)) ;
                contact.put(TAG_HARGA5 , cursor.getString(13)) ;
                contact.put(TAG_HARGA6 , cursor.getString(14)) ;
                contact.put(TAG_HARGA7 , cursor.getString(15)) ;
                contact.put(TAG_HARGA8 , cursor.getString(16)) ;
                contact.put(TAG_HARGA9 , cursor.getString(17)) ;
                contact.put(TAG_HARGA10 , cursor.getString(10)) ;
*/

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();

        return ProdukList;

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Input Data Customer ke ListView ArrayList
    ///////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<Customer> getAllDataCustomerListViewArrayList(){
        ArrayList<Customer> dataList = new ArrayList<Customer>();
        String selectQuery = "SELECT * FROM " + NAMA_TABEL_CUSTOMER + " ; ";
        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer(cursor.getString(1), cursor.getString(2), cursor.getString(3));
                dataList.add(customer);
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();

        return dataList;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    // Input Data Produk ke dalam ListView
    ////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<HashMap<String, String>> getAllDataProdukListView(String produk){
        produk = produk.trim();
        ArrayList<HashMap<String, String>> ProdukList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " + NAMA_TABEL_PRODUK + " where " +  TAG_GROUP_ID + " = '" +produk + "' ; ";
        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contact = new HashMap<String, String>();
                Log.d("list", cursor.getString(1));
                contact.put(TAG_KODE , cursor.getString(1));
                contact.put(TAG_NAMA , cursor.getString(2));
                contact.put(TAG_UNITB , cursor.getString(3));
                /*contact.put(TAG_UNITM, cursor.getString(4)) ;
                contact.put(TAG_UNITK , cursor.getString(5)) ;
                contact.put(TAG_QTYB , cursor.getString(6)) ;
                contact.put(TAG_QTYM , cursor.getString(7)) ;
                contact.put(TAG_QTYK , cursor.getString(8)) ;
                contact.put(TAG_HARGA1 , cursor.getString(9)) ;
                contact.put(TAG_HARGA2 , cursor.getString(10)) ;
                contact.put(TAG_HARGA3 , cursor.getString(11)) ;
                contact.put(TAG_HARGA4 , cursor.getString(12)) ;
                contact.put(TAG_HARGA5 , cursor.getString(13)) ;
                contact.put(TAG_HARGA6 , cursor.getString(14)) ;
                contact.put(TAG_HARGA7 , cursor.getString(15)) ;
                contact.put(TAG_HARGA8 , cursor.getString(16)) ;
                contact.put(TAG_HARGA9 , cursor.getString(17)) ;
                contact.put(TAG_HARGA10 , cursor.getString(10)) ;
*/
                ProdukList.add(contact);
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();

        return ProdukList;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    // Input Data Produk ke dalam ListView Arraylist
    ////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<Customer> getAllDataProdukListViewArrayList(String produk){
        produk = produk.trim();
        ArrayList<Customer> ProdukList = new ArrayList<Customer>();
        String selectQuery = "SELECT * FROM " + NAMA_TABEL_PRODUK + " where " +  TAG_GROUP_ID + " = '" +produk + "' ; ";
        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                Customer customer = new Customer(cursor.getString(1), cursor.getString(2), cursor.getString(3));
                ProdukList.add(customer);

                /*contact.put(TAG_UNITM, cursor.getString(4)) ;
                contact.put(TAG_UNITK , cursor.getString(5)) ;
                contact.put(TAG_QTYB , cursor.getString(6)) ;
                contact.put(TAG_QTYM , cursor.getString(7)) ;
                contact.put(TAG_QTYK , cursor.getString(8)) ;
                contact.put(TAG_HARGA1 , cursor.getString(9)) ;
                contact.put(TAG_HARGA2 , cursor.getString(10)) ;
                contact.put(TAG_HARGA3 , cursor.getString(11)) ;
                contact.put(TAG_HARGA4 , cursor.getString(12)) ;
                contact.put(TAG_HARGA5 , cursor.getString(13)) ;
                contact.put(TAG_HARGA6 , cursor.getString(14)) ;
                contact.put(TAG_HARGA7 , cursor.getString(15)) ;
                contact.put(TAG_HARGA8 , cursor.getString(16)) ;
                contact.put(TAG_HARGA9 , cursor.getString(17)) ;
                contact.put(TAG_HARGA10 , cursor.getString(10)) ;
*/

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();

        return ProdukList;

    }


    ////////////////////////////////////////////////////////////////////////////////////////////
    // Input Data Produk Harga Detail ke dalam ListView
    ////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<String> getAllDataProdukDetail(String kode){
        kode = kode.trim();
        ArrayList<String> ProdukList = new ArrayList<String>();
        String selectQuery = "SELECT * FROM " + NAMA_TABEL_PRODUK + " where " +  TAG_KODE + " = '" +kode + "' ; ";
        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contact = new HashMap<String, String>();
                Log.d("list", cursor.getString(1));
                ProdukList.add(cursor.getString(1));
                ProdukList.add( cursor.getString(2));
                ProdukList.add( cursor.getString(3));
                ProdukList.add( cursor.getString(4)) ;
                ProdukList.add( cursor.getString(5)) ;
                ProdukList.add( cursor.getString(6)) ;
                ProdukList.add( cursor.getString(7)) ;
                ProdukList.add( cursor.getString(8)) ;
                ProdukList.add( cursor.getString(9)) ;
                ProdukList.add( cursor.getString(10)) ;
                ProdukList.add( cursor.getString(11)) ;
                ProdukList.add( cursor.getString(12)) ;
                ProdukList.add( cursor.getString(13)) ;
                ProdukList.add( cursor.getString(14)) ;
                ProdukList.add( cursor.getString(15)) ;
                ProdukList.add( cursor.getString(16)) ;
                ProdukList.add(cursor.getString(17)) ;
                ProdukList.add( cursor.getString(18)) ;
                ProdukList.add( cursor.getString(19)) ;
                ProdukList.add( cursor.getString(20)) ;
//                ProdukList.add();
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();

        return ProdukList;

    }


    ////////////////////////////////////////////////////////////////////////////////////////////
    // Input Data ke Spinner
    ///////////////////////////////////////////////////////////////////////////////////////////

    public List<String> getAllToSpinner(String CustID){
        List<String> toSpinner = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + NAMA_TABEL_PLAFOND + " where " + TAG_CUSTID_PLAFOND + " = '" + CustID + "' ;" ;

        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getReadableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        String crlimit="", crlimit2="";
        if (cursor.moveToFirst()) {
            do {

                crlimit = cursor.getString(2);
                crlimit2 = cursor.getString(3);
                crlimit = crlimit + crlimit2;
                //dataList.add(contact);
            } while (cursor.moveToNext());
        }

        String arr[] = crlimit.split("\\;",-1);
        for(int i = 0; i< arr.length; i++)
        {
            toSpinner.add(arr[i]);
        }
        cursor.close();

        return toSpinner;

    }


    /////////////////////////////////////////////////////////////////////////////////////////
    // Getting All Contacts Produk
    ////////////////////////////////////////////////////////////////////////////////////////

    public List<DataVariabel> getAllContactsProduk(String username, String password, String crc) {
        List<DataVariabel> contactList = new ArrayList<DataVariabel>();
        // Select All Query

//        String selectQuery = "SELECT  * FROM " + NAMA_TABEL;
        String nilai = "ok";
        String selectQuery = "SELECT  * FROM " + NAMA_TABEL_PRODUK + " ;" ;


        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataVariabel contact = new DataVariabel();
                contact.setKodeProduk(cursor.getString(0));
                contact.setNamaProduk(cursor.getString(1));
                contact.setGroupID(cursor.getString(2));
                contact.setUnitB(cursor.getString(3));
                contact.setUnitM(cursor.getString(4));
                contact.setUnitK(cursor.getString(5));
                contact.setQtyB(cursor.getString(6));
                contact.setQtyM(cursor.getString(7));
                contact.setQtyK(cursor.getString(8));
                contact.setHarga1(cursor.getString(9));
                contact.setHarga2(cursor.getString(10));
                contact.setHarga3(cursor.getString(11));
                contact.setHarga4(cursor.getString(12));
                contact.setHarga5(cursor.getString(13));
                contact.setHarga6(cursor.getString(14));
                contact.setHarga7(cursor.getString(15));
                contact.setHarga8(cursor.getString(16));
                contact.setHarga9(cursor.getString(17));
                contact.setHarga10(cursor.getString(18));
                contact.setBhjual1(cursor.getString(19));
                contact.setBhjual2(cursor.getString(20));
                contact.setBhjual3(cursor.getString(21));
                contact.setBhjual4(cursor.getString(22));
                contact.setBhjual5(cursor.getString(23));
                contact.setBhjual6(cursor.getString(24));
                contact.setBhjual7(cursor.getString(25));
                contact.setBhjual8(cursor.getString(26));
                contact.setBhjual9(cursor.getString(27));
                contact.setBhjual10(cursor.getString(28));
                contact.setTgl1a(cursor.getString(29));
                contact.setTgl2a(cursor.getString(30));
                contact.setTgl3a(cursor.getString(31));
                contact.setTgl4a(cursor.getString(32));
                contact.setTgl5a(cursor.getString(33));
                contact.setTgl6a(cursor.getString(34));
                contact.setTgl7a(cursor.getString(35));
                contact.setTgl8a(cursor.getString(36));
                contact.setTgl9a(cursor.getString(37));
                contact.setTgl10a(cursor.getString(38));
                contact.setTgl1b(cursor.getString(39));
                contact.setTgl2b(cursor.getString(40));
                contact.setTgl3b(cursor.getString(41));
                contact.setTgl4B(cursor.getString(42));
                contact.setTgl5b(cursor.getString(43));
                contact.setTgl6b(cursor.getString(44));
                contact.setTgl7b(cursor.getString(45));
                contact.setTgl8b(cursor.getString(46));
                contact.setTgl9b(cursor.getString(47));
                contact.setTgl10b(cursor.getString(48));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // Masukkan Data Produk
    ///////////////////////////////////////////////////////////////////////////////////////

    public ContentValues InsertDataProduk(String kode, String nama,
                                   String group_id, String unitb,
                                   String unitm, String unitk, String qtyB, String qtyM, String qtyK, String harga1,
        String harga2, String harga3, String harga4, String harga5, String harga6,
                                          String harga7, String harga8, String harga9, String harga10,
                                          String bhjual1, String bhjual2, String bhjual3, String bhjual4,
                                          String bhjual5, String bhjual6, String bhjual7, String bhjual8,
                                          String bhjual9, String bhjual10, String tgl1a, String tgl2a,
                                          String tgl3a, String tgl4a, String tgl5a, String tgl6a, String tgl7a,
                                          String tgl8a, String tgl9a, String tgl10a, String tgl1b, String tgl2b,
                                          String tgl3b, String tgl4b, String tgl5b, String tgl6b, String tgl7b,
                                          String tgl8b, String tgl9b, String tgl10b) {
        ContentValues values = new ContentValues();
        values.put(SqliteManagerData.TAG_KODE, kode);
        values.put(SqliteManagerData.TAG_NAMA, nama);
        values.put(SqliteManagerData.TAG_GROUP_ID, group_id);
        values.put(SqliteManagerData.TAG_UNITB, unitb);
        values.put(SqliteManagerData.TAG_UNITM, unitm);
        values.put(SqliteManagerData.TAG_UNITK, unitk);
        values.put(SqliteManagerData.TAG_QTYB, qtyB);
        values.put(SqliteManagerData.TAG_QTYM, qtyM);
        values.put(SqliteManagerData.TAG_QTYK, qtyK);
        values.put(SqliteManagerData.TAG_HARGA1, harga1);
        values.put(SqliteManagerData.TAG_HARGA2, harga2);
        values.put(SqliteManagerData.TAG_HARGA3, harga3);
        values.put(SqliteManagerData.TAG_HARGA4, harga4);
        values.put(SqliteManagerData.TAG_HARGA5, harga5);
        values.put(SqliteManagerData.TAG_HARGA6, harga6);
        values.put(SqliteManagerData.TAG_HARGA7, harga7);
        values.put(SqliteManagerData.TAG_HARGA8, harga8);
        values.put(SqliteManagerData.TAG_HARGA9, harga9);
        values.put(SqliteManagerData.TAG_HARGA10, harga10);
        values.put(SqliteManagerData.TAG_BHJUAL1, bhjual1);
        values.put(SqliteManagerData.TAG_BHJUAL2, bhjual2);
        values.put(SqliteManagerData.TAG_BHJUAL3, bhjual3);
        values.put(SqliteManagerData.TAG_BHJUAL4, bhjual4);
        values.put(SqliteManagerData.TAG_BHJUAL5, bhjual5);
        values.put(SqliteManagerData.TAG_BHJUAL6, bhjual6);
        values.put(SqliteManagerData.TAG_BHJUAL7, bhjual7);
        values.put(SqliteManagerData.TAG_BHJUAL8, bhjual8);
        values.put(SqliteManagerData.TAG_BHJUAL9, bhjual9);
        values.put(SqliteManagerData.TAG_BHJUAL10, bhjual10);
        values.put(SqliteManagerData.TAG_TGL1A, tgl1a);
        values.put(SqliteManagerData.TAG_TGL2A, tgl2a);
        values.put(SqliteManagerData.TAG_TGL3A, tgl3a);
        values.put(SqliteManagerData.TAG_TGL4A, tgl4a);
        values.put(SqliteManagerData.TAG_TGL5A, tgl5a);
        values.put(SqliteManagerData.TAG_TGL6A, tgl6a);
        values.put(SqliteManagerData.TAG_TGL7A, tgl7a);
        values.put(SqliteManagerData.TAG_TGL8A, tgl8a);
        values.put(SqliteManagerData.TAG_TGL9A, tgl9a);
        values.put(SqliteManagerData.TAG_TGL10A, tgl10a);
        values.put(SqliteManagerData.TAG_TGL1B, tgl1b);
        values.put(SqliteManagerData.TAG_TGL2B, tgl2b);
        values.put(SqliteManagerData.TAG_TGL3B, tgl3b);
        values.put(SqliteManagerData.TAG_TGL4B, tgl4b);
        values.put(SqliteManagerData.TAG_TGL5B, tgl5b);
        values.put(SqliteManagerData.TAG_TGL6B, tgl6b);
        values.put(SqliteManagerData.TAG_TGL7B, tgl7b);
        values.put(SqliteManagerData.TAG_TGL8B, tgl8b);
        values.put(SqliteManagerData.TAG_TGL9B, tgl9b);
        values.put(SqliteManagerData.TAG_TGL10B, tgl10b);

        //values.put(SqliteManager.FIELD_TIPE_SALES, tipe_sales);
        return values;
    }

}
