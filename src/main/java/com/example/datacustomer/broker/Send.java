package com.example.datacustomer.broker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;

public class Send {
    public void sendData(String dataString){
        ConnectionFactory factory = new ConnectionFactory();        //Membuat koneksi ke RabbitMQ
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            Thread.sleep(100);                                //Membuat proses tertunda sementara,
            //agar receive bisa mulai dan standby bersamaan
            String ExchangeName = "tabeldatainf";                   //Menentukaan nama exchange
            boolean durable = true;
            channel.exchangeDeclare(ExchangeName, "fanout", durable, false, false, null);
            channel.basicPublish(ExchangeName, "",              //Mengirim pesan ke receive dan receiveLog
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    dataString.getBytes(StandardCharsets.UTF_8));
            System.out.println("[Producer] \t: Sent '" + dataString + "'");
        }catch (Exception e){
            System.out.println("Error Send ke RabbitMQ : " + e);
        }
    }
}