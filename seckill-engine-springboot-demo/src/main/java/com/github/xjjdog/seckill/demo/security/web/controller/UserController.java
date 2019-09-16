package com.github.xjjdog.seckill.demo.security.web.controller;

import com.github.xjjdog.seckill.demo.common.model.ResponseData;
import com.github.xjjdog.seckill.demo.common.util.ResponseUtil;
import com.github.xjjdog.seckill.demo.security.annotation.CurrentUser;
import com.github.xjjdog.seckill.demo.security.model.SysUser;
import com.github.xjjdog.seckill.demo.security.service.SysUserServiceI;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/sys/user"})
public class UserController {

    @Autowired
    private SysUserServiceI userService;

    @GetMapping({"/index"})
    public String indexPage() {
        return "/sys/user/index";
    }

    @GetMapping("/add")
    public String userAdd() {

        return "/sys/user/user_add";
    }

//    @GetMapping("/update/{userId}")
//    public String userUpdate(@PathVariable Integer userId, Model model) {
//
//        SysUserDO user = userService.getById(userId);
//        SysDeptDO dept = deptService.getById(user.getDeptid());
//        user.clearPasswd();
//        model.addAttribute("user",user);
//        model.addAttribute("roleName", StringUtils.isNotBlank(user.getRoleid())?roleService.getRoleNames(user.getRoleid()):"");
//        model.addAttribute("deptName", dept==null?"顶级":dept.getSimplename());
//        return "/sys/user/user_edit";
//    }

//    @GetMapping("/role_assign/{userId}")
//    public String roleAssign(@PathVariable Integer userId, Model model) {
//        SysUserDO user = userService.getById(userId);
//        model.addAttribute("userId", userId);
//        model.addAttribute("userAccount", user.getAccount());
//        return "/sys/user/user_roleassign";
//    }

//    /**
//     * 添加管理员
//     */
//    @PostMapping("/add")
//    @ResponseBody
//    public ResponseData add(@Valid SysUserDO user) {
//
//        // 判断账号是否重复
//        SysUser theUser = userService.getByAccount(user.getAccount());
//        if (theUser != null) {
//            return ResponseUtil.error("500000","帐号已存在");
//        }
//
//        user.setSalt(BCrypt.gensalt(5));
//        user.setPassword(BCrypt.hashpw(user.getPassword(), user.getSalt()));
//        user.setStatus(1);
//        userService.save(user);
//        return ResponseUtil.success();
//    }

//    @PostMapping("/update")
//    @ResponseBody
//    public ResponseData edit(@Valid SysUserDO user) {
//
//        SysUserDO oldUser = userService.getById(user.getId());
//        oldUser.editUser(user);
//        userService.updateById(oldUser);
//        return ResponseUtil.success();
//    }

//    @PostMapping("/delete")
//    @ResponseBody
//    public ResponseData delete(@RequestParam Integer userId) {
//        SysUserDO user = userService.getById(userId);
//        user.setStatus(3);
//        userService.updateById(user);
//        return ResponseUtil.success();
//    }


//    @PostMapping("/list")
//    @ResponseBody
//    public ResponseData list(@ModelAttribute SysUserSearch userSearch) {
//
//        return ResponseUtil.success(userService.getUsers(userSearch));
//    }

//    @PostMapping("/reset")
//    @ResponseBody
//    public ResponseData reset(@RequestParam Integer userId) {
//        SysUserDO user = userService.getById(userId);
//        user.setSalt(BCrypt.gensalt(5));
//        user.setPassword(BCrypt.hashpw("111111", user.getSalt()));
//        userService.updateById(user);
//        return ResponseUtil.success();
//    }

//    /**
//     * 冻结用户
//     */
//    @BussinessLog("冻结用户")
//    @PostMapping("/freeze")
//    @ResponseBody
//    public ResponseData freeze(@RequestParam Integer userId, @CurrentUser SysUser currentUser) {
//                  SysUserDO user = userService.getById(userId);
//        user.setStatus(2);
//        userService.updateById(user);
//        return ResponseUtil.success();
//    }

//    /**
//     * 解除冻结用户
//     */
//    @PostMapping("/unfreeze")
//    @ResponseBody
//    public ResponseData unfreeze(@RequestParam Integer userId) {
//        SysUserDO user = userService.getById(userId);
//        user.setStatus(1);
//        userService.updateById(user);
//        return ResponseUtil.success();
//    }

//    @PostMapping("/setRole")
//    @ResponseBody
//    public ResponseData setRole(@RequestParam("userId") Integer userId, @RequestParam("roleIds") String roleIds) {
//
//        SysUserDO user = userService.getById(userId);
//        user.setRoleid(roleIds);
//        userService.updateById(user);
//        return ResponseUtil.success();
//    }

//    @PostMapping("/email/{email}")
//    @ResponseBody
//    public ResponseData findUser(@PathVariable String email) {
//
//        return ResponseUtil.success(userService.getUserByEmail(email));
//    }


    @ApiOperation(value = "获取当前登录用户信息", response = ResponseData.class)
    @PostMapping("/info")
    @ResponseBody
    public ResponseData currentUserInfo(@ApiParam(hidden = true) @CurrentUser SysUser user) {
        user.setSalt(null);
        user.setPasswd(null);
        return ResponseUtil.success(user);
    }

//    @ApiOperation(value = "修改密码", response = ResponseData.class)
//    @PostMapping("/changePwd")
//    @ResponseBody
//    public ResponseData changePwd(@RequestParam("userId") Integer userId,
//                                  @RequestParam("passwd") String passwd,
//                                  @ApiParam(hidden = true) @CurrentUser SysUser sysUser) {
//        if(sysUser.getId().equals(userId)){
//            SysUserDO user = userService.getById(userId);
//            user.setSalt(BCrypt.gensalt(5));
//            user.setPassword(BCrypt.hashpw(passwd, user.getSalt()));
//            userService.updateById(user);
//            return ResponseUtil.success();
//        }
//
//        return ResponseUtil.error("401","您没有权限进行此操作");
//    }


//    @ApiOperation(value = "获取所有用户", response = ResponseData.class)
//    @PostMapping("/all")
//    @ResponseBody
//    public ResponseData getAllUser() {
//
//        return ResponseUtil.success(userService.getAllUser());
//    }


}
