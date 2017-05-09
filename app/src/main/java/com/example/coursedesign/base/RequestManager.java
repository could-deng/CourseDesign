package com.example.coursedesign.base;

import android.os.Handler;
import android.util.Log;

import com.example.coursedesign.bean.Country;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;

/**
 * Created by yuanqiang on 2016/3/29.
 */
public class RequestManager {
    public static final int REQUEST_COUNTRY = 111;
    private ThreadPoolExecutor requestExecutor;
    private ThreadPoolExecutor uploadExecutor;
    private ThreadPoolExecutor smallFileDownloadExcutor;
    private ThreadPoolExecutor bigFileDownloadExcutor;

    private static RequestManager instance;
    public static RequestManager getInstance(){
        if(instance == null){
            instance = new RequestManager();
        }
        return instance;
    }
    public RequestManager(){
    }

    private ThreadPoolExecutor getRequestExcutor() {
        if (requestExecutor == null)
            requestExecutor = new ThreadPoolExecutor(2, 3, 10,
                    TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        return requestExecutor;
    }
    public void request(final String url, final Handler handler,
                        final int iRequestType) {
        if (url == null || url.isEmpty())
            return;
//        getRequestExcutor().execute(new Runnable() {
//            public void run() {
//                OkHttpUtil okhttputil = new OkHttpUtil();
//                Log.d(FitmixConstant.TAG, "url:" + url);
//                String sResult = okhttputil.getStringFromServer(url);
//                if (sResult == null)
//                    sResult = "";
//                Log.d(FitmixConstant.TAG, "result:" + sResult);
//                sendRequestResultMessage(sResult, handler, iRequestType);
//            }
//        });
        if(iRequestType == REQUEST_COUNTRY){
//            BmobQuery<Country> query = new BmobQuery<Country>();
//            //执行查询方法
//            query.findObjects(this, new FindListener<Country>() {
//                @Override
//                public void onSuccess(List<GameScore> object) {
//                    // TODO Auto-generated method stub
//                    toast("查询成功：共"+object.size()+"条数据。");
//                    for (GameScore gameScore : object) {
//                        //获得playerName的信息
//                        gameScore.getPlayerName();
//                        //获得数据的objectId信息
//                        gameScore.getObjectId();
//                        //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
//                        gameScore.getCreatedAt();
//                    }
//                }
//                @Override
//                public void onError(int code, String msg) {
//                    // TODO Auto-generated method stub
//                    toast("查询失败："+msg);
//                }
//            });
        }
    }


}
