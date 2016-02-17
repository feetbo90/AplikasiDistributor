package distributor.app.material;

import android.support.v7.app.AppCompatActivity;



        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.location.LocationListener;
        import android.location.LocationManager;
        import android.os.Bundle;
        import android.widget.Button;

import distributor.app.material.DownloadCompressParsing.JsonParsing;

public class TampilanAwal extends AppCompatActivity {
    Button image;
    Button jenis;
    private LocationManager locationManager = null;
    @SuppressWarnings("unused")
    private LocationListener locationListener;
    // private Location location;
    private String bestProvider = null;
    private double userLon = 0;
    private double userLat = 0;

    private static final long JARAK_MINIMAL_UNTUK_UPDATE = 1; // dalam meter
    private static final long WAKTU_MINIMUM_UNTUK_UPDATE = 1000; // dalam detik
    SharedPreferences prefLocation;
    protected boolean _active = true;
    protected int _splashTime = 5000;
    static String sKode_Area = "";
    static String sKode_Aktivasi = "";
    Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cariberdasarkan);
        mContext = this;
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            sKode_Area = extras.getString("kodearea");
            sKode_Aktivasi = extras.getString("kodesales");
            //Log.d("KodeArea dan KodeAktivasi", sKodeArea + " " + sKodeAktivasi);
        }
            // thread for displaying the SplashScreen


    }

    @Override
    public void onResume()
    {
        super.onResume();
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while(_active && (waited < _splashTime)) {
                        sleep(100);
                        if(_active) {
                            waited += 100;
                        }
                    }
                } catch(InterruptedException e) {
                    // do nothing
                } finally {

                    Intent newIntent=new Intent(TampilanAwal.this, MainActivity.class);
                    newIntent.putExtra("kodearea", sKode_Area);
                    newIntent.putExtra("kodesales", sKode_Aktivasi);
                    startActivity(newIntent);
                }
            }
        };
        splashTread.start();

    }



}
