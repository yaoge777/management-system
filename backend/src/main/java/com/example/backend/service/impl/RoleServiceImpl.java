package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.entity.Role;
import com.example.backend.entity.RoleMenu;
import com.example.backend.mapper.RoleMapper;
import com.example.backend.service.IRoleMenuService;
import com.example.backend.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {


    @Autowired
    private IRoleMenuService roleMenuService;


    @Override
    public List<Role> getRoleList(){
        return this.list();
    }

    @Override
    public List<Role> getAllRole(Long pageNumber, Long pageSize){
        Page<Role> page = new Page<>(pageNumber,pageSize);
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper();
        wrapper.orderByDesc(Role::getRoleId);
        this.page(page,wrapper);
        List<Role> list = page.getRecords();
        return list;
    }

    @Override
    @Transactional
    public boolean insertRole(Role role){
        int row = this.getBaseMapper().insert(role);
        List<Integer> list = role.getMenuIdList();
        for(Integer i : list){
            roleMenuService.getBaseMapper().insert(new RoleMenu(null, role.getRoleId(), i));
        }
        return row != 0;
    }

    @Override
    @Transactional
    public boolean updateRole(Role role){
        boolean r = this.updateById(role);
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, role.getRoleId());
        int row = roleMenuService.getBaseMapper().delete(wrapper);
        List<Integer> list = role.getMenuIdList();
        for(Integer i : list){
            roleMenuService.getBaseMapper().insert(new RoleMenu(null, role.getRoleId(), i));
        }
        return r && row!=0;
    }

    @Override
    @Transactional
    public boolean deleteRole(Role role){
        System.out.println(role.toString());
        LambdaQueryWrapper<Role> w1 = new LambdaQueryWrapper<>();
        w1.eq(Role::getRoleId, role.getRoleId());
        int r = this.baseMapper.delete(w1);
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, role.getRoleId());
        int row = roleMenuService.getBaseMapper().delete(wrapper);
        return r != 0 && row!=0;
    }

    @Override
    public List<Role> queryRole(String roleName, Long pageNumber, Long pageSize){
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(roleName.length() != 0, Role::getRoleName, roleName);
        wrapper.orderByDesc(Role::getRoleId);
        Page<Role> page = new Page(pageNumber,pageSize);
        this.page(page,wrapper);
        List<Role> list = page.getRecords();
        if(list != null){
            for(Role l : list){
                LambdaQueryWrapper<RoleMenu> wrapper1 = new LambdaQueryWrapper<>();
                wrapper1.eq(RoleMenu::getRoleId, l.getRoleId());
                List<RoleMenu> roleMenu = roleMenuService.list(wrapper1);
                l.setMenuIdList(roleMenu.stream().map(v -> v.getMenuId()).collect(Collectors.toList()));
            }
        }
        return list;
    }

}
