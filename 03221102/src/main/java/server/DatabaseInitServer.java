package server;

import config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.SqliteHelper;

import java.io.File;

/**
 * CREATE TABLE `demo_user` (
 *   `id` int(11) NOT NULL AUTO_INCREMENT,
 *   `user_name` varchar(50) NOT NULL COMMENT '用户名',
 *   `user_password` varchar(50) NOT NULL COMMENT '用户密码',
 *   `user_address` varchar(50) NOT NULL COMMENT '用户地址',
 *   `user_phone` varchar(50) NOT NULL COMMENT '用户电话',
 *   `money` INT(50) NOT NULL default 0  COMMENT '余额',
 *   `user_status` tinyint(4) NOT NULL COMMENT '状态 启用 非启用',
 *   `is_admin` tinyint(4) NOT NULL COMMENT '是否管理员',
 *   PRIMARY KEY (`id`) USING BTREE,
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='用户表';
 *
 * CREATE TABLE `demo_user_account` (
 *   `id` int(11) NOT NULL AUTO_INCREMENT,
 *   `user_id` varchar(50) NOT NULL COMMENT '用户ID',
 *   `money` INT(50) NOT NULL COMMENT '余额',
 *   PRIMARY KEY (`id`) USING BTREE,
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='银行账户';
 *
 *
 * CREATE TABLE `demo_user_account_flow` (
 *   `id` int(11) NOT NULL AUTO_INCREMENT,
 *   `account_id` varchar(50) NOT NULL COMMENT '用户ID',
 *   `type` varchar(50) NOT NULL COMMENT '1 存钱 2 取钱',
 *   `charge` INT(50) NOT NULL COMMENT '1 存钱 2 取钱',
 * 	 `money` INT(50) NOT NULL COMMENT '余额',
 *   PRIMARY KEY (`id`) USING BTREE,
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='银行账户';
 * @author  阿尔卑斯狗 2019-3-22 初始化数据库
 */
public class DatabaseInitServer {
    final static Logger logger = LoggerFactory.getLogger(DatabaseInitServer.class);

    private static String user = "CREATE TABLE `demo_user` (\n" +
            "  `id` int(11) PRIMARY KEY,\n" +
            "  `user_name` varchar(50) NOT NULL,\n" +
            "  `user_password` varchar(50) NOT NULL,\n" +
            "  `user_address` varchar(50) NOT NULL,\n"+
            "  `user_phone` varchar(50) NOT NULL,\n"+
            "  `money` INT(50) NOT NULL default 0,\n"+
            "  `user_status` tinyint(4) NOT NULL default 1,\n" +
            "  `is_admin` tinyint(4) NOT NULL default 0\n" +
            ");\n";

//    private static String account = "CREATE TABLE `demo_user_account` (\n" +
//            "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
//            "  `user_id` varchar(50) NOT NULL COMMENT '用户ID',\n" +
//            "  `money` INT(50) NOT NULL COMMENT '余额',\n" +
//            "  PRIMARY KEY (`id`) USING BTREE,\n" +
//            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='银行账户';";

    private static String flow = "CREATE TABLE `demo_user_flow` (\n" +
            "  `id` int(11) PRIMARY KEY,\n" +
            "  `account_id` varchar(50) NOT NULL,\n" +
            "  `type` varchar(50) NOT NULL,\n" +
            "  `charge` INT(50) NOT NULL,\n" +
            "  `money` INT(50) NOT NULL\n" +
            ");";


    public static Boolean init(){
        logger.info("初始化数据。。。");
        try {
            File dbfile = new File(Config.dbfile);
            if(dbfile.exists()){
                return true;
            }
            SqliteHelper sqliteHelper = new SqliteHelper(Config.dbfile);
            sqliteHelper.executeUpdate(DatabaseInitServer.user);
            sqliteHelper.executeUpdate(DatabaseInitServer.flow);
            logger.info("数据初始化成功");
        }catch (Exception e){
            logger.info("初始化数据失败");
            return false;
        }
        return true;
    }
}
