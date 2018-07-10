package com.app.sample.chatting.SignalR.Classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.app.sample.chatting.SharedPreference.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.SignalR.Interface.HubConnection;
import com.app.sample.chatting.SignalR.Interface.HubConnectionListener;
import com.app.sample.chatting.SignalR.Interface.HubEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.SSLSocketFactory;

/**
 * This class for alpha version of server.
 *
 * @deprecated use {@link WebSocketHubConnectionP2} instead for preview2-final version.
 */
@Deprecated
public class WebSocketHubConnection  implements HubConnection  {
    private static String SPECIAL_SYMBOL = "\u001E";
    private static String TAG = "WebSockets";
    public static boolean Flag=false;
    public static WebSocketClient client;
    private List<HubConnectionListener> listeners = new ArrayList<>();
    private Map<String, List<HubEventListener>> eventListeners = new HashMap<>();
    private Uri parsedUri;
    private String hubUrl;
    private Gson gson = new Gson();
    private static String connectionId;
    private String authHeader;
    public Context context;
    private static  boolean flag;


    public WebSocketHubConnection()
    {
    }

    @Deprecated
    public WebSocketHubConnection(String hubUrl, String authHeader,Context ctx)
    {
        this.hubUrl = hubUrl;
        this.authHeader = authHeader;
        parsedUri = Uri.parse(hubUrl);
        this.context=ctx;
    }


   public static boolean CheckedConnection()
   {
        if(client !=null && (client.isOpen() || client.isConnecting()))
        {
            Flag=true;
        }
        else if(client == null || client.isClosed())
        {
            Flag=false;
        }
       return Flag;
   }

    @Override
    public synchronized void connect()
    {
        if (client != null && (client.isOpen() || client.isConnecting()))
        {
            return;
        }
        Runnable runnable;
        if (connectionId == null)
        {
            runnable = new Runnable()
            {
                public void run()
                {
                    getConnectionId();
                    /*
                    Intent intent = new Intent("SignalRConnection");
                    // You can also include some extra data.
                    intent.putExtra("SignalR", flag);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);*/

                }
            };
        } else
            {
            runnable = new Runnable()
            {
                public void run()
                {
                    connectClient();
                    /*Intent intent = new Intent("SignalRConnection");
                    // You can also include some extra data.
                    intent.putExtra("SignalR", flag);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);*/
                }
            };
        }
        new Thread(runnable).start();

    }

    private void getConnectionId()
    {
        Log.i(TAG, "Requesting connection id...");
        if (!(parsedUri.getScheme().equals("http") || parsedUri.getScheme().equals("https")))
            throw new RuntimeException("URL must start with http or https");

        try
        {
            HttpURLConnection connection = (HttpURLConnection) new URL(hubUrl).openConnection();
            if (authHeader != null && !authHeader.isEmpty())
            {
                connection.addRequestProperty("Authorization", authHeader);
            }
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setRequestMethod("OPTIONS");
            int responseCode = connection.getResponseCode();

            if (responseCode == 200)
            {
                String result = InputStreamConverter.convert(connection.getInputStream());
                JsonElement jsonElement = gson.fromJson(result, JsonElement.class);
                connectionId = jsonElement.getAsJsonObject().get("connectionId").getAsString();
                JsonElement availableTransportsElements = jsonElement.getAsJsonObject().get("availableTransports");
                List<String> availableTransports = Arrays.asList(gson.fromJson(availableTransportsElements, String[].class));
                if (!availableTransports.contains("WebSockets"))
                {
                    throw new RuntimeException("The server does not support WebSockets transport");
                }
                //tempo.savePreferences(context.getApplicationContext(),"ConnectionId",connectionId);
                connectionId = connectionId;
                connectClient();
            }
            else if (responseCode == 401)
            {
                RuntimeException runtimeException = new RuntimeException("Unauthorized request");
                error(runtimeException);
                flag=false;
                throw runtimeException;
            }
            else
                {
                RuntimeException runtimeException = new RuntimeException("Server error");
                error(runtimeException);
                flag=false;
                throw runtimeException;
            }
        }
        catch (Exception e)
        {
            error(e);
        }
        Intent intent = new Intent("SignalRConnection");
        // You can also include some extra data.
        intent.putExtra("SignalR", flag);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void connectClient()
    {
        Uri.Builder uriBuilder = parsedUri.buildUpon();
        uriBuilder.appendQueryParameter("id", connectionId);
        uriBuilder.scheme(parsedUri.getScheme().replace("http", "ws"));
        Uri uri = uriBuilder.build();
        Map<String, String> headers = new HashMap<>();
        if (authHeader != null && !authHeader.isEmpty())
        {
            headers.put("Authorization", authHeader);
        }
        try
        {
            client = new WebSocketClient(new URI(uri.toString()), new Draft_6455(), headers, 15000)
            {
                @Override
                public void onOpen(ServerHandshake handshakeData)
                {
                    Log.i(TAG, "Opened");
                    for (HubConnectionListener listener : listeners)
                    {
                        listener.onConnected();
                    }
                    send("{\"protocol\":\"json\"}" + SPECIAL_SYMBOL);
                }

                @Override
                public void onMessage(String message)
                {
                    Log.i(TAG, message);
                    SignalRMessage element = gson.fromJson(message.replace(SPECIAL_SYMBOL, ""), SignalRMessage.class);
                    if (element.getType() == 1) {
                        HubMessage hubMessage = new HubMessage(element.getInvocationId(), element.getTarget(), element.getArguments());
                        for (HubConnectionListener listener : listeners)
                        {
                            listener.onMessage(hubMessage);
                        }

                        List<HubEventListener> hubEventListeners = eventListeners.get(hubMessage.getTarget());
                        if (hubEventListeners != null)
                        {
                            for (HubEventListener listener : hubEventListeners)
                            {
                                listener.onEventMessage(hubMessage);
                            }
                        }
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote)
                {
                    Log.i(TAG, String.format("Closed. Code: %s, Reason: %s, Remote: %s", code, reason, remote));
                    for (HubConnectionListener listener : listeners)
                    {
                        listener.onDisconnected();
                    }
                    connectionId = null;
                }

                @Override
                public void onError(Exception ex)
                {
                    Log.i(TAG, "Error " + ex.getMessage());
                    error(ex);
                }
            };

            if (parsedUri.getScheme().equals("https"))
            {
                client.setSocket(SSLSocketFactory.getDefault().createSocket());
            }
        }
        catch (Exception e)
        {
            error(e);
        }
        Log.i(TAG, "Connecting...");
        flag=true;
        client.connect();

    }

    private void error(Exception ex)
    {
        for (HubConnectionListener listener : listeners)
        {
            listener.onError(ex);
        }
    }

    @Override
    public void disconnect()
    {
        Runnable runnable = new Runnable()
        {
            public void run()
            {
                if (client != null && !(client.isClosed() || client.isClosing()))
                    client.close();
            }
        };
        new Thread(runnable).start();
    }

    @Override
    public void addListener(HubConnectionListener listener) {

        listeners.add(listener);
    }

    @Override
    public void removeListener(HubConnectionListener listener)
    {
        listeners.remove(listener);
    }

    @Override
    public void subscribeToEvent(String eventName, HubEventListener eventListener)
    {
        List<HubEventListener> eventMap;
        if (eventListeners.containsKey(eventName))
        {
            eventMap = eventListeners.get(eventName);
        }
        else
            {
            eventMap = new ArrayList<>();
            eventListeners.put(eventName, eventMap);
        }
        eventMap.add(eventListener);
    }

    @Override
    public void unSubscribeFromEvent(String eventName, HubEventListener eventListener)
    {
        if (eventListeners.containsKey(eventName))
        {
            List<HubEventListener> eventMap = eventListeners.get(eventName);
            eventMap.remove(eventListener);
            if (eventMap.isEmpty())
            {
                eventListeners.remove(eventName);
            }
        }
    }

    @Override
    public void invoke(String event, Object... parameters)
    {
        if (client == null )//|| !client.isOpen())
        {
            //Toast.makeText(context,"Not Send Try Again",Toast.LENGTH_LONG).show();
            throw new RuntimeException("Not connected");
        }
        final Map<String, Object> map = new HashMap<>();
        map.put("type", 1);
        map.put("invocationId", UUID.randomUUID());
        map.put("target", event);
        map.put("arguments", parameters);
        map.put("nonblocking", false);
        Runnable runnable = new Runnable()
        {
            public void run()
            {
                try
                {
                    client.send(gson.toJson(map) + SPECIAL_SYMBOL);
                }
                catch (Exception e)
                {
                    error(e);
                }
            }
        };
        new Thread(runnable).start();
    }



    private static class InputStreamConverter
    {
        static String convert(InputStream stream) throws IOException
        {
            BufferedReader r = new BufferedReader(new InputStreamReader(stream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null)
            {
                total.append(line);
                total.append('\n');
            }

            return total.toString();
        }
    }
    //public void BroadcastIntent()
}
