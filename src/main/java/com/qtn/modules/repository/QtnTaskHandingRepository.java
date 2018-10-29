package com.qtn.modules.repository;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface QtnTaskHandingRepository {

    public List<Map<String,Object>>  getList();

}
