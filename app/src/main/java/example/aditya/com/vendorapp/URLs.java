package example.aditya.com.vendorapp;

/**
 * Created by aditya on 2/7/2018.
 */

public class URLs {
    //public static String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InR1c2hhciIsIm9yaWdfaWF0IjoxNTE4MzQwNDI3LCJ1c2VyX2lkIjoxLCJlbWFpbCI6IiIsImV4cCI6MTUxODM0NjQyN30.derumV9nIDmwkCDuM0LSpstnHbH8blLvsXxVpZhRWV0";
    public static String WALLET_TOKEN = "4b6e39873f40492aadee397b03316b62";
    public static String USERNAME="";
    public static String PASSWORD="";
//   public static final String base = "http://192.168.43.14:8000";
   public static final String base = "https://www.bits-apogee.org/2018";

//     public static final String base = "http://172.17.38.252:8000";

    public static final String URL_LOGIN = base+"/shop/api_token/";
    public static final String URL_ORDER_COMPLETE = base+"/shop/ready_order/";
    public static final String URL_TOKEN_REFRESH = base + "/shop/api_token_refresh/";
    public static final String URL_CANCEL_ORDER = base+"/shop/cancel_order/";
    public static final String URL_STALL_ID = base+"/shop/get_stall_id/";
    public static  String stallID;
    public static  String stallName;

    public static final String URL_STALL_PRODUCTS = base+"/shop/get_products/";
    public static final String URL_BLOCK_PRODUCT = base+"/shop/available/";
    public static final String URLCcmp = base+"/shop/order_complete";


}
