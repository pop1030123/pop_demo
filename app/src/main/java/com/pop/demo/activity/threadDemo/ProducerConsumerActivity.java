package com.pop.demo.activity.threadDemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.pop.demo.R;
import com.pop.demo.util.ToastUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    private TextView tv_producer ,tv_queue ,tv_consumer ;

    private TextView btn_producer ,btn_consumer ;

    private ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(5);

    /**
     * producerLock主要是为了控制生产者的暂停生产和继续生产;
     */
    private ReentrantLock producerLock = new ReentrantLock() ;
    /**
     * consumerLock主要是为了控制消费者的暂停消费和继续消费;
     */
    private ReentrantLock consumerLock = new ReentrantLock() ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_producer_consumer);
        findViewById(R.id.btn_start).setOnClickListener(this);

        tv_producer = findViewById(R.id.tv_producer) ;
        tv_queue = findViewById(R.id.tv_queue) ;
        tv_consumer = findViewById(R.id.tv_consumer) ;

        tv_producer.setText("生产者：");
        tv_queue.setText("队列：");
        tv_consumer.setText("消费者：");

        btn_producer = findViewById(R.id.btn_producer) ;
        btn_consumer = findViewById(R.id.btn_consumer) ;

        btn_producer.setOnClickListener(this);
        btn_consumer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                // 启动进行生产，
                producer.start();
                //同时消费者进行消费
                consumer.start();
                break;
            case R.id.btn_producer:
                toggleProducer();
                break ;
            case R.id.btn_consumer:
                toggleConsumer();
                break ;
        }
    }

    private void toggleProducer(){
        if(producerLock.isHeldByCurrentThread()){
            Log.d(TAG ,"toggleProducer：生产者解锁");
            producerLock.unlock();
            btn_producer.setText("暂停生产");
        }else{
            Log.d(TAG ,"toggleProducer：准备生产者锁上");
            // 使用tryLock()，避免获取不到锁而阻塞主线程；
            boolean lockResult = producerLock.tryLock();
            if(lockResult){
                tv_producer.setText("生产者：暂停生产。");
                btn_producer.setText("开始生产");
            }else{
                Log.d(TAG ,"toggleProducer：生产者锁上-->没锁上");
                ToastUtils.showSingleToast("正在等待生产，无法暂停。");
            }
        }
    }

    private void toggleConsumer(){
        if(consumerLock.isHeldByCurrentThread()){
            Log.d(TAG ,"toggleConsumer：消费者解锁");
            consumerLock.unlock();
            btn_consumer.setText("暂停消费");
        }else{
            Log.d(TAG ,"toggleConsumer：准备消费者锁上");
            // 使用tryLock(),避免lock()获取不到而阻塞主线程；
            boolean lockResult = consumerLock.tryLock();
            if(lockResult){
                tv_consumer.setText("消费者：暂停消费。");
                btn_consumer.setText("开始消费");
            }else{
                Log.d(TAG ,"toggleConsumer：消费者锁上-->没锁上");
                ToastUtils.showSingleToast("正在等待消费，无法暂停。");
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 停止生产
        producer.interrupt();
        // 停止消费
        consumer.interrupt();
    }

    /**
     * 生产者线程
     */
    private Thread producer = new Thread(new Runnable() {
        @Override
        public void run() {
            //
            //
            int index = 0 ;
            try {
                while (true) {

                    producerLock.lock();

                    index++ ;
                    String product = "产品"+index;
                    myHandler.sendMessage(myHandler.obtainMessage(MSG_PRODUCE ,product));
                    queue.put(product);
                    myHandler.sendEmptyMessage(MSG_QUEUE);
                    Log.d(TAG, "生产了：" + product);

                    producerLock.unlock();

                    Thread.sleep(1000);
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
                while (!stop) {
                    consumerLock.lock();

                    product = queue.take();
                    myHandler.sendEmptyMessage(MSG_QUEUE);
                    Log.d(TAG, "消费了：" + product);
                    myHandler.sendMessage(myHandler.obtainMessage(MSG_CONSUME ,product));

                    consumerLock.unlock();
                    try {
                        Thread.sleep(2000);
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


    private static final int MSG_PRODUCE = 1 ;
    private static final int MSG_CONSUME = 2 ;
    private static final int MSG_QUEUE = 3 ;

    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_PRODUCE:
                    // 生产了一个
                    String in_product = msg.obj.toString() ;
                    tv_producer.setText("生产者："+in_product);
                    break ;
                case MSG_QUEUE:
                    tv_queue.setText("队列："+queue);
                    break ;
                case MSG_CONSUME:
                    // 消费了一个
                    String out_product = msg.obj.toString();
                    tv_consumer.setText("消费者："+out_product);
                    break ;
            }
        }
    };
}
