package com.punuo.sys.app.agedcare.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;


import com.punuo.sys.app.agedcare.R;
import com.punuo.sys.app.agedcare.sip.SipInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.punuo.sys.app.agedcare.sip.SipInfo.format;

public class CommunityActivity extends HindebarActivity {
    public Button back;
    private TextView tv_time;
    public TextView net;
    public Button web_back;
    public Button web_forward;
    SwipeRefreshLayout gank_swipe_refresh_layout;
    private  final int msgKey1 = 1;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        webView=(WebView)findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setSupportZoom(true);
// 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
//扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
//自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.loadUrl("http://sip.qinqingonline.com:8888/qinqingcommunity?devid="+ SipInfo.devId+"&page=1");//此处填链接
        Log.e("sheeeequuuuu","http://sip.qinqingonline.com:8888/qinqingcommunity?devid="+ SipInfo.devId+"&page=1");
        tv_time = (TextView) findViewById(R.id.time);
        net=(TextView)findViewById(R.id.net);
        gank_swipe_refresh_layout=(SwipeRefreshLayout)findViewById(R.id.gank_swipe_refresh_layout) ;
        back=(Button)findViewById(R.id.web_view_back);
        web_back=(Button)findViewById(R.id.web_back);
        web_forward=(Button)findViewById(R.id.web_forward);
        gank_swipe_refresh_layout.setSize(SwipeRefreshLayout.LARGE);
        gank_swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // do something, such as re-request from server or other
                webView.reload();
                new Handler().postDelayed(new Runnable() {//模拟耗时操作
                    @Override
                    public void run() {
                        gank_swipe_refresh_layout.setRefreshing(false);//取消刷新

                    }
                },1500);

            }

        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    finish();


            }
        });
        web_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("title","back");
                if (webView.canGoBack()) {
                    webView.goBack();//返回上个页面

                } else {
                    finish();
                }
            }
        });
        web_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoForward())
                {
                    webView.goForward();
                }
            }
        });
        new CommunityActivity.TimeThread().start();
        if(is4GAvailable())
        {
            net.setText(R.string.fourG);
        }else if (isWifiAvailable()){
            net.setText(R.string.wifi);
        }else if (isNetworkAvailable(this)){
            net.setText("有线网");
        }else {
            net.setText("无网络");
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if(keyCode== KeyEvent.KEYCODE_BACK&&webView.canGoBack()){
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public class TimeThread extends Thread{
        @Override
        public void run() {
            super.run();
            do{
                try {
                    Thread.sleep(100);
                    Message msg = new Message();
                    msg.what = msgKey1;
                    mHandler.sendMessage(msg);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (true);
        }
    }
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what){
                case msgKey1:
                    long time = System.currentTimeMillis();
                    Date date = new Date(time);
                    format = new SimpleDateFormat("aa hh:mm");
                    tv_time.setText(format.format(date));
                    break;
                default:
                    break;
            }
            return false;
        }
    });
    public boolean isWifiAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected() && networkInfo
                .getType() == ConnectivityManager.TYPE_WIFI);
    }

    public boolean is4GAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            TelephonyManager telephonyManager = (TelephonyManager)
                    this.getSystemService(
                            Context.TELEPHONY_SERVICE);
            int networkType = telephonyManager.getNetworkType();
            /** Current network is LTE */
            if (networkType == 13) {
                /**此时的网络是4G的*/
                return true;
            }
        }
        return false;
    }
    /**
     * 判断以太网网络是否可用

     */
    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ethNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
//        NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);


        if (ethNetInfo != null && ethNetInfo.isConnected()) {
            return true;
        } else  {
            return false;
        }
    }
    private void waitFor() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
