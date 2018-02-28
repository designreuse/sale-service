package com.rocoinfo.entity.sale.workorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocoinfo.entity.IdEntity;

import java.util.Date;

/**
 * <dl>
 * <dd>Description: 审批预计完成时间 结果记录 POJO</dd>
 * <dd>Company: mdni</dd>
 * <dd>@date：2017-8-24 15:38:44</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
public class TreamentResult extends IdEntity {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    private Long createUser;

    /**
     * 审批结果  1 通过 2 驳回
     */
    private String approvalResult;

    /**
     * 审批结果 说明
     */
    private String remarks;

    private Long approvalId;
    /**
     * 工单id
     */

    private Long workorderId;
    /**
     * 操作人名字
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWorkorderId() {
        return workorderId;
    }

    public void setWorkorderId(Long workorderId) {
        this.workorderId = workorderId;
    }

    public Long getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(Long approvalId) {
        this.approvalId = approvalId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public String getApprovalResult() {
        return approvalResult;
    }

    public void setApprovalResult(String approvalResult) {
        this.approvalResult = approvalResult;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}