package com.hodehtml.demo.dao;

import com.hodehtml.demo.model.FaceRecognition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("FaceRecognitionMapper")
public interface FaceRecognitionMapper {
    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(FaceRecognition record);

    int insertSelective(FaceRecognition record);

    FaceRecognition selectByPrimaryKey(@Param("id") Integer id);

    FaceRecognition selectByUserId(@Param("UserId") String UserId);

    int updateByPrimaryKeySelective(FaceRecognition record);

    int updateByPrimaryKey(FaceRecognition record);
}