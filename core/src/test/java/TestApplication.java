import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tv.huan.master.Application;
import tv.huan.master.entity.Resource;
import tv.huan.master.entity.User;
import tv.huan.master.service.ResourceService;
import tv.huan.master.service.UserService;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestApplication {

    @Autowired
    private UserService userService;
    @Autowired
    private ResourceService resourceService;

    @Test
    public void contextLoads() {
        User user = userService.findById(1L);
        System.out.println(user.getUpdateDate() + "==" + user.getRealName());
        user.setUpdateDate(new Date());
        User save = userService.save(user);
        System.out.println(save.getUpdateDate());

    }


    @Test
    public void test3() {
        Resource resource = new Resource();
//        resource.setName("哈哈哈2");
//        resource.setId(2L);
//        resource.setUrl("/333");
        Resource save = resourceService.save(resource);
        System.out.println("user: " + save.getId());
    }
    @Test
    public void test4() {
        Resource byId = resourceService.findById(1L);
        byId.setUpdateDate(new Date());
        Resource save = resourceService.save(byId);
//        System.out.println("user: " + save.getId());
    }

}
