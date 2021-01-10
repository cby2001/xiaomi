package com.xiaomi.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.xiaomi.dao.Util.DBUtil;
import com.xiaomi.dao.vo.Good;
import com.xiaomi.dao.vo.GoodExample;
import com.xiaomi.dao.vo.GoodExample.Criteria;
import com.xiaomi.mapper.GoodMapper;

public class GoodService {

	public List<Good> getGoodsByname(String good_name){
		SqlSession sqlSession = DBUtil.getSqlSession();
		GoodMapper mapper = sqlSession.getMapper(GoodMapper.class);
		GoodExample example = new GoodExample();
		Criteria condition = example.createCriteria();
		condition.andGoodNameLike("%"+good_name+"%");
		List<Good> goodList = mapper.selectByExample(example );
		return goodList;
	}
	
	public List<Good> getAllGoods(){
		SqlSession sqlSession = DBUtil.getSqlSession();
		GoodMapper mapper = sqlSession.getMapper(GoodMapper.class);
		List<Good> goodList = mapper.selectByExample(null);
		return goodList;
	}
	
	public List<Good> detail(String good_name) {
		SqlSession sqlSession = DBUtil.getSqlSession();
		GoodMapper mapper = sqlSession.getMapper(GoodMapper.class);
		GoodExample example = new GoodExample();
		Criteria condition = example.createCriteria();
		condition.andGoodNameEqualTo(good_name);
		List<Good> goodsList = mapper.selectByExample(example);
		return goodsList;
		
	}
	
	public List<Good> getGoodByCondition(String type,String good_name,String color){
		SqlSession sqlSession = DBUtil.getSqlSession();
		GoodMapper mapper = sqlSession.getMapper(GoodMapper.class);
		GoodExample example = new GoodExample();
		Criteria condition = example.createCriteria();
		condition.andGoodNameEqualTo(good_name);
		condition.andGoodTypeEqualTo(type);
		condition.andGoodColorEqualTo(color);
		List<Good> selectByExample = mapper.selectByExample(example);
		return selectByExample;
	}
	
	public Good getGoodByGid(int gid){
		SqlSession sqlSession = DBUtil.getSqlSession();
		GoodMapper mapper = sqlSession.getMapper(GoodMapper.class);
		GoodExample example = new GoodExample();
		Criteria condition = example.createCriteria();
		condition.andGoodIdEqualTo(gid);
		List<Good> selectByExample = mapper.selectByExample(example);
		sqlSession.close();
		return selectByExample.get(0);
	}
	
}
