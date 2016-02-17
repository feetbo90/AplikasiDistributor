package distributor.app.material;

/**
 * Created by feetbo on 10/17/2015.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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


import java.util.Calendar;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


import distributor.app.material.distributor.app.material.date.tanggal;
import distributor.app.material.model.Customer;
import distributor.app.material.model.Header;
import distributor.app.material.sqlite.SqliteManagerDetails;

import distributor.app.material.sqlite.SqliteManagerTelahTerkirim;


public class TabTelahTerkirim extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    FlatButton flabtn;
    ArrayList<HashMap<String, String>> contactlist;
    List<String> vSpinner;
    SqliteManagerTelahTerkirim dbPending;
    SqliteManagerDetails dbDetails;
    EditText custid, OrderPenjualan, NoPoCustomer, Catatan;
    static String sKodeArea, sKodeSales;
    ListView lv;
    Spinner spinner, spinnertocanvasds;


    ////////////////////////////////////////////////////////////////////////////
    // PENGAMBILAN NILAI CALENDAR
    private int year, jam, menit, detik;
    private int month;
    private int day;
    private String TahunStr, JamStr, MenitStr, DetikStr, BulanStr, HariStr;
    ///////////////////////////////////////////////////////////////////////////

    String orderpo, catatan;
    static String nama, alamat;
    private SwipeRefreshLayout swipeRefreshLayout;
    // Data Customer
    MyCustomAdapter CustomerAdapter = null;
    Calendar c;

    static String sCustid = "", penggabungan;

    static ArrayList<Header> customerList;
    public TabTelahTerkirim() {
        // Required empty public constructor
    }
    Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        mContext = this;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragement_transaksi);
        Toolbar tool = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        contactlist = new ArrayList<HashMap<String, String>>();
        dbPending = new SqliteManagerTelahTerkirim(mContext);
        dbPending.bukaKoneksi();
        lv = (ListView) findViewById(R.id.list);
        ////////////////////////////////////////////////////////////////////////////////
        // MENGAMBIL KODE AREA DAN KODE SALES
        ///////////////////////////////////////////////////////////////////////////////
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        //////////////////////////////////////////////////////////////////////////////////////////
        // setCustomList yang baru
        /////////////////////////////////////////////////////////////////////////////////////////


        customerList = new ArrayList<Header>();
        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        fetchList();

                                    }
                                }
        );

        CustomerAdapter = new MyCustomAdapter(mContext, R.layout.customer_layout, customerList);
        lv.setAdapter(CustomerAdapter);
        lv.setTextFilterEnabled(true);
        dbPending.tutupKoneksi();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Header custom = (Header) parent.getItemAtPosition(position);
                Toast.makeText(mContext,
                        custom.getCode() + custom.getNoPOcustomer() + custom.getCatatan(), Toast.LENGTH_SHORT).show();
                //custid.setText(custom.getCode().trim());

                Intent i = new Intent(mContext, TelahTerkirim.class);
                /*i.putExtra("faktur", custom.getCode());
                i.putExtra("tanggal", custom.getTanggal());
                i.putExtra("custid", custom.getKustomer());
                i.putExtra("nopocustomer", custom.getNoPOcustomer());
                i.putExtra("custid", custom.getKustomer());
                //i.putExtra("pilihandistributor", custom.getPilihandistributor());
                i.putExtra("catatan", custom.getCatatan());
                i.putExtra("myBoolean", false);
                */

                i.putExtra("orderpenjualan", custom.getCode());
                i.putExtra("latitude", custom.getNoPOcustomer());
                i.putExtra("custid", custom.getKustomer());
                //i.putExtra("pilihandistributor", custom.getPilihandistributor());
                i.putExtra("ppn", custom.getCatatan());
                i.putExtra("longitude", custom.getTanggal());
                startActivity(i);

            }
        });

    }

    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        contactlist = new ArrayList<HashMap<String, String>>();
        View rootView = inflater.inflate(R.layout.fragement_transaksi, container, false);
        // pertama buka koneksi untuk membuat list
        dbPending = new SqliteManagerTelahTerkirim(getActivity());
        dbPending.bukaKoneksi();
        lv = (ListView) rootView.findViewById(R.id.list);
        ////////////////////////////////////////////////////////////////////////////////
        // MENGAMBIL KODE AREA DAN KODE SALES
        ///////////////////////////////////////////////////////////////////////////////
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);






        //////////////////////////////////////////////////////////////////////////////////////////
        // setCustomList yang baru
        /////////////////////////////////////////////////////////////////////////////////////////


        customerList = new ArrayList<Header>();
        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
      /*
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        fetchList();

                                    }
                                }
        );

        CustomerAdapter = new MyCustomAdapter(getActivity(), R.layout.customer_layout, customerList);
        lv.setAdapter(CustomerAdapter);

        lv.setTextFilterEnabled(true);
        dbPending.tutupKoneksi();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Header custom = (Header) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(),
                        custom.getCode() + custom.getNoPOcustomer() + custom.getCatatan(), Toast.LENGTH_SHORT).show();
                //custid.setText(custom.getCode().trim());

                Intent i = new Intent(getActivity(), TelahTerkirim.class);
                /*i.putExtra("faktur", custom.getCode());
                i.putExtra("tanggal", custom.getTanggal());
                i.putExtra("custid", custom.getKustomer());
                i.putExtra("nopocustomer", custom.getNoPOcustomer());
                i.putExtra("custid", custom.getKustomer());
                //i.putExtra("pilihandistributor", custom.getPilihandistributor());
                i.putExtra("catatan", custom.getCatatan());
                i.putExtra("myBoolean", false);
                */
/*
                i.putExtra("orderpenjualan", custom.getCode());
                i.putExtra("latitude", custom.getNoPOcustomer());
                i.putExtra("custid", custom.getKustomer());
                //i.putExtra("pilihandistributor", custom.getPilihandistributor());
                i.putExtra("ppn", custom.getCatatan());
                i.putExtra("longitude", custom.getTanggal());
                startActivity(i);
                getActivity().finish();
            }
        });












/*
                    long nilai = dbDetails.insertData(dbDetails.InsertDataHeader(penggabungan, custid.getText().toString(),
                            "1", HariStr + "-" + BulanStr + "-" + TahunStr, "tanggal jam", sKodeSales, "-", "grss", "ppn",
                            "d1", "d2", "d3", "nett", "lat", "lon", "jlhrec", device_id, orderpo, catatan, parts3[0],
                            parts3[1], nSpinner1));
*/
/*
        return rootView;
    }

*/
    public void fetchList(){
        swipeRefreshLayout.setRefreshing(true);
        customerList = dbPending.getAllDataHeaderArrayList();
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onRefresh() {dbPending.bukaKoneksi();
        customerList.clear();
        fetchList();
        dbPending.tutupKoneksi();

        CustomerAdapter = new MyCustomAdapter(mContext, R.layout.customer_layout, customerList);

        lv.setAdapter(CustomerAdapter);
        lv.setTextFilterEnabled(true);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    // MENGAMBIL NILAI SPINNER 2
    // String.valueOf(spinner1.getSelectedItem());
    ///////////////////////////////////////////////////////////////////////////////
    class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView parent, View view, int pos, long id) {
           // Toast.makeText(getActivity(), "distributor: " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
            sCustid = parent.getItemAtPosition(pos).toString();
        }

        @Override
        public void onNothingSelected(AdapterView parent) {
        }
    }


    private class MyCustomAdapter extends ArrayAdapter<Header> {

        private ArrayList<Header> originalList;
        private ArrayList<Header> countryList;
        private CountryFilter filter;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Header> countryList) {
            super(context, textViewResourceId, countryList);
            this.countryList = new ArrayList<Header>();
            this.countryList.addAll(countryList);
            this.originalList = new ArrayList<Header>();
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
            TextView region,total;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));
            if (convertView == null) {

                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.list_item, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.name);
                holder.name = (TextView) convertView.findViewById(R.id.email);
                holder.continent = (TextView) convertView.findViewById(R.id.mobile);
                holder.region = (TextView) convertView.findViewById(R.id.region);
                holder.total = (TextView) convertView.findViewById(R.id.total);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Header country = countryList.get(position);
            tanggal tgl = new tanggal();
            holder.code.setText(country.getCode());
            holder.name.setText(country.getKustomer());
            holder.continent.setText(tgl.getTanggalKonver(country.getTanggal()) + ","+ country.getTanggal());
            holder.region.setText("Disc Y :" +country.getCatatan());
            holder.total.setText("Total :" +country.getKodeSales());
            return convertView;

        }

        private class CountryFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                constraint = constraint.toString().toLowerCase();
                FilterResults result = new FilterResults();
                if (constraint != null && constraint.toString().length() > 0) {
                    ArrayList<Header> filteredItems = new ArrayList<Header>();

                    for (int i = 0, l = originalList.size(); i < l; i++) {
                        Header country = originalList.get(i);
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

                countryList = (ArrayList<Header>) results.values;
                notifyDataSetChanged();
                clear();
                for (int i = 0, l = countryList.size(); i < l; i++)
                    add(countryList.get(i));
                notifyDataSetInvalidated();
            }
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        dbPending.bukaKoneksi();
        customerList.clear();
        fetchList();
        dbPending.tutupKoneksi();

        CustomerAdapter = new MyCustomAdapter(mContext, R.layout.customer_layout, customerList);

        lv.setAdapter(CustomerAdapter);
        lv.setTextFilterEnabled(true);

    }

    @Override
    public void onPause() {
        super.onPause();
        //dbPending.tutupKoneksi();
    }

}