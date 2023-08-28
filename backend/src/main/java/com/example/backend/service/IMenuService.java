package com.example.backend.service;

import com.example.backend.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backend.entity.RoleMenu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yaoge
 * @since 2023-08-14
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> getMenuByRoleMenu(List<RoleMenu> roleMenu);

    List<Menu> getAllMenu();
}
