package com.springboot.controller;

import com.springboot.common.business.CommonLogParams;
import com.springboot.entity.vo.ResponseData;
import com.springboot.common.shiro.SessionUtil;
import com.springboot.common.util.BrowserUtils;
import com.springboot.common.util.ValidCodeUtil;
import com.springboot.common.util.GraphicHelper;
import com.springboot.common.util.IpUtil;
import com.springboot.common.util.LoginPersonUtil;
import com.springboot.common.util.OSUtil;
import com.springboot.common.util.Screen;
import com.springboot.common.util.ThreadUtil;
import com.springboot.dao.business.IUserDao;
import com.springboot.entity.business.CmsLoginHistoryEO;
import com.springboot.entity.business.HbUserEO;
import com.springboot.entity.mybatis.OrganEO;
import com.springboot.entity.mybatis.UserEO;
import com.springboot.service.hibernate.ICmsLoginHistoryService;
import com.springboot.service.system.IOrganService;
import com.springboot.service.system.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/login")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IUserService userService;

    @Autowired
    private ICmsLoginHistoryService cmsLoginHistoryService;

    @RequestMapping("/loginIn")
    public String toLogin() {
        logger.info("跳转登陆页面...");
        return "login";
    }

    @RequestMapping("/getCode")
    public void getValidCode(HttpServletRequest req, HttpServletResponse resp) {
        // 调用工具类生成的验证码和验证码图片
        Map<String, Object> codeMap = ValidCodeUtil.generateCodeAndPic();

        // 将四位数字的验证码保存到Session中。
        HttpSession session = req.getSession();
        session.setAttribute("code", codeMap.get("code").toString());

        // 禁止图像缓存。
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", -1);

        resp.setContentType("image/jpeg");

        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos;
        try {
            sos = resp.getOutputStream();
            ImageIO.write((RenderedImage) codeMap.get("codePic"), "jpeg", sos);
            sos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/getGhCode")
    public void getGhCode(HttpServletRequest request, HttpServletResponse response) {
        // 获得 当前请求 对应的 会话对象
        HttpSession session = request.getSession();

        // 从请求中获得 URI ( 统一资源标识符 )
        String uri = request.getRequestURI();
        final int width = 120; // 图片宽度
        final int height = 36; // 图片高度
        final String imgType = "jpeg"; // 指定图片格式 (不是指MIME类型)
        OutputStream output = null; // 获得可以向客户端返回图片的输出流
        try {
            output = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // (字节流)
        // 创建验证码图片并返回图片上的字符串
        String code = GraphicHelper.create(width, height, imgType, output);
        logger.info("验证码内容: {}", code);

        // 建立 uri 和 相应的 验证码 的关联 ( 存储到当前会话对象的属性中 )
        session.setAttribute(uri, code);
    }

    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    @ResponseBody
    @CommonLogParams(value = LoginController.class, type = "login", method = "userLogin", desc = "用户登陆")
    public Object userLogin(HttpServletRequest request, String username, String password, String validCode) {
        logger.info(username + " " + password + " " + validCode);
        Object code = request.getSession().getAttribute("/login/getGhCode");
        if (code == null || !validCode.equalsIgnoreCase(code.toString())) {
            return ResponseData.fail("验证码不正确", "登陆失败");
        }
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        UserEO userEO = null;
        // 执行认证登陆
        CmsLoginHistoryEO eo = new CmsLoginHistoryEO();
        String curIp = IpUtil.getIpAddr(request);
        eo.setLoginIp(curIp);
        eo.setColumnId(0L);
        eo.setType(CmsLoginHistoryEO.Type.PC.getValue());
        eo.setSiteId(LoginPersonUtil.getSiteId());
        eo.setOs(OSUtil.get().get("os.name").toString());
        eo.setResolutionRatio(Screen.getScreen());
        eo.setBrowser("IE:" + BrowserUtils.getIEVersion(request) + "-" + BrowserUtils.getBrowserType(request));
        try {
            subject.login(token);
            eo.setLoginStatus(CmsLoginHistoryEO.LoginStatus.Success.name());
            eo.setDescription("登录成功");
        } catch (Exception e) {
            eo.setLoginStatus(CmsLoginHistoryEO.LoginStatus.Failure.name());
            eo.setDescription(e.getMessage());
            return ResponseData.fail(e.getMessage(), "登陆失败");
        } finally {
            if (CmsLoginHistoryEO.LoginStatus.Success.name().equals(eo.getLoginStatus())) {
                // 用户认证成功,获取当前用户
                userEO = SessionUtil.getCurrentUser();
                initThreadLocal(userEO, request);
                eo.setUid(ThreadUtil.getString(ThreadUtil.LocalParamsKey.UserName));
                eo.setCreateUser(ThreadUtil.getString(ThreadUtil.LocalParamsKey.PersonName));
                eo.setOrganId(ThreadUtil.getLong(ThreadUtil.LocalParamsKey.OrganId));
                eo.setOrganName(ThreadUtil.getString(ThreadUtil.LocalParamsKey.OrganName));
            } else {
                // 用户认证失败,删除当前用户
                SessionUtil.removeCurrentUser();
                eo.setUid(username);
                Map map = new HashMap();
                map.put("userId", username);
                HbUserEO person = userDao.getEntity(HbUserEO.class, map);
                eo.setCreateUser(person.getUserName());
                OrganEO organEO = organService.getOrganById(person.getOrganId());
                eo.setOrganId(person.getOrganId());
                eo.setOrganName(organEO.getOrganName());
            }
            cmsLoginHistoryService.saveEntity(eo);
        }
        //根据权限，指定返回数据
        ResponseData role = userService.selectByUserAccount(username);
        if (role == null) {
            // 用户认证失败,删除当前用户
            SessionUtil.removeCurrentUser();
            return ResponseData.fail("当前用户没有配置角色", "登陆失败");
        }
        // 在此添加权限信息到用户
        if ("sysadmin".equals(((UserEO) role.getData()).getUserName())) {
            return ResponseData.success("欢迎来到管理员页面");
        }
        return ResponseData.success(userEO, "普通用户登陆成功");
    }

    @Autowired
    private IUserDao userDao;
    @Autowired
    private IOrganService organService;

    /**
     * 初始化线程线程本地变量
     *
     * @param eo
     */
    private void initThreadLocal(UserEO eo, HttpServletRequest request) {
        HbUserEO userEO = userDao.getEntity(HbUserEO.class, eo.getId());
        OrganEO organEO = organService.getOrganById(userEO.getOrganId());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ThreadUtil.LocalParamsKey.RoleId.toString(), userEO.getRoleId());
        map.put(ThreadUtil.LocalParamsKey.UserId.toString(), eo.getId());
        map.put(ThreadUtil.LocalParamsKey.UserName.toString(), userEO.getUserId());
        map.put(ThreadUtil.LocalParamsKey.Uid.toString(), UUID.randomUUID());
        map.put(ThreadUtil.LocalParamsKey.PersonName.toString(), userEO.getUserName());
        map.put(ThreadUtil.LocalParamsKey.OrganId.toString(), userEO.getOrganId());
        map.put(ThreadUtil.LocalParamsKey.OrganName.toString(), organEO.getOrganName());
        map.put(ThreadUtil.LocalParamsKey.Callback.toString(), "callback");
        map.put(ThreadUtil.LocalParamsKey.DataFlag.toString(), true);
        map.put(ThreadUtil.LocalParamsKey.IP.toString(), IpUtil.getIpAddr(request));
        SessionUtil.setAttribute("threadLocal", map);
        ThreadUtil.set(map);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    @CommonLogParams(value = LoginController.class, type = "logout", method = "logout", desc = "用户登出")
    public ResponseData logout() {
        Subject subject = SecurityUtils.getSubject();
        //注销
        subject.logout();
        return ResponseData.success("成功注销！");
    }
}
