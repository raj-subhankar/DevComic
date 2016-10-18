package com.yellowsoft.subhankar.devcomic;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView comicImage;
    TextView title;
    FloatingActionButton next, prev, random;
    int comicNum;
    ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        comicImage = (ImageView) findViewById(R.id.comicImage);
        title = (TextView) findViewById(R.id.title);
        next = (FloatingActionButton) findViewById(R.id.btnNext);
        prev = (FloatingActionButton) findViewById(R.id.btnPrev);
        random = (FloatingActionButton) findViewById(R.id.btnRandom);
        mProgressBar = (ProgressBar) findViewById(R.id.loading_progress_bar);

        next.setOnClickListener(this);
        prev.setOnClickListener(this);
        random.setOnClickListener(this);

        comicNum = generateRandomInt();
        getComic(comicNum);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext:
                comicNum++;
                break;
            case R.id.btnPrev:
                comicNum--;
                break;
            case R.id.btnRandom:
                comicNum = generateRandomInt();
                break;
        }
        getComic(comicNum);
    }

    void getComic(int num) {


        mProgressBar.setVisibility(View.VISIBLE);
        comicImage.setVisibility(View.INVISIBLE);
        title.setText("Loading...");
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
                    Picasso.with(getApplicationContext()).load(comic.getCartoon_jpg()).into(comicImage, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            mProgressBar.setVisibility(View.GONE);
                            comicImage.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError() {
                            new AlertDialog.Builder(MainActivity.this).setMessage("Could not load image").show();
                            mProgressBar.setVisibility(View.GONE);
                        }
                    });
                    title.setText(comic.getTitle());
                }

            }

            @Override
            public void onFailure(Call<Comic> call, Throwable t) {
                Log.e("TAG", t.toString());
            }
        });
    }

    int generateRandomInt() {
        Random r = new Random();
        return (r.nextInt(53) + 1);

    }
}