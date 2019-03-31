package com.example.gabbar.whatappstories;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class MainActivity extends AppCompatActivity implements StoriesProgressView.StoriesListener {
private static final int PROGRESS_COUNT=3;
private StoriesProgressView storiesProgressView;
private ImageView imageView;
private  int couter=0;
private final int[] resources=new int[]
        {
                R.drawable.loading,
                R.drawable.online,
                R.drawable.back,
        };
private final long[]duration=new long[]
        {
                500L,1000L,1500L,
        };
long limit=500L;
long pressTime=0L;
private View.OnTouchListener onTouchListener=new View.OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                pressTime=System.currentTimeMillis();
                storiesProgressView.pause();
                return false;
            case MotionEvent.ACTION_UP:
                long now=System.currentTimeMillis();
                storiesProgressView.resume();
                return limit < now - pressTime;


        }
        return false;
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        storiesProgressView=(StoriesProgressView) findViewById(R.id.stories);
        storiesProgressView.setStoriesCount(PROGRESS_COUNT);
        storiesProgressView.setStoryDuration(3000L);
        storiesProgressView.setStoriesListener(this);
        couter=2;
        storiesProgressView.startStories(couter);
        imageView.setImageResource(resources[couter]);
        View reverse =findViewById(R.id.reverse);
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storiesProgressView.reverse();
            }
        });
        reverse.setOnTouchListener(onTouchListener);
        View skip=findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storiesProgressView.skip();
            }
        });
        skip.setOnTouchListener(onTouchListener);

    }

    @Override
    public void onNext() {
        imageView.setImageResource(resources[--couter]);
    }

    @Override
    public void onPrev() {
if((couter-1)<0)return;;
        imageView.setImageResource(resources[--couter]);
    }

    @Override
    public void onComplete() {


    }

    @Override
    protected void onDestroy() {
        storiesProgressView.destroy();
        super.onDestroy();
    }
}
