package com.rocoinfo.rest.sale.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rocoinfo.common.BaseController;
import com.rocoinfo.dto.StatusBootTableDto;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.sale.account.Permission;
import com.rocoinfo.entity.sale.account.Role;
import com.rocoinfo.service.sale.account.RoleService;

/**
 * <dl>
 * <dd>Description: 角色相关的接口</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/13 13:54</dd>
 * <dd>@author：Kong</dd>
 * </dl>
 */
@RestController
@RequestMapping("/api/roles")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 列表
     */
    @RequestMapping(method = RequestMethod.GET)
    public Object search(@RequestParam(required = false) String keyword,
                         @RequestParam(defaultValue = "0") int offset,
                         @RequestParam(defaultValue = "20") int limit,
                         @RequestParam(defaultValue = "id") String orderColumn,
                         @RequestParam(defaultValue = "DESC") String orderSort) {
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(keyword)) {
            params.put("keyword", keyword);
        }
        PageRequest pageable = new PageRequest(offset, limit, new Sort(Sort.Direction.valueOf(orderSort.toUpperCase()), orderColumn));

        Page<Role> page = roleService.searchScrollPage(params, pageable);

        return StatusBootTableDto.buildDataSuccessStatusDto(page);
    }

    /**
     * 根据id查询角色信息
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object get(@PathVariable("id") Long id) {
        if (id == null) {
            return StatusDto.buildDataFailureStatusDto("角色ID无效！");
        }
        StatusDto res = StatusDto.buildSuccessStatusDto();
        res.setData(roleService.getById(id));
        return res;
    }

    /**
     * 保存/修改角色
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object saveRole(Role role) {
        if (StringUtils.isBlank(role.getName())) {
            return StatusDto.buildDataFailureStatusDto("角色名称为空！");
        }
        try {
            return roleService.saveOrUpdate(role);
        } catch (Exception e) {
            e.printStackTrace();
            return StatusDto.buildDataFailureStatusDto("保存失败！");
        }
    }

    /**
     * 删除角色
     */
    @RequestMapping(value = "/{id}/del", method = RequestMethod.GET)
    public Object delete(@PathVariable("id") Long id) {
        try {
            return this.roleService.deleteRole(id);
        } catch (Exception e) {
            return StatusDto.buildDataFailureStatusDto("删除失败！");
        }

    }

    /**
     * 查询角色绑定的权限及未绑定权限
     */
    @RequestMapping(value = "/findRolePermission/{id}", method = RequestMethod.GET)
    public Object findRolePermission(@PathVariable("id") Long id) {
        Map<String, List<Permission>> modulePermListMap = new LinkedHashMap<String, List<Permission>>();
        List<Permission> allPermission = this.roleService.findRolePermission(id);
        for (Permission perm : allPermission) {
            String module = perm.getModule();

            List<Permission> permList = modulePermListMap.get(module);
            if (permList == null) {
                permList = new ArrayList<Permission>();
                modulePermListMap.put(module, permList);
            }
            permList.add(perm);
        }

        return StatusDto.buildDataSuccessStatusDto("success", modulePermListMap);
    }

    /**
     * 角色绑定权限
     */
    @RequestMapping(value = "/rolepermission", method = RequestMethod.POST)
    public Object setUserRole(@RequestParam("roleId") Long roleId, @RequestParam("permissions[]") List<Long> permissions) {
        if (roleId == null)
            return StatusDto.buildDataFailureStatusDto("角色id为空！");
        if (permissions == null || permissions.size() == 0)
            return StatusDto.buildDataFailureStatusDto("角色为空！");
        return roleService.insertRolePermission(roleId, permissions);
    }
}
