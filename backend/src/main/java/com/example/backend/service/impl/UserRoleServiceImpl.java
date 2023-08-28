package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.example.backend.entity.Role;
import com.example.backend.entity.User;
import com.example.backend.entity.UserRole;
import com.example.backend.mapper.UserRoleMapper;
import com.example.backend.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yaoge
 * @since 2023-08-14
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
    public List<Integer> getRoleByUser(User user){
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, user.getId());
        List<Integer> userroles = this.list(wrapper).stream().map(v -> v.getRoleId()).collect(Collectors.toList());
        return userroles;
    }
}
