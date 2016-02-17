package ibel.app.applikasipertanian;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import java.util.List;

import ibel.app.applikasipertanian.model.Contact;
import ibel.app.applikasipertanian.sqlite.SqliteManager;

/**
 * Created by feetbo on 1/24/2016.
 */
public class HasilQRCode extends AppCompatActivity{

    String nama="", pekerjaan, alamat, username, password;
private Context context;
private String hasilscan;
    SqliteManager sqliteDB;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Bundle extras = getIntent().getExtras();
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
            hasilscan = extras.getString("hasilscan");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

            context = this;
            sqliteDB = new SqliteManager( HasilQRCode.this);
            sqliteDB.bukaKoneksi();
            List<Contact> contacts = sqliteDB.getAllContacts(hasilscan);
            for (Contact cn : contacts) {
                String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
                // Writing Contacts to log

                nama = cn.getKode_Sales();
                pekerjaan = cn.getName();
                alamat = cn.getPhoneNumber();
                username = cn.getKode_Area();
                password = cn.getCRC();
                Log.d("Name: ", log);

            }
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (nama.equals(hasilscan))
                        Snackbar.make(view, "nama sesuai : " + nama, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    else {
                        Snackbar.make(view, "nama tidak sesuai", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            });


        }

}
