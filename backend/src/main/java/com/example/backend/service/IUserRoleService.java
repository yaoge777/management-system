package com.example.backend.service;

import com.example.backend.entity.Role;
import com.example.backend.entity.User;
import com.example.backend.entity.UserRole;
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
public interface IUserRoleService extends IService<UserRole> {
    List<Integer> getRoleByUser(User user);
}
