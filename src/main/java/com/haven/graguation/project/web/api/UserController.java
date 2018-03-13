package com.haven.graguation.project.web.api;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.haven.graguation.project.domain.User;
import com.haven.graguation.project.repository.mybatis.service.Page;
import com.haven.graguation.project.repository.mybatis.service.WhereCondition;
import com.haven.graguation.project.repository.mybatis.service.impl.DefaultWhereCondition;
import com.haven.graguation.project.service.BaseService;
import com.haven.graguation.project.util.FileUtil;
import com.haven.graguation.project.web.exception.SourceModifyException;
import com.haven.graguation.project.web.exception.SourceNoContentException;
import com.haven.graguation.project.web.exception.SourceNotFoundException;
import com.haven.graguation.project.web.exception.SourceRemoveException;
import com.haven.graguation.project.web.exception.SourceSaveException;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private BaseService<User> userService;
	
	@ApiOperation(value="注册用户")
	@ApiImplicitParams({
		@ApiImplicitParam(name="user", value="用户实体对象user", required=false, dataType="User")
	})
	@PostMapping("user")
	public Boolean register(@RequestBody User user) {
		if(!userService.saveOne(user)) { throw new SourceSaveException(user); }
		return Boolean.TRUE;
	}
	
	@ApiOperation(value="上传文件")
	@ApiImplicitParams({
		@ApiImplicitParam(name="file", value="上传的文件", required=true, dataType="MultipartFile")
	})
	@PostMapping("/user/upload")
	public Boolean upload(@RequestPart("file")MultipartFile file) {
		Boolean result = Boolean.FALSE;
		try {
			logger.debug(file.getName());
			result = FileUtil.upload("user", file.getBytes(), file.getOriginalFilename());
		} catch(Exception e) {
			logger.error("file upload error: "+ e.getMessage(), e);
		}
		return result;
	}
	
	@GetMapping("/user/download/{fileName}")
	public void download(@RequestParam("filePath")String filePath, 
			@PathVariable("fileName")String fileName, HttpServletResponse response) {
		try {
			FileUtil.download(filePath, fileName, response);
		} catch(Exception e) {
			logger.error("file download error: "+ e.getMessage(), e);
		}
	}
	
	@GetMapping("/user/{id}")
	public User userById(@PathVariable Long id) {
		User user = userService.findById(User.class, id);
		if(user == null) { throw new SourceNotFoundException(id, User.class.getSimpleName()); }
		return user;
	}
	
	@DeleteMapping("user/{id}")
	public Boolean remove(@PathVariable Long id) {
		if(!userService.removeOne(User.class, id)) { throw new SourceRemoveException(id, User.class.getSimpleName()); }
		return Boolean.TRUE;
	}
	
	@PutMapping("user/{id}")
	public Boolean modify(@PathVariable Long id, @RequestBody User user) {
		if(!userService.update(user, id)) { throw new SourceModifyException(id, User.class.getSimpleName()); }
		return Boolean.TRUE;
	}

	@PostMapping("users")
	public Page<User> usersByCondition(@RequestBody User user) {
		Page<User> page = null;
		WhereCondition condition = new DefaultWhereCondition();
		condition.like("userName", user.getUserName());// 根据用户名模糊查询
		condition.orderByAsc("id");// 根据id进行升序排序
		page = userService.findByPage(User.class, condition);
		if(page == null || page.getTotalCount() < 1) { throw new SourceNoContentException(user); }
		return page;
	}
	
}
