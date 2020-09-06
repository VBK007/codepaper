package nr.king.codepaper.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dmax.dialog.SpotsDialog;
import nr.king.codepaper.Common.Common;
import nr.king.codepaper.Home;
import nr.king.codepaper.Model.Category;
import nr.king.codepaper.Model.USer;
import nr.king.codepaper.R;
import nr.king.codepaper.editprofile;
import nr.king.codepaper.login;

public class addPost extends AppCompatActivity {
MaterialEditText editname,editamount;
RadioButton free,sale;
Uri saveuri;
StorageReference storageReference;
FirebaseUser firebaseUser;
ImageView selectimage,back;
String username,uid,imageurl,uppid;
TextView post;
String categoryIdSelectre="";
RadioButton freepost,salepost;
MaterialSpinner spinner;
Map<String,String> spinnerdata=new HashMap<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpost);
firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
selectimage=findViewById(R.id.image);
selectimage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        selectimagee();
    }
});
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),Common.PICK_IMAGE_REQUEST);
editamount=findViewById(R.id.amount);
editname=findViewById(R.id.name);
editamount.setVisibility(View.GONE);
sale=findViewById(R.id.sale);
free=findViewById(R.id.free);
spinner=findViewById(R.id.spinner);
//freepost=findViewById(R.id.free);

loadSpinner();
back=findViewById(R.id.back);
back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(addPost.this, Home.class));
        finish();
    }
});
post=findViewById(R.id.post);
post.setVisibility(View.INVISIBLE);

DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
reference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        USer user=snapshot.getValue(USer.class);

        username=user.getUserName();
        uid=user.getUserid();
        imageurl=user.getImageUrl();
uppid=user.getUppid();

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});






post.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        if (spinner.getSelectedIndex()==0){
            Toast.makeText(addPost.this, "Please Select Category", Toast.LENGTH_SHORT).show();
        }
else {

            poster();

        }

    }
});
storageReference= FirebaseStorage.getInstance().getReference("Posts");
        selectimage=findViewById(R.id.image);






    }

    private void selectimagee() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),Common.PICK_IMAGE_REQUEST);
    }

    private void poster() {


        if (free.isChecked()){
            final AlertDialog progressDialog=new SpotsDialog.Builder()
                    .setContext(addPost.this)
                    .setMessage("Please wait")
                    .setCancelable(false).build();
            progressDialog.show();

            if (saveuri!=null){



                String imagename= UUID.randomUUID().toString();
                final StorageReference reference=storageReference.child("Image"+imagename);
                reference.putFile(saveuri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Foods");
                                        DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("New");
                                        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

                                        String postid = reference.push().getKey();

                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("postid", postid);
                                        hashMap.put("menuId",categoryIdSelectre);
                                        hashMap.put("image", uri.toString());
                                        hashMap.put("isSale",false);
                                        hashMap.put("userName",username);
                                        hashMap.put("name",editname.getText().toString().toLowerCase());
                                        hashMap.put("imageUrl",imageurl);
                                        hashMap.put("publisherid", FirebaseAuth.getInstance().getCurrentUser().getUid());

                                        reference.child(postid).setValue(hashMap);


                                        HashMap<String ,Object> hashMap1=new HashMap<>();
                                        hashMap1.put("postid",postid);
                                        reference1.child(postid).setValue(hashMap1);


                                        Toast.makeText(addPost.this, "Image Uploaded Sucesfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(addPost.this,Home.class));
                                        finish();

                                        progressDialog.dismiss();





                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();

                                    }
                                });



                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progress=( 100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploaded"+(int)progress+"%");
                    }
                });







            }



        }

       else if (sale.isChecked()){
          if (uppid!=null){

                final ProgressDialog progressDialog=new ProgressDialog(addPost.this);
                progressDialog.setMessage("Please wait....");
                progressDialog.show();


                if (saveuri!=null){


                    String imagename= UUID.randomUUID().toString();
                    final StorageReference reference=storageReference.child("Image"+imagename);
                    reference.putFile(saveuri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {


                                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Foods");
                                            DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("New");
                                            FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

                                            String postid = reference.push().getKey();

                                            HashMap<String, Object> hashMap = new HashMap<>();
                                            hashMap.put("postid", postid);
                                            hashMap.put("menuId",categoryIdSelectre);
                                            hashMap.put("image", uri.toString());
                                            hashMap.put("userName",username);
                                            hashMap.put("money", editamount.getText().toString().toLowerCase());
                                            hashMap.put("name",editname.getText().toString().toLowerCase());
                                            hashMap.put("imageUrl",imageurl);
                                            hashMap.put("isSale",true);
                                            hashMap.put("publisherid", FirebaseAuth.getInstance().getCurrentUser().getUid());

                                            reference.child(postid).setValue(hashMap);


                                            HashMap<String ,Object> hashMap1=new HashMap<>();
                                            hashMap1.put("postid",postid);
                                            reference1.child(postid).setValue(hashMap1);



                                            Toast.makeText(addPost.this, "Image Uploaded Sucesfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(addPost.this,Home.class));
                                            finish();


                                            progressDialog.dismiss();





                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();

                                        }
                                    });



                                }
                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress=( 100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded:" +(int)progress+"%");
                        }
                    });







                }


















            }


          else {
              aletrtDaialog();

          }


        }

else {
            Toast.makeText(this, "Please Select any one!", Toast.LENGTH_SHORT).show();
        }

















    }

    private void aletrtDaialog() {

        AlertDialog.Builder alertdialog=new AlertDialog.Builder(addPost.this);

        alertdialog.setTitle("Add UPI ID  IN YOUR ACCOUNT FOR SALE");
        alertdialog.setPositiveButton("Add UPID From Gpay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(addPost.this, editprofile.class));
                finish();
            }
        });
        alertdialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(addPost.this, "Please add UPID for Sale Your Product", Toast.LENGTH_SHORT).show();
            }
        });

        alertdialog.show();




    }

    private void loadSpinner() {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Category");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Category category=dataSnapshot.getValue(Category.class);


                    String key=dataSnapshot.getKey();
                    spinnerdata.put(key,category.getName());



                }

                Object[] valuearray=spinnerdata.values().toArray();
                List<Object> valueList=new ArrayList<>();
                valueList.add("Choose Category");
                valueList.addAll(Arrays.asList(valuearray));
                spinner.setItems(valueList);


                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                        Object[] keyArray=spinnerdata.keySet().toArray();
                        List<Object> keylsit=new ArrayList<>();
                        keylsit.add("Choose Category_Key");
                        keylsit.addAll(Arrays.asList(keyArray));
categoryIdSelectre=keylsit.get(position).toString();

                    }
                });




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void posthepic() {



if (sale.isChecked()){
    final AlertDialog progressDialog=new SpotsDialog.Builder()
            .setContext(addPost.this)
            .setMessage("Please wait")
            .setCancelable(false).build();
    progressDialog.show();

    if (saveuri!=null){


final StorageReference reference=storageReference.child(new StringBuilder("images/").append(UUID.randomUUID().toString()).toString());
reference.putFile(saveuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
    @Override
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
         saveuriToCategory(categoryIdSelectre,taskSnapshot.getUploadSessionUri().toString());
progressDialog.dismiss();
    }
})

        .addOnFailureListener(new OnFailureListener() {
    @Override
    public void onFailure(@NonNull Exception e) {

    }
}).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
    @Override
    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

        double progress=( 100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
        progressDialog.setMessage("Uploaded"+(int)progress+"%");

    }
});






    }



}


else if (free.isChecked()){
    if (saveuri!=null){

        final AlertDialog progressDialog=new SpotsDialog.Builder()
                .setContext(addPost.this)
                .setMessage("Please wait")
                .setCancelable(false).build();
        progressDialog.show();

        final StorageReference reference=storageReference.child(new StringBuilder("images/").append(UUID.randomUUID().toString()).toString());
        reference.putFile(saveuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                saveFreeUri(categoryIdSelectre,taskSnapshot.getUploadSessionUri().toString());
progressDialog.dismiss();
            }
        })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                double progress=( 100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("Uploaded"+(int)progress+"%");

            }
        });






    }





}





    }

    private void saveFreeUri(final String categoryIdSelectre, final String imagelink) {
DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
reference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        final USer user=snapshot.getValue(USer.class);
        final DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Foods");
        final DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("New");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String,Object> hashMap=new HashMap<>();
                String postid = reference.push().getKey();
                hashMap.put("menuId",categoryIdSelectre);
                hashMap.put("image",imagelink);
                hashMap.put("userName",user.getUserName());
                hashMap.put("isSale",false);
                hashMap.put("name",editname.getText().toString().toLowerCase());
                hashMap.put("imageUrl",user.getImageUrl());
                hashMap.put("publisherid", FirebaseAuth.getInstance().getCurrentUser().getUid());

                reference.child(postid).setValue(hashMap);


                HashMap<String ,Object> hashMap1=new HashMap<>();
                hashMap1.put("postid",postid);
                reference1.child(postid).setValue(hashMap1);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});



    }

    private void saveuriToCategory(final String categoryIdSelectre, final String imagelink) {
DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
databaseReference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        final USer user=snapshot.getValue(USer.class);


        final DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Foods");


        final DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("New");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                HashMap<String,Object> hashMap=new HashMap<>();
                String postid = reference.push().getKey();
                hashMap.put("menuId",categoryIdSelectre);
                hashMap.put("image",imagelink);
                hashMap.put("isSale",true);
                hashMap.put("postid",postid);
                hashMap.put("userName",user.getUserName());
                hashMap.put("description",editamount.getText().toString());
                hashMap.put("name",editname.getText().toString().toLowerCase());
                hashMap.put("imageUrl",user.getImageUrl());
                hashMap.put("publisherid", FirebaseAuth.getInstance().getCurrentUser().getUid());

                reference.child(postid).setValue(hashMap);


                HashMap<String ,Object> hashMap1=new HashMap<>();
                hashMap1.put("postid",postid);
                reference1.child(postid).setValue(hashMap1);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Common.PICK_IMAGE_REQUEST && resultCode==RESULT_OK&& data!=null && data.getData()!=null){

            saveuri=data.getData();
            if (saveuri!=null){
                selectimage.setImageURI(saveuri);

            }
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.sale:
                if (checked)
                 editamount.setVisibility(View.VISIBLE);
                checkeditamount();
                    break;
            case R.id.free:
                if (checked)
                    editamount.setVisibility(View.GONE);
              checkedd();


                    break;
        }


    }

    private void checkedd() {
        if (TextUtils.isEmpty(editname.getText().toString())){
            post.setVisibility(View.INVISIBLE);

        }

        else{
            post.setVisibility(View.VISIBLE);
        }
    }

    private void checkeditamount() {

       if (TextUtils.isEmpty(editamount.getText().toString()) && TextUtils.isEmpty(editname.getText().toString())){
           post.setVisibility(View.INVISIBLE);

       }

        else {
            post.setVisibility(View.VISIBLE);
        }


    }


}

