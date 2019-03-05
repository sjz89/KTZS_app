package me.daylight.ktzs.http;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import me.daylight.ktzs.app.KTZSApp;
import me.daylight.ktzs.utils.GlobalField;
import me.daylight.ktzs.utils.SharedPreferencesUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WsManager extends WebSocketListener {
    private static WsManager mInstance;
    private WebSocket webSocket;
    private String phone;
    private boolean isConnected;

    private WsManager() {

        heartBeatTask.start();
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

    public void init() {
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
        String url="ws://"+ SharedPreferencesUtil.getString(KTZSApp.getApplication().getApplicationContext(),
                GlobalField.SETTING,GlobalField.URL)+"/webSocketService?phone="+phone;
        Request request = new Request.Builder()
                .url(url)
                .build();
        webSocket=client.newWebSocket(request, this);
        client.dispatcher().executorService().shutdown();
    }

    public void sendMessage(Map<String,Object> msgMap) {
        webSocket.send(new Gson().toJson(msgMap));
    }

    public void disconnect() {
        mInstance = null;
        if (webSocket != null)
            webSocket.close(1000, "close");
        heartBeatTask.interrupt();
        heartBeatTask=null;
    }

    private void handleMessage(String msg) {

    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        Log.d("onOpen","");
        webSocket.send("{\"msgType\":102}");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        try {
            JSONObject jsonObject=new JSONObject(text);
            if (!jsonObject.has("msgType"))
                return;
            switch (jsonObject.getInt("msgType")) {
                case GlobalField.WebSocket_Type_Message:
                    handleMessage(jsonObject.getString("msg"));
                    break;
                case GlobalField.WebSocket_Type_HeartBeat:
                    isConnected = true;
                    break;
                case GlobalField.WebSocket_Type_Push:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        init();
    }

    private Thread heartBeatTask =new Thread (() -> {
        while (mInstance != null) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            webSocket.send("{\"msgType\":102}");
            if (isConnected) {
                isConnected=false;
                continue;
            }
            init();
        }
    });
}
