package com.kim.hbase;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.ColumnValueFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

@Slf4j
public class HBaseDML {

    public static Connection connection = HBaseConnection.connection;

    /**
     * @param rowKey       主键
     * @param columnFamily 列族
     * @param columnName   列名
     * @param value        值
     */
    public static void putCell(String nameSpace, String tableName, String rowKey,
                               String columnFamily, String columnName, String value) throws IOException {
        Table table = connection.getTable(TableName.valueOf(nameSpace, tableName));

        Put put = new Put(Bytes.toBytes(rowKey));

        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(columnName), Bytes.toBytes(value));

        try {
            table.put(put);
        } catch (IOException e) {
            log.error("数据写入异常", e);
        }

        table.close();
    }

    /**
     * 读取数据：读取一行中对应的某一列
     */
    public static void getCells(String nameSpace, String tableName, String rowKey,
                                String columnFamily, String columnName) throws IOException {
        Table table = connection.getTable(TableName.valueOf(nameSpace, tableName));

        try {
            Get get = new Get(Bytes.toBytes(rowKey));

            // 读取某一列数据
            get.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(columnName));
            get.readAllVersions();

            Result result = table.get(get);
            Cell[] cells = result.rawCells();
            for (Cell cell : cells) {
                String value = new String(CellUtil.cloneValue(cell));
                log.info("value={}", value);
            }
        } catch (IOException e) {
            log.error("数据读取异常", e);
        }

        table.close();
    }

    /**
     * 扫描数据
     *
     * @param startRow [
     * @param stopRow  )
     */
    public static void scanRows(String nameSpace, String tableName, String startRow,
                                String stopRow) throws IOException {
        Table table = connection.getTable(TableName.valueOf(nameSpace, tableName));
        try {
            Scan scan = new Scan();

            scan.withStartRow(Bytes.toBytes(startRow));
            scan.withStopRow(Bytes.toBytes(stopRow));
            // 读取多行
            ResultScanner scanner = table.getScanner(scan);
            for (Result result : scanner) {
                Cell[] cells = result.rawCells();
                for (Cell cell : cells) {
                    log.info("Qualifier={}, Family={}, Row={}, Value={}",
                            new String(CellUtil.cloneQualifier(cell)),
                            new String(CellUtil.cloneFamily(cell)),
                            new String(CellUtil.cloneRow(cell)),
                            new String(CellUtil.cloneValue(cell)));
                }
            }
        } catch (IOException e) {
            log.error("数据扫描异常", e);
        }
        table.close();
    }


    /**
     * 过滤扫描
     */
    public static void filterScan(String nameSpace, String tableName, String startRow, String stopRow,
                                  String columnFamily, String columnName, String condition) throws IOException {
        Table table = connection.getTable(TableName.valueOf(nameSpace, tableName));
        try {
            Scan scan = new Scan();

            scan.withStartRow(Bytes.toBytes(startRow));
            scan.withStopRow(Bytes.toBytes(stopRow));

            FilterList filterList = new FilterList();

            // 结果只保留当列的过滤器
            ColumnValueFilter filter = new ColumnValueFilter(Bytes.toBytes(columnFamily), Bytes.toBytes(columnName), CompareOperator.EQUAL, Bytes.toBytes(condition));
//            filterList.addFilter(filter);

            // 结果保留整行（多列）
            SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(Bytes.toBytes(columnFamily), Bytes.toBytes(columnName), CompareOperator.EQUAL, Bytes.toBytes(condition));
            filterList.addFilter(singleColumnValueFilter);

            scan.setFilter(filterList);

            // 读取多行
            ResultScanner scanner = table.getScanner(scan);
            for (Result result : scanner) {
                Cell[] cells = result.rawCells();
                for (Cell cell : cells) {
                    log.info("Qualifier={}, Family={}, Row={}, Value={}",
                            new String(CellUtil.cloneQualifier(cell)),
                            new String(CellUtil.cloneFamily(cell)),
                            new String(CellUtil.cloneRow(cell)),
                            new String(CellUtil.cloneValue(cell)));
                }
            }
        } catch (IOException e) {
            log.error("数据扫描异常", e);
        }
        table.close();
    }


    /**
     * 删除一行中的一列
     */
    public static void deleteColumn(String nameSpace, String tableName, String rowKey,
                                    String columnFamily, String columnName) throws IOException {
        Table table = connection.getTable(TableName.valueOf(nameSpace, tableName));

        try {
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            // 删除一个版本
            delete.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(columnName));
            // 删除所有版本
            delete.addColumns(Bytes.toBytes(columnFamily), Bytes.toBytes(columnName));
            table.delete(delete);
        } catch (IOException e) {
            log.error("列删除异常", e);
        }
        table.close();
    }


    public static void main(String[] args) throws IOException {
        putCell("bigdata", "student", "2001", "info", "name", "scq0");
        putCell("bigdata", "student", "2001", "info", "name", "scq1");
        putCell("bigdata", "student", "2001", "info", "name", "scq2");
        putCell("bigdata", "student", "2001", "info", "name", "scq3");

        getCells("bigdata", "student", "2001", "info", "name");

        scanRows("bigdata", "student", "1001", "1004");

        filterScan("bigdata", "student", "1001", "1004",
                "info", "name", "tianqi");


        deleteColumn("bigdata", "student", "2001",
                "info", "name");

        HBaseConnection.closeConnection();
    }
}
