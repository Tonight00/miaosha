package com.buaa.backkom.miaosha.dao;

import com.buaa.backkom.miaosha.dataobject.UserPasswordDO;
import org.springframework.stereotype.Component;

@Component
public interface UserPasswordDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbggenerated Sun Sep 20 19:31:59 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbggenerated Sun Sep 20 19:31:59 CST 2020
     */
    int insert(UserPasswordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbggenerated Sun Sep 20 19:31:59 CST 2020
     */
    int insertSelective(UserPasswordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbggenerated Sun Sep 20 19:31:59 CST 2020
     */
    UserPasswordDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbggenerated Sun Sep 20 19:31:59 CST 2020
     */
    int updateByPrimaryKeySelective(UserPasswordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbggenerated Sun Sep 20 19:31:59 CST 2020
     */
    int updateByPrimaryKey(UserPasswordDO record);
    
    UserPasswordDO selectByUserId (Integer userId);
}