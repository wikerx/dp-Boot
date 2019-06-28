package com.scott.dp.modules.doc.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * InnoDB free: 206848 kB
 * @author Mr.薛<*****@163.com>
 */
public class DocNoticeEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 通告编号
	 */
	private Long id;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 发布者
	 */
	private Long creator;

	/**
	 * 发布人
	 */
	private String person;

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	/**
	 * 发布时间
	 */
	private String createTime;
	
	/**
	 * 截止时间
	 */
	private String endTime;
	
	/**
	 * 状态(0：结束 1：生效)
	 */
	private Integer status;
	
	/**
	 * 适用人群（N：不适用 Y：全员适用  其他表示适用的部门，以,分割）
	 */
	private String intendedFor;
	
	/**
	 * 
	 */
	private String content;
	
    /**
     * DocNoticeEntity constructor
     */
	public DocNoticeEntity() {
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
     * setter for title
     * @param title
     */
	public void setTitle(String title) {
		this.title = title;
	}

    /**
     * getter for title
     */
	public String getTitle() {
		return title;
	}

    /**
     * setter for creator
     * @param creator
     */
	public void setCreator(Long creator) {
		this.creator = creator;
	}

    /**
     * getter for creator
     */
	public Long getCreator() {
		return creator;
	}

    /**
     * setter for createTime
     * @param createTime
     */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

    /**
     * getter for createTime
     */
	public String getCreateTime() {
		return createTime;
	}

    /**
     * setter for endTime
     * @param endTime
     */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

    /**
     * getter for endTime
     */
	public String getEndTime() {
		return endTime;
	}

    /**
     * setter for status
     * @param status
     */
	public void setStatus(Integer status) {
		this.status = status;
	}

    /**
     * getter for status
     */
	public Integer getStatus() {
		return status;
	}

    /**
     * setter for intendedFor
     * @param intendedFor
     */
	public void setIntendedFor(String intendedFor) {
		this.intendedFor = intendedFor;
	}

    /**
     * getter for intendedFor
     */
	public String getIntendedFor() {
		return intendedFor;
	}

    /**
     * setter for content
     * @param content
     */
	public void setContent(String content) {
		this.content = content;
	}

    /**
     * getter for content
     */
	public String getContent() {
		return content;
	}

    /**
     * DocNoticeEntity.toString()
     */
    @Override
    public String toString() {
        return "DocNoticeEntity{" +
               "id='" + id + '\'' +
               ", title='" + title + '\'' +
               ", creator='" + creator + '\'' +
               ", createTime='" + createTime + '\'' +
               ", endTime='" + endTime + '\'' +
               ", status='" + status + '\'' +
               ", intendedFor='" + intendedFor + '\'' +
               ", content='" + content + '\'' +
               '}';
    }

}
