package me.daylight.ktzs.http;

import android.util.Log;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import me.daylight.ktzs.app.KTZSApp;
import me.daylight.ktzs.entity.EventMsg;
import me.daylight.ktzs.entity.Notice;
import me.daylight.ktzs.utils.GlobalField;
import me.daylight.ktzs.utils.SharedPreferencesUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WsManager extends WebSocketListener {
    public static final String Channel_SignInCount="signInCount";
    public static final String Channel_Notice="notice";

    private static WsManager mInstance;
    private Map<String,WebSocket> webSocketMap;
    private String idNumber;
    private boolean isConnected;

    private WsManager() {
        webSocketMap=new ConcurrentHashMap<>();
        idNumber=SharedPreferencesUtil.getString(KTZSApp.getApplication().getApplicationContext(),
                GlobalField.USER,"idNumber");
    }

    public static WsManager getInstance() {
        if (mInstance == null) {
            synchronized (WsManager.class) {
                if (mInstance == null) {
                    mInstance = new WsManager();
                }
            }
        }
        return mInstance;
    }

    public void init(String channel) {
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
        String url="ws://"+ SharedPreferencesUtil.getString(KTZSApp.getApplication().getApplicationContext(),
                GlobalField.SETTING,GlobalField.URL)+(channel.equals(Channel_SignInCount)?":8080/webSocket/":"/webSocket/")+channel+"/"+idNumber;
        Request request = new Request.Builder()
                .url(url)
                .build();
        WebSocket webSocket=client.newWebSocket(request, this);
        webSocketMap.put(channel,webSocket);
        client.dispatcher().executorService().shutdown();
        if (channel.equals(Channel_Notice)&&!heartBeatTask.isAlive())
            heartBeatTask.start();
    }

    public void disconnect(String channel) {
        if (mInstance==null||webSocketMap==null)
            return;
        if (channel.equals(Channel_Notice)) {
            heartBeatTask.interrupt();
            heartBeatTask=null;
        }
        if (webSocketMap.containsKey(channel)&&webSocketMap.get(channel)!=null) {
            Objects.requireNonNull(webSocketMap.get(channel)).close(1000, "close");
            webSocketMap.remove(channel);
        }
    }

    public void destroy(){
        mInstance=null;
        if (webSocketMap!=null){
            for (String channel:webSocketMap.keySet())
                disconnect(channel);
            webSocketMap=null;
        }
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        Log.d("onOpen","");
        if (Objects.equals(getChannel(webSocket), Channel_Notice))
            webSocket.send("0x00");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        if (text.equals("0x01")) {
            isConnected = true;
            return;
        }
        String channel=getChannel(webSocket);
        if (channel==null)
            return;
        if (channel.equals(Channel_SignInCount))
            EventBus.getDefault().post(new EventMsg(GlobalField.Event_Channel_SignInCount,Integer.parseInt(text)));
        else if (channel.equals(Channel_Notice))
            EventBus.getDefault().post(new EventMsg(GlobalField.Event_Channel_Notice,new Gson().fromJson(text, Notice.class)));
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        Log.d("onClosing", reason);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        Log.d("onClosed", reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.d("onFailure", t+"");
    }

    private String getChannel(WebSocket webSocket){
        for (Map.Entry<String,WebSocket> entry:webSocketMap.entrySet()){
            if (entry.getValue().equals(webSocket))
                return entry.getKey();
        }
        return null;
    }

    private Thread heartBeatTask =new Thread (() -> {
        while (mInstance!=null&&webSocketMap.containsKey(Channel_Notice)&&webSocketMap.get(Channel_Notice)!=null) {
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (webSocketMap!=null)
                Objects.requireNonNull(webSocketMap.get(Channel_Notice)).send("0x00");
            if (isConnected) {
                isConnected=false;
                continue;
            }
            init(Channel_Notice);
        }
    });
}
