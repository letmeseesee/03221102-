package server.business;

import config.Config;
import facade.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RowMapper;
import util.SqliteHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 阿尔卑斯狗 2019-3-22 存取款
 */
public class ChargeServer {
    final static Logger logger = LoggerFactory.getLogger(ChargeServer.class);

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
    public Boolean saveMoney(User user,Integer type){
        //校验用户
        if(userServer.validate(user)){
            logger.warn("用户{}校验失败",user.getUserName());
            return false;
        }

        String msg ;
        if(type == 1){
            msg = "存款";
        }else {
            msg = "取款";
        }
        //添加余额
        String UpdateUserSql = String.format("Update "+dbUserName+" set money = %S where id = %d",user.getMoney(),user.getId());
        try {
            Integer effectRows = sqliteHelper.executeUpdate(UpdateUserSql);
            if(effectRows>0){
                //添加流水数据
                String insertFlowSql = String.format("Insert into " + dbUserFlow
                                +"(account_id,type,charge,money) values(%d,%d,%d,%d)",
                        user.getId(),type,user.getCharge(),user.getMoney()
                        );
                effectRows = sqliteHelper.executeUpdate(insertFlowSql);
                if(effectRows>0){
                    logger.warn("用户{}{}成功",user.getUserName(),msg);
                    return true;
                }else {
                    logger.warn("用户{}{}失败",user.getUserName(),msg);
                    return false;
                }
            }else {
                logger.warn("用户{}{}失败",user.getUserName(),msg);
                return false;
            }
        }catch (Exception e){
            logger.warn("用户{}{}失败",user.getUserName(),msg);
            return false;
        }
    }
}
