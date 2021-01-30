package com.sugoijapaneseschool.rxandroidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView helloTv = (TextView) findViewById(R.id.hello);

//        이 부분은 지금은 간단하게 람다형식을 처리할거임. s -> helloTv.setText(s)
//        Observer<String> observable=new DisposableObserver<String>(){
//            @Override
//            public void onNext(String s) {
//                helloTv.setText(s);
//            }
//
//            @Override
//            public void onError(Throwable e) { }
//            @Override
//            public void onComplete() { }
//        };

        //create 는 아이템을 바로바로 서브스크들한테 방출한다.
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            emitter.onNext(getJest());
            emitter.onComplete();
        })
                .subscribeOn(Schedulers.io()) //스케쥴러로 몰래 뒤에서 실행시키고
                .observeOn(AndroidSchedulers.mainThread())  //결과는 화면 메인쓰레드에서 하자.
                .subscribe(s -> helloTv.setText(s));


        //just 는 아이템을 바로바로 서브스크들한테 방출한다.
        Observable.just("Hello ! from just").subscribe(s -> helloTv.setText(s));
    }

    private String getJest() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello! from create";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}