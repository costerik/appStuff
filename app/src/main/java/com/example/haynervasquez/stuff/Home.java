package com.example.haynervasquez.stuff;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class Home extends Fragment {

    private Button signUpButton, logInButton;
    private EditText emailEdt, passwordEdt;
    private Boolean userType,log;
    Firebase m_fb;


    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        signUpButton = (Button)view.findViewById(R.id.bottom_button);
        logInButton  = (Button)view.findViewById(R.id.top_button);
        emailEdt     = (EditText)view.findViewById(R.id.input_email_log_in);
        passwordEdt  = (EditText)view.findViewById(R.id.input_password_log_in);
        m_fb = new Firebase("https://movil.firebaseio.com/users");


        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType=false;
                log=false;
                m_fb.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            User user = postSnapshot.getValue(User.class);
                            System.out.println(user.getEmail() + " " + emailEdt.getText().toString());
                            System.out.println(user.getPassword() + " " + passwordEdt.getText().toString());
                            if ((user.getEmail().compareTo(emailEdt.getText().toString()) == 0) && (user.getPassword().compareTo(passwordEdt.getText().toString()) == 0)) {
                                Log.e("OnClickLogIn", "Ok");
                                userType=true;
                                log=true;
                                break;
                            } else {
                                Log.e("OnClickLogIn", "false");
                            }
                        }
                        if(!userType){
                            m_fb = new Firebase("https://movil.firebaseio.com/professors");
                            m_fb.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                        User user = postSnapshot.getValue(User.class);
                                        System.out.println(user.getEmail() + " " + emailEdt.getText().toString());
                                        System.out.println(user.getPassword() + " " + passwordEdt.getText().toString());
                                        if ((user.getEmail().compareTo(emailEdt.getText().toString()) == 0) && (user.getPassword().compareTo(passwordEdt.getText().toString()) == 0)) {
                                            Log.e("OnClickLogIn", "OkProfessor");
                                            log=true;
                                            Professor p = Professor.newInstance(postSnapshot.getKey(),user.getName(),user.getEmail());
                                            getFragmentManager().beginTransaction().replace(R.id.fragment_container,p).addToBackStack(null).commit();
                                            //getFragmentManager().beginTransaction().replace(R.id.fragment_container,p).commit();
                                            break;
                                        } else {
                                            Log.e("OnClickLogIn", "falseProfessor");
                                        }
                                    }
                                    m_fb = new Firebase("https://movil.firebaseio.com/users");
                                    if(log==false){
                                        Snackbar.make(getView(),"Email and/or password incorrect",Snackbar.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                /*m_fb.authWithPassword(emailEdt.getText().toString(), passwordEdt.getText().toString(), new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        //System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                        Snackbar.make(getView(),"User ID: " + authData.getUid() + ", Provider: " + authData.getProvider(), Snackbar.LENGTH_SHORT ).show();
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        // there was an error
                    }
                });*/
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, "Hola mundo", Snackbar.LENGTH_SHORT).show();

                SignUp su= new SignUp();
                //LocationStuff ls =new LocationStuff();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container,su);
                ft.addToBackStack(null);
                ft.commit();

            }
        });
        return view;
    }

    public static class User{
        private String keyFireBase, name, email, password;

        public String getKeyFireBase(){
            return this.keyFireBase;
        }

        public void setKeyFireBase(String keyFireBase){
            this.keyFireBase=keyFireBase;
        }

        public String getName(){
            return this.name;
        }

        public String getEmail(){
            return this.email;
        }

        public String getPassword(){
            return this.password;
        }
    }
}
