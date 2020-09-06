package nr.king.codepaper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import nr.king.codepaper.Adapter.ListAdapter;
import nr.king.codepaper.Common.Common;
import nr.king.codepaper.Fragment.homefrag;

public class List extends AppCompatActivity {
    MaterialEditText edttitle,edtdesc;
    Uri saveuri,uri1;
    StorageReference storageReference1;

    RecyclerView recyclerView;
    ListAdapter listAdapter;
    java.util.List<nr.king.codepaper.Model.List> listList;
    CircleImageView circleImageView,addimage;
    ImageView selectimage,Category;
    StorageReference storageReference;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        TextView textView=findViewById(R.id.category);
        textView.setText(Common.CATEGORY_NAME);
storageReference= FirebaseStorage.getInstance().getReference("Posts");
        //Toast.makeText(this, ""+Common.CATEGORY_ID_SELECTED, Toast.LENGTH_SHORT).show();

        storageReference=FirebaseStorage.getInstance().getReference("Category");
        circleImageView =findViewById(R.id.addimage);
        circleImageView.setVisibility(View.GONE);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //uploadImage();
            }
        });


        ImageView imageView=findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(List.this, Home.class);
                startActivity(intent);


            }
        });


        recyclerView=findViewById(R.id.recycle);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        listList=new ArrayList<>();
        listAdapter=new ListAdapter(getApplicationContext(),listList);

        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(listAdapter);

        loadList();


        String email=FirebaseAuth.getInstance().getCurrentUser().getEmail();

       addimage=(CircleImageView)findViewById(R.id.ater);

        java.util.List<String> emails=new ArrayList<>();
        emails.add("kbharath326@gmail.com");
        emails.add("jeyavetri226@gmail.com");
        emails.add("codeplays303@gmail.com");

        if (emails.contains(email)){
addimage.setVisibility(View.VISIBLE);

addimage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        loadCard();
    }
});


        }
        else {
            addimage.setVisibility(View.GONE);
        }





    }





    private void loadCard() {

        AlertDialog.Builder alertdialog=new AlertDialog.Builder(this);
        LayoutInflater inflater=this.getLayoutInflater();
        View view=inflater.inflate(R.layout.uploadimage,null);
        alertdialog.setView(view);
        alertdialog.setTitle("Select catergory Image");
        selectimage=view.findViewById(R.id.selectimage);
        edttitle=view.findViewById(R.id.editname);
        edtdesc=view.findViewById(R.id.edtdesc);

        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });





        alertdialog.setPositiveButton("AddCategory", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (TextUtils.isEmpty(edttitle.getText().toString()) && TextUtils.isEmpty(edtdesc.getText().toString())){
                    Toast.makeText(List.this, "Please fill Name ", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();

                }






                else{

                    if (saveuri!=null){

                        final ProgressDialog progressDialog=new ProgressDialog(List.this);
                        progressDialog.setMessage("Please wait....");
                        progressDialog.show();


                        String imagename= UUID.randomUUID().toString();

                        final StorageReference reference=storageReference.child("Image"+imagename);
                        reference.putFile(saveuri)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {

                                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Category");
                                                // DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("New");
                                                FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();


                                                if(firebaseUser.getPhotoUrl() == null){
                                                    String photo="https://lh3.googleusercontent.com/-0lHZU74RIgk/X0HIL16_JhI/AAAAAAAAAAs/r8U9UEMCBas3pwhGBmlq_ln2XqcFEANUgCEwYBhgLKtQDAL1OcqzFVwk8_YwuFLjpt9-1f3qD3z4fIycu2eSj7vvQUSQ59OnsjDsT3ScAvaRByuEbIhiqOq6F0HnQLOftMFlkWxC-Tw97-8qGgqznZQ2NF4NRXypavFoIxrwuccR0gCZfDLzMIntoMSg6beIu8asuTg5js-5cdgnBqxti9CyaFI1O1X3V2TxoUzBkVdKG_VjPXCuyw-L1njot46qgilEfp_ZTZt3nm5Ry4gOnY8XhnG3Ejr4MDDJxMz9T_BGV1QYuU7HoqbYom7cGZhNLxZls1Sycr3fVFuZpLcDR4jLMkKI-9_TrZ0U6B4r3f5fQykyF3Zt2PhXvaNThymrMqBqjxbvLZBdj9tDc1rZ2shNLpYHdmMAfE0F0MUmEuE8x_Vtg2K0AsARfosbXDkqyk9mTgdt849YSgvgN4nQf6ILisOsvzQ31iKakE9ZWhkdIs-cxmkFfVfwQh2Qh93r2w9W2g82yweXPnOqjuEF2eV1Qk0hHfBF6_bLSuXZ4c5xn-pj2iqAXt3pR4b2-H8rRpWR1eZqLeIrwgxT0k2Dp9BeFNZNlv_yR_A7R9oK6L96A8YdTEHSkpGitn9mnffvggq_6x3B_B5VtuOao085jw-tOxt_8MJObh_oF/w140-h139-p/aa.jpeg";
                                                    try {
                                                        URL url=new URL(photo);
                                                        uri1 = Uri.parse( url.toURI().toString() );
                                                    } catch (MalformedURLException | URISyntaxException e) {
                                                        e.printStackTrace();
                                                    }


                                                }
                                                else {
                                                    uri1=firebaseUser.getPhotoUrl();
                                                }
                                                String postid = reference.push().getKey();

                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                hashMap.put("menuId",postid);
                                                hashMap.put("image", uri.toString());
                                                hashMap.put("userName",firebaseUser.getDisplayName());
                                                hashMap.put("description", edtdesc.getText().toString().toUpperCase());
                                                hashMap.put("name",edttitle.getText().toString().toLowerCase());
                                                hashMap.put("imageUrl",uri1.toString());
                                                hashMap.put("publisherid", FirebaseAuth.getInstance().getCurrentUser().getUid());

                                                reference.child(postid).setValue(hashMap);


                                                //ference1.child(postid).setValue(hashMap1);





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

                            }
                        });







                    }


                }








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

//    private void selecteimage() {
//
//        Intent intent=getIntent();
//        intent.setType("./image*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent,"Select Picture"), Common.PICK_IMAGE_REQUEST);
//
//
//
//    }


















    private void uploadImage() {

        AlertDialog.Builder imagedailog=new AlertDialog.Builder(this);
        imagedailog.setTitle("Please Select Image to Upload!!");
        LayoutInflater inflater=this.getLayoutInflater();
        View view=inflater.inflate(R.layout.uploadimage,null);
        imagedailog.setView(view);
        selectimage=view.findViewById(R.id.selectimage);
        final TextView name=view.findViewById(R.id.editname);
        final TextView desc=view.findViewById(R.id.edtdesc);


        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        imagedailog.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (TextUtils.isEmpty(name.getText().toString()) && TextUtils.isEmpty(desc.getText().toString())){
                    Toast.makeText(List.this, "Please fill Name ", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();

                }
                else{

                    if (saveuri!=null){

                        final ProgressDialog progressDialog=new ProgressDialog(List.this);
                        progressDialog.setMessage("Please wait....");
                        progressDialog.show();


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


           if(firebaseUser.getPhotoUrl() == null){
              String photo="https://lh3.googleusercontent.com/-0lHZU74RIgk/X0HIL16_JhI/AAAAAAAAAAs/r8U9UEMCBas3pwhGBmlq_ln2XqcFEANUgCEwYBhgLKtQDAL1OcqzFVwk8_YwuFLjpt9-1f3qD3z4fIycu2eSj7vvQUSQ59OnsjDsT3ScAvaRByuEbIhiqOq6F0HnQLOftMFlkWxC-Tw97-8qGgqznZQ2NF4NRXypavFoIxrwuccR0gCZfDLzMIntoMSg6beIu8asuTg5js-5cdgnBqxti9CyaFI1O1X3V2TxoUzBkVdKG_VjPXCuyw-L1njot46qgilEfp_ZTZt3nm5Ry4gOnY8XhnG3Ejr4MDDJxMz9T_BGV1QYuU7HoqbYom7cGZhNLxZls1Sycr3fVFuZpLcDR4jLMkKI-9_TrZ0U6B4r3f5fQykyF3Zt2PhXvaNThymrMqBqjxbvLZBdj9tDc1rZ2shNLpYHdmMAfE0F0MUmEuE8x_Vtg2K0AsARfosbXDkqyk9mTgdt849YSgvgN4nQf6ILisOsvzQ31iKakE9ZWhkdIs-cxmkFfVfwQh2Qh93r2w9W2g82yweXPnOqjuEF2eV1Qk0hHfBF6_bLSuXZ4c5xn-pj2iqAXt3pR4b2-H8rRpWR1eZqLeIrwgxT0k2Dp9BeFNZNlv_yR_A7R9oK6L96A8YdTEHSkpGitn9mnffvggq_6x3B_B5VtuOao085jw-tOxt_8MJObh_oF/w140-h139-p/aa.jpeg";
               try {
                   URL url=new URL(photo);
                   uri1 = Uri.parse( url.toURI().toString() );
               } catch (MalformedURLException | URISyntaxException e) {
                   e.printStackTrace();
               }


           }
           else {
               uri1=firebaseUser.getPhotoUrl();
           }
                                                String postid = reference.push().getKey();

                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                hashMap.put("postid", postid);
                                                hashMap.put("menuId",Common.CATEGORY_ID_SELECTED);
                                                hashMap.put("image", uri.toString());
                                                hashMap.put("userName",firebaseUser.getDisplayName());
                                                hashMap.put("description", desc.getText().toString().toLowerCase());
                                                hashMap.put("name",name.getText().toString().toLowerCase());
                                                hashMap.put("imageUrl",uri1.toString());
                                                hashMap.put("publisherid", FirebaseAuth.getInstance().getCurrentUser().getUid());

                                                reference.child(postid).setValue(hashMap);


                                                HashMap<String ,Object> hashMap1=new HashMap<>();
                                                hashMap1.put("postid",postid);
                                                reference1.child(postid).setValue(hashMap1);





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

                            }
                        });







                    }


                }














            }
        });


        imagedailog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
dialogInterface.dismiss();





            }
        });

imagedailog.show();



    }

    private void chooseImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),Common.PICK_IMAGE_REQUEST);





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

    private void loadList() {

        Query reference= FirebaseDatabase.getInstance().getReference("Foods").orderByChild("menuId").equalTo(Common.CATEGORY_ID_SELECTED);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                    nr.king.codepaper.Model.List post=dataSnapshot.getValue(nr.king.codepaper.Model.List.class);
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


    @Override
    protected void onStop() {
        listAdapter.notifyDataSetChanged();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        listAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        listAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        listAdapter.notifyDataSetChanged();
    }
}