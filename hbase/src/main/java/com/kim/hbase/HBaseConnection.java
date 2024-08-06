package com.kim.hbase;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;


import java.io.IOException;

/**
 * Hello world!
 */
public class HBaseConnection {

    public static Connection connection = null;

    static {
        try {
            connection = ConnectionFactory.createConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() throws IOException {
        connection.close();
    }

    public static void main(String[] args) throws IOException {
        System.out.println(connection.getAdmin().getMaster().getServerName());
        closeConnection();
    }
}
