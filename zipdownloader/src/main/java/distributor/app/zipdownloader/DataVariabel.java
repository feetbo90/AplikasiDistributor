package distributor.app.zipdownloader;

import android.content.Context;

/**
 * Created by feetbo on 11/1/2015.
 */
public class DataVariabel {

    private static String TAG_KODE ;
    private static String TAG_NAMA ;
    private static String TAG_GROUP_ID ;
    private static String TAG_UNITB ;
    private static String TAG_UNITM ;
    private static String TAG_UNITK ;
    private static String TAG_QTYB ;
    private static String TAG_QTYM ;
    private static String TAG_QTYK ;

    // JSON Node names
    private static String TAG_HARGA1 ;
    private static String TAG_HARGA2 ;
    private static String TAG_HARGA3 ;
    private static String TAG_HARGA4;
    private static String TAG_HARGA5 ;
    private static String TAG_HARGA6 ;
    private static String TAG_HARGA7 ;
    private static String TAG_HARGA8 ;
    private static String TAG_HARGA9 ;
    private static String TAG_HARGA10 ;

    // JSON Node names
    private static String TAG_BHJUAL1 ;
    private static String TAG_BHJUAL2 ;
    private static String TAG_BHJUAL3 ;
    private static String TAG_BHJUAL4 ;
    private static String TAG_BHJUAL5 ;
    private static String TAG_BHJUAL6 ;
    private static String TAG_BHJUAL7 ;
    private static String TAG_BHJUAL8 ;
    private static String TAG_BHJUAL9 ;
    private static String TAG_BHJUAL10 ;

    // JSON Node names
    private static String TAG_TGL1A ;
    private static String TAG_TGL2A ;
    private static String TAG_TGL3A ;
    private static String TAG_TGL4A ;
    private static String TAG_TGL5A ;
    private static String TAG_TGL6A ;
    private static String TAG_TGL7A ;
    private static String TAG_TGL8A ;
    private static String TAG_TGL9A ;
    private static String TAG_TGL10A ;

    // JSON Node names
    private static String TAG_TGL1B ;
    private static String TAG_TGL2B ;
    private static String TAG_TGL3B ;
    private static String TAG_TGL4B ;
    private static String TAG_TGL5B ;
    private static String TAG_TGL6B ;
    private static String TAG_TGL7B ;
    private static String TAG_TGL8B ;
    private static String TAG_TGL9B ;
    private static String TAG_TGL10B ;
    Context context;

    public DataVariabel(){}



    public DataVariabel(String kode, String nama,
                        String group_id, String unitb,
                        String unitm, String unitk, String harga1,
                        String harga2, String harga3, String harga4, String harga5, String harga6,
                        String harga7, String harga8, String harga9, String harga10,
                        String bhjual1, String bhjual2, String bhjual3, String bhjual4,
                        String bhjual5, String bhjual6, String bhjual7, String bhjual8,
                        String bhjual9, String bhjual10, String tgl1a, String tgl2a,
                        String tgl3a, String tgl4a, String tgl5a, String tgl6a, String tgl7a,
                        String tgl8a, String tgl9a, String tgl10a, String tgl1b, String tgl2b,
                        String tgl3b, String tgl4b, String tgl5b, String tgl6b, String tgl7b,
                        String tgl8b, String tgl9b, String tgl10b)
    {
        this.TAG_KODE = kode;
        this.TAG_NAMA = nama;
        this.TAG_GROUP_ID = group_id;
        this.TAG_UNITB = unitb;
        this.TAG_UNITM = unitm;
        this.TAG_UNITK = unitk;
        this.TAG_HARGA1 = harga1;
        this.TAG_HARGA2 = harga2;
        this.TAG_HARGA3 = harga3;
        this.TAG_HARGA4 = harga4;
        this.TAG_HARGA5 = harga5;
        this.TAG_HARGA6 = harga6;
        this.TAG_HARGA7 = harga7;
        this.TAG_HARGA8 = harga8;
        this.TAG_HARGA9 = harga9;
        this.TAG_HARGA10 = harga10;
        this.TAG_BHJUAL1 = bhjual1;
        this.TAG_BHJUAL2 = bhjual2;
        this.TAG_BHJUAL3 = bhjual3;
        this.TAG_BHJUAL4 = bhjual4;
        this.TAG_BHJUAL5 = bhjual5;
        this.TAG_BHJUAL6 = bhjual6;
        this.TAG_BHJUAL7 = bhjual7;
        this.TAG_BHJUAL8 = bhjual8;
        this.TAG_BHJUAL9 = bhjual9;
        this.TAG_BHJUAL10 = bhjual10;
        this.TAG_TGL1A = tgl1a;
        this.TAG_TGL2A = tgl2a;
        this.TAG_TGL3A = tgl3a;
        this.TAG_TGL4A = tgl4a;
        this.TAG_TGL5A = tgl5a;
        this.TAG_TGL6A = tgl6a;
        this.TAG_TGL7A = tgl7a;
        this.TAG_TGL8A = tgl8a;
        this.TAG_TGL9A = tgl9a;
        this.TAG_TGL10A = tgl10a;
        this.TAG_TGL1B = tgl1b;
        this.TAG_TGL2B = tgl2b;
        this.TAG_TGL3B = tgl3b;
        this.TAG_TGL4B = tgl4b;
        this.TAG_TGL5B = tgl5b;
        this.TAG_TGL6B = tgl6b;
        this.TAG_TGL7B = tgl7b;
        this.TAG_TGL8B = tgl8b;
        this.TAG_TGL9B = tgl9b;
        this.TAG_TGL10B = tgl10b;
    }


    // getting kode
    public String getKodeProduk(){ return this.TAG_KODE ; }

    // setting kode
    public void setKodeProduk(String kode) { this.TAG_KODE = kode; }

    // getting nama
    public String getNamaProduk(){ return this.TAG_NAMA ; }

    // setting nama_
    public void setNamaProduk(String namaProduk) { this.TAG_NAMA = namaProduk; }

    // getting groupID
    public String getGroupID(){ return this.TAG_GROUP_ID ; }

    // setting groupID
    public void setGroupID(String groupID) { this.TAG_GROUP_ID = groupID; }

    // getting UnitB
    public String getUnitB(){ return this.TAG_UNITB ; }

    // setting unitB
    public void setUnitB(String unitB) { this.TAG_UNITB = unitB; }

    // getting unitM
    public String getUnitM(){ return this.TAG_UNITM ; }

    // setting unitM
    public void setUnitM(String unitM) { this.TAG_UNITM = unitM; }

    // getting unitK
    public String getUnitK(){ return this.TAG_UNITK ; }

    // setting unitK
    public void setUnitK(String unitK) { this.TAG_UNITK = unitK; }

    // getting qtyb
    public String getQtyB(){ return this.TAG_QTYB ; }

    // setting qtyb
    public void setQtyB(String qtyB) { this.TAG_QTYB = qtyB; }

    // getting qtyM
    public String getQtyM(){ return this.TAG_QTYM ; }

    // setting qtyM
    public void setQtyM(String qtyM) { this.TAG_QTYM = qtyM; }

    // getting qtyK
    public String getQtyK(){ return this.TAG_QTYK ; }

    // setting qtyK
    public void setQtyK(String qtyK) { this.TAG_QTYK = qtyK; }


    // getting harga1
    public String getHarga1(){ return this.TAG_HARGA1 ; }

    // setting harga1
    public void setHarga1(String harga1) { this.TAG_HARGA1 = harga1; }

    // getting harga2
    public String getHarga2(){ return this.TAG_HARGA2 ; }

    // setting harga2
    public void setHarga2(String harga2) { this.TAG_HARGA2 = harga2; }

    // getting harga3
    public String getHarga3(){ return this.TAG_HARGA3 ; }

    // setting harga3
    public void setHarga3(String harga3) { this.TAG_HARGA3 = harga3; }

    // getting harga4
    public String getHarga4(){ return this.TAG_HARGA4 ; }

    // setting harga4
    public void setHarga4(String harga4) { this.TAG_HARGA4 = harga4; }

    // getting harga5
    public String getHarga5(){ return this.TAG_HARGA5 ; }

    // setting harga5
    public void setHarga5(String harga5) { this.TAG_HARGA5 = harga5; }

    // getting harga6
    public String getHarga6(){ return this.TAG_HARGA6 ; }

    // setting harga6
    public void setHarga6(String harga6) { this.TAG_HARGA6 = harga6; }

    // getting harga10
    public String getHarga10(){ return this.TAG_HARGA10 ; }

    // setting harga10
    public void setHarga10(String harga10) { this.TAG_HARGA10 = harga10; }

    // getting harga7
    public String getHarga7(){ return this.TAG_HARGA7 ; }

    // setting harga7
    public void setHarga7(String harga7) { this.TAG_HARGA7 = harga7; }

    // getting harga8
    public String getHarga8(){ return this.TAG_HARGA8 ; }

    // setting harga8
    public void setHarga8(String harga8) { this.TAG_HARGA8 = harga8; }

    // getting harga9
    public String getHarga9(){ return this.TAG_HARGA9 ; }

    // setting harga9
    public void setHarga9(String harga9) { this.TAG_HARGA9 = harga9; }

    // getting bhjual1
    public String getBhjual1(){ return this.TAG_BHJUAL1 ; }

    // setting bhjual1
    public void setBhjual1(String bhjual1) { this.TAG_BHJUAL1 = bhjual1; }

    // getting bhjual2
    public String getBhjual2(){ return this.TAG_BHJUAL2 ; }

    // setting bhjual2
    public void setBhjual2(String bhjual2) { this.TAG_BHJUAL2 = bhjual2; }

    // getting bhjual3
    public String getBhjual3(){ return this.TAG_BHJUAL3 ; }

    // setting bhjual3
    public void setBhjual3(String bhjual3) { this.TAG_BHJUAL3 = bhjual3; }

    // getting bhjual4
    public String getBhjual4(){ return this.TAG_BHJUAL4 ; }

    // setting bhjual4
    public void setBhjual4(String bhjual4) { this.TAG_BHJUAL4 = bhjual4; }

    // getting bhjual5
    public String getBhjual5(){ return this.TAG_BHJUAL5 ; }

    // setting bhjual5
    public void setBhjual5(String bhjual5) { this.TAG_BHJUAL5 = bhjual5; }

    // getting bhjual6
    public String getBhjual6(){ return this.TAG_BHJUAL6 ; }

    // setting bhjual6
    public void setBhjual6(String bhjual6) { this.TAG_BHJUAL6 = bhjual6; }

    // getting bhjual7
    public String getBhjual7(){ return this.TAG_BHJUAL7 ; }

    // setting bhjual7
    public void setBhjual7(String bhjual7) { this.TAG_BHJUAL7 = bhjual7; }

    // getting bhjual8
    public String getBhjual8(){ return this.TAG_BHJUAL8 ; }

    // setting bhjual8
    public void setBhjual8(String bhjual8) { this.TAG_BHJUAL8 = bhjual8; }

    // getting bhjual9
    public String getBhjual9(){ return this.TAG_BHJUAL9 ; }

    // setting bhjual9
    public void setBhjual9(String bhjual9) { this.TAG_BHJUAL9 = bhjual9; }

    // getting bhjual10
    public String getBhjual10(){ return this.TAG_BHJUAL10 ; }

    // setting bhjual10
    public void setBhjual10(String bhjual10) { this.TAG_BHJUAL10 = bhjual10; }

    // getting tgl1a
    public String getTgl1a(){ return this.TAG_TGL1A ; }

    // setting tgl1a
    public void setTgl1a(String tgl1a) { this.TAG_TGL1A = tgl1a; }

    // getting tgl2a
    public String getTgl2a(){ return this.TAG_TGL2A ; }

    // setting tgl2a
    public void setTgl2a(String tgl2a) { this.TAG_TGL2A = tgl2a; }

    // getting tgl3a
    public String getTgl3a(){ return this.TAG_TGL3A ; }

    // setting tgl3a
    public void setTgl3a(String tgl3a) { this.TAG_TGL3A = tgl3a; }

    // getting tgl4a
    public String getTgl4a(){ return this.TAG_TGL4A ; }

    // setting tgl4a
    public void setTgl4a(String tgl4a) { this.TAG_TGL4A = tgl4a; }

    // getting tgl5a
    public String getTgl5a(){ return this.TAG_TGL5A ; }

    // setting tgl5a
    public void setTgl5a(String tgl5a) { this.TAG_TGL5A = tgl5a; }

    // getting tgl6a
    public String getTgl6a(){ return this.TAG_TGL6A ; }

    // setting tgl6a
    public void setTgl6a(String tgl6a) { this.TAG_TGL6A = tgl6a; }

    // getting tgl7a
    public String getTgl7a(){ return this.TAG_TGL7A ; }

    // setting tgl7a
    public void setTgl7a(String tgl7a) { this.TAG_TGL7A = tgl7a; }

    // getting tgl8a
    public String getTgl8a(){ return this.TAG_TGL8A ; }

    // setting tgl8a
    public void setTgl8a(String tgl8a) { this.TAG_TGL8A = tgl8a; }

    // getting tgl9a
    public String getTgl9a(){ return this.TAG_TGL9A ; }

    // setting tgl9a
    public void setTgl9a(String tgl9a) { this.TAG_TGL9A = tgl9a; }

    // getting tgl10a
    public String getTgl10a(){ return this.TAG_TGL10A ; }

    // setting tgl10a
    public void setTgl10a(String tgl10a) { this.TAG_TGL10A = tgl10a; }

    // getting tgl1b
    public String getTgl1b(){ return this.TAG_TGL1B ; }

    // setting tgl1b
    public void setTgl1b(String tgl1b) { this.TAG_TGL1B = tgl1b; }

    // getting tgl2b
    public String getTgl2b(){ return this.TAG_TGL2B ; }

    // setting tgl2b
    public void setTgl2b(String tgl2b) { this.TAG_TGL2B = tgl2b; }

    // getting tgl3b
    public String getTgl3b(){ return this.TAG_TGL3B ; }

    // setting tgl3b
    public void setTgl3b(String tgl3b) { this.TAG_TGL3B = tgl3b; }

    // getting tgl4b
    public String getTgl4b(){ return this.TAG_TGL4B ; }

    // setting tgl4B
    public void setTgl4B(String tgl4B) { this.TAG_TGL4B = tgl4B; }

    // getting tgl5b
    public String getTgl5B(){ return this.TAG_TGL5B ; }

    // setting tgl5a
    public void setTgl5b(String tgl5b) { this.TAG_TGL5B= tgl5b; }

    // getting tgl6b
    public String getTgl6b(){ return this.TAG_TGL6B ; }

    // setting tgl6b
    public void setTgl6b(String tgl6b) { this.TAG_TGL6B = tgl6b; }

    // getting tgl7b
    public String getTgl7b(){ return this.TAG_TGL7B ; }

    // setting tgl7b
    public void setTgl7b(String tgl7b) { this.TAG_TGL7B = tgl7b; }

    // getting tgl8b
    public String getTgl8b(){ return this.TAG_TGL8B ; }

    // setting tgl8B
    public void setTgl8b(String tgl8b) { this.TAG_TGL8B = tgl8b; }

    // getting tgl9b
    public String getTgl9b(){ return this.TAG_TGL9B ; }

    // setting tgl9B
    public void setTgl9b(String tgl9b) { this.TAG_TGL9B = tgl9b; }

    // getting tgl10b
    public String getTgl10b(){ return this.TAG_TGL10B ; }

    // setting tgl10b
    public void setTgl10b(String tgl10b) { this.TAG_TGL10B = tgl10b; }



}
