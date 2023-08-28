package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.backend.entity.Menu;
import com.example.backend.entity.Role;
import com.example.backend.entity.RoleMenu;
import com.example.backend.mapper.RoleMenuMapper;
import com.example.backend.service.IMenuService;
import com.example.backend.service.IRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yaoge
 * @since 2023-08-14
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Autowired
    private IMenuService menuService;

    @Override
    public List<Menu> getMenuByRole(Role role){
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, role.getRoleId());
        List<RoleMenu> roleMenus = this.list(wrapper);
        List<Menu> menus = menuService.getMenuByRoleMenu(roleMenus);
        return menus;
    }
}
