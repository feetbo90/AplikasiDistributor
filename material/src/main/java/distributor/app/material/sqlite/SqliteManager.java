package distributor.app.material.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import distributor.app.material.model.Contact;

/**
 * Created by feetbo on 10/11/2015.
 */
public class SqliteManager {
    public static final int VERSI_DATABASE= 1;
    public static final String NAMA_DATABASE = "dbCrudSqlite";
    public static final String NAMA_TABEL = "tbUser";

    public static final String FIELD_ID = "_id";
/*    public static final int POSISI_ID = 0;
    public static final int POSISI_KODE_SALES = 1;
    public static final int POSISI_NAMA_SALES = 2;
    public static final int POSISI_USERNAME = 3;
    public static final int POSISI_PASSWORD = 4;
    public static final int POSISI_KODE_AREA = 5;
    public static final int POSISI_KODE_AKTIVASI = 6;
    public static final int POSISI_GPS = 7;
    public static final int POSISI_TIPE_SALES = 8;

*/


    public static final String FIELD_KODE_SALES = "kode_sales";
    public static final String FIELD_NAMA_SALES = "nama_sales";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_KODE_AREA = "kode_area";
    public static final String FIELD_KODE_AKTIVASI = "kode_aktivasi";
    public static final String FIELD_GPS = "gps";
  //  public static final String FIELD_TIPE_SALES = "tipe_sales";


/*    public static final String FIELD_JUDUL = "judul";
    public static final int POSISI_JUDUL = 1;
    public static final String FIELD_DESKRIPSI = "deskripsi";
    public static final int POSISI_DESKRIPSI = 2;
    public static final String FIELD_WAKTU = "waktu";
    public static final int POSISI_WAKTU = 3;
*/
    public static final String[] FIELD_TABEL = {SqliteManager.FIELD_ID,
            SqliteManager.FIELD_KODE_SALES,
            SqliteManager.FIELD_NAMA_SALES,
            SqliteManager.FIELD_USERNAME,
            SqliteManager.FIELD_PASSWORD,
            SqliteManager.FIELD_KODE_AREA,
            SqliteManager.FIELD_KODE_AKTIVASI,
            SqliteManager.FIELD_GPS,
          //  SqliteManager.FIELD_TIPE_SALES
    };
    private Context crudContext;
    private SQLiteDatabase crudDatabase;
    private SqliteManagerHelper crudHelper;

    private static class SqliteManagerHelper extends SQLiteOpenHelper {
        private static final String BUAT_TABEL =
                "create table " + NAMA_TABEL + " (" +
                        SqliteManager.FIELD_ID + " integer primary key autoincrement, " +
                        SqliteManager.FIELD_KODE_SALES + " text not null ," +
                        SqliteManager.FIELD_NAMA_SALES + " text not null, " +
                        SqliteManager.FIELD_USERNAME + " text not null, " +
                        SqliteManager.FIELD_PASSWORD + " text not null, " +
                        SqliteManager.FIELD_KODE_AREA + " text not null, " +
                        SqliteManager.FIELD_KODE_AKTIVASI + " text not null," +
                        SqliteManager.FIELD_GPS + " text not null " +
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
    public List<Contact> getAllContacts(String username, String password, String crc) {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query

//        String selectQuery = "SELECT  * FROM " + NAMA_TABEL;
        String nilai = "ok";
        String selectQuery = "SELECT  * FROM " + NAMA_TABEL + " where " + FIELD_USERNAME + " = '" + username + "'  and " + FIELD_PASSWORD +  " = '" +password + "' " +
                "and " + FIELD_KODE_AKTIVASI + " = '" + crc + "' ;" ;


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

    public ContentValues ambilData(String kode_sales, String nama_sales, String username, String password, String kode_area,
                                   String kode_aktivasi, String gps) {
        ContentValues values = new ContentValues();
        values.put(SqliteManager.FIELD_KODE_SALES, kode_sales);
        values.put(SqliteManager.FIELD_NAMA_SALES, nama_sales);
        values.put(SqliteManager.FIELD_USERNAME, username);
        values.put(SqliteManager.FIELD_PASSWORD, password);
        values.put(SqliteManager.FIELD_KODE_AREA, kode_area);
        values.put(SqliteManager.FIELD_KODE_AKTIVASI, kode_aktivasi);
        values.put(SqliteManager.FIELD_GPS, gps);
        //values.put(SqliteManager.FIELD_TIPE_SALES, tipe_sales);
        return values;
    }
}
