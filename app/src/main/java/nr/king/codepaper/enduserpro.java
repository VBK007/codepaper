package nr.king.codepaper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import nr.king.codepaper.Adapter.ListAdapter;
import nr.king.codepaper.Common.Common;

public class enduserpro  extends AppCompatActivity {
    TextView username,email,phone,uppid;
    CircleImageView userimage,another;
    RecyclerView recyclerView;
    ImageView back,exit;
    FirebaseUser firebaseUser;
    TextView nrpost,follow,follwers;
    java.util.List<nr.king.codepaper.Model.List> listList;
    ListAdapter listAdapter;
    List<String> list;
    String userid;
    Button editprof,donate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userhome);


        userimage=findViewById(R.id.userimage);
        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        exit=findViewById(R.id.exit);
        phone=findViewById(R.id.phone);
        uppid=findViewById(R.id.upiid);
        recyclerView=findViewById(R.id.recycle);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        nrpost=findViewById(R.id.nrpost);
        follow=findViewById(R.id.nrfollowing);
        follwers=findViewById(R.id.nrfollowers);
another=findViewById(R.id.another);

editprof=findViewById(R.id.editpro);
back=findViewById(R.id.back);
back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(enduserpro.this,Home.class));
        finish();
    }
});
donate=findViewById(R.id.donate);
editprof.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String btn=editprof.getText().toString();

        if (btn.equals("Edit Profile")){
            gotopage();
        }
        else if (btn.equals("follow")){

            FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                    .child("following").child(Common.UID).setValue(true);

            FirebaseDatabase.getInstance().getReference().child("Follow").child(Common.UID)
                    .child("followers").child(firebaseUser.getUid()).setValue(true);

            //addNotifications();


        }
        else if (btn.equals("following")){

            FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                    .child("following").child(Common.UID).removeValue();

            FirebaseDatabase.getInstance().getReference().child("Follow").child(Common.UID)
                    .child("followers").child(firebaseUser.getUid()).removeValue();




        }



    }
});



if (Common.UID.equals(firebaseUser.getUid())){
    loadUserinfo();
    editprof.setText("Edit Profile") ;
    donate.setVisibility(View.GONE);

}
else {

    loadenduserpro();
    checkfollow();

}



        list=new ArrayList<>();

        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        listList=new ArrayList<>();
        listAdapter=new ListAdapter(getApplicationContext(),listList);
        recyclerView.setAdapter(listAdapter);
        loadPost();
        nrposti();
        nrfollowersi();
        nrfollowing();

    }

    private void gotopage() {
        startActivity(new Intent(enduserpro.this,editprofile.class));
        finish();
    }

    private void checkfollow() {

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseUser.getUid()).child("following");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.child(Common.UID).exists())
                {
                    editprof.setText("following");
                }
                else {
                    editprof.setText("follow");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    private void loadenduserpro() {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users").child(Common.UID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                USer uSer=snapshot.getValue(USer.class);

                if (uSer.getImageUrl()!=null){
                    Glide.with(getApplicationContext())
                            .load(uSer.getImageUrl())
                            .into(userimage);
                    another.setVisibility(View.GONE);
                    userimage.setVisibility(View.VISIBLE);

                }
                    username.setText(uSer.getUserName());
                    email.setText(uSer.getEmail());
                    phone.setText(uSer.getPhone());
                    if (uSer.getUppid()!=null) {


                        uppid.setText(uSer.getUppid());

                    }
                    else {
                        uppid.setVisibility(View.GONE);
                    }


//                    exit.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//
//                            logout();
//
//
//                        }
//                    });
                    exit.setVisibility(View.GONE);


                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void loadUserinfo() {

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users").child(Common.UID);
reference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        USer uSer=snapshot.getValue(USer.class);
       if(firebaseUser.getUid().equals(Common.USER_ID)){
           if (uSer.getImageUrl()!=null){
               Glide.with(getApplicationContext())
                       .load(uSer.getImageUrl())
                       .into(userimage);
               another.setVisibility(View.GONE);
               userimage.setVisibility(View.VISIBLE);

           }


           username.setText(uSer.getUserName());
           email.setText(uSer.getEmail());
           phone.setText(uSer.getPhone());
           if (uSer.getUppid()!=null) {


               uppid.setText(uSer.getUppid());

           }
           else {
               uppid.setVisibility(View.GONE);
           }
       }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});





//
//        if (firebaseUser.getPhotoUrl()==null){
//            userimage.setImageResource(R.mipmap.ic_launcher);
//        }
//        else {
//            Glide.with(getApplicationContext())
//                    .load(firebaseUser.getPhotoUrl())
//                    .into(userimage);
//
//        }
//
//        username.setText(firebaseUser.getDisplayName());
//        email.setText(firebaseUser.getEmail());
//        phone.setText(uSer.getPhone());
//        uppid.setText(uSer.getUppid());

exit.setVisibility(View.GONE);



    }

    private void logout() {
        AlertDialog.Builder alertdialog=new AlertDialog.Builder(getApplicationContext());
        alertdialog.setTitle("Logout");
        alertdialog.setMessage("Are you sure you want to Logout " + firebaseUser.getDisplayName());
        alertdialog.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(),Home.class);
                startActivity(intent);

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

    private void nrfollowing() {

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference()
                .child("Follow")
                .child(Common.UID)
                .child("followers");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                follwers.setText(""+snapshot.getChildrenCount());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference reference1=FirebaseDatabase.getInstance().getReference()
                .child("Follow")
                .child(Common.UID)
                .child("following");


        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                follow.setText(""+snapshot.getChildrenCount());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    private void nrfollowersi() {

    }

    private void nrposti() {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Foods");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                int i=0;
                for (DataSnapshot snapshot:datasnapshot.getChildren()){
                    nr.king.codepaper.Model.List post=snapshot.getValue(nr.king.codepaper.Model.List.class);

                    if (post.getPublisherid().equals(Common.UID)){

                        i++;
                    }


                }

                nrpost.setText(""+i);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void loadPost() {
        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Foods");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){

                    nr.king.codepaper.Model.List post=snapshot1.getValue(nr.king.codepaper.Model.List.class);

                    if (Common.UID.equals(post.getPublisherid())){
                        listList.add(post);
                    }

                }
                listAdapter.notifyDataSetChanged();
                Collections.reverse(listList);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void loadSave() {


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Saved").child(Common.UID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    list.add(dataSnapshot.getKey());

                    //  Toast.makeText(getContext(), ""+list, Toast.LENGTH_SHORT).show();

                }
                readSave();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readSave() {
//        String id="-L6SE1mgsskVaqC3EN5y";
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Foods");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                    nr.king.codepaper.Model.List post=dataSnapshot.getValue(nr.king.codepaper.Model.List.class);

                    for(String id:list) {

//                     if (post.getPostid().equals(id))
//                        {
//                            listList.add(post);
//                  }


                        if (id.equals(post.getPostid())){
                            listList.add(post);
                        }

                        // Toast.makeText(getContext(), ""+post.getPostid(), Toast.LENGTH_SHORT).show();




                    }


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
