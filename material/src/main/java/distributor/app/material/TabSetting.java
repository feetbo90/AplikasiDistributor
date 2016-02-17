package distributor.app.material;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class TabSetting extends Activity {



    SharedPreferences prefLocations;
    SharedPreferences.Editor ed ;
    EditText edit;
    Button btn;
    static String sIPSetting;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmentsetting);
        // Inflate the layout for this fragment



        ////////////////////////////////////////////////////////////////////////////////////////
        // Penyimpanan ke SharedPreferences
        ///////////////////////////////////////////////////////////////////////////////////////
        prefLocations = getSharedPreferences("tbIP", Context.MODE_PRIVATE);
        sIPSetting = prefLocations.getString("ipsetting", "");

        edit = (EditText)findViewById(R.id.editSetting);
        btn = (Button) findViewById(R.id.button);
        edit.setText(sIPSetting);
        ed = prefLocations.edit();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ed.putString("ipsetting", edit.getText().toString().trim());
                ed.commit();
                Toast.makeText(TabSetting.this, "Berhasil Disimpan" , Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }







}