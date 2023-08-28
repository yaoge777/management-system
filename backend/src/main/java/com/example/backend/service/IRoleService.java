package com.example.backend.service;

import com.example.backend.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yaoge
 * @since 2023-08-14
 */
public interface IRoleService extends IService<Role> {

    List<Role> getRoleList();

    List<Role> getAllRole(Long pageNumber, Long pageSize);

    @Transactional
    boolean insertRole(Role role);

    @Transactional
    boolean updateRole(Role role);

    @Transactional
    boolean deleteRole(Role role);

    List<Role> queryRole(String roleName, Long pageNumber, Long pageSize);
}
