package server.business;

import config.Config;
import facade.vo.User;
import org.apache.commons.lang3.StringUtils;
import util.RowMapper;
import util.SqliteHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 阿尔卑斯狗 2019-3-22 存取款
 */
public class ChargeServer {
    private SqliteHelper sqliteHelper ;

    private String dbUserName = "demo_user";
    private String dbUserFlow = "demo_user_flow";
    private UserServer userServer;

    public ChargeServer(){
        try {
            this.sqliteHelper = new SqliteHelper(Config.dbfile);
            this.userServer = new UserServer();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 存款
     * @param user 存款账户
     * @return mixed
     */
    public Boolean saveMoney(User user){
        //添加余额
        return false;
    }

    /**
     * 取款
     * @param user 取款账户
     * @return mixed
     */
    public Boolean getMoney(User user){
        //
        return true;
    }

    /**
     * 添加流水
     * @return int 影响函数
     */
    public Integer addFlow(User user){
        return 0;
    }
}
