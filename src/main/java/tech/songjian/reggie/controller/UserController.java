/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.controller
 * @className tech.songjian.reggie.controller.UserController
 */
package tech.songjian.reggie.controller;

import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.songjian.reggie.common.R;
import tech.songjian.reggie.entity.User;
import tech.songjian.reggie.service.UserService;
import tech.songjian.reggie.utils.SMSUtils;
import tech.songjian.reggie.utils.ValidateCodeUtils;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * UserController
 * @description
 * @author SongJian
 * @date 2022/12/7 21:36
 * @version
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @title sendMsg
     * @author SongJian
     * @param: user
     * @param: session
     * @updateTime 2022/12/7 22:12
     * @return: tech.songjian.reggie.common.R<java.lang.String>
     * @throws
     * @description 移动端发送短信验证码
     */

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        // 获取手机号
        String phone = user.getPhone();

        if(!StringUtils.isEmpty(phone)) {
            // 生成随机四位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("验证码为：{}", code);
            // 调用阿里云短信api发送短信
            // SMSUtils.sendMessage();

            // 将生成的验证码缓存至redis，并设置五分钟有效期
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);

            return R.success("手机验证码短信发送成功");
        }
        return R.error("短信发送失败");
    }

    /**
     * @title login
     * @author SongJian
     * @updateTime 2022/12/7 22:12
     * @return: tech.songjian.reggie.common.R<java.lang.String>
     * @throws
     * @description 移动端用户登入
     */

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {
        log.info(map.toString());

        // 获取手机号
        String phone = map.get("phone").toString();
        // 获取验证码
        String code = map.get("code").toString();
        // 从session中获取保存的验证码
        //Object codeInSession = session.getAttribute(phone);

        // 从redis中获取缓存的验证码
        Object codeInSession = redisTemplate.opsForValue().get(phone);

        // 验证码比对
        if (codeInSession != null && codeInSession.equals(code)) {
            // 如果比对成功，说明登入成功


            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getPhone, phone);
            User user = userService.getOne(lambdaQueryWrapper);

            // 判断当前手机号对应的用户是否是新用户，如果是新用户则自动完成注册
            if (user == null) {
                // 新用户
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());
            //如果用户登入成功，删除缓存的验证码
            redisTemplate.delete(phone);
            return R.success(user);
        }
        return R.error("登入失败");
    }
}
 
