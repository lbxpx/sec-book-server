package com.sky.service.impl;

import com.sky.dto.UserDataDTO;
import com.sky.entity.User;
import com.sky.mapper.UserDataMapper;
import com.sky.mapper.UsersMapper;
import com.sky.service.UserDataService;
import com.sky.vo.UserDataVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class UserDataServiceImpl implements UserDataService {
    @Autowired
    private UserDataMapper userDataMapper;
    @Autowired
    private UsersMapper usersMapper;

    /**
     * 获取用户信息
     */
    public UserDataVO getUserData(Integer id) {
        UserDataVO userDataVO = userDataMapper.getUserData(id);
        return userDataVO;
    }

    /**
     * 修改用户信息
     */
    public void update(UserDataDTO userDataDTO) {
        userDataDTO.setPassword(DigestUtils.md5DigestAsHex(userDataDTO.getPassword().getBytes()));
        userDataMapper.update(userDataDTO);
        User user = new User();
        BeanUtils.copyProperties(userDataDTO, user);
        user.setUsername(userDataDTO.getName());
        user.setUpdatedAt(LocalDateTime.now());
        user.setId(userDataDTO.getUserId());
        usersMapper.update(user);
    }
}
