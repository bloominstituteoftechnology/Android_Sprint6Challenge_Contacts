package com.joshuahalvorson.android_sprint6challenge_contacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;

public class UserDetailActivity extends AppCompatActivity {
    private TextView userName, userNumber;
    private ImageView userImage;
    private Bitmap bitmap;
    final AtomicBoolean canceled = new AtomicBoolean(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        userImage = findViewById(R.id.user_image);
        userName = findViewById(R.id.user_name);
        userNumber = findViewById(R.id.user_number);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        final User user = (User)bundle.getSerializable("user");

        new Thread(new Runnable() {
            @Override
            public void run() {
                bitmap = UsersDbDao.getImage(user.getPictureLarge(), canceled);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        userImage.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
        userName.setText(user.getTitle() + " " + user.getFirst() + " " + user.getLast());
        userNumber.setText(user.getPhone());
    }
}
