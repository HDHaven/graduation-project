package com.haven.graguation.project.repository.mybatis.service;

import java.util.List;
import java.util.Map;

/**
 * @author Haven
 * @date 2018/03/12
 */
public interface WhereCondition {

	void gt(String fieldName, Object value);
	
	void eq(String fieldName, Object value);
	
	void lt(String fieldName, Object value);
	
	void ge(String fieldName, Object value);
	
	void le(String fieldName, Object value);
	
	void notEq(String fieldName, Object value);
	
	void like(String fieldName, Object value);
	
	void notLike(String fieldName, Object value);
	
	void isNull(String fieldName);
	
	void isNotNull(String fieldName);
	
	void betweenAnd(String fieldName, Object floorValue, Object ceilValue);
	
	void orderByDesc(String fieldName);
	
	void orderByAsc(String fieldName);
	
	Map<String, Object> getSearchMap();
	
	List<String> getOrderBy();
	
}
