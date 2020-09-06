package nr.king.codepaper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.rengwuxian.materialedittext.MaterialEditText;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import nr.king.codepaper.Adapter.CategoryAdapter;
import nr.king.codepaper.Common.Common;

public class Category extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    CircleImageView circleImageView;
    TextView username;
    List<nr.king.codepaper.Model.Category> categoryList;
    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    FirebaseDatabase firebaseDatabase;
    CircleImageView addimage;
    ImageView selectimage;
    MaterialEditText edttitle,edtdesc;
    Uri saveuri,uri1;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        circleImageView=findViewById(R.id.userimage);
        username=findViewById(R.id.username);
        recyclerView=findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        storageReference= FirebaseStorage.getInstance().getReference("Category");
        addimage=findViewById(R.id.add);

        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadCard();
            }
        });



        LinearLayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),2);
        //layoutManager.setReverseLayout(true);
        //layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        categoryList=new ArrayList<>();


        categoryAdapter=new CategoryAdapter(getApplicationContext(),categoryList);
        recyclerView.setAdapter(categoryAdapter);


        loadUserInfo();

        loadCategory();






    }
    private void loadCategory() {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Category");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot:datasnapshot.getChildren())
                {
                    nr.king.codepaper.Model.Category post=snapshot.getValue(nr.king.codepaper.Model.Category.class);
                    categoryList.add(post);



                }

                categoryAdapter.notifyDataSetChanged();
                Collections.reverse(categoryList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }

    @Override
    public void onStop() {
        super.onStop();
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        categoryAdapter.notifyDataSetChanged();
    }




    private void loadUserInfo() {
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        if (FirebaseAuth.getInstance().getCurrentUser()!=null){

            if (FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()!=null){
                Glide.with(getApplicationContext()).
                        load(FirebaseAuth.getInstance()
                                .getCurrentUser().getPhotoUrl())
                        .into(circleImageView);

            }
            else {
                circleImageView.setImageResource(R.mipmap.ic_launcher);
            }




            if (FirebaseAuth.getInstance().getCurrentUser().getDisplayName()!=null){
                username.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

            }
            else {
                username.setText("Codeplays");
            }



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
                selecteimage();
            }
        });

        alertdialog.setPositiveButton("AddCategory", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (TextUtils.isEmpty(edttitle.getText().toString()) && TextUtils.isEmpty(edtdesc.getText().toString())){
                    Toast.makeText(Category.this, "Please fill Name ", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();

                }
                else{

                    if (saveuri!=null){

                        final ProgressDialog progressDialog=new ProgressDialog(Category.this);
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
                                                hashMap.put("description", edtdesc.getText().toString().toLowerCase());
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

    private void selecteimage() {

        Intent intent=getIntent();
        intent.setType("./image*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), Common.PICK_IMAGE_REQUEST);



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




}