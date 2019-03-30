package server;

import config.Config;
import facade.vo.data.DataList;

import java.io.File;
import java.io.FileInputStream;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * @author  阿尔卑斯狗 2019-3-22 初始化数据
 */
public class DatabaseInitServer {
    public static Boolean init(){
        System.out.println("初始化数据。。。");
        try {
            File dataFile = new File(Config.dataFile);
            if(dataFile.exists()){
                //將数据文件导入内存中
                FileInputStream fis=new FileInputStream(dataFile);
                byte[] buf = new byte[1024];
                StringBuffer stringBuffer = new StringBuffer();
                while((fis.read(buf))!=-1){
                    stringBuffer.append(new String(buf));
                    buf=new byte[1024];
                }
                String dataJsonString =  stringBuffer.toString().trim();
                //解析json文件
                Config.dataList  = parseObject(dataJsonString, DataList.class);
            }else {
                //生成文件
                if(!dataFile.createNewFile()){
                    System.out.println("初始化数据失败");
                    return false;
                }
                Config.dataList = new DataList();
            }
            System.out.println("数据初始化成功");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("初始化数据失败");
            return false;
        }
        return true;
    }
}
