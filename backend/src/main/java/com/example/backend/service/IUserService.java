package com.example.backend.service;

import com.example.backend.entity.Menu;
import com.example.backend.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yaoge
 * @since 2023-08-14
 */
public interface IUserService extends IService<User> {
   List<User> getAllUser(Long PageNumber, Long PageSize);
   List<User> queryUser(String username, String email, String phone, Long PageNumber, Long PageSize);

   User getUserById(Integer id);
   boolean addUser(User user);

   boolean updateUser(User user);

   @Transactional
   boolean deleteUser(User user);

    List<Menu> getMenuByUserId(Integer id);

    Map<String, Object> login(User user);
}
