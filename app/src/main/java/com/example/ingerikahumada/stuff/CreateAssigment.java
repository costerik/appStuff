package com.example.ingerikahumada.stuff;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;


public class CreateAssigment extends Fragment {

    private EditText name;
    private ImageButton picker, picker2;
    private TextView startDate, finishDate;
    private Button btnCreate;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateAssigment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateAssigment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateAssigment newInstance(String param1, String param2) {
        CreateAssigment fragment = new CreateAssigment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_create_assigment, container, false);
        name=(EditText)v.findViewById(R.id.input_name_create);
        startDate = (TextView)v.findViewById(R.id.start_date_create);
        finishDate = (TextView)v.findViewById(R.id.finish_date_create);
        picker = (ImageButton)v.findViewById(R.id.picker);
        picker2 = (ImageButton)v.findViewById(R.id.picker2);
        btnCreate = (Button) v.findViewById(R.id.create_assign);

        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatePicker mdp=new MyDatePicker();
                        //MyDatePicker.newInstance(startDate.getId());
                mdp.show(getFragmentManager(),"DatePicker");
                mdp.setId(startDate.getId());
            }
        });

        picker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatePicker mdp = new MyDatePicker();
                mdp.show(getFragmentManager(),"DatePicker");
                mdp.setId(finishDate.getId());

            }
        });

        return v;
    }

}
