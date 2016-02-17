package distributor.app.material.model;

/**
 * Created by feetbo on 12/2/2015.
 */
public class Customer {

    String code = null;
    String name = null;
    String continent = null;


    public Customer(String code, String name, String continent) {
        super();
        this.code = code;
        this.name = name;
        this.continent = continent;

    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getNama() {
        return name;
    }
    public void setNama(String name) {
        this.name = name;
    }
    public String getContinent() {
        return continent;
    }
    public void setContinent(String continent) {
        this.continent = continent;
    }


    @Override
    public String toString() {
        return  code + " " + name + " "
                + continent ;
    }


}