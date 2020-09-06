package nr.king.codepaper.Fragment;

import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.datatransport.runtime.time.WallTimeClock;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karan.churi.PermissionManager.PermissionManager;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import nr.king.codepaper.Adapter.ListAdapter;
import nr.king.codepaper.Adapter.randomwall;
import nr.king.codepaper.Common.Common;
import nr.king.codepaper.Home;
import nr.king.codepaper.Model.List;
import nr.king.codepaper.Model.USer;
import nr.king.codepaper.R;
import nr.king.codepaper.SaveImageUrl;
import nr.king.codepaper.viewwall;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class viewrandi extends Fragment {
public String postid,publisherid,userName,imageUrl,image;
Fragment fragment;
java.util.List<List> listList;
randomwall listAdapter;
    RecyclerView recyclerView1;
    public  ImageView imageView;
    public ImageView back,edit;
    public ImageView like,lock;
    public TextView likes,username,desmoney;
    public ImageView save;
public CircleImageView setwall,download;
public CircleImageView userimage;
FirebaseUser firebaseUser;
Button folow,donate;
public AlertDialog dialog;
public PermissionManager permission;
    String na,upppid;
    String dei,amounted;
    String uppid,userendname,amount;
    final int UPI_PAYEMNT =0 ;
public Target target=new Target() {
    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

        WallpaperManager wallpaperManager=WallpaperManager.getInstance(getContext());
      try {
          wallpaperManager.setBitmap(bitmap);
          Toast.makeText(getContext(), "Wallpaper was Set Sucessfully", Toast.LENGTH_SHORT).show();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater
                .inflate(R.layout.viewwall,container,false);
       SharedPreferences pref=getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        postid=pref.getString("postid","none");
        publisherid=pref.getString("publisherid","none");
        imageUrl=pref.getString("imageUrl","none");
        userName=pref.getString("userName","none");
        image=pref.getString("image","none");
        folow=view.findViewById(R.id.follow);
        edit=view.findViewById(R.id.edit);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        username=view.findViewById(R.id.username);
lock=view.findViewById(R.id.lock);
desmoney=view.findViewById(R.id.descption);
donate=view.findViewById(R.id.donate);
//registerForContextMenu(edit);



        folow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (folow.getText().toString().equals("follow")){

                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(publisherid).setValue(true);

                    FirebaseDatabase.getInstance().getReference().child("Follow").child(publisherid)
                            .child("followers").child(firebaseUser.getUid()).setValue(true);


                    // addNotifications(user.getId());

                }
                else{


                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(publisherid).removeValue();

                    FirebaseDatabase.getInstance().getReference().child("Follow").child(publisherid)
                            .child("followers").child(firebaseUser.getUid()).removeValue();



                }

            }
        });




//        Toast.makeText(getContext(), ""+postid, Toast.LENGTH_SHORT).show();


        isFollow(publisherid,folow);


       imageView=view.findViewById(R.id.imageview);
        like=view.findViewById(R.id.like);
        likes=view.findViewById(R.id.likes);
        userimage=view.findViewById(R.id.userimage);
        save=view.findViewById(R.id.save);
       download=view.findViewById(R.id.download);
       download.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               downloadImage();
           }
       });
setwall=view.findViewById(R.id.setwall);
        isLiked(postid,like);
        nrLikes(postid,likes);
        isSaved(postid,save);
        lock.setVisibility(View.INVISIBLE);
        setwall.setVisibility(View.INVISIBLE);
        download.setVisibility(View.INVISIBLE);

Glide.with(getContext())
        .load(image)
        .into(imageView);



setwall.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        setImage();
    }
});


       back=view.findViewById(R.id.back);
        recyclerView1=view.findViewById(R.id.recyclepost);
       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent intent=new Intent(getContext(), Home.class);
               startActivity(intent);

           }
       });




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                if (save.getTag().equals("Save")) {


                    FirebaseDatabase.getInstance().getReference().child("Saved").child(firebaseUser.getUid()).child(postid).setValue(true);
                }
                else{
                    FirebaseDatabase.getInstance().getReference().child("Saved").child(firebaseUser.getUid()).child(postid).removeValue();

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


                    FirebaseDatabase.getInstance().getReference().child("Likes").child(postid).child(firebaseUser.getUid())
                            .child(postid)
                            .setValue(true);
                }
                else{
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(postid).child(firebaseUser.getUid())
                            .child(postid).removeValue();

                }






            }
        });


       // Toast.makeText(getContext(), "" +image, Toast.LENGTH_SHORT).show();


        //Toast.makeText(getContext(), ""+ postid, Toast.LENGTH_SHORT).show();

//loadImage();
        loadRandom();
        loadUserInfo();


        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        checkBuyied();
        loadState();
    }




    private void checkenduserupid(String userId, final Button donate) {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users").child(userId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                USer user=snapshot.getValue(USer.class);
                if (user.getUppid()==null){

                    donate.setVisibility(View.GONE);
                }
                else {
                    uppid=user.getUppid();
                    userendname=user.getUserName();


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
                if (user.getUppid()==null){




                    donate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialogi();
                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void showpay() {

        AlertDialog.Builder alertdialog=new AlertDialog.Builder(getContext());
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
                String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
                int GOOGLE_PAY_REQUEST_CODE = 123;
                na = edtto.getText().toString();
                amounted=edtamount.getText().toString();
                dei=desc.getText().toString();
                // paywithapi(name,edtto,edtamount,desc);
                Uri uri =Uri.parse("upi://pay").buildUpon()
                        .appendQueryParameter("pa", uppid)
                        .appendQueryParameter("pn", na)
//                                .appendQueryParameter("mc", "your-merchant-code")
//                                .appendQueryParameter("tr", "your-transaction-ref-id")
                        .appendQueryParameter("tn", dei)
                        .appendQueryParameter("am", amount)
                        .appendQueryParameter("cu", "INR")
                        //.appendQueryParameter("url", "your-transaction-url")
                        .build();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);


                Intent chooser=Intent.createChooser(intent,"Pay with");


                    startActivityForResult(chooser,UPI_PAYEMNT);
                //}


//                intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
//                startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//switch (requestCode){
//    case UPI_PAYEMNT:
//        if ((RESULT_OK==resultCode)||resultCode==11 ){
//            if (data!=null){
//                String txt=data.getStringExtra("response");
//                ArrayList<String> dataList=new ArrayList<>();
//                dataList.add(txt);
//                upiPaymentDataOperation(dataList);
//
//
//            }
//
//            else {
//                ArrayList<String> dataList=new ArrayList<>();
//                dataList.add("nothing");
//                upiPaymentDataOperation(dataList);
//            }
//
//
//
//        }
//
//
//
//        else {
//            ArrayList<String> dataList=new ArrayList<>();
//            dataList.add("nothing");
//            upiPaymentDataOperation(dataList);
//
//
//        }
//        break;
//
//
//
//
//}

//
//        switch (requestCode) {
//            case UPI_PAYEMNT:
//                if ((requestCode == resultCode) || (resultCode == 11)) {
//                    if (data != null) {
//                        String trxt = data.getStringExtra("response");
//                        Log.e("UPI", "onActivityResult: " + trxt);
//                        ArrayList<String> dataList = new ArrayList<>();
//                        dataList.add(trxt);
//                        upiPaymentDataOperation(dataList);
//                    } else {
//                        Log.e("UPI", "onActivityResult: " + "Return data is null");
//                        ArrayList<String> dataList = new ArrayList<>();
//                        dataList.add("nothing");
//                        upiPaymentDataOperation(dataList);
//                    }
//                } else {
//                    //when user simply back without payment
//                    Log.e("UPI", "onActivityResult: " + "Return data is null");
//                    ArrayList<String> dataList = new ArrayList<>();
//                    dataList.add("nothing");
//                    upiPaymentDataOperation(dataList);
//                }
//                break;
//        }
//













    }

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

//        if (isConnectionAvailable(getContext())) {
//            String str = data.get(0);
//            Log.e("UPIPAY", "upiPaymentDataOperation: "+str);
//            String paymentCancel = "";
//            if(str == null) str = "discard";
//            String status = "";
//            String approvalRefNo = "";
//            String response[] = str.split("&");
//            for (int i = 0; i < response.length; i++) {
//                String equalStr[] = response[i].split("=");
//                if(equalStr.length >= 2) {
//                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
//                        status = equalStr[1].toLowerCase();
//                    }
//                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
//                        approvalRefNo = equalStr[1];
//                    }
//                }
//                else {
//                    paymentCancel = "Payment cancelled by user.";
//                }
//            }
//
//            if (status.equals("success")) {
//                //Code to handle successful transaction here.
//                Toast.makeText(getContext(), "Transaction successful.", Toast.LENGTH_SHORT).show();
//                Log.e("UPI", "payment successfull: "+approvalRefNo);
//
//                FirebaseDatabase.getInstance().getReference("Buyed").child(Common.List_ID).child(firebaseUser.getUid()).setValue(true);
//
//
//
//            }
//            else if("Payment cancelled by user.".equals(paymentCancel)) {
//                Toast.makeText(getContext(), "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
//                Log.e("UPI", "Cancelled by user: "+approvalRefNo);
//
//            }
//            else {
//                Toast.makeText(getContext(), "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
//                Log.e("UPI", "failed payment: "+approvalRefNo);
//
//            }
//        } else {
//            Log.e("UPI", "Internet issue: ");
//
//            Toast.makeText(getContext(), "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
//        }
//























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

        AlertDialog.Builder alertdialog=new AlertDialog.Builder(getContext());
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

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
                int GOOGLE_PAY_REQUEST_CODE = 123;
                String na=name.getText().toString();
                dei=desc.getText().toString();
                // paywithapi(name,edtto,edtamount,desc);
                Uri uri =Uri.parse("upi://pay").buildUpon()
                        .appendQueryParameter("pa", uppid)
                        .appendQueryParameter("pn", userendname)
//                                .appendQueryParameter("mc", "your-merchant-code")
//                                .appendQueryParameter("tr", "your-transaction-ref-id")
                        .appendQueryParameter("tn", dei)
                        .appendQueryParameter("am", amounted)
                        .appendQueryParameter("cu", "INR")
                        //.appendQueryParameter("url", "your-transaction-url")
                        .build();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);


                Intent chooser=Intent.createChooser(intent,"Pay with");


                    startActivityForResult(chooser,UPI_PAYEMNT);
               // }


//                intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
//                startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);

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


    private void alertDialogi() {

        AlertDialog.Builder alertdialog=new AlertDialog.Builder(getContext());

        alertdialog.setTitle("Are you want to delete Post");
        alertdialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                FirebaseDatabase.getInstance().getReference("Foods").child(Common.List_ID).removeValue();
                startActivity(new Intent(getContext(),Home.class));
                Toast.makeText(getContext(), "Deleted Succesfully", Toast.LENGTH_SHORT).show();


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























    private void checkBuyied() {


        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Buyed").child(postid);

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

    private void loadState() {

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Foods").child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String publisher="YVV3euL2Mrb1nEnmH4yBU5JjpMz1";

                nr.king.codepaper.Model.List list=snapshot.getValue(nr.king.codepaper.Model.List.class);
                FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(publisherid)){
                    donate.setVisibility(View.GONE);
                    download.setVisibility(View.VISIBLE);
                    setwall.setVisibility(View.VISIBLE);
                    lock.setVisibility(View.GONE);
                    //edit.setVisibility(View.VISIBLE);
                    desmoney.setVisibility(View.INVISIBLE);


                }
                else if (publisherid.equals(publisher)){
                    lock.setVisibility(View.GONE);
                    download.setVisibility(View.VISIBLE);
                    setwall.setVisibility(View.VISIBLE);
                    donate.setVisibility(View.VISIBLE);
                    donate.setText("DONATE");
                    desmoney.setVisibility(View.GONE);
                }


                else {
                    if (list.isSale()){
                        lock.setVisibility(View.VISIBLE);
                        desmoney.setText("Rs."+list.getMoney());
                        download.setVisibility(View.GONE);
                        setwall.setVisibility(View.GONE);
                        donate.setVisibility(View.VISIBLE);
                        donate.setTag("BUY THE WALLPAPER");
                        donate.setText("BUY THE WALLPAPER");

                    }

                    else {
                        lock.setVisibility(View.GONE);
                        download.setVisibility(View.VISIBLE);
                        setwall.setVisibility(View.VISIBLE);
                        donate.setVisibility(View.VISIBLE);
                        donate.setText("DONATE");
                        desmoney.setVisibility(View.GONE);
                    }


                }








            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    private void isFollow(final String publisherid, final Button folow) {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseUser.getUid()).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(publisherid).exists()){
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

    private void downloadImage() {
        permission=new PermissionManager() {};
        permission.checkAndRequestPermissions(getActivity());

            dialog=new SpotsDialog.Builder()
                        .setContext(getContext())
                        .setMessage("Please wait")
                        .setCancelable(false).build();


                dialog.show();
//        dialog.setMessage("Please Waiting...");

                String filename= UUID.randomUUID().toString()+".png";
                Picasso.get()
                        .load(image)
                        .into(new SaveImageUrl(getContext(),
                                dialog,
                                getContext().getContentResolver()
                                ,filename,
                                "VBK Live Wallpapers"));


            }



    private void loadUserInfo() {


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Foods").child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List list=snapshot.getValue(List.class);

//

                if (imageUrl!=null){
                    Glide.with(getContext())
                            .load(imageUrl)
                            .into(userimage);
                }


                if (userName!=null){

                    username.setText(userName);
                }


                if (userName==null){
                    username.setText(R.string.app_name);
                }

                if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(publisherid)){
                    folow.setVisibility(View.GONE);
                    // Toast.makeText(this, "Equal fuck"+Common.USER_ID, Toast.LENGTH_SHORT).show();
                }
                else{
                    folow.setVisibility(View.VISIBLE);
                    //Toast.makeText(this, "not equal"+Common.USER_ID, Toast.LENGTH_SHORT).show();
                }






            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



















    }

    private void setImage() {

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Foods").child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List list=snapshot.getValue(List.class);
//                Glide.with(getContext())
//                        .load(list.getImage())
//                        .into(imageView);

//                Picasso.get().load(list.getImage())
//                        .into(imageView);
                Toast.makeText(getContext(), "Please wait...", Toast.LENGTH_LONG).show();

                Picasso.get().load(image)
                        .into(target);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void isSaved(final String list_id, final ImageView save) {
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

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





    private void loadImage() {

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Foods").child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List list=snapshot.getValue(List.class);
//                Glide.with(getContext())
//                        .load(list.getImage())
//                        .into(imageView);

//                Picasso.get().load(list.getImage())
//                        .into(imageView);
//                Picasso.get().load(list.getImage())
//                        .into(target);

                Toast.makeText(getContext(), "" +list.getImage(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    private void loadRandom() {
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView1.setHasFixedSize(true);
        listList=new ArrayList<>();
        listAdapter=new randomwall(getContext(),listList);
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
                Collections.reverse(listList);
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }

}
