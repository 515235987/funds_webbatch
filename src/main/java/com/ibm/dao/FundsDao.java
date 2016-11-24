package com.ibm.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

public class FundsDao extends SqlSessionDaoSupport {
	public List<String> getFundsCode() {
		return getSqlSession().selectList("funds.getFundsCode");
	}
	
	public int addHistoryFunds(List<String> funds){
		return getSqlSession().insert("funds.addHistoryFunds", funds);
	}
}
