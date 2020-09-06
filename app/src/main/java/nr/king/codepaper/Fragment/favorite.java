package nr.king.codepaper.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.data.DataBufferObserverSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import nr.king.codepaper.Adapter.ListAdapter;
import nr.king.codepaper.R;

public class favorite extends Fragment {
List<nr.king.codepaper.Model.List> listList;
ListAdapter listAdapter;
RecyclerView recyclerView;
CircleImageView circleImageView;
FirebaseUser firebaseUser;
List<String> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater
                .inflate(R.layout.favorite,container,false);

        recyclerView=view.findViewById(R.id.recycle);
        circleImageView=view.findViewById(R.id.userimage);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        Glide.with(getContext())
                .load(firebaseUser.getPhotoUrl())
                .into(circleImageView);

        list = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getContext(),3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        listList=new ArrayList<>();
        listAdapter=new ListAdapter(getContext(),listList);
recyclerView.setAdapter(listAdapter);

        loadSave();


        return view;
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
