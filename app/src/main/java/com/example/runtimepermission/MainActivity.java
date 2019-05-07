package com.example.runtimepermission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUST_CAMERA = 123;
    private static final int REQUST_CONTACTS = 456;
    private static final int REQUST_STORAGE = 789;

    private static final int PERMISSSION_CAMERA = 56;
    private static final int PERMISSSION_CONTACTS = 78;
    private static final int PERMISSSION_STORAGE = 90;

    PermissionUtil permissionUtil;

    //Check Permission...
    private int checkPermission(int permission) {

        int status = PackageManager.PERMISSION_DENIED;
        switch (permission) {
            case PERMISSSION_CAMERA:
                status = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
                break;
            case PERMISSSION_CONTACTS:
                status = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
                break;
            case PERMISSSION_STORAGE:
                status = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
                break;

        }
        return status;
    }

    //Request New Permission.....

    private void requestPermission(int permission) {
        switch (permission) {
            case PERMISSSION_CAMERA:
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUST_CAMERA);
                break;
            case PERMISSSION_CONTACTS:
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUST_CONTACTS);
                break;
            case PERMISSSION_STORAGE:
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUST_STORAGE);
                break;
        }
    }

    private void permissionExplanation(final int permission) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (permission == PERMISSSION_CAMERA) {
            builder.setMessage("This app must need Camera permission..");
            builder.setTitle("Camera permission Needed..");
        }
        if (permission == PERMISSSION_CONTACTS) {
            builder.setMessage("This app must need Contact permission..");
            builder.setTitle("Contacts permission Needed..");
        }
        if (permission == PERMISSSION_STORAGE) {
            builder.setMessage("This app must need Storage permission..");
            builder.setTitle("Storage permission Needed");
        }
        builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (permission == PERMISSSION_CAMERA)
                    requestPermission(PERMISSSION_CAMERA);
                else if (permission == PERMISSSION_CONTACTS)
                    requestPermission(PERMISSSION_CONTACTS);
                else if (permission == PERMISSSION_STORAGE)
                    requestPermission(PERMISSSION_STORAGE);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionUtil = new PermissionUtil(this);
    }

    //**********************************************

    public void cameraAccess(View view) {

        if (checkPermission(PERMISSSION_CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))

            {
                permissionExplanation(PERMISSSION_CAMERA);
            }
            else if (!permissionUtil.checkPermission("camera")) {
                requestPermission(PERMISSSION_CAMERA);
                permissionUtil.updatePermission("camera");
            } else {
                Toast.makeText(this, "Please allow camera permission in your app setting ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                this.startActivity(intent);
            }
        } else {
            Toast.makeText(this, "You have Camera Permission..", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Main2Activity.class);
            intent.putExtra("message", "You can now take photos and pictures..");
            startActivity(intent);
        }

    }

    public void storageWrite(View view) {

        if (checkPermission(PERMISSSION_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissionExplanation(PERMISSSION_STORAGE);
            } else if (!permissionUtil.checkPermission("storage")) {
                requestPermission(PERMISSSION_STORAGE);
                permissionUtil.updatePermission("storage");
            } else {
                Toast.makeText(this, "Please allow storage permission in your app setting ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                this.startActivity(intent);
            }
        } else {
            Toast.makeText(this, "You have Storage Permission..", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Main2Activity.class);
            intent.putExtra("message", "You can now access your storage..");
            startActivity(intent);
        }
    }

    public void contactsAccess(View view) {

        if (checkPermission(PERMISSSION_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS))

            {
                permissionExplanation(PERMISSSION_CONTACTS);
            }
            else if (!permissionUtil.checkPermission("contacts")) {
                requestPermission(PERMISSSION_CONTACTS);
                permissionUtil.updatePermission("contacts");
            } else {
                Toast.makeText(this, "Please allow contact permission in your app setting ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                this.startActivity(intent);
            }
        } else {
            Toast.makeText(this, "You have Contact Permission..", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Main2Activity.class);
            intent.putExtra("message", "You can now read your contacts..");
            startActivity(intent);
        }
    }

    //**********************************************
}
