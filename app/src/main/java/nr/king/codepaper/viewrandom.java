package nr.king.codepaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.service.autofill.Dataset;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import nr.king.codepaper.Adapter.randomwall;
import nr.king.codepaper.Adapter.viewwalladapter;
import nr.king.codepaper.Common.Common;
import nr.king.codepaper.Model.List;

public class viewrandom extends AppCompatActivity {
    ImageView imageView;
    ImageView back,like;
    CircleImageView circleImageView;
    java.util.List<List> listList;
    randomwall listAdapter;
    nr.king.codepaper.Adapter.viewwalladapter viewwalladapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrandom);

     //Toast.makeText(this, ""+ Common.List_ID, Toast.LENGTH_SHORT).show();
  loadImage();


    }

    private void loadImage() {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Foods")
                .child(Common.List_ID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
List list=snapshot.getValue(List.class);
                    imageView=findViewById(R.id.imageview);
                    Glide.with(getApplicationContext())
                            .load(list.getImage())
                            .into(imageView);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}