package com.scott.dp.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * InnoDB free: 9216 kB
 * @author Mr.Ñ¦<*****@163.com>
 */
public class SysDepartmentEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 编号
	 */
	private Long id;
	
	/**
	 * 部门名称
	 */
	private String name;
	
	/**
	 * 创建人id
	 */
	private Long createId;

	/**
	 * 创建人
	 */
	private String creater;

	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 部门描述信息
	 */
	private String discribe;
	
	/**
	 * 部门经理编号
	 */
	private Long managerId;

	/**
	 * 部门经理编号
	 */
	private String manager;
	/**
	 * 信息查看权限（经理看所有，其他人只能看自己）1：是 0：否
	 */
	private Integer check;
	
    /**
     * SysDepartmentEntity constructor
     */
	public SysDepartmentEntity() {
		super();
	}

    /**
     * setter for id
     * @param id
     */
	public void setId(Long id) {
		this.id = id;
	}

    /**
     * getter for id
     */
	public Long getId() {
		return id;
	}

    /**
     * setter for name
     * @param name
     */
	public void setName(String name) {
		this.name = name;
	}

    /**
     * getter for name
     */
	public String getName() {
		return name;
	}

    /**
     * setter for createId
     * @param createId
     */
	public void setCreateId(Long createId) {
		this.createId = createId;
	}

    /**
     * getter for createId
     */
	public Long getCreateId() {
		return createId;
	}

    /**
     * setter for createTime
     * @param createTime
     */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

    /**
     * getter for createTime
     */
	public Date getCreateTime() {
		return createTime;
	}

    /**
     * setter for discribe
     * @param discribe
     */
	public void setDiscribe(String discribe) {
		this.discribe = discribe;
	}

    /**
     * getter for discribe
     */
	public String getDiscribe() {
		return discribe;
	}

    /**
     * setter for managerId
     * @param managerId
     */
	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

    /**
     * getter for managerId
     */
	public Long getManagerId() {
		return managerId;
	}

    /**
     * setter for check
     * @param check
     */
	public void setCheck(Integer check) {
		this.check = check;
	}

    /**
     * getter for check
     */
	public Integer getCheck() {
		return check;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	/**
     * SysDepartmentEntity.toString()
     */
    @Override
    public String toString() {
        return "SysDepartmentEntity{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", createId='" + createId + '\'' +
               ", createTime='" + createTime + '\'' +
               ", discribe='" + discribe + '\'' +
               ", managerId='" + managerId + '\'' +
               ", check='" + check + '\'' +
				", manager='" + manager + '\'' +
				", creater='" + creater + '\'' +
               '}';
    }

}
