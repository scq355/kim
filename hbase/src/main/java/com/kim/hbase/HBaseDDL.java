package com.kim.hbase;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.NamespaceExistException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

@Slf4j
public class HBaseDDL {

    public static Connection connection = HBaseConnection.connection;

    /**
     * 创建命名空间
     */
    public static void createNameSpace(String nameSpace) throws IOException {
        Admin admin = connection.getAdmin();
        try {
            admin.createNamespace(NamespaceDescriptor.create(nameSpace)
                    .addConfiguration("user", "kim")
                    .build());
        } catch (NamespaceExistException e) {
            log.warn("命名空间已经存在");
        }
        admin.close();
    }

    /**
     * 判断表格是否存在
     */
    public static boolean tableExist(String nameSpace, String tableName) throws IOException {
        Admin admin = connection.getAdmin();
        boolean b = false;
        try {
             b = admin.tableExists(TableName.valueOf(nameSpace, tableName));
        } catch (IOException e) {
            log.warn("获取表名异常");
        }
        admin.close();
        return b;
    }

    /**
     * 创建表格
     * @param columnFamilies 列族
     */
    public static void createTable(String nameSpace, String tableName, String... columnFamilies) throws IOException {
        if (columnFamilies.length == 0) {
            log.error("表格列族不能为空");
            return;
        }

        if (tableExist(nameSpace, tableName)) {
            log.error("表格已经存在");
            return;
        }
        Admin admin = connection.getAdmin();

        TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(TableName.valueOf(nameSpace, tableName));

        for (String columnFamily : columnFamilies) {
            tableDescriptorBuilder.setColumnFamily(
                    ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(columnFamily))
                            .setMaxVersions(5)
                            .build()
            );
        }

        try {
            admin.createTable(tableDescriptorBuilder.build());
        } catch (IOException e) {
            log.warn("表格创建异常");
        }

        admin.close();
    }

    /**
     * 修改表格
     */
    public static void modifyTable(String nameSpace, String tableName, String columnFamily, int version) throws IOException {

        if (!tableExist(nameSpace, tableName)) {
            log.warn("表格不存在");
            return;
        }

        Admin admin = connection.getAdmin();

        try {
            TableDescriptor descriptor = admin.getDescriptor(TableName.valueOf(nameSpace, tableName));

            ColumnFamilyDescriptor columnFamilyDescriptor = ColumnFamilyDescriptorBuilder
                    .newBuilder(descriptor.getColumnFamily(Bytes.toBytes(columnFamily)))
                    .setMaxVersions(version)
                    .build();

            TableDescriptor tableDescriptor = TableDescriptorBuilder.newBuilder(descriptor)
                    .modifyColumnFamily(columnFamilyDescriptor)
                    .build();
            admin.modifyTable(tableDescriptor);
        } catch (IOException e) {
            log.error("表格修改异常", e);
        }

        admin.close();
    }

    /**
     * 删除表格
     */
    public static boolean deleteTable(String nameSpace, String tableName) throws IOException {
        if (!tableExist(nameSpace, tableName)) {
            log.warn("表格不存在, 删除失败");
            return false;
        }

        Admin admin = connection.getAdmin();
        try {
            TableName table = TableName.valueOf(nameSpace, tableName);
            admin.disableTable(table);
            admin.deleteTable(table);
        } catch (IOException e) {
            log.error("删除表格异常", e);
        }

        admin.close();

        return true;
    }

    public static void main(String[] args) throws IOException {
        createNameSpace("kim");
        System.out.println(tableExist("bigdata", "student"));
        System.out.println(tableExist("bigdata", "person"));

        createTable("kim", "student", "info", "msg");

        modifyTable("kim", "student", "info", 6);

        System.out.println(deleteTable("kim", "student1"));
        HBaseConnection.closeConnection();
    }
}
