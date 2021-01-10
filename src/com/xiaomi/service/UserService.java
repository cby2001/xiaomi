package com.xiaomi.service;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;

import com.xiaomi.dao.Util.DBUtil;
import com.xiaomi.dao.vo.Users;
import com.xiaomi.dao.vo.UsersExample;
import com.xiaomi.dao.vo.UsersExample.Criteria;
import com.xiaomi.mapper.UsersMapper;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

public class UserService {

	public boolean ImgCodeValidate(String Imgcode,HttpServletRequest request) {
		boolean flag = true;
		
		String code = (String)request.getSession().getAttribute("code");
		
		if(!code.equals(Imgcode)) {
			flag = false;
		}
		return flag;
	}
	//用户注册
	public boolean register(Users user) {
		SqlSession sqlSession = DBUtil.getSqlSession();
		boolean flag = true;
		UsersMapper mapper = sqlSession.getMapper(UsersMapper.class);
		int insertUser = mapper.insertSelective(user);
		if(insertUser == 0) {
			flag = false;
		}
		sqlSession.commit();
		return flag;
	}
	
	public List<Users> login(String username,String password,HttpServletRequest request) {
		SqlSession sqlSession = DBUtil.getSqlSession();
		UsersMapper mapper = sqlSession.getMapper(UsersMapper.class);
		UsersExample example = new UsersExample();
		Criteria condition = example.createCriteria();
		condition.andUsernameEqualTo(username);
		condition.andPasswordEqualTo(password);
		List<Users> userList = mapper.selectByExample(example);
		if(userList.size() == 0) {
			request.setAttribute("msg", "账号或密码错误！");
			return null;
		}
		for(Users user : userList) {
			request.getSession().setAttribute("user",user);
		}
		return userList;
	}
	
	public List<Users> selfinfo(Users user) {
		SqlSession sqlSession = DBUtil.getSqlSession();
		UsersMapper mapper = sqlSession.getMapper(UsersMapper.class);
		UsersExample example = new UsersExample();
		Criteria condition = example.createCriteria();
		condition.andUsernameEqualTo(user.getUsername());
		List<Users> userList = mapper.selectByExample(example );
		return userList;
		
	}
	public boolean editupdate(Users user) {
		SqlSession sqlSession = DBUtil.getSqlSession();
		UsersMapper mapper = sqlSession.getMapper(UsersMapper.class);
		UsersExample example = new UsersExample();
		Criteria condition = example.createCriteria();
		condition.andUsernameEqualTo(user.getUsername());
		int updateuser = mapper.updateByExampleSelective(user, example);
		sqlSession.commit();
		return updateuser>0?true:false;
		
	}
	
}
