package server.business;

import config.Config;
import facade.vo.Flow;
import facade.vo.User;
import util.dataHelper;

import java.util.List;

/**
 * @author 阿尔卑斯狗 2019-3-22 存取款
 */
public class ChargeServer {

    private dataHelper dataHelper;

    public ChargeServer() {
        try {
            this.dataHelper = new dataHelper(Config.dataFile, Config.dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 存款
     *
     * @param user 存款账户
     * @return mixed
     */
    public Boolean saveMoney(User user, Integer type) {

        //添加余额
        User userR = Config.dataList.userDataList.get(user.getId()).getUser();
        userR.setMoney(user.getMoney());

        //添加流水数据
        Integer flowId = dataHelper.getNewId();
        Flow flow = new Flow();
        flow.setId(flowId);
        flow.setMoney(user.getMoney());
        flow.setCharge(user.getCharge());
        flow.setType(type);
        flow.setAccountId(user.getId());
        Config.dataList.userDataList.get(user.getId()).getFlows().add(flow);

        //更新数据文件
        dataHelper.updateDataFile();
        System.out.println(Config.dataList.userDataList);
        return true;
    }

    /**
     * 流水
     *
     * @param user 用户
     * @return List
     */
    public List<Flow> getFlow(User user) {
        List<Flow> flowList;
        try {
            flowList =  Config.dataList.userDataList.get(user.getId()).getFlows();
            return flowList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
