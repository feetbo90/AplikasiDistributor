package distributor.app.material;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cengalabs.flatui.views.FlatButton;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import org.w3c.dom.Text;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.CRC32;

import cn.pedant.SweetAlert.SweetAlertDialog;
import distributor.app.material.DownloadCompressParsing.GPSTracker;
import distributor.app.material.model.Contact;
import distributor.app.material.model.Header;
import distributor.app.material.sqlite.SqliteManagerDetails;
import distributor.app.material.sqlite.SqliteManagerPendingKirim;
import distributor.app.material.sqlite.SqliteManagerTelahTerkirim;
import dmax.dialog.SpotsDialog;

import retrofit.Callback;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.mime.TypedFile;

/**
 * Created by feetbo on 11/23/2015.
 */
public class Testing extends AppCompatActivity {
    ArrayList<HashMap<String , String>> contactlist , contactlistview;
    SqliteManagerDetails db;

    SharedPreferences prefLocations;

    TextView totalbelanja, totalDiskonX, messageText, diskonY, kodeop, tanggal, customer, cekitem;


Button kirim;
    // Font path
    String fontPathNeoSans = "fonts/Neo Sans.ttf";
    String fontPathUnivers = "fonts/Univers.ttf";

    static String keterangan="-" , order ="-";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kirimulang);
        Bundle extras = getIntent().getExtras();

        kirim = (Button)findViewById(R.id.kirim);
        //Apis service = ServiceGenerator.createService(Apis.class);

        Toolbar tool = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


        Date today = new Date();


        //formatting date in Java using SimpleDateFormat

        totalbelanja = (TextView) findViewById(R.id.totalbelanja);
        totalDiskonX = (TextView) findViewById(R.id.totalDiskonX);
        kodeop = (TextView) findViewById(R.id.op);
        cekitem = (TextView)findViewById(R.id.cekitem);
        diskonY = (TextView) findViewById(R.id.diskonY);
        tanggal = (TextView) findViewById(R.id.tanggal);
        customer = (TextView) findViewById(R.id.kodecustomer);


        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPathNeoSans);
        cekitem.setTypeface(tf);
        cekitem.setText("CekItem");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), fontPathUnivers);
        tanggal.setTypeface(tf2);
        customer.setTypeface(tf2);
        kodeop.setTypeface(tf2);
        totalbelanja.setTypeface(tf2);
        totalDiskonX.setTypeface(tf2);
        diskonY.setTypeface(tf2);
        tanggal.setText("Testing");
        customer.setText("Testing");
        kodeop.setText("Testing");
        kodeop.setText("Testing");
        totalbelanja.setText("Testing");
        totalDiskonX.setText("Testing");
        diskonY.setText("Testing");

    }




















}
