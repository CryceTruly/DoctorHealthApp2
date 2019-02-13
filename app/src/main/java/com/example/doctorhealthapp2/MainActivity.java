package com.example.doctorhealthapp2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.doctorhealthapp2.Common.Common;
import com.example.doctorhealthapp2.Model.Patient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    Button signUp, Register;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    RelativeLayout rootLayout;
    private Intent WelcomeActivity;
    public AlertDialog waitingDialog;
    ProgressBar loading;
    private static final int PERMISSION = 1000;

    @Override
    protected void attachBaseContext(Context newBase) {


        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //before set content view
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Arkhip.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

       auth = FirebaseAuth.getInstance();
       // WelcomeActivity = new Intent(this,Welcome.class);
        db = FirebaseDatabase.getInstance();
        users = db.getReference(Common.user_patient_tb1);
        Register = findViewById(R.id.btn_Reg);
        signUp = findViewById(R.id.btn_signIn);
        loading = findViewById(R.id.progressing);
        //loading.setVisibility(View.INVISIBLE);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterDialog();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginDialog();
            }
        });

    }
    private void showLoginDialog() {


        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("LOG IN");
        dialog.setMessage("Please use email to login");

        LayoutInflater inflater = LayoutInflater.from(this);

        View login_layout = inflater.inflate(R.layout.layout_login,null);
        final MaterialEditText editEmail = login_layout.findViewById(R.id.editEmail);
        final MaterialEditText editPassword = login_layout.findViewById(R.id.editpassword);
        rootLayout = findViewById(R.id.rootLayout);
        dialog.setView(login_layout);


        dialog.setPositiveButton("LOG IN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                //set login button disabled
                signUp.setEnabled(false);

                //check validation
                final  String mail = editEmail.getText().toString();
                final  String password = editPassword.getText().toString();

                if (TextUtils.isEmpty(editEmail.getText().toString())) {
                    Snackbar.make(rootLayout, "Please enter email", Snackbar.LENGTH_SHORT).show();
                    return;

                }
                else if (TextUtils.isEmpty(editPassword.getText().toString())) {
                    Snackbar.make(rootLayout, "Please enter password", Snackbar.LENGTH_SHORT).show();
                    return;

                }else if (editPassword.getText().toString().length() < 6) {
                    Snackbar.make(rootLayout, "password cant be less than 6 characters", Snackbar.LENGTH_SHORT).show();
                    return;

                }else {


                    /// Open sign in activity
                    signIn(mail,password);


                    // lOGIN
//                auth.signInWithEmailAndPassword(editEmail.getText().toString(),editPassword.getText().toString())
//                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                            @Override
//                            public void onSuccess(AuthResult authResult) {
//                               // waitingDialog.dismiss();
//
//                                startActivity(new Intent(MainActivity.this,Welcome.class));
//                                finish();
//
//
//
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                      //  waitingDialog.dismiss();
//                        Snackbar.make(rootLayout,"Fialed"+e.getMessage(),Snackbar.LENGTH_SHORT).show();
//                        signUp.setEnabled(true);
//                    }
//                });


                }
            }}).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        dialog.show();




    }

    private void signIn(String mail, String password) {
        //loading.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                   // loading.setVisibility(View.INVISIBLE);
                    Intent welcome = new Intent(MainActivity.this,Welcome.class);
                    startActivity(welcome);



                }else {
                    showMessage(task.getException().getMessage());
                    //loading.setVisibility(View.INVISIBLE);
                }
            }
        }) ;


    }
    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    private void showRegisterDialog() {


        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("REGISTER");
        dialog.setMessage("Please use email to register");

        LayoutInflater inflater = LayoutInflater.from(this);

        View register_layout = inflater.inflate(R.layout.layout_register,null);
        final MaterialEditText editEmail = register_layout.findViewById(R.id.editEmail);
        final MaterialEditText editName = register_layout.findViewById(R.id.editName);
        final MaterialEditText editPhone = register_layout.findViewById(R.id.editphne);
        final MaterialEditText editPassword = register_layout.findViewById(R.id.editpassword);
        final MaterialEditText confirmPassword = register_layout.findViewById(R.id.confirmPassword);
        rootLayout = findViewById(R.id.rootLayout);
        dialog.setView(register_layout);


        dialog.setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

                //check validation

                if(TextUtils.isEmpty(editEmail.getText().toString())){
                    Snackbar.make(rootLayout,"Please enter email",Snackbar.LENGTH_SHORT).show();
                    return;

                }else if(TextUtils.isEmpty(editName.getText().toString())){
                    Snackbar.make(rootLayout,"Please enter name",Snackbar.LENGTH_SHORT).show();
                    return;

                }else if(TextUtils.isEmpty(editPassword.getText().toString())){
                    Snackbar.make(rootLayout,"Please enter password",Snackbar.LENGTH_SHORT).show();
                    return;

                }else if(TextUtils.isEmpty(editPhone.getText().toString())){
                    Snackbar.make(rootLayout,"Please enter phone",Snackbar.LENGTH_SHORT).show();
                    return;

                }else if(editPassword.getText().toString().length()<6){
                    Snackbar.make(rootLayout,"password cant be lessthan 6 characters",Snackbar.LENGTH_SHORT).show();
                    return;

                }  else if(!confirmPassword.getText().toString().equals(editPassword.getText().toString())){
                    Snackbar.make(rootLayout,"Password fields must be matching",Snackbar.LENGTH_SHORT).show();
                    return;

                }
                else {
                    //Register user

                    //Display progress bar
                   // loading.setVisibility(View.VISIBLE);
                    auth.createUserWithEmailAndPassword(editEmail.getText().toString(),editPassword.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {

                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    //loading.setVisibility(View.INVISIBLE);
                                    Patient patient = new Patient();
                                    patient.setEmail(editEmail.getText().toString());
                                    patient.setPassword(editPassword.getText().toString());
                                    patient.setName(editName.getText().toString());
                                    patient.setPhone(editPhone.getText().toString());

                                    //user email to key
                                    users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(patient).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Snackbar.make(rootLayout,"Registeration successfull",Snackbar.LENGTH_SHORT).show();


                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            //loading.setVisibility(View.INVISIBLE);
                                            Snackbar.make(rootLayout,"Registeration failed"+e.getMessage(),Snackbar.LENGTH_SHORT).show();

                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                          //  loading.setVisibility(View.INVISIBLE);
                            Snackbar.make(rootLayout,"Registeration failed"+e.getMessage(),Snackbar.LENGTH_SHORT).show();

                        }
                    });



                }}
        });
        dialog.setNegativeButton("CANCAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }



}
