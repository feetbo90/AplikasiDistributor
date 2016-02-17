package distributor.app.zipdownloader;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by feetbo on 10/28/2015.
 */
public class JsonParsing {

    private String json;
    // JSON Node names
    private static final String TAG_PRODUK = "produk";
    private static final String TAG_KODE = "kode";
    private static final String TAG_NAMA = "nama";
    private static final String TAG_GROUP_ID = "groupid";
    private static final String TAG_UNITB = "unitb";
    private static final String TAG_UNITM = "unitm";
    private static final String TAG_UNITK = "unitk";
    private static final String TAG_QTYB = "qtyb";
    private static final String TAG_QTYM = "qtym";
    private static final String TAG_QTYK = "qtyk";

    // JSON Node names
    private static final String TAG_HARGA = "harga";
    private static final String TAG_HARGA1 = "harga1";
    private static final String TAG_HARGA2 = "harga2";
    private static final String TAG_HARGA3 = "harga3";
    private static final String TAG_HARGA4 = "harga4";
    private static final String TAG_HARGA5 = "harga5";
    private static final String TAG_HARGA6 = "harga6";
    private static final String TAG_HARGA7 = "harga7";
    private static final String TAG_HARGA8 = "harga8";
    private static final String TAG_HARGA9 = "harga9";
    private static final String TAG_HARGA10 = "harga10";

    // JSON Node names
    private static final String TAG_BHARGA = "bharga";
    private static final String TAG_BHJUAL1 = "bhjual1";
    private static final String TAG_BHJUAL2 = "bhjual2";
    private static final String TAG_BHJUAL3 = "bhjual3";
    private static final String TAG_BHJUAL4 = "bhjual4";
    private static final String TAG_BHJUAL5 = "bhjual5";
    private static final String TAG_BHJUAL6 = "bhjual6";
    private static final String TAG_BHJUAL7 = "bhjual7";
    private static final String TAG_BHJUAL8 = "bhjual8";
    private static final String TAG_BHJUAL9 = "bhjual9";
    private static final String TAG_BHJUAL10 = "bhjual10";

    // JSON Node names
    private static final String TAG_TGLA = "tgla";
    private static final String TAG_TGL1A = "tgl1a";
    private static final String TAG_TGL2A = "tgl2a";
    private static final String TAG_TGL3A = "tgl3a";
    private static final String TAG_TGL4A = "tgl4a";
    private static final String TAG_TGL5A = "tgl5a";
    private static final String TAG_TGL6A = "tgl6a";
    private static final String TAG_TGL7A = "tgl7a";
    private static final String TAG_TGL8A = "tgl8a";
    private static final String TAG_TGL9A= "tgl9a";
    private static final String TAG_TGL10A = "tgl10a";

    // JSON Node names
    private static final String TAG_TGLB = "tglb";
    private static final String TAG_TGL1B = "tgl1b";
    private static final String TAG_TGL2B = "tgl2b";
    private static final String TAG_TGL3B = "tgl3b";
    private static final String TAG_TGL4B = "tgl4b";
    private static final String TAG_TGL5B = "tgl5b";
    private static final String TAG_TGL6B = "tgl6b";
    private static final String TAG_TGL7B = "tgl7b";
    private static final String TAG_TGL8B = "tgl8b";
    private static final String TAG_TGL9B = "tgl9b";
    private static final String TAG_TGL10B = "tgl10b";

    JSONArray produk = null;
    SqliteManagerData sqLite;

    public JsonParsing(){};

    public JsonParsing(String json)
    {
        this.json = json;
    }

    static Context mContext;

    public static void setmContext(Context context)
    {
        mContext = context.getApplicationContext();
    }

    public JsonParsing(Context context)
    {
        Log.d("masuk jsonParsing", "context");
        this.mContext = context;
    }
    public void parsing()
    {
       sqLite = new SqliteManagerData(mContext);
        sqLite.bukaKoneksi();
        Log.d("Decompress", "masuk");
        if(json !=null)
        {
            try {
                JSONObject jsonObject = new JSONObject(json);
                // Getting JSON Array node
                produk = jsonObject.getJSONArray(TAG_PRODUK);
                //looping pengambilan semua data produk
                for (int i = 0; i < produk.length(); i++) {
                    JSONObject p = produk.getJSONObject(i);
                    String kode = p.getString(TAG_KODE);
                    String nama = p.getString(TAG_NAMA);
                    String groupid = p.getString(TAG_GROUP_ID);
                    String unitb = p.getString(TAG_UNITB);
                    String unitm = p.getString(TAG_UNITM);
                    String unitk = p.getString(TAG_UNITK);
                    String qtyb = p.getString(TAG_QTYB);
                    String qtym = p.getString(TAG_QTYM);
                    String qtyk = p.getString(TAG_QTYK);

                    JSONObject harga = p.getJSONObject(TAG_HARGA);
                    String harga1 = harga.getString(TAG_HARGA1);
                    String harga2 = harga.getString(TAG_HARGA2);
                    String harga3 = harga.getString(TAG_HARGA3);
                    String harga4 = harga.getString(TAG_HARGA4);
                    String harga5 = harga.getString(TAG_HARGA5);
                    String harga6 = harga.getString(TAG_HARGA6);
                    String harga7 = harga.getString(TAG_HARGA7);
                    String harga8 = harga.getString(TAG_HARGA8);
                    String harga9 = harga.getString(TAG_HARGA9);
                    String harga10 = harga.getString(TAG_HARGA10);

                    JSONObject bharga = p.getJSONObject(TAG_BHARGA);
                    String bhjual1 = bharga.getString(TAG_BHJUAL1);
                    String bhjual2 = bharga.getString(TAG_BHJUAL2);
                    String bhjual3 = bharga.getString(TAG_BHJUAL3);
                    String bhjual4 = bharga.getString(TAG_BHJUAL4);
                    String bhjual5 = bharga.getString(TAG_BHJUAL5);
                    String bhjual6 = bharga.getString(TAG_BHJUAL6);
                    String bhjual7 = bharga.getString(TAG_BHJUAL7);
                    String bhjual8 = bharga.getString(TAG_BHJUAL8);
                    String bhjual9 = bharga.getString(TAG_BHJUAL9);
                    String bhjual10 = bharga.getString(TAG_BHJUAL10);

                    JSONObject tgla = p.getJSONObject(TAG_TGLA);
                    String tgl1a = tgla.getString(TAG_TGL1A);
                    String tgl2a = tgla.getString(TAG_TGL2A);
                    String tgl3a = tgla.getString(TAG_TGL3A);
                    String tgl4a = tgla.getString(TAG_TGL4A);
                    String tgl5a = tgla.getString(TAG_TGL5A);
                    String tgl6a = tgla.getString(TAG_TGL6A);
                    String tgl7a = tgla.getString(TAG_TGL7A);
                    String tgl8a = tgla.getString(TAG_TGL8A);
                    String tgl9a = tgla.getString(TAG_TGL9A);
                    String tgl10a = tgla.getString(TAG_TGL10A);

                    JSONObject tglb = p.getJSONObject(TAG_TGLB);
                    String tgl1b = tglb.getString(TAG_TGL1B);
                    String tgl2b = tglb.getString(TAG_TGL2B);
                    String tgl3b = tglb.getString(TAG_TGL3B);
                    String tgl4b = tglb.getString(TAG_TGL4B);
                    String tgl5b = tglb.getString(TAG_TGL5B);
                    String tgl6b = tglb.getString(TAG_TGL6B);
                    String tgl7b = tglb.getString(TAG_TGL7B);
                    String tgl8b = tglb.getString(TAG_TGL8B);
                    String tgl9b = tglb.getString(TAG_TGL9B);
                    String tgl10b = tglb.getString(TAG_TGL10B);

                    Log.d("Decompress", "Read Json " +  kode);
               long nilai = sqLite.insertDataroduk(sqLite.InsertDataProduk(kode, nama, groupid, unitb, unitm, unitk,
                            qtyb, qtym, qtyk, harga1, harga2, harga3, harga4, harga5, harga6, harga7, harga8,
                            harga9, harga10, bhjual1, bhjual2, bhjual3, bhjual4, bhjual5, bhjual6, bhjual7, bhjual8,
                            bhjual9, bhjual10, tgl1a, tgl2a, tgl3a, tgl4a, tgl5a, tgl6a, tgl7a, tgl8a, tgl9a, tgl10a,
                            tgl1b, tgl2b, tgl3b, tgl4b, tgl5b, tgl6b, tgl7b, tgl8b, tgl9b, tgl10b));
                    Log.d("Masuk Database", "ini nilai " );

                }
                sqLite.tutupKoneksi();
            }catch (JSONException e)
            {
                e.printStackTrace();
            }
        }else{
            Log.e("Json", "Gagal Parsing");
        }
    }
}
