package com.yym.setting.mapper;

import com.yym.setting.model.CoreSettingEntity;
import com.yym.setting.model.CoreSettingEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CoreSettingEntityMapper {
    long countByExample(CoreSettingEntityExample example);

    int deleteByExample(CoreSettingEntityExample example);

    int insert(CoreSettingEntity record);

    int insertSelective(CoreSettingEntity record);

    CoreSettingEntity selectOneByExample(CoreSettingEntityExample example);

    List<CoreSettingEntity> selectByExample(CoreSettingEntityExample example);

    int updateByExampleSelective(@Param("record") CoreSettingEntity record, @Param("example") CoreSettingEntityExample example);

    int updateByExample(@Param("record") CoreSettingEntity record, @Param("example") CoreSettingEntityExample example);
}