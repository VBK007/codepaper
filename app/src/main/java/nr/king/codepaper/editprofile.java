package nr.king.codepaper;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.rengwuxian.materialedittext.MaterialEditText;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import nr.king.codepaper.Common.Common;
import nr.king.codepaper.Model.User;


public class editprofile extends AppCompatActivity {
    MaterialEditText ediupid,editemail,editphone,editpassword,editusername,edtgpayname;
    ImageView ok,back;
    FirebaseAuth auth;
    Uri saveuri;
    TextView click,hint;
    CircleImageView circleImageView;
    FirebaseUser firebaseUser;
    StorageReference storageReference;
String email,password,phone,username,upid,gpayname;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprof);
        editemail=findViewById(R.id.edtEmail);
        ediupid=findViewById(R.id.ediuppid);
        editphone=findViewById(R.id.editphone);
        editpassword=findViewById(R.id.edtpassword);
        editusername=findViewById(R.id.editusername);
edtgpayname=findViewById(R.id.edtgpayname);
click=findViewById(R.id.click);
hint=findViewById(R.id.hint);
        circleImageView=findViewById(R.id.userimage);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        ok=findViewById(R.id.ok);
        back=findViewById(R.id.back);
        storageReference= FirebaseStorage.getInstance().getReference("Userprofile");

        loadData();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(editprofile.this,Home.class));
                finish();

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploaddata();
            }
        });





    }

    private void selectImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),Common.PICK_IMAGE_REQUEST);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== Common.PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data !=null && data.getData()!=null) {

            saveuri = data.getData();
            if (saveuri != null) {
circleImageView.setImageURI(saveuri);

            }
        }




    }



    private void loadData() {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                USer user=snapshot.getValue(USer.class);
              if (user.getImageUrl()!=null){
                  Glide.with(getApplicationContext())
                          .load(user.getImageUrl())
                          .into(circleImageView);

              }

              else if (user.getImageUrl()==null){
                  circleImageView.setImageResource(R.drawable.ic_baseline_account_circle_24);
              }

              editemail.setText(user.getEmail());
                //email=editemail.getText().toString();
                editusername.setText(user.getUserName());
               // username=editusername.getText().toString();
              editpassword.setText(user.getPassword());
                //password=editpassword.getText().toString();
              editphone.setText(user.getPhone());
                //phone=editphone.getText().toString();

                if (user.getUppid()!=null){
                  ediupid.setText(user.getUppid());
                  hint.setVisibility(View.INVISIBLE);
                   // upid=ediupid.getText().toString();
              }
                if (user.getGpayname()!=null){
                    edtgpayname.setText(user.getGpayname());
                    hint.setVisibility(View.INVISIBLE);
                }



                else {

                    hint.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(editprofile.this,viewshop.class));
                        }
                    });
                }








            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void uploaddata() {
         final AlertDialog progressDialog=
                new SpotsDialog.Builder()
                        .setContext(editprofile.this)
                        .setMessage("Please wait")
                        .setCancelable(false).build();
         progressDialog.show();


        email=editemail.getText().toString();
        password=editpassword.getText().toString();
        phone=editphone.getText().toString();
        username=editusername.getText().toString();
        if (!TextUtils.isEmpty(ediupid.getText().toString())){
            upid=ediupid.getText().toString();


        }
        if (!TextUtils.isEmpty(edtgpayname.getText().toString())){
            gpayname=edtgpayname.getText().toString();


        }


        if(saveuri!=null){


    String imagename= UUID.randomUUID().toString();
    final StorageReference reference=storageReference.child("Profile"+imagename);
    reference.putFile(saveuri)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

//                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
//                            FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

                            Map<String, Object> hashMap = new HashMap<>();
                            hashMap.put("imageUrl",uri.toString());

                                hashMap.put("email",email);
                                hashMap.put("password",password);
                                hashMap.put("phone",phone);
                                hashMap.put("userName",username);
                                if (upid!=null){
                                    hashMap.put("uppid",upid);
                                }
                                hashMap.put("gpayname",gpayname);

                            DatabaseReference userinfo= FirebaseDatabase.getInstance().getReference("Users");
                            userinfo.child(firebaseUser.getUid())
                                    .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        progressDialog.dismiss();


                                        Toast.makeText(editprofile.this, "Information Updated", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(editprofile.this,Home.class));
                                        finish();
                                        // Glide.with(profilemain.this).load(Common.currentUser.getAvatarUrl()).into(imageView);

                                    }

                                    else {
                                        Toast.makeText(editprofile.this, "Information not Updated!!", Toast.LENGTH_SHORT).show();
                                    }

                                }


                            });









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

        }
    });





}


else{

    DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
    reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
USer user=snapshot.getValue(USer.class);

            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("imageUrl",user.getImageUrl());

            hashMap.put("email",email);
            hashMap.put("password",password);
            hashMap.put("phone",phone);
            hashMap.put("userName",username);
            if (upid!=null){
                hashMap.put("uppid",upid);
            }
            hashMap.put("gpayname",gpayname);
            DatabaseReference userinfo= FirebaseDatabase.getInstance().getReference(Common.User);
            userinfo.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();


                        Toast.makeText(editprofile.this, "Information Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(editprofile.this,Home.class));
                        finish();
                        // Glide.with(profilemain.this).load(Common.currentUser.getAvatarUrl()).into(imageView);

                    }

                    else {
                        Toast.makeText(editprofile.this, "Information not Updated!!", Toast.LENGTH_SHORT).show();
                    }

                }


            });









        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });








}






    }




































    }



