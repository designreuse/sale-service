package cn.damei.rest.sale.question;

import cn.damei.common.BaseController;
import cn.damei.dto.StatusBootTableDto;
import cn.damei.entity.sale.dameiorganization.DameiOrganization;
import cn.damei.entity.sale.question.OrgQuestion;
import cn.damei.enumeration.oa.DEPTYPE;
import cn.damei.service.sale.dameiorganization.DameiOrganizationService;
import cn.damei.service.sale.question.OrgQuestionService;
import cn.damei.utils.WebUtils;
import cn.damei.dto.StatusDto;
import com.rocoinfo.weixin.util.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/question")
@SuppressWarnings("all")
public class OrgQuestionController extends BaseController {
	@Autowired
    OrgQuestionService orgQuestionService;
	@Autowired
    DameiOrganizationService dameiOrganizationService;

	/**
	 * 根据责任部门的id 查询列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Object search(@RequestParam(required = false) Long orgId, @RequestParam(required = false) Long companyId,
			@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "20") int limit,
			@RequestParam(defaultValue = "id") String orderColumn,
			@RequestParam(defaultValue = "DESC") String orderSort) {
		Map<String, Object> map = MapUtils.of("orgId", orgId);
		map.put("companyId", companyId);
		PageRequest pageable = new PageRequest(offset, limit,
				new Sort(Sort.Direction.valueOf(orderSort.toUpperCase()), orderColumn));

		Page<OrgQuestion> orgQuestions = orgQuestionService.searchScrollPage(map, pageable);
		if (orgQuestions == null) {
			return StatusBootTableDto.buildDataSuccessStatusDto();
		}
		return StatusBootTableDto.buildDataSuccessStatusDto(orgQuestions);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public Object delete(@RequestParam long id) {
		try {
			orgQuestionService.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return StatusDto.buildDataFailureStatusDto("删除失败！");
		}
		return StatusDto.buildDataSuccessStatusDto("删除成功");
	}

	/**
	 * 添加
	 * 
	 * @param orgQuestion
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Object add(@RequestBody OrgQuestion orgQuestion) {
		try {
			// 校验是否已经重复添加了
			boolean result = orgQuestionService.checkRepeat(orgQuestion);
			if (result) {
				return StatusDto.buildDataFailureStatusDto("该问题类型已经被添加,请勿重复添加!");
			}
			orgQuestion.setCreateTime(new Date());
			orgQuestion.setCreateUser(WebUtils.getLoggedUser().getId());
			orgQuestionService.insert(orgQuestion);
			return StatusDto.buildDataSuccessStatusDto("添加成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return StatusDto.buildDataFailureStatusDto("添加失败！");
		}
	}

	/**
	 * 查询责任部门
	 * 
	 * @return
	 */
	@RequestMapping(value = "/orgResponsibility", method = RequestMethod.GET)
	public Object findOrgResponsibility(@RequestParam(required = false) Long companyId) {
		List<DameiOrganization> all = dameiOrganizationService.findAll();
		// 过滤责任部门
		List<DameiOrganization> collect;
		if (companyId != null) {
			collect = all.stream()
					.filter(dameiOrganization -> dameiOrganization.getDepType() != null
							&& DEPTYPE.LIABLEDEPARTMENT.equals(dameiOrganization.getDepType())
							&& dameiOrganization.getParentId().equals(companyId))
					.collect(Collectors.toList());
		} else {
			collect = all.stream()
					.filter(dameiOrganization -> dameiOrganization.getDepType() != null
							&& DEPTYPE.LIABLEDEPARTMENT.equals(dameiOrganization.getDepType()))
					.collect(Collectors.toList());
		}
		return StatusDto.buildDataSuccessStatusDto(collect);
	}

	/**
	 * 根据部门查问题
	 * 
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/findOrgQuestion/{orgId}")
	public Object findOrgQuestion(@PathVariable Long orgId) {
		List<Map<String, Object>> findOrgQuestion = this.orgQuestionService.findOrgQuestion(orgId);
		return StatusDto.buildDataSuccessStatusDto(findOrgQuestion);
	}
	/**
	 * 根据部门查问题
	 * 
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/findQuestionType/{orgId}")
	public Object findQuestionType(@PathVariable Long orgId) {
		List<Map<String, Object>> findOrgQuestion = this.orgQuestionService.findOrgQuestion(orgId);
		return StatusDto.buildDataSuccessStatusDto(findOrgQuestion);
	}

}
