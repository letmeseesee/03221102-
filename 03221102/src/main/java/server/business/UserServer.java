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
 * @author 阿尔卑斯狗 2019-3-22 用户操作
 */
public class UserServer {
    final static Logger logger = LoggerFactory.getLogger(UserServer.class);

    private SqliteHelper sqliteHelper ;

    private String dbUserName = "demo_user";

    public UserServer(){
        try {
            this.sqliteHelper = new SqliteHelper(Config.dbfile);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 添加用户
     * @param User
     * @return
     */
    public Boolean addUser(User user){
        try {
            //创建主表数据
            String addUserSql = String.format("insert into "+dbUserName+"(user_name,user_password,is_admin,user_address,user_phone) " +
                    "values(%S,%S,%S,%S,%S)",user.getUserName(),user.getUserPassword(),user.getIsAdmin(),user.getUserAddress(),user.getUserPhone());

            Integer effectRows = sqliteHelper.executeUpdate(addUserSql);

            return (effectRows  > 0);
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 删除用户
     * @param User
     * @return
     */
    public Boolean delUser(User user){
        try {
            String delUserSql = String.format("delete from "+dbUserName+" where id =  %s",user.getId());
            Integer effectRows = sqliteHelper.executeUpdate(delUserSql);
            return (effectRows  > 0);
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 获取用户信息
     * @param user 用户
     * @return User
     */
    public User getUser(User user){
        try {
            String selelctUserSql = String.format("selelct *  from "+dbUserName+" where id =  %s",user.getId());
            User userR = sqliteHelper.executeQuery(selelctUserSql,new RowMapper<User>(){
                @Override
                public User mapRow(ResultSet rs, int index)
                        throws SQLException {
                    User userSelected = new User();
                    userSelected.setUserName(rs.getString("user_name"));
                    userSelected.setUserAddress(rs.getString("user_address"));
                    userSelected.setUserPhone(rs.getString("user_phone"));
                    userSelected.setIsAdmin(rs.getInt("is_admin"));
                    return userSelected;
                }
            }).get(0);
            return userR;
        }catch (Exception e){
            return null;
        }
    }

    public Boolean validate(User user){
        User dbUser = this.getUser(user);
        return StringUtils.equals(dbUser.getUserPassword(),user.getUserPassword());
    }
}
