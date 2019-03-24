package facade.vo.request;

import facade.vo.User;
import lombok.Data;

/**
 * @author 2019-3-24
 */
@Data
public class Request {
    String target;
    User user;
    Integer chargeType;
}
