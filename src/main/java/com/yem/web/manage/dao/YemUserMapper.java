package com.yem.web.manage.dao;

import java.util.List;

import com.yem.dto.YemUserDTO;
import com.yem.entity.YemUser;

public interface YemUserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(YemUser record);

    int insertSelective(YemUser record);

    YemUser selectByUserCode(Long userCode);

    List<YemUser> selectUserList(YemUserDTO dto);

    int selectUserListCount(YemUserDTO dto);

    int updateByUserCodeSelective(YemUser record);

    int updateByPrimaryKey(YemUser record);
    


    /**
     * 根据会员名查找会员
     * @param userName 会员名
     * @return 会员
     */
    YemUser findByUserName(String userName);
}