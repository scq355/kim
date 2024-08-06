package com.kim.hbase;

import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;

import java.io.IOException;

public class HBaseDDL {

    public static Connection connection = HBaseConnection.connection;

    public static void createNameSpace(String nameSpace) throws IOException {
        Admin admin = connection.getAdmin();
        admin.createNamespace(NamespaceDescriptor.create(nameSpace)
                .addConfiguration("user", "kim")
                .build());
        admin.close();
    }

    public static void main(String[] args) throws IOException {
        createNameSpace("kim");
        HBaseConnection.closeConnection();
    }
}
