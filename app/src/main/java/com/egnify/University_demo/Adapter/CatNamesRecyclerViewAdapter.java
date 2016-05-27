package com.egnify.University_demo.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.toolbox.ImageLoader;
import com.egnify.University_demo.Activities.noification_open;
import com.egnify.University_demo.Network.AppController;
import com.egnify.University_demo.R;
import com.egnify.University_demo.model.app_url;
import com.egnify.University_demo.pojos.FeedItem;
import com.egnify.University_demo.utils.p_MyCustomTextView_mbold;
import com.egnify.University_demo.utils.p_MyCustomTextView_regular;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class CatNamesRecyclerViewAdapter extends RecyclerView.Adapter<CatNamesRecyclerViewAdapter.CatNamesViewHolder> {
    private static int[] color = new int[]{
            R.color.lred, R.color.lgreen, R.color.blue, R.color.dred
    };
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private Activity activity;
    private LayoutInflater inflater;
    private List<FeedItem> feedItems;
    private Picasso picasso;


    public CatNamesRecyclerViewAdapter(Activity activity, List<FeedItem> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public CatNamesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflatedView = LayoutInflater.from(activity).inflate(R.layout.cat_name_view, viewGroup, false);
        picasso = Picasso.with(activity);
        picasso.setLoggingEnabled(true);

        picasso.setIndicatorsEnabled(false);
        //picasso.memoryCache(Cache.NONE);
        return new CatNamesViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(final CatNamesViewHolder viewHolder, int i) {

        final FeedItem item = feedItems.get(i);
        String title_txt = null, msg_txt = null;
        try {
            title_txt = java.net.URLDecoder.decode(item.getTitle(), "UTF-8");
            msg_txt = java.net.URLDecoder.decode(item.getMsg(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        viewHolder.name.setText(item.getName());
        viewHolder.heading.setText(title_txt);
        // Converting timestamp into x ago format

       /* CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(item.getTimeStamp()),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);*/
        viewHolder.timestamp.setText(item.getTimeStamp());
        viewHolder.statusMsg.setText(msg_txt);
        if (item.getCateg().equals("Exams")) {
            viewHolder.cat_ll.setBackgroundColor(activity.getResources().getColor(color[0]));
        } else if (item.getCateg().equals("Remark")) {
            viewHolder.cat_ll.setBackgroundColor(activity.getResources().getColor(color[1]));
        } else if (item.getCateg().equals("Circular")) {
            viewHolder.cat_ll.setBackgroundColor(activity.getResources().getColor(color[2]));
        } else {
            viewHolder.cat_ll.setBackgroundColor(activity.getResources().getColor(color[3]));
        }
        viewHolder.categ.setText(item.getCateg());
        if (item.getUrl() != null) {
            viewHolder.url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">"
                    + item.getUrl() + "</a> "));

            // Making url clickable
            viewHolder.url.setMovementMethod(LinkMovementMethod.getInstance());
            viewHolder.url.setVisibility(View.VISIBLE);
        } else {
            // url is null, remove from the view
            viewHolder.url.setVisibility(View.GONE);
        }
        String propic = app_url.prourl + item.getId() + ".jpg";
        picasso.load(propic)
                .placeholder(R.drawable.place_holder)
                //  .memoryPolicy(MemoryPolicy.NO_CACHE )
                //.networkPolicy(NetworkPolicy.NO_CACHE)
                .error(R.drawable.place_holder)
                .into(viewHolder.profilePic);
        // user profile pic
        // viewHolder.profilePic.setImageUrl(item.getProfilePic(), imageLoader);

        // Feed image
        viewHolder.txholder.setWeightSum(25f);
        if (!item.getImge().equals("null")) {
            LinearLayout.LayoutParams lParams2 = (LinearLayout.LayoutParams) viewHolder.status_holder.getLayoutParams();
            LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) viewHolder.feedImageView.getLayoutParams();
            lParams.weight = 10f;
            lParams2.weight = 15f;
            Log.e("url_link", item.getImge());
            String furl = app_url.feed_img_url + item.getImge();
            picasso.load(furl)
                    .placeholder(R.drawable.loading)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    // .networkPolicy(NetworkPolicy.NO_CACHE)
                    .error(R.drawable.loading)
                    .into(viewHolder.feedImageView);
            viewHolder.feedImageView.setVisibility(View.VISIBLE);
            viewHolder.feedImageView.setLayoutParams(lParams);
            viewHolder.status_holder.setLayoutParams(lParams2);
        } else {
            LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) viewHolder.status_holder.getLayoutParams();
            lParams.weight = 25f;
            viewHolder.status_holder.setLayoutParams(lParams);
            viewHolder.feedImageView.setVisibility(View.GONE);
        }
        final String finalMsg_txt = msg_txt;
        final String finalTitle_txt = title_txt;
        /*viewHolder.img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!item.getImge().equals("null")) {
                    String furl = app_url.feed_img_url + item.getImge();
                    shareImage(furl,finalTitle_txt + finalMsg_txt,viewHolder);
                }
                else
                    shareTextUrl(finalTitle_txt+finalMsg_txt);
            }
        });*/
        viewHolder.ll_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, noification_open.class);
                intent.putExtra("message", finalMsg_txt);
                intent.putExtra("Title", finalTitle_txt);
                intent.putExtra("timestamp", item.getTimeStamp());
                intent.putExtra("sender_id", item.getId());
                intent.putExtra("name_txt", item.getName());
                intent.putExtra("categ", item.getCateg());
                String img_val = "null";
                if (!item.getImge().equals("null")) {

                    img_val = app_url.feed_img_url + item.getImge();
                }
                intent.putExtra("image", img_val);
                activity.startActivity(intent);
            }
        });
    }

    public Object getItem(int position) {
        return feedItems.get(position);
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
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

        activity.startActivity(Intent.createChooser(share, "Share Feed"));
    }

    // Method to share any image.
    private void shareImage(String url, String text, CatNamesViewHolder viewHolder) {

        ImageView ivImage = viewHolder.feedImageView;
        Uri bmpUri = getLocalBitmapUri(ivImage);
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);

            shareIntent.putExtra(Intent.EXTRA_TEXT, text);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/*");
            // Launch sharing dialog for image
            activity.startActivity(Intent.createChooser(shareIntent, "Share Feed"));
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

    public class CatNamesViewHolder extends RecyclerView.ViewHolder {
        p_MyCustomTextView_mbold name;
        p_MyCustomTextView_regular timestamp;
        p_MyCustomTextView_regular statusMsg;
        p_MyCustomTextView_regular url;
        CircularImageView profilePic;
        LinearLayout txholder;
        ImageView feedImageView;
        View img_view;
        LinearLayout status_holder;
        p_MyCustomTextView_mbold heading;
        p_MyCustomTextView_regular categ;
        LinearLayout cat_ll;
        LinearLayout ll_click;
        //   ImageView img_share;

        public CatNamesViewHolder(View itemView) {
            super(itemView);
            this.name = (p_MyCustomTextView_mbold) itemView.findViewById(R.id.name);
            this.timestamp = (p_MyCustomTextView_regular) itemView.findViewById(R.id.timestamp);
            this.statusMsg = (p_MyCustomTextView_regular) itemView.findViewById(R.id.txtStatusMsg);
            this.url = (p_MyCustomTextView_regular) itemView.findViewById(R.id.txtUrl);
            this.profilePic = (CircularImageView) itemView.findViewById(R.id.profilePic);
            this.feedImageView = (ImageView) itemView.findViewById(R.id.feedImage1);
            this.txholder = (LinearLayout) itemView.findViewById(R.id.txholder);
            this.status_holder = (LinearLayout) itemView.findViewById(R.id.status_holder);
            this.img_view = itemView.findViewById(R.id.feedImage1);
            this.heading = (p_MyCustomTextView_mbold) itemView.findViewById(R.id.heading);
            this.categ = (p_MyCustomTextView_regular) itemView.findViewById(R.id.categ);
            this.cat_ll = (LinearLayout) itemView.findViewById(R.id.cat_ll);
            this.ll_click = (LinearLayout) itemView.findViewById(R.id.ll_click);
            // this.img_share=(ImageView) itemView.findViewById(R.id.img_share);
        }
    }
}
