package distributor.app.material;


import android.content.Context;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import com.cengalabs.flatui.FlatUI;
import com.cengalabs.flatui.views.FlatCheckBox;
import com.cengalabs.flatui.views.FlatEditText;
import com.cengalabs.flatui.views.FlatButton;

import java.io.File;
import java.util.zip.CRC32;

import distributor.app.material.sqlite.SqliteManager;


public class Register extends AppCompatActivity {


    private ProgressDialog pDialog;
    private final int APP_THEME = R.array.grass;

    SharedPreferences prefLocations2;
    SharedPreferences.Editor ed2 ;
    static String sIPSetting;
    FlatCheckBox gps;
    FlatEditText ipserver, kodesales, namasales, kodearea, kodeaktivasi, tipesales, username, password;
    FlatButton button;
    String nama, pass, kodesales1, namasales1, kodearea1, kodeaktivasi1, gps1, tipesales1;
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
        setContentView(R.layout.activity_scrolling);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Shared untuk data ip
        prefLocations2 = getSharedPreferences("tbIP", Context.MODE_PRIVATE);
        sIPSetting = prefLocations2.getString("ipsetting", "");

       /* File f = new File(
                "/data/data/distributor.app.material/shared_prefs/tbUser.xml");
        if (f.exists())
        {
            Log.d("TAG", "SharedPreferences tbUser : exist");
        }
        else {
            Log.d("TAG", "Setup default preferences");
        }*/
            prefLocations = getSharedPreferences("tbUser", Context.MODE_PRIVATE);
        ed = prefLocations.edit();

        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(APP_THEME);

        Toast.makeText(Register.this, "ini register", Toast.LENGTH_LONG).show();
        username = (FlatEditText) findViewById(R.id.username);
        password = (FlatEditText)findViewById(R.id.password);
        kodesales = (FlatEditText)findViewById(R.id.kodesales);
        namasales = (FlatEditText)findViewById(R.id.namasales);
        kodearea = (FlatEditText)findViewById(R.id.kodearea);
        ipserver = (FlatEditText)findViewById(R.id.ipserver2);
        kodeaktivasi = (FlatEditText)findViewById(R.id.kodeaktivasi);
        gps = (FlatCheckBox) findViewById(R.id.gps45);
        button = (FlatButton)findViewById(R.id.btnLogin);
        //text = (TextView)findViewById(R.id.link_to_register);
        imei = (TextView)findViewById(R.id.namaIMEI);

//      mengambil getSharedPreferences
        ipserver.setText(sIPSetting);
        ed2 = prefLocations2.edit();
        username.setText(prefLocations.getString(FIELD_USERNAME, ""));
        password.setText(prefLocations.getString(FIELD_PASSWORD, ""));
        kodesales.setText(prefLocations.getString(FIELD_KODE_SALES, ""));
        namasales.setText(prefLocations.getString(FIELD_NAMA_SALES, ""));
        kodearea.setText(prefLocations.getString(FIELD_KODE_AREA, ""));
        kodeaktivasi.setText(prefLocations.getString(FIELD_KODE_AKTIVASI, ""));
        //gps.setText(prefLocations.getString(FIELD_GPS, ""));
        if (prefLocations.getString(FIELD_GPS,"").equals("1"))
        {
            gps.setChecked(true);
        }
        else{
            gps.setChecked(false);
        }
        Toast.makeText(Register.this, "ini gps: "  + prefLocations.getString(FIELD_GPS, ""), Toast.LENGTH_LONG).show();

        sqliteDB = new SqliteManager(this);
        sqliteDB.bukaKoneksi();


        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String device_id = tm.getDeviceId();
        imei.setText(device_id);
        CRC32 crc = new CRC32();
        String update = "srb" + device_id + "lupi";
        crc.update(update.getBytes());
        cRc = Long.toHexString(crc.getValue());
        //Toast.makeText(Register.this, "" + device_id + "  ini adalah crc " + Long.toHexString(crc.getValue()), Toast.LENGTH_LONG ).show();

        //kodeaktivasi.setText(Long.toHexString(crc.getValue()));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nama = username.getText().toString();
                pass = password.getText().toString();
                kodesales1 = kodesales.getText().toString();
                namasales1 = namasales.getText().toString();
                kodearea1 = kodearea.getText().toString();

                kodeaktivasi1 = kodeaktivasi.getText().toString();
                if(gps.isChecked())
                {
                    gps1 = "1";
                }
                else{
                    gps1 = "0";
                }
               // tipesales1 = tipesales.getText().toString();

                ed2.putString("ipsetting", ipserver.getText().toString().trim());
                ed2.commit();


                ed.putString("nilai", "1");
                ed.putString(FIELD_KODE_SALES, kodesales1);
                ed.putString(FIELD_NAMA_SALES, namasales1);
                ed.putString(FIELD_USERNAME, nama);
                ed.putString(FIELD_PASSWORD, pass);
                ed.putString(FIELD_KODE_AREA, kodearea1);
                ed.putString(FIELD_KODE_AKTIVASI, kodeaktivasi1);
                ed.putString(FIELD_GPS, gps1);
                ed.commit();

                long nilai = sqliteDB.insertData(sqliteDB.ambilData(kodesales1, namasales1, nama, pass,  kodearea1,
                        kodeaktivasi1, gps1));
                Toast.makeText(Register.this, "" + nilai, Toast.LENGTH_LONG ).show();
                finish();
            }
        });

        /*text.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(Register.this, TabSetting.class);
                startActivity(i);
            }
        });*/

    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        sqliteDB.tutupKoneksi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
