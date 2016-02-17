package distributor.app.materialtiga;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.cengalabs.flatui.FlatUI;
import com.cengalabs.flatui.views.FlatButton;
import com.cengalabs.flatui.views.FlatEditText;

import java.util.List;
import java.util.zip.CRC32;

import distributor.app.materialtiga.databases.SqliteManager;
import distributor.app.materialtiga.model.Contact;

public class ScrollingActivity extends AppCompatActivity {


    private ProgressDialog pDialog;
    private final int APP_THEME = R.array.grass;

    FlatEditText flatnorangka, flatnomesin, flatplatkendaraan;
    FlatButton button;
    String norangka, nomesin, platkendaraan, nilai;
    TextView text, imei;
    SqliteManager sqliteDB;

    SharedPreferences prefLocations;
    SharedPreferences.Editor ed ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_two);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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



        flatnorangka = (FlatEditText)findViewById(R.id.norangka);
        flatnomesin = (FlatEditText)findViewById(R.id.nomesin);
        flatplatkendaraan = (FlatEditText) findViewById(R.id.platkendaraan);
        button = (FlatButton)findViewById(R.id.btnLogin);
        prefLocations = getSharedPreferences("tbUser", Context.MODE_PRIVATE);
        ed = prefLocations.edit();

//      mengambil getSharedPreferences
        String nilai = prefLocations.getString("nilai", "");
        sqliteDB = new SqliteManager(this);
        sqliteDB.bukaKoneksi();

        if(!nilai.equals("1")) {
            sqliteDB.insertData(sqliteDB.ambilData("MH1JBB", "JBB1E", "6509AU"));
            ed.putString("nilai", "1");
            ed.commit();
        }

        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String device_id = tm.getDeviceId();

        CRC32 crc = new CRC32();
        String update = "srb" + device_id + "lupi";
        crc.update(update.getBytes());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                norangka = flatnorangka.getText().toString().trim();
                nomesin = flatnomesin.getText().toString().trim();
                platkendaraan = flatplatkendaraan.getText().toString().trim();



                if(norangka.equals("1") && nomesin.equals("1") && platkendaraan.equals("1"))
                {
                    Intent in = new Intent(ScrollingActivity.this, Register.class);
                    startActivity(in);

                }else{
                    String dbNorangka ="", dbNoMesin ="", dbPlatKendaraan="";
                    List<Contact> contacts = sqliteDB.getAllContacts(norangka, nomesin, platkendaraan);
                    for (Contact cn : contacts) {
                        // Writing Contacts to log
                        dbNorangka = cn.getKode_Sales();
                        dbNoMesin = cn.getName();
                        dbPlatKendaraan = cn.getPhoneNumber();

                    }
                    if(norangka.equals(dbNorangka) && nomesin.equals(dbNoMesin) && platkendaraan.equals(dbPlatKendaraan))
                    Toast.makeText(ScrollingActivity.this, "Data Terregistrasi", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(ScrollingActivity.this, "Data Tidak Terregistrasi", Toast.LENGTH_LONG).show();

                }


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
            pDialog = new ProgressDialog(ScrollingActivity.this);
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
