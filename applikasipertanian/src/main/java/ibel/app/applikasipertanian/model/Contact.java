package ibel.app.applikasipertanian.model;

import android.content.Context;

/**
 * Created by feetbo on 10/11/2015.
 */

public class Contact {

    //private variables
    int _id;
    String _name;
    String _phone_number;
    String crc;
    String _kode_area, _kode_sales;
    Context context;
    // Empty constructor
    public Contact(){

    }
    // constructor
    public Contact(int id, String name, String _phone_number){
        this._id = id;
        this._name = name;
        this._phone_number = _phone_number;
    }

    // constructor
    public Contact(String name, String _phone_number){
        this._name = name;
        this._phone_number = _phone_number;
    }

    public Context nilai()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    // getting kode area
    public String getKode_Area(){ return this._kode_area ; }

    // setting kode_area
    public void setKode_Area(String kode_area) { this._kode_area = kode_area; }

    // getting kode sales
    public String getKode_Sales(){ return this._kode_sales ; }

    // setting kode_area
    public void setKode_Sales(String kode_sales) { this._kode_sales = kode_sales; }

    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting ID
    public String getCRC(){
        return this.crc;
    }

    // setting id
    public void setCRC(String crc){
        this.crc = crc;
    }


    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }

    // getting phone number
    public String getPhoneNumber(){
        return this._phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number){
        this._phone_number = phone_number;
    }
}