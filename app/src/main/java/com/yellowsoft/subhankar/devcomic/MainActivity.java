package com.yellowsoft.subhankar.devcomic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ImageView comicImage;
    TextView title;
    Button next, prev, first, last, random;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        comicImage = (ImageView) findViewById(R.id.comicImage);
        title = (TextView) findViewById(R.id.title);
        next = (Button) findViewById(R.id.btnNext);
        prev = (Button) findViewById(R.id.btnPrev);
        first = (Button) findViewById(R.id.btnFirst);
        last = (Button) findViewById(R.id.btnLast);
        random = (Button) findViewById(R.id.btnRandom);


        getComic(1);

    }

    void getComic(int num) {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        String numStr = num + ".json";
        Call<Comic> call = apiService.getComic(numStr);
        call.enqueue(new Callback<Comic>() {
            @Override
            public void onResponse(Call<Comic> call, Response<Comic> response) {
                Log.d("response", response.toString());
                if (response != null) {
                    Comic comic = response.body();
                    Picasso.with(getApplicationContext()).load(comic.getCartoon_jpg()).into(comicImage);
                    title.setText(comic.getTitle());
                }

            }

            @Override
            public void onFailure(Call<Comic> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAG", t.toString());
            }
        });
    }
}
