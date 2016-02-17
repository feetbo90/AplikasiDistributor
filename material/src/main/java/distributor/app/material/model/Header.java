package distributor.app.material.model;

/**
 * Created by feetbo on 12/2/2015.
 */
public class Header {

    String code = null;
    String kustomer = null;
    String tanggal = null;
    String kodesales = null;
    String noPOcustomer = null;
    String catatan = null;
    String pilihandistributor = null;

    public Header(String code, String kustomer, String tanggal, String kodesales,
                  String noPOcustomer, String catatan) {
        super();
        this.code = code;
        this.kustomer = kustomer;
        this.tanggal = tanggal;
        this.kodesales = kodesales;
        this.noPOcustomer = noPOcustomer;
        this.catatan = catatan;
       // this.pilihandistributor = pilihandistributor;
    }

    public String getNoPOcustomer() {
        return noPOcustomer;
    }

    public String getCatatan() {
        return catatan;
    }

    public String getPilihandistributor() {
        return pilihandistributor;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getKustomer() {
        return kustomer;
    }

    public void setKustomer(String kustomer) {
        this.kustomer = kustomer;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String continent) {
        this.tanggal = continent;
    }

    public String getKodeSales() {
        return kodesales;
    }

    public void setKodeSales(String kodesales) {
        this.kodesales = kodesales;
    }


    @Override
    public String toString() {
        return  code + " " + tanggal + " "
                + kustomer ;
    }


}