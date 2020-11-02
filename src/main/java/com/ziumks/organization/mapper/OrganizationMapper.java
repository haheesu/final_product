package com.ziumks.organization.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ziumks.organization.model.FileVO;
import com.ziumks.organization.model.Organization;

@Mapper
public interface OrganizationMapper {
	
	@Select("select * from test_mysql where id=#{id}")
	Organization getOrganization(@Param("id") int id);
	
	@Select("select * from test_mysql")
	List<Organization> getOrganizationList();
	
	@Insert("insert into test_mysql(id, name) values(#{id}, #{name})")
	int insertOrganization(@Param("id") int id, @Param("name") String name);
	
	@Update("update test_mysql set name=#{name} where id=#{id}")
	int updateOrganization(@Param("id") int id, @Param("name") String name);
	
	@Delete("delete from test_mysql where id=#{id}")
	int deleteOrganization(@Param("id") int id);
	
	@Insert(
//			"select max(id) from test_mysql " +
			 "insert into file(id, filename, fileOriginName, fileUrl) "
			+ "values(#{id}, #{filename}, #{fileoriginname}, #{fileurl})")
	int fileInsert(FileVO file);
	
	@Select("select * from file where id=#{id}")
	FileVO fileDetail(@Param("id") int id);
	
	@Update("update file set"  
			+ "filename=#{filename}, fileOriginName=#{fileoriginname}, fileUrl=#{fileurl} " 
			+ "where id=#{id}")
	int fileUpdate(@Param("id") int id, @Param("filename") String filename, 
			@Param("fileoriginname") String fileoriginname , @Param("fileurl") String fileurl);
}
