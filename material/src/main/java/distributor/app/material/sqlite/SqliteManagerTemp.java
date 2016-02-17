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
public class SqliteManagerTemp {
    public static final int VERSI_DATABASE= 1;
    public static final String NAMA_DATABASE = "dbTemp";
    public static final String NAMA_TABEL = "tbTempPilihanDistributor";

    public static final String FIELD_ID = "_id";
    public static final int POSISI_ID = 0;
    public static final int POSISI_KODE_FAKTUR = 1;
    public static final int POSISI_FIELD_PILIHAN = 2;



    public static final String FIELD_KODE_FAKTUR = "kode_sales";
    public static final String FIELD_PILIHAN = "pilihandistributor";



    public static final String[] FIELD_TABEL = {SqliteManagerTemp.FIELD_ID,
            SqliteManagerTemp.FIELD_KODE_FAKTUR,
            SqliteManagerTemp.FIELD_PILIHAN,

            //  SqliteManager.FIELD_TIPE_SALES
    };
    private Context crudContext;
    private SQLiteDatabase crudDatabase;
    private SqliteManagerHelper crudHelper;

    private static class SqliteManagerHelper extends SQLiteOpenHelper {
        private static final String BUAT_TABEL =
                "create table " + NAMA_TABEL + " (" +
                        SqliteManagerTemp.FIELD_ID + " integer primary key autoincrement, " +
                        SqliteManagerTemp.FIELD_KODE_FAKTUR + " text not null ," +
                        SqliteManagerTemp.FIELD_PILIHAN + " text not null " +
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

    public SqliteManagerTemp(Context context) {
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

    public Cursor bacaData() {
        return crudDatabase.query(NAMA_TABEL,FIELD_TABEL,null, null, null, null,SqliteManager.FIELD_KODE_SALES + " DESC");

    }

    public Cursor bacaDataTerseleksi(long rowId) throws SQLException {
        Cursor cursor = crudDatabase.query(true, NAMA_TABEL,FIELD_TABEL,FIELD_ID + "=" + rowId,null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }




    // Getting All Contacts
    public String getAllContacts(String username) {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query

//        String selectQuery = "SELECT  * FROM " + NAMA_TABEL;
        String nilai = "ok";
        String selectQuery = "SELECT  * FROM " + NAMA_TABEL + " where " + FIELD_KODE_FAKTUR + " = '" + username + "'  ;" ;
        String pilihandistributor="";

        crudHelper = new SqliteManagerHelper(crudContext);
        crudDatabase = crudHelper.getWritableDatabase();
        Cursor cursor = crudDatabase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                pilihandistributor = cursor.getString(POSISI_FIELD_PILIHAN);

                } while (cursor.moveToNext());
        }

        // return contact list
        return pilihandistributor;
    }

    public ContentValues ambilData(String kode_sales, String nama_sales) {
        ContentValues values = new ContentValues();
        values.put(SqliteManagerTemp.FIELD_KODE_FAKTUR, kode_sales);
        values.put(SqliteManagerTemp.FIELD_PILIHAN, nama_sales);

        //values.put(SqliteManager.FIELD_TIPE_SALES, tipe_sales);
        return values;
    }
}
