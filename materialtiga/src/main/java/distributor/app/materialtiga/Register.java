package distributor.app.materialtiga;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.cengalabs.flatui.FlatUI;
import com.cengalabs.flatui.views.FlatEditText;
import com.cengalabs.flatui.views.FlatButton;

import java.util.zip.CRC32;

import distributor.app.materialtiga.databases.SqliteManager;


public class Register extends AppCompatActivity {


    private ProgressDialog pDialog;
    private final int APP_THEME = R.array.grass;

    FlatEditText flatnorangka, flatnomesin, flatplatkendaraan;
    FlatButton button;
    String norangka, nomesin, platkendaraan;
    TextView text, imei;
    SqliteManager sqliteDB;
    String cRc, imeitext;
    SharedPreferences prefLocations;
    SharedPreferences.Editor ed ;

    public static final String FIELD_KODE_SALES = "kode_sales";
    public static final String FIELD_NAMA_SALES = "nama_sales";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_KODE_AREA = "kode_area";
    public static final String FIELD_KODE_AKTIVASI = "kode_aktivasi";
    public static final String FIELD_GPS = "gps";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_two);



       /* File f = new File(
                "/data/data/distributor.app.material/shared_prefs/tbUser.xml");
        if (f.exists())
        {
            Log.d("TAG", "SharedPreferences tbUser : exist");
        }
        else {
            Log.d("TAG", "Setup default preferences");
        }*/

        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(APP_THEME);

        Toast.makeText(Register.this, "ini register", Toast.LENGTH_LONG).show();

        flatnorangka = (FlatEditText)findViewById(R.id.norangka);
        flatnomesin = (FlatEditText)findViewById(R.id.nomesin);
        flatplatkendaraan = (FlatEditText) findViewById(R.id.platkendaraan);
        button = (FlatButton)findViewById(R.id.btnLogin);

//      mengambil getSharedPreferences

        sqliteDB = new SqliteManager(this);
        sqliteDB.bukaKoneksi();


        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String device_id = tm.getDeviceId();

        CRC32 crc = new CRC32();
        String update = "srb" + device_id + "lupi";
        crc.update(update.getBytes());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                norangka = flatnorangka.getText().toString();
                nomesin = flatnomesin.getText().toString();
                platkendaraan = flatplatkendaraan.getText().toString();


                long nilai = sqliteDB.insertData(sqliteDB.ambilData(norangka, nomesin, platkendaraan));
                Toast.makeText(Register.this, "" + nilai, Toast.LENGTH_LONG ).show();
            }
        });



    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        sqliteDB.tutupKoneksi();
    }


    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Register.this);
            pDialog.setMessage("Sedang Proses ... ");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            /**
             * Updating parsed JSON data into ListView
             * */

        }

    }

}
