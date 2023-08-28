package com.example.backend.mapper;

import com.example.backend.entity.Menu;
import com.example.backend.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yaoge
 * @since 2023-08-14
 */

@Repository
public interface UserMapper extends BaseMapper<User> {
    public List<Menu> getMenuByUserId(@Param("id") Integer id);

}
