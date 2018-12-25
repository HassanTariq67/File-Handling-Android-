package com.example.assignment2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import static java.sql.DriverManager.println;

public class SignUp extends AppCompatActivity  {

    public static Object item = "";
    public static FileOutputStream outputStream = null;
    public static String gender = "";
    public static TextView date ;
    private Calendar calendar;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //list of spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        date = (TextView) findViewById(R.id.DateField);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


    }
    public void getValues(View view) {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               item = parent.getItemAtPosition(position);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        int checkError = 0;
        String Item = item.toString();
        TextView name = findViewById(R.id.NameField);
        if(TextUtils.isEmpty(name.getText())){
            name.setError("Field is required");
            checkError = 1;
        }
        String Name = name.getText().toString();

        TextView address = findViewById(R.id.AddressField);
        if(TextUtils.isEmpty(address.getText())){
            address.setError("Address is Required");
            checkError = 1;
        }
        String Address = address.getText().toString();

        TextView phone = findViewById(R.id.PhoneNumberField);
        if(TextUtils.isEmpty(phone.getText())){
            phone.setError("Phone is required");
            checkError = 1;
        }
        String PhoneNumber = phone.getText().toString();

        TextView email = findViewById(R.id.EmailField);
        if(TextUtils.isEmpty(email.getText())){
            email.setError("Email is required");
            checkError = 1;
        }
        if(TextUtils.isEmpty(email.getText())){
            System.out.println("Valid");
        }
        else {
            if(!(Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches())){
                System.out.println("Invalid");
                email.setError("Invalid Email");
                checkError = 1;
            }
        }
        String Email = email.getText().toString();

        TextView dob = findViewById(R.id.DateField);
        if(TextUtils.isEmpty(dob.getText())){
            dob.setError("DOB is required");
            checkError = 1;
        }
        String DOB = dob.getText().toString();

        if(checkError == 0){
            saveItems(Name,Address,PhoneNumber,Email,DOB,gender,Item);
        }

    }

    public void saveItems(String name,String address, String phoneNumber,String email,String dob,String gender,String country) {

        String fileContents = name + "," + address + "," + phoneNumber + "," + email + "," + dob + "," + gender + "," + country +"\n" ;

        try {
            outputStream = openFileOutput ("listFile.txt", Context.MODE_APPEND);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.radio_male:
                if(checked){
                    gender = "Male";
                }
                break;
            case R.id.radio_female:
                if (checked){
                    gender = "Female";
                }
                break;
        }
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        date.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
}
