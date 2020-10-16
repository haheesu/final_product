package com.ziumks.organization;

import javax.sql.DataSource;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Component
public class MysqlRunner implements ApplicationRunner{
	
	private DataSource dataSource;
	
	public MysqlRunner(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		try(Connection connection = dataSource.getConnection()){
			System.out.println(dataSource.getClass());
			System.out.println(connection.getMetaData().getURL());
			System.out.println(connection.getMetaData().getUserName());
		}
	}
}
