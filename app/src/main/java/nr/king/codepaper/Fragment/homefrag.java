package nr.king.codepaper.Fragment;

import android.graphics.Path;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
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
import nr.king.codepaper.Adapter.CategoryAdapter;
import nr.king.codepaper.Common.Common;
import nr.king.codepaper.Model.Category;
import nr.king.codepaper.Model.User;
import nr.king.codepaper.R;
import nr.king.codepaper.USer;

public class homefrag extends Fragment {
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    CircleImageView circleImageView;
    TextView username;
    List<Category> categoryList;
    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    FirebaseDatabase firebaseDatabase;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.homefrag,container,false);
        circleImageView=view.findViewById(R.id.userimage);
        username=view.findViewById(R.id.username);
        recyclerView=view.findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);

firebaseUser=FirebaseAuth.getInstance().getCurrentUser();



        RecyclerView.LayoutManager layoutManager=new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        //layoutManager.setReverseLayout(true);
        //layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        categoryList=new ArrayList<>();


        categoryAdapter=new CategoryAdapter(getContext(),categoryList);
        recyclerView.setAdapter(categoryAdapter);
            //Toast.makeText(getContext(), "" + firebaseUser.getDisplayName(), Toast.LENGTH_SHORT).show();

//        username.setText(firebaseUser.getDisplayName());
//
//        Glide.with(getActivity()).
//                load(firebaseUser.getPhotoUrl())
//                .into(circleImageView);








//      if (Common.currentUser!=null){
//          username.setText(Common.currentUser.getUserName());
//          if (Common.currentUser.getImageUrl()!=null){
//              Glide.with(getContext())
//                      .load(Common.currentUser.getImageUrl())
//                      .into(circleImageView);
//          }
//          else {
//              circleImageView.setImageResource(R.mipmap.ic_launcher);
//          }


     // }


        loadCategory();
        if (firebaseUser!=null){
            loadUserInfo();
        }

        return view;
    }

    private void loadCategory() {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Category");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot:datasnapshot.getChildren())
                {
                    Category post=snapshot.getValue(Category.class);
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

    @Override
    public void onDetach() {
        super.onDetach();

    }

    private void loadUserInfo() {


        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                USer user=snapshot.getValue(USer.class);
                if (getActivity() == null) {
                    return ;
                }
                else{
                    if (user.getImageUrl()!=null){
                        Glide.with(getActivity())
                                .load(user.getImageUrl())
                                .into(circleImageView);
                    }
                    else {
                        circleImageView.setImageResource(R.mipmap.ic_launcher);
                    }

                    username.setText(user.getUserName());

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }


}
