package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.backend.entity.Menu;
import com.example.backend.entity.RoleMenu;
import com.example.backend.mapper.MenuMapper;
import com.example.backend.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> getMenuByRoleMenu(List<RoleMenu> roleMenu){
        List<Menu> data = new ArrayList<>();
        for(RoleMenu rm : roleMenu){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Menu::getMenuId, rm.getMenuId()).eq(Menu::getIsLeaf, "Y");
            Menu one = this.baseMapper.selectOne(wrapper);
            if(one != null) data.add(one);
        }
        return data;
    }

    @Override
    public List<Menu> getAllMenu(){
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId, 0);
        List<Menu> data = this.list(wrapper);
        BFS(data);
        return data;
    }

    public void BFS(List<Menu> data){
        ArrayDeque<Menu> deque = new ArrayDeque<>(data);
        while(!deque.isEmpty()){
            int len = deque.size();
            for(int i=0; i<len; i++){
                Menu cur = deque.pollFirst();
                LambdaQueryWrapper<Menu> w1 = new LambdaQueryWrapper<>();
                w1.eq(Menu::getParentId, cur.getMenuId());
                List<Menu> list = this.list(w1);
                if(!list.isEmpty()){
                    cur.setChildren(list);
                    deque.addAll(list);
                }
            }
        }
    }

}
