package distributor.app.material;

/**
 * Created by feetbo on 10/18/2015.
 */
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import  com.google.android.gms.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;


import com.cengalabs.flatui.FlatUI;
import com.cengalabs.flatui.views.FlatButton;
import com.cengalabs.flatui.views.FlatEditText;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.zip.CRC32;

import distributor.app.material.DownloadCompressParsing.GPSTracker;
import distributor.app.material.DownloadCompressParsing.JsonParsing;
import distributor.app.material.model.Contact;
import distributor.app.material.service.HapusDatabaseH_3;
import distributor.app.material.sqlite.SqliteManager;
import distributor.app.material.sqlite.SqliteManagerData;
import distributor.app.material.sqlite.SqliteManagerDetails;
import distributor.app.material.sqlite.SqliteManagerPendingKirim;
import distributor.app.material.sqlite.SqliteManagerTelahTerkirim;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks
,GoogleApiClient.OnConnectionFailedListener, LocationListener{


    private ProgressDialog pDialog;
    private final int APP_THEME = R.array.grass;
    FlatEditText username, password2;
    FlatButton button;
    String nama, pass;
    TextView text;
    GPSTracker gps;
    SqliteManager sqliteDB;
    SqliteManagerData sqLite;


    static LocationRequest mLocationRequest;
    static boolean mRequestingLocationUpdates= true;

    private Context context;
    Activity activity;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(APP_THEME);
        //Intent svc = new Intent(LoginActivity.this, HapusDatabaseH_3.class);
        //stopService(svc);
        SqliteManagerDetails dbDetails = new SqliteManagerDetails(LoginActivity.this);
        dbDetails.bukaKoneksi();
        dbDetails.hapusHeader();
        dbDetails.hapusDetail();

        dbDetails.tutupKoneksi();

        SqliteManagerPendingKirim dbPendingKirim = new SqliteManagerPendingKirim(LoginActivity.this);
        dbPendingKirim.bukaKoneksi();
        dbPendingKirim.hapusHeader();
        dbPendingKirim.hapusDetail();
        dbPendingKirim.tutupKoneksi();

        SqliteManagerTelahTerkirim dbTelahTerkirim = new SqliteManagerTelahTerkirim(LoginActivity.this);
        dbTelahTerkirim.bukaKoneksi();
        dbTelahTerkirim.hapusHeader();
        dbTelahTerkirim.hapusDetail();
        dbTelahTerkirim.tutupKoneksi();
        username = (FlatEditText) findViewById(R.id.username);
        password2 = (FlatEditText)findViewById(R.id.password);
        button = (FlatButton)findViewById(R.id.btnLogin);
       // text = (TextView)findViewById(R.id.link_to_register);



        // SETTING YANG TERBARU GOOGLE PLAY SERVICE
        context = getApplicationContext();
        activity = this;
        if(checkPermission())
        {
            //Toast.makeText(LoginActivity.this, "permission granted", Toast.LENGTH_SHORT).show();
            buildGoogleApiClient();
            createLocationRequest();
        }
        else{
            requestPermission();
        }



        JsonParsing json = new JsonParsing(LoginActivity.this);
        //json.insert();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqliteDB = new SqliteManager(LoginActivity.this);
                sqliteDB.bukaKoneksi();
                nama = username.getText().toString();
                pass = password2.getText().toString();
                Log.d("Reading: ", "Reading all contacts..");
                TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                String device_id = tm.getDeviceId();
                CRC32 crc = new CRC32();
                String update = "bismillah" + device_id + "alhamdulillah";
                crc.update(update.getBytes());
                String name = "", password = "" , CRC ="", Kode_Area = "", Kode_Sales = "";




                List<Contact> contacts = sqliteDB.getAllContacts(nama, pass, Long.toHexString(crc.getValue()));
                for (Contact cn : contacts) {
                    String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
                    // Writing Contacts to log
                    name = cn.getName();
                    password = cn.getPhoneNumber();
                    Kode_Area = cn.getKode_Area();
                    CRC = cn.getCRC();
                    Kode_Sales = cn.getKode_Sales();
                    Log.d("Name: ", log);
                }
                sqliteDB.tutupKoneksi();
                if(nama.equals(name)&& pass.equals(password) && Long.toHexString(crc.getValue()).equals(CRC))
                {
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("kodearea", Kode_Area);
                    i.putExtra("kodesales", Kode_Sales);
                    startActivity(i);
                }else if(nama.equals("admin") && pass.equals("123"))
                {
                    Intent i = new Intent(LoginActivity.this, Register.class);
                    username.setText("");
                    password2.setText("");
                    startActivity(i);
                }
            }
        });
/*
        text.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(LoginActivity.this, TabSetting.class);
                startActivity(i);
            }
        });*/
        //startService(svc);
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

    protected synchronized void buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API)
                .addConnectionCallbacks(this).addOnConnectionFailedListener(this)
                .build();
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
          //  Toast.makeText(context, "Last Location : " + String.valueOf(mLastLocation.getLatitude()), Toast.LENGTH_LONG).show();
          //  Toast.makeText(context, "Last Location : " + String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_LONG).show();
        }

        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // This callback is important for handling errors that
        // may occur while attempting to connect with Google.
        //
        // More about this in the 'Handle Connection Failures' section.
        Toast.makeText(context, "pengambilan koneksi gagal " + result.getErrorMessage(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onLocationChanged(Location location) {

        Toast.makeText(context, "update Location : " + String.valueOf(location.getLatitude()), Toast.LENGTH_LONG).show();
        Toast.makeText(context, "Update Location : " + String.valueOf(location.getLongitude()), Toast.LENGTH_LONG).show();

    }



    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(LoginActivity.this);
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
    @Override
    protected void onStart() {
        super.onStart();
       // if (!mResolvingError) {  // more about this later
            mGoogleApiClient.connect();
        //}
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();

        //Intent svc = new Intent(LoginActivity.this, HapusDatabaseH_3.class);
        //startService(svc);
    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        int resulttwo = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        if ((result == PackageManager.PERMISSION_GRANTED) && (resulttwo == PackageManager.PERMISSION_GRANTED)){

            return true;

        } else {

            return false;

        }
    }

    public void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                && ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION))
        {
            Toast.makeText(context,"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();

        }else{
            ActivityCompat.requestPermissions(activity, new String []{Manifest.permission.ACCESS_FINE_LOCATION
                    , Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(context, "Permission granted , can access location data" , Toast.LENGTH_LONG).show();
                } else {

                    Toast.makeText(context, "Permission Denied , you can't access location data", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {

        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

}
