package distributor.app.material;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cengalabs.flatui.FlatUI;
import com.cengalabs.flatui.views.FlatButton;
import com.cengalabs.flatui.views.FlatEditText;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import distributor.app.material.model.Contact;
import distributor.app.material.model.Customer;
import distributor.app.material.sqlite.SqliteManagerData;
import distributor.app.material.sqlite.SqliteManagerDetails;
import distributor.app.material.sqlite.SqliteManagerTemp;

/**
 * Created by feetbo on 11/18/2015.
 */
public class Details extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks
        ,GoogleApiClient.OnConnectionFailedListener, LocationListener {

    TextView kodeOP, NamaProduk, textBesar, textSedang, textKecil, discountpersen, textTotal ;
    static EditText KodeProduk, stock, discountsatu, discountdua, discounttiga,
    editdisRpsatu, editdisRpdua, editdisRptiga;
    static  Spinner Harga;
    FlatEditText  editBesar, editSedang, editKecil;
    static int cekhitung=0;
    FlatButton cekitem, simpan, hitung;
    static String orderpenjualan, sSpinnnerHarga, sNamaProduk, discount1, discount2, discount3;
    ListView list;
    SqliteManagerData db;
    SqliteManagerDetails dbDetails;

    SqliteManagerTemp dbTemp;
    ArrayList<HashMap<String,String>> ProdukList;
    static ArrayList<String> harga = new ArrayList<String>();
    static ArrayList<String> detail;
    static ArrayList<String> harganondesimal = new ArrayList<String>();


    static String discountpersen1 = "0.0", discountpersen2 = "0.0", discountpersen3= "0.0",
            discountRp1= "0.0", discountRp2= "0.0", discountRp3= "0.0";
    static double hargakecil, TotalQtyKecil, TotalHargaBeli, JumlahTotalHargaBeli =0;
    static double TotalHargaBeli2 ;
    static String TotalGross, custid;
    static int click = 1;
    static ArrayList<Customer> PRODUKList;
    static SqliteManagerTemp dbtemp;
    static String nopocustomer;
    static String pilihandistributor;
    static String catatan;
    SharedPreferences.Editor ed ;
    MyCustomAdapter CustomerAdapter;
    SharedPreferences prefLocations;

    static LocationRequest mLocationRequest;
    static boolean mRequestingLocationUpdates= true;

    private Context context;
    Activity activity;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    static String latitude = "0";
    static String longitude = "0";
    private final int APP_THEME = R.array.blood;
    String fontPathNeoSans = "fonts/Neo Sans.ttf";
    static Typeface tf;
    String fontPathUnivers = "fonts/Univers.ttf";
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(APP_THEME);
        ProdukList = new ArrayList<HashMap<String, String>>();

        Toolbar tool = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        db = new SqliteManagerData(Details.this);
        db.bukaKoneksi();
        dbtemp = new SqliteManagerTemp(Details.this);
        kodeOP = (TextView) findViewById(R.id.kodeOP);
        NamaProduk = (TextView) findViewById(R.id.NamaProduk);
        textBesar = (TextView) findViewById(R.id.textBesar);
        textSedang = (TextView) findViewById(R.id.textSedang);
        textKecil = (TextView) findViewById(R.id.textKecil);
        discountpersen = (TextView) findViewById(R.id.discountpersen);
        textTotal = (TextView) findViewById(R.id.textTotal);
        KodeProduk = (EditText) findViewById(R.id.KodeProduk);
        stock = (EditText) findViewById(R.id.stock);
        editBesar = (FlatEditText) findViewById(R.id.editBesar);
        editSedang = (FlatEditText) findViewById(R.id.editSedang);
        editKecil = (FlatEditText) findViewById(R.id.editKecil);
        discountsatu = (EditText) findViewById(R.id.discountsatu);
        discountdua = (EditText) findViewById(R.id.discountdua);
        discounttiga = (EditText) findViewById(R.id.discounttiga);
        editdisRpsatu = (EditText) findViewById(R.id.editdisRpsatu);
        editdisRpdua = (EditText) findViewById(R.id.editdisRpdua);
        editdisRptiga = (EditText) findViewById(R.id.editdisRptiga);
        Harga = (Spinner) findViewById(R.id.Harga);
        cekitem = (FlatButton) findViewById(R.id.cekitem);
        simpan = (FlatButton) findViewById(R.id.simpan);
        hitung = (FlatButton) findViewById(R.id.hitung);
        list = (ListView) findViewById(R.id.listProduk);

        prefLocations = getSharedPreferences("tbGross", Context.MODE_PRIVATE);
        TotalGross = prefLocations.getString("gross", "");
        ed = prefLocations.edit();
        tf = Typeface.createFromAsset(getAssets(), fontPathNeoSans);
        Typeface tf2 = Typeface.createFromAsset(getAssets(), fontPathUnivers);

        // SETTING YANG TERBARU GOOGLE PLAY SERVICE
        context = getApplicationContext();
        activity = this;
        if(checkPermission())
        {
         //   Toast.makeText(Details.this, "permission granted", Toast.LENGTH_SHORT).show();
            buildGoogleApiClient();
            createLocationRequest();
        }
        else{
            requestPermission();
        }




        Bundle extras = getIntent().getExtras();
        if(extras.getBoolean("myBoolean") ) {
            orderpenjualan = extras.getString("orderpenjualan");
            nopocustomer = extras.getString("nopocustomer");
            custid = extras.getString("custid");
            pilihandistributor = extras.getString("pilihandistributor");
            catatan = extras.getString("catatan");
         //   Toast.makeText(Details.this, "ini bukan dari database" , Toast.LENGTH_LONG).show();
        }else if(!extras.getBoolean("myBoolean")){
            orderpenjualan = extras.getString("faktur");
            nopocustomer = extras.getString("nopocustomer");
            custid = extras.getString("custid");
            catatan = extras.getString("catatan");
            pilihandistributor = dbtemp.getAllContacts(orderpenjualan);
            dbtemp.tutupKoneksi();
        }
        kodeOP.setText(orderpenjualan);
        final String parts3[] = pilihandistributor.split("\\/", -1);
      //  Toast.makeText(Details.this, parts3[0], Toast.LENGTH_LONG).show();







        //////////////////////////////////////////////////////////////////////////////////////////
        // setCustomList yang baru
        /////////////////////////////////////////////////////////////////////////////////////////



        PRODUKList = new ArrayList<Customer>();
        PRODUKList = db.getAllDataProdukListViewArrayList(parts3[0].trim());
        CustomerAdapter = new MyCustomAdapter(Details.this, R.layout.customer_layout, PRODUKList);
        list.setAdapter(CustomerAdapter);
        list.setTextFilterEnabled(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Customer custom = (Customer) parent.getItemAtPosition(position);
       //         Toast.makeText(Details.this,
       //                 custom.getCode(), Toast.LENGTH_SHORT).show();

                detail = db.getAllDataProdukDetail(custom.getCode().trim());

                KodeProduk.setText(custom.getCode().trim());
                sNamaProduk = custom.getNama().trim();
                Log.d("nilai", detail.get(0));
                // memasukkan nilai harga
                textBesar.setTypeface(tf);
                textSedang.setTypeface(tf);
                textKecil.setTypeface(tf);

                textBesar.setText(detail.get(6) + ":" + detail.get(3));
                textSedang.setText(detail.get(7) + ":" +detail.get(4));
                textKecil.setText(detail.get(8) + ":" + detail.get(5));
                DecimalFormat df2 = new DecimalFormat(".##");

                String ppn = parts3[4].trim();
                if (ppn.equals("N")) {
                    harga.add("" + df2.format((Double.parseDouble(detail.get(9)) * 1.1)));
                    harga.add("" + df2.format((Double.parseDouble(detail.get(10)) * 1.1)));
                    harga.add("" + df2.format((Double.parseDouble(detail.get(11)) * 1.1)));
                    harga.add("" + df2.format((Double.parseDouble(detail.get(12)) * 1.1)));
                    harga.add("" + df2.format((Double.parseDouble(detail.get(13)) * 1.1)));
                    harga.add("" + df2.format((Double.parseDouble(detail.get(14)) * 1.1)));
                    harga.add("" + df2.format((Double.parseDouble(detail.get(15)) * 1.1)));
                    harga.add("" + df2.format((Double.parseDouble(detail.get(16)) * 1.1)));
                    harga.add("" + df2.format((Double.parseDouble(detail.get(17)) * 1.1)));
                    harga.add("" + df2.format((Double.parseDouble(detail.get(18)) * 1.1)));

                    // harga non desimal
                    harganondesimal.add("" + (int)((Double.parseDouble(detail.get(9)) * 1.1)));
                    harganondesimal.add("" + (int)((Double.parseDouble(detail.get(10)) * 1.1)));
                    harganondesimal.add("" + (int)((Double.parseDouble(detail.get(11)) * 1.1)));
                    harganondesimal.add("" + (int)((Double.parseDouble(detail.get(12)) * 1.1)));
                    harganondesimal.add("" + (int)((Double.parseDouble(detail.get(13)) * 1.1)));
                    harganondesimal.add("" + (int)((Double.parseDouble(detail.get(14)) * 1.1)));
                    harganondesimal.add("" + (int)((Double.parseDouble(detail.get(15)) * 1.1)));
                    harganondesimal.add("" + (int)((Double.parseDouble(detail.get(16)) * 1.1)));
                    harganondesimal.add("" + (int)((Double.parseDouble(detail.get(17)) * 1.1)));
                    harganondesimal.add("" + (int)((Double.parseDouble(detail.get(18)) * 1.1)));


                    //  harga.add(""+(Double.parseDouble(detail.get(19)) * 1.1));

                } else {

                    harga.add(detail.get(9));
                    harga.add(detail.get(10));
                    harga.add(detail.get(11));
                    harga.add(detail.get(12));
                    harga.add(detail.get(13));
                    harga.add(detail.get(14));
                    harga.add(detail.get(15));
                    harga.add(detail.get(16));
                    harga.add(detail.get(17));
                    harga.add(detail.get(18));

                    // hasil non desimal
                    harganondesimal.add(detail.get(9));
                    harganondesimal.add(detail.get(10));
                    harganondesimal.add(detail.get(11));
                    harganondesimal.add(detail.get(12));
                    harganondesimal.add(detail.get(13));
                    harganondesimal.add(detail.get(14));
                    harganondesimal.add(detail.get(15));
                    harganondesimal.add(detail.get(16));
                    harganondesimal.add(detail.get(17));
                    harganondesimal.add(detail.get(18));

                    // harga.add(detail.get(19));
                    //harga.add(detail.get(11));
                }

                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Details.this,
                        android.R.layout.simple_spinner_item, harganondesimal);
                // Drop down layout style - list view with radio button
                dataAdapter
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Harga.setAdapter(dataAdapter);
            }
        });

        KodeProduk.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CustomerAdapter.getFilter().filter(s.toString());
            }
        });





        //////////////////////////////////////////////////////////////////////////////////////////
        // setCustomList yang lama
        /////////////////////////////////////////////////////////////////////////////////////////

/*
        ProdukList = db.getAllDataProdukListView(parts3[0].trim());

        ListAdapter adapter;
        adapter = new SimpleAdapter(Details.this, ProdukList, R.layout.listproduk, new
                String[]{"kode", "nama"} , new int []{R.id.name, R.id.email});
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // getting values from selected ListItem
                harga = new ArrayList<String>();

                String
                        name = ((TextView) view.findViewById(R.id.name))
                        .getText().toString();
                sNamaProduk = ((TextView) view.findViewById(R.id.email))
                        .getText().toString();
                String description = ((TextView) view.findViewById(R.id.mobile))
                        .getText().toString();

                KodeProduk.setText(name);

                detail = db.getAllDataProdukDetail(name);


                Log.d("nilai", detail.get(0));
                // memasukkan nilai harga
                textBesar.setText(detail.get(6) + detail.get(3));
                textSedang.setText(detail.get(7) + detail.get(4));
                textKecil.setText(detail.get(8) + detail.get(5));
                DecimalFormat df2 = new DecimalFormat(".##");
                String ppn = parts3[4].trim();
                if (ppn.equals("N")) {
                    harga.add("" + df2.format((Double.parseDouble(detail.get(9)) * 1.1)));
                    harga.add("" + df2.format((Double.parseDouble(detail.get(10)) * 1.1)));
                    harga.add("" + df2.format((Double.parseDouble(detail.get(11)) * 1.1)));
                    harga.add("" + df2.format((Double.parseDouble(detail.get(12)) * 1.1)));
                    harga.add("" + df2.format((Double.parseDouble(detail.get(13)) * 1.1)));
                    harga.add("" + df2.format((Double.parseDouble(detail.get(14)) * 1.1)));
                    harga.add("" + df2.format((Double.parseDouble(detail.get(15)) * 1.1)));
                    harga.add("" + df2.format((Double.parseDouble(detail.get(16)) * 1.1)));
                    harga.add("" + df2.format((Double.parseDouble(detail.get(17)) * 1.1)));
                    harga.add("" + df2.format((Double.parseDouble(detail.get(18)) * 1.1)));
                    //  harga.add(""+(Double.parseDouble(detail.get(19)) * 1.1));

                } else {

                    harga.add(detail.get(9));
                    harga.add(detail.get(10));
                    harga.add(detail.get(11));
                    harga.add(detail.get(12));
                    harga.add(detail.get(13));
                    harga.add(detail.get(14));
                    harga.add(detail.get(15));
                    harga.add(detail.get(16));
                    harga.add(detail.get(17));
                    harga.add(detail.get(18));
                    // harga.add(detail.get(19));
                    //harga.add(detail.get(11));
                }

                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Details.this,
                        android.R.layout.simple_spinner_item, harga);
                // Drop down layout style - list view with radio button
                dataAdapter
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Harga.setAdapter(dataAdapter);
            }
        });
*/



































        db.tutupKoneksi();
        Harga.setOnItemSelectedListener(new MyOnItemSelectedListener());

        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //////////////////////////////////////////////////////////////////////////////////
                // Total Harga (harga kecil) = hargaList / Qty besar
                /////////////////////////////////////////////////////////////////////////////////
                cekhitung++;
                if(!harganondesimal.isEmpty()) {

                    /*if(TextUtils.isEmpty(editBesar.getText().toString()) && TextUtils.isEmpty(editSedang.getText().toString())
                            && TextUtils.isEmpty(editKecil.getText().toString()) && TextUtils.isEmpty(discountsatu.getText().toString())
                            && TextUtils.isEmpty(discountdua.getText().toString()) && TextUtils.isEmpty(discounttiga.getText().toString())
                            && TextUtils.isEmpty(editdisRpsatu.getText().toString()) && TextUtils.isEmpty(editdisRpdua.getText().toString())
                            && TextUtils.isEmpty(editdisRptiga.getText().toString())){
                        textTotal.setText("Total Rp 0.-" );
                    }
                    else {
                    */

                        hargakecil = Double.parseDouble(sSpinnnerHarga) / Double.parseDouble(detail.get(6));

                    Toast.makeText(Details.this, "harga spinner " + sSpinnnerHarga, Toast.LENGTH_SHORT).show();



                    //   Toast.makeText(Details.this, "harga kecil " + hargakecil, Toast.LENGTH_SHORT).show();
                        //////////////////////////////////////////////////////////////////////////////////
                        // Total QtyKecil = belibesar * Qty besar + beliSedang * Qty sedang +
                        // beli Kecil * Qty Kecil
                        // TotalHargaBeli = hargakecil * TotalQtyKecil;
                        /////////////////////////////////////////////////////////////////////////////////
                        TotalQtyKecil = 0;
                        if (!TextUtils.isEmpty(editBesar.getText().toString())) {
                            TotalQtyKecil = TotalQtyKecil + Double.parseDouble(editBesar.getText().toString())
                                    * Double.parseDouble(detail.get(6));
                        } else {
                            TotalQtyKecil = TotalQtyKecil + 0
                                    * Double.parseDouble(detail.get(6));
                        }
                        if (!TextUtils.isEmpty(editSedang.getText().toString())) {
                            TotalQtyKecil = TotalQtyKecil + Double.parseDouble(editSedang.getText().toString())
                                    * Double.parseDouble(detail.get(7));
                        } else {
                            TotalQtyKecil = TotalQtyKecil + 0
                                    * Double.parseDouble(detail.get(7));
                        }
                        if (!TextUtils.isEmpty(editKecil.getText().toString())) {
                            TotalQtyKecil = TotalQtyKecil + Double.parseDouble(editKecil.getText().toString())
                                    * Double.parseDouble(detail.get(8));
                        } else {
                            TotalQtyKecil = TotalQtyKecil + 0
                                    * Double.parseDouble(detail.get(8));
                        }


                        // Total Qty Kecil sudah diperbaiki dengan kode yang diatas
/*                TotalQtyKecil = Double.parseDouble(editBesar.getText().toString()) * Double.parseDouble(detail.get(6))
                        + Double.parseDouble(editSedang.getText().toString()) * Double.parseDouble(detail.get(7)) +
                        Double.parseDouble(editKecil.getText().toString()) * Double.parseDouble(detail.get(8));
  */              // Toast.makeText(Details.this, "Total QtyKecil " + TotalQtyKecil, Toast.LENGTH_SHORT).show();

                        // Total Harga Beli per pieces (Harga Terkecil)
                        TotalHargaBeli = hargakecil * TotalQtyKecil;
                        //  Toast.makeText(Details.this, "TotalHargaBeli " + TotalHargaBeli, Toast.LENGTH_SHORT).show();


                        int TotalQtyKecilInt = (int) TotalQtyKecil / Integer.parseInt(detail.get(6));
                        //  Toast.makeText(Details.this, "per karton " + TotalQtyKecilInt, Toast.LENGTH_SHORT).show();
                    Toast.makeText(Details.this, "ini Total Kuantitas Kecil " + TotalQtyKecilInt, Toast.LENGTH_LONG ).show();


                    if (!TextUtils.isEmpty(discountsatu.getText().toString())) {
                            TotalHargaBeli2 = TotalHargaBeli - (TotalHargaBeli *
                                    Double.parseDouble(discountsatu.getText().toString()) / 100);
                            discount1 = discountsatu.getText().toString() + "%";
                            discountpersen1 = discountsatu.getText().toString();
                        } else {
                            if (!TextUtils.isEmpty(editdisRpsatu.getText().toString())) {
                                if(TotalQtyKecilInt == 0 )
                                {
                                    TotalHargaBeli2 = TotalHargaBeli - Double.parseDouble(editdisRpsatu.getText().toString());
                                    discount1 = editdisRpsatu.getText().toString();
                                    discountRp1 = editdisRpsatu.getText().toString();
                                }else{
                                TotalHargaBeli2 = TotalHargaBeli - (Double.parseDouble(editdisRpsatu.getText().toString()) * (double)
                                        TotalQtyKecilInt);
                                discount1 = editdisRpsatu.getText().toString();
                                discountRp1 = editdisRpsatu.getText().toString();}
                            }
                            else {
                                TotalHargaBeli2 = TotalHargaBeli - (0 * (double)
                                        TotalQtyKecilInt);
                                discount1 = "0";
                                discountRp1 = "0.0";
                            }
                        }

                        if (!TextUtils.isEmpty(discountdua.getText().toString())) {
                            TotalHargaBeli2 = TotalHargaBeli2 - (TotalHargaBeli2 * Double.parseDouble(discountdua.getText().toString()) / 100);
                            //     Toast.makeText(Details.this, "Discount Kedua " + TotalHargaBeli2, Toast.LENGTH_SHORT).show();
                            discount2 = discountdua.getText().toString() + "%";
                            discountpersen2 = discountdua.getText().toString();
                        } else {
                            if (!TextUtils.isEmpty(editdisRpdua.getText().toString())) {
                                if(TotalQtyKecilInt == 0 )
                                {
                                    TotalHargaBeli2 = TotalHargaBeli2 - Double.parseDouble(editdisRpdua.getText().toString());
                                    discount2 = editdisRpdua.getText().toString();
                                    discountRp2 = editdisRpdua.getText().toString();
                                }else{
                                TotalHargaBeli2 = TotalHargaBeli2 - (Double.parseDouble(editdisRpdua.getText().toString()) * (double)
                                        TotalQtyKecilInt);
                                discount2 = editdisRpdua.getText().toString();
                                discountRp2 = editdisRpdua.getText().toString();}
                            } else {
                                TotalHargaBeli2 = TotalHargaBeli2 - (0 * (double)
                                        TotalQtyKecilInt);
                                discount2 = "0";

                                discountRp2 = "0.0";
                            }
                        }

                        if (!TextUtils.isEmpty(discounttiga.getText().toString())) {
                            TotalHargaBeli2 = TotalHargaBeli2 - (TotalHargaBeli2 * Double.parseDouble(discounttiga.getText().toString()) / 100);
                            discount3 = discounttiga.getText().toString() + "%";
                            discountpersen3 = discounttiga.getText().toString();
                        } else {
                            if (!TextUtils.isEmpty(editdisRptiga.getText().toString())) {
                                if(TotalQtyKecilInt == 0 )
                                {
                                    TotalHargaBeli2 = TotalHargaBeli2 - Double.parseDouble(editdisRptiga.getText().toString());
                                    discount3 = editdisRptiga.getText().toString();
                                    discountRp3 = editdisRptiga.getText().toString();
                                }else{
                                TotalHargaBeli2 = TotalHargaBeli2 - (Double.parseDouble(editdisRptiga.getText().toString()) * (double)
                                        TotalQtyKecilInt);
                                discount3 = editdisRptiga.getText().toString();
                                discountRp3 = editdisRptiga.getText().toString();}
                            } else {
                                TotalHargaBeli2 = TotalHargaBeli2 - (0 * (double)
                                        TotalQtyKecilInt);
                                discount3 = "0";
                                discountRp3 = "0.0";
                            }
                        }


                        // KODING LAMA PENTING
               /* if (!TextUtils.isEmpty(discountsatu.getText().toString())) {
                    // Discount 1
                    TotalHargaBeli2 = (double) TotalHargaBeli - ((double) TotalHargaBeli *
                            Double.parseDouble(discountsatu.getText().toString()) / 100);
                    discount1 = discountsatu.getText().toString() + "%";
                    discountpersen1 = discountsatu.getText().toString();
               //     Toast.makeText(Details.this, "Discount Pertama " + TotalHargaBeli2, Toast.LENGTH_SHORT).show();

                    if (!TextUtils.isEmpty(discountdua.getText().toString())) {
                        // Discount 2
                        TotalHargaBeli2 = TotalHargaBeli2 - (TotalHargaBeli2 * Double.parseDouble(discountdua.getText().toString()) / 100);
                   //     Toast.makeText(Details.this, "Discount Kedua " + TotalHargaBeli2, Toast.LENGTH_SHORT).show();
                        discount2 = discountdua.getText().toString() + "%";
                        discountpersen2 = discountdua.getText().toString();
                        if (!TextUtils.isEmpty(discounttiga.getText().toString())) {
                            // Discount 3
                            TotalHargaBeli2 = TotalHargaBeli2 - (TotalHargaBeli2 * Double.parseDouble(discounttiga.getText().toString()) / 100);
                            discount3 = discounttiga.getText().toString() + "%";
                            discountpersen3 = discounttiga.getText().toString();
                        } else if (!TextUtils.isEmpty(editdisRptiga.getText().toString())) {
                            // Rupiah 3
                            TotalHargaBeli2 = TotalHargaBeli2 - (Double.parseDouble(editdisRptiga.getText().toString()) * (double)
                                    TotalQtyKecilInt);
                            discount3 = editdisRptiga.getText().toString();
                            discountRp3 = editdisRptiga.getText().toString();
                        }
                    } else if (!TextUtils.isEmpty(editdisRpdua.getText().toString())) {
                        // Rupiah 2
                        TotalHargaBeli2 = TotalHargaBeli2 - (Double.parseDouble(editdisRpdua.getText().toString()) * (double)
                                TotalQtyKecilInt);
                        discount2 = editdisRpdua.getText().toString();
                        discountRp2 = editdisRpdua.getText().toString();
                        if (!TextUtils.isEmpty(discounttiga.getText().toString())) {
                            // Discount 3
                            TotalHargaBeli2 = TotalHargaBeli2 - (TotalHargaBeli2 * Double.parseDouble(discounttiga.getText().toString()) / 100);
                            discount3 = discounttiga.getText().toString() + "%";
                            discountpersen3 = discounttiga.getText().toString();
                        } else if (!TextUtils.isEmpty(editdisRptiga.getText().toString())) {
                            // Rupiah 3
                            TotalHargaBeli2 = TotalHargaBeli2 - (Double.parseDouble(editdisRptiga.getText().toString()) * (double)
                                    TotalQtyKecilInt);
                            discount3 = editdisRptiga.getText().toString();
                            discountRp3 = editdisRptiga.getText().toString();
                        }
                    }

                } else if (!TextUtils.isEmpty(editdisRpsatu.getText().toString())) {
                    // Rupiah 1
                    TotalHargaBeli2 = (double) TotalHargaBeli - (Double.parseDouble(editdisRpsatu.getText().toString()) * (double)
                            TotalQtyKecilInt);
                    discount1 = editdisRpsatu.getText().toString();
                    discountRp1 = editdisRpsatu.getText().toString();
                //    Toast.makeText(Details.this, "Harga Setelah Diskon1 " + TotalHargaBeli2, Toast.LENGTH_SHORT).show();

                    if (!TextUtils.isEmpty(discountdua.getText().toString())) {
                        // Discount 2
                        TotalHargaBeli2 = TotalHargaBeli2 - (TotalHargaBeli2 * Double.parseDouble(discountdua.getText().toString()) / 100);
                        discount2 = discountdua.getText().toString() + "%";
                        discountpersen2 = discountdua.getText().toString();
                        if (!TextUtils.isEmpty(discounttiga.getText().toString())) {
                            // Discount 3
                            TotalHargaBeli2 = TotalHargaBeli2 - (TotalHargaBeli2 * Double.parseDouble(discounttiga.getText().toString()) / 100);
                            discount3 = discounttiga.getText().toString() + "%";
                            discountpersen3 = discounttiga.getText().toString();
                        } else if (!TextUtils.isEmpty(editdisRptiga.getText().toString())) {
                            // Rupiah 3
                            TotalHargaBeli2 = TotalHargaBeli2 - (Double.parseDouble(editdisRptiga.getText().toString()) * (double)
                                    TotalQtyKecilInt);
                            discount3 = editdisRptiga.getText().toString();
                            discountRp3 = editdisRptiga.getText().toString();
                        }
                    } else if (!TextUtils.isEmpty(editdisRpdua.getText().toString())) {
                        // Rupiah 2
                        TotalHargaBeli2 = TotalHargaBeli2 - (Double.parseDouble(editdisRpdua.getText().toString()) * (double)
                                TotalQtyKecilInt);
                        discount2 = editdisRpdua.getText().toString();
                        discountRp2 = editdisRpdua.getText().toString();

                        if (!TextUtils.isEmpty(discounttiga.getText().toString())) {
                            // Discount 3
                            TotalHargaBeli2 = TotalHargaBeli2 - (TotalHargaBeli2 * Double.parseDouble(discounttiga.getText().toString()) / 100);
                            discount3 = discounttiga.getText().toString() + "%";
                            discountpersen3 = discounttiga.getText().toString();
                        } else if (!TextUtils.isEmpty(editdisRptiga.getText().toString())) {
                            // Rupiah 3
                            TotalHargaBeli2 = TotalHargaBeli2 - (Double.parseDouble(editdisRptiga.getText().toString()) * (double)
                                    TotalQtyKecilInt);
                            discount3 = editdisRptiga.getText().toString();
                            discountRp3 = editdisRptiga.getText().toString();
                        }
                    }

                }*/
                        //    Toast.makeText(Details.this, "Total Harga Setelah Diskon " + TotalHargaBeli2, Toast.LENGTH_SHORT).show();
                    if(TotalHargaBeli2>10)
                    {
                    DecimalFormat df2 = new DecimalFormat(".-");
                        String roundup = "" + df2.format(TotalHargaBeli2);
                        //roundup = Double.toString(TotalHargaBeli2);
                        //Toast.makeText(Details.this, roundup + " ini nilai  " + roundup.charAt(roundup.length() - 4), Toast.LENGTH_LONG).show();

                        textTotal.setText("Total Rp " + Math.round(TotalHargaBeli2));
                        // round ratusan
                        /*if (Character.getNumericValue(roundup.charAt(roundup.length() - 4)) < 5) {
                            textTotal.setText("Total Rp " + roundup);
                            Toast.makeText(Details.this, " ini benar  ", Toast.LENGTH_LONG).show();

                        } else {
                            textTotal.setText("Total Rp " + ((double) Math.ceil(TotalHargaBeli2 / 100.0)) * 100.0);
                        }*/
                    }else{
                        textTotal.setText("Total Rp 0.-");
                    }

                }
                else{
                    Toast.makeText(Details.this, "Pilih Kode Produk Terlebih Dahulu" , Toast.LENGTH_LONG).show();
                }
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbDetails = new SqliteManagerDetails(Details.this);
                dbDetails.bukaKoneksi();

                String name = "";
                List<Contact> contacts = dbDetails.getDuplikat(orderpenjualan, KodeProduk.getText().toString());
                for (Contact cn : contacts) {
                    // Writing Contacts to log
                    name = cn.getName();

                }
                dbDetails.tutupKoneksi();
                if (name.equals("")) {
                    if (cekhitung > 0) {
                        dbDetails = new SqliteManagerDetails(Details.this);
                        dbDetails.bukaKoneksi();




/*                long nilai = dbDetails.insertDataDetail(dbDetails.InsertDataDetail(orderpenjualan, KodeProduk.getText().toString(),
                        sSpinnnerHarga, stock.getText().toString(), sNamaProduk,editBesar.getText().toString(),
                        editSedang.getText().toString(), editKecil.getText().toString(), ""+ TotalHargaBeli , discount1,
                        discount2, discount3, "" +TotalHargaBeli2));
*/
                        ////////////////////////////////////////////////////////////////////////////////////
                        // no urut, no faktur, stock id, stock name, stock outlet, qty, harga, jlh, d1, d2, d3
                        // total, qtyb, qtym, qtyk, rpd1, rpd2, rpd3
                        ////////////////////////////////////////////////////////////////////////////////////

                        String besar = "0", sedang = "0", kecil = "0";
                        if (!TextUtils.isEmpty(editBesar.getText().toString())) {
                            besar = editBesar.getText().toString();
                        }
                        if (!TextUtils.isEmpty(editSedang.getText().toString())) {
                            sedang = "" + (Double.parseDouble(editSedang.getText().toString()) * Double.parseDouble(detail.get(7)));
                        }
                        if (!TextUtils.isEmpty(editKecil.getText().toString())) {
                            kecil = "" + (Double.parseDouble(editKecil.getText().toString()) * Double.parseDouble(detail.get(8)));
                        }

                        /*if(TextUtils.isEmpty(editBesar.getText().toString()) && TextUtils.isEmpty(editSedang.getText().toString())
                                && TextUtils.isEmpty(editKecil.getText().toString()) && TextUtils.isEmpty(discountsatu.getText().toString())
                                && TextUtils.isEmpty(discountdua.getText().toString()) && TextUtils.isEmpty(discounttiga.getText().toString())
                                && TextUtils.isEmpty(editdisRpsatu.getText().toString()) && TextUtils.isEmpty(editdisRpdua.getText().toString())
                                && TextUtils.isEmpty(editdisRptiga.getText().toString())){
                            long nilai = dbDetails.insertDataDetail(dbDetails.InsertDataDetail(
                                    "" + click, orderpenjualan, KodeProduk.getText().toString(), sNamaProduk, "0",
                                    "" + "0", parts3[2], "" + "0", "0", "0", "0",
                                    "" + "0", "0", "0", "0",
                                    "0", "0", "0"
                            ));
                        }
                        else {*/
                        String stok = "0";
                        if(!TextUtils.isEmpty(stock.getText().toString()))
                        {
                            stok = stock.getText().toString();
                        }


                            long nilai = dbDetails.insertDataDetail(dbDetails.InsertDataDetail(
                                    "" + click, orderpenjualan, KodeProduk.getText().toString(), sNamaProduk, stok,
                                    "" + TotalQtyKecil, sSpinnnerHarga, "" + TotalHargaBeli, discountpersen1, discountpersen2, discountpersen3,
                                    "" + TotalHargaBeli2, besar, sedang, kecil,
                                    discountRp1, discountRp2, discountRp3
                            ));
                        cekitem.setText("cek :" + click);
//                        }

                        dbDetails.tutupKoneksi();
                        TotalHargaBeli2 = 0;
                        click++;


                        stock.setText("");
                        KodeProduk.setText("");
                        editBesar.setText("");
                        editSedang.setText("");
                        editKecil.setText("");
                        discountsatu.setText("");
                        discountdua.setText("");
                        discounttiga.setText("");
                        editdisRpsatu.setText("");
                        editdisRpdua.setText("");
                        editdisRptiga.setText("");
                        harganondesimal.clear();
                        harga.clear();
                        textTotal.setText("Total Rp");
                        cekhitung = 0;
                        textBesar.setText("xxx:nn");
                        textSedang.setText("xxx:nn");
                        textKecil.setText("xxx:nn");
                        KodeProduk.requestFocus();
                        Harga.setAdapter(null);
                    } else {
                        Toast.makeText(Details.this, "Tolong Tekan Tombol Hitung terlebih dahulu", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Details.this, "Data Produk Sudah dipesan", Toast.LENGTH_LONG).show();
                    stock.setText("");
                    KodeProduk.setText("");
                    editBesar.setText("");
                    editSedang.setText("");
                    editKecil.setText("");
                    discountsatu.setText("");
                    discountdua.setText("");
                    discounttiga.setText("");
                    editdisRpsatu.setText("");
                    editdisRpdua.setText("");
                    editdisRptiga.setText("");
                    harganondesimal.clear();
                    harga.clear();
                    textBesar.setText("xxx:nn");
                    textSedang.setText("xxx:nn");
                    textKecil.setText("xxx:nn");
                    textTotal.setText("Total Rp");
                    cekhitung = 0;
                    KodeProduk.requestFocus();
                    Harga.setAdapter(null);
                }

            }

        });

        cekitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //dbDetails = new SqliteManagerDetails(Details.this);
                //dbDetails.bukaKoneksi();
                //dbDetails.setUpdateHeader("masuk", orderpenjualan);
                //dbDetails.tutupKoneksi();
                if(TextUtils.isEmpty(editBesar.getText().toString()) && TextUtils.isEmpty(editSedang.getText().toString())
                        && TextUtils.isEmpty(editKecil.getText().toString()) && TextUtils.isEmpty(discountsatu.getText().toString())
                        && TextUtils.isEmpty(discountdua.getText().toString()) && TextUtils.isEmpty(discounttiga.getText().toString())
                        && TextUtils.isEmpty(editdisRpsatu.getText().toString()) && TextUtils.isEmpty(editdisRpdua.getText().toString())
                        && TextUtils.isEmpty(editdisRptiga.getText().toString())){
                    Intent i = new Intent(Details.this, CekItem.class);
                    i.putExtra("orderpenjualan", orderpenjualan);
                    i.putExtra("custid", custid);
                    i.putExtra("ppn", "" + parts3[4]);
                    i.putExtra("latitude", latitude);
                    i.putExtra("longitude", longitude);

                    startActivity(i);
                    finish();
                }else {

                    Intent i = new Intent(Details.this, CekItem.class);
                    i.putExtra("orderpenjualan", orderpenjualan);
                    i.putExtra("custid", custid);
                    i.putExtra("ppn", "" + parts3[4]);
                    i.putExtra("latitude", latitude);
                    i.putExtra("longitude", longitude);

                    startActivity(i);
                    finish();
                }
            }
        });
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
           // Toast.makeText(context, "Last Location : " + String.valueOf(mLastLocation.getLatitude()), Toast.LENGTH_LONG).show();
           // Toast.makeText(context, "Last Location : " + String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_LONG).show();
            latitude = String.valueOf(mLastLocation.getLatitude());
            longitude = String.valueOf(mLastLocation.getLongitude());

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

       // Toast.makeText(context, "update Location : " + String.valueOf(location.getLatitude()), Toast.LENGTH_LONG).show();
       // Toast.makeText(context, "Update Location : " + String.valueOf(location.getLongitude()), Toast.LENGTH_LONG).show();

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
        click = 1;
    }






    class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView parent, View view, int pos, long id) {
            sSpinnnerHarga = harganondesimal.get(pos);
            Toast.makeText(Details.this, ""+ harga.get(pos) , Toast.LENGTH_LONG).show();
        }
        @Override
        public void onNothingSelected(AdapterView parent) {
        }
    }


    private class MyCustomAdapter extends ArrayAdapter<Customer> {

        private ArrayList<Customer> originalList;
        private ArrayList<Customer> countryList;
        private CountryFilter filter;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Customer> countryList) {
            super(context, textViewResourceId, countryList);
            this.countryList = new ArrayList<Customer>();
            this.countryList.addAll(countryList);
            this.originalList = new ArrayList<Customer>();
            this.originalList.addAll(countryList);
        }

        @Override
        public Filter getFilter() {
            if (filter == null) {
                filter = new CountryFilter();
            }
            return filter;
        }


        private class ViewHolder {
            TextView code;
            TextView name;
            TextView continent;
            TextView region;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));
            if (convertView == null) {

                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.listproduk, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.name);
                holder.name = (TextView) convertView.findViewById(R.id.email);
                holder.continent = (TextView) convertView.findViewById(R.id.mobile);
                //holder.region = (TextView) convertView.findViewById(R.id.region);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Customer country = countryList.get(position);
            holder.code.setText(country.getCode());
            holder.name.setText(country.getNama());
            //holder.continent.setText(country.getContinent());
            // holder.region.setText(country.getRegion());

            return convertView;

        }

        private class CountryFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                constraint = constraint.toString().toLowerCase();
                FilterResults result = new FilterResults();
                if (constraint != null && constraint.toString().length() > 0) {
                    ArrayList<Customer> filteredItems = new ArrayList<Customer>();

                    for (int i = 0, l = originalList.size(); i < l; i++) {
                        Customer country = originalList.get(i);
                        if (country.toString().toLowerCase().contains(constraint))
                            filteredItems.add(country);
                    }
                    result.count = filteredItems.size();
                    result.values = filteredItems;
                } else {
                    synchronized (this) {
                        result.values = originalList;
                        result.count = originalList.size();
                    }
                }
                return result;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {

                countryList = (ArrayList<Customer>) results.values;
                notifyDataSetChanged();
                clear();
                for (int i = 0, l = countryList.size(); i < l; i++)
                    add(countryList.get(i));
                notifyDataSetInvalidated();
            }
        }

    }

}
