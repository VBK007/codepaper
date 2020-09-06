package nr.king.codepaper.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nr.king.codepaper.Adapter.StageredRecyclerView;
import nr.king.codepaper.Adapter.randomwall;
import nr.king.codepaper.Adapter.randomwalli;
import nr.king.codepaper.R;

public class explore extends Fragment {
List<nr.king.codepaper.Model.List> listList;
StageredRecyclerView randomwall;
RecyclerView recyclerView;
MaterialSearchBar searchBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater
               .inflate(R.layout.explore,container,false);

       searchBar=view.findViewById(R.id.search);

       searchBar.addTextChangeListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
searchUser(charSequence.toString().toLowerCase());


           }

           @Override
           public void afterTextChanged(Editable editable) {

           }
       });



       recyclerView=view.findViewById(R.id.recycle);
        listList=new ArrayList<>();
       // RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getContext(),3);
       RecyclerView.LayoutManager layoutManager=new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        randomwall=new StageredRecyclerView(getContext(),listList);
       recyclerView.setLayoutManager(layoutManager);



//       Context context=recyclerView.getContext();
      // LayoutAnimationController controller= AnimationUtils.loadLayoutAnimation(context,R.anim.layout_fall);

        recyclerView.setAdapter(randomwall);

      // recyclerView.setLayoutAnimation(controller);

recyclerView.getAdapter().notifyDataSetChanged();
recyclerView.scheduleLayoutAnimation();



       //runAnimation(recyclerView,0);


       loadPost();

readPost();

        return view;
    }

//    private void runAnimation(RecyclerView recyclerView, int type) {
//        Context context=recyclerView.getContext();
//        LayoutAnimationController controller=null;
//        randomwall=new randomwalli(getContext(),listList);
//        recyclerView.setAdapter(randomwall);
//
//        recyclerView
//
//    }

    private void readPost() {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Foods");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (searchBar.getText().equals("")){
                    listList.clear();
                    for (DataSnapshot snapshot:datasnapshot.getChildren()){
                        nr.king.codepaper.Model.List user=snapshot.getValue(nr.king.codepaper.Model.List.class);
                        listList.add(user);
                    }


                    randomwall.notifyDataSetChanged();
                    Collections.reverse(listList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void searchUser(String s) {
        Query query= FirebaseDatabase.getInstance().getReference("Foods").orderByChild("name")
                .startAt(s)
                .endAt(s+"\uf8ff");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                listList.clear();
                for (DataSnapshot snapshot:datasnapshot.getChildren()){
                    nr.king.codepaper.Model.List user=snapshot.getValue(nr.king.codepaper.Model.List.class);
                    listList.add(user);
                }
                randomwall.notifyDataSetChanged();
                Collections.reverse(listList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }










    private void loadPost() {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Foods");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {

                    nr.king.codepaper.Model.List post= dataSnapshot.getValue(nr.king.codepaper.Model.List.class);
                    listList.add(post);
                }
               Collections.reverse(listList);
                randomwall.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }







    @Override
    public void onStart() {
        super.onStart();
        randomwall.notifyDataSetChanged();
    }


    @Override
    public void onPause() {
        super.onPause();
        randomwall.notifyDataSetChanged();

    }


    @Override
    public void onResume() {
        super.onResume();
        randomwall.notifyDataSetChanged();

    }

    @Override
    public void onStop() {
        super.onStop();
        randomwall.notifyDataSetChanged();

    }



}
