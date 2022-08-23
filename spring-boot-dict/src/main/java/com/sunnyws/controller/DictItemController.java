package com.sunnyws.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunnyws.entity.DictItemEntity;
import com.sunnyws.entity.Result;
import com.sunnyws.service.DictItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
- @Description:   Controller
- @author generator
- @since 2020-07-10 11:22:59
*/

 
@Slf4j
@Api(tags = "字典属性表")
@RestController
@RequestMapping("/dictItem")
public class DictItemController {

   
    @Autowired
    private DictItemService dictItemService;

    /**
     * 分页列表查询
     * @param dictItem
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "字典属性表-分页列表查询", notes = "字典属性表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<DictItemEntity>> queryPageList(DictItemEntity dictItem,
                                                       @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                       @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                       HttpServletRequest req) {
        Result<IPage<DictItemEntity>> result = new Result<IPage<DictItemEntity>>();
        QueryWrapper<DictItemEntity> queryWrapper = new QueryWrapper<DictItemEntity>();
        Page<DictItemEntity> page = new Page<DictItemEntity>(pageNo, pageSize);
        IPage<DictItemEntity> pageList = dictItemService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }
    

    /**
    * 通过id查询+.
	 *
    * @param id
    * @return
    */
    @ApiOperation(value = "字典属性表-通过id查询", notes = "字典属性表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<DictItemEntity> queryById(@RequestParam(name="id",required=true) String id) {
		Result<DictItemEntity> result = new Result<DictItemEntity>();
		DictItemEntity dictItem = dictItemService.getById(id);
		if(dictItem==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(dictItem);
			result.setSuccess(true);
		}
		return result;
	}
	
	/**
	 *   添加
	 * @param dictItem
	 * @return
	 */
	@ApiOperation(value = "字典属性表- 添加", notes = "字典属性表- 添加")
	@PostMapping(value = "/add")
	public Result<DictItemEntity> add(@RequestBody DictItemEntity dictItem) {
		Result<DictItemEntity> result = new Result<DictItemEntity>();
		try {
			dictItemService.save(dictItem);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	 *  修改
	 * @param dictItem
	 * @return
	 */
	@ApiOperation(value = "字典属性表-修改", notes = "字典属性表- 修改")
	@PutMapping(value = "/edit")
//	@CacheEvict(value = CommonConstant.DICT_CACHE)
	public Result<DictItemEntity> edit(@RequestBody DictItemEntity dictItem) {
		Result<DictItemEntity> result = new Result<DictItemEntity>();
		DictItemEntity qureyResult = dictItemService.getById(dictItem.getId());
		if(qureyResult==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = dictItemService.updateById(dictItem);
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
	@ApiOperation(value = "字典属性表-通过id删除", notes = "字典属性表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<DictItemEntity> delete(@RequestParam(name="id",required=true) String id) {
		Result<DictItemEntity> result = new Result<DictItemEntity>();
		DictItemEntity dictItem = dictItemService.getById(id);
		if(dictItem==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok =  dictItemService.removeById(id);
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
	@ApiOperation(value = "字典属性表-批量删除", notes = "字典属性表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<DictItemEntity> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<DictItemEntity> result = new Result<DictItemEntity>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this. dictItemService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}

}
