package com.pop.demo.activity.threadDemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.pop.demo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by pengfu on 05/05/2018.
 */

public class SellTicketActivity extends Activity implements View.OnClickListener {


    private static final String TAG = SellTicketActivity.class.getSimpleName();

    private Lock sellLock = new ReentrantLock();


    private TextView tv_sell_1, tv_sell_2, tv_sell_3, tv_sell_4;

    private int ticketCount ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sell_ticket);

        findViewById(R.id.btn_start).setOnClickListener(this);

        tv_sell_1 = findViewById(R.id.tv_sell_1);
        tv_sell_2 = findViewById(R.id.tv_sell_2);
        tv_sell_3 = findViewById(R.id.tv_sell_3);
        tv_sell_4 = findViewById(R.id.tv_sell_4);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
//                Executor executor = Executors.newFixedThreadPool(4);
//                for (int i=0 ;i< 4 ; i++){
//                    executor.execute(new BuyRunnable(i));
//                }
                ticketCount = 0 ;
                tv_sell_1.setText("售票窗口1：");
                tv_sell_2.setText("售票窗口2：");
                tv_sell_3.setText("售票窗口3：");
                tv_sell_4.setText("售票窗口4：");

                for (int i = 0; i < 4; i++) {
                    new Thread(new BuyRunnable(i)).start();
                }
                break;
        }
    }

    private class BuyRunnable implements Runnable {

        private int mSellIndex;

        public BuyRunnable(int sellIndex) {
            mSellIndex = sellIndex;
        }

        @Override
        public void run() {
            while (true) {
                // 买票线程
                sellLock.lock();
                if(ticketCount >= 20){
                    sellLock.unlock();
                    break ;
                }
                ticketCount++ ;
                final String thisTicket = "票"+ticketCount ;
                Log.d(TAG, "售票窗口" + mSellIndex + "卖出：" + thisTicket);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateSellCount(mSellIndex, thisTicket);
                    }
                });
                sellLock.unlock();
                try {
                    Random random = new Random();
                    int time = random.nextInt(5) * 100;
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Log.d(TAG, "票已经卖完了。");
        }
    }

    private void updateSellCount(int index, String ticket) {
        switch (index) {
            case 0:
                tv_sell_1.append("，");
                tv_sell_1.append(ticket);
                break;
            case 1:
                tv_sell_2.append("，");
                tv_sell_2.append(ticket);
                break;
            case 2:
                tv_sell_3.append("，");
                tv_sell_3.append(ticket);
                break;
            case 3:
                tv_sell_4.append("，");
                tv_sell_4.append(ticket);
                break;
        }
    }
}
