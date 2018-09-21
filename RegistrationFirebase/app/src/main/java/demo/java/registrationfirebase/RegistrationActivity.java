package demo.java.registrationfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import demo.java.registrationfirebase.Model.Data;


public class RegistrationActivity extends AppCompatActivity {

//    private EditText userName;
    private EditText email;
    private EditText passworld;

    private Button btnRegistrantion;
    private TextView txtSignInhere;

    private ProgressBar mDialog;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    Data data; // init a model class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("UserData");

        //init a ProgressBar class.
        mDialog = new ProgressBar(this);

        registration();

    }

    private void registration() {

//        userName = findViewById(R.id.user_name);
        email = findViewById(R.id.email_registration);
        passworld = findViewById(R.id.pass_registration);

        btnRegistrantion = findViewById(R.id.registration_btn);

        txtSignInhere = findViewById(R.id.txt_registration);

        txtSignInhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        btnRegistrantion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final String user_name_str = userName.getText().toString().trim();
                final String email_str = email.getText().toString().trim();
                final String pass_world_str = passworld.getText().toString().trim();

//                if (TextUtils.isEmpty(user_name_str)) {
//                    userName.setError("Required field..");
//                    return;
//                }

                if (TextUtils.isEmpty(email_str)) {
                    email.setError("Required field..");
                    return;
                }

                if (TextUtils.isEmpty(pass_world_str)) {
                    passworld.setError("Required field");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email_str, pass_world_str).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String id = mDatabase.push().getKey(); // create a key for save database on Firebase
                            //data = new Data();
                            //data = Data(user_name_str, email_str, pass_world_str, id);

                            mDialog.onVisibilityAggregated(true);
                            data = new Data("Hung Nguyen learning android basic.", email_str, pass_world_str,id);

                            Log.e("data","data:" + data);
                            Log.e("email","string:" + email_str);

                            mDatabase.child(id).setValue(data);
                            Toast.makeText(getApplicationContext(),"Registration successful", Toast.LENGTH_SHORT).show();
                        }else {
                            mDialog.onVisibilityAggregated(false);
                            Toast.makeText(getApplicationContext(),"Registration failed", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
            }
        });
    }
}
