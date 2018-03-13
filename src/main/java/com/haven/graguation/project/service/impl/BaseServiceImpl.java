package com.haven.graguation.project.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haven.graguation.project.repository.mybatis.annotation.Table;
import com.haven.graguation.project.repository.mybatis.service.Page;
import com.haven.graguation.project.repository.mybatis.service.SuperService;
import com.haven.graguation.project.repository.mybatis.service.WhereCondition;
import com.haven.graguation.project.repository.redis.service.RedisService;
import com.haven.graguation.project.service.BaseService;
import com.haven.graguation.project.service.exception.DeleteFailureException;
import com.haven.graguation.project.service.exception.SaveFailureException;
import com.haven.graguation.project.service.exception.SearchFailureException;
import com.haven.graguation.project.service.exception.UpdateFailureException;

@Service
public class BaseServiceImpl<T> implements BaseService<T> {
	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);
	
	@Autowired
	private SuperService<T> superService;
	
	@Autowired
	private RedisService redisService;

	@Override
	public Boolean saveOne(T obj) throws SaveFailureException {
		Boolean result = Boolean.FALSE;
		try {
			result = superService.saveOne(obj);
		} catch(Exception e) {
			logger.error("save object "+ obj.getClass().getName() +" error: "+ e.getMessage(), e);
			throw new SaveFailureException();// TODO
		}
		return result;
	}

	@Override
	public Boolean saveBatch(List<T> lists) throws SaveFailureException {
		Boolean result = Boolean.FALSE;
		try {
			result = superService.saveBatch(lists);
		} catch(Exception e) {
			logger.error("save object "+ lists.get(0).getClass().getName() +" error: "+ e.getMessage(), e);
			throw new SaveFailureException();// TODO
		}
		return result;
	}

	@Override
	public Boolean removeOne(Class<T> clazz, Long id) throws DeleteFailureException {
		Boolean result = Boolean.FALSE;
		try {
			result = superService.deleteOne(clazz, id);
		} catch(Exception e) {
			logger.error("delete object "+ clazz.getName() +"{id: "+ id +"} error: "+ e.getMessage(), e);
			throw new DeleteFailureException();// TODO
		}
		return result;
	}

	@Override
	public Boolean removeBatch(Class<T> clazz, Long... ids) throws DeleteFailureException {
		Boolean result = Boolean.FALSE;
		try {
			result = superService.deleteBatch(clazz, ids);
		} catch(Exception e) {
			logger.error("delete object "+ clazz.getName() +"{id: "+ ids +"} error: "+ e.getMessage(), e);
			throw new DeleteFailureException();// TODO
		}
		return result;
	}

	@Override
	public Boolean update(T obj, Long id) throws UpdateFailureException {
		Boolean result = Boolean.FALSE;
		try {
			result = superService.update(id, obj);
		} catch(Exception e) {
			logger.error("update object "+ obj.getClass().getName() +"{id: "+ id +"} error: "+ e.getMessage(), e);
			throw new UpdateFailureException();// TODO
		}
		return result;
	}

	@Override
	public Page<T> findByPage(Class<T> clazz, WhereCondition condition) throws SearchFailureException {
		return this.findByPage(clazz, condition, null, Boolean.TRUE);
	}
	
	@Override
	public Page<T> findByPage(Class<T> clazz, WhereCondition condition, Long pageSize) throws SearchFailureException {
		return this.findByPage(clazz, condition, pageSize, Boolean.TRUE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<T> findByPage(Class<T> clazz, WhereCondition condition, Long pageSize, Boolean useCache)
			throws SearchFailureException {
		Page<T> page = null;
		try {
			if(useCache) {
				String key = this.generateKey(clazz, condition.getSearchMap(), condition.getOrderBy(), null);
				page = redisService.exists(key) ? (Page<T>) redisService.get(key) : null;
				if(page == null) {
					page = superService.findByPage(clazz, condition, pageSize);
					redisService.set(key, page);
				}
			} else {
				page = superService.findByPage(clazz, condition, pageSize);
			}
		} catch(Exception e) {
			logger.error("search object "+ clazz.getName() +" error: "+ e.getMessage(), e);
			throw new SearchFailureException();// TODO
		}
		return page;
	}

	@Override
	public Page<T> findByPage(Class<T> clazz, Page<T> page) throws SearchFailureException {
		return this.findByPage(clazz, page, Boolean.TRUE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<T> findByPage(Class<T> clazz, Page<T> page, Boolean useCache) throws SearchFailureException {
		Page<T> nextPage = null;
		try {
			if(useCache) {
				String key = this.generateKey(clazz, page.getSearchMap(), page.getOrderBy(), page.getCurrentPage());
				nextPage = redisService.exists(key) ? (Page<T>)redisService.get(key) : null;
				if(nextPage == null) {
					nextPage = superService.findByPage(clazz, page);
					redisService.set(key, nextPage);
				}
			} else {
				nextPage = superService.findByPage(clazz, page);
			}
		} catch(Exception e) {
			logger.error("search object "+ clazz.getName() +" error: "+ e.getMessage(), e);
			throw new SearchFailureException();// TODO
		}
		return nextPage;
	}

	@Override
	public T findById(Class<T> clazz, Long id) throws SearchFailureException {
		return this.findById(clazz, id, Boolean.TRUE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findById(Class<T> clazz, Long id, Boolean useCache) throws SearchFailureException {
		T obj = null;
		try {
			if(useCache) {
				String key = this.generateKey(clazz, null, null, null) + id;
				obj = redisService.exists(key) ? (T)redisService.get(key) : null;
				if(obj == null) {
					obj = superService.findById(clazz, id);
					redisService.set(key, obj);
				}
			} else {
				obj = superService.findById(clazz, id);
			}
		} catch(Exception e) {
			logger.error("search object "+ clazz.getName() +"{id: "+ id +"} error: "+ e.getMessage(), e);
			throw new SearchFailureException();// TODO
		}
		return obj;
	}

	@Override
	public List<T> findByList(Class<T> clazz, WhereCondition condition) throws SearchFailureException {
		return this.findByList(clazz, condition, Boolean.TRUE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByList(Class<T> clazz, WhereCondition condition, Boolean useCache)
			throws SearchFailureException {
		List<T> lists = null;
		try {
			if(useCache) {
				String key = this.generateKey(clazz, condition.getSearchMap(), condition.getOrderBy(), null);
				lists = redisService.exists(key) ? (List<T>)redisService.get(key) : null;
				if(lists == null) {
					lists = superService.findByList(clazz, condition);
					redisService.set(key, lists);
				}
			} else {
				lists = superService.findByList(clazz, condition);
			}
		} catch(Exception e) {
			logger.error("search object "+ clazz.getName() +" error: "+ e.getMessage(), e);
			throw new SearchFailureException();// TODO
		}
		return lists;
	}

	@Override
	public List<T> findByList(Class<T> clazz, String fieldName, Boolean isIn, Object... fieldValues)
			throws SearchFailureException {
		return this.findByList(clazz, fieldName, isIn, Boolean.TRUE, fieldValues);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByList(Class<T> clazz, String fieldName, Boolean isIn, Boolean useCache, Object... fieldValues)
			throws SearchFailureException {
		List<T> lists = null;
		try {
			if(useCache) {
				String key = this.generateKey(clazz, null, null, null) + fieldName +"["+ fieldValues +"]";
				lists = redisService.exists(key) ? (List<T>)redisService.get(key) : null;
				if(lists == null) {
					lists = superService.findByList(clazz, fieldName, isIn, fieldValues);
					redisService.set(key, lists);
				}
			} else {
				lists = superService.findByList(clazz, fieldName, isIn, fieldValues);
			}
		} catch(Exception e) {
			logger.error("search object "+ clazz.getName() +" error: "+ e.getMessage(), e);
			throw new SearchFailureException();// TODO
		}
		return lists;
	}

	@Override
	public Boolean set(String key, Object value) throws SaveFailureException {
		return redisService.set(key, value);
	}

	@Override
	public Boolean set(String key, Object value, Long expireTime) throws SaveFailureException {
		return redisService.set(key, value, expireTime);
	}

	@Override
	public Boolean setBatch(String key, Object... values) throws SaveFailureException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean setBatch(String key, Long expireTime, Object... values) throws SaveFailureException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean del(String key) throws DeleteFailureException {
		Boolean result = Boolean.FALSE;
		try {
			redisService.remove(key);
			result = Boolean.TRUE;
		} catch(Exception e) {
			logger.error("delete cache{key: "+ key +"} error: "+ e.getMessage(), e);
			throw new DeleteFailureException();// TODO
		}
		return result;
	}

	@Override
	public Boolean delBatch(String... keys) throws DeleteFailureException {
		Boolean result = Boolean.FALSE;
		try {
			redisService.remove(keys);
			result = Boolean.TRUE;
		} catch(Exception e) {
			logger.error("delete cache({keys: "+ keys +"} error: "+ e.getMessage(), e);
			throw new DeleteFailureException();// TODO
		}
		return result;
	}

	@Override
	public Boolean delBatch(String pattern) throws DeleteFailureException {
		Boolean result = Boolean.FALSE;
		try {
			redisService.removeByPattern(pattern);
			result = Boolean.TRUE;
		} catch(Exception e) {
			logger.error("delete cache{keyPattern: "+ pattern +"} error: "+ e.getMessage(), e);
			throw new DeleteFailureException();// TODO
		}
		return result;
	}

	@Override
	public Boolean update(String key, Object value) throws UpdateFailureException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(String key) throws SearchFailureException {
		Object obj = null;
		try {
			obj = redisService.get(key);
		} catch(Exception e) {
			logger.error("search cache(key: "+ key +"} error: "+ e.getMessage(), e);
			throw new SearchFailureException();// TODO
		}
		return obj;
	}
	
	private String generateKey(Class<T> clazz, Map<String, Object> searchMap, List<String> orderBy, Long currentPage) {
		StringBuffer sb = new StringBuffer();
		if(clazz.isAnnotationPresent(Table.class)) {
			sb.append(clazz.getAnnotation(Table.class).value());
		} else {
			sb.append(clazz.getName());
		}
		sb.append(":");
		if(searchMap != null && searchMap.size() > 0) {
			sb.append("searchMap[");
			for(String key : searchMap.keySet()) {
				sb.append(key);
				sb.append(searchMap.get(key).toString());
			}
			sb.append("]");
		}
		if(orderBy != null && orderBy.size() > 0) {
			sb.append("orderBy[");
			for(String str : orderBy) {
				sb.append(str);
			}
			sb.append("]");
		}
		if(currentPage != null) {
			sb.append("currentPage["+ currentPage +"]");
		}
		return sb.toString();
	}

}
