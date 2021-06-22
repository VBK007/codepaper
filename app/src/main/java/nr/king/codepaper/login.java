package nr.king.codepaper;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import nr.king.codepaper.Common.Common;

public class login extends AppCompatActivity {
Button lreg,login;
ConstraintLayout rootlayout;
    FirebaseAuth auth;
    FirebaseDatabase db;
    FirebaseUser firebaseUser;
    DatabaseReference users;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lreg=findViewById(R.id.reg);
        login=findViewById(R.id.log);
rootlayout=findViewById(R.id.rootlayout);
        lreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
registerLay();
                
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginlay();
            }
        });


        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        final DatabaseReference staff_user = database.getReference("User");
//
//
//        String user = Paper.book().read(Common.STAFF_KEY);
//        String pwd = Paper.book().read(Common.pwd_KEY);
//
//        if (user != null && pwd != null) {
//
//            if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pwd)) {
//                autoLogin(user, pwd);
//            }
//        }


    }

    private void loginlay() {

        AlertDialog.Builder alertdialog=new AlertDialog.Builder(this);
        alertdialog.setTitle("LoginForm");
        LayoutInflater layoutInflater=LayoutInflater.from(this);
        final View login_input=layoutInflater.inflate(R.layout.login_input,null);
        alertdialog.setView(login_input);
        final MaterialEditText edtemail=login_input.findViewById(R.id.edtEmail);
        final MaterialEditText edtpass=login_input.findViewById(R.id.edtpassword);




        alertdialog.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {



                final android.app.AlertDialog mDialog = new SpotsDialog.Builder()
                        .setContext(login.this)
                        .setMessage("Please wait")
                        .setCancelable(false).build();
                mDialog.show();

                if (TextUtils.isEmpty(edtemail.getText().toString())) {
                    Snackbar.make(rootlayout, "Please enter email address", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }

                if (TextUtils.isEmpty(edtpass.getText().toString())) {
                    Snackbar.make(rootlayout, "Please enter password", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (edtpass.getText().toString().length() < 6) {
                    Snackbar.make(rootlayout, "password too short!!", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }



                auth.signInWithEmailAndPassword(edtemail.getText().toString(), edtpass.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {



                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                                Paper.book().write(Common.STAFF_KEY,edtemail.getText().toString());
                                                Paper.book().write(Common.pwd_KEY,edtpass.getText().toString());
                                                // if (datasnapshot.child(edtemail.getText().toString()).exists()) {
                                                mDialog.dismiss();
                                                //btnsig.setEnabled(false);
//                                                    User staff = datasnapshot.child(edtemail.getText().toString()).getValue(User.class);
//                                                    if (staff.getPassword().equals(edtpass.getText().toString())) {
                                                Intent intent=new Intent(login.this,Home.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                Common.currentUser = datasnapshot.getValue(USer.class);

startActivity(intent);
                                                Toast.makeText(login.this, "Login Sucessfully", Toast.LENGTH_SHORT).show();
                                                finish();








                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

//                                startActivity(new Intent(loginreg.this, mainmain.class));
//                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(rootlayout, "Failed" + e.getMessage(), Snackbar.LENGTH_SHORT)
                                        .show();
                                mDialog.dismiss();
                            }
                        });




            }
        });
        alertdialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });





//                staff_user.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.child(edtemail.getText().toString()).exists()) {
//
//                            mDialog.dismiss();
//
//                            User staff = dataSnapshot.child(edtemail.getText().toString()).getValue(User.class);
//                            //staff.setAvatarUrl("");
//                           // staff.setRates("0");
//
//                            if (staff.getPassword().equals(edtpass.getText().toString())) {
//                                Intent homestaff = new Intent(loginreg.this,mainmain.class);
//                                Common.currentUser= staff;
//                                startActivity(homestaff);
//                                Toast.makeText(loginreg.this, "LoginSuccesfull!", Toast.LENGTH_SHORT).show();
//                                finish();
//
//                                staff_user.removeEventListener(this);
//                            } else {
//                                Toast.makeText(loginreg.this, "LoginFailed!", Toast.LENGTH_SHORT).show();
//                            }
//
//                        } else {
//                            Toast.makeText(loginreg.this, "User not Found!", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });







        alertdialog.show();

    }
    private void registerLay() {
        final AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("LoginForm");
        LayoutInflater layoutInflater=LayoutInflater.from(this);
        final View register_input=layoutInflater
                .inflate(R.layout.layout_register,null);
        dialog.setView(register_input);
        dialog.setPositiveButton("Register Details", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {





                final MaterialEditText edtemail = register_input.findViewById(R.id.edtEmail);
        final MaterialEditText edtpass = register_input.findViewById(R.id.edtpassword);
        final MaterialEditText edtphone = register_input.findViewById(R.id.edtphone);
        final MaterialEditText edtname = register_input.findViewById(R.id.edtUsername);
       dialog.setView(register_input);
                 dialogInterface.dismiss();


                final android.app.AlertDialog progressDialog =new SpotsDialog.Builder()
                        .setContext(login.this)
                        .setMessage("Please wait")
                        .setCancelable(false).build();
                progressDialog.show();


                if (TextUtils.isEmpty(edtemail.getText().toString())) {
                    progressDialog.dismiss();
                    Snackbar.make(rootlayout, "Please enter email address", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }

                if (TextUtils.isEmpty(edtpass.getText().toString())) {
                    progressDialog.dismiss();
                    Snackbar.make(rootlayout, "Please enter password", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (edtpass.getText().toString().length() < 6) {
                    progressDialog.dismiss();
                    Snackbar.make(rootlayout, "password too short!!", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (TextUtils.isEmpty(edtphone.getText().toString())) {
                    progressDialog.dismiss();
                    Snackbar.make(rootlayout, "Please enter phone no", Snackbar.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(edtname.getText().toString())) {
                    progressDialog.dismiss();
                    Snackbar.make(rootlayout, "Please enter UserName", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(edtemail.getText().toString(), edtpass.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                firebaseUser = auth.getCurrentUser();
                                String userid = firebaseUser.getUid();
                                final USer user = new USer();
                                user.setEmail(edtemail.getText().toString());
                                user.setPassword(edtpass.getText().toString());
                                user.setUserName(edtname.getText().toString());
                                user.setPhone(edtphone.getText().toString());
                                user.setUserid(userid);
                                progressDialog.dismiss();
                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Paper.book().write(Common.STAFF_KEY,edtemail.getText().toString());
                                                Paper.book().write(Common.pwd_KEY,edtpass.getText().toString());


                                                Intent intent=new Intent(login.this,Home.class);
                                                Common.currentUser=user;
                                                startActivity(intent);
                                                Snackbar.make(rootlayout, "Register Success fully", Snackbar.LENGTH_SHORT)
                                                        .show();
                                                progressDialog.dismiss();

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Snackbar.make(rootlayout, "Failes" + e.getMessage(), Snackbar.LENGTH_SHORT)
                                                        .show();

                                            }
                                        });


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Snackbar.make(rootlayout, "Failes" + e.getMessage(), Snackbar.LENGTH_SHORT)
                                        .show();

                            }
                        });

            }
        });
        dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.show();



    }
}
