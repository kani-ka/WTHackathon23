package com.example.roomeaze;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class Booking extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private TextView dateTimeTextView, hostelNameEditText, roomNumberEditText, problemDescriptionEditText;
    private Button pickButton, submitButton;
    private int day, month, year, hour, minute;
    private int myday, myMonth, myYear, myHour, myMinute;
    private DatabaseReference rootDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        FirebaseApp.initializeApp(this);
        // Initialize Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();

        dateTimeTextView = findViewById(R.id.textView);
        hostelNameEditText = findViewById(R.id.hostelNameEditText);
        roomNumberEditText = findViewById(R.id.roomNumberEditText);
        problemDescriptionEditText = findViewById(R.id.problemDescriptionEditText);
        pickButton = findViewById(R.id.btnPick);
        submitButton = findViewById(R.id.btnSubmit);
        rootDatabaseRef = FirebaseDatabase.getInstance().getReference();

        pickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                rootDatabaseRef.setValue(year);
                rootDatabaseRef.setValue(month);
                rootDatabaseRef.setValue(day);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Booking.this, Booking.this, year, month, day);
                datePickerDialog.show();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myday = dayOfMonth;
        myMonth = month+1;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        rootDatabaseRef.setValue(hour);
        rootDatabaseRef.setValue(minute);


        TimePickerDialog timePickerDialog = new TimePickerDialog(Booking.this, Booking.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myHour = hourOfDay;
        myMinute = minute;
        updateDateTimeTextView();
    }

    private void submitForm() {
        String hostelName = hostelNameEditText.getText().toString().trim();
        String roomNumber = roomNumberEditText.getText().toString().trim();
        String problemDescription = problemDescriptionEditText.getText().toString().trim();
        rootDatabaseRef.setValue(hostelName);
        rootDatabaseRef.setValue(roomNumber);
        rootDatabaseRef.setValue(problemDescription);
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("Hostel",hostelName);
        hashMap.put("roomNum",roomNumber);
        hashMap.put("Problem",problemDescription);
        hashMap.put("Year",myYear);
        hashMap.put("Month",myMonth);
        hashMap.put("Day",myday);
        hashMap.put("Time",myHour);
        FirebaseFirestore .getInstance().collection("user").document("userData").set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void avoid) {

            }
        });

        // Validate form data
        if (hostelName.isEmpty()) {
            hostelNameEditText.setError("Hostel name is required!");
            hostelNameEditText.requestFocus();
            return;
        }

        if (roomNumber.isEmpty()) {
            roomNumberEditText.setError("Room number is required!");
            roomNumberEditText.requestFocus();
            return;
        }
        DatabaseReference bookingsRef = rootDatabaseRef.child("bookings");
        String bookingId = bookingsRef.push().getKey();
        BookingData bookingData = new BookingData(hostelName, roomNumber, myYear, myMonth, myday, myHour, myMinute, problemDescription);
        bookingsRef.child(bookingId).setValue(bookingData);

        if (problemDescription.isEmpty()) {
            problemDescriptionEditText.setError("Problem description is required!");
            problemDescriptionEditText.requestFocus();
            return;
        }

        // Create and show the pop-up dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Booking details:\n" + "Hostel name: " + hostelName + "\n" +
                        "Room number: " + roomNumber + "\n" +
                        "Date and time: " + getFormattedDateTime() + "\n" +
                        "Problem description: " + problemDescription + "\n\n" +
                        "Confirm booking?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform booking submission action here
                        // For now, let's just show a success message
                        showSuccessMessage();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked "No", so do nothing
                    }
                })
                .show();
    }

    private void showSuccessMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Booking successful!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
// User clicked "OK", so close the dialog and finish the activity
                        dialog.dismiss();
                        finish();
                    }
                })
                .show();
    }

    private String getFormattedDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(myYear, myMonth, myday, myHour, myMinute);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }

    private void updateDateTimeTextView() {

    }
}
