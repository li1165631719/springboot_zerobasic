import org.example.Application;
import org.example.param.RegisterParam;
import org.example.service.TokenService;
import org.example.util.CommonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 李志豪
 * @create 2024/5/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SpringbootTest1 {


    @Autowired
    TokenService tokenService;

    @Test
    public void testSalt(){
        String salt = CommonUtil.generateUUID().substring(0, 6);
        System.out.println(salt);
    }

    @Test
    public void testTokenService(){
        RegisterParam registerParam =new RegisterParam();
        registerParam.setName("user-1");
        registerParam.setPassword("123456");
        tokenService.register(registerParam);
    }
}
