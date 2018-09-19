package megvii.testfacepass.beans;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

/**
 * Created by Administrator on 2018/8/3.
 */
@Entity
public class BenDiInFoBean {

    @Id(assignable = true)
    long id;
    @Index
    String name;


}
