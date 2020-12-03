package com.ruoyi.web.controller.open;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysMenuService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/open")
public class OpenController extends BaseController {

    @Resource
    private ISysMenuService menuService;

    @Resource
    private ISysUserService userService;

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("/getRouters")
    public AjaxResult getRouters(String projectId) {

        List<SysMenu> menus = menuService.selectAllMenuTree();
        List<SysMenu> result = new ArrayList<>();
        if (!StringUtils.isEmpty(projectId)) {
            for (SysMenu m : menus) {
                if (projectId.equals(m.getProjectId())) {
                    result.add(m);
                }
            }
        } else {
            result.addAll(menus);
        }

        return AjaxResult.success(menuService.buildMenus(result));
    }

    /**
     * 通过用户名获取用户信息
     *
     * @return 用户信息
     */
    @PostMapping("/getUserByName")
    public SysUser getUserByName(String username) {
        SysUser user = userService.selectUserByUserName(username);
        return user;
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @PostMapping("/selectMenuPermsByUserId")
    public Set<String> selectMenuPermsByUserId(Long userId) {
        return menuService.selectMenuPermsByUserId(userId);
    }

}
