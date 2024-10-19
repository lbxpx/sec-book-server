package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.UsersPageQueryDTO;
import com.sky.entity.User;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.UserDataMapper;
import com.sky.mapper.UsersMapper;
import com.sky.result.ChartResult;
import com.sky.result.PageResult;
import com.sky.service.UsersService;
import com.sky.vo.UserCountByMonthVO;
import com.sky.vo.UsersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserDataMapper userDataMapper;

    /**
     * 分页查询用户信息
     * @param usersPageQueryDTO
     * @return
     */
    public PageResult pageQuery(UsersPageQueryDTO usersPageQueryDTO) {
        PageHelper.startPage(usersPageQueryDTO.getPage(),usersPageQueryDTO.getPageSize());
        Page<UsersVO> page = usersMapper.pageQuery(usersPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 批量删除用户信息
     * @param ids
     */
    public void deleteBatch(List<Long> ids) {
        //判断当前账户能否删除
        for (Long id : ids) {
            User user = usersMapper.getById(id);
            if(user.getStatus() == StatusConstant.ENABLE){
                //当前用户处于启用状态，不能删除
                throw new DeletionNotAllowedException(MessageConstant.USERS_ON_USE);
            }
            if(user.getRole() == "admin"){
                //管理员账号不能删除
                throw new DeletionNotAllowedException(MessageConstant.ADMIN_ON_USE);
            }
        }
        //将用户详情表中数据一并删除
        userDataMapper.deleteByUserIds(ids);
        //根据用户id集合批量删除用户数据
        usersMapper.deleteByIds(ids);
        }

    /**
     * 启用或停用账户
     * @param status
     * @param id
     */
    public void updateStatus(String status, Long id) {
        User user = User.builder()
                .id(id)
                .status(status)
                .updatedAt(LocalDateTime.now())
                .build();
        usersMapper.update(user);
    }

    /**
     * 查询用户图表数据
     * @return
     */
    public ChartResult countUsersByMonth() {
        List<UserCountByMonthVO> list= usersMapper.countUsersByMonth();
        // 创建两个列表来存储 dateMonth 和 userCount
        List<String> dateMonths = new ArrayList<>();
        List<Long> userCountsList = new ArrayList<>();

        for (UserCountByMonthVO userCount : list) {
            dateMonths.add(userCount.getDateMonth()); // 添加 dateMonth 到第一个列表
            userCountsList.add(userCount.getUserCount()); // 添加 userCount 到第二个列表
        }
        return new ChartResult(dateMonths,userCountsList);
    }


}

