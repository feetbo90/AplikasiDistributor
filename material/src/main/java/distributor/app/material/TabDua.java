package distributor.app.material;

/*
 * Created by feetbo on 10/17/2015.
 */
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;

import android.widget.ListView;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.cengalabs.flatui.views.FlatButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


import distributor.app.material.model.Customer;
import distributor.app.material.sqlite.SqliteManagerData;
import distributor.app.material.sqlite.SqliteManagerDetails;
import distributor.app.material.sqlite.SqliteManagerTemp;


public class TabDua extends Fragment {

    FlatButton flabtn;
    ArrayList<HashMap<String, String>> contactlist;
    static List<String> vSpinner;
    SqliteManagerData db;
    SqliteManagerDetails dbDetails;
    EditText custid, OrderPenjualan, NoPoCustomer, Catatan;
    static String sKodeArea, sKodeSales;
    ListView lv;
    Spinner spinner, spinnertocanvasds;

    static SqliteManagerTemp dbTemp ;
    ////////////////////////////////////////////////////////////////////////////
    // PENGAMBILAN NILAI CALENDAR
    private int year, jam, menit, detik;
    private int month;
    private int day;
    private String TahunStr, JamStr, MenitStr, DetikStr, BulanStr, HariStr;
    ///////////////////////////////////////////////////////////////////////////

    //GPSTracker gps;
    String orderpo, catatan;
    //static String nama, alamat;

// Data Customer
MyCustomAdapter CustomerAdapter = null;
    Calendar c;

    static String sCustid = "", penggabungan;

    static ArrayList<Customer> customerList;
    public TabDua() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        contactlist = new ArrayList<HashMap<String, String>>();

        // pertama buka koneksi untuk membuat list
        db = new SqliteManagerData(getActivity());
        db.bukaKoneksi();


        ////////////////////////////////////////////////////////////////////////////////
        // MENGAMBIL KODE AREA DAN KODE SALES
        ///////////////////////////////////////////////////////////////////////////////


        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            sKodeArea = extras.getString("kodearea");
            sKodeSales = extras.getString("kodesales");
            Log.d("fragment dua", sKodeArea + " " + sKodeSales);

        }


        contactlist = db.getAllDataCustomerListView();
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_two, container, false);
        lv = (ListView) rootView.findViewById(R.id.list);
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        spinnertocanvasds = (Spinner) rootView.findViewById(R.id.spinnertocanvasds);
        OrderPenjualan = (EditText) rootView.findViewById(R.id.kodeOP);
        NoPoCustomer = (EditText) rootView.findViewById(R.id.kode1);
        Catatan = (EditText) rootView.findViewById(R.id.kode2);
        custid = (EditText) rootView.findViewById(R.id.kode3);
        flabtn = (FlatButton) rootView.findViewById(R.id.btnMasukDetails);
        /////////////////////////////////////////////////////////////////////////////
        // MENGAMBIL NILAI CALENDER
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        jam = c.get(Calendar.HOUR);
        menit = c.get(Calendar.MINUTE);
        detik = c.get(Calendar.SECOND);

        Date today = new Date();






        //////////////////////////////////////////////////////////////////////////////////////////
        // setCustomList yang baru
        /////////////////////////////////////////////////////////////////////////////////////////



        customerList = new ArrayList<Customer>();
        customerList = db.getAllDataCustomerListViewArrayList();
        CustomerAdapter = new MyCustomAdapter(getActivity(), R.layout.customer_layout, customerList);
        lv.setAdapter(CustomerAdapter);
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Customer custom = (Customer) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(),
                        custom.getCode(), Toast.LENGTH_SHORT).show();
                custid.setText(custom.getCode().trim());
                vSpinner = db.getAllToSpinner(custom.getCode().trim());
                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, vSpinner);
                // Drop down layout style - list view with radio button
                dataAdapter
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                spinner.setAdapter(dataAdapter);
            }
        });

        custid.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CustomerAdapter.getFilter().filter(s.toString());
            }
        });








        //If you print Date, you will get un formatted output
        System.out.println("Today is : " + today);

        //formatting date in Java using SimpleDateFormat
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = DATE_FORMAT.format(today);
        TahunStr = String.format("%02d", year);
        BulanStr = String.format("%02d", month + 1);
        HariStr = String.format("%02d", day);
        JamStr = String.format("%02d", jam);
        MenitStr = String.format("%02d", menit);
        DetikStr = String.format("%02d", detik);
        //SimpleDateFormat DateForm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        //String tanggalkirim = DateForm.format(today);


        //////////////////////////////////////////////////////////////////////////
        // Mengambil data dari editText
        /////////////////////////////////////////////////////////////////////////
        /*custid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "ini edittext", Toast.LENGTH_LONG).show();
            }
        });*/


        penggabungan = "OP" + sKodeArea + date + sKodeSales;
        OrderPenjualan.setEnabled(false);
       // OrderPenjualan.setText(penggabungan);

        Toast.makeText(getActivity(), "" + String.format("%02d", jam), Toast.LENGTH_LONG).show();


        List<String> list = new ArrayList<String>();
        list.add("TO");
        list.add("Canvas");
        list.add("Ds");
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertocanvasds.setAdapter(dataAdapter);

        ////////////////////////////////////////////////////////////////////////////////////////////
        // List Data Lama
        ////////////////////////////////////////////////////////////////////////////////////////////
        /*
        ListAdapter adapter;
        adapter = new SimpleAdapter(getActivity(), contactlist, R.layout.list_item, new
        String[]{"nama", "email", "phone"} , new int []{R.id.name, R.id.email, R.id.mobile});
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // getting values from selected ListItem
                String
                        name = ((TextView) view.findViewById(R.id.name))
                        .getText().toString();
                nama = ((TextView) view.findViewById(R.id.email))
                        .getText().toString();
                alamat = ((TextView) view.findViewById(R.id.mobile))
                        .getText().toString();

                custid.setText(name);
                vSpinner = db.getAllToSpinner(name);
                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, vSpinner);
                // Drop down layout style - list view with radio button
                dataAdapter
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                spinner.setAdapter(dataAdapter);

            }
        });
*/


        db.tutupKoneksi();

        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());


        //////////////////////////////////////////////////////////////////////////////////////
        // MENGAMBIL NILAI DARI BUTTON
        /////////////////////////////////////////////////////////////////////////////////////

        flabtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String nSpinner1 = String.valueOf(spinnertocanvasds.getSelectedItem());
                dbTemp = new SqliteManagerTemp(getActivity());
                dbTemp.bukaKoneksi();
                Date today = new Date();
                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
                String date = DATE_FORMAT.format(today);

                penggabungan = "OP" + sKodeArea + date + sKodeSales;
                if (!sCustid.equals("")) {
                    if (nSpinner1.equals("TO")) {
                        nSpinner1 = "1";
                    } else if (nSpinner1.equals("Canvas")) {
                        nSpinner1 = "2";
                    } else if (nSpinner1.equals("Ds")) {
                        nSpinner1 = "3";
                    }

                    if (!TextUtils.isEmpty(Catatan.getText().toString())) {
                        catatan = Catatan.getText().toString();
                    } else {
                        catatan = "-";
                    }

                    if (!TextUtils.isEmpty(NoPoCustomer.getText().toString())) {
                        orderpo = NoPoCustomer.getText().toString();
                    } else {
                        orderpo = "-";
                    }


                    dbDetails = new SqliteManagerDetails(getActivity());
                    dbDetails.bukaKoneksi();

                    String parts3[] = sCustid.split("\\/", -1);
                    // long nilai=  dbDetails.insertData(dbDetails.InsertDataHeader(nSpinner1, penggabungan, NoPoCustomer.getText().toString(),
                    //         Catatan.getText().toString(), custid.getText().toString(), parts3[0] , nama, alamat));

                    ////////////////////////////////////////////////////////////////////////////////////
                    // No faktur, kode customer, kode trans, tgl, tgl-jam kirim, kode sales, reff, grss,
                    // ppn, diskon pertama, diskon kedua, diskon ketiga, nett, lat, lon, jlhrec, imei,
                    // orderno, ket, group, top, canvas
                    ////////////////////////////////////////////////////////////////////////////////////

                    TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                    String device_id = tm.getDeviceId();

                    long nilai = dbDetails.insertData(dbDetails.InsertDataHeader(penggabungan, custid.getText().toString(),
                            "1", HariStr + "-" + BulanStr + "-" + TahunStr, "tanggal jam", sKodeSales, "-", "0", "0",
                            "0", "0", "0", "0", "lat", "lon", "0", device_id, orderpo, catatan, parts3[0],
                            parts3[1], nSpinner1));
                    dbDetails.tutupKoneksi();

                    long temp = dbTemp.insertData(dbTemp.ambilData(penggabungan, sCustid));
/*                long nilai =dbDetails.insertData(dbDetails.InsertDataHeader(penggabungan, "d",
                        "1", "d", "d" , "d", "reff", "grss", "ppn",
                        "d1", "d2", "d3", "nett", "lat", "lon", "jlhrec", "d", "orderno", "d", "d",
                        "d", "d"));
*/
                    Toast.makeText(getActivity(), "ini nilai dan temp =" + nilai + " " + temp, Toast.LENGTH_SHORT).show();

                    dbTemp.tutupKoneksi();

                    Intent i = new Intent(getActivity(), Details.class);
                    i.putExtra("orderpenjualan", penggabungan);
                    i.putExtra("nopocustomer", NoPoCustomer.getText().toString());
                    i.putExtra("custid", custid.getText().toString());
                    i.putExtra("pilihandistributor", sCustid);
                    i.putExtra("catatan", Catatan.getText().toString());
                    i.putExtra("myBoolean", true);
                    NoPoCustomer.setText("");
                    custid.setText("");
                    Catatan.setText("");
                    vSpinner.clear();
                    spinner.setAdapter(null);
                    startActivity(i);
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), "Pilih Customer", Toast.LENGTH_LONG).show();
                }
            }
        });


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Date today = new Date();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = DATE_FORMAT.format(today);
        penggabungan = "OP" + sKodeArea + date + sKodeSales;
        OrderPenjualan.setEnabled(false);
        //OrderPenjualan.setText(penggabungan);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    // MENGAMBIL NILAI SPINNER 2
    // String.valueOf(spinner1.getSelectedItem());
    ///////////////////////////////////////////////////////////////////////////////
    class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView parent, View view, int pos, long id) {
            Toast.makeText(getActivity(), "distributor: " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
            sCustid = parent.getItemAtPosition(pos).toString();
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

                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.list_item, null);

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
            holder.continent.setText(country.getContinent());
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