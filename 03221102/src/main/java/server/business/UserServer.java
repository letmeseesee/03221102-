package server.business;

import config.Config;
import facade.vo.User;
import facade.vo.UserData;
import org.apache.commons.lang3.StringUtils;
import util.dataHelper;

import java.util.Map;

/**
 * @author 阿尔卑斯狗 2019-3-22 用户操作
 */
public class UserServer {
    private dataHelper dataHelper;
    private ChargeServer chargeServer = new ChargeServer();

    public UserServer() {
        try {
            this.dataHelper = new dataHelper(Config.dataFile, Config.dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加用户
     *
     * @param User 用户
     * @return user
     */
    public User addUser(User user) {
        //创建主表数据
        int userId = dataHelper.getNewId();
        System.out.println(userId + "");
        user.setId(userId);
        user.setIsAdmin(0);
        user.setMoney(0);
        user.setCharge(0);
        UserData userData = new UserData();
        userData.setUser(user);
        Config.dataList.userDataList.put(userId, userData);

        //插入一条流水
        chargeServer.saveMoney(user,0);

        //更新数据文件
        dataHelper.updateDataFile();
        return user;
    }

    /**
     * 删除用户
     *
     * @param User
     * @return
     */
    public Boolean delUser(User user) {
        try {
            Config.dataList.userDataList.remove(user.getId());
            //更新数据文件
            dataHelper.updateDataFile();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取用户信息
     *
     * @param user 用户
     * @return User
     */
    public User getUser(User user) {
        try {
            System.out.println(Config.dataList.userDataList.get(user.getId()));
            for (Map.Entry<Integer,UserData> entry :Config.dataList.userDataList.entrySet()){
                if(this.validate(user,entry.getValue().getUser())){
                    return entry.getValue().getUser();
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private Boolean validate(User user,User requiredUser) {
        return StringUtils.equals(requiredUser.getUserName(), user.getUserName()) && StringUtils.equals(requiredUser.getUserPassword(), user.getUserPassword());
    }
}
