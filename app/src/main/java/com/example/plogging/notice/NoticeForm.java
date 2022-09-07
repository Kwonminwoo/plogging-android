package com.example.plogging.notice;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plogging.R;

import org.w3c.dom.Text;

import java.io.FileDescriptor;
import java.io.IOException;

public class NoticeForm extends AppCompatActivity {
    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                Uri image_uri;
                @Override
                public void onActivityResult(ActivityResult result) {
                    if( result.getResultCode() == RESULT_OK && result.getData() != null){
                        image_uri = result.getData().getData();
                    }
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image_uri);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_form);
    }

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void processDatePickerResult(int year, int month, int day){
        TextView date = (TextView) findViewById(R.id.date_text);
        String month_string = Integer.toString(month+1);
        if (Integer.parseInt(month_string) < 10) {
            month_string = "0" + month_string;
        }
        String day_string = Integer.toString(day);
        if (Integer.parseInt(day_string) < 10) {
            day_string = "0" + day_string;
        }
        String year_string = Integer.toString(year);
        String dateMessage = (year_string + "-" + month_string + "-" + day_string);

        date.setText(dateMessage);
        Toast.makeText(this,"Date: "+dateMessage, Toast.LENGTH_SHORT).show();
    }

    public void getImageOnLocal(View view) {
        //System.out.println("MainActivity.getImageOnLocal");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        startActivityResult.launch(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        ImageView image = (ImageView) findViewById(R.id.imageView);

        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                Bitmap bm = getBitmapFromUri(uri) ;
                image.setImageBitmap(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

}
