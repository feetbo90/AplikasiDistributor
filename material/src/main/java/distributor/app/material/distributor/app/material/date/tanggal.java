package distributor.app.material.distributor.app.material.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by feetbo on 2/14/2016.
 */
public class tanggal {
    static String tanggalkirim = "";
    static String hari = "";
    public String getTanggalKonver(String tanggal) {
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date MyDate = newDateFormat.parse(tanggal);
            newDateFormat.applyPattern("EEEE");
            String MyDate2 = newDateFormat.format(MyDate);
            tanggalkirim = MyDate2;
            tanggalkirim = getKonversiTanggal(MyDate2);
            System.out.println(MyDate2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tanggalkirim;
    }


    public String getKonversiTanggal(String konversi){

        if(konversi.equals("Sunday"))
        {
            konversi = "Minggu";
        }
        if(konversi.equals("Monday"))
        {
            konversi = "Senin";
        }
        if(konversi.equals("Tuesday"))
        {
            konversi = "Selasa";
        }
        if(konversi.equals("Wednesday"))
        {
            konversi = "Rabu";
        }
        if(konversi.equals("Thursday"))
        {
            konversi = "Kamis";
        }
        if(konversi.equals("Friday"))
        {
            konversi = "Jum'at";
        }
        if(konversi.equals("Saturday"))
        {
            konversi = "Sabtu";
        }

        return konversi;
    }


}
