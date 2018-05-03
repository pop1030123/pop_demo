package com.pop.demo.activity.threadDemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.pop.demo.R;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by pengfu on 03/05/2018.
 * <p>
 * 1.一个生产线程，负责生产；
 * 2.一个消费线程，负责消费；
 * 3.一个队列，负责保存生产的产品；
 * 4.如果队列小于5个，就一直生产，如果大于5个，就停止生产，等待通知；
 * 5.如果队列中有产品 ，就消费，没有产品，就停止消费，等待通知；
 * 6.生产线程，每生产一个产品，就放入队列中；
 * 7.消费线程，每消费一个产品，就从队列中移除；
 */

public class ProducerConsumerActivity extends Activity implements View.OnClickListener {


    private static final String TAG = ProducerConsumerActivity.class.getSimpleName();

    private ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(5);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_producer_consumer);
        findViewById(R.id.tv_start_producer).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start_producer:
                // 启动进行生产，
                producer.start();
                //同时消费者进行消费
                consumer.start();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        producer.interrupt();
        consumer.interrupt();
    }

    /**
     * 生产者线程
     */
    private Thread producer = new Thread(new Runnable() {
        @Override
        public void run() {
            //
            try {
                while (true) {
                    String product = String.valueOf(System.currentTimeMillis());
                    queue.put(product);
                    Log.d(TAG, "生产了：" + product);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Log.d(TAG, "生产者被中断了：" + e);
            }

            Log.d(TAG, "工厂倒闭。");
        }
    });


    /**
     * 消费者线程
     */
    private Thread consumer = new Thread(new Runnable() {
        @Override
        public void run() {
            boolean stop = false ;
            String product = null;
            try {
                while ((product = queue.take()) != null && !stop) {
                    Log.d(TAG, "消费了：" + product);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.d(TAG, "消费者睡觉时被中断了：" + e);
                        stop = true ;
                    }
                }
            } catch (InterruptedException e) {
                Log.d(TAG, "消费者被中断了：" + e);
            }
            Log.d(TAG, "把剩余的产品都消费完，剩余产品个数：" + queue.size());
            Log.d(TAG, "消费者倒闭。");
        }
    });
}
