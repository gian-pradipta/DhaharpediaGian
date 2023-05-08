package com.example.dhaharpedia;



import java.security.MessageDigest;
import java.sql.*;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class Koneksi
{
    static java.sql.Connection conn;
    static Statement statement;
    static PreparedStatement preparedStatement;
    static private ResultSet resultSet;

    public static String sha256(final String base) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static void start(){
        try {

            Scanner scanner = new Scanner(System.in);
//            System.out.print("Masukkan Nama: ");
//            String nama = scanner.nextLine();
//            System.out.print("Masukkan Password: ");
//            String password = scanner.nextLine();
            conn =  DriverManager.getConnection("jdbc:mysql://localhost/namapass", "root", "");
            statement = conn.createStatement();
//            insert("Chan", "password");
//            resultSet = statement.executeQuery("SELECT * FROM nama_pass");
//
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString("username"));
//                System.out.println(resultSet.getString("password"));
//            }
//            statement.close();
//            conn.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void insert(String nama, String pass) {
        try {
            pass = sha256(pass + "salt");
            String sql = "INSERT INTO nama_pass VALUES (" +
                    "'" + nama +"'"
                    + ", '" + pass + "'" + ")";
            statement.execute(sql);
//            System.out.println(sql);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


}
