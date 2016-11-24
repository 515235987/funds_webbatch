package com.ibm.service;

import java.util.List;

public interface IFundsService {
	
	public List<String> getFundCode();

	public int addHistoryFunds(List<String> funds);
}
