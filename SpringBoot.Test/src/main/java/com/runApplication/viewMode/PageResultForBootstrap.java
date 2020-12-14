package com.runApplication.viewMode;

import java.util.List;

import com.runApplication.entity.Persons;

public class PageResultForBootstrap {
	private List<Persons> rows;
	
	private int total;
	
	private int deleteSussful;
	
	private int addSussful;
	
	private int updateSussful;

	/**
	 * @return the updateSussful
	 */
	public int getUpdateSussful() {
		return updateSussful;
	}

	/**
	 * @param updateSussful the updateSussful to set
	 */
	public void setUpdateSussful(int updateSussful) {
		this.updateSussful = updateSussful;
	}

	/**
	 * @return the deleteSussful
	 */
	public int getDeleteSussful() {
		return deleteSussful;
	}

	/**
	 * @param deleteSussful the deleteSussful to set
	 */
	public void setDeleteSussful(int deleteSussful) {
		this.deleteSussful = deleteSussful;
	}

	/**
	 * @return the addSussful
	 */
	public int getAddSussful() {
		return addSussful;
	}

	/**
	 * @param addSussful the addSussful to set
	 */
	public void setAddSussful(int addSussful) {
		this.addSussful = addSussful;
	}

	/**
	 * @return the rows
	 */
	public List<Persons> getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<Persons> rows) {
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
