package com.ibm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibm.dao.FundsDao;
import com.ibm.service.IFundsService;

public class FundsServiceIMPL implements IFundsService {

	@Autowired
	private FundsDao fundsDao;
	
	public void setFundsDao(FundsDao fundsDao) {
		this.fundsDao = fundsDao;
	}
	@Override
	public List<String> getFundCode() {
		// TODO Auto-generated method stub
		return fundsDao.getFundsCode();
	}
	
	@Override
	public int addHistoryFunds(List<String> funds) {
		// TODO Auto-generated method stub
		return fundsDao.addHistoryFunds(funds);
	}

}
