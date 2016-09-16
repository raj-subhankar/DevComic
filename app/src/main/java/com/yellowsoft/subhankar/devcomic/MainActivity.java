package com.yellowsoft.subhankar.devcomic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Random;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends AppCompatActivity implements MainScreen {

    @Inject
    ComicsPresenter comicsPresenter;

    @InjectView(R.id.comicImage)
    ImageView comicImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        DaggerInjector.get().inject(this);
        ButterKnife.inject(this);

        initRecyclerView();
        comicsPresenter.loadComicsFromAPI();
    }

    private void initRecyclerView() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(NewComicsEvent newComicsEvent) {
        hideError();
        Comic comic = newComicsEvent.getComics();
        Picasso.with(getApplicationContext()).load(comic.getCartoon_jpg()).into(comicImageView);
//                    title.setText(comic.getTitle());
    }

    public void onEventMainThread(ErrorEvent errorEvent) {
        showError();
    }

    private void hideError() {

    }

    private void showError() {

    }



//    ImageView comicImage;
//    TextView title;
//    Button next, prev, random;
//    int comicNum, newComicNum;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
////        getSupportActionBar().setTitle("Devcomic");
//
//        comicImage = (ImageView) findViewById(R.id.comicImage);
//        title = (TextView) findViewById(R.id.title);
//        next = (Button) findViewById(R.id.btnNext);
//        prev = (Button) findViewById(R.id.btnPrev);
//        random = (Button) findViewById(R.id.btnRandom);
//
//        next.setOnClickListener(this);
//        prev.setOnClickListener(this);
//        random.setOnClickListener(this);
//
//        newComicNum = generateRandomInt();
//        getComic(newComicNum);
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btnNext:
//                newComicNum = comicNum + 1;
//                getComic(newComicNum);
//                break;
//            case R.id.btnPrev:
//                if(comicNum > 1) {
//                    newComicNum = comicNum - 1;
//                    getComic(newComicNum);
//                } else {
//                    Toast.makeText(MainActivity.this, "No more comics!", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case R.id.btnRandom:
//                newComicNum = generateRandomInt();
//                getComic(newComicNum);
//                break;
//        }
//    }
//
//    void getComic(final int num) {
//
//        ApiInterface apiService =
//                ApiClient.getClient().create(ApiInterface.class);
//
//        String numStr = num + ".json";
//        Call<Comic> call = apiService.getComic(numStr);
//        call.enqueue(new Callback<Comic>() {
//            @Override
//            public void onResponse(Call<Comic> call, Response<Comic> response) {
//                if (response.code() != 404) {
//                    comicNum = num;
//                    Comic comic = response.body();
//                    Picasso.with(getApplicationContext()).load(comic.getCartoon_jpg()).into(comicImage);
//                    title.setText(comic.getTitle());
//                } else {
//                    Toast.makeText(MainActivity.this, "New comics coming soon!", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Comic> call, Throwable t) {
//                // Log error here since request failed
//                Log.e("TAG", t.toString());
//            }
//        });
//    }
//
//    int generateRandomInt() {
//        Random r = new Random();
//        return(r.nextInt(53) + 1);
//
//    }
}
