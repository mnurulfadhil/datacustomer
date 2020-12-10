package com.example.datacustomer.services;

import com.example.datacustomer.model.User;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

@Service
public class MyBatisService {
    SqlSession session;

    public void connectMyBatis() throws IOException {                           //Membuat koneksi ke MyBatis
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        session = sqlSessionFactory.openSession();
    }

    public String tambahUser(String userString) {                                //Method registrasi user
        System.out.println("Memulai menambahkan user.....");
        User user = new Gson().fromJson(userString, User.class);                //Konversi JsonString ke Object

        int hasil = 0;
        try {
            connectMyBatis();                                                   //Memulai koneksi MyBatis
            hasil = session.insert("tabeldatainf.tambahUser", user);         //Membuat proses insert record
        } catch (Exception e) {
            System.out.println("gagal menambahkan user = " + e);
        }

        if (hasil == 1) {
            session.commit();                                                   //Query akan dicommit jika
            session.close();                                                    //hasil = 1
            System.out.println("Berhasil menambahkan user....");
            return "Registrasi User : " + user.getNama() + " berhasil...";
        } else {
            session.close();
            System.out.println("Gagal menambahkan user....");
            return "Failed...";
        }
    }
    public String editUser(String userString){                                  //Method edit user
        System.out.println("Memulai edit user.....");
        User user = new Gson().fromJson(userString, User.class);                //Konversi JsonString ke Object

        int hasil = 0;
        try {
            connectMyBatis();                                                   //Memulai koneksi MyBatis
            hasil = session.update("tabledatainf.editUser", user);           //Membuat proses edit user
        }catch (Exception e){
            System.out.println("Error edit user = " + e);
        }

        if (hasil != 0){
            session.commit();                                                   //Query akan dicommit jika
            session.close();                                                    //hasil = 1
            System.out.println("Berhasil edit user....");
            return "Edit 'User : " + user.getNama() + "' berhasil...";
        } else {
            session.close();
            System.out.println("Gagal edit user....");
            return "Edit user gagal";
        }
    }

    public String deleteUser(String userString){                                //Method delete user
        System.out.println("Deleting.....");
        long id_user = Long.parseLong(userString);                              //Konversi String ke long

        int hasil = 0;
        try {
            connectMyBatis();                                                   //Memulai koneksi MyBatis
            hasil = session.delete("tabeldatainf.deleteUser", id_user);       //Membuat proses delete user
        }catch (Exception e){
            System.out.println("Error delete user = " + e);
        }

        if (hasil != 0){
            session.commit();                                                   //Query akan dicommit jika
            session.close();                                                    //hasil = 1
            System.out.println("Berhasil delete user....");
            return "Delete user berhasil...";
        } else {
            session.close();
            System.out.println("Gagal Delete user....");
            return "Delete user gagal";
        }
    }
}