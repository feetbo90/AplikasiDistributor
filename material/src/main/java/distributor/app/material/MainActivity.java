package distributor.app.material;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import distributor.app.material.DownloadCompressParsing.JsonParsing;

public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Fragment fragment = null;
    static String sKodeArea, sKodeAktivasi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        new JsonParsing(MainActivity.this);
        if (extras != null) {
            sKodeArea = extras.getString("kodearea");
            sKodeAktivasi = extras.getString("kodesales");
            //Log.d("KodeArea dan KodeAktivasi", sKodeArea + " " + sKodeAktivasi);

        }
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        ///////////////////////////////////////////////////////////////////////
        // menyeleksi jika salah satu tab di klik
        //////////////////////////////////////////////////////////////////////
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
               switch (tab.getPosition())
                {
                    // pembuatan yang tidak perlu
                    case 0:
                        Bundle bundle = new Bundle();
                        bundle.putString("kodearea", sKodeArea);
                        bundle.putString("kodesales", sKodeAktivasi);
                        fragment = new TabSatu();
                        fragment.setArguments(bundle);

                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new TabDua(), "Order");
        adapter.addFragment(new TabTransaksi() , "Masih Transaksi" );
        adapter.addFragment(new TabPendingKirim(), "Pending Kirim");
        adapter.addFragment(new TabTelahTerkirim(), "Telah Terkirim");
        adapter.addFragment(new Routing(), "Routing");
        adapter.addFragment(new TabSatu(), "Sinkronisasi");
        //adapter.addFragment(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) { return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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

    //---used for updating the UI on the main activity---
   /* public static Handler UIupdater = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int numOfBytesReceived = msg.arg1;
            byte[] buffer = (byte[]) msg.obj;

            //---convert the entire byte array to string---
            String strReceived = new String(buffer);

            //---extract only the actual string received---
            strReceived = strReceived.substring(
                    0, numOfBytesReceived);

            Toast.makeText(MainActivity.this, "" + strReceived, Toast.LENGTH_SHORT).show();

        }
    };*/

}
