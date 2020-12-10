package com.example.datacustomer.broker;

import com.example.datacustomer.services.MyBatisService;
import com.rabbitmq.client.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class Receive {
    MyBatisService service = new MyBatisService();

    public String receive(String aksi) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String ExchangeName = "tabeldatainf";
        boolean autoAck = true;
        channel.exchangeDeclare(ExchangeName, "fanout", autoAck, false, false, null);
        String queueName = channel.queueDeclare().getQueue();       //Generate queue name
        channel.queueBind(queueName, ExchangeName, "");

        System.out.println("[Receiver] \t: Menunggu pesan dari database...");
        GetResponse response;
        do {                                                         //Looping untuk
            response = channel.basicGet(queueName, true);        //mengambil pesan dari RabbitMQ
        } while (response == null);                                  //berhenti jika pesan sudah diambil

        String dataString = new String(                             //Mengambil pesan yang diterima oleh respon
                response.getBody(), "UTF-8");

        channel.close();                                            //Menutup koneksi
        connection.close();

        //Mengirim ke database dan mengembalikan hasil proses di database
        switch (aksi) {                                              //Mengakses database sesuai dengan
            case "tambahuser":
                return service.tambahUser(dataString);
            case "edituser":
                return service.editUser(dataString);
            case "deleteuser":
                return service.deleteUser(dataString);
            default:return "aksi salah";
        }
    }
}
