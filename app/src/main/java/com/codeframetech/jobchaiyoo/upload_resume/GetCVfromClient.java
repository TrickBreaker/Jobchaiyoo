package com.codeframetech.jobchaiyoo.upload_resume;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.codeframetech.jobchaiyoo.R;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.util.UUID;

public class GetCVfromClient extends AppCompatActivity implements View.OnClickListener {

    //Declaring views
    private Button buttonChoose;
    private Button buttonUpload;
    private TextView tvFileName;
    private String path;


    public static final String UPLOAD_URL = "http://mydigitions.000webhostapp.com/AndroidPdfUpload/upload.php";


    //Pdf request code
    private int PICK_PDF_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;


    //Uri to store the image uri
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_cvfrom_client);

        //Requesting storage permission
        requestStoragePermission();
        tvFileName = (TextView) findViewById(R.id.tv_file_name);

        //Initializing views
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);


        //Setting clicklistener
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);


    }



    /*
    * This is the method responsible for pdf upload
    * We need the full pdf path and the name for the pdf in this method
    * */

    public void uploadMultipart() {

        //getting name for the image
        Intent i = this.getIntent();
        String name = i.getExtras().getString("COMPANY_NAME");

        //String name = editText.getText().toString().trim();

        //getting the actual path of the image
         path = FilePath.getPath(this, filePath);

        if (path == null) {


        } else {
            /*dialog = ProgressDialog.show(GetCVfromClient.this, "", "Check Upload-Progress in Notification Bar", true);
            dialog.setCancelable(true);*/


            try {
                //Uploading code
                String uploadId = UUID.randomUUID().toString();

                //Creating a multi part request
                new MultipartUploadRequest(this, uploadId, UPLOAD_URL)
                        .addFileToUpload(path, "pdf") //Adding file
                        .addParameter("name", name) //Adding text parameter to the request
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload();
                //Starting the upload


            } catch (Exception exc) {
                Log.e("ERROR",exc.getMessage());
            }
        }
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
        buttonChoose.setVisibility(View.INVISIBLE);
        buttonUpload.setVisibility(View.VISIBLE);

    }


    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            tvFileName.setText(filePath.toString());
        }
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();

        }
        if (v == buttonUpload) {
            if(filePath != null) {
                new Thread(new Runnable(){
                    @Override
                    public void run(){
                        uploadMultipart();

                    }

                }).start();


                this.onBackPressed();
                /*dialog = ProgressDialog.show(GetCVfromClient.this, "", "Check Upload-Progress in Notification Bar", false);*/
                Toast.makeText(GetCVfromClient.this, "Check Upload-Progress in Notification Bar", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(GetCVfromClient.this, "Choose File First", Toast.LENGTH_LONG).show();

            }


        }


    }
}