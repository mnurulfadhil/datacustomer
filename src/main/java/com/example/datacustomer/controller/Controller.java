package com.example.datacustomer.controller;

import com.example.datacustomer.broker.Receive;
import com.example.datacustomer.broker.Send;
import com.example.datacustomer.model.User;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tabeldatainf")
public class Controller {
    Send send=new Send();
    Receive receive=new Receive();

    @PostMapping("/tambahuser")
    public ResponseEntity<?> tambahUser(@RequestBody User user){
        String userString =new Gson().toJson(user);
        String pesanDB="";
        try{
            Thread thread = new Thread(){                               //Membuat thread
                public void run(){
                    send.sendData(userString);                          //Send ke RabbitMQ dengan thread
                }                                                       //agar receive bisa mulai berjalan dan standby
            };
            thread.start();                                              //Memulai thread Send
            pesanDB = receive.receive("registrasi");                //Memmulai receive dan mengambil hasilnya
            //thread.stop();
        }catch (Exception e){
            System.out.println("Gagal mengirim dan/atau menerima RabbitMQ : " + e );
            return new ResponseEntity<>("Registrasi gagal...", HttpStatus.OK);
        }
        return new ResponseEntity<>(pesanDB, HttpStatus.OK);
    }
}
