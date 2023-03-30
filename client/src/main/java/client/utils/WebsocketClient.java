package client.utils;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class WebsocketClient {
    private StompSession session;

    /**
     * Creates a stomp session
     * @param ip the server ip the client is connected to
     */
    public void setStompSession(String ip) {
        String url =ip.replace("http://", "").replaceAll("(/)", "");
        this.session = connect("ws://" + url + "/websocket");
    }

    private Map<String, StompSession.Subscription> subscriptions = new HashMap<>();

    /**
     * Connects to the websocket endpoint
     * @param url the endpoint to be connected to
     * @return a stomp session
     */
    private StompSession connect(String url){
        StandardWebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        try {
            System.out.println("Connecting to WebSocket server...");
            return stompClient.connect(url, new StompSessionHandlerAdapter() {

            }).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException();
    }

    /**
     * Subscribes to a destination and listens for updates
     * @param dest the destination it subscribes to
     * @param type specifies the class type of the payload
     * @param consumer the actual payload
     * @param <T> the type of the payload
     */
    public <T> void registerForMessages(String dest, Class<T> type, Consumer<T> consumer){
         subscriptions.put(dest, session.subscribe(dest, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return type;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((T)payload);
            }
        }));
        System.out.println("Subscribed to: " + dest);
    }

    /**
     * Unsubscribes from a given endpoint
     * @param dest the endpoint to unsubscribe from
     */
    public void unsubscribe(String dest){
         subscriptions.remove(dest).unsubscribe();
        System.out.println("Unsubscribed from: " + dest);
    }

//    public void sendMessage(String dest, Object o){
//        session.send(dest, o);
//    }
}
