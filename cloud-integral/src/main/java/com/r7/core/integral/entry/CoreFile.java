package com.r7.core.integral.entry;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Table(name="core_file")
public class CoreFile implements Serializable,Cloneable{
    /** 编号 */
    @Id
    @GeneratedValue
    private Integer id ;
    /** 用户id */
    private String userId ;
    /** 路径 */
    private String url ;
    /** 类型;1文章2图片3视频4混合 */
    private Integer type ;
    /** 状态;-1已删除（用户不可见）1已发布 */
    private Integer status ;
    /** 资源描述 */
    private String description ;
    /** 创建人 */
    private Long createdBy ;
    /** 创建时间 */
    private Date createdAt ;
    /** 更新人 */
    private Long updatedBy ;
    /** 更新时间 */
    private Date updatedAt ;

    /** 编号 */
    public Integer getId(){
        return this.id;
    }
    /** 编号 */
    public void setId(Integer id){
        this.id = id;
    }
    /** 用户id */
    public String getUserId(){
        return this.userId;
    }
    /** 用户id */
    public void setUserId(String userId){
        this.userId = userId;
    }
    /** 路径 */
    public String getUrl(){
        return this.url;
    }
    /** 路径 */
    public void setUrl(String url){
        this.url = url;
    }
    /** 类型;1文章2图片3视频4混合 */
    public Integer getType(){
        return this.type;
    }
    /** 类型;1文章2图片3视频4混合 */
    public void setType(Integer type){
        this.type = type;
    }
    /** 状态;-1已删除（用户不可见）1已发布 */
    public Integer getStatus(){
        return this.status;
    }
    /** 状态;-1已删除（用户不可见）1已发布 */
    public void setStatus(Integer status){
        this.status = status;
    }
    /** 资源描述 */
    public String getDescription(){
        return this.description;
    }
    /** 资源描述 */
    public void setDescription(String description){
        this.description = description;
    }
    /** 创建人 */
    public Long getCreatedBy(){
        return this.createdBy;
    }
    /** 创建人 */
    public void setCreatedBy(Long createdBy){
        this.createdBy = createdBy;
    }
    /** 创建时间 */
    public Date getCreatedAt(){
        return this.createdAt;
    }
    /** 创建时间 */
    public void setCreatedAt(Date createdAt){
        this.createdAt = createdAt;
    }
    /** 更新人 */
    public Long getUpdatedBy(){
        return this.updatedBy;
    }
    /** 更新人 */
    public void setUpdatedBy(Long updatedBy){
        this.updatedBy = updatedBy;
    }
    /** 更新时间 */
    public Date getUpdatedAt(){
        return this.updatedAt;
    }
    /** 更新时间 */
    public void setUpdatedAt(Date updatedAt){
        this.updatedAt = updatedAt;
    }
}