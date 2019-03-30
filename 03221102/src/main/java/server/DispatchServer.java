package server;

import com.alibaba.fastjson.JSON;
import facade.vo.Flow;
import facade.vo.Reponse.Reponse;
import facade.vo.User;
import facade.vo.request.Request;
import server.business.ChargeServer;
import server.business.UserServer;

import java.util.List;

/**
 * @author 2019-3-24 分发请求
 */
public class DispatchServer {
    private UserServer userServer = new UserServer();
    private ChargeServer chargeServer = new ChargeServer();
    public String handle(Request request){
        String target = request.getTarget();
        User requestUser = request.getUser();
        Integer chargeType = request.getChargeType();
        Reponse reponseObject = new Reponse();
        String reponseString = null;
        Boolean result;
        switch (target){
            case "addUser":
                User lastInsertUser = userServer.addUser(requestUser);
                if(lastInsertUser !=null){
                    reponseObject.setCode(0);
                    reponseObject.setUser(lastInsertUser);
                }else {
                    reponseObject.setCode(1);
                }
                break;
            case "delUser":
                result = userServer.delUser(requestUser);
                if(result){
                    reponseObject.setCode(0);
                }else {
                    reponseObject.setCode(1);
                }
                break;
            case "getUser":
                User user = userServer.getUser(requestUser);
                reponseObject.setUser(user);
                if(user != null){
                    reponseObject.setCode(0);
                }else {
                    reponseObject.setCode(1);
                }
                break;
            case "saveMoney":
                result = chargeServer.saveMoney(requestUser,chargeType);
                if(result){
                    reponseObject.setCode(0);
                }else {
                    reponseObject.setCode(1);
                }
                break;
            case "getFlow":
                List<Flow> flowList = chargeServer.getFlow(requestUser);
                if(flowList != null){
                    reponseObject.setCode(0);
                    reponseObject.setUserFlows(flowList);
                }else {
                    reponseObject.setCode(1);
                }
                break;
            default:
                reponseObject.setCode(1);
                reponseObject.setMsg("无对应功能");
        }
        reponseString = JSON.toJSONString(reponseObject);
        return reponseString;
    }
}
