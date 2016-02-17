package distributor.app.material.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import distributor.app.material.model.Contact;
import distributor.app.material.model.Customer;
import distributor.app.material.model.Header;

/**
 * Created by feetbo on 10/11/2015.
 */
public class SqliteManagerPendingKirim {
    public static final int VERSI_DATABASE= 2;
    public static final String NAMA_DATABASE = "dbHeaderDetail2";
    public static final String NAMA_HEADER = "tbHeader2";
    public static final String NAMA_DETAIL = "tbDetail2";

    //////////////////////////////////////////////////////////////////////////////////////////
    // Data Header
    /////////////////////////////////////////////////////////////////////////////////////////

    public static final String FIELD_ID = "_id";
    public static final int POSISI_ID = 0;
    public static final int POSISI_FAKTUR_PENJUALAN = 1;
    public static final int POSISI_CUSTOMER_ID = 2;
    public static final int POSISI_KODE_TRANS = 3;
    public static final int POSISI_TANGGAL = 4;
    public static final int POSISI_TANGGAL_JAM = 5;
    public static final int POSISI_KODE_SALES = 6;
    public static final int POSISI_REFF = 7;
    public static final int POSISI_GRSS = 8;
    public static final int POSISI_PPN = 9;
    public static final int POSISI_D1 = 10;
    public static final int POSISI_D2 = 11;
    public static final int POSISI_D3 = 12;
    public static final int POSISI_NETT = 13;
    public static final int POSISI_LAT = 14;
    public static final int POSISI_LONG = 15;
    public static final int POSISI_JLHREC = 16;
    public static final int POSISI_IMEI = 17;
    public static final int POSISI_ORDERNO = 18;
    public static final int POSISI_KET = 19;
    public static final int POSISI_GROUP = 20;
    public static final int POSISI_TOP = 21;
    public static final int POSISI_TO_CANVAS_DS = 22;

    /*
    public static final int POSISI_TO_CANVAS_DS = 1;
    public static final int POSISI_NO_POCUSTOMER = 3;
    public static final int POSISI_CATATAN = 4;
    public static final int POSISI_GROUP = 6;
    public static final int POSISI_NAMA_CUSTOMER = 7;
    public static final int POSISI_ALAMAT = 8;
*/
    public static final String FIELD_FAKTUR_PENJUALAN = "faktur_penjualan";
    public static final String FIELD_CUSTOMER_ID = "customer_id";
    public static final String FIELD_KODE_TRANS = "kode_trans";
    public static final String FIELD_TGL = "tgl";
    public static final String FIELD_TANGGAL_JAM = "tgl_jam";
    public static final String FIELD_KODE_SALES = "kode_sales";
    public static final String FIELD_REFF = "reff";
    public static final String FIELD_GRSS = "grss";
    public static final String FIELD_PPN = "ppn";
    public static final String FIELD_D1 = "d1";
    public static final String FIELD_D2 = "d2";
    public static final String FIELD_D3 = "d3";
    public static final String FIELD_NETT = "nett";
    public static final String FIELD_LAT = "lat";
    public static final String FIELD_LONG = "lon";
    public static final String FIELD_JLHREC = "jlhrec";
    public static final String FIELD_IMEI = "imei";
    public static final String FIELD_ORDERNO = "orderno";
    public static final String FIELD_KET = "ket";
    public static final String FIELD_GROUP = "grup";
    public static final String FIELD_TOP = "top";
    public static final String FIELD_TO_CANVAS_DS = "canvas";



/*
    public static final String FIELD_TO_CANVAS_DS = "tocanvasds";
    public static final String FIELD_FAKTUR_PENJUALAN = "faktur_penjualan";
    public static final String FIELD_NO_POCUSTOMER = "no_pocustomer";
    public static final String FIELD_CATATAN = "catatan";
    public static final String FIELD_CUSTOMER_ID = "customer_id";
    public static final String FIELD_GROUP = "gropu";
    public static final String FIELD_NAMA_CUSTOMER = "nama_customer";
    public static final String FIELD_ALAMAT = "alamat";
*/



    public static final String[] FIELD_TABEL = {SqliteManagerPendingKirim.FIELD_ID,
            SqliteManagerPendingKirim.FIELD_FAKTUR_PENJUALAN,
            SqliteManagerPendingKirim.FIELD_CUSTOMER_ID,
            SqliteManagerPendingKirim.FIELD_KODE_TRANS,
            SqliteManagerPendingKirim.FIELD_TGL,
            SqliteManagerPendingKirim.FIELD_TANGGAL_JAM,
            SqliteManagerPendingKirim.FIELD_KODE_SALES,
            SqliteManagerPendingKirim.FIELD_REFF,
            SqliteManagerPendingKirim.FIELD_GRSS,
            SqliteManagerPendingKirim.FIELD_PPN,
            SqliteManagerPendingKirim.FIELD_D1,
            SqliteManagerPendingKirim.FIELD_D2,
            SqliteManagerPendingKirim.FIELD_D3,
            SqliteManagerPendingKirim.FIELD_NETT,
            SqliteManagerPendingKirim.FIELD_LAT,
            SqliteManagerPendingKirim.FIELD_LONG,
            SqliteManagerPendingKirim.FIELD_JLHREC,
            SqliteManagerPendingKirim.FIELD_IMEI,
            SqliteManagerPendingKirim.FIELD_ORDERNO,
            SqliteManagerPendingKirim.FIELD_KET,
            SqliteManagerPendingKirim.FIELD_GROUP,
            SqliteManagerPendingKirim.FIELD_TOP,
            SqliteManagerPendingKirim.FIELD_TO_CANVAS_DS

            //  SqliteManager.FIELD_TIPE_SALES
    };

    /////////////////////////////////////////////////////////////////////////////////////////////
    // Data Details
    ////////////////////////////////////////////////////////////////////////////////////////////

    public static final String FIELD_NO_URUT = "no_urut"
            ;    public static final String FIELD_NO_FAKTUR = "no_faktur";
    public static final String FIELD_STOCK_ID = "stock_id";
    public static final String FIELD_STOCK_NAME = "stock_name";
    public static final String FIELD_STOCK_OUTLET = "stock_outlet";
    public static final String FIELD_QTY = "qty";
    public static final String FIELD_HARGA = "harga";
    public static final String FIELD_JLH = "jlh";
    public static final String FIELD_TOTAL = "total";
    public static final String FIELD_QTYB = "qtyb";
    public static final String FIELD_QTYM = "qtym";
    public static final String FIELD_QTYK = "qtyk";
    public static final String FIELD_RPD1 = "rpd1";
    public static final String FIELD_RPD2 = "rpd2";
    public static final String FIELD_RPD3 = "rpd3";

    public static final int POSISI_NO_URUT = 1;
    public static final int POSISI_NO_FAKTUR = 2;
    public static final int POSISI_STOCK_ID = 3;
    public static final int POSISI_STOCK_NAME = 4;
    public static final int POSISI_STOCK_OUTLET = 5;
    public static final int POSISI_QTY = 6;
    public static final int POSISI_HARGA = 7;
    public static final int POSISI_JLH = 8;
    public static final int POSISI_DETAILD1 = 9;
    public static final int POSISI_DETAILD2 = 10;
    public static final int POSISI_DETAILD3 = 11;
    public static final int POSISI_TOTAL = 12;
    public static final int POSISI_QTYB = 13;
    public static final int POSISI_QTYM = 14;
    public static final int POSISI_QTYK = 15;
    public static final int POSISI_RPD1 = 16;
    public static final int POSISI_RPD2 = 17;
    public static final int POSISI_RPD3 = 18;

    public static final String[] FIELD_TABEL_DETAIL = {SqliteManagerPendingKirim.FIELD_ID,
            SqliteManagerPendingKirim.FIELD_NO_URUT,
            SqliteManagerPendingKirim.FIELD_NO_FAKTUR,
            SqliteManagerPendingKirim.FIELD_STOCK_ID,
            SqliteManagerPendingKirim.FIELD_STOCK_NAME,
            SqliteManagerPendingKirim.FIELD_STOCK_OUTLET,
            SqliteManagerPendingKirim.FIELD_QTY,
            SqliteManagerPendingKirim.FIELD_HARGA,
            SqliteManagerPendingKirim.FIELD_JLH,
            SqliteManagerPendingKirim.FIELD_D1,
            SqliteManagerPendingKirim.FIELD_D2,
            SqliteManagerPendingKirim.FIELD_D3,
            SqliteManagerPendingKirim.FIELD_TOTAL,
            SqliteManagerPendingKirim.FIELD_QTYB,
            SqliteManagerPendingKirim.FIELD_QTYM,
            SqliteManagerPendingKirim.FIELD_QTYK,
            SqliteManagerPendingKirim.FIELD_RPD1,
            SqliteManagerPendingKirim.FIELD_RPD2,
            SqliteManagerPendingKirim.FIELD_RPD3
            //  SqliteManager.FIELD_TIPE_SALES
    };



    private Context crudContext;
    private SQLiteDatabase crudDatabase, crudDatabase2;
    private SqliteManagerHelper crudHelper, crudHelper2;

    private static class SqliteManagerHelper extends SQLiteOpenHelper {
        private static final String BUAT_TABEL =
                "create table " + NAMA_HEADER + " (" +
                        SqliteManagerPendingKirim.FIELD_ID + " integer primary key autoincrement, " +
                        SqliteManagerPendingKirim.FIELD_FAKTUR_PENJUALAN + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_CUSTOMER_ID + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_KODE_TRANS + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_TGL + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_TANGGAL_JAM + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_KODE_SALES + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_REFF + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_GRSS + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_PPN + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_D1+ " text not null ," +
                        SqliteManagerPendingKirim.FIELD_D2 + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_D3 + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_NETT+ " text not null ," +
                        SqliteManagerPendingKirim.FIELD_LAT+ " text not null ," +
                        SqliteManagerPendingKirim.FIELD_LONG+ " text not null ," +
                        SqliteManagerPendingKirim.FIELD_JLHREC+ " text not null ," +
                        SqliteManagerPendingKirim.FIELD_IMEI+ " text not null ," +
                        SqliteManagerPendingKirim.FIELD_ORDERNO+ " text not null ," +
                        SqliteManagerPendingKirim.FIELD_KET+ " text not null ," +
                        SqliteManagerPendingKirim.FIELD_GROUP+ " text not null ," +
                        SqliteManagerPendingKirim.FIELD_TOP+ " text not null ," +
                        SqliteManagerPendingKirim.FIELD_TO_CANVAS_DS+ " text not null " +

                        //   SqliteManager.FIELD_TIPE_SALES + " text not null " +
                        ");";


        private static final String BUAT_TABEL_DETAIL =
                "create table " + NAMA_DETAIL + " (" +
                        SqliteManagerPendingKirim.FIELD_ID + " integer primary key autoincrement, " +
                        SqliteManagerPendingKirim.FIELD_NO_URUT + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_NO_FAKTUR + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_STOCK_ID + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_STOCK_NAME + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_STOCK_OUTLET + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_QTY + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_HARGA + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_JLH + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_D1 + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_D2+ " text not null ," +
                        SqliteManagerPendingKirim.FIELD_D3 + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_TOTAL+ " text not null ," +
                        SqliteManagerPendingKirim.FIELD_QTYB + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_QTYM + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_QTYK + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_RPD1 + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_RPD2 + " text not null ," +
                        SqliteManagerPendingKirim.FIELD_RPD3 + " text not null " +
                        //   SqliteManager.FIELD_TIPE_SALES + " text not null " +
                        ");";
        public SqliteManagerHelper(Context context) {
            super(context, NAMA_DATABASE, null, VERSI_DATABASE);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(BUAT_TABEL);
            database.execSQL(BUAT_TABEL_DETAIL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {}
    }

    public SqliteManagerPendingKirim(Context context) {
        crudContext = context;
    }

    public void bukaKoneksi() throws SQLException {
        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        crudHelper2 = new SqliteManagerHelper(crudContext);
        crudDatabase2 = crudHelper2.getWritableDatabase();
    }

    public void tutupKoneksi() {
        crudHelper.close();
        crudHelper = null;
        crudDatabase = null;
        crudHelper2.close();
        crudHelper2 = null;
        crudDatabase2 = null;

    }

    public long insertData(ContentValues values) {
        return crudDatabase.insert(NAMA_HEADER, null, values);
    }

    public boolean updateData(long rowId, ContentValues values) {
        return crudDatabase.update(NAMA_HEADER, values,
                SqliteManager.FIELD_ID + "=" + rowId, null) > 0;
    }

    public boolean hapusData(long rowId) {
        return crudDatabase.delete(NAMA_HEADER,
                SqliteManager.FIELD_ID + "=" + rowId, null) > 0;
    }

    public Cursor bacaData() {
        return crudDatabase.query(NAMA_HEADER,FIELD_TABEL,null, null, null, null,SqliteManager.FIELD_KODE_SALES + " DESC");

    }

    public Cursor bacaDataTerseleksi(long rowId) throws SQLException {
        Cursor cursor = crudDatabase.query(true, NAMA_HEADER,FIELD_TABEL,FIELD_ID + "=" + rowId,null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    /////////////////////////////////////////////////////////////////////////////////
    // ngambil data Keterangan dan orderno
    ////////////////////////////////////////////////////////////////////////////////

    public ArrayList<Contact> getAllDataKetdanOrder(String fakturpenjualan){

        ArrayList<Contact> ketorder = new ArrayList<Contact>();
        String selectQuery = "SELECT  * FROM " + NAMA_HEADER  + " where " +FIELD_FAKTUR_PENJUALAN + " = '" + fakturpenjualan + "';";

        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setName(cursor.getString(POSISI_FAKTUR_PENJUALAN));
                contact.setKode_Sales(cursor.getString(POSISI_KET));
                ketorder.add(contact);

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

        return ketorder;
    }

    public ArrayList<Header> getAllDataProdukListViewArrayList(String fakturpenjualan){
        fakturpenjualan.trim();
        ArrayList<Header> ProdukList = new ArrayList<Header>();
        String selectQuery = "SELECT * FROM " + NAMA_DETAIL + " where " +FIELD_NO_FAKTUR + " = '" + fakturpenjualan + "';";

        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String diskon1="0", diskon2="0", diskon3="0", diskonRp1="0", diskonRp2="0", diskonRp3="0";
                diskonRp1 = cursor.getString(POSISI_RPD1);

                Log.d("diskon 1 : ", "" + cursor.getString(POSISI_D1));
                Log.d("diskon 2 : ", "" + cursor.getString(POSISI_D2));
                Log.d("diskon 3 : ", "" + cursor.getString(POSISI_D3));
                Log.d("diskon 1 : ", "" + cursor.getString(POSISI_DETAILD1));
                Log.d("diskon 2 : ", "" + cursor.getString(POSISI_DETAILD2));
                Log.d("diskon 3 : ", "" + cursor.getString(POSISI_DETAILD3));
                Log.d("diskon rp 1 : ", "" + cursor.getString(POSISI_RPD1));
                Log.d("diskon rp 2 : ", "" + cursor.getString(POSISI_RPD2));
                Log.d("diskon rp 3 : ", "" + cursor.getString(POSISI_RPD3));
                if(diskonRp1.equals("0.0"))
                {
                    diskon1 = Math.round(Double.parseDouble(cursor.getString(POSISI_DETAILD1))) +"%";
                }else{diskon1 = "Rp " + diskonRp1;}

                diskonRp2 = cursor.getString(POSISI_RPD2);

                if(diskonRp2.equals("0.0"))
                {
                    diskon2 = Math.round(Double.parseDouble(cursor.getString(POSISI_DETAILD2))) + "%";
                }else{diskon2 = "Rp " + diskonRp2;}

                diskonRp3 = cursor.getString(POSISI_RPD3);

                if(diskonRp3.equals("0.0"))
                {
                    diskon3 = Math.round(Double.parseDouble(cursor.getString(POSISI_DETAILD3))) + "%";
                }else{diskon3 = "Rp " + diskonRp3;}
                Header customer = new Header(cursor.getString(POSISI_STOCK_ID) + "- " + cursor.getString(POSISI_STOCK_NAME), "Qty: "+cursor.getString(POSISI_QTY)
                        +", Harga: Rp " + Math.round(Double.parseDouble(cursor.getString(POSISI_HARGA))),
                        "Stock: " + cursor.getString(POSISI_STOCK_OUTLET), "Harga 2" +cursor.getString(POSISI_HARGA),
                        "Total belanja: Rp " +Math.round(Double.parseDouble(cursor.getString(POSISI_JLH))), "diskon 1 : " + diskon1 + ", diskon 2 : "+ diskon2 + ", diskon 3 :" +
                        diskon3 + "= Rp "+ Math.round(Double.parseDouble(cursor.getString(POSISI_TOTAL))));
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

    // Getting All Contacts
    public ArrayList<HashMap<String, String>> getAllDetailforList(String kodefaktur) {
        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
        // Select All Query

//        String selectQuery = "SELECT  * FROM " + NAMA_TABEL;
        String nilai = "ok";
        String selectQuery = "SELECT  * FROM " + NAMA_DETAIL + " where " +FIELD_NO_FAKTUR + " = '" + kodefaktur + "';";


        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list


        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contact = new HashMap<String, String>();
                Log.d("faktur", kodefaktur);
              /*  contact.put("nama" , cursor.getString(POSISI_FAKTUR_PENJUALAN_DETAILS));
                contact.put("email" , cursor.getString(POSISI_KODE_PRODUK));
                contact.put("phone" , cursor.getString(POSISI_HARGA_PRODUK));*/



                contact.put(FIELD_NO_FAKTUR , cursor.getString(POSISI_NO_FAKTUR));
                contact.put(FIELD_STOCK_ID , cursor.getString(POSISI_STOCK_ID));
                contact.put(FIELD_STOCK_NAME , cursor.getString(POSISI_STOCK_NAME));
                contact.put(FIELD_STOCK_OUTLET, cursor.getString(POSISI_STOCK_OUTLET));
                contact.put(FIELD_TOTAL, cursor.getString(POSISI_TOTAL));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }




    ////////////////////////////////////////////////////////////////////////////////////////////
    // Input Data Header ke dalam ListView Arraylist
    ////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<Header> getAllDataHeaderArrayList(){

        ArrayList<Header> ProdukList = new ArrayList<Header>();
        String selectQuery = "SELECT  * FROM " + NAMA_HEADER +";";
        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        SqliteManagerPendingKirim dbPending = new SqliteManagerPendingKirim(crudContext);
        dbPending.bukaKoneksi();

        SqliteManagerData dbData = new SqliteManagerData(crudContext);
        dbData.bukaKoneksi();
        if (cursor.moveToFirst()) {
            do {





               double nilaiNett = 0;

                double totalbelanja = 0;
                if ((int) dbPending.JumlahTotalNilaiGross(cursor.getString(POSISI_FAKTUR_PENJUALAN)) > 0) {

                     totalbelanja = Math.round(dbPending.JumlahTotalNilaiGross(cursor.getString(POSISI_FAKTUR_PENJUALAN)));

                } else {
                    totalbelanja = 0;
                }

                /*Header customer = new Header(cursor.getString(POSISI_FAKTUR_PENJUALAN), cursor.getString(POSISI_CUSTOMER_ID), cursor.getString(POSISI_TANGGAL),
                        cursor.getString(POSISI_KODE_SALES), cursor.getString(POSISI_ORDERNO),
                        cursor.getString(POSISI_KET));*/
                String alamat = dbData.ambilAlamatCustomer(cursor.getString(POSISI_CUSTOMER_ID));
                String alamat2 = dbData.ambilAlamatCustomer2(cursor.getString(POSISI_CUSTOMER_ID));
                Header customer = new Header(cursor.getString(POSISI_FAKTUR_PENJUALAN), cursor.getString(POSISI_CUSTOMER_ID) + " - "+ alamat + " - " +alamat2, cursor.getString(POSISI_TANGGAL),
                        ""+ totalbelanja, cursor.getString(POSISI_ORDERNO),
                       ""+ Math.round(Double.parseDouble(cursor.getString(POSISI_NETT))));
                Log.d("diskon y" , ""+ cursor.getString(POSISI_NETT));
                ProdukList.add(customer);

            } while (cursor.moveToNext());
        }
        dbPending.tutupKoneksi();
        dbData.tutupKoneksi();
        // closing connection
        cursor.close();

        return ProdukList;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    // Input Data Header ke dalam ListView Arraylist
    ////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<Header> getAllDataHeaderPending(){

        ArrayList<Header> ProdukList = new ArrayList<Header>();
        String selectQuery = "SELECT  * FROM " + NAMA_HEADER +";";
        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                Header customer = new Header(cursor.getString(POSISI_FAKTUR_PENJUALAN), cursor.getString(POSISI_CUSTOMER_ID), cursor.getString(POSISI_TANGGAL),
                        cursor.getString(POSISI_KODE_SALES), cursor.getString(POSISI_ORDERNO),
                        cursor.getString(POSISI_PPN));
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

    public void hapusHeader2()
    {
        String selectQuery = "SELECT * FROM " + NAMA_HEADER +";";
        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contact = new HashMap<String, String>();

                contact.put(FIELD_TGL, cursor.getString(POSISI_TANGGAL));
                contact.put(FIELD_FAKTUR_PENJUALAN, cursor.getString(POSISI_FAKTUR_PENJUALAN));
                try {
                    String value = cursor.getString(POSISI_TANGGAL);

                    Date today = new Date();

                    Date date2 = null;
                    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
                    String date = DATE_FORMAT.format(today);
                    today = DATE_FORMAT.parse(date);
                    date2 = DATE_FORMAT.parse(value);
                    long diff = Math.abs(today.getTime() - date2.getTime());
                    long diffDays = diff / (24 * 60 * 60 * 1000);
                    Log.d("service", "perbedaan tanggal : " + diffDays);
                    //if (diffDays >= 3) {
                    String selectQuery2 = "DELETE FROM " + NAMA_HEADER + " where " + FIELD_FAKTUR_PENJUALAN + " = '" + cursor.getString(POSISI_FAKTUR_PENJUALAN) + "';";
                    crudHelper2 = new SqliteManagerHelper(crudContext);
                    crudDatabase2 = crudHelper2.getWritableDatabase();
                    Cursor cursor2 = crudDatabase2.rawQuery(selectQuery2, null);
                    if (cursor2.moveToFirst()) {
                        do {
                            Log.d("service", "Berhasil hapus");
                        } while (cursor2.moveToNext());
                    }
                    //}
                }catch(Exception e)
                {
                    e.printStackTrace();

                }

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }



    }

    // hapus data H-3
    public void hapusHeader()
    {
        String selectQuery = "SELECT * FROM " + NAMA_HEADER +";";
        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contact = new HashMap<String, String>();

                contact.put(FIELD_TGL, cursor.getString(POSISI_TANGGAL));
                contact.put(FIELD_FAKTUR_PENJUALAN, cursor.getString(POSISI_FAKTUR_PENJUALAN));
                try {
                    String value = cursor.getString(POSISI_TANGGAL);

                    Date today = new Date();

                    Date date2 = null;
                    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
                    String date = DATE_FORMAT.format(today);
                    today = DATE_FORMAT.parse(date);
                    date2 = DATE_FORMAT.parse(value);
                    long diff = Math.abs(today.getTime() - date2.getTime());
                    long diffDays = diff / (24 * 60 * 60 * 1000);
                    Log.d("service", "perbedaan tanggal : " + diffDays);
                    if (diffDays >= 3) {
                        String selectQuery2 = "DELETE FROM " + NAMA_HEADER + " where " + FIELD_FAKTUR_PENJUALAN + " = '" + cursor.getString(POSISI_FAKTUR_PENJUALAN) + "';";
                        crudHelper2 = new SqliteManagerHelper(crudContext);
                        crudDatabase2 = crudHelper2.getWritableDatabase();
                        Cursor cursor2 = crudDatabase2.rawQuery(selectQuery2, null);
                        if (cursor2.moveToFirst()) {
                            do {
                                Log.d("service", "Berhasil hapus");
                            } while (cursor2.moveToNext());
                        }
                    }
                }catch(Exception e)
                {
                    e.printStackTrace();

                }

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }



    }

    public void hapusDetail2()
    {
        String selectQuery = "SELECT * FROM " + NAMA_HEADER +";";
        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contact = new HashMap<String, String>();

                contact.put(FIELD_TGL, cursor.getString(POSISI_TANGGAL));
                contact.put(FIELD_FAKTUR_PENJUALAN, cursor.getString(POSISI_FAKTUR_PENJUALAN));
                try {
                    String value = cursor.getString(POSISI_TANGGAL);

                    Date today = new Date();

                    Date date2 = null;
                    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
                    String date = DATE_FORMAT.format(today);
                    today = DATE_FORMAT.parse(date);
                    date2 = DATE_FORMAT.parse(value);
                    long diff = Math.abs(today.getTime() - date2.getTime());
                    long diffDays = diff / (24 * 60 * 60 * 1000);
                    Log.d("service", "perbedaan tanggal : " + diffDays);
                    //if (diffDays >= 3) {
                    String selectQuery2 = "DELETE FROM " + NAMA_DETAIL + " where " + FIELD_NO_FAKTUR + " = '" + cursor.getString(POSISI_NO_FAKTUR) + "';";
                    crudHelper2 = new SqliteManagerHelper(crudContext);
                    crudDatabase2 = crudHelper2.getWritableDatabase();
                    Cursor cursor2 = crudDatabase2.rawQuery(selectQuery2, null);
                    if (cursor2.moveToFirst()) {
                        do {
                            Log.d("service", "Berhasil hapus");
                        } while (cursor2.moveToNext());
                    }
                    //}
                }catch(Exception e)
                {
                    e.printStackTrace();

                }

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }



    }
    // hapus data H-3
    public void hapusDetail()
    {
        String selectQuery = "SELECT * FROM " + NAMA_HEADER +";";
        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contact = new HashMap<String, String>();

                contact.put(FIELD_TGL, cursor.getString(POSISI_TANGGAL));
                contact.put(FIELD_FAKTUR_PENJUALAN, cursor.getString(POSISI_FAKTUR_PENJUALAN));
                try {
                    String value = cursor.getString(POSISI_TANGGAL);

                    Date today = new Date();

                    Date date2 = null;
                    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
                    String date = DATE_FORMAT.format(today);
                    today = DATE_FORMAT.parse(date);
                    date2 = DATE_FORMAT.parse(value);
                    long diff = Math.abs(today.getTime() - date2.getTime());
                    long diffDays = diff / (24 * 60 * 60 * 1000);
                    Log.d("service", "perbedaan tanggal : " + diffDays);
                    if (diffDays >= 3) {
                        String selectQuery2 = "DELETE FROM " + NAMA_DETAIL + " where " + FIELD_NO_FAKTUR + " = '" + cursor.getString(POSISI_NO_FAKTUR) + "';";
                        crudHelper2 = new SqliteManagerHelper(crudContext);
                        crudDatabase2 = crudHelper2.getWritableDatabase();
                        Cursor cursor2 = crudDatabase2.rawQuery(selectQuery2, null);
                        if (cursor2.moveToFirst()) {
                            do {
                                Log.d("service", "Berhasil hapus");
                            } while (cursor2.moveToNext());
                        }
                    }
                }catch(Exception e)
                {
                    e.printStackTrace();

                }

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }



    }




    // hapus data H-3
    // hapus data H-3
    public void hapusHeaderFaktur(String fakturpenjualan)
    {
        String selectQuery = "DELETE FROM " + NAMA_HEADER + " where " +FIELD_FAKTUR_PENJUALAN + " = '" + fakturpenjualan + "';";

        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contact = new HashMap<String, String>();
                Log.d("service", "Berhasil hapus");
                contact.put(FIELD_STOCK_ID, cursor.getString(POSISI_STOCK_ID));


                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
    }

    // Getting All Contacts
    public ArrayList<HashMap<String, String>> getAllHeader(String fakturpenjualan) {
        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
        // Select All Query

//        String selectQuery = "SELECT  * FROM " + NAMA_TABEL;
        String nilai = "ok";
        String selectQuery = "SELECT  * FROM " + NAMA_HEADER + " where " +FIELD_FAKTUR_PENJUALAN + " = '" + fakturpenjualan + "';";


        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contact = new HashMap<String, String>();

                contact.put(FIELD_FAKTUR_PENJUALAN, cursor.getString(POSISI_FAKTUR_PENJUALAN));
                contact.put(FIELD_CUSTOMER_ID, cursor.getString(POSISI_CUSTOMER_ID));
                contact.put(FIELD_KODE_TRANS, cursor.getString(POSISI_KODE_TRANS));
                contact.put(FIELD_TGL, cursor.getString(POSISI_TANGGAL));
                contact.put(FIELD_TANGGAL_JAM, cursor.getString(POSISI_TANGGAL_JAM));
                contact.put(FIELD_KODE_SALES, cursor.getString(POSISI_KODE_SALES));
                contact.put(FIELD_REFF, cursor.getString(POSISI_REFF));
                contact.put(FIELD_GRSS, cursor.getString(POSISI_GRSS));
                contact.put(FIELD_PPN, cursor.getString(POSISI_PPN));
                contact.put(FIELD_D1, cursor.getString(POSISI_D1));
                contact.put(FIELD_D2, cursor.getString(POSISI_D2));
                contact.put(FIELD_D3, cursor.getString(POSISI_D3));
                contact.put(FIELD_NETT, cursor.getString(POSISI_NETT));
                contact.put(FIELD_LAT, cursor.getString(POSISI_LAT));
                contact.put(FIELD_LONG, cursor.getString(POSISI_LONG));
                contact.put(FIELD_JLHREC, cursor.getString(POSISI_JLHREC));
                contact.put(FIELD_IMEI, cursor.getString(POSISI_IMEI));
                contact.put(FIELD_ORDERNO, cursor.getString(POSISI_ORDERNO));
                contact.put(FIELD_KET, cursor.getString(POSISI_KET));
                contact.put(FIELD_GROUP, cursor.getString(POSISI_GROUP));
                contact.put(FIELD_TOP, cursor.getString(POSISI_TOP));
                contact.put(FIELD_TO_CANVAS_DS, cursor.getString(POSISI_TO_CANVAS_DS));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    // Getting Pemesanan yang double
    public List<Contact> getDuplikat(String fakturpenjualan, String kodeproduk) {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query


//        String selectQuery = "SELECT  * FROM " + NAMA_TABEL;
        String nilai = "ok";
        String selectQuery = "SELECT  * FROM " + NAMA_HEADER + " where " +FIELD_FAKTUR_PENJUALAN + " = '" + fakturpenjualan + "' ;"
                ;
        Log.d("nama :  " , "masuk");


        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();

                Log.d("nama :  " , cursor.getString(POSISI_FAKTUR_PENJUALAN));

                contact.setName( cursor.getString(POSISI_FAKTUR_PENJUALAN));


                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public ContentValues InsertDataHeader(String fakturpenjualan, String customerid,
                                          String kode_trans, String tgl, String tanggaljam,
                                          String kodesales, String reff, String grss, String ppn, String d1,
                                          String d2, String d3, String nett, String lat, String lon, String
                                                  jlhrec, String imei, String orderno, String ket, String group, String top,
                                          String tocanvasds) {
        ContentValues values = new ContentValues();



        values.put(SqliteManagerDetails.FIELD_FAKTUR_PENJUALAN, fakturpenjualan);
        values.put(SqliteManagerDetails.FIELD_CUSTOMER_ID, customerid);
        values.put(SqliteManagerDetails.FIELD_KODE_TRANS, kode_trans);
        values.put(SqliteManagerDetails.FIELD_TGL, tgl);
        values.put(SqliteManagerDetails.FIELD_TANGGAL_JAM, tanggaljam);
        values.put(SqliteManagerDetails.FIELD_KODE_SALES, kodesales);
        values.put(SqliteManagerDetails.FIELD_REFF, reff);
        values.put(SqliteManagerDetails.FIELD_GRSS, grss);
        values.put(SqliteManagerDetails.FIELD_PPN, ppn);
        values.put(SqliteManagerDetails.FIELD_D1, d1);
        values.put(SqliteManagerDetails.FIELD_D2, d2);
        values.put(SqliteManagerDetails.FIELD_D3, d3);
        values.put(SqliteManagerDetails.FIELD_NETT, nett);
        values.put(SqliteManagerDetails.FIELD_LAT, lat);
        values.put(SqliteManagerDetails.FIELD_LONG, lon);
        values.put(SqliteManagerDetails.FIELD_JLHREC, jlhrec);
        values.put(SqliteManagerDetails.FIELD_IMEI, imei);
        values.put(SqliteManagerDetails.FIELD_ORDERNO, orderno);
        values.put(SqliteManagerDetails.FIELD_KET, ket);
        values.put(SqliteManagerDetails.FIELD_GROUP, group);
        values.put(SqliteManagerDetails.FIELD_TOP, top );
        values.put(SqliteManagerDetails.FIELD_TO_CANVAS_DS, tocanvasds);

        return values;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    // Update Nilai Header
    //////////////////////////////////////////////////////////////////////////////////////////////
    public void setUpdateHeader(String totalGross, String ppn, String net,  String faktur, String date,
                                String d1, String d2, String d3, String jlhrec){

        String updateQuery ="UPDATE " + NAMA_HEADER+" SET " + FIELD_GRSS+ " =  '" + totalGross +"' ,"+  FIELD_PPN + "= '" + ppn + "' ," + FIELD_NETT + "='" + net +"' ," + FIELD_TANGGAL_JAM + "='" + date +"'  , " +
                FIELD_D1 + "= '" + d1 + "' ," + FIELD_D2 + "= '" +d2 + "' ," + FIELD_D3 + "= '" +d3+ "' ," + FIELD_JLHREC  + "= '" + jlhrec +"'"     +        "  WHERE "
                + FIELD_FAKTUR_PENJUALAN + " = '" +  faktur + "' ;";
        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor c= crudDatabase.rawQuery(updateQuery, null);
        c.moveToFirst();
        c.close();
        Log.d("faktur", "masuk : " + totalGross);


        updateQuery = "SELECT  * FROM " + NAMA_HEADER + " where " +FIELD_GRSS + " = '" + totalGross + "';";
        c= crudDatabase.rawQuery(updateQuery, null);

        if (c.moveToFirst()) {
            do {
                Log.d("faktur", "sukses : " + c.getString(POSISI_FAKTUR_PENJUALAN));
              /*  contact.put("nama" , cursor.getString(POSISI_FAKTUR_PENJUALAN_DETAILS));
                contact.put("email" , cursor.getString(POSISI_KODE_PRODUK));
                contact.put("phone" , cursor.getString(POSISI_HARGA_PRODUK));*/


                // Adding contact to list
            } while (c.moveToNext());
            c.close();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Ambil Data TotalGross
    //////////////////////////////////////////////////////////////////////////////////////////////

    public double JumlahTotalNilaiGross(String fakturpenjualan)
    {

        String selectQuery = "SELECT  * FROM " + NAMA_DETAIL + " where " +FIELD_NO_FAKTUR + " = '" + fakturpenjualan + "';";
        double total = 0;

        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list


        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contact = new HashMap<String, String>();

              /*  contact.put("nama" , cursor.getString(POSISI_FAKTUR_PENJUALAN_DETAILS));
                contact.put("email" , cursor.getString(POSISI_KODE_PRODUK));
                contact.put("phone" , cursor.getString(POSISI_HARGA_PRODUK));*/


                total = total + Double.parseDouble(cursor.getString(POSISI_JLH));
                // Log.d("jumlah total gross", ""+ cursor.getString(POSISI_JLH));
                // Adding contact to list

            } while (cursor.moveToNext());
        }
        return total;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Ambil Data TOTAL DISKON X
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public double JumlahTotalDiskonX(String fakturpenjualan)
    {

        String selectQuery = "SELECT  * FROM " + NAMA_DETAIL + " where " +FIELD_NO_FAKTUR + " = '" + fakturpenjualan + "';";
        double total = 0;

        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list


        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contact = new HashMap<String, String>();

              /*  contact.put("nama" , cursor.getString(POSISI_FAKTUR_PENJUALAN_DETAILS));
                contact.put("email" , cursor.getString(POSISI_KODE_PRODUK));
                contact.put("phone" , cursor.getString(POSISI_HARGA_PRODUK));*/


                total = total + Double.parseDouble(cursor.getString(POSISI_TOTAL));
                //Log.d("jumlah total gross", ""+ cursor.getString(POSISI_TOTAL));
                // Adding contact to list

            } while (cursor.moveToNext());
        }
        return total;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    // Insert Data Details
    ////////////////////////////////////////////////////////////////////////////////////////////
    public long insertDataDetail(ContentValues values) {

        return crudDatabase.insert(NAMA_DETAIL, null, values);
    }




    // Getting All Contacts
    public ArrayList<HashMap<String, String>> getAllDetail(String kodefaktur) {
        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
        // Select All Query

//        String selectQuery = "SELECT  * FROM " + NAMA_TABEL;
        String nilai = "ok";
        String selectQuery = "SELECT  * FROM " + NAMA_DETAIL + " where " +FIELD_NO_FAKTUR + " = '" + kodefaktur + "';";


        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list


        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contact = new HashMap<String, String>();
                Log.d("faktur", kodefaktur);
              /*  contact.put("nama" , cursor.getString(POSISI_FAKTUR_PENJUALAN_DETAILS));
                contact.put("email" , cursor.getString(POSISI_KODE_PRODUK));
                contact.put("phone" , cursor.getString(POSISI_HARGA_PRODUK));*/



                contact.put(FIELD_NO_URUT , cursor.getString(POSISI_NO_URUT));
                contact.put(FIELD_NO_FAKTUR , cursor.getString(POSISI_NO_FAKTUR));
                contact.put(FIELD_STOCK_ID , cursor.getString(POSISI_STOCK_ID));
                contact.put(FIELD_STOCK_NAME , cursor.getString(POSISI_STOCK_NAME));
                contact.put(FIELD_STOCK_OUTLET , cursor.getString(POSISI_STOCK_OUTLET));
                contact.put(FIELD_QTY , cursor.getString(POSISI_QTY));
                contact.put(FIELD_HARGA , cursor.getString(POSISI_HARGA));
                contact.put(FIELD_JLH , cursor.getString(POSISI_JLH));
                contact.put(FIELD_D1 , cursor.getString(POSISI_DETAILD1));
                contact.put(FIELD_D2 , cursor.getString(POSISI_DETAILD2));
                contact.put(FIELD_D3 , cursor.getString(POSISI_DETAILD3));
                contact.put(FIELD_TOTAL , cursor.getString(POSISI_TOTAL));
                contact.put(FIELD_QTYB , cursor.getString(POSISI_QTYB));
                contact.put(FIELD_QTYM , cursor.getString(POSISI_QTYM));
                contact.put(FIELD_QTYK , cursor.getString(POSISI_QTYK));
                contact.put(FIELD_RPD1 , cursor.getString(POSISI_RPD1));
                contact.put(FIELD_RPD2 , cursor.getString(POSISI_RPD2));
                contact.put(FIELD_RPD3 , cursor.getString(POSISI_RPD3));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public ContentValues InsertDataDetail(String nourut, String nofaktur, String stock_id, String stockname,
                                          String stockoutlet, String qty, String harga, String jlh, String d1,
                                          String d2, String d3, String total, String qtyb, String qtym, String qtyk,
                                          String rpd1, String rpd2, String rpd3) {
        ContentValues values = new ContentValues();



        values.put(SqliteManagerDetails.FIELD_NO_URUT, nourut);
        values.put(SqliteManagerDetails.FIELD_NO_FAKTUR, nofaktur);
        values.put(SqliteManagerDetails.FIELD_STOCK_ID, stock_id);
        values.put(SqliteManagerDetails.FIELD_STOCK_NAME, stockname);
        values.put(SqliteManagerDetails.FIELD_STOCK_OUTLET, stockoutlet);
        values.put(SqliteManagerDetails.FIELD_QTY, qty);
        values.put(SqliteManagerDetails.FIELD_HARGA, harga);
        values.put(SqliteManagerDetails.FIELD_JLH, jlh);
        values.put(SqliteManagerDetails.FIELD_D1, d1);
        values.put(SqliteManagerDetails.FIELD_D2, d2);
        values.put(SqliteManagerDetails.FIELD_D3, d3);
        values.put(SqliteManagerDetails.FIELD_TOTAL, total);
        values.put(SqliteManagerDetails.FIELD_QTYB, qtyb);
        values.put(SqliteManagerDetails.FIELD_QTYM, qtym);
        values.put(SqliteManagerDetails.FIELD_QTYK, qtyk);
        values.put(SqliteManagerDetails.FIELD_RPD1, rpd1);
        values.put(SqliteManagerDetails.FIELD_RPD2, rpd2);
        values.put(SqliteManagerDetails.FIELD_RPD3, rpd3);

        return values;
    }


}
