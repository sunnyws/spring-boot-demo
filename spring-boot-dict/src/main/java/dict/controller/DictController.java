package dict.controller;

import dict.entity.DictEntity;
import dict.entity.Result;
import dict.service.DictService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
- @since 2020-07-10 11:22:05
*/


@Slf4j
@Api(tags = "字典表")
@RestController
@RequestMapping("/dict")
public class DictController {


    @Autowired
    private DictService  dictService;

    /**
     * 分页列表查询
     * @param dict
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "字典表-分页列表查询", notes = "字典表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<DictEntity>> queryPageList(DictEntity dict,
												   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        Result<IPage<DictEntity>> result = new Result<IPage<DictEntity>>();
		QueryWrapper<DictEntity> queryWrapper = new QueryWrapper<DictEntity>();
        Page<DictEntity> page = new Page<DictEntity>(pageNo, pageSize);
        IPage<DictEntity> pageList = dictService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }


    /**
    * 通过id查询
    * @param id
    * @return
    */
    @ApiOperation(value = "字典表-通过id查询", notes = "字典表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<DictEntity> queryById(@RequestParam(name="id",required=true) String id) {
		Result<DictEntity> result = new Result<DictEntity>();
		DictEntity dict = dictService.getById(id);
		if(dict==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(dict);
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 *   添加
	 * @param dict
	 * @return
	 */
	@ApiOperation(value = "字典表- 添加", notes = "字典表- 添加")
	@PostMapping(value = "/add")
	public Result<DictEntity> add(@RequestBody DictEntity dict) {
		Result<DictEntity> result = new Result<DictEntity>();
		try {
			dictService.save(dict);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}

	/**
	 *  修改
	 * @param dict
	 * @return
	 */
	@ApiOperation(value = "字典表-修改", notes = "字典表- 修改")
	@PutMapping(value = "/edit")
	public Result<DictEntity> edit(@RequestBody DictEntity dict) {
		Result<DictEntity> result = new Result<DictEntity>();
		DictEntity qureyResult = dictService.getById(dict.getId());
		if(qureyResult==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = dictService.updateById(dict);
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
	@ApiOperation(value = "字典表-通过id删除", notes = "字典表- 通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<DictEntity> delete(@RequestParam(name="id",required=true) String id) {
		Result<DictEntity> result = new Result<DictEntity>();
		DictEntity dict = dictService.getById(id);
		if(dict==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok =  dictService.removeById(id);
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
	@ApiOperation(value = "字典表-批量删除", notes = "字典表- 批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<DictEntity> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<DictEntity> result = new Result<DictEntity>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this. dictService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}

}
