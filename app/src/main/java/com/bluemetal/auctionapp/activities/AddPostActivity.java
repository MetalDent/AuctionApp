package com.bluemetal.auctionapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bluemetal.auctionapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddPostActivity extends Activity {

    private ImageButton imageBn;
    private EditText editItemName;
    private EditText editDesc;
    private EditText editBaseBid;
    private EditText editStartTime;
    private EditText editEndTime;
    private Button submitBn;

    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabase;

    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        imageBn = findViewById(R.id.image_bn);
        editItemName = findViewById(R.id.edit_item_name);
        editDesc = findViewById(R.id.edit_description);
        editBaseBid = findViewById(R.id.edit_base_bid);
        editStartTime = findViewById(R.id.edit_start_time);
        editEndTime = findViewById(R.id.edit_end_time);
        submitBn = findViewById(R.id.submit_bn);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Posts");

        imageBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        submitBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publishPost();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            mImageUri = data.getData();
            imageBn.setImageURI(mImageUri);
        }
    }

    private void publishPost() {
        final String itemName = editItemName.getText().toString().trim();
        final String itemDesc = editDesc.getText().toString().trim();
        final String baseBid = editBaseBid.getText().toString().trim();
        final String startTime = editStartTime.getText().toString().trim();
        final String endTime = editEndTime.getText().toString().trim();

        if(!itemName.isEmpty() && !itemDesc.isEmpty() && !baseBid.isEmpty()
                && !startTime.isEmpty() && !endTime.isEmpty() && mImageUri != null) {

            final StorageReference filePath = mStorageRef.child("AuctionImages").child(mImageUri.getLastPathSegment());

            UploadTask uploadTask = filePath.putFile(mImageUri);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String downloadUrl = downloadUri.toString();

                        DatabaseReference newPost = mDatabase.push();
                        newPost.child("itemName").setValue(itemName);
                        newPost.child("description").setValue(itemDesc);
                        newPost.child("baseBid").setValue(baseBid);
                        newPost.child("startTime").setValue(startTime);
                        newPost.child("endTime").setValue(endTime);
                        newPost.child("imageUrl").setValue(downloadUrl);

                        Toast.makeText(getApplicationContext(), "Published Successfully!", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(AddPostActivity.this, HomeActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Error... Please Try Again.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "Please provide an image and fill in all the fields.", Toast.LENGTH_SHORT).show();
        }
    }
}
