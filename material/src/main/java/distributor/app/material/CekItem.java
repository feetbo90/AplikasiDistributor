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
import android.view.View;
import android.widget.EditText;
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
public class CekItem extends AppCompatActivity {
    static ArrayList<HashMap<String , String>> contactlist , pendingkirim;
    SqliteManagerDetails db;
    int serverResponseCode = 0;
    SharedPreferences prefLocations;
    SharedPreferences.Editor ed ;
    static String HeaderArray[] = new String [22];
    static String DetailArray[] = new String [18];
    static String sIPSetting, gps;
    static boolean nilaiRetrovit= false, nilaiServer=false;
    static CRC32 crc;
    //////////////////////////////////////////////////////////////////////////////////////////////

    static double nilaippn, nilaiNett;
    static String sCRC= "";// Header
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
    static String sNamaFile2 = "";
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
    TextView totalbelanja, totalDiskonX, messageText, diskonY, kodeop, tanggal, customer;
    EditText diskon1, diskon2, diskon3;
    FlatButton hitung, kirim;
    static DecimalFormat df2;
    static String roundup;
    static EditText editket, editord;
    // ProgressDialog dialog = null;

    SqliteManagerPendingKirim dbPendingKirim;
    SqliteManagerTelahTerkirim dbTelahTerkirim;
    static String sNamaFile ="<HEADEROP>";
    static int jlhreq ;
    static String faktur, custid, ppnextras;
    static String dis1 ="0", dis2="0", dis3="0", latitude, longitude;
    String fontPathNeoSans = "fonts/Neo Sans.ttf";
    String fontPathUnivers = "fonts/Univers.ttf";
    static SpotsDialog spot =null;
    static String keterangan="-" , order ="-";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cekitem);
        Bundle extras = getIntent().getExtras();

        //Apis service = ServiceGenerator.createService(Apis.class);

        Toolbar tool = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(false);

        totalbelanja = (TextView) findViewById(R.id.totalbelanja);
        totalDiskonX = (TextView) findViewById(R.id.totalDiskonX);
        kodeop = (TextView) findViewById(R.id.op);
        hitung = (FlatButton) findViewById(R.id.hitung);
        kirim = (FlatButton) findViewById(R.id.kirim);
        diskon1 = (EditText) findViewById(R.id.diskon1);
        diskon2 = (EditText) findViewById(R.id.diskon2);
        editket = (EditText) findViewById(R.id.keterangan);
        editord = (EditText) findViewById(R.id.order);
        diskon3 = (EditText) findViewById(R.id.diskon3);
        diskonY = (TextView) findViewById(R.id.diskonY);
        tanggal = (TextView) findViewById(R.id.tanggal);
        customer = (TextView) findViewById(R.id.kodecustomer);
        messageText = (TextView) findViewById(R.id.message);
        prefLocations = getSharedPreferences("tbIP", Context.MODE_PRIVATE);

        Typeface tf = Typeface.createFromAsset(getAssets(), fontPathNeoSans);
        Typeface tf2 = Typeface.createFromAsset(getAssets(), fontPathUnivers);
        totalbelanja.setTypeface(tf);
        totalDiskonX.setTypeface(tf);
        kodeop.setTypeface(tf);
        diskonY.setTypeface(tf);
        tanggal.setTypeface(tf);
        customer.setTypeface(tf);

        Date today = new Date();

        dbPendingKirim = new SqliteManagerPendingKirim(CekItem.this);
        dbTelahTerkirim = new SqliteManagerTelahTerkirim(CekItem.this);
        //formatting date in Java using SimpleDateFormat
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy ");
        String date = DATE_FORMAT.format(today);

        tanggal.setText("Tanggal :"+date);
        sIPSetting = prefLocations.getString("ipsetting", "");
        faktur = extras.getString("orderpenjualan");
        custid = extras.getString("custid");
        ppnextras = extras.getString("ppn");
        latitude = extras.getString("latitude");
        longitude = extras.getString("longitude");
        customer.setText("Customer : " +custid);

        // Pengambilan nilai gps
        prefLocations = getSharedPreferences("tbUser", Context.MODE_PRIVATE);
        gps = prefLocations.getString("gps", "");

        db = new SqliteManagerDetails(CekItem.this);
        db.bukaKoneksi();
        kodeop.setText("No faktur: "+faktur);

        List<Contact> contacts = db.getAllDataKetdanOrder(faktur);
        for (Contact cn : contacts) {
            // Writing Contacts to log
            order = cn.getName();
            keterangan = cn.getKode_Sales();
        }
        if (order.equals("-"))
            editord.setText("");
        else
            editord.setText(order);

        if (keterangan.equals("-"))
            editket.setText("");
        else
            editket.setText(keterangan);


        Toast.makeText(CekItem.this, "faktur : " + faktur, Toast.LENGTH_LONG).show();
        ListView lv = (ListView) findViewById(R.id.ceklist);

        // untuk mendapatkan nilai detail kedalam
        contactlist = db.getAllDetail(faktur);

        if ((int) db.JumlahTotalNilaiGross(faktur) > 0) {
            df2 = new DecimalFormat(".-");
            roundup = "" + df2.format((int) db.JumlahTotalNilaiGross(faktur));
            totalbelanja.setText("Total : "+ Math.round(db.JumlahTotalNilaiGross(faktur)));
            /*if (Character.getNumericValue(roundup.charAt(roundup.length() - 4)) < 5)
                totalbelanja.setText("" + roundup);
            else {
                totalbelanja.setText("" +
                        df2.format(((double) Math.ceil((double) db.JumlahTotalNilaiGross(faktur) / 100.0))
                                * 100.0));

            }*/
        } else {
            totalbelanja.setText("Total : Rp 0.-");
        }

        if (!ppnextras.equals("N")) {
            nilaippn = db.JumlahTotalNilaiGross(faktur) * 1.1;
        } else {
            nilaippn = 0;
        }

        if ((int) db.JumlahTotalDiskonX(faktur)>10)
        {
            roundup = "" + df2.format((int) db.JumlahTotalDiskonX(faktur));
            totalDiskonX.setText("Diskon x Rp"+ Math.round(db.JumlahTotalDiskonX(faktur)));
            /*if (Character.getNumericValue(roundup.charAt(roundup.length() - 4)) < 5)
                totalDiskonX.setText("" + roundup);
            else {
                totalDiskonX.setText("" + df2.format(((double) Math.ceil((double) db.JumlahTotalDiskonX(faktur) / 100.0)) * 100.0));

            }*/
        }else{
            totalDiskonX.setText("Diskon x Rp 0.-");
        }
//        totalDiskonX.setText("" + (int)db.JumlahTotalDiskonX(faktur));

        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nilaiNett = db.JumlahTotalDiskonX(faktur);
                sCRC ="";
                Log.d("nilai nett" ,""+nilaiNett );
                if(!TextUtils.isEmpty(diskon1.getText().toString()))
                {
                    nilaiNett = nilaiNett - (nilaiNett * (Double.parseDouble(diskon1.getText().toString()) / 100 ));
                    Log.d("nilai nett 1" ,""+nilaiNett  + Double.parseDouble(diskon1.getText().toString()));
                    dis1 = diskon1.getText().toString();
                }


                if(!TextUtils.isEmpty(diskon2.getText().toString()))
                {
                    nilaiNett = nilaiNett - (nilaiNett * (Double.parseDouble(diskon2.getText().toString()) /100));
                    Log.d("nilai nett 2" ,""+nilaiNett ); dis2 = diskon2.getText().toString();
                }

                if(!TextUtils.isEmpty(diskon3.getText().toString()))
                {
                    nilaiNett = nilaiNett - (nilaiNett * (Double.parseDouble(diskon3.getText().toString()) /100 ));
                    Log.d("nilai nett 3" ,""+nilaiNett );
                    dis3 = diskon3.getText().toString();
                }
                Log.d("nilai nett", "" + nilaiNett);

                // Tempat Update Sebelumnya
                if((int) nilaiNett > 10)
                {
                    //roundup = ""+df2.format((int)nilaiNett);
                    //if(Character.getNumericValue(roundup.charAt(roundup.length()-4)) < 5)
                        diskonY.setText("Total Diskon Y  Rp " + Math.round(nilaiNett));
                    //else{
                     //   diskonY.setText("Total Diskon Y  Rp " + df2.format( ((double) Math.ceil((double) nilaiNett / 100.0)) *100.0));

                }
                else{
                    diskonY.setText("Total Diskon Y Rp 0.-");
                }



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
                    HeaderArray[13] = ""+ latitude;

                    sNamaFile = sNamaFile + latitude + ",";
                    sCRC = sCRC + latitude +",";
                    String lon = map.get(FIELD_LONG);
                    Log.d("header ", lon);
                    sNamaFile = sNamaFile + longitude + ",";
                    HeaderArray[14] = ""+longitude;
                    sCRC = sCRC + longitude +",";
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
                    if(TextUtils.isEmpty(editord.getText().toString()))
                    {
                        orderno = "-";
                    }else {
                        orderno = editord.getText().toString();
                    }sNamaFile = sNamaFile + orderno + ",";
                    HeaderArray[17] = orderno;
                    sCRC = sCRC + orderno +",";
                    String ket = map.get(FIELD_KET);
                    Log.d("header ", ket);
                    if(TextUtils.isEmpty(editket.getText().toString()))
                    {
                        ket = "-";
                    }else {
                        ket = editket.getText().toString();
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
                Log.d("CRC 2: ", sCRC);
                sNamaFile = "<HEADEROP>";

            }
        });


        ListAdapter adapter;
        adapter = new SimpleAdapter(CekItem.this, contactlist, R.layout.list_item, new
                String[]{FIELD_NO_URUT, FIELD_NO_FAKTUR, FIELD_STOCK_ID} , new int []{R.id.name, R.id.email, R.id.mobile});
        lv.setAdapter(adapter);


        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // proses untuk penghitung
                sCRC = "";
                sNamaFile2 = "";
                gantiHitung();

                //   Log.d("path file : ", sNamaFile2 + Long.toHexString(crc.getValue()));
                if(gps.equals("1")){
                    Intent i = new Intent(CekItem.this, KirimLocation.class);
                    i.putExtra("orderpenjualan", faktur);
                    i.putExtra("custid", custid);
                    i.putExtra("ppn", "" + ppnextras);
                    i.putExtra("latitude", latitude);
                    i.putExtra("longitude", longitude);
                    i.putExtra("dis1", dis1);
                    i.putExtra("dis2", dis2);
                    i.putExtra("dis3", dis3);
                    startActivity(i);
                    finish();
                }else {
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
                            spot = new SpotsDialog(CekItem.this, "Uploading file...", R.style.Custom);
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
                            Toast.makeText(CekItem.this, "File size " + file.length(), Toast.LENGTH_LONG).show();
                        }
                        Log.d("path file : ", getFilesDir() + "/" + faktur + ".kir");
                        i++;
                    }
                    // dialog = ProgressDialog.show(CekItem.this, "", "Uploading file...", true);
                }
            }
        });

        db.tutupKoneksi();
    }

    public void gantiHitung()
    {
        nilaiNett = db.JumlahTotalDiskonX(faktur);
        sCRC ="";
        Log.d("nilai nett" ,""+nilaiNett );
        if(!TextUtils.isEmpty(diskon1.getText().toString()))
        {
            nilaiNett = nilaiNett - (nilaiNett * (Double.parseDouble(diskon1.getText().toString()) / 100 ));
            Log.d("nilai nett 1" ,""+nilaiNett  + Double.parseDouble(diskon1.getText().toString()));
            dis1 = diskon1.getText().toString();
        }


        if(!TextUtils.isEmpty(diskon2.getText().toString()))
        {
            nilaiNett = nilaiNett - (nilaiNett * (Double.parseDouble(diskon2.getText().toString()) /100));
            Log.d("nilai nett 2" ,""+nilaiNett ); dis2 = diskon2.getText().toString();
        }

        if(!TextUtils.isEmpty(diskon3.getText().toString()))
        {
            nilaiNett = nilaiNett - (nilaiNett * (Double.parseDouble(diskon3.getText().toString()) /100 ));
            Log.d("nilai nett 3" ,""+nilaiNett );
            dis3 = diskon3.getText().toString();
        }Log.d("nilai nett", "" + nilaiNett);

        // Tempat Update Sebelumnya
        // Tempat Update Sebelumnya
        if((int) nilaiNett > 10)
        {
            //roundup = ""+df2.format((int)nilaiNett);
            //if(Character.getNumericValue(roundup.charAt(roundup.length()-4)) < 5)
            diskonY.setText("Total Diskon Y  Rp " + Math.round(nilaiNett));
            //else{
            //   diskonY.setText("Total Diskon Y  Rp " + df2.format( ((double) Math.ceil((double) nilaiNett / 100.0)) *100.0));

        }
        else{
            diskonY.setText("Total Diskon Y Rp 0.-");
        }
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
            HeaderArray[13] = ""+ latitude;

            sNamaFile = sNamaFile + latitude + ",";
            sCRC = sCRC + latitude +",";
            String lon = map.get(FIELD_LONG);
            Log.d("header ", lon);
            sNamaFile = sNamaFile + longitude + ",";
            HeaderArray[14] = ""+longitude;
            sCRC = sCRC + longitude +",";
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
            if(TextUtils.isEmpty(editord.getText().toString()))
            {
                orderno = "-";
            }else {
                orderno = editord.getText().toString();
            }sNamaFile = sNamaFile + orderno + ",";
            HeaderArray[17] = orderno;
            sCRC = sCRC + orderno +",";
            String ket = map.get(FIELD_KET);
            Log.d("header ", ket);
            if(TextUtils.isEmpty(editket.getText().toString()))
            {
                ket = "-";
            }else {
                ket = editket.getText().toString();
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
        Log.d("CRC 2: ", sCRC);
        sNamaFile = "<HEADEROP>";

    }


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
                /*new SweetAlertDialog(CekItem.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("File Upload!")
                        .setContentText("File Upload Complete!")
                        .show();
*/
                nilaiRetrovit = true;
            }

            @Override
            public void failure(RetrofitError error) {

                Log.d("gagal ", "gagal masuk ip");
            }
        });



        /*Apis service = ServiceGenerator.createservice(Apis.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                file);
        requestBody.create(MediaType.parse("text/plain"),file);
        Call<String> call = service.upload(requestBody);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(retrofit.Response<String> response) {
                Log.v("Upload" , "Sukses " + response);
                spot.dismiss();
                new SweetAlertDialog(CekItem.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("File Upload!")
                        .setContentText("File Upload Complete!")
                        .show();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Upload", t.getMessage());
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(sIPSetting)
                .addConverterFactory(String.class, new StringConverter())
                .build();
        retrofit.create(Apis.class);


        RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        Call<String> call = retrofit.upload
*/


    }


    static class ServiceGenerator2 {



        // public static final String api_url = "http://192.168.43.35";
        // public static String sIPSetting2= "";
        private static RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint("http://" +sIPSetting.trim())
                .setClient(new OkClient(new OkHttpClient()));


        public static<S> S createservice(Class<S> serviceclass) {
            RestAdapter adapter = builder.build();
            return adapter.create(serviceclass);
        }
   /* private static OkHttpClient okhttpklien;
    private static OkHttpClient sHttpClient = new OkHttpClient();
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(api_url).addConverter(String.class, new StringConverter());



    public static<S> S createservice(Class<S> serviceclass){
        Retrofit retrofit= builder.client(okhttpklien).build();
        return retrofit.create(serviceclass);
    }
*/

/*    static {

        if (okhttpklien == null) {

            okhttpklien = new OkHttpClient();
            okhttpklien.setConnectTimeout(60, TimeUnit.SECONDS);
            okhttpklien.setWriteTimeout(60, TimeUnit.SECONDS);
            okhttpklien.setReadTimeout(60, TimeUnit.SECONDS);

        }

    }


    public static class StringConverter implements Converter<String> {

        public static String fromStream(InputStream in) throws IOException {

            BufferedSource sourceStr = Okio.buffer(Okio.source(in));

            String line1 = "";
            StringBuilder out1 = new StringBuilder();
            String newLine1 = System.getProperty("line.separator");

            while ((line1 = sourceStr.readUtf8Line()) != null) {
                out1.append(line1);
                out1.append(newLine1);
            }

            sourceStr.close();

            return out1.toString();
        }

        @Override
        public String fromBody(ResponseBody body) throws IOException {

            String text;
            try {

                text = fromStream(body.byteStream());

            } catch (Exception ex) {
                ex.printStackTrace();
                text = "";
            }


            return text;
        }

        @Override
        public RequestBody toBody(String value) {
            return null;
        }
    }
*/

    }

















    public int uploadFile(String sourceFileUri)
    {


        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile())
        {

            //  dialog.dismiss();
            spot.dismiss();
            Log.e("uploadFile", "Source File not exist :"
                    +getFilesDir() + "/"  + ".kir");

            runOnUiThread(new Runnable()
            {
                public void run()
                {
                    //  messageText.setText("Source File not exist :" +getFilesDir() + "/" + ".kir");
                }
            });

            return 0;

        }
        else
        {
            try
            {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL("http://"+ sIPSetting.trim()+"/media/flupi.php");

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0)
                {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200)
                {

                    runOnUiThread(new Runnable()
                    {
                        public void run()
                        {

                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"+" serverpath"
                                    ;

                            new SweetAlertDialog(CekItem.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("File Upload!")
                                    .setContentText("File Upload Complete!")
                                    .show();
                            // Intent i = new Intent(CekItem.this, MainActivity.class);
                            // startActivity(i);
                            //  messageText.setText(msg);
                            Toast.makeText(CekItem.this, "File Upload Complete.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            }
            catch (MalformedURLException ex)
            {

                //      dialog.dismiss();
                spot.dismiss();
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("MalformedURLException Exception : check script url.");
                        new SweetAlertDialog(CekItem.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("File Upload !")
                                .setContentText("MalformedURLException Exception : check script url!")
                                .show();
                        Toast.makeText(CekItem.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                    }
                });


                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            }
            catch (Exception e) {

                //      dialog.dismiss();
                spot.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        //messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(CekItem.this, "Got Exception : see logcat ",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            }
            //  dialog.dismiss();
            spot.dismiss();
            return serverResponseCode;

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
                        new SweetAlertDialog(CekItem.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("File Upload!")
                                .setContentText("File Upload Complete!")
                                .setConfirmText("Yes, Exit")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener(){
                                    @Override
                                public void onClick(SweetAlertDialog sDialog){
                                        sDialog.dismissWithAnimation();

                                        SqliteManagerDetails dbDetails;
                                        dbDetails = new SqliteManagerDetails(CekItem.this);
                                        dbDetails.bukaKoneksi();
                                        dbDetails.hapusHeaderFaktur(HeaderArray[0]);
                                        dbDetails.tutupKoneksi();

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
                new SweetAlertDialog(CekItem.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Something went wrong, Error response!")
                        .show();
                Log.d("Something went wrong!", "error response" );
                System.out.println("Something went wrong!");
                error.printStackTrace();
                dbPendingKirim.bukaKoneksi();

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

}
