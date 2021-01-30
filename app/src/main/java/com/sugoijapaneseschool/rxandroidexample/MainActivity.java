package com.sugoijapaneseschool.rxandroidexample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;

public class MainActivity extends AppCompatActivity {
    private ConnectableObservable<String> dataStream;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView helloTv = (TextView) findViewById(R.id.hello);
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(e -> btnBlicked());

        dataStream = Observable.just("Hello ! from just" )
                .observeOn(AndroidSchedulers.mainThread())
                .publish();
        dataStream.subscribe(s -> helloTv.setText(s));

    }

    public void addMe(View view) {
        TextView helloTv2 = (TextView) findViewById(R.id.hello2);
        dataStream.subscribe(s -> helloTv2.setText(s));
    }

    private void btnBlicked() {
        disposable = dataStream.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}