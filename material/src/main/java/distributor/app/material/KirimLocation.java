package distributor.app.material;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cengalabs.flatui.views.FlatButton;
import com.google.android.gms.location.LocationRequest;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.zip.CRC32;

import cn.pedant.SweetAlert.SweetAlertDialog;
import distributor.app.material.model.Contact;
import distributor.app.material.sqlite.SqliteManagerDetails;
import distributor.app.material.sqlite.SqliteManagerPendingKirim;
import distributor.app.material.sqlite.SqliteManagerTelahTerkirim;
import dmax.dialog.SpotsDialog;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.mime.TypedFile;
import rx.functions.Action1;

/**
 * Created by feetbo on 1/24/2016.
 */
public class KirimLocation extends AppCompatActivity {

    // Header
    /////////////////////////////////////////////////////////////////////////////////////////////
    public static final String FIELD_FAKTUR_PENJUALAN = "faktur_penjualan";
    public static final String FIELD_CUSTOMER_ID = "customer_id";
    public static final String FIELD_KODE_TRANS = "kode_trans";
    public static final String FIELD_TGL = "tgl";
    public static final String FIELD_TANGGAL_JAM = "tgl_jam";
    public static final String FIELD_KODE_SALES = "kode_sales";
    public static final String FIELD_REFF = "reff";
    public static final String FIELD_GRSS = "grss";
    public static final String FIELD_PPN = "ppn";
    //   public static final String FIELD_D1 = "d1";
    //   public static final String FIELD_D2 = "d2";
    //   public static final String FIELD_D3 = "d3";
    public static final String FIELD_NETT = "nett";
    public static final String FIELD_LAT = "lat";
    public static final String FIELD_LONG = "lon";
    public static final String FIELD_JLHREC = "jlhrec";
    public static final String FIELD_IMEI = "imei";
    public static final String FIELD_ORDERNO = "orderno";
    public static final String FIELD_KET = "ket";
    public static final String FIELD_GROUP = "grup";
    public static final String FIELD_TOP = "top";
    public static final String FIELD_TO_CANVAS_DS = "canvas";

    //////////////////////////////////////////////////////////////////////////////////////////////
    // Detail
    /////////////////////////////////////////////////////////////////////////////////////////////

    public static final String FIELD_NO_URUT = "no_urut";
    public static final String FIELD_NO_FAKTUR = "no_faktur";
    public static final String FIELD_STOCK_ID = "stock_id";
    public static final String FIELD_STOCK_NAME = "stock_name";
    public static final String FIELD_STOCK_OUTLET = "stock_outlet";
    public static final String FIELD_QTY = "qty";
    public static final String FIELD_HARGA = "harga";
    public static final String FIELD_JLH = "jlh";
    public static final String FIELD_D1 = "d1";
    public static final String FIELD_D2 = "d2";
    public static final String FIELD_D3 = "d3";
    public static final String FIELD_TOTAL = "total";
    public static final String FIELD_QTYB = "qtyb";
    public static final String FIELD_QTYM = "qtym";
    public static final String FIELD_QTYK = "qtyk";
    public static final String FIELD_RPD1 = "rpd1";
    public static final String FIELD_RPD2 = "rpd2";
    public static final String FIELD_RPD3 = "rpd3";





    static String HeaderArray[] = new String [22];
    static String DetailArray[] = new String [18];
    private Context context;
    private static final int RP_ACCESS_LOCATION = 1;
    static ArrayList<HashMap<String , String>> contactlist, pendingkirim ;
    static String faktur, custid, ppnextras, latitude, longitude, dis1, dis2, dis3;
    SharedPreferences prefLocations;
    SqliteManagerDetails db;
    SqliteManagerPendingKirim dbPendingKirim;
    SqliteManagerTelahTerkirim dbTelahTerkirim;
    static String order = "-", keterangan ="-", sCRC ,
    sNamaFile2, sNamaFile ="<HEADEROP>";
    static double nilaippn, nilaiNett;
    static int jlhreq ;
    static TextView latitudetext, longitudetext;
    Button kirim;
    static boolean nilaiRetrovit= false, nilaiServer=false;
    static SpotsDialog spot =null;
    static CRC32 crc;
    static String sIPSetting;
    static double getLatitude=0.0, getLongitude=0.0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kirimlocation);

        kirim = (Button) findViewById(R.id.kirim);
        latitudetext = (TextView) findViewById(R.id.latitude);
        longitudetext = (TextView) findViewById(R.id.longitude);
        
        Bundle extras = getIntent().getExtras();
        faktur = extras.getString("orderpenjualan");
        custid = extras.getString("custid");
        ppnextras = extras.getString("ppn");
        latitude = extras.getString("latitude");
        longitude = extras.getString("longitude");
        dis1 = extras.getString("dis1");
        dis2 = extras.getString("dis2");
        dis3 = extras.getString("dis3");


        //Apis service = ServiceGenerator.createService(Apis.class);
        prefLocations  = getSharedPreferences("tbIP", Context.MODE_PRIVATE);
        sIPSetting = prefLocations.getString("ipsetting", "");

        Toolbar tool = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(false);
        context = this;
        accessLocation();


        db = new SqliteManagerDetails(KirimLocation.this);
        dbPendingKirim = new SqliteManagerPendingKirim((KirimLocation.this));
        dbTelahTerkirim = new SqliteManagerTelahTerkirim(KirimLocation.this);
        db.bukaKoneksi();
        List<Contact> contacts = db.getAllDataKetdanOrder(faktur);
        for (Contact cn : contacts) {
            // Writing Contacts to log
            order = cn.getName();
            keterangan = cn.getKode_Sales();
        }
        if (order.equals("-"))
            order = "";
        if (keterangan.equals("-"))
            keterangan ="";
        contactlist = db.getAllDetail(faktur);
        if (!ppnextras.equals("N")) {
            nilaippn = db.JumlahTotalNilaiGross(faktur) * 1.1;
        } else {
            nilaippn = 0;
        }

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // proses untuk penghitung
             /*   SweetAlertDialog pDialog = new SweetAlertDialog(KirimLocation.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Mengambil Lokasi");
                pDialog.setCancelable(false);
                pDialog.show();*/
                if ((getLatitude != 0) && (getLongitude != 0)) {
                    //pDialog.dismiss();
                    sCRC = "";
                    sNamaFile2 = "";
                    gantiHitung();
                    int i = 0;
                    boolean hasil = false;
                    while (i < 4 || hasil == false) {


                        try {

                            FileOutputStream fileout = openFileOutput(faktur + ".kir", MODE_PRIVATE);
                            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                            outputWriter.write(sNamaFile2);
                            outputWriter.flush();
                            outputWriter.close();

                            //display file saved message
                            Toast.makeText(getBaseContext(), "File saved successfully!",
                                    Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        File file = new File(getFilesDir() + "/" + faktur + ".kir");
                        Log.d("File size", "" + file.length());
                        if (file.length() > 10) {
                            spot = new SpotsDialog(KirimLocation.this, "Uploading file...", R.style.Custom);
                            spot.show();
                            crc = new CRC32();
                            String update = "bismillah" + sCRC + "alhamdulillah";

                            crc.update(update.getBytes());
                            new Thread(new Runnable() {
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            //    messageText.setText("uploading started.....");
                                        }
                                    });
                                    //ganti sementara
                                    // uploadFile(getFilesDir() + "/" + faktur + ".kir");
                                    uploadFileWithRetrofit(getFilesDir() + "/" + faktur + ".kir");
                                    // kirim nilai crc nya
                                    sendToServer("" + Long.toHexString(crc.getValue()));


                                }
                            }).start();
                            hasil = true;
                            sCRC = "";
                            break;

                        } else if (i == 3) {
                            sCRC = "";
                        } else {
                            Toast.makeText(KirimLocation.this, "File size " + file.length(), Toast.LENGTH_LONG).show();
                        }
                        Log.d("path file : ", getFilesDir() + "/" + faktur + ".kir");
                        i++;
                    }
                }else{
                    Toast.makeText(KirimLocation.this, "sabar sedang mengambil location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // upload file with retrofit
    private void uploadFileWithRetrofit(String sourceFileUri) {

        String filename = sourceFileUri;
        File file = new File(filename);

        ServiceGenerator2 serviceGen = new ServiceGenerator2();
        //serviceGen.setURL(sIPSetting);
        Apis service = serviceGen.createservice(Apis.class);
        TypedFile typedFile = new TypedFile("multipart/form-data", new File(sourceFileUri));
        service.upload(typedFile, new Callback<String>() {
            @Override
            public void success(String s, retrofit.client.Response response) {
                //spot.dismiss();
                nilaiRetrovit =true;

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("gagal ", "gagal masuk ip");
            }
        });
    }

    static class ServiceGenerator2 {


        // public static final String api_url = "http://192.168.43.35";
        // public static String sIPSetting2= "";
        private static RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint("http://" + sIPSetting.trim())
                .setClient(new OkClient(new OkHttpClient()));


        public static <S> S createservice(Class<S> serviceclass) {
            RestAdapter adapter = builder.build();
            return adapter.create(serviceclass);
        }
    }

    public void sendToServer(String url)
    {
        String url2 = "http://" + sIPSetting.trim() + "/media/salin.php?name=" + faktur +".kir&kode=" + url;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        nilaiServer = true;

                        // Result handling
                        Log.d("Something Success!", response);
                        if(nilaiServer == true && nilaiRetrovit == true){
                            spot.dismiss();
                            new SweetAlertDialog(KirimLocation.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("File Upload!")
                                    .setContentText("File Upload Complete!")
                                    .setConfirmText("Yes, Exit")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener(){
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog){
                                            sDialog.dismissWithAnimation();
                                            SqliteManagerPendingKirim dbPendingKirim;
                                            SqliteManagerDetails dbDetails;
                                            dbDetails = new SqliteManagerDetails(KirimLocation.this);
                                            dbDetails.bukaKoneksi();
                                            dbDetails.hapusHeaderFaktur(HeaderArray[0]);
                                            dbDetails.tutupKoneksi();

                                            dbPendingKirim = new SqliteManagerPendingKirim(KirimLocation.this);
                                            dbPendingKirim.bukaKoneksi();
                                            dbPendingKirim.hapusHeaderFaktur(HeaderArray[0]);
                                            dbPendingKirim.tutupKoneksi();
                                            finish();
                                        }
                                    })
                                    .show();
                            System.out.println(response);
                            dbTelahTerkirim.bukaKoneksi();
                            long nilai = dbTelahTerkirim.insertData(dbTelahTerkirim.InsertDataHeader(HeaderArray[0], HeaderArray[1],
                                    HeaderArray[2], HeaderArray[3], HeaderArray[4], HeaderArray[5], HeaderArray[6], HeaderArray[7], HeaderArray[8],
                                    HeaderArray[9], HeaderArray[10], HeaderArray[11], HeaderArray[12], HeaderArray[13], HeaderArray[14], HeaderArray[15],
                                    HeaderArray[16], HeaderArray[17], HeaderArray[18], HeaderArray[19],
                                    HeaderArray[20], HeaderArray[21]));
                            pendingkirim = db.getAllDetail(faktur);

                            for (HashMap<String, String> map : pendingkirim) {

                                Log.d("masuk", "yoi");
                                String no_urut = map.get(FIELD_NO_URUT);
                                DetailArray[0]= no_urut;
                                String no_faktur = map.get(FIELD_NO_FAKTUR);
                                DetailArray[1]= no_faktur;
                                String stock_id = map.get(FIELD_STOCK_ID);
                                DetailArray[2]= stock_id;
                                String stock_name = map.get(FIELD_STOCK_NAME);
                                DetailArray[3]= stock_name;
                                String stock_outlet = map.get(FIELD_STOCK_OUTLET);
                                DetailArray[4]= no_urut;
                                String qty = map.get(FIELD_QTY);
                                DetailArray[5]= qty;
                                String harga = map.get(FIELD_HARGA);
                                DetailArray[6]= harga;
                                String jlh = map.get(FIELD_JLH);
                                DetailArray[7]= jlh;
                                String d1 = map.get(FIELD_D1);
                                DetailArray[8]= d1;
                                String d2 = map.get(FIELD_D2);
                                DetailArray[9]= d2;
                                String d3 = map.get(FIELD_D3);
                                DetailArray[10]= d3;
                                String total = map.get(FIELD_TOTAL);
                                DetailArray[11]= total;
                                String qtyb = map.get(FIELD_QTYB);
                                DetailArray[12]= qtyb;
                                String qtym = map.get(FIELD_QTYM);
                                DetailArray[13]= qtym;
                                String qtyk = map.get(FIELD_QTYK);
                                DetailArray[14]= qtyk;
                                String rpd1 = map.get(FIELD_RPD1);
                                DetailArray[15]= rpd1;
                                String rpd2 = map.get(FIELD_RPD2);
                                DetailArray[16]= rpd2;
                                String rpd3 = map.get(FIELD_RPD3);
                                DetailArray[17]= rpd3;
                                Log.d("detail ", no_urut);
                                Log.d("detail ", no_faktur);
                                Log.d("detail ", stock_id);
                                Log.d("detail ", stock_name);
                                Log.d("detail ", stock_outlet);
                                Log.d("detail qty", qty);
                                Log.d("detail harga", harga);
                                Log.d("detail jumlah", jlh);
                                Log.d("detail d1", d1);
                                Log.d("detail d2", d2);
                                Log.d("detail d3", d3);
                                Log.d("detail d4", total);
                                Log.d("detail qtyb", qtyb);
                                Log.d("detail qtym", qtym);
                                Log.d("detail qtyk", qtyk);
                                Log.d("detail rpd1", rpd1);
                                Log.d("detail ", rpd2);
                                Log.d("detail ", rpd3);


                                long nilai2 = dbTelahTerkirim.insertDataDetail(dbTelahTerkirim.InsertDataDetail(
                                        DetailArray[0], DetailArray[1], DetailArray[2], DetailArray[3], DetailArray[4],
                                        DetailArray[5], DetailArray[6], DetailArray[7], DetailArray[8], DetailArray[9], DetailArray[10],
                                        DetailArray[11], DetailArray[12], DetailArray[13], DetailArray[14],
                                        DetailArray[15], DetailArray[16], DetailArray[17]
                                ));}
                            pendingkirim.clear();

                            nilaiRetrovit = false; nilaiServer = false;
                            dbTelahTerkirim.tutupKoneksi();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                spot.dismiss();
                // Error handling
                new SweetAlertDialog(KirimLocation.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Something went wrong, Error response!")
                        .show();
                Log.d("Something went wrong!", "error response" );
                System.out.println("Something went wrong!");
                error.printStackTrace();
                dbPendingKirim.bukaKoneksi();


                // Penambahan supaya tidak duplikat
                String name = "";
                List<Contact> contacts = dbPendingKirim.getDuplikat(faktur, HeaderArray[2]);
                for (Contact cn : contacts) {
                    // Writing Contacts to log
                    name = cn.getName();

                }
                Log.d("nama :  " , name);
                if (name.equals("")) {

                long nilai = dbPendingKirim.insertData(dbPendingKirim.InsertDataHeader(HeaderArray[0], HeaderArray[1],
                        HeaderArray[2], HeaderArray[3], HeaderArray[4], HeaderArray[5], HeaderArray[6], HeaderArray[7], HeaderArray[8],
                        HeaderArray[9], HeaderArray[10], HeaderArray[11], HeaderArray[12], HeaderArray[13], HeaderArray[14], HeaderArray[15],
                        HeaderArray[16], HeaderArray[17], HeaderArray[18], HeaderArray[19],
                        HeaderArray[20], HeaderArray[21]));
                    pendingkirim = db.getAllDetail(faktur);

                    for (HashMap<String, String> map : pendingkirim) {

                        Log.d("masuk", "yoi");
                        String no_urut = map.get(FIELD_NO_URUT);
                        DetailArray[0]= no_urut;
                        String no_faktur = map.get(FIELD_NO_FAKTUR);
                        DetailArray[1]= no_faktur;
                        String stock_id = map.get(FIELD_STOCK_ID);
                        DetailArray[2]= stock_id;
                        String stock_name = map.get(FIELD_STOCK_NAME);
                        DetailArray[3]= stock_name;
                        String stock_outlet = map.get(FIELD_STOCK_OUTLET);
                        DetailArray[4]= no_urut;
                        String qty = map.get(FIELD_QTY);
                        DetailArray[5]= qty;
                        String harga = map.get(FIELD_HARGA);
                        DetailArray[6]= harga;
                        String jlh = map.get(FIELD_JLH);
                        DetailArray[7]= jlh;
                        String d1 = map.get(FIELD_D1);
                        DetailArray[8]= d1;
                        String d2 = map.get(FIELD_D2);
                        DetailArray[9]= d2;
                        String d3 = map.get(FIELD_D3);
                        DetailArray[10]= d3;
                        String total = map.get(FIELD_TOTAL);
                        DetailArray[11]= total;
                        String qtyb = map.get(FIELD_QTYB);
                        DetailArray[12]= qtyb;
                        String qtym = map.get(FIELD_QTYM);
                        DetailArray[13]= qtym;
                        String qtyk = map.get(FIELD_QTYK);
                        DetailArray[14]= qtyk;
                        String rpd1 = map.get(FIELD_RPD1);
                        DetailArray[15]= rpd1;
                        String rpd2 = map.get(FIELD_RPD2);
                        DetailArray[16]= rpd2;
                        String rpd3 = map.get(FIELD_RPD3);
                        DetailArray[17]= rpd3;
                        Log.d("detail ", no_urut);
                        Log.d("detail ", no_faktur);
                        Log.d("detail ", stock_id);
                        Log.d("detail ", stock_name);
                        Log.d("detail ", stock_outlet);
                        Log.d("detail qty", qty);
                        Log.d("detail harga", harga);
                        Log.d("detail jumlah", jlh);
                        Log.d("detail d1", d1);
                        Log.d("detail d2", d2);
                        Log.d("detail d3", d3);
                        Log.d("detail d4", total);
                        Log.d("detail qtyb", qtyb);
                        Log.d("detail qtym", qtym);
                        Log.d("detail qtyk", qtyk);
                        Log.d("detail rpd1", rpd1);
                        Log.d("detail ", rpd2);
                        Log.d("detail ", rpd3);


                        dbPendingKirim.insertDataDetail(dbPendingKirim.InsertDataDetail(
                                DetailArray[0], DetailArray[1], DetailArray[2], DetailArray[3], DetailArray[4],
                                DetailArray[5], DetailArray[6], DetailArray[7], DetailArray[8], DetailArray[9], DetailArray[10],
                                DetailArray[11], DetailArray[12], DetailArray[13], DetailArray[14],
                                DetailArray[15], DetailArray[16], DetailArray[17]
                        ));}
                    pendingkirim.clear();}
                dbPendingKirim.tutupKoneksi();

            }
        });

// Add the request to the queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
    /**
     * get access location
     */
    private void accessLocation() {
        // cek apakah sudah memiliki permission untuk access fine location
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // cek apakah perlu menampilkan info kenapa membutuhkan access fine location
            if (ActivityCompat.shouldShowRequestPermissionRationale(KirimLocation.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(KirimLocation.this, "Access Location dibutuhkan",
                        Toast.LENGTH_SHORT).show();

                String[] perm = { Manifest.permission.ACCESS_FINE_LOCATION };
                ActivityCompat.requestPermissions(KirimLocation.this, perm,
                        RP_ACCESS_LOCATION);
            } else {
                // request permission untuk access fine location
                String[] perm = { Manifest.permission.ACCESS_FINE_LOCATION };
                ActivityCompat.requestPermissions(KirimLocation.this, perm,
                        RP_ACCESS_LOCATION);
            }
        } else {
            // permission access fine location didapat
            Toast.makeText(KirimLocation.this, "Yay, has location permission",
                    Toast.LENGTH_SHORT).show();
            doSomething();
        }
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RP_ACCESS_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do location thing
                    // access location didapatkan
                    Toast.makeText(KirimLocation.this, "Yay, has location permission",
                            Toast.LENGTH_SHORT).show();

                    doSomething();
                } else {
                    // access location ditolak user
                    Toast.makeText(KirimLocation.this, "permission ditolak user",
                            Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    private void doSomething() {
        LocationRequest request = LocationRequest.create() //standard GMS LocationRequest
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setNumUpdates(5).setInterval(100);

        ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(this);

        locationProvider.getUpdatedLocation(request).subscribe(new Action1<Location>() {
            @Override
            public void call(Location location) {
                Log.d("tag", "location : " + location.getLatitude());
                getLongitude = location.getLongitude();
                getLatitude = location.getLatitude();
                latitudetext.setText("" + getLatitude);
                longitudetext.setText(""+ getLongitude);
                Toast.makeText(KirimLocation.this, " ini latitude : " + location.getLatitude() +
                                " ini longitude : " + location.getLongitude(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void gantiHitung()
    {
        nilaiNett = db.JumlahTotalDiskonX(faktur);
        sCRC ="";
        Log.d("nilai nett" ,""+nilaiNett );
        if(!TextUtils.isEmpty(dis1))
        {
            nilaiNett = nilaiNett - (nilaiNett * (Double.parseDouble(dis1) / 100 ));
            Log.d("nilai nett 1" ,""+nilaiNett  + Double.parseDouble(dis1));
        }


        if(!TextUtils.isEmpty(dis2))
        {
            nilaiNett = nilaiNett - (nilaiNett * (Double.parseDouble(dis2) /100));
            Log.d("nilai nett 2" ,""+nilaiNett );
        }

        if(!TextUtils.isEmpty(dis3))
        {
            nilaiNett = nilaiNett - (nilaiNett * (Double.parseDouble(dis3) /100 ));
            Log.d("nilai nett 3" ,""+nilaiNett );
        }
        Log.d("nilai nett", "" + nilaiNett);

        ArrayList<HashMap<String, String>> header = db.getAllHeader(faktur);
        Date today = new Date();



        //formatting date in Java using SimpleDateFormat
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date = DATE_FORMAT.format(today);

        jlhreq = 0;
        for (HashMap<String, String> map : contactlist) {
            jlhreq++;
        }


        for (HashMap<String, String> map : header) {
            String faktur_penjualan = map.get(FIELD_FAKTUR_PENJUALAN);
            Log.d("header ", faktur_penjualan);
            sNamaFile = sNamaFile + faktur_penjualan + ",";
            sCRC = sCRC + faktur_penjualan +",";
            HeaderArray[0] = faktur_penjualan;
            String customer_id = map.get(FIELD_CUSTOMER_ID);
            Log.d("header ", customer_id);
            sNamaFile = sNamaFile + customer_id + ",";
            sCRC = sCRC  + customer_id + ",";
            HeaderArray[1] = customer_id;
            String kode_trans = map.get(FIELD_KODE_TRANS);
            Log.d("header ", kode_trans);
            HeaderArray[2] = kode_trans;
            sNamaFile = sNamaFile + kode_trans + ",";
            sCRC = sCRC + kode_trans +",";
            String tanggal = map.get(FIELD_TGL);
            Log.d("header ", tanggal);
            HeaderArray[3] = tanggal;
            sNamaFile = sNamaFile + tanggal + ",";
            sCRC = sCRC + tanggal +",";
            String tanggal_jam = map.get(FIELD_TANGGAL_JAM);
            Log.d("header ", date);
            HeaderArray[4] = date;
            sNamaFile = sNamaFile + date + ",";
            sCRC = sCRC + date +",";
            String kode_sales = map.get(FIELD_KODE_SALES);
            Log.d("header ", kode_sales);
            HeaderArray[5] = kode_sales;
            sNamaFile = sNamaFile + kode_sales + ",";
            sCRC = sCRC + kode_sales +",";
            String reff = map.get(FIELD_REFF);
            Log.d("header ", reff);
            HeaderArray[6] = reff;
            sNamaFile = sNamaFile + reff + ",";
            sCRC = sCRC + reff +",";
//String grss = map.get(FIELD_GRSS);
            Log.d("header ",""+ db.JumlahTotalNilaiGross(faktur));
            sNamaFile = sNamaFile + db.JumlahTotalNilaiGross(faktur) + ",";
            sCRC = sCRC +db.JumlahTotalNilaiGross(faktur) +",";
            HeaderArray[7] = ""+db.JumlahTotalNilaiGross(faktur);
            String ppn = map.get(FIELD_PPN);
            Log.d("header ", "" + nilaippn);
            HeaderArray[8] = ""+ nilaippn;
            sNamaFile = sNamaFile + nilaippn + ",";
            sCRC = sCRC + nilaippn +",";
            //String d1 = map.get(FIELD_D1);
            Log.d("header ", dis1);
            HeaderArray[9] = dis1;
            sNamaFile = sNamaFile + dis1 + ",";
            sCRC = sCRC + dis1 +",";
//String d2 = map.get(FIELD_D2);
            Log.d("header ", dis2);
            sNamaFile = sNamaFile + dis2 + ",";
            sCRC = sCRC + dis2 +",";
            HeaderArray[10] = dis2;
//String d3 = map.get(FIELD_D3);
            Log.d("header ", dis3);
            sNamaFile = sNamaFile + dis3 + ",";
            sCRC = sCRC + dis3 +",";
            HeaderArray[11] = dis3;
            //String nett = map.get(FIELD_NETT);
            Log.d("header ", ""+nilaiNett);
            sNamaFile = sNamaFile + nilaiNett + ",";
            sCRC = sCRC + nilaiNett +",";
            HeaderArray[12] =  "" +nilaiNett;
            String lat = map.get(FIELD_LAT);
            Log.d("header ", lat);
            HeaderArray[13] = ""+getLatitude;

            sNamaFile = sNamaFile + getLatitude + ",";
            sCRC = sCRC + getLatitude +",";
            String lon = map.get(FIELD_LONG);
            Log.d("header ", lon);
            sNamaFile = sNamaFile + getLongitude + ",";
            HeaderArray[14] = ""+getLongitude;
            sCRC = sCRC + getLongitude +",";
//String jlhrec = map.get(FIELD_JLHREC);
            Log.d("header ", ""+jlhreq);
            HeaderArray[15] = ""+jlhreq;
            sNamaFile = sNamaFile + jlhreq + ",";
            sCRC = sCRC + jlhreq +",";
            String imei = map.get(FIELD_IMEI);
            Log.d("header ", imei);
            HeaderArray[16] = imei;
            sNamaFile = sNamaFile + imei + ",";
            sCRC = sCRC + imei +",";
            String orderno = map.get(FIELD_ORDERNO);
            Log.d("header ", orderno);
            if(order.equals("-"))
            {
                orderno = "-";
            }else {
                orderno = order;
            }sNamaFile = sNamaFile + orderno + ",";
            HeaderArray[17] = orderno;
            sCRC = sCRC + orderno +",";
            String ket = map.get(FIELD_KET);
            Log.d("header ", ket);
            if(keterangan.equals("-"))
            {
                ket = "-";
            }else {
                ket = keterangan;
            }
            HeaderArray[18] = ket;
            sNamaFile = sNamaFile + ket + ",";
            sCRC = sCRC + ket +",";
            String group = map.get(FIELD_GROUP);
            Log.d("header ", group);
            HeaderArray[19] = group;
            sNamaFile = sNamaFile + group + ",";
            sCRC = sCRC + group +",";
            String top = map.get(FIELD_TOP);
            Log.d("header ", top);
            HeaderArray[20] = top;
            sNamaFile = sNamaFile + top + ",";
            sCRC = sCRC + top +",";
            String canvas = map.get(FIELD_TO_CANVAS_DS);
            Log.d("header ", canvas);
            HeaderArray[21] = canvas;
            sNamaFile = sNamaFile + canvas + ",<END>\n";
            sCRC = sCRC + canvas +",";


        }

        for (HashMap<String, String> map : contactlist) {


            String no_urut = map.get(FIELD_NO_URUT);
            sNamaFile = sNamaFile + "<DETAILOP>" + no_urut + ",";
            sCRC = sCRC + no_urut +",";
            DetailArray[0]= no_urut;
            String no_faktur = map.get(FIELD_NO_FAKTUR);
            DetailArray[1]= no_faktur;
            sNamaFile = sNamaFile + no_faktur + ",";
            sCRC = sCRC + no_faktur +",";
            String stock_id = map.get(FIELD_STOCK_ID);
            DetailArray[2]= stock_id;
            sNamaFile = sNamaFile + stock_id + ",";
            sCRC = sCRC + stock_id +",";
            String stock_name = map.get(FIELD_STOCK_NAME);
            sNamaFile = sNamaFile + stock_name + ",";
            DetailArray[3]= stock_name;
            sCRC = sCRC + stock_name +",";
            String stock_outlet = map.get(FIELD_STOCK_OUTLET);
            sNamaFile = sNamaFile + stock_outlet + ",";
            sCRC = sCRC + stock_outlet +",";
            DetailArray[4]= no_urut;
            String qty = map.get(FIELD_QTY);
            sNamaFile = sNamaFile + qty + ",";
            DetailArray[5]= qty;
            sCRC = sCRC + qty +",";
            String harga = map.get(FIELD_HARGA);
            DetailArray[6]= harga;
            sNamaFile = sNamaFile + harga + ",";
            sCRC = sCRC + harga +",";
            String jlh = map.get(FIELD_JLH);
            DetailArray[7]= jlh;
            sNamaFile = sNamaFile + jlh + ",";
            sCRC = sCRC + jlh +",";
            String d1 = map.get(FIELD_D1);
            sNamaFile = sNamaFile + d1 + ",";
            sCRC = sCRC + d1 +",";
            DetailArray[8]= d1;
            String d2 = map.get(FIELD_D2);
            DetailArray[9]= d2;
            sNamaFile = sNamaFile + d2 + ",";
            sCRC = sCRC + d2 +",";
            String d3 = map.get(FIELD_D3);
            DetailArray[10]= d3;
            sNamaFile = sNamaFile + d3 + ",";
            sCRC = sCRC + d3 +",";
            String total = map.get(FIELD_TOTAL);
            DetailArray[11]= total;
            sNamaFile = sNamaFile + total + ",";
            sCRC = sCRC + total +",";
            String qtyb = map.get(FIELD_QTYB);
            DetailArray[12]= qtyb;
            sNamaFile = sNamaFile + qtyb + ",";
            sCRC = sCRC + qtyb +",";
            String qtym = map.get(FIELD_QTYM);
            DetailArray[13]= qtym;
            sNamaFile = sNamaFile + qtym + ",";
            sCRC = sCRC + qtym +",";
            String qtyk = map.get(FIELD_QTYK);
            DetailArray[14]= qtyk;
            sNamaFile = sNamaFile + qtyk + ",";
            sCRC = sCRC + qtyk +",";
            String rpd1 = map.get(FIELD_RPD1);
            sNamaFile = sNamaFile + rpd1 + ",";
            DetailArray[15]= rpd1;
            sCRC = sCRC + rpd1 +",";
            String rpd2 = map.get(FIELD_RPD2);
            sNamaFile = sNamaFile + rpd2 + ",";
            DetailArray[16]= rpd2;
            sCRC = sCRC + rpd2 +",";
            String rpd3 = map.get(FIELD_RPD3);
            sNamaFile = sNamaFile + rpd3 + ",<END>\n";
            DetailArray[17]= rpd3;
            sCRC = sCRC + rpd3 +",";
            Log.d("detail ", no_urut);
            Log.d("detail ", no_faktur);
            Log.d("detail ", stock_id);
            Log.d("detail ", stock_name);
            Log.d("detail ", stock_outlet);
            Log.d("detail qty", qty);
            Log.d("detail harga", harga);
            Log.d("detail jumlah", jlh);
            Log.d("detail d1", d1);
            Log.d("detail d2", d2);
            Log.d("detail d3", d3);
            Log.d("detail d4", total);
            Log.d("detail qtyb", qtyb);
            Log.d("detail qtym", qtym);
            Log.d("detail qtyk", qtyk);
            Log.d("detail rpd1", rpd1);
            Log.d("detail ", rpd2);
            Log.d("detail ", rpd3);
        }

        // Update Yang Terbaru
        db.setUpdateHeader(""+ db.JumlahTotalNilaiGross(faktur), ""+nilaippn, ""+nilaiNett , faktur,
                date, ""+dis1, ""+dis2, ""+ dis3,""+ jlhreq);

        sNamaFile = sNamaFile +  "<FOOTEROP>" + faktur + "<END>";
        sNamaFile2  = sNamaFile;
        sCRC = sCRC + faktur ;
        Log.d("Nama File 2", sNamaFile2);
        Log.d("CRC 2 ", sCRC);
        sNamaFile = "<HEADEROP>";

    }

}
