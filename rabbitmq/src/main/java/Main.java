import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    private static Map<String, Integer> live_score = new ConcurrentHashMap<>();
    private final static String QUEUE_NAME = "hello";
    private static void initScore() {
        live_score.put("Player1", 0);
        live_score.put("Player2", 0);
    }
    private static void resetData() {
        live_score.put("Player1", 0);
        live_score.put("Player2", 0);
    }
    private static void decodeData(String data) {
        if (data.equals("Exit"))
        {
            liveScore(1);
            resetData();
            return ;
        }
        String[] decodedData = data.split("\\|");
        live_score.put(decodedData[0], live_score.get(decodedData[0]) + Integer.parseInt(decodedData[1]));
        liveScore(0);

    }
    private static void liveScore(int flag) {
        if (flag == 1) {
            System.out.println("#### The final score is ####");
        }
        if (live_score.get("Player1") > live_score.get("Player2")) {
            System.out.println("Player 1 wins with the score " + live_score.get("Player1") + " > " + live_score.get("Player2"));
        }
        else  if (live_score.get("Player1") < live_score.get("Player2")) {
            System.out.println("Player 2 wins with the score " + live_score.get("Player1") + " < " + live_score.get("Player2"));
        }
        else
            System.out.println("TIE with the score: " + live_score.get("Player1"));
    }
    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Main.initScore();

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            decodeData(message);
            //System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}