package ibel.app.applikasipertanian.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ibel.app.applikasipertanian.model.Contact;


/**
 * Created by feetbo on 10/11/2015.
 */
public class SqliteManager {
    public static final int VERSI_DATABASE= 1;
    public static final String NAMA_DATABASE = "dbCrudSqlite";
    public static final String NAMA_TABEL = "tbUser";


    public static final int POSISI_ID = 0;
    public static final int POSISI_NAMA = 1;
    public static final int POSISI_PEKERJAAN = 2;
    public static final int POSISI_ALAMAT = 3;
    public static final int POSISI_USERNAME = 4;
    public static final int POSISI_PASSWORD = 5;



    public static final String FIELD_ID = "_id";
    public static final String FIELD_NAMA = "nama";
    public static final String FIELD_PEKERJAAN ="pekerjaan";
    public static final String FIELD_ALAMAT = "alamat";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_PASSWORD = "password";

    public static final String[] FIELD_TABEL = {SqliteManager.FIELD_ID,
            SqliteManager.FIELD_NAMA,
            SqliteManager.FIELD_PEKERJAAN,
            SqliteManager.FIELD_ALAMAT,
            SqliteManager.FIELD_USERNAME,
            SqliteManager.FIELD_PASSWORD,
          //  SqliteManager.FIELD_TIPE_SALES
    };
    private Context crudContext;
    private SQLiteDatabase crudDatabase;
    private SqliteManagerHelper crudHelper;

    private static class SqliteManagerHelper extends SQLiteOpenHelper {
        private static final String BUAT_TABEL =
                "create table " + NAMA_TABEL + " (" +
                        SqliteManager.FIELD_ID + " integer primary key autoincrement, " +
                        SqliteManager.FIELD_NAMA + " text not null ," +
                        SqliteManager.FIELD_PEKERJAAN + " text not null, " +
                        SqliteManager.FIELD_ALAMAT + " text not null, " +
                        SqliteManager.FIELD_USERNAME + " text not null, " +
                           SqliteManager.FIELD_PASSWORD + " text not null " +
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

/*    public boolean updateData(long rowId, ContentValues values) {
        return crudDatabase.update(NAMA_TABEL, values,
                SqliteManager.FIELD_ID + "=" + rowId, null) > 0;
    }

    public boolean hapusData(long rowId) {
        return crudDatabase.delete(NAMA_TABEL,
                SqliteManager.FIELD_ID + "=" + rowId, null) > 0;
    }

    public Cursor bacaData() {
        return crudDatabase.query(NAMA_TABEL,FIELD_TABEL,null, null, null, null,SqliteManager.FIELD_KODE_SALES + " DESC");

    }

    public Cursor bacaDataTerseleksi(long rowId) throws SQLException {
        Cursor cursor = crudDatabase.query(true, NAMA_TABEL,FIELD_TABEL,FIELD_ID + "=" + rowId,null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor bacaDataUsernameDanPassword(String username , String password) throws SQLException{
        Cursor cursor = crudDatabase.query(true, NAMA_TABEL, FIELD_TABEL, FIELD_USERNAME + "=" + username + " and " + FIELD_PASSWORD + "=" +password,
                null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }
*/

    // Getting All Contacts
    public List<Contact> getAllContacts(String username) {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query

//        String selectQuery = "SELECT  * FROM " + NAMA_TABEL;
        String nilai = "ok";
        String selectQuery = "SELECT  * FROM " + NAMA_TABEL + " where " + FIELD_NAMA + " = '" + username + "';";


        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(POSISI_ID)));
                contact.setKode_Sales(cursor.getString(POSISI_NAMA));
                contact.setName(cursor.getString(POSISI_PEKERJAAN));
                contact.setPhoneNumber(cursor.getString(POSISI_ALAMAT));
                contact.setKode_Area(cursor.getString(POSISI_USERNAME));
                contact.setCRC(cursor.getString(POSISI_PASSWORD));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public ContentValues ambilData(String nama, String pekerjaan, String alamat, String username, String password) {
        ContentValues values = new ContentValues();
        values.put(SqliteManager.FIELD_NAMA, nama);
        values.put(SqliteManager.FIELD_PEKERJAAN, pekerjaan);
        values.put(SqliteManager.FIELD_ALAMAT, alamat);
        values.put(SqliteManager.FIELD_USERNAME, username);
        values.put(SqliteManager.FIELD_PASSWORD, password);
        //values.put(SqliteManager.FIELD_TIPE_SALES, tipe_sales);
        return values;
    }
}
