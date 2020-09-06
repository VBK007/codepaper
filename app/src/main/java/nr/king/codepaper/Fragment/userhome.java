package nr.king.codepaper.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import nr.king.codepaper.Home;
import nr.king.codepaper.Model.USer;
import nr.king.codepaper.Model.User;
import nr.king.codepaper.R;
import nr.king.codepaper.editprofile;

public class userhome extends Fragment {
TextView username,email,phone,uppid;
CircleImageView userimage,another;
RecyclerView recyclerView;
ImageView back,exit;
FirebaseUser firebaseUser;
TextView nrpost,follow,follwers;
List<nr.king.codepaper.Model.List>  listList;
ListAdapter listAdapter;
List<String> list;
Button editprof,donate;
String userid;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater
                .inflate(R.layout.userhome,container,false);
userimage=view.findViewById(R.id.userimage);
username=view.findViewById(R.id.username);
email=view.findViewById(R.id.email);
exit=view.findViewById(R.id.exit);
phone=view.findViewById(R.id.phone);
uppid=view.findViewById(R.id.upiid);
recyclerView=view.findViewById(R.id.recycle);
firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
nrpost=view.findViewById(R.id.nrpost);
follow=view.findViewById(R.id.nrfollowing);
follwers=view.findViewById(R.id.nrfollowers);
editprof=view.findViewById(R.id.editpro);
donate=view.findViewById(R.id.donate);
donate.setVisibility(View.GONE);
another=view.findViewById(R.id.another);

editprof.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(getContext(), editprofile.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
});

exit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
//FirebaseAuth.getInstance().signOut();
//        Intent intent=new Intent(getContext(),Home.class);
//        startActivity(intent);


        logout();


    }
});

userid=firebaseUser.getUid();


       if (firebaseUser!=null){
           loadUserinfo();
       }




//loadUserinfo();


list=new ArrayList<>();

        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getContext(),3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        listList=new ArrayList<>();
        listAdapter=new ListAdapter(getContext(),listList);
        recyclerView.setAdapter(listAdapter);
//        TextView userno=view.findViewById(R.id.userno);
//        userno.setText(firebaseUser.getPhoneNumber());

//loadSave();
       loadPost();
nrposti();
nrfollowersi();
 nrfollowing();






        return view;
    }

    private void loadUserinfo() {

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

reference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        USer user=snapshot.getValue(USer.class);
        if (getActivity() == null) {
            return ;
        }else{


            if (user.getImageUrl()==null){

            }
            else {
                Glide.with(getContext())
                        .load(user.getImageUrl())
                        .into(userimage);
                userimage.setVisibility(View.VISIBLE);
                another.setVisibility(View.GONE);

            }




            username.setText(user.getUserName());
            email.setText(user.getEmail());
            phone.setText(user.getPhone());
            if (user.getUppid()!=null){
                uppid.setText(user.getUppid());
            }
            else {
                uppid.setText("Please add upid to create Online Transaction");
            }


        }


    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});


//       if (Common.currentUser!=null){
//           if (Common.currentUser.getImageUrl()==null){
//               userimage.setImageResource(R.mipmap.ic_launcher);
//           }
//           else {
//               Glide.with(getContext())
//                       .load(Common.currentUser.getImageUrl())
//                       .into(userimage);
//
//           }
//
//           username.setText(Common.currentUser.getUserName());
//           email.setText(Common.currentUser.getEmail());
//
//
//       }



    }

    private void logout() {
        AlertDialog.Builder alertdialog=new AlertDialog.Builder(getContext());
        alertdialog.setTitle("Logout");
        alertdialog.setMessage("Are you sure you want to Logout " + firebaseUser.getDisplayName());
        alertdialog.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(getContext(),Home.class);
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
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
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
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
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

                    if (post.getPublisherid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){

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

                    if (firebaseUser.getUid().equals(post.getPublisherid())){
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


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Saved").child(firebaseUser.getUid());
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
