package facade.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserData {
    User user;
    List<Flow> flows = new ArrayList<>();
}
