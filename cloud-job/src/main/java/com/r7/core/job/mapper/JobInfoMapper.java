package com.r7.core.job.mapper;

import com.r7.core.job.model.CoreJob;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zs
 */

@Mapper
public interface JobInfoMapper {
    @Insert("insert into core_job(id,app_id,job_name,job_code,content,job_rule,winner_num,status,on_shelf,off_shelf,created_by,created_at,updated_by,updated_at)" +
            "values(#{id},#{appId},#{jobName},#{jobCode},#{content},#{jobRule},#{winnerNum},#{status},#{onShelf},#{offShelf},#{createdBy},#{createdAt},#{updatedBy},#{updatedAt})")
    void add(CoreJob coreJob);

    @Delete("delete from core_job where id=#{id}")
    void delete(Long id);

    @Update("update core_job set app_id=#{appId},job_name=#{jobName},job_code=#{jobCode},content=#{content},job_rule=#{jobRule},winner_num=#{winnerNum},status=#{status},on_shelf=#{onShelf},off_shelf=#{offShelf},created_by=#{createdBy},created_at=#{createdAt},updated_by=#{updatedBy},updated_at=#{updatedAt} where id=#{id}")
    void update(CoreJob coreJob);

    @Select("select * from core_job where id=#{id}")
    CoreJob findById(Long id);

    @Select("select * from core_job")
    List<CoreJob> findAll();
}
