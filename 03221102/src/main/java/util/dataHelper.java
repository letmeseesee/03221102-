package util;

import facade.vo.data.DataList;

import java.io.*;
import java.util.Random;

import static com.alibaba.fastjson.JSON.toJSONString;

/**
 * sqlite帮助类，直接创建该类示例，并调用相应的借口即可对sqlite数据库进行操作
 * @author 阿尔卑斯狗 2019-3-22
 */
public class dataHelper {
    private String dbFilePath;
    private DataList dataList;
    /**
     * 构造函数
     * @param dataFilePath sqlite db 文件路径
     */
    public dataHelper(String dataFilePath, DataList dataList){
        this.dbFilePath = dataFilePath;
        this.dataList = dataList;
    }

    /**
     * 获取新的随机主键
     */
    public Integer getNewId(){
        Integer lastInsertId = 0;
        Random ra =new Random();
        lastInsertId = ra.nextInt(10000);
        return lastInsertId;
    }

    synchronized public void updateDataFile(){
        System.out.println(dataList);
        //更新源文件
        String dataString = toJSONString(dataList);
        System.out.println(dataString);
        Thread fileUpdateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(dbFilePath);
                try {
                    FileOutputStream out = new FileOutputStream(file, false);
                    out.write(dataString.getBytes());
                    out.close();
                }catch (IOException e){
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        });
        fileUpdateThread.start();
    }


}
