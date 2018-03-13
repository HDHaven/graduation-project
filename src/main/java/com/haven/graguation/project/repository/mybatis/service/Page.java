package com.haven.graguation.project.repository.mybatis.service;

import java.util.List;
import java.util.Map;

/**
 * @author Haven
 * @date 2018/03/12
 * @param <T>
 */
public interface Page<T> {

	public Long getOffset();

	public Long getRowCount();

	public Long getPageSize();

	public void setPageSize(Long pageSize);

	public Long getTotalCount();

	public void setTotalCount(Long totalCount);

	public void setOffset(Long offset);

	public void setRowCount(Long rowCount);

	public void setPageNum(Long pageNum);

	public Long getPageNum();

	public Long getCurrentPage();

	public void setCurrentPage(Long currentPage);
	
	public List<T> getData();

	public void setData(List<T> data);

	public Map<String, Object> getSearchMap();

	public void setSearchMap(Map<String, Object> searchMap);

	public List<String> getOrderBy();

	public void setOrderBy(List<String> orderBy);
	
}
