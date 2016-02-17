package ibel.app.applikasipertanian;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cengalabs.flatui.views.FlatButton;

/**
 * Created by feetbo on 1/26/2016.
 */
public class Main extends Activity {

    FlatButton btnscan, btnpengisian;

    @Override
    public void onCreate(Bundle savedInstancestate)
    {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.formawal);
        btnscan = (FlatButton)findViewById(R.id.btnscan);
        btnpengisian = (FlatButton)findViewById(R.id.btnpengisian);


        btnscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main.this, MainActivity.class);
                startActivity(i);

            }
        });

        btnpengisian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main.this, Register.class);
                startActivity(i);
            }
        });


    }
}
