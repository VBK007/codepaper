package nr.king.codepaper.Common;

import com.google.firebase.auth.FirebaseUser;

import nr.king.codepaper.Model.List;
import nr.king.codepaper.Model.USer;
import nr.king.codepaper.Model.User;

public class Common {
    public static final int SIGN_INREQUEST_CODE =99 ;
    public static final int PICK_IMAGE_REQUEST = 77;
    public static  String UID;
    //public static  USER_NAME = ;
    public static  String USER_NAME;
    public static final String STAFF_KEY="Email";
    public static final String STUDENTKEY="RegNo";

    public static final String pwd_KEY="Password";
    public static  String USER_ID;
    public static  String LIST_NAME;
    public static  String LIST_WALLPAER ;
    public static String CATEGORY_NAME ;
    public static  String CATEGORY_ID;
    public static String CATEGORY_ID_SELECTED ;
public static List select_background=new List();

    public static String List_ID;
    public static String User_IMAGE;
    public static USer currentUser;

    public static String User="Users";
    public static boolean Sale;
    public static String image;
    public static String name;
    public static String money;
}
