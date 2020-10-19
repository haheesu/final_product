package com.ziumks.organization.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ziumks.organization.model.Organization;

@Mapper
public interface OrganizationMapper {
	
	@Select("select * from test_mysql where id=#{id}")
	Organization getOrganization(@Param("id") Long id);
	
	@Select("select * from test_mysql")
	List<Organization> getOrganizationList();
	
	@Insert("insert into test_mysql(id, name) values(#{id}, #{name})")
	int insertOrganization(@Param("id") Long id, @Param("name") String name);
	
	@Update("update test_mysql set name=#{name} where id=#{id}")
	int updateOrganization(@Param("id") Long id, @Param("name") String name);
	
	@Delete("delete from test_mysql where id=#{id}")
	int deleteOrganization(@Param("id") Long id);
}
