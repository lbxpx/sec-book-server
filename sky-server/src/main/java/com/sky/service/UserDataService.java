package com.sky.service;

import com.sky.dto.UserDataDTO;
import com.sky.vo.UserDataVO;

public interface UserDataService {
    /**
     * 获取用户信息
     * @param id
     * @return
     */
    UserDataVO getUserData(Integer id);

    /**
     * 修改用户信息
     * @param userDataDTO
     */
    void update(UserDataDTO userDataDTO);
}
