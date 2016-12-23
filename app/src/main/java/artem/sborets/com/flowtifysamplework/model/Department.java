package artem.sborets.com.flowtifysamplework.model;

import com.google.gson.annotations.SerializedName;


public class Department {

    @SerializedName("_id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("street")
    private String street;
    @SerializedName("streetNumber")
    private String streetNumber;
    @SerializedName("zip")
    private int zip;
    @SerializedName("city")
    private String city;

    public Department(String id, String name, String street, String streetNumber, int zip, String city) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.streetNumber = streetNumber;
        this.zip = zip;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public int getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }
}