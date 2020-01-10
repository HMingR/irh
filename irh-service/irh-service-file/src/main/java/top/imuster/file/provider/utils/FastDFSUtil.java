package top.imuster.file.provider.utils;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;
import top.imuster.file.provider.file.FastDFSFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @ClassName: FastDFSUtil
 * @Description:
 * @author: lpf
 * @date: 2019/12/23 12:27
 * 实现FastDFS文件管理
 *      文件上传
 *      文件删除
 *      文件下载
 *      文件信息获取
 *      Storage信息获取
 *      Tracker信息获取
 */
public class FastDFSUtil {

    /**
     * @Description: 加载Tracker链接信息
     * @Author: lpf
     * @Date: 2019/12/23 12:29
     **/
    static {
        try {
            //查找classpath下的文件路径
            String fileNameConf = new ClassPathResource("fdfs_client.conf").getPath();
            //加载Tracker链接信息
            ClientGlobal.init(fileNameConf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 文件上传实现
     * @Author: lpf
     * @Date: 2019/12/23 12:35
     * @param fastDFSFile :上传的文件信息封装
     **/
    public static String[] upload(FastDFSFile fastDFSFile) throws Exception {
        //附加参数
        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("author",fastDFSFile.getAuthor());

        //获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();

        //获取StorageClient
        StorageClient storageClient = getStorageClient(trackerServer);

        /**
         * @Description:
         * @Author: lpf
         * @Date: 2019/12/23 12:44
         * 通过StorageClient访问Storage，实现文件上传，并且获取文件上传后的存储信息
         * 函数的参数
         *      1.上传文件的字节数组
         *      2.文件的拓展名
         *      3.附加参数  比如拍摄地点：北京
         * uploads[]
         *      upload[0]:文件上传所存储的Storage的组的名字  group1
         *      upload[1]:文件存储到Storage上的文件的名字   M00/02/44/1.jpg
         **/
        String[] uploads = storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), meta_list);
        return uploads;
    }

    /***
     * @Description: 获取文件信息
     * @Author: lpf
     * @Date: 2019/12/23 16:11
     * @param groupName :文件的组名   group1
     * @param remoteFileName :文件的存储路径名字  M00/02/44/1.jpg
     * @reture:
     **/
    public static FileInfo getFile(String groupName, String remoteFileName) throws Exception{
        //获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();

        //获取StorageClient
        StorageClient storageClient = getStorageClient(trackerServer);

        //获取文件信息
        return storageClient.get_file_info(groupName, remoteFileName);
    }

    /**
     * @Description: 文件下载
     * @Author: lpf
     * @Date: 2019/12/23 17:14
     * @param groupName :文件的组名   group1
     * @param remoteFileName :文件的存储路径名字  M00/02/44/1.jpg
     * @reture: void
     **/
    public static InputStream downloadFile(String groupName, String remoteFileName) throws Exception{
        //获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();

        //获取StorageClient
        StorageClient storageClient = getStorageClient(trackerServer);

        //文件下载
        byte[] buffer = storageClient.download_file(groupName, remoteFileName);
        return new ByteArrayInputStream(buffer);
    }

    /**
     * @Description: 删除文件
     * @Author: lpf
     * @Date: 2019/12/23 17:14
     * @param groupName :文件的组名   group1
     * @param remoteFileName :文件的存储路径名字  M00/02/44/1.jpg
     * @reture: void
     **/
    public static void deleteFile(String groupName, String remoteFileName) throws Exception{
        //获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();

        //获取StorageClient
        StorageClient storageClient = getStorageClient(trackerServer);

        //删除文件
        storageClient.delete_file(groupName, remoteFileName);
    }
    /**
     * @Description: 获取storage信息
     * @Author: lpf
     * @Date: 2019/12/23 17:37
     * @reture: 返回storage信息
     **/
    public static StorageServer getStorage() throws Exception{
        //创建一个TrackerClient对象，通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer的链接对象
        TrackerServer trackerServer = trackerClient.getConnection();

        //获取storage信息
        return trackerClient.getStoreStorage(trackerServer);
    }


    /**
     * @Description: 获取storage信息的端口和IP信息
     * @Author: lpf
     * @Date: 2019/12/23 17:37
     * @reture: 返回storage的IP和端口信息
     **/
    public static ServerInfo[] getServerInfo (String groupName, String remoteFileName) throws Exception{
        //创建一个TrackerClient对象，通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer的链接对象
        TrackerServer trackerServer = trackerClient.getConnection();

        //获取Storage的IP和端口信息
        return trackerClient.getFetchStorages(trackerServer, groupName, groupName);
    }

    /**
     * @Description: 获取Tracker的信息
     * @Author: lpf
     * @Date: 2019/12/23 18:17
     * @param
     * @reture: void
     **/
    public static String getTrackerInfo() throws Exception{
        //获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();

        //Tracker的IP，Http端口
        String ip = trackerServer.getInetSocketAddress().getHostString();
        int httpPort = ClientGlobal.getG_tracker_http_port();
        String url = "http://" + ip + ":" + httpPort;

        return url;
    }

    /**
     * @Description: 获取TrackerServer（防止冗余）
     * @Author: lpf
     * @Date: 2019/12/23 18:29
     * @param
     * @reture: org.csource.fastdfs.TrackerServer
     **/
    public static TrackerServer getTrackerServer() throws Exception{
        //创建一个TrackerClient对象，通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer的链接对象
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerServer;
    }

    /**
     * @Description: 获取StorageClient
     * @Author: lpf
     * @Date: 2019/12/23 18:29
     * @param trackerServer
     * @reture: org.csource.fastdfs.TrackerServer
     **/
    public static StorageClient getStorageClient(TrackerServer trackerServer) throws Exception {
        StorageClient storageClient = new StorageClient(trackerServer,null);
        return storageClient;
    }

    //测试
    public static void main(String[] args) throws Exception{
        /*FileInfo fileInfo = getFile("group1", "M00/00/00/rBgYGV4AkH-AafqxAAGGegCgxtc834.jpg");
        System.out.println(fileInfo.getSourceIpAddr());
        System.out.println(fileInfo.getFileSize());*/

       /* //文件下载
        InputStream is = downloadFile("group1", "M00/00/00/rBgYGV4Ag3aACWPGAAIsTF3RcOs943.jpg");

        //将文件写入到本地磁盘
        FileOutputStream os = new FileOutputStream("D:\\tool\\images\\test_download\\1.jpg");

        //定义一个缓冲区
        byte[] buffer = new byte[1024];
        while (is.read(buffer) != -1) {
            os.write(buffer);
        }
        os.flush();  //刷新缓冲区
        os.close();
        is.close();*/

        //测试文件删除
        //deleteFile("group1", "M00/00/00/rBgYGV4AijSAD9iqAAIVMmsTSos650.jpg");

        //获取storage信息
        /*StorageServer storageServer = getStorage();
        System.out.println(storageServer.getStorePathIndex());
        System.out.println(storageServer.getInetSocketAddress().getHostString());   //IP信息*/

        //获取Storage组的IP和端口信息
        /*ServerInfo[] groups = getServerInfo("group1", "M00/00/00/rBgYGV4AkH-AafqxAAGGegCgxtc834.jpg");
        System.out.println(groups);
        for (ServerInfo group : groups) {
            System.out.println(group.getIpAddr());
            System.out.println(group.getPort());
        }*/

        //获取tracker的信息
        //System.out.println(getTrackerInfo());
    }
}
