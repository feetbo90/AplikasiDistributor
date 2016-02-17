package distributor.app.material.DownloadCompressParsing;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import distributor.app.material.sqlite.SqliteManagerData;

/**
 * Created by feetbo on 10/27/2015.
 */public class DecompressZip {
    private static final int BUFFER_SIZE=8192;

    private String _zipFile;
    private String _location;
    private byte[] _buffer;
    static String sCurrentLine = "";
    static String sJson = "";
    static String nilaiParsing = "";



    public void setParsing(String nilai)
    {
        nilaiParsing = nilai;
    }

    public DecompressZip(){

    }

    public DecompressZip(String zipFile, String location) {
        _zipFile = zipFile;
        _location = location;
        _buffer = new byte[BUFFER_SIZE];
        dirChecker("");
    }

    public void unzip() {
        FileInputStream fin = null;
        ZipInputStream zin = null;
        OutputStream fout = null;




        File outputDir = new File(_location);
        File tmp = null;

        try {
            fin = new FileInputStream(_zipFile);
            zin = new ZipInputStream(fin);

            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                Log.d("Decompress", "Unzipping " + _location + ze.getName());

                if (ze.isDirectory()) {
                    dirChecker(ze.getName());
                } else {
                    tmp = File.createTempFile( "decomp", ".tmp", outputDir );
                    fout = new BufferedOutputStream(new FileOutputStream(tmp));
                    DownloadFile.copyStream( zin, fout, _buffer, BUFFER_SIZE );
                    zin.closeEntry();
                    fout.close();
                    fout = null;
                    tmp.renameTo( new File(_location + ze.getName()) );

                    // membaca nilai file
                    BufferedReader br = new BufferedReader(new FileReader(_location + ze.getName()));

                    /*InputStream is = new InputStream(new FileReader(_location + ze.getName()));
                    int size = is.available();
                    byte [] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    sJson = new String (buffer, "UTF-8");
*/

                    sJson = "";
                   /* ini adalah parsing yang lama
                   sCurrentLine = "";
                        sCurrentLine = br.readLine();
                        sCurrentLine = br.readLine();
                    while ((sCurrentLine = br.readLine()) != null) {
                        //    System.out.println(sCurrentLine);
                        sJson = sJson + sCurrentLine;
                       // Log.d("Decompress", "Read File " +  sJson);
                        }*/

                    try{
                        File myFile = new File(_location + ze.getName());
                        FileInputStream stream = new FileInputStream(myFile);
                        try {
                            FileChannel fc = stream.getChannel();
                            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

                            sJson = Charset.defaultCharset().decode(bb).toString();
                        }
                        finally {
                            stream.close();
                        }
                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    Log.d("sJson", "crlimit1" + sJson);
                    JsonParsing parse = new JsonParsing(sJson);

                    //
                    //
                    //
                    //
                    if(nilaiParsing.equals("dp")) {

                        parse.parsing();
                    }else if(nilaiParsing.equals("dh"))
                    {//data.hapusplafond();
                        parse.parsingPlafond();
                    }else if(nilaiParsing.equals("dc"))
                    {//data.hapuscustomer();
                        parse.parsingCustomer();
                    }//data.tutupKoneksi();
                    //Log.d("Decompress", "Read File " +  sJson);
                    //parse.parsingCustomer();
                    sJson = "";

                    tmp = null;
                }
            }
            zin.close();
            zin = null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if ( tmp != null  ) { try { tmp.delete();     } catch (Exception ignore) {;} }
            if ( fout != null ) { try { fout.close(); 	  } catch (Exception ignore) {;} }
            if ( zin != null  ) { try { zin.closeEntry(); } catch (Exception ignore) {;} }
            if ( fin != null  ) { try { fin.close(); 	  } catch (Exception ignore) {;} }
        }
    }

    private void dirChecker(String dir) {
        File f = new File(_location + dir);

        if (!f.isDirectory()) {
            f.mkdirs();
        }
    }
}