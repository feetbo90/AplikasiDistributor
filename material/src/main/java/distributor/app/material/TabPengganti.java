package distributor.app.material;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cengalabs.flatui.views.FlatButton;



public class TabPengganti extends Fragment {

    FlatButton masihtransaksi, pendingkirim, telahterkirim;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.tabpengganti, container, false);
        // pertama buka koneksi untuk membuat list
        masihtransaksi = (FlatButton)rootView.findViewById(R.id.masihtransaksi);
        pendingkirim = (FlatButton)rootView.findViewById(R.id.pendingkirim);
        telahterkirim = (FlatButton)rootView.findViewById(R.id.telahterkirim);

        masihtransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TabTransaksi.class);
                startActivity(i);
            }
        });

        pendingkirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (getActivity(), TabPendingKirim.class);
                startActivity(i);
            }
        });

        telahterkirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TabTelahTerkirim.class);
                startActivity(i);
            }
        });

        return rootView;

    }

}
