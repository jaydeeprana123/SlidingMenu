package info.androidhive.slidingmenu.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 01-05-2015.
 */
public class UserOrder {

    @SerializedName("OrderId")
    public String OrderId;
    @SerializedName("OrderStatus")
    public String OrderStatus;
    @SerializedName("PriceToPay")
    public String PriceToPay;
    @SerializedName("Tax")
    public String Tax;
    @SerializedName("TotalPrice")
    public String TotalPrice;



}
