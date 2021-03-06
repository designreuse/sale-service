package cn.damei.entity.sale.dict;

import cn.damei.entity.IdEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ProblemCatBrand extends IdEntity {
	
	private static final long serialVersionUID = 1L;

	private DameiDictionary questionType1;
	
	private Brand brand;
	
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    
    private Long createUser;
    
    

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public DameiDictionary getQuestionType1() {
		return questionType1;
	}

	public void setQuestionType1(DameiDictionary questionType1) {
		this.questionType1 = questionType1;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
}
