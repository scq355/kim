package com.kim.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HdfsClientApi {

    public void mkDirs() throws IOException, URISyntaxException, InterruptedException {
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://hadoop101:8020"), configuration, "root");
        fileSystem.mkdirs(new Path("/api"));
        fileSystem.close();
    }

    public void copyFromLocalFile() throws IOException, URISyntaxException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("dfs.replication", "2");
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://hadoop101:8020"), configuration, "root");
        fileSystem.copyFromLocalFile(new Path(""), new Path("/api"));
        fileSystem.close();
    }

    public void copyToLocalFile() throws IOException, URISyntaxException, InterruptedException {
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://hadoop101:8020"), configuration, "root");
        fileSystem.copyToLocalFile(new Path("/api/data.txt"), new Path(""));
        fileSystem.close();
    }

    public void delete() throws IOException, URISyntaxException, InterruptedException {
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://hadoop101:8020"), configuration, "root");
        fileSystem.delete(new Path("/api/data.txt"), true);
        fileSystem.close();
    }

    public void rename() throws IOException, URISyntaxException, InterruptedException {
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://hadoop101:8020"), configuration, "root");
        fileSystem.rename(new Path("/api/data.txt"), new Path("/api/data2.txt"));
        fileSystem.close();
    }

    public void listFiles() throws IOException, URISyntaxException, InterruptedException {
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://hadoop101:8020"), configuration, "root");
        RemoteIterator<LocatedFileStatus> listFiles = fileSystem.listFiles(new Path("/api"), true);
        while (listFiles.hasNext()) {
            LocatedFileStatus fileStatus = listFiles.next();
            System.out.println("文件名称：" + fileStatus.getPath().getName());
            System.out.println("文件权限：" + fileStatus.getPermission());
            System.out.println("文件长度：" + fileStatus.getLen());
            System.out.println("文件块信息：" + fileStatus.getBlockLocations());
            System.out.println("文件块大小：" + fileStatus.getBlockSize());
            System.out.println("文件副本数量：" + fileStatus.getReplication());
            System.out.println("文件拥有者：" + fileStatus.getOwner());
            System.out.println("文件组：" + fileStatus.getGroup());
            System.out.println("文件最后修改时间：" + fileStatus.getModificationTime());
            System.out.println("文件访问时间：" + fileStatus.getAccessTime());
            System.out.println("文件是否为目录：" + fileStatus.isDirectory());
            System.out.println("文件是否为文件：" + fileStatus.isFile());
            System.out.println("文件是否为符号链接：" + fileStatus.isSymlink());

            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            for (BlockLocation blockLocation : blockLocations) {
                System.out.println("块起始位置：" + blockLocation.getOffset());
                System.out.println("块长度：" + blockLocation.getLength());
                System.out.println("块所在节点：" + blockLocation.getHosts());
            }

            System.out.println("==================================================");
        }

        fileSystem.close();
    }

    public void listStatus() throws IOException, URISyntaxException, InterruptedException {
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://hadoop101:8020"), configuration, "root");
        FileStatus[] listStatus = fileSystem.listStatus(new Path("/api"));
        for (FileStatus fileStatus : listStatus) {
            System.out.println("文件名称：" + fileStatus.getPath().getName());
            System.out.println("文件权限：" + fileStatus.getPermission());
            System.out.println("文件长度：" + fileStatus.getLen());
            System.out.println("文件块大小：" + fileStatus.getBlockSize());
            System.out.println("文件副本数量：" + fileStatus.getReplication());
            System.out.println("文件拥有者：" + fileStatus.getOwner());
            System.out.println("文件组：" + fileStatus.getGroup());
            System.out.println("文件最后修改时间：" + fileStatus.getModificationTime());
            System.out.println("文件访问时间：" + fileStatus.getAccessTime());
            System.out.println("文件是否为目录：" + fileStatus.isDirectory());
            System.out.println("文件是否为文件：" + fileStatus.isFile());
            System.out.println("文件是否为符号链接：" + fileStatus.isSymlink());
            System.out.println("==================================================");
        }
    }
}
