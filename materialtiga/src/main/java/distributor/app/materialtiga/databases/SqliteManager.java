package distributor.app.materialtiga.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import distributor.app.materialtiga.model.Contact;

/**
 * Created by feetbo on 10/11/2015.
 */
public class SqliteManager {
    public static final int VERSI_DATABASE= 1;
    public static final String NAMA_DATABASE = "dbCrudSqlite";
    public static final String NAMA_TABEL = "tbUser";

    public static final String FIELD_ID = "_id";
    public static final int POSISI_ID = 0;
    public static final int POSISI_NO_RANGKA = 1;
    public static final int POSISI_NO_MESIN = 2;
    public static final int POSISI_PLAT_KENDARAAN = 3;




    public static final String FIELD_NO_RANGKA = "no_rangka";
    public static final String FIELD_NO_MESIN = "no_mesin";
    public static final String FIELD_PLAT_KENDARAAN = "plat_kendaraan";



    public static final String[] FIELD_TABEL = {SqliteManager.FIELD_ID,
            SqliteManager.FIELD_NO_RANGKA,
            SqliteManager.FIELD_NO_MESIN,
            SqliteManager.FIELD_PLAT_KENDARAAN
          //  SqliteManager.FIELD_TIPE_SALES
    };
    private Context crudContext;
    private SQLiteDatabase crudDatabase;
    private SqliteManagerHelper crudHelper;

    private static class SqliteManagerHelper extends SQLiteOpenHelper {
        private static final String BUAT_TABEL =
                "create table " + NAMA_TABEL + " (" +
                        SqliteManager.FIELD_ID + " integer primary key autoincrement, " +
                        SqliteManager.FIELD_NO_RANGKA + " text not null ," +
                        SqliteManager.FIELD_NO_MESIN + " text not null, " +
                        SqliteManager.FIELD_PLAT_KENDARAAN + " text not null " +

                     //   SqliteManager.FIELD_TIPE_SALES + " text not null " +
                        ");";

        public SqliteManagerHelper(Context context) {
            super(context, NAMA_DATABASE, null, VERSI_DATABASE);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(BUAT_TABEL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {}
    }

    public SqliteManager(Context context) {
        crudContext = context;
    }

    public void bukaKoneksi() throws SQLException {
        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
    }

    public void tutupKoneksi() {
        crudHelper.close();
        crudHelper = null;
        crudDatabase = null;
    }

    public long insertData(ContentValues values) {
        return crudDatabase.insert(NAMA_TABEL, null, values);
    }

    public boolean updateData(long rowId, ContentValues values) {
        return crudDatabase.update(NAMA_TABEL, values,
                SqliteManager.FIELD_ID + "=" + rowId, null) > 0;
    }

    public boolean hapusData(long rowId) {
        return crudDatabase.delete(NAMA_TABEL,
                SqliteManager.FIELD_ID + "=" + rowId, null) > 0;
    }





    // Getting All Contacts
    public List<Contact> getAllContacts(String no_rangka, String no_mesin, String plat_kendaraan) {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query

//        String selectQuery = "SELECT  * FROM " + NAMA_TABEL;
        String nilai = "ok";
        String selectQuery = "SELECT  * FROM " + NAMA_TABEL + " where " + FIELD_NO_RANGKA
                + " = '" + no_rangka + "'  and " + FIELD_NO_MESIN +  " = '"
                + no_mesin + "' " +
                "and " + FIELD_PLAT_KENDARAAN + " = '" + plat_kendaraan + "' ;" ;


        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(POSISI_ID)));
                contact.setKode_Sales(cursor.getString(POSISI_NO_RANGKA));
                contact.setName(cursor.getString(POSISI_NO_MESIN));
                contact.setPhoneNumber(cursor.getString(POSISI_PLAT_KENDARAAN));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public ContentValues ambilData(String no_rangka, String no_mesin, String plat_kendaraan) {
        ContentValues values = new ContentValues();
        values.put(SqliteManager.FIELD_NO_RANGKA, no_rangka);
        values.put(SqliteManager.FIELD_NO_MESIN, no_mesin);
        values.put(SqliteManager.FIELD_PLAT_KENDARAAN, plat_kendaraan);
        //values.put(SqliteManager.FIELD_TIPE_SALES, tipe_sales);
        return values;
    }
}
