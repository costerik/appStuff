package com.example.haynervasquez.stuff;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Student#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Student extends Fragment implements StudentViewAdapter.RecyclerClickListener{

    private RecyclerView myRecyclerView;
    private StudentViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Firebase m_fb;
    private ArrayList<Professor.Assigment> assigments;
    private ProgressDialog pDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    static final String ID_KEY = "idKeyStudent";
    static final String ID_NAME = "idNameStudent";

    // TODO: Rename and change types of parameters
    private String keyStudent;
    private String nameStudent;


    public Student() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param key Parameter 1.
     * @param name Parameter 2.
     * @return A new instance of fragment Student.
     */
    // TODO: Rename and change types and number of parameters
    public static Student newInstance(String key, String name) {
        Student fragment = new Student();
        Bundle args = new Bundle();
        args.putString(ID_KEY, key);
        args.putString(ID_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            keyStudent = getArguments().getString(ID_KEY);
            nameStudent = getArguments().getString(ID_NAME);
        }
        setHasOptionsMenu(true);
        m_fb = new Firebase("https://movil.firebaseio.com/assigments");
        Log.i("onCreate","Student");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_student, container, false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_student_view);
        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        Log.i("onCreateView",mLayoutManager.toString());
        myRecyclerView.setLayoutManager(mLayoutManager);
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());
        assigments = new ArrayList<Professor.Assigment>();

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar_student_view);
        AppCompatActivity appca = ((AppCompatActivity)getActivity());
        appca.setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar ab=appca.getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setIcon(R.drawable.pin2);
        ab.setTitle(nameStudent);

        Log.i("onCreateView","Student");
        return v;
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.i("onStart","Student");
        assigments.clear();
        m_fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Professor.Assigment a = postSnapshot.getValue(Professor.Assigment.class);
                    a.setKeyFireBase(postSnapshot.getKey());
                    System.out.println(a.getKeyFireBase()+" "+a.getCreatedBy()+" "+ a.getName() + " - " + a.getStartDate()+" - "+a.getFinishDate());
                    assigments.add(a);
                    System.out.println(assigments.size());
                }
                mAdapter = new StudentViewAdapter(assigments);
                mAdapter.setRecyclerClickListener(Student.this);
                myRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    /*
    * Metodo para saber cual item del recyclerview ha sido presionado con
    * la información pertinent sobre ese item
    *
    * */

    @Override
    public void itemClick(Professor.Assigment assigment) {
        Log.i("itemClick",assigment.getName());
    }
}
