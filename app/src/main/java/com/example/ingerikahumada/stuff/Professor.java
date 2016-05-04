package com.example.ingerikahumada.stuff;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class Professor extends Fragment{

    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Firebase m_fb;
    private ArrayList<Assigment> assigments;
    private ProgressDialog pDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    static final String ID_KEY = "idKey";
    static final String ID_NAME = "idName";
    static final String ID_EMAIL = "idEmail";

    private String keyProfessor;


    public Professor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param key Parameter 1.
     * @return A new instance of fragment CreateProfessor.
     */

    public static Professor newInstance(String key, String name, String email) {
        Professor fragment = new Professor();
        Bundle args = new Bundle();
        args.putString(ID_KEY, key);
        args.putString(ID_NAME,name);
        args.putString(ID_EMAIL,email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras= getArguments();
        if(extras!=null) {
            keyProfessor = extras.getString(ID_KEY);
            System.out.println(extras.getString(ID_KEY)+" "+extras.getString(ID_NAME)+" "+extras.getString(ID_EMAIL));
        }
        setHasOptionsMenu(true);
        m_fb = new Firebase("https://movil.firebaseio.com/assigments");
        Log.i("onCreate","OnCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_professor, container, false);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycle_view);
        //ArrayList<String> a=new ArrayList<String>(Arrays.asList("Plaza de la paz","Via 40", "Buenavista"));
        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        myRecyclerView.setLayoutManager(mLayoutManager);
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());
        assigments = new ArrayList<Assigment>();

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity appca = ((AppCompatActivity)getActivity());
        appca.setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar ab=appca.getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setIcon(R.drawable.pin2);
        ab.setTitle("");

        Log.i("onCreateView","");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setHasOptionsMenu(true);
        new GetData().execute();
        Log.i("onResume", ""+assigments.size());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_create:
                CreateAssigment ca = CreateAssigment.newInstance(keyProfessor);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, ca).addToBackStack(null).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class GetData extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog=new ProgressDialog(getContext());
            pDialog.setTitle("Learn English");
            pDialog.setMessage("Checking...");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            m_fb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        Assigment a = postSnapshot.getValue(Assigment.class);
                        System.out.println(a.getCreatedBy()+" "+ a.getName() + " - " + a.getStartDate()+" - "+a.getFinishDate());
                        if(a.getCreatedBy().compareTo(keyProfessor)==0) {
                            assigments.add(a);
                        }
                        System.out.println(assigments.size());
                    }
                    mAdapter = new AssignmentAdapter(assigments);
                    myRecyclerView.setAdapter(mAdapter);
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("The read failed: " + firebaseError.getMessage());
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            pDialog.dismiss();
        }
    }

    public static class Assigment{
        private String createdBy, finishDate, name ,startDate;

        /*public Assigment(String name, String startDate, String finishDate){
            this.name= name;
            this.startDate = startDate;
            this.finishDate = finishDate;
        }*/
        public String getCreatedBy(){
            return this.createdBy;
        }

        public String getName(){
            return this.name;
        }

        public String getStartDate(){
            return this.startDate;
        }

        public String getFinishDate(){
            return this.finishDate;
        }
    }
}
