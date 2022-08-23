package com.sunnyws.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunnyws.annotation.DictMethod;
import com.sunnyws.entity.Result;
import com.sunnyws.entity.UserEntity;
import com.sunnyws.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
- @Description: 用户表  Controller
- @author generator
- @since 2020-07-10 11:26:07
*/

 
@Slf4j
@Api(tags = "用户表")
@RestController
@RequestMapping("/user")
public class UserController {

   
    @Autowired
    private UserService userService;

    /**
     * 分页列表查询
     * @param user
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "用户表-分页列表查询", notes = "用户表-分页列表查询")
    @GetMapping(value = "/list")
	@DictMethod
    public Result<IPage<UserEntity>> queryPageList(UserEntity user,
                                                   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                   HttpServletRequest req) {
        Result<IPage<UserEntity>> result = new Result<IPage<UserEntity>>();
		QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<UserEntity>();
		Page<UserEntity> page = new Page<UserEntity>(pageNo, pageSize);
        IPage<UserEntity> pageList = userService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }
    

    /**
    * 通过id查询
    * @param id
    * @return
    */
    @ApiOperation(value = "用户表-通过id查询", notes = "用户表-通过id查询")
    @GetMapping(value = "/queryById")
	@DictMethod
    public Result<UserEntity> queryById(@RequestParam(name="id",required=true) String id) {
		Result<UserEntity> result = new Result<UserEntity>();
		UserEntity user = userService.getById(id);
		if(user==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(user);
			result.setSuccess(true);
		}
		return result;
	}
	
	/**
	 *   添加
	 * @param user
	 * @return
	 */
	@ApiOperation(value = "用户表- 添加", notes = "用户表- 添加")
	@PostMapping(value = "/add")
	public Result<UserEntity> add(@RequestBody UserEntity user) {
		Result<UserEntity> result = new Result<UserEntity>();
		try {
			userService.save(user);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	 *  修改
	 * @param user
	 * @return
	 */
	@ApiOperation(value = "用户表-修改", notes = "用户表- 修改")
	@PutMapping(value = "/edit")
	public Result<UserEntity> edit(@RequestBody UserEntity user) {
		Result<UserEntity> result = new Result<UserEntity>();
		UserEntity qureyResult = userService.getById(user.getId());
		if(qureyResult==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = userService.updateById(user);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}

		return result;
	}
	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@DictMethod
	@ApiOperation(value = "用户表-通过id删除", notes = "用户表- 通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<UserEntity> delete(@RequestParam(name="id",required=true) String id) {
		Result<UserEntity> result = new Result<UserEntity>();
		UserEntity user = userService.getById(id);
		if(user==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok =  userService.removeById(id);
			if(ok) {
				result.success("删除成功!");
			}
		}

		return result;
	}
	/**
	 *  批量删除
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "用户表-批量删除", notes = "用户表- 批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<UserEntity> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<UserEntity> result = new Result<UserEntity>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this. userService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}

}
