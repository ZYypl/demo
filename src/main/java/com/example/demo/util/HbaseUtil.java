//package com.example.demo.util;
//
//import com.example.demo.service.impl.HbaseServiceImpl;
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.hbase.Cell;
//import org.apache.hadoop.hbase.HColumnDescriptor;
//import org.apache.hadoop.hbase.HTableDescriptor;
//import org.apache.hadoop.hbase.TableName;
//import org.apache.hadoop.hbase.client.*;
//import org.apache.hadoop.hbase.util.Bytes;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.hadoop.hbase.HbaseTemplate;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.MessageFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * com.example.demo.util
// *
// * @author ypl
// * @create 2020-10-23 14:26
// */
//@Component
//public class HbaseUtil {
//
////    public static String quorum;
////    public static String port;
////
////    @Value("${hbase.zookeeper.quorum}")
////    public void setQuorum(String quorum1){
////        quorum=quorum1;
////    }
////
////    @Value("${hbase.zookeeper.port}")
////    public void setPort(String port1){
////        port=port1;
////    }
//
//
//    private  static Admin admin;
//    public static Connection conn;
//    private static Logger log = LoggerFactory.getLogger(HbaseServiceImpl.class);
//
//
//    static {
////        File workaround = new File(".");
////        System.getProperties().put("hadoop.home.dir",
////                workaround.getAbsolutePath());
////        new File("./bin").mkdirs();
////        try {
////            new File("./bin/winutils.exe").createNewFile();
////        } catch (IOException e) {
////            // TODO Auto-generated catch block
////            e.printStackTrace();
////        }
//
//
//        Configuration configuration = new Configuration();
//        configuration.set("hbase.zookeeper.quorum","172.20.10.11");
//        configuration.set("hbase.zookeeper.property.clientPort","2181");
//        try {
//            conn = ConnectionFactory.createConnection(configuration);
//            admin = conn.getAdmin();
//        } catch (IOException e) {
//            e.printStackTrace();
//            log.error("获取HBase连接失败!");
//            System.out.println("获取HBase连接失败!");
//        }
//    }
//
//
//
//    /**
//     * 根据表名获取table
//     */
//    private static Table getTable(String tableName) throws IOException {
//        return conn.getTable(TableName.valueOf(tableName));
//    }
//
//
//
//    /**
//     * 遍历查询指定表中的所有数据
//     */
//    public static Map<String, Map<String, String>> getResultScanner(String tableName) {
//        Scan scan = new Scan();
//        return queryData(tableName, scan);
//    }
//
//
//    /**
//     * 取出表中所有的key
//     * @param tableName
//     * @return
//     */
//    public static List<String> getAllKey(String tableName) throws IOException {
//        List<String> keys = new ArrayList<>();
//        try {
//            Scan scan = new Scan();
//            Table table = conn.getTable(TableName.valueOf(tableName));
//            ResultScanner scanner = table.getScanner(scan);
//            for (Result r : scanner) {
//                keys.add(new String(r.getRow()));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return keys;
//    }
//
//
//
//    /**
//     * 通过表名及过滤条件查询数据
//     */
//    private static Map<String, Map<String, String>> queryData(String tableName, Scan scan) {
//        // <rowKey,对应的行数据>
//        Map<String, Map<String, String>> result = new HashMap<>();
//        ResultScanner rs = null;
//        //获取表
//        Table table = null;
//        try {
//            table = getTable(tableName);
//            rs = table.getScanner(scan);
//            for (Result r : rs) {
//                // 每一行数据
//                Map<String, String> columnMap = new HashMap<>();
//                String rowKey = null;
//                // 行键，列族和列限定符一起确定一个单元（Cell）
//                for (Cell cell : r.listCells()) {
//                    if (rowKey == null) {
//                        rowKey = Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
//                    }
//                    columnMap.put(
//                            //列限定符
//                            Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength()),
//                            //列族
//                            Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
//                }
//                if (rowKey != null) {
//                    result.put(rowKey, columnMap);
//                }
//            }
//        } catch (IOException e) {
//            log.error(MessageFormat.format("遍历查询指定表中的所有数据失败,tableName:{0}", tableName), e);
//        } finally {
//            close(null, rs, table);
//        }
//        return result;
//    }
//
//
//
//
//    //查询所有表名
//    public static List<String> selectAllTables() {
//        // org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
//
//        List<String> result = new ArrayList<>();
//        try {
//            TableName[] tableNames = admin.listTableNames();
//            for (TableName tableName : tableNames) {
//                result.add(tableName.getNameAsString());
//            }
//        } catch (IOException e) {
//            log.error("获取所有表的表名失败", e);
//        } finally {
//            close(admin, null, null);
//        }
//        return result;
//    }
//
//
//    //建表
//    public static void createTable(String tableName, String... columnFamilies) throws IOException {
//        TableName tablename = TableName.valueOf(tableName);
//        if(admin.tableExists(tablename)){
//            System.out.println("Table Exists");
//        }else{
//            System.out.println("Start create table");
//            HTableDescriptor tableDescriptor = new HTableDescriptor(tablename);
//            for (String columnFamliy : columnFamilies) {
//                HTableDescriptor column = tableDescriptor.addFamily(new HColumnDescriptor(columnFamliy));
//            }
//            admin.createTable(tableDescriptor);
//            System.out.println("Create Table success");
//        }
//    }
//
//
//    /**
//     * 为表添加或者更新数据
//     */
//    public static void putData(String tableName, String rowKey, String familyName, String[] columns, String[] values) {
//        Table table = null;
//        try {
//            table = getTable(tableName);
//            putData(table, rowKey, tableName, familyName, columns, values);
//        } catch (Exception e) {
//            log.error(MessageFormat.format("为表添加 or 更新数据失败,tableName:{0},rowKey:{1},familyName:{2}", tableName, rowKey, familyName), e);
//        } finally {
//            close(null, null, table);
//        }
//    }
//
//
//    private static void putData(Table table, String rowKey, String tableName, String familyName, String[] columns, String[] values) {
//        try {
//            //设置rowkey
//            Put put = new Put(Bytes.toBytes(rowKey));
//            if (columns != null && values != null && columns.length == values.length) {
//                for (int i = 0; i < columns.length; i++) {
//                    if (columns[i] != null && values[i] != null) {
//                        put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columns[i]), Bytes.toBytes(values[i]));
//                    } else {
//                        throw new NullPointerException(MessageFormat.format(
//                                "列名和列数据都不能为空,column:{0},value:{1}", columns[i], values[i]));
//                    }
//                }
//            }
//            table.put(put);
//            log.debug("putData add or update data Success,rowKey:" + rowKey);
//            table.close();
//        } catch (Exception e) {
//            log.error(MessageFormat.format(
//                    "为表添加 or 更新数据失败,tableName:{0},rowKey:{1},familyName:{2}",
//                    tableName, rowKey, familyName), e);
//        }
//    }
//
//    /**
//     * 关闭流
//     */
//    private static void close(Admin admin, ResultScanner rs, Table table) {
//        if (admin != null) {
//            try {
//                admin.close();
//            } catch (IOException e) {
//                log.error("关闭Admin失败", e);
//            }
//            if (rs != null) {
//                rs.close();
//            }
//            if (table != null) {
//                rs.close();
//            }
//            if (table != null) {
//                try {
//                    table.close();
//                } catch (IOException e) {
//                    log.error("关闭Table失败", e);
//                }
//            }
//        }
//    }
//
//
//}
