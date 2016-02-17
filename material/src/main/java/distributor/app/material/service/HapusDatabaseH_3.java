package distributor.app.material.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import distributor.app.material.sqlite.SqliteManagerDetails;
import distributor.app.material.sqlite.SqliteManagerPendingKirim;
import distributor.app.material.sqlite.SqliteManagerTelahTerkirim;

/**
 * Created by feetbo on 1/21/2016.
 */
public class HapusDatabaseH_3 extends Service {

    private Timer timer = new Timer();
    SqliteManagerDetails dbDetails;
    SqliteManagerPendingKirim dbPendingKirim;
    SqliteManagerTelahTerkirim dbTelahTerkirim;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        _startService();
    }

    private void _startService() {
        long opIntervalUpdate = 6000;
        if(opIntervalUpdate != 0)
        {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    checkStatus();
                }
            },0, opIntervalUpdate);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        _shutdownService();
    }

    private void _shutdownService() {
        if (timer != null)
            timer.cancel();


        Log.i(getClass().getSimpleName(), "Timer stopped!!!");
    }

    private void checkStatus()
    {
        Log.d("service", "Berhasil service 2");
        //getRequest(Url);
        dbDetails = new SqliteManagerDetails(HapusDatabaseH_3.this);
        dbDetails.bukaKoneksi();
        dbDetails.hapusHeader();
        dbDetails.hapusDetail();

        dbDetails.tutupKoneksi();

        dbPendingKirim = new SqliteManagerPendingKirim(HapusDatabaseH_3.this);
        dbPendingKirim.bukaKoneksi();
        dbPendingKirim.hapusHeader();
        dbPendingKirim.hapusDetail();
        dbPendingKirim.tutupKoneksi();

        dbTelahTerkirim = new SqliteManagerTelahTerkirim(HapusDatabaseH_3.this);
        dbTelahTerkirim.bukaKoneksi();
        dbTelahTerkirim.hapusHeader();
        dbTelahTerkirim.hapusDetail();
        dbTelahTerkirim.tutupKoneksi();

    }

}
