package nr.king.codepaper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.karan.churi.PermissionManager.PermissionManager;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import nr.king.codepaper.Adapter.randomwall;
import nr.king.codepaper.Adapter.viewwalladapter;
import nr.king.codepaper.Common.Common;
import nr.king.codepaper.Fragment.userhome;
import nr.king.codepaper.Model.User;

public class viewwall extends AppCompatActivity {
  final int UPI_PAYEMNT =0 ;
    ImageView imageView;
ImageView back,like;
CircleImageView circleImageView;
List<nr.king.codepaper.Model.List> listList;
randomwall listAdapter;
viewwalladapter viewwalladapter;
RecyclerView recyclerView;
TextView likes,username;
ImageView save,lock,edit;
CircleImageView userimage;
CircleImageView download;
public AlertDialog dialog;
Button folow,donate;
String na,upppid;
TextView desmoney;
    String dei,amounted;
String uppid,userendname,amount;
    public static final String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    int GOOGLE_PAY_REQUEST_CODE = 123;
    String newamount;
    String names = "Highbrow Director";
    String upiIds = "hashimads123@oksbi";
    String transactionNotes = "pay test";
    String status;
    Uri uri;
  private ActivityMainBinding binding;
String Gpayname;


public Target target=new Target() {
    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        WallpaperManager wallpaperManager=WallpaperManager.getInstance(getApplicationContext());
        try {
            wallpaperManager.setBitmap(bitmap);
            Toast.makeText(viewwall.this, "Wallpaer set Sucessfully", Toast.LENGTH_SHORT).show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
};
    FirebaseUser firebaseUser;
CircleImageView setwall;
public PermissionManager permission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewwall);
firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
      imageView=findViewById(R.id.imageview);
        Glide.with(this).load(Common.LIST_WALLPAER)
                .into(imageView);
        edit=findViewById(R.id.edit);
download=findViewById(R.id.download);
        like=findViewById(R.id.like);
likes=findViewById(R.id.likes);
save=findViewById(R.id.save);
        isLiked(Common.List_ID,like);
        nrLikes(Common.List_ID,likes);
        isSaved(Common.List_ID,save);

setwall=findViewById(R.id.setwall);
userimage=findViewById(R.id.userimage);
        username=findViewById(R.id.username);
donate=findViewById(R.id.donate);
desmoney=findViewById(R.id.descption);
lock=findViewById(R.id.lock);

checkBuyied();
loadState();
checkUpid(firebaseUser.getUid(),donate);

checkenduserupid(Common.USER_ID,donate);



        lock.setVisibility(View.INVISIBLE);
        download.setVisibility(View.INVISIBLE);
        setwall.setVisibility(View.INVISIBLE);



      registerForContextMenu(edit);

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(viewwall.this, enduserpro.class);
                Common.UID=Common.USER_ID;
                startActivity(intent);

            }
        });


userimage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(viewwall.this, enduserpro.class);
        Common.UID=Common.USER_ID;
        startActivity(intent);

    }
});


folow=findViewById(R.id.follow);

         firebaseUser=FirebaseAuth.getInstance().getCurrentUser();


download.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        permission=new PermissionManager() {};
        permission.checkAndRequestPermissions(viewwall.this);
       dialog=new SpotsDialog.Builder()
               .setContext(viewwall.this)
               .setMessage("Please wait")
               .setCancelable(false).build();

       
        dialog.show();
//        dialog.setMessage("Please Waiting...");

        String filename= UUID.randomUUID().toString()+".png";
        Picasso.get()
                .load(Common.LIST_WALLPAER)
                .into(new SaveImageUrl(getBaseContext(),
                        dialog,
                        getApplicationContext().getContentResolver()
                        ,filename,
                        "VBK Live Wallpapers"));

    }
});



if (Common.User_IMAGE!=null){
    Glide.with(getApplicationContext())
            .load(Common.User_IMAGE)
            .into(userimage);
}

else{
    userimage.setImageResource(R.mipmap.ic_launcher);
}


if (Common.USER_NAME!=null){

    username.setText(Common.USER_NAME);
}
else {
    username.setText("Codeplays");
}

if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(Common.USER_ID)){
  folow.setVisibility(View.GONE);
   // Toast.makeText(this, "Equal fuck"+Common.USER_ID, Toast.LENGTH_SHORT).show();
}
else{
  folow.setVisibility(View.VISIBLE);
    //Toast.makeText(this, "not equal"+Common.USER_ID, Toast.LENGTH_SHORT).show();
}


isFollowing(Common.USER_ID,folow);


folow.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (folow.getText().toString().equals("follow")){

            FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                    .child("following").child(Common.USER_ID).setValue(true);

            FirebaseDatabase.getInstance().getReference().child("Follow").child(Common.USER_ID)
                    .child("followers").child(firebaseUser.getUid()).setValue(true);


           // addNotifications(user.getId());

        }
        else{


            FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                    .child("following").child(Common.USER_ID).removeValue();

            FirebaseDatabase.getInstance().getReference().child("Follow").child(Common.USER_ID)
                    .child("followers").child(firebaseUser.getUid()).removeValue();



        }



    }
});









setwall.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(viewwall.this, "Please wait...", Toast.LENGTH_LONG).show();

        Picasso.get().load(Common.LIST_WALLPAER)
                .into(target);
    }
});





        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(viewwall.this, nr.king.codepaper.List.class);
                startActivity(intent);
            }
        });





save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if (save.getTag().equals("Save")) {


            FirebaseDatabase.getInstance().getReference().child("Saved").child(firebaseUser.getUid()).child(Common.List_ID).setValue(true);
        }
        else{
            FirebaseDatabase.getInstance().getReference().child("Saved").child(firebaseUser.getUid()).child(Common.List_ID).removeValue();

        }


    }
});



like.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        //favpost();
        nr.king.codepaper.Model.List list=new nr.king.codepaper.Model.List();
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
if (like.getTag().equals("Like")) {


    FirebaseDatabase.getInstance().getReference().child("Likes").child(Common.List_ID).child(firebaseUser.getUid())
            .child(Common.List_ID)
            .setValue(true);
}
        else{
            FirebaseDatabase.getInstance().getReference().child("Likes").child(Common.List_ID).child(firebaseUser.getUid())
                    .child(Common.List_ID).removeValue();

        }






        }
});





//
//if (donate.getTag().equals("BUY THE WALLPAPER")){
//
//    donate.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            showpay();
//        }
//    });
//
//
//}
//
//











       // Toast.makeText(this, ""+Common.CATEGORY_ID_SELECTED, Toast.LENGTH_SHORT).show();

       // loadPost();

       loadRandom();











    }



    private void checkenduserupid(String userId, final Button donate) {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users").child(userId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                USer user=snapshot.getValue(USer.class);
                if (user.getUppid()==null && user.getGpayname()==null){

                   donate.setVisibility(View.GONE);
                }
                else {
                    uppid=user.getUppid();
                    userendname=user.getUserName();
                    Gpayname=user.getGpayname();


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void checkUpid(String uid, final Button donate) {

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users").child(uid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                USer user=snapshot.getValue(USer.class);
                if (user.getUppid()==null && user.getGpayname()==null){




                    donate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alerted();
                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void alerted() {

        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("Please add UPIID to Donate");
        alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
           dialogInterface.dismiss();
            }
        });

        alert.setPositiveButton("Add UPID", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(viewwall.this,editprofile.class));
                finish();
            }
        });
alert.show();

    }


    private void checkBuyied() {


        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Buyed").child(Common.List_ID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nr.king.codepaper.Model.List list=snapshot.getValue(nr.king.codepaper.Model.List.class);
                if (snapshot.child(firebaseUser.getUid()).exists()){

                    donate.setVisibility(View.VISIBLE);
                            download.setVisibility(View.VISIBLE);
                            setwall.setVisibility(View.VISIBLE);
                            lock.setVisibility(View.GONE);
                            donate.setText("DONATE");


                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {


        if (item.getItemId()==R.id.editpost){

            DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Foods").child(Common.List_ID);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    nr.king.codepaper.Model.List list=snapshot.getValue(nr.king.codepaper.Model.List.class);
                    Intent intent=new Intent(viewwall.this,Descpost.class);
                    assert list != null;
                    Common.image=list.getImage();
                    Common.UID=list.getPublisherid();
Common.List_ID=list.getPostid();
Common.name=list.getName();
Common.money=list.getMoney();
                    startActivity(intent);
                    finish();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });







        }

        else if (item.getItemId()==R.id.postdelete){

         alertDialogi();

        }

        return true;
    }

    private void alertDialogi() {

        AlertDialog.Builder alertdialog=new AlertDialog.Builder(this);

        alertdialog.setTitle("Are you want to delete Post");
        alertdialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                FirebaseDatabase.getInstance().getReference("Foods").child(Common.List_ID).removeValue();
                startActivity(new Intent(viewwall.this,Home.class));
                Toast.makeText(viewwall.this, "Deleted Succesfully", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        alertdialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
alertdialog.show();

    }

    private void loadState() {
        final String publisher="YVV3euL2Mrb1nEnmH4yBU5JjpMz1";

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Foods").child(Common.List_ID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nr.king.codepaper.Model.List list=snapshot.getValue(nr.king.codepaper.Model.List.class);
                FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(Common.USER_ID)){
                     donate.setVisibility(View.GONE);
                    download.setVisibility(View.VISIBLE);
                    setwall.setVisibility(View.VISIBLE);
                    lock.setVisibility(View.GONE);
                    desmoney.setVisibility(View.GONE);
                    edit.setVisibility(View.VISIBLE);
//                    edit.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
//                        @Override
//                        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
////                            MenuInflater inflater = getMenuInflater();
////                            inflater.inflate(R.menu.editmenu, menu);
////                            menu.setHeaderTitle("Select The Action");
//
//                        }
//                    });

//                 edit.setOnClickListener(new View.OnClickListener() {
//                     @Override
//                     public void onClick(View view) {
//                         Toast.makeText(viewwall.this, "Clciked", Toast.LENGTH_SHORT).show();
//                         registerForContextMenu(edit);
//                     }
//                 });










                }


else if (Common.USER_ID.equals(publisher)){



                    lock.setVisibility(View.GONE);
                    download.setVisibility(View.VISIBLE);
                    setwall.setVisibility(View.VISIBLE);
                    donate.setVisibility(View.GONE);
                    donate.setText("DONATE");
                    desmoney.setVisibility(View.GONE);

                    donate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showpayi();
                        }
                    });

                }














                    else {
                        if (list.isSale()) {
                        lock.setVisibility(View.VISIBLE);
                        desmoney.setText("Rs." + list.getMoney());
                        download.setVisibility(View.GONE);
                        setwall.setVisibility(View.GONE);
                        donate.setVisibility(View.VISIBLE);
                        donate.setText("BUY THE WALLPAPER");
                            amount=list.getMoney();
                       donate.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                              showpay();
                           }
                       });




                    } else {
                        lock.setVisibility(View.GONE);
                        download.setVisibility(View.VISIBLE);
                        setwall.setVisibility(View.VISIBLE);
                        donate.setVisibility(View.VISIBLE);
                        donate.setText("DONATE");
                        desmoney.setVisibility(View.GONE);
                            donate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    showpayi();
                                }
                            });

                        }


                }











            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }





    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
                            inflater.inflate(R.menu.editmenu, menu);
                            menu.setHeaderTitle("Select The Action");




    }



    private void showpay() {

        AlertDialog.Builder alertdialog=new AlertDialog.Builder(this);
        alertdialog.setTitle("Payment Gateway");
        LayoutInflater inflater=this.getLayoutInflater();
        View view=inflater.inflate(R.layout.paylay,null);
        final MaterialEditText name,edtto,edtamount,desc;
        Button pay;
        name=view.findViewById(R.id.username);
        edtto=view.findViewById(R.id.endUser);
        edtamount=view.findViewById(R.id.amount);
        desc=view.findViewById(R.id.dexc);
        pay=view.findViewById(R.id.pay);
        alertdialog.setView(view);

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nr.king.codepaper.Model.List list=snapshot.getValue(nr.king.codepaper.Model.List.class);
                name.setText(list.getUserName());
                edtto.setText(userendname);
                edtamount.setText(amount);
                desc.setText("Amount for Wallpaer");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dei=desc.getText().toString();
                uri=getupipaymentUri(Gpayname,uppid,dei,amounted);
                paywithGpay();

            }
        });



        alertdialog.setPositiveButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertdialog.setNegativeButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertdialog.show();
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
////switch (requestCode){
////    case UPI_PAYEMNT:
////        if ((RESULT_OK==resultCode)||resultCode==11 ){
////            if (data!=null){
////                String txt=data.getStringExtra("response");
////                ArrayList<String> dataList=new ArrayList<>();
////                dataList.add(txt);
////                upiPaymentDataOperation(dataList);
////
////
////            }
////
////            else {
////                ArrayList<String> dataList=new ArrayList<>();
////                dataList.add("nothing");
////                upiPaymentDataOperation(dataList);
////            }
////
////
////
////        }
////
////
////
////        else {
////            ArrayList<String> dataList=new ArrayList<>();
////            dataList.add("nothing");
////            upiPaymentDataOperation(dataList);
////
////
////        }
////        break;
////
////
////
////
////}
//
////
////        switch (requestCode) {
////            case UPI_PAYEMNT:
////                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
////                    if (data != null) {
////                        String trxt = data.getStringExtra("response");
////                        Log.e("UPI", "onActivityResult: " + trxt);
////                        ArrayList<String> dataList = new ArrayList<>();
////                        dataList.add(trxt);
////                        upiPaymentDataOperation(dataList);
////                    } else {
////                        Log.e("UPI", "onActivityResult: " + "Return data is null");
////                        ArrayList<String> dataList = new ArrayList<>();
////                        dataList.add("nothing");
////                        upiPaymentDataOperation(dataList);
////                    }
////                } else {
////                    //when user simply back without payment
////                    Log.e("UPI", "onActivityResult: " + "Return data is null");
////                    ArrayList<String> dataList = new ArrayList<>();
////                    dataList.add("nothing");
////                    upiPaymentDataOperation(dataList);
////                }
////                break;
////        }
////
//
//
//
//
//
//
//
//
//
//
//
//
//
//    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
//if (isConnectionAvailabe(viewwall.this)) {
//
//    String str = dataList.get(0);
//    String paymentcancel = "";
//    if (str == null) str = "discard";
//    String status = "";
//    String approvalRefNo = "";
//    String response[] = str.split("&");
//    for (int i = 0; i < response.length; i++) {
//        String equalstr[] = response[i].split("m");
//        if (equalstr.length >= 2) {
//
//            if (equalstr[0].toLowerCase().equals("Status".toLowerCase())) {
//                status = equalstr[1].toLowerCase();
//            } else if (equalstr[0].equals("ApprovalRefNo".toLowerCase()) || equalstr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase())) {
//
//                approvalRefNo = equalstr[1];
//
//            }
//        } else {
//            paymentcancel = "Payment cancelled by user";
//        }
//
//
//    }
//
//    if (status.equals("success")) {
//        Toast.makeText(this, "Transaction sucesfully", Toast.LENGTH_SHORT).show();
//
//
//    } else if ("Payment cancelled by user".equals(paymentcancel)) {
//        Toast.makeText(this, "Payment cancelled by user", Toast.LENGTH_SHORT).show();
//    } else {
//        Toast.makeText(this, "Transcation failed Please try again", Toast.LENGTH_SHORT).show();
//    }
//
//
//}

        if (isConnectionAvailable(viewwall.this)) {
            String str = data.get(0);
            Log.e("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(viewwall.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "payment successfull: "+approvalRefNo);

                FirebaseDatabase.getInstance().getReference("Buyed").child(Common.List_ID).child(firebaseUser.getUid()).setValue(true);



            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(viewwall.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "Cancelled by user: "+approvalRefNo);

            }
            else {
                Toast.makeText(viewwall.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "failed payment: "+approvalRefNo);

            }
        } else {
            Log.e("UPI", "Internet issue: ");

            Toast.makeText(viewwall.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }





















    }

    private boolean isConnectionAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    private void showpayi() {

        AlertDialog.Builder alertdialog=new AlertDialog.Builder(this);
        alertdialog.setTitle("Payment Gateway");
        LayoutInflater inflater=this.getLayoutInflater();
        View view=inflater.inflate(R.layout.paydon,null);
        final MaterialEditText name,edtto,edtamount,desc;
        Button pay;
        name=view.findViewById(R.id.username);
        edtto=view.findViewById(R.id.endUser);
        edtamount=view.findViewById(R.id.amount);
        desc=view.findViewById(R.id.dexc);
        pay=view.findViewById(R.id.pay);

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nr.king.codepaper.Model.List list=snapshot.getValue(nr.king.codepaper.Model.List.class);
                name.setText(list.getUserName());
                edtto.setText(userendname);

                amounted=edtamount.getText().toString();
                desc.setText("Amount for Wallpaer");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        pay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
//                int GOOGLE_PAY_REQUEST_CODE = 123;
//                String na=name.getText().toString();
//                dei=desc.getText().toString();
//                // paywithapi(name,edtto,edtamount,desc);
//                Uri uri =Uri.parse("upi://pay").buildUpon()
//                        .appendQueryParameter("pa", uppid)
//                        .appendQueryParameter("pn", userendname)
////                                .appendQueryParameter("mc", "your-merchant-code")
////                                .appendQueryParameter("tr", "your-transaction-ref-id")
//                        .appendQueryParameter("tn", dei)
//                        .appendQueryParameter("am", amounted)
//                        .appendQueryParameter("cu", "INR")
//                        //.appendQueryParameter("url", "your-transaction-url")
//                        .build();
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(uri);
//
//
//                Intent chooser=Intent.createChooser(intent,"Pay with");
//
//                if (null!=chooser.resolveActivity(getPackageManager())){
//                    startActivityForResult(chooser,UPI_PAYEMNT);
//                }
//
//
////                intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
////                startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);
//
//            }
//        });



        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                names=edtto.getText().toString();
                dei=desc.getText().toString();

                uri=getupipaymentUri(names,uppid,dei,amounted);
                paywithGpay();
            }
        });
















        alertdialog.setView(view);
        alertdialog.setPositiveButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertdialog.setNegativeButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertdialog.show();
    }

    private void paywithGpay() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
        startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (data != null) {
            status = data.getStringExtra("Status").toLowerCase();
        }

        if ((RESULT_OK == resultCode) && status.equals("success")) {
            Toast.makeText(viewwall.this, "Transaction Successful", Toast.LENGTH_SHORT).show();
            FirebaseDatabase.getInstance().getReference("Buyed").child(Common.List_ID).child(firebaseUser.getUid()).setValue(true);
        } else {
            Toast.makeText(viewwall.this, "Transaction Failed", Toast.LENGTH_SHORT).show();
        }




    }

    private Uri getupipaymentUri(String names, String upiIds, String transactionNotes, String amount) {
        return new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", upiIds)
                .appendQueryParameter("pn", names)
                .appendQueryParameter("tn", transactionNotes)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();


    }







    private void isFollowing(final String userId, final Button folow) {

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseUser.getUid()).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(userId).exists()){
                    folow.setText("following");

                }
                else{
                    folow.setText("follow");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

//    private void setImage() {
//
////        Glide.with(this).load(Common.LIST_WALLPAER)
////                .into(imageView);
//
//
//
//    }

    private void isSaved(final String list_id, final ImageView save) {
        final FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Saved")
                .child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(list_id).exists()){

                    save.setImageResource(R.drawable.ic_baseline_bookmark_24 );
                    save.setTag("Saved");


                }

                else {
                    save.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
                    save.setTag("Save");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void nrLikes(String list_id, final TextView likes) {

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Likes")
                .child(list_id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                likes.setText(snapshot.getChildrenCount()+"Likes");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void isLiked(String list_id, final ImageView like) {

        final FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Likes")
                .child(list_id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(firebaseUser.getUid()).exists()){

                    like.setImageResource(R.drawable.ic_baseline_favorite_24 );
                    like.setTag("Liked");


                }

                else {
                    like.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    like.setTag("Like");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }

    private void favpost() {

        final DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Likes");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
String postid=reference.push().getKey();
HashMap<String,Object> hashMap=new HashMap<>();
hashMap.put("postid",postid);
hashMap.put("uid",firebaseUser.getUid());






            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void loadPost() {
        recyclerView=findViewById(R.id.recycle);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);

        listList=new ArrayList<>();

        viewwalladapter=new viewwalladapter(getApplicationContext(),listList);
        recyclerView.setAdapter(viewwalladapter);


        Query reference= FirebaseDatabase.getInstance().getReference("Foods")
                .orderByChild("menuId").equalTo(Common.CATEGORY_ID_SELECTED);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                    nr.king.codepaper.Model.List post= dataSnapshot.getValue(nr.king.codepaper.Model.List.class);
                    listList.add(post);


                }
                viewwalladapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void loadRandom() {

        RecyclerView recyclerView1=findViewById(R.id.recyclepost);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView1.setHasFixedSize(true);
listList=new ArrayList<>();
listAdapter=new randomwall(getApplicationContext(),listList);
recyclerView1.setAdapter(listAdapter);

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Foods");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {

                    nr.king.codepaper.Model.List post= dataSnapshot.getValue(nr.king.codepaper.Model.List.class);
                    listList.add(post);
                }

                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }


    private class ActivityMainBinding {
    }
}