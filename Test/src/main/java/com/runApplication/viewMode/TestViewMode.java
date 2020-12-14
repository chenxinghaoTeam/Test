package com.runApplication.viewMode;

import java.util.List;
import java.util.Map;

public class TestViewMode {
	
	private List<Map<String, Object>> rows; //列表数据
	
	private int total; //列表个数

	/**
	 * List结果集
	 */
	public List<Map<String, Object>> resultList;
	
	
	/**
	 * 是否成功
	 */
	public Boolean successful;
	
	

	/**
	 * @return the successful
	 */
	public Boolean getSuccessful() {
		return successful;
	}

	/**
	 * @param successful the successful to set
	 */
	public void setSuccessful(Boolean successful) {
		this.successful = successful;
	}

	/**
	 * @return the resultList
	 */
	public List<Map<String, Object>> getResultList() {
		return resultList;
	}

	/**
	 * @param resultList the resultList to set
	 */
	public void setResultList(List<Map<String, Object>> resultList) {
		this.resultList = resultList;
	}

	/**
	 * @return the rows
	 */
	public List<Map<String, Object>> getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<Map<String, Object>> rows) {
		this.rows = rows;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	
}
