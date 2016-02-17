package ibel.app.applikasipertanian;

/**
 * Created by feetbo on 1/26/2016.
 */


import android.support.v7.app.ActionBar;
import android.app.ProgressDialog;

import android.os.AsyncTask;

import android.os.Bundle;




import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.cengalabs.flatui.FlatUI;
import com.cengalabs.flatui.views.FlatEditText;
import com.cengalabs.flatui.views.FlatButton;

import android.support.v7.app.AppCompatActivity;

import ibel.app.applikasipertanian.sqlite.SqliteManager;


public class Register extends AppCompatActivity {


    private ProgressDialog pDialog;
    private final int APP_THEME = R.array.grass;


    FlatEditText editnama, pekerjaan, alamat, username , password;
    FlatButton button;
    String nama, pass, kodesales1, namasales1, kodearea1, kodeaktivasi1, gps1, tipesales1;

    SqliteManager sqliteDB;


    public static final String FIELD_KODE_SALES = "kode_sales";
    public static final String FIELD_NAMA_SALES = "nama_sales";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_KODE_AREA = "kode_area";
    public static final String FIELD_KODE_AKTIVASI = "kode_aktivasi";
    public static final String FIELD_GPS = "gps";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);





        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(APP_THEME);

        Toast.makeText(Register.this, "ini register", Toast.LENGTH_LONG).show();
        editnama = (FlatEditText) findViewById(R.id.editnama);
        pekerjaan = (FlatEditText)findViewById(R.id.pekerjaan);
        alamat = (FlatEditText)findViewById(R.id.alamat);
        username = (FlatEditText)findViewById(R.id.username);
        password = (FlatEditText)findViewById(R.id.password);

        button = (FlatButton)findViewById(R.id.btnLogin);




        sqliteDB = new SqliteManager(this);
        sqliteDB.bukaKoneksi();



        //kodeaktivasi.setText(Long.toHexString(crc.getValue()));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                long nilai = sqliteDB.insertData(sqliteDB.ambilData(editnama.getText().toString(),
                        pekerjaan.getText().toString(), alamat.getText().toString(), username.getText().toString(),
                        password.getText().toString()));
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
