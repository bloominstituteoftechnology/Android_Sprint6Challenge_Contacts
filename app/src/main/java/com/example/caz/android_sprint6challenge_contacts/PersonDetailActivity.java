package com.example.caz.android_sprint6challenge_contacts;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonDetailActivity extends AppCompatActivity {

    Person selectedPerson;
    TextView tvPersonPhone;
    TextView tvPersonEmail;
    ImageView ivPersonLarge;
    TextView tvPersonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);

        selectedPerson = (Person)getIntent().getSerializableExtra("selectedPerson");

        tvPersonEmail = findViewById(R.id.tvPersonEmail);
        tvPersonPhone = findViewById(R.id.tvPersonPhone);
        tvPersonName = findViewById(R.id.tvPersonDetailName);
        ivPersonLarge = findViewById(R.id.ivPersonDetail);

        tvPersonEmail.setText(selectedPerson.getEmail());
        tvPersonPhone.setText(selectedPerson.getPhone());

        String fullname = selectedPerson.getTitle() + " " + selectedPerson.getFirst() + " " + selectedPerson.getLast();

        tvPersonName.setText(fullname);

        Bitmap bitmap = ImageCache.loadImageFromStorage(selectedPerson, getApplicationContext());

        if(bitmap == null){

            new GetImageFromUrl(ivPersonLarge, selectedPerson, getApplicationContext()).execute(selectedPerson.getLarge());

        } else {

            ivPersonLarge.setImageBitmap(bitmap);


        }
    }
}