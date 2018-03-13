package com.haven.graguation.project.service;

import java.util.List;

import com.haven.graguation.project.repository.mybatis.service.Page;
import com.haven.graguation.project.repository.mybatis.service.WhereCondition;
import com.haven.graguation.project.service.exception.DeleteFailureException;
import com.haven.graguation.project.service.exception.SaveFailureException;
import com.haven.graguation.project.service.exception.SearchFailureException;
import com.haven.graguation.project.service.exception.UpdateFailureException;

/**
 * @author Haven
 * @date 2018/03/12
 * @param <T>
 */
public interface BaseService<T> {

	/**
	 * 将数据保存到关系数据库中（mysql）
	 * 
	 * @param obj
	 * @return
	 * @throws SaveFailureException
	 */
	Boolean saveOne(T obj) throws SaveFailureException;
	
	Boolean saveBatch(List<T> lists) throws SaveFailureException;
	
	/**
	 * 从关系数据库中删除数据（mysql）
	 * 
	 * @param clazz
	 * @param id 主键id
	 * @return
	 * @throws DeleteFailureException
	 */
	Boolean removeOne(Class<T> clazz, Long id) throws DeleteFailureException;
	
	Boolean removeBatch(Class<T> clazz, Long...ids) throws DeleteFailureException;
	
	Boolean update(T obj, Long id) throws UpdateFailureException;
	
	Page<T> findByPage(Class<T> clazz, WhereCondition condition) throws SearchFailureException;
	
	Page<T> findByPage(Class<T> clazz, WhereCondition condition, Long pageSize) throws SearchFailureException;
	
	Page<T> findByPage(Class<T> clazz, WhereCondition condition, Long pageSize, Boolean useCache) throws SearchFailureException;
	
	Page<T> findByPage(Class<T> clazz, Page<T> page) throws SearchFailureException;
	
	Page<T> findByPage(Class<T> clazz, Page<T> page, Boolean useCache) throws SearchFailureException;
	
	T findById(Class<T> clazz, Long id) throws SearchFailureException;
	
	T findById(Class<T> clazz, Long id, Boolean useCache) throws SearchFailureException;
	
	List<T> findByList(Class<T> clazz, WhereCondition condition) throws SearchFailureException;
	
	List<T> findByList(Class<T> clazz, WhereCondition condition, Boolean useCache) throws SearchFailureException;
	
	List<T> findByList(Class<T> clazz, String fieldName, Boolean isIn, Object...fieldValues) throws SearchFailureException;
	
	List<T> findByList(Class<T> clazz, String fieldName, Boolean isIn, Boolean useCache, Object...fieldValues) throws SearchFailureException;
	
	/**
	 * 将数据存储到非关系数据库中（redis）
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws SaveFailureException
	 */
	Boolean set(String key, Object value) throws SaveFailureException;
	
	Boolean set(String key, Object value, Long expireTime) throws SaveFailureException;
	
	Boolean setBatch(String key, Object...values) throws SaveFailureException;
	
	Boolean setBatch(String key, Long expireTime, Object...values) throws SaveFailureException;
	
	/**
	 * 从非关系数据库中删除数据（redis）
	 * 
	 * @param key
	 * @return
	 * @throws DeleteFailureException
	 */
	Boolean del(String key) throws DeleteFailureException;
	
	Boolean delBatch(String...keys) throws DeleteFailureException;
	
	Boolean delBatch(String pattern) throws DeleteFailureException;
	
	Boolean update(String key, Object value) throws UpdateFailureException;

	Object get(String key) throws SearchFailureException;
	
}
