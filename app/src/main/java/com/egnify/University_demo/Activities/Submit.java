package com.egnify.University_demo.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.egnify.University_demo.Network.AppController;
import com.egnify.University_demo.R;
import com.egnify.University_demo.helper.SQLiteHandler;
import com.egnify.University_demo.model.app_url;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Submit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RelativeLayout img_l;
    // MultiSelectionSpinner multiselectionspinner;
    private SimpleDialog.Builder builder;
    private RelativeLayout ll_user, ll_image;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    private String UPLOAD_URL = "http://egnify.com/demo/gcm_chat/VolleyUpload/upload.php";
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    private String item_cat;
    private String users_list;
    private EditText title_edit;
    private EditText input_msg;
    private SQLiteHandler db;
    private String user_id;
    private String TAG = "Submit";
    private String file_name = "null";
    private ImageView img_v, cancel;

    public static boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError("Shoud not be empty");
            return false;
        }

        return true;
    }

    public static boolean hasText(String editText) {


        // length 0 means there is no text
        return editText != null;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        db = new SQLiteHandler(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        img_l = (RelativeLayout) findViewById(R.id.img_l);
        ll_user = (RelativeLayout) findViewById(R.id.ll_user);
        ll_image = (RelativeLayout) findViewById(R.id.ll_image);
        title_edit = (EditText) findViewById(R.id.input_name);
        input_msg = (EditText) findViewById(R.id.input_msg);
        img_v = (ImageView) findViewById(R.id.img_v);
        cancel = (ImageView) findViewById(R.id.cancel);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.submit);
        HashMap<String, String> user = db.getUserDetails();
        user_id = "44";
        List<String> categories = new ArrayList<String>();
        categories.add("Exams");
        categories.add("News");
        categories.add("Circular");
        categories.add("Education");
        categories.add("Remark");
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        ll_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new SimpleDialog.Builder(R.style.SimpleDialogLight) {
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        CharSequence[] values = getSelectedValues();
                        if (values == null)
                            Toast.makeText(Submit.this, "You have selected nothing.", Toast.LENGTH_SHORT).show();
                        else {
                            StringBuffer sb = new StringBuffer();
                            // sb.append("You have selected ");
                            for (int i = 0; i < values.length; i++) {
                                String add_val = "0";
                                if (values[i].equals("Chairman")) {
                                    add_val = "1";
                                } else if (values[i].equals("HODs")) {
                                    add_val = "2";
                                } else if (values[i].equals("Deans")) {
                                    add_val = "3";
                                } else {
                                    add_val = "4";
                                }
                                sb.append(add_val).append(i == values.length - 1 ? "" : ",");
                            }
                            users_list = sb.toString();
                            Toast.makeText(Submit.this, sb.toString(), Toast.LENGTH_SHORT).show();
                        }
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(Submit.this, "Cancelled", Toast.LENGTH_SHORT).show();
                        super.onNegativeActionClicked(fragment);
                    }
                };

                builder.multiChoiceItems(new String[]{"Chairman", "HODs", "Deans", "Faculty"})
                        .title("Select UserGroup")
                        .positiveAction("OK")
                        .negativeAction("CANCEL");
                DialogFragment fragment = DialogFragment.newInstance(builder);
                fragment.show(getSupportFragmentManager(), null);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_l.setVisibility(View.GONE);
                file_name = "null";
            }
        });
        ll_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String title_tx = title_edit.getText().toString();
                final String msg_tx = input_msg.getText().toString();
                if (checkValidation()) {

                    login(user_id, item_cat, users_list, title_tx, msg_tx);
                } else

                    Toast.makeText(Submit.this, "Form contains error", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        item_cat = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item_cat, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!hasText(item_cat)) ret = false;
        if (!hasText(users_list)) ret = false;
        if (!hasText(title_edit)) ret = false;
        if (!hasText(input_msg)) ret = false;
        //  if (!Validation.isEmailAddress(etEmailAddrss, true)) ret = false;
        //if (!Validation.isPhoneNumber(etPhoneNumber, false)) ret = false;

        return ret;
    }

    private void uploadImage() {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        file_name = s;
                        //Showing toast message of the response
                        Toast.makeText(Submit.this, s, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
//                        file_name=volleyError.getMessage().toString();
                        Toast.makeText(Submit.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);


                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_IMAGE, image);
                params.put(KEY_NAME, "img_name");

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                uploadImage();
                //Setting the Bitmap to ImageView
                img_l.setVisibility(View.VISIBLE);
                img_v.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void login(final String luser_id, final String litem_cat, final String lusers_list, final String ltitle_tx, final String lmsg_tx) {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                app_url.submit_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "response: " + response);

                try {
                    JSONObject obj = new JSONObject(response);

                    // check for error flag
                    if (obj.getBoolean("error") == false) {
                        // user successfully logged in

                        Toast.makeText(Submit.this, "Done!!!!", Toast.LENGTH_LONG).show();

                        // start main activity
                        startActivity(new Intent(getApplicationContext(), Feeds.class));
                        finish();

                    } else {
                        // login error - simply toast the message
                        Toast.makeText(getApplicationContext(), "" + obj.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "json parsing error: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                Log.e(TAG, "Volley error: " + error.getMessage() + ", code: " + networkResponse);
                Toast.makeText(getApplicationContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", luser_id);
                params.put("litem_cat", litem_cat);
                params.put("lusers_list", lusers_list);
                params.put("ltitle_tx", ltitle_tx);
                params.put("lmsg_tx", lmsg_tx);
                params.put("filename", file_name);
                Log.e(TAG, "params: " + params.toString());
                return params;
            }
        };

        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

}
