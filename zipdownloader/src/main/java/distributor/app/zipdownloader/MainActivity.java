package distributor.app.zipdownloader;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        JsonParsing json = new JsonParsing();
        json.setmContext(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public void startDownload(View v)
    {
       // String url = ((TextView) findViewById(R.id.url_field)).getText().toString();
        String url = "http://192.168.1.10/fileZip/dp120101A01.zip";
        new DownloadTask().execute(url);
    }


    private class DownloadTask extends AsyncTask<String , Void, Exception>{

        @Override
        protected void onPreExecute() {
            showProgress();
        }

        @Override
        protected Exception doInBackground(String... params) {
            String url = (String ) params[0];
            try{
                    downloadAllAssets(url);
            }catch(Exception e)
            {
                return e;
            }

            return null;
        }





        @Override
        protected void onPostExecute(Exception result) {
            dismissProgress();
            if ( result == null ) { return; }
            // something went wrong, post a message to user - you could use a dialog here or whatever
            Toast.makeText(MainActivity.this, result.getLocalizedMessage(), Toast.LENGTH_LONG ).show();
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
    private void downloadAllAssets(String url){
        //File zipDir = ExternalStorage.get
        // Temp folder for holding asset during download
        File zipDir =  ExternalStorage.getSDCacheDir(this, "tmp");
        // File path to store .zip file before unzipping
        File zipFile = new File( zipDir.getPath() + "/temp.zip" );
        // Folder to hold unzipped output
        File outputDir = ExternalStorage.getSDCacheDir( this, "unzipped" );

        try {
            DownloadFile.download( url, zipFile, zipDir );
            unzipFile( zipFile, outputDir );
        } finally {
            zipFile.delete();
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
    protected void unzipFile( File zipFile, File destination ) {
        DecompressZip decomp = new DecompressZip( zipFile.getPath(),
                destination.getPath() + File.separator );
        decomp.unzip();
    }

    //////////////////////////////////////////////////////////////////////////
    // Progress Dialog
    //////////////////////////////////////////////////////////////////////////

    protected void showProgress( ) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle( R.string.progress_title );
        mProgressDialog.setMessage( getString(R.string.progress_detail) );
        mProgressDialog.setIndeterminate( true );
        mProgressDialog.setCancelable( false );
        mProgressDialog.show();
    }

    protected void dismissProgress() {
        // You can't be too careful.
        if (mProgressDialog != null && mProgressDialog.isShowing() && mProgressDialog.getWindow() != null) {
            try {
                mProgressDialog.dismiss();
            } catch ( IllegalArgumentException ignore ) { ; }
        }
        mProgressDialog = null;
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
}
