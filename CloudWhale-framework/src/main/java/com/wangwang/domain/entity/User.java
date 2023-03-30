package com.wangwang.domain.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 用户表(User)表实体类
 *
 * @author makejava
 * @since 2023-03-25 21:40:15
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User implements Serializable {

    /**
     * Todo:当我们将一个Java对象存储到Redis缓存中时，为了确保该对象可以正确序列化和反序列化，
     * Todo:我们需要实现Serializable接口并添加私有的序列化版本号。一般情况下，我们可以将 serialVersionUID 设置为固定值-1，
     * Todo:这样Java会自动生成一个版本号。如果我们更新了类的成员变量或方法，需要手动更新 serialVersionUID。
     */

    private static final long serialVersionUID = -1;

    //主键注释
    @TableId
    private Long id;

    //用户名
    private String userName;
    //昵称
    private String nickName;
    //密码
    private String password;
    //用户类型：0代表普通用户，1代表管理员
    private String type;
    //账号状态（0正常 1停用）
    private String status;
    //邮箱
    private String email;
    //手机号
    private String phonenumber;
    //用户性别（0男，1女，2未知）
    private String sex;
    //头像
    private String avatar;
    //创建人的用户id
    private Long createBy;
    //创建时间
    private Date createTime;
    //更新人
    private Long updateBy;
    //更新时间
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;

}

