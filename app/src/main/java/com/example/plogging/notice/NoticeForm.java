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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plogging.R;
import com.example.plogging.retrofit.PloggingService;
import com.example.plogging.retrofit.RetrofitClient;

import org.w3c.dom.Text;

import java.io.FileDescriptor;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NoticeForm extends AppCompatActivity {
    private Button button, cancleBtn;
    private EditText location;
    private TextView date;
    private EditText kit;
    private ImageView image;
    private EditText content;
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

        button = findViewById(R.id.register_notice);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("register", "test");
                intent.putExtra("intentName", "noticeForm");
                Notice notice = new Notice("loca", "2022-1-1", "바지", "alb.jpg", "contents");
                intent.putExtra("new_notice", notice);
                setResult(RESULT_OK, intent);

                addPost();
                finish();
            }
        });

        cancleBtn = findViewById(R.id.cancle_btn);
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void init(){
        location = findViewById(R.id.form_location);
        date = findViewById(R.id.form_date);
        kit = findViewById(R.id.form_kit);
        image = findViewById(R.id.form_image);
        content = findViewById(R.id.form_content);
    }

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void processDatePickerResult(int year, int month, int day){
        TextView date = (TextView) findViewById(R.id.form_date);
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
        ImageView image = (ImageView) findViewById(R.id.form_image);

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

    private void addPost(){
        Retrofit retrofit = RetrofitClient.getClient();
        PloggingService service = retrofit.create(PloggingService.class);

//        Notice notice = new Notice(1, location.getText().toString(),
//                date.getText().toString(), kit.getText().toString(),
//                image.toString(), content.getText().toString());

        Notice notice = new Notice(6, "dd55" ," dd"," zdz"," dzdz","dd");

        Call<Notice> call = service.addPost(notice);
        call.enqueue(new Callback<Notice>() {
            @Override
            public void onResponse(Call<Notice> call, Response<Notice> response) {
                if(!response.isSuccessful()){
                    Log.d("addPostResponse", "msg: " + response.code());
                    return;
                }
                response.body();
            }

            @Override
            public void onFailure(Call<Notice> call, Throwable t) {
                Log.d("addOnFail", "msg: " + t.getMessage());
            }
        });

    }
}
