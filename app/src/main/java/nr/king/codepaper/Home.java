package nr.king.codepaper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import nr.king.codepaper.Common.Common;
import nr.king.codepaper.Fragment.addPost;
import nr.king.codepaper.Fragment.explore;
import nr.king.codepaper.Fragment.favorite;
import nr.king.codepaper.Fragment.homefrag;
import nr.king.codepaper.Fragment.userhome;

public class Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
FirebaseAuth mauth;
FirebaseUser firebaseUser;
private CircleImageView circleImageView;
private TextView txtUsername;
private Uri uri1;
Paper paper;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        loadFragment(new homefrag());
     BottomNavigationView   bottomNavigationView=findViewById(R.id.botview);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        List<String> email=new ArrayList<>();
        email.add("kbharath326@gmail.com");
        email.add("codeplays326@gmail.com");
        email.add("jeyavetri226@gmail.com");

      // String Useremail=FirebaseAuth.getInstance().getCurrentUser().getEmail();

Paper.init(this);



    }


    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseUser==null){
            // startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false).build(),Common.SIGN_INREQUEST_CODE);

            startActivity(new Intent(Home.this,login.class));

finish();

        }






    }

    private boolean loadFragment(Fragment fragment) {


      if (fragment!=null){
          getSupportFragmentManager().beginTransaction().replace(R.id.fralay,fragment).commit();
          return true;
      }
       return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode==Common.SIGN_INREQUEST_CODE && resultCode==RESULT_OK){

//startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false).build(),Common.SIGN_INREQUEST_CODE);

        }
    }


    private void loadUserInfo() {

      reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

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


                HashMap<String,Object> hashMap=new HashMap<>();
                hashMap.put("email",FirebaseAuth.getInstance().getCurrentUser().getEmail());
                hashMap.put("userName",firebaseUser.getDisplayName());
                hashMap.put("userid",firebaseUser.getUid());
                hashMap.put("imageUrl",uri1.toString());
               reference.setValue(hashMap);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment=null;
        switch (item.getItemId()){
            case R.id.home:
                fragment=new homefrag();
                break;


            case R.id.explore:
                fragment=new explore();
                break;
            case R.id.add:
               startActivity(new Intent(Home.this,addPost.class));
                break;

            case R.id.favorite:
                fragment=new favorite();
                break;

            case R.id.user:
                fragment=new userhome();
                break;




        }


        return loadFragment(fragment);
    }
}