package com.yellowsoft.subhankar.devcomic;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
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
    private static final int distance = 10;
    private static final int velocity = 10;
    private final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());


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

        comicImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });


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

    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (e1.getX() - e2.getX() > distance && Math.abs(velocityX) > velocity) {
                    comicNum++;
                    getComic(comicNum);
                    // viewflipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_in));
                    // viewflipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_out));
                    // viewflipper.showPrevious();
                    return true;
                } else if (e2.getX() - e1.getX() > distance && Math.abs(velocityX) > velocity) {
                    comicNum--;
                    getComic(comicNum);
                    // viewflipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
                    // viewflipper.setOutAnimation(AnimationUtils.loadAnimation(mContext,R.anim.left_out));
                    // viewflipper.showNext();
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    }
}