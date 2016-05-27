package com.egnify.University_demo.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.egnify.University_demo.R;
import com.egnify.University_demo.model.app_url;
import com.egnify.University_demo.utils.p_MyCustomTextView_mbold;
import com.egnify.University_demo.utils.p_MyCustomTextView_regular;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class noification_open extends AppCompatActivity {
    private static int[] color = new int[]{
            R.color.lred, R.color.lgreen, R.color.blue, R.color.dred
    };
    int sender_id;
    ImageView img_share;
    private String message;
    private String Title;
    private String timestamp;
    private String image, name_txt, categ_txt;
    private Picasso picasso;
    private ImageView img_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noification_open);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        picasso = Picasso.with(this);

        picasso.setLoggingEnabled(true);
        picasso.setIndicatorsEnabled(false);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Bundle bundle = new Bundle();
            bundle = getIntent().getExtras();
            message = bundle.getString("message");
            Title = bundle.getString("Title");
            timestamp = bundle.getString("timestamp");
            image = bundle.getString("image");
            sender_id = bundle.getInt("sender_id");
            name_txt = bundle.getString("name_txt");
            categ_txt = bundle.getString("categ");
        }
        img_share = (ImageView) findViewById(R.id.img_share);
        p_MyCustomTextView_mbold title = (p_MyCustomTextView_mbold) findViewById(R.id.title);
        p_MyCustomTextView_regular msg = (p_MyCustomTextView_regular) findViewById(R.id.msgs);
        p_MyCustomTextView_regular timestamp_b = (p_MyCustomTextView_regular) findViewById(R.id.timestamp);
        p_MyCustomTextView_regular categ = (p_MyCustomTextView_regular) findViewById(R.id.categ);
        LinearLayout cat_ll = (LinearLayout) findViewById(R.id.cat_ll);
        p_MyCustomTextView_mbold name = (p_MyCustomTextView_mbold) findViewById(R.id.name);
        CircularImageView profilePic = (CircularImageView) findViewById(R.id.profilePic);
        img_view = (ImageView) findViewById(R.id.img_view);
        if (categ_txt.equals("Exams")) {
            cat_ll.setBackgroundColor(getResources().getColor(color[0]));
        } else if (categ_txt.equals("Remark")) {
            cat_ll.setBackgroundColor(getResources().getColor(color[1]));
        } else if (categ_txt.equals("Circular")) {
            cat_ll.setBackgroundColor(getResources().getColor(color[2]));
        } else {
            cat_ll.setBackgroundColor(getResources().getColor(color[3]));
        }
        categ.setText(categ_txt);
        name.setText(name_txt);
        String propic = app_url.prourl + String.valueOf(sender_id) + ".jpg";
        picasso.load(propic)
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(profilePic);
        title.setText(Title);
        msg.setText(message);
        timestamp_b.setText(timestamp);
        Log.e("image", image);
        if (!(image.isEmpty() || image.equals("null"))) {
            img_view.setVisibility(View.VISIBLE);

            picasso.load(image)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.loading)
                    .into(img_view);

        }
        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(image.isEmpty() || image.equals("null"))) {
                    String furl = app_url.feed_img_url + image;
                    shareImage(furl, Title + message);
                } else
                    shareTextUrl(Title + message);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(noification_open.this, Feeds.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        // overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

        }
        return true;
    }

    // Method to share either text or URL.
    private void shareTextUrl(String text) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, text);

        startActivity(Intent.createChooser(share, "Share Feed"));
    }

    // Method to share any image.
    private void shareImage(String url, String text) {

        ImageView ivImage = (ImageView) findViewById(R.id.img_view);
        Uri bmpUri = getLocalBitmapUri(ivImage);
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);

            shareIntent.putExtra(Intent.EXTRA_TEXT, text);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/*");
            // Launch sharing dialog for image
            startActivity(Intent.createChooser(shareIntent, "Share Feed"));
        } else {
            // ...sharing failed, handle error
        }

    }

    // Returns the URI path to the Bitmap displayed in specified ImageView
    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable) {
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}
