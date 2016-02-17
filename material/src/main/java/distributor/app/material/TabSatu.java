package distributor.app.material;

/*
 * Created by feetbo on 10/17/2015.
 */

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.cengalabs.flatui.views.FlatButton;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import distributor.app.material.DownloadCompressParsing.DecompressZip;
import distributor.app.material.DownloadCompressParsing.DownloadFile;
import distributor.app.material.DownloadCompressParsing.ExternalStorage;
import distributor.app.material.DownloadCompressParsing.JsonParsing;
import distributor.app.material.model.context;
import distributor.app.material.socket.CommsThread;
import distributor.app.material.sqlite.SqliteManagerData;
import dmax.dialog.SpotsDialog;


public class TabSatu extends Fragment{

//    private final int APP_THEME = R.array.grass;
    static FlatButton downloadProduk, downloadCustomer, dataPlafond, versiData;
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    static String sKodeArea, sKodeSales;
    static File zipDir, zipFile, outputDir;
    //---socket---
    InetAddress serverAddress;
    static Socket socket;
    CommsThread commsThread;
    static TextView text;
    static SharedPreferences prefLocations, prefLocations2, prefIp, prefTanggal;
    static SharedPreferences.Editor ed, ed2 ;
    static SpotsDialog mProgressDialog = null;
    static ProgressDialog dialog = null;

    // -- nama- nama variabel key berupa version1, version2, version3 untuk di SharedPreferences--
    public static final String FIELD_VERSION1 = "version1";
    public static final String FIELD_VERSION2 = "version2";
    public static final String FIELD_VERSION3 = "version3";
    public static final String FIELD_AKTIF_PRODUK = "aktifproduk";
    public static final String FIELD_AKTIF_CUSTOMER = "aktifcustomer";
    public static final String FIELD_AKTIF_PLAFON = "aktifplafon";
    public static final String FIELD_KODE_AREA = "kode_area";
    public static final String FIELD_KODE_AKTIVASI = "kode_aktivasi";

    public static context cont;
    static String snilai, sRequest, sIPSetting;
  //  static int nilai=0;
    public static String  kodearea, kodeaktivasi;
    public static ProgressDialog pDialog;
    public static int versionProduk, versionPlafond, versionCustomer;
    public static String sVersiData = "";
   // public static boolean aktifproduk, aktifcustomer, aktifplafond;
    public static DecompressZip com;

    static Date today;
    static int part4, part5, part6;
    static SimpleDateFormat DATE_FORMAT;
    static String date;
    static int tanggalsekarang;





    public TabSatu() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /* version1 = prefLocations2.getString(FIELD_VERSION1, "");
        version2 = prefLocations2.getString(FIELD_VERSION2, "");
        version3 = prefLocations2.getString(FIELD_VERSION3, "");
        */

        if(mHandler == null)
            mHandler = new MyHandler(this);
        else
            mHandler.setTarget(this);



        ////////////////////////////////////////////////////////////////////////////////
        // MENGAMBIL KODE AREA DAN KODE SALES
        ///////////////////////////////////////////////////////////////////////////////
        
        com = new DecompressZip();

        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            sKodeArea = extras.getString("kodearea");
            sKodeSales = extras.getString("kodesales");
            Log.d("fragment", sKodeArea + " " + sKodeSales);

        }
        //Log.d("KodeArea dan KodeAktivasi", sKodeArea + " " + sKodeAktivasi);`

        View rootView = inflater.inflate(R.layout.fragment_one, container, false);


        downloadProduk = (FlatButton)rootView.findViewById(R.id.dataproduk);
        downloadCustomer = (FlatButton)rootView.findViewById(R.id.datacustomer);
        dataPlafond = (FlatButton)rootView.findViewById(R.id.dataplafond);
        versiData = (FlatButton)rootView.findViewById(R.id.versidata);

        // opsi yang false yang benar



        prefLocations = rootView.getContext().getSharedPreferences("tbUser", Context.MODE_PRIVATE);
        prefIp = rootView.getContext().getSharedPreferences("tbIP" , Context.MODE_PRIVATE );
        prefTanggal = rootView.getContext().getSharedPreferences("tbTanggal" , Context.MODE_PRIVATE );
        prefLocations2 = rootView.getContext().getSharedPreferences("tbVersi", Context.MODE_PRIVATE);

       /* part4 = prefLocations2.getInt(FIELD_VERSION1, 0);
        part5 = prefLocations2.getInt(FIELD_VERSION2, 0);
        part6 = prefLocations2.getInt(FIELD_VERSION3, 0);
*/
        if(prefLocations2.getBoolean(FIELD_AKTIF_PRODUK, false))
        {
            downloadProduk.setEnabled(false);

        }else downloadProduk.setEnabled(true);

        if(prefLocations2.getBoolean(FIELD_AKTIF_CUSTOMER, false))
        {
            downloadCustomer.setEnabled(false);

        }else downloadCustomer.setEnabled(true);

        if(prefLocations2.getBoolean(FIELD_AKTIF_PLAFON, false))
        {
            dataPlafond.setEnabled(false);

        }else dataPlafond.setEnabled(true);

        ///////////////////////////
        // memasukkan context ke JsonParsing
        //////////////////////////
        cont = new context();
        cont.setContext(rootView.getContext());
        JsonParsing json = new JsonParsing();
        json.setmContext(rootView.getContext());

        ed = prefLocations.edit();

        sIPSetting = prefIp.getString("ipsetting", "");
        kodearea = prefLocations.getString(FIELD_KODE_AREA, "");
        kodeaktivasi = prefLocations.getString(FIELD_KODE_AKTIVASI, "");

        text = (TextView)rootView.findViewById(R.id.text);


                versiData.setOnClickListener(new View.OnClickListener(){
                    @Override
                public void onClick(View v){
                        sVersiData = "VersiData";
                        new CreateCommThreadTask().execute();
                    }
                });
                downloadProduk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sRequest = "dp";


                        com.setParsing(sRequest);
                        new RequestTask().execute("");

                    }
                });

                downloadCustomer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sRequest = "dc";

                        com.setParsing(sRequest);
                        new RequestTask().execute("");//downloadCustomer.setEnabled(false);
                    }
                });

                dataPlafond.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sRequest = "dh";

                        com.setParsing(sRequest);

                        new RequestTask().execute("");//dataPlafond.setEnabled(false);
                    }
                });

        return rootView;

    }



    // sinkron 120101K01

    //---used for updating the UI on the main activity---
    public static MyHandler mHandler;// = new MyHandler(this);
    public static class MyHandler extends Handler{
        private  WeakReference<Fragment> mFragmentWeakReference;
        public MyHandler(Fragment fragment){
            mFragmentWeakReference = new WeakReference<>(fragment);

        }

        public void setTarget(Fragment target) {
            mFragmentWeakReference.clear();
            mFragmentWeakReference = new WeakReference<>(target);
        }

        @Override
        public void handleMessage(Message msg){
            Fragment fragment = mFragmentWeakReference.get();
            if (fragment != null) {
                // ...
                int numOfBytesReceived = msg.arg1;
                byte[] buffer = (byte[]) msg.obj;

                //---convert the entire byte array to string---
                String strReceived = new String(buffer);
                //prefLocations2.getString(FIELD_VERSION1, Context.MODE_PRIVATE);

                prefLocations2 = cont.nilai().getSharedPreferences("tbVersi", Context.MODE_PRIVATE);
                ed2 = prefLocations2.edit();
                //---extract only the actual string received---
                strReceived = strReceived.substring(
                        0, numOfBytesReceived);
                Log.d("server", strReceived);
                //strReceived = strReceived.trim();
                if(strReceived.equals("Selamat datang di Server"))
                {
                    //text.setText("benar");
                }else if(strReceived.equals("-Koneksi ditutup Server-"))
                {
                    Log.d("tes", "tes");
                }else if(strReceived.contains("Ready")) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        Log.d("Sockets", e.getLocalizedMessage());
                    }
                    Log.d("ini IP" , sIPSetting);
                    new DownloadTask().execute("http://"+sIPSetting.trim() + "/media/downloads/" +sRequest + sKodeArea+sKodeSales+".zip");
                    Log.d("ready server", strReceived);
                }else if(!(strReceived.contains("|")))
                {
                   // pDialog.dismiss();
                }
                else if(strReceived.contains("|") ){

                    // close socket jika sudah memenuhi

                    try {
                        socket.close();
                    } catch (IOException e) {
                        Log.d("Sockets", e.getLocalizedMessage());
                    }

                    String parts[] = strReceived.split(sKodeSales);
                    String parts2 = parts[1];
                    String parts3[] = parts2.split("\\|",-1);
                    String parts4 = parts3[0];
                    String parts5 = parts3[1];
                    String parts6 = parts3[2];
                    Log.d("server", parts4 + " " + parts5 + " " + parts6);
                    //text.setText(parts4 + parts5 + parts6);
//              mengambil nilai versi produk , cusotmer dan plafond sebelum di set atau sudah di set
                    versionProduk = prefLocations2.getInt(FIELD_VERSION1, 0);
                    versionCustomer = prefLocations2.getInt(FIELD_VERSION2, 0);
                    versionPlafond = prefLocations2.getInt(FIELD_VERSION3, 0);

                    Log.d("server", versionProduk + " " + versionCustomer +" " + versionPlafond);

                    part4 = Integer.parseInt(parts4.trim());
                    part5 = Integer.parseInt(parts5.trim());
                    part6 = Integer.parseInt(parts6.trim());

                    // membandingkan versi produk yang di SharedPreferences dan di socket
                    if((part4 > versionProduk) || (part5 > versionCustomer) || (part6 > versionPlafond))
                    {
                        // kalau versi lama lebih kecil dari versi socket maka memasukkan ke versi baru untuk
                        // di SharedPreferences.
                        if(part4 > versionProduk)
                        {


                            ed2.putBoolean(FIELD_AKTIF_PRODUK, false);
                            downloadProduk.setEnabled(true);
                        }
                        else {
                            downloadProduk.setEnabled(false);
                            ed2.putBoolean(FIELD_AKTIF_PRODUK, true);
                        }

                        if(part5 > versionCustomer)
                        {
                            ed2.putBoolean(FIELD_AKTIF_CUSTOMER, false);
                            downloadCustomer.setEnabled(true);
                        }
                        else{
                            downloadCustomer.setEnabled(false);
                            ed2.putBoolean(FIELD_AKTIF_CUSTOMER, true);
                        }

                        if(part6 > versionPlafond)
                        {
                            ed2.putBoolean(FIELD_AKTIF_PLAFON, false);
                            dataPlafond.setEnabled(true);
                        }
                        else{
                            dataPlafond.setEnabled(false);
                            ed2.putBoolean(FIELD_AKTIF_PLAFON, true);

                        }
                        ed2.commit();
                        Log.d("server", "masuk dia kok");

                    }/*else{

                    // opsi false yang benar
                    downloadProduk.setEnabled(false);
                    downloadCustomer.setEnabled(false);
                    dataPlafond.setEnabled(false);
                }*/

                }

            /*            text.setText(
                    "batas" +strReceived + "batas");*/


            }


        }
    }



/*
    public static  Handler UIupdater = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int numOfBytesReceived = msg.arg1;
            byte[] buffer = (byte[]) msg.obj;

            //---convert the entire byte array to string---
            String strReceived = new String(buffer);
            //prefLocations2.getString(FIELD_VERSION1, Context.MODE_PRIVATE);

            prefLocations2 = cont.nilai().getSharedPreferences("tbVersi", Context.MODE_PRIVATE);
            ed2 = prefLocations2.edit();
            //---extract only the actual string received---
            strReceived = strReceived.substring(
                    0, numOfBytesReceived);
            Log.d("server", strReceived);
            //strReceived = strReceived.trim();
            if(strReceived.equals("Selamat datang di Server"))
            {
                text.setText("benar");
            }else if(strReceived.equals("-Koneksi ditutup Server-"))
            {

            }else if(strReceived.contains("Ready")) {
                try {
                    socket.close();
                } catch (IOException e) {
                    Log.d("Sockets", e.getLocalizedMessage());
                }
                Log.d("ini IP" , sIPSetting);
                new DownloadTask().execute("http://"+sIPSetting.trim() + "/media/downloads/" +sRequest + sKodeArea+sKodeSales+".zip");
                Log.d("ready server", strReceived);
            }else if(!(strReceived.contains("|")))
            {
                pDialog.dismiss();
            }
            else if(strReceived.contains("|") ){

                // close socket jika sudah memenuhi

                try {
                    socket.close();
                } catch (IOException e) {
                    Log.d("Sockets", e.getLocalizedMessage());
                }

                String parts[] = strReceived.split(sKodeSales);
                String parts2 = parts[1];
                String parts3[] = parts2.split("\\|",-1);
                String parts4 = parts3[0];
                String parts5 = parts3[1];
                String parts6 = parts3[2];
                Log.d("server", parts4 + " " + parts5 + " " + parts6);
                text.setText(parts4 + parts5 + parts6);
//              mengambil nilai versi produk , cusotmer dan plafond sebelum di set atau sudah di set
                versionProduk = prefLocations2.getInt(FIELD_VERSION1, 0);
                versionCustomer = prefLocations2.getInt(FIELD_VERSION2, 0);
                versionPlafond = prefLocations2.getInt(FIELD_VERSION3, 0);

                Log.d("server", versionProduk + " " + versionCustomer +" " + versionPlafond);

                part4 = Integer.parseInt(parts4.trim());
                part5 = Integer.parseInt(parts5.trim());
                part6 = Integer.parseInt(parts6.trim());

                // membandingkan versi produk yang di SharedPreferences dan di socket
                if((part4 > versionProduk) || (part5 > versionCustomer) || (part6 > versionPlafond))
                {
                    // kalau versi lama lebih kecil dari versi socket maka memasukkan ke versi baru untuk
                    // di SharedPreferences.
                    if(part4 > versionProduk)
                    {


                        ed2.putBoolean(FIELD_AKTIF_PRODUK, false);
                        downloadProduk.setEnabled(true);
                    }
                    else {
                        downloadProduk.setEnabled(false);
                        ed2.putBoolean(FIELD_AKTIF_PRODUK, true);
                    }

                    if(part5 > versionCustomer)
                    {
                        ed2.putBoolean(FIELD_AKTIF_CUSTOMER, false);
                        downloadCustomer.setEnabled(true);
                    }
                    else{
                        downloadCustomer.setEnabled(false);
                        ed2.putBoolean(FIELD_AKTIF_CUSTOMER, true);
                    }

                    if(part6 > versionPlafond)
                    {
                         ed2.putBoolean(FIELD_AKTIF_PLAFON, false);
                        dataPlafond.setEnabled(true);
                    }
                    else{
                        dataPlafond.setEnabled(false);
                        ed2.putBoolean(FIELD_AKTIF_PLAFON, true);

                    }
                    ed2.commit();
                    Log.d("server", "masuk dia kok");

                }/*else{

                    // opsi false yang benar
                    downloadProduk.setEnabled(false);
                    downloadCustomer.setEnabled(false);
                    dataPlafond.setEnabled(false);
                }*/

      //      }

            /*            text.setText(
                    "batas" +strReceived + "batas");*/



  //      }
 //   };*/




    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Request data ready atau tidak untuk di download
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private class RequestTask extends AsyncTask<String , Void, Exception>{

        @Override
        protected void onPreExecute() {
            showProgress("Request to Server...");
        }

        @Override
        protected Exception doInBackground(String... params) {
            try {
                //---create a socket---
                serverAddress = InetAddress.getByName(sIPSetting);
                socket = new Socket(serverAddress, 8888);
                commsThread = new CommsThread(socket);
                commsThread.start();
                //---sign in for the user; sends the nick name---
                // kode sales, dengan kode area
                // ini untuk download produk, cutomer
                sendToServer("update "+ sRequest +  sKodeArea+sKodeSales);
                //mProgressDialog.dismiss();
            }  catch (IOException e) {
                Log.d("Sockets", e.getLocalizedMessage());
            }

            return null;
        }





        @Override
        protected void onPostExecute(Exception result) {
           // dismissProgress();
            if ( result == null ) {
                Log.d("null", "null");
            }
            // something went wrong, post a message to user - you could use a dialog here or whatever
            //Toast.makeText(MainActivity.this, result.getLocalizedMessage(), Toast.LENGTH_LONG ).show();
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Download data Asynctask
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /*private void runThread() {
        final int i =0;
        new Thread() {
            public void run() {
                while (i++ < 1000) {
                    try {
                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                            }
                         });
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

*/





    public static class DownloadTask extends AsyncTask<String , String, Exception>{

        @Override
        protected void onPreExecute() {
            dismissProgress();

            //showProgress("Download & Insert to Databases...");
            showProgressDownload();
        }

        @Override
        protected Exception doInBackground(String... params) {
            String url = (String ) params[0];

             int BUFFER_SIZE = 8192;

            try{
                zipDir =  ExternalStorage.getSDCacheDir(cont.nilai(), "tmp");
                // File path to store .zip file before unzipping
                zipFile = new File( zipDir.getPath() + "/temp.zip" );
                // Folder to hold unzipped output
                outputDir = ExternalStorage.getSDCacheDir(cont.nilai(), "unzipped");
                Log.d("Ready", "ready download");

                InputStream is = null;
                OutputStream os = null;
                File tmp = null;

                try {
                    tmp = File.createTempFile("download", ".tmp", zipDir);
                    is = new URL(url).openStream();
                    URL url2 = new URL(url);
                    URLConnection conexion = url2.openConnection();
                    conexion.connect();

                    int lenghtOfFile = conexion.getContentLength();
                    os = new BufferedOutputStream( new FileOutputStream( tmp ) );
                    byte[] buffer = new byte[ BUFFER_SIZE ];

                    try {
                        long total = 0;
                        Thread.sleep(1000);
                        for (;;) {
                            int count = is.read( buffer, 0, BUFFER_SIZE );
                            total += count;
                            Log.d("total nilai : " ,""+ ((total*100)/lenghtOfFile));
                            dialog.setProgress((int)((total*100)/lenghtOfFile));
                            if ( count == -1 ) {

                                break; }
                            os.write( buffer, 0, count );
                        }
                    } catch ( IOException e ) {
                        throw e;

                    }
                    tmp.renameTo( zipFile );
                    tmp = null;
                } catch ( IOException e ) {
                    throw new RuntimeException( e );
                } finally {
                    if ( tmp != null ) { try { tmp.delete(); tmp = null; } catch (Exception ignore) {;} }
                    if ( is != null  ) { try { is.close();   is = null;  } catch (Exception ignore) {;} }
                    if (os != null) { try { os.close();   os = null;  } catch (Exception ignore) {;} }
                }

                //downloadAllAssets(url);
            }catch(Exception e)
            {
                return e;
            }

            return null;
        }






        @Override
        protected void onPostExecute(Exception result) {







            try{


                new UnzipTask().execute("insert database");
            }finally{
                // tutup sementara
                //zipFile.delete();
            }



            // something went wrong, post a message to user - you could use a dialog here or whatever
            //Toast.makeText(MainActivity.this, result.getLocalizedMessage(), Toast.LENGTH_LONG ).show();
        }
    }

    public static class UnzipTask extends AsyncTask<String , String, Exception>{

        @Override
        protected void onPreExecute() {
            //dismissProgress();
            dismissProgressDownload();
            showProgress("Insert to Databases...");
            //showProgressDownload();
        }

        @Override
        protected Exception doInBackground(String... params) {
            String url = (String ) params[0];



            return null;
        }







        @Override
        protected void onPostExecute(Exception result) {







            try{
                unzipFile( zipFile, outputDir );
                dismissProgress();
            }finally{
                // tutup sementara
                zipFile.delete();
            }
            if ( result == null ) {
                if(sRequest.equals("dp")){
                    downloadProduk.setEnabled(false);
                    ed2.putInt(FIELD_VERSION1, part4);
                    ed2.putBoolean(FIELD_AKTIF_PRODUK, true);

                }
                if(sRequest.equals("dh")){

                    ed2.putInt(FIELD_VERSION3, part6);
                    dataPlafond.setEnabled(false);
                    ed2.putBoolean(FIELD_AKTIF_PLAFON, true);

                }
                if(sRequest.equals("dc")){
                    ed2.putInt(FIELD_VERSION2, part5);
                    ed2.putBoolean(FIELD_AKTIF_CUSTOMER, true);

                    downloadCustomer.setEnabled(false);
                }
                ed2.commit();
                return; }else{
                if(sRequest.equals("dp")){

                    downloadProduk.setEnabled(true);
                    ed2.putBoolean(FIELD_AKTIF_PRODUK, false);
                }
                if(sRequest.equals("dh")){
                    dataPlafond.setEnabled(true);ed2.putBoolean(FIELD_AKTIF_PLAFON, false);
                }
                if(sRequest.equals("dc")){
                    downloadCustomer.setEnabled(true);ed2.putBoolean(FIELD_AKTIF_CUSTOMER, false);
                }
                ed2.commit();
            }


            // something went wrong, post a message to user - you could use a dialog here or whatever
            //Toast.makeText(MainActivity.this, result.getLocalizedMessage(), Toast.LENGTH_LONG ).show();
        }
    }




//////////////////////////////////////////////////////////////////////////
    // File Download
    //////////////////////////////////////////////////////////////////////////

    /**
     * Download .zip file specified by url, then unzip it to a folder in external storage.
     *
     * @param url
     */
    private static void downloadAllAssets(String url){
        //File zipDir = ExternalStorage.get
        // Temp folder for holding asset during download
        zipDir =  ExternalStorage.getSDCacheDir(cont.nilai(), "tmp");
        // File path to store .zip file before unzipping
        zipFile = new File( zipDir.getPath() + "/temp.zip" );
        // Folder to hold unzipped output
        outputDir = ExternalStorage.getSDCacheDir(cont.nilai(), "unzipped");
        Log.d("Ready", "ready download");
        try {

            DownloadFile.download(url, zipFile, zipDir);
        } finally {
            // tutup sementara
            //zipFile.delete();
        }
    }







    //////////////////////////////////////////////////////////////////////////
    // Zip Extraction
    //////////////////////////////////////////////////////////////////////////
    /**
     * Unpack .zip file.
     *
     * @param zipFile
     * @param destination
     */
    protected static void unzipFile( File zipFile, File destination ) {
        DecompressZip decomp = new DecompressZip( zipFile.getPath(),

                destination.getPath() + File.separator );
        decomp.unzip();
    }



    //////////////////////////////////////////////////////////////////////////
    // Progress Dialog
    //////////////////////////////////////////////////////////////////////////

    protected static void showProgress(String request ) {
        snilai = "baca json";
        mProgressDialog = new SpotsDialog(cont.nilai(), request, R.style.Custom);

        mProgressDialog.show();
    }

    //////////////////////////////////////////////////////////////////////////
    // Progress Dialog
    //////////////////////////////////////////////////////////////////////////
    protected static void showProgressDownload(){

        dialog = new ProgressDialog(cont.nilai());
        dialog.setMessage("Download File");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setProgress(0);
        dialog.setMax(100);
        dialog.setCancelable(false);
        dialog.show();
        



    }


    protected static void dismissProgress() {
        // You can't be too careful.
        if (mProgressDialog != null && mProgressDialog.isShowing() && mProgressDialog.getWindow() != null) {
            try {
                mProgressDialog.dismiss();
            } catch ( IllegalArgumentException ignore ) { ignore.printStackTrace(); }
        }
        mProgressDialog = null;
    }

    protected static void dismissProgressDownload() {
        // You can't be too careful.
        if (dialog != null && dialog.isShowing() && dialog.getWindow() != null) {
            try {
                dialog.dismiss();
            } catch ( IllegalArgumentException ignore ) { ignore.printStackTrace(); }
        }
        dialog = null;
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////
    // membuat komunikasi pertama
    ////////////////////////////////////////////////////////////////////////////////////////////////


                private class CreateCommThreadTask extends AsyncTask<Void,Integer ,  Void> {

                    @Override
                    protected void onPreExecute() {

                        super.onPreExecute();

                        pDialog = new ProgressDialog(getActivity());

                        pDialog.setMessage("Please Wait...");

                        pDialog.setIndeterminate(false);

                        pDialog.setCancelable(true);
                        pDialog.show();

                    }



                    @Override
                    protected Void doInBackground(Void... params) {


                        try {
                            //---create a socket---
                            serverAddress = InetAddress.getByName(sIPSetting);
                            socket = new Socket(serverAddress, 8888);
                            commsThread = new CommsThread(socket);
                            commsThread.start();
                            //---sign in for the user; sends the nick name---
                            // kode sales, dengan kode area
//                            sendToServer("sinkron 120101K01" + sKodeArea+sKodeSales);
                            sendToServer("sinkron " + sKodeArea+sKodeSales);
                            pDialog.dismiss();
            }  catch (IOException e) {
                Log.d("Sockets", e.getLocalizedMessage());
            }
            return null;
        }




    }

    private void sendToServer(String message) {
        byte[] theByteArray =
                message.getBytes();
        new WriteToServerTask().execute(theByteArray);
    }

    private class WriteToServerTask extends AsyncTask
            <byte[], Void, Void> {
        protected Void doInBackground(byte[]...data) {
            commsThread.write(data[0]);
            return null;
        }
    }

    private class CloseSocketTask extends AsyncTask
            <Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                socket.close();
            } catch (IOException e) {
                Log.d("Sockets", e.getLocalizedMessage());
            }
            return null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        today = new Date();
        //If you print Date, you will get un formatted output
        System.out.println("Today is : " + today);

        //formatting date in Java using SimpleDateFormat
        DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
        date = DATE_FORMAT.format(today);
        ed = prefTanggal.edit();
        tanggalsekarang = Integer.parseInt(date);
        int sTanggalPref = prefTanggal.getInt("tanggal", 0);

        if(!sIPSetting.equals("") && tanggalsekarang > sTanggalPref) {
            new CreateCommThreadTask().execute();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        int sTanggalPref = prefTanggal.getInt("tanggal", 0);
        if (!sIPSetting.equals("") && tanggalsekarang >sTanggalPref) {
            ed.putInt("tanggal", tanggalsekarang);
            ed.commit();
            new CloseSocketTask().execute();
        }
    }

}