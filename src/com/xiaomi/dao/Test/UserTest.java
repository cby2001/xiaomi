package com.xiaomi.dao.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import com.xiaomi.dao.Util.DBUtil;
import com.xiaomi.dao.vo.Cart;
import com.xiaomi.dao.vo.CartExample;
import com.xiaomi.dao.vo.CartExample.Criteria;
import com.xiaomi.dao.vo.Users;
import com.xiaomi.mapper.CartMapper;
import com.xiaomi.mapper.UsersMapper;

class UserTest {
	

	@Test
	public void test() {
		SqlSession sqlSession = DBUtil.getSqlSession();
		UsersMapper mapper = sqlSession.getMapper(UsersMapper.class);
		List<com.xiaomi.dao.vo.Users> userList = mapper.selectByExample(null);
		for(Users user : userList) {
			System.out.println(user);
		}
		sqlSession.close();
		
		
	}
	
	@Test
	public void test1(){
		SqlSession sqlSession = DBUtil.getSqlSession();
		CartMapper mapper = sqlSession.getMapper(CartMapper.class);
		CartExample example = new CartExample();
		Criteria condition = example.createCriteria();
		condition.andGoodIdEqualTo(18);
		condition.andUidEqualTo(1);
		List<Cart> selectByExample = mapper.selectByExample(example);
		for(Cart cart : selectByExample) {
			System.out.println(cart);
		}
		sqlSession.close();
	}

}
