package com.example.backend.service;

import com.example.backend.entity.Menu;
import com.example.backend.entity.Role;
import com.example.backend.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yaoge
 * @since 2023-08-14
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    List<Menu> getMenuByRole(Role role);
}
