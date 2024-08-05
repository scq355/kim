package com.kim.hbase;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;


import java.io.IOException;

/**
 * Hello world!
 */
public class HBaseConnection {
    public static void main(String[] args) throws IOException {
        Configuration configuration = new Configuration();

        configuration.set("hbase.zookeeper.quorum", "hadoop104,hadoop105,hadoop106");

        Connection connection = ConnectionFactory.createConnection(configuration);
        System.out.println(connection.getAdmin().getMaster().getServerName());

        connection.close();
    }
}
