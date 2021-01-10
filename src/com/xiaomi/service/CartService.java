package com.xiaomi.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.xiaomi.dao.Util.DBUtil;
import com.xiaomi.dao.vo.Cart;
import com.xiaomi.dao.vo.CartExample;
import com.xiaomi.dao.vo.CartExample.Criteria;
import com.xiaomi.mapper.CartMapper;

public class CartService {

	public boolean addCart(Cart cart) {
		SqlSession sqlSession = DBUtil.getSqlSession();
		CartMapper mapper = sqlSession.getMapper(CartMapper.class);
		int insertSelective = mapper.insertSelective(cart);
		sqlSession.commit();
		sqlSession.close();
		return insertSelective>0?true:false;
	}
	
	public Cart findCartByUidGid(int uid,int gid) {
		SqlSession sqlSession = DBUtil.getSqlSession();
		CartMapper mapper = sqlSession.getMapper(CartMapper.class);
		CartExample example = new CartExample();
		Criteria condition = example.createCriteria();
		condition.andUidEqualTo(uid);
		condition.andGoodIdEqualTo(gid);
		List<Cart> selectByExample = mapper.selectByExample(example);
		sqlSession.close();
		return selectByExample.size() > 0?selectByExample.get(0):null;
	}
	
	public boolean updateCart(Cart cart) {
		SqlSession sqlSession = DBUtil.getSqlSession();
		CartMapper mapper = sqlSession.getMapper(CartMapper.class);
		CartExample example = new CartExample();
		Criteria condition = example.createCriteria();
		condition.andPreIdEqualTo(cart.getPreId());
		int updateByExampleSelective = mapper.updateByExampleSelective(cart, example);
		sqlSession.commit();
		sqlSession.close();
		return updateByExampleSelective>0?true:false;
	}
}
