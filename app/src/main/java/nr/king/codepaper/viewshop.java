package nr.king.codepaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import nr.king.codepaper.Adapter.DemoAdapter;
import nr.king.codepaper.Model.Demo;

public class viewshop extends AppCompatActivity {
RecyclerView recyclerView;
List<Demo> demos;
ImageView back;
DemoAdapter demoAdapter;
FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewshop);

back=findViewById(R.id.back);
firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        recyclerView=findViewById(R.id.demo);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.setHasFixedSize(true);
demos=new ArrayList<>();
demoAdapter=new DemoAdapter(getApplicationContext(),demos);

recyclerView.setAdapter(demoAdapter);
loadState();
back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(viewshop.this,editprofile.class));
        finish();
    }
});


    }

    private void loadState() {

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Demos");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Demo demo=dataSnapshot.getValue(Demo.class);
                    demos.add(demo);


                }
               demoAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}