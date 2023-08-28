package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.entity.Menu;
import com.example.backend.entity.User;
import com.example.backend.entity.UserRole;
import com.example.backend.mapper.UserMapper;
import com.example.backend.service.IUserRoleService;
import com.example.backend.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserRoleService userroleservice;

    @Autowired
    private PasswordEncoder pe;

    @Override
    public List<User> getAllUser(Long PageNumber, Long PageSize){
        Page<User> page = new Page<>(PageNumber,PageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(User::getId);
        this.page(page,wrapper);
        List<User> users = page.getRecords();
        for(User user : users){
            user.setRoleList(userroleservice.getRoleByUser(user));
        }
        return users;
    }
    @Override
    public List<User> queryUser(String userName, String email, String phone, Long PageNumber, Long PageSize){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(userName.length() != 0, User::getUsername, userName);
        wrapper.eq(email.length() != 0, User::getEmail, email);
        wrapper.eq(phone.length() != 0, User::getPhone, phone);
        wrapper.orderByDesc(User::getId);
        Page<User> page = new Page<>(PageNumber, PageSize);
        this.page(page, wrapper);
        List<User> list = page.getRecords();
        for(User u : list){
            u.setRoleList(userroleservice.getRoleByUser(u));
        }
        return list;
    }

    @Override
    public User getUserById(Integer id){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, id);
        User user = this.list(wrapper).get(0);
        List<Integer> roleList = userroleservice.getRoleByUser(user);
        user.setRoleList(roleList);
        return user;
    }

    @Override
    @Transactional
    public boolean addUser(User user){
        user.setPassword(pe.encode(user.getPassword()));
        int row = this.baseMapper.insert(user);
        List<Integer> list = user.getRoleList();
        if(list != null){
            for(Integer l : list){
                userroleservice.getBaseMapper().insert(new UserRole(null, user.getId(), l));
            }
        }
        return row != 0;
    }

    @Override
    @Transactional
    public boolean updateUser(User user){
        user.setPassword(pe.encode(user.getPassword()));
        int row = this.baseMapper.updateById(user);
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, user.getId());
        userroleservice.getBaseMapper().delete(wrapper);
        if(!user.getRoleList().isEmpty()){
            for(Integer i : user.getRoleList()){
                userroleservice.getBaseMapper().insert(new UserRole(null, user.getId(), i));
            }
        }
        return row != 0;
    }

    @Override
    @Transactional
    public boolean deleteUser(User user){
        boolean row = this.removeById(user);
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, user.getId());
        userroleservice.remove(wrapper);
        return row;

    }

    @Override
    public List<Menu> getMenuByUserId(Integer id){
        List<Menu> menus = this.baseMapper.getMenuByUserId(id);
        List<Menu> pmenu = menus.stream().filter(v ->v.getParentId() == 0).collect(Collectors.toList());
        List<Menu> cmenu = menus.stream().filter(v ->v.getParentId() != 0).collect(Collectors.toList());
        for(Menu m : pmenu)  setChildrenMenu(cmenu, m);
        return pmenu;
    }

    public void setChildrenMenu(List<Menu> menus, Menu parent){
        for(Menu m : menus) {
            if(m.getParentId() == parent.getMenuId()){
                parent.addChildren(m);
                setChildrenMenu(menus, m);
            }
        }
    }

    @Override
    public Map<String, Object> login(User user){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User u = this.baseMapper.selectOne(wrapper);
        if(u != null && pe.matches(user.getPassword(), u.getPassword())){
            u.setPassword(null);
            List<Menu> menus = getMenuByUserId(u.getId());
            HashMap<String, Object> map= new HashMap<>();
            map.put("user", u);
            map.put("menu", menus);
            System.out.println(map);
            return map;
        }
        return null;
    }


}
