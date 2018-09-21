package demo.java.registrationfirebase;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import demo.java.registrationfirebase.Model.Data;

public class MainActivity extends AppCompatActivity {

    private TextView signup;
    private EditText password;
    private EditText email;
    private Button btnLogin;

    private ProgressBar mDialog;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    Data data; // init a model class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("UserData");

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), NotesActivity.class));
        }
        //init a ProgressBar class.
        mDialog = new ProgressBar(this);

        loginFunc();

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

    }

    private void loginFunc() {
        password = findViewById(R.id.pass_login);
        email = findViewById(R.id.email_login);
        btnLogin =findViewById(R.id.login_btn);

        // add progressBar for Main screen
        //mDialog = findViewById(R.id.progressbar);

        signup = findViewById(R.id.txt_login);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailStr = email.getText().toString().trim();
                final String passStr = password.getText().toString().trim();

                if (TextUtils.isEmpty(emailStr)) {
                    email.setError("Required field..");
                    return;
                }

                if (TextUtils.isEmpty(passStr)) {
                    password.setError("Required field..");
                    return;
                }

                mAuth.signInWithEmailAndPassword(emailStr, passStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String id = mDatabase.push().getKey();
                            data = new Data("Hung Nguyen learning android basic.", emailStr, passStr,id);
                            mDatabase.child(id).setValue(data);

                            mDialog.onVisibilityAggregated(true);
                            startActivity(new Intent(getApplicationContext(), NotesActivity.class));

                            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                        } else {
                            mDialog.onVisibilityAggregated(false);
                            Toast.makeText(getApplicationContext(), "Login fails", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


    }
}
