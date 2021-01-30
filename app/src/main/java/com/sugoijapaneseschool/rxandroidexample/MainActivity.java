package com.sugoijapaneseschool.rxandroidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Disposable mDisposable;
    TextView helloTv;
    TextView helloTv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helloTv = (TextView) findViewById(R.id.hello);
        helloTv2 = (TextView) findViewById(R.id.hello2);
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(e -> btnBlicked());
    }

    private void btnBlicked() {

        //timer 를 이용하면 앞서 했던 thread 를 안써도 일정시간 지난후 구독이 이루어지게 할수 있다..
        Observable.timer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())  //결과는 화면 메인쓰레드에서 하자.
                .subscribe(d -> {
                    changeText(getJest());
                });


        //just 는 아이템을 바로바로 서브스크들한테 방출한다.
        Observable.just("Hello ! from just").subscribe(s -> changeText(s));
    }

    private void changeText(String txt) {
        helloTv.setText(txt);
        helloTv2.setText(txt);
    }

    private String getJest() {
        return "Hello! from create";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}