package nr.king.codepaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import nr.king.codepaper.Common.Common;
import nr.king.codepaper.Fragment.addPost;
import nr.king.codepaper.Model.List;

public class Descpost extends AppCompatActivity {

    MaterialEditText edtname,edtamount;
    String name,money;
    RadioButton sale,free;
    ImageView image;
    ImageView cancel,check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descpost);
        edtname=findViewById(R.id.name);
        image=findViewById(R.id.image);
        edtamount=findViewById(R.id.amount);
        free=findViewById(R.id.free);
        sale=findViewById(R.id.sale);
        cancel=findViewById(R.id.cancel);
        check=findViewById(R.id.ok);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData();
            }
        });

cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(Descpost.this,Home.class));
        finish();
    }
});

        Glide.with(getApplicationContext())
                .load(Common.image)
                .into(image);

        loadData();







    }

    private void uploadData() {
        name=edtname.getText().toString().toLowerCase();
        if (edtamount.getText().toString()!=null){
            money=edtamount.getText().toString();
        }

        final AlertDialog progressDialog=new SpotsDialog.Builder()
                .setContext(Descpost.this)
                .setMessage("Please wait")
                .setCancelable(false).build();
        progressDialog.show();


        if (free.isChecked()){

            Map<String, Object> hashMap = new HashMap<>();

            hashMap.put("name",name);
            hashMap.put("isSale",false);
            DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Foods");
            reference.child(Common.List_ID).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    progressDialog.dismiss();
                    Toast.makeText(Descpost.this, "Uploaded Sucessfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Descpost.this,Home.class));
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Descpost.this, "Failed" +e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });



        }


        else if (sale.isChecked()){


            Map<String, Object> hashMap = new HashMap<>();

            hashMap.put("name",name);
            hashMap.put("money",money);
            hashMap.put("isSale",true);
            DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Foods");
            reference.child(Common.List_ID).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    progressDialog.dismiss();
                    Toast.makeText(Descpost.this, "Uploaded Sucessfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Descpost.this,Home.class));
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Descpost.this, "Failed" +e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });




        }












    }


    private void loadData() {

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Foods").child(Common.List_ID);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List list=snapshot.getValue(List.class);

                if (list.isSale()){
                    sale.setChecked(true);

                    edtname.setText(list.getName());
                    edtamount.setText(list.getMoney());

                }

                else {
                    free.setChecked(true);

                    edtamount.setVisibility(View.GONE);
                    edtname.setVisibility(View.VISIBLE);
                    edtname.setText(list.getName());

                }









            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.sale:
                if (checked)
                    edtamount.setVisibility(View.VISIBLE);
                checkeditamount();
                break;
            case R.id.free:
                if (checked)
                    edtamount.setVisibility(View.GONE);
                checkedd();


                break;
        }


    }

    private void checkedd() {
        if (TextUtils.isEmpty(edtname.getText().toString())){
            check.setVisibility(View.INVISIBLE);

        }

        else{
            check.setVisibility(View.VISIBLE);
        }
    }

    private void checkeditamount() {

        if (TextUtils.isEmpty(edtname.getText().toString()) && TextUtils.isEmpty(edtamount.getText().toString())){
            check.setVisibility(View.INVISIBLE);

        }

        else {
            check.setVisibility(View.VISIBLE);
        }


    }

}