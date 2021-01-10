package com.xiaomi.dao.Util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBUtil {

	static SqlSessionFactory factory;
	static {
		InputStream is = null;
		try {
			is = Resources.getResourceAsStream("SqlMapConfig.xml");
			factory = new SqlSessionFactoryBuilder().build(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static SqlSession getSqlSession() {
		return factory.openSession();
	}
	
}
