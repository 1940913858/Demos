package com.example.meetyou.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.meetyou.myapplication.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxJavaActivity extends Activity {

    String TAG = "senfa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_default);

        TextView default_txt = findViewById(R.id.default_txt);


        /*Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.e(TAG, "subscribe");
                emitter.onNext("1");
                emitter.onNext("2");
                emitter.onComplete();
                emitter.onNext("3");
                Log.e(TAG, "emitter 3");
            }
        }).subscribe(new Observer<String>() {
            Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
                disposable = d;

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, s);
                if (s.equals("1")) {
                    disposable.dispose();
                    Log.d(TAG, "isDisposed : " + disposable.isDisposed());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError:" + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });*/

        /*D/TAG: onSubscribe
        D/TAG: subscribe
        D/TAG: 1
        D/TAG: isDisposed : true
        D/TAG: emitter 3

        结果分析
        当在下游调用disposable.dispose()方法时，下游将不会在接收后续的事件，但上游还是会将全部事件发送完毕。
        这里的dispose就相当于将水管切断了，因此接收不了后续事件。*/


        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.e(TAG, "subscribe:" + Thread.currentThread().getName());
                emitter.onNext("1");
                emitter.onNext("2");
                emitter.onNext("3");
            }
        });
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "accept:" + s + ":" + Thread.currentThread().getName());
            }
        };
        //关注点
        observable.subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, "doOnNext accept:" + s + ":" + Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(consumer);

        /*
        输出结果
        E/senfa: subscribe:RxNewThreadScheduler-1
        E/senfa: doOnNext accept:1:main
        E/senfa: doOnNext accept:2:main
        E/senfa: doOnNext accept:3:main
        E/senfa: accept:1:RxCachedThreadScheduler-2
        E/senfa: accept:2:RxCachedThreadScheduler-2
        E/senfa: accept:3:RxCachedThreadScheduler-2

        结果分析
        修改后的代码指定了2次上游发送事件的线程，下游也指定了2次线程，通过输出结果，我们可以得出结论：上游线程只有第一次指定的有效，下游线程最终会切换至最后一个指定的线程。
        为了更加清晰的知道下游线程的切换过程，我们修改代码如下*/


    }


}
