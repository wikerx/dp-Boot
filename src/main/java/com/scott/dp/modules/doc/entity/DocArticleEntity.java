package com.scott.dp.modules.doc.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * InnoDB free: 206848 kB
 * @author Mr.薛<*****@163.com>
 */
public class DocArticleEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 文章序号
	 */
	private Long id;
	
	/**
	 * 文章标题
	 */
	private String title;
	
	/**
	 * 发布时间
	 */
	private String createTime;
	
	/**
	 * 修改时间
	 */
	private String updateTime;
	
	/**
	 * 发布人
	 */
	private String creator;
	
	/**
	 * 类目
	 */
	private String category;
	
	/**
	 * 浏览量
	 */
	private Integer clicks;
	
	/**
	 * 状态（0：待审核 1：开放浏览 2：审核不通过 3：文章下架 4：只读）
	 */
	private Integer status;
	
	/**
	 * 标签属性
	 */
	private String tag;

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
	 * 所属分类
	 */
	private String type;
	
	/**
	 * 综合属性(0：普通 1：小人气 2：人气榜 3：热门聚焦)
	 */
	private Integer attribute;
	
	/**
	 * 内容
	 */
	private String content;
	
    /**
     * DocArticleEntity constructor
     */
	public DocArticleEntity() {
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
     * setter for updateTime
     * @param updateTime
     */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

    /**
     * getter for updateTime
     */
	public String getUpdateTime() {
		return updateTime;
	}

    /**
     * setter for creator
     * @param creator
     */
	public void setCreator(String creator) {
		this.creator = creator;
	}

    /**
     * getter for creator
     */
	public String getCreator() {
		return creator;
	}

    /**
     * setter for category
     * @param category
     */
	public void setCategory(String category) {
		this.category = category;
	}

    /**
     * getter for category
     */
	public String getCategory() {
		return category;
	}

    /**
     * setter for clicks
     * @param clicks
     */
	public void setClicks(Integer clicks) {
		this.clicks = clicks;
	}

    /**
     * getter for clicks
     */
	public Integer getClicks() {
		return clicks;
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
     * setter for tag
     * @param tag
     */
	public void setTag(String tag) {
		this.tag = tag;
	}

    /**
     * getter for tag
     */
	public String getTag() {
		return tag;
	}

    /**
     * setter for type
     * @param type
     */
	public void setType(String type) {
		this.type = type;
	}

    /**
     * getter for type
     */
	public String getType() {
		return type;
	}

    /**
     * setter for attribute
     * @param attribute
     */
	public void setAttribute(Integer attribute) {
		this.attribute = attribute;
	}

    /**
     * getter for attribute
     */
	public Integer getAttribute() {
		return attribute;
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
     * DocArticleEntity.toString()
     */
    @Override
    public String toString() {
        return "DocArticleEntity{" +
               "id='" + id + '\'' +
               ", title='" + title + '\'' +
               ", createTime='" + createTime + '\'' +
               ", updateTime='" + updateTime + '\'' +
               ", creator='" + creator + '\'' +
               ", category='" + category + '\'' +
               ", clicks='" + clicks + '\'' +
               ", status='" + status + '\'' +
               ", tag='" + tag + '\'' +
               ", type='" + type + '\'' +
               ", attribute='" + attribute + '\'' +
               ", content='" + content + '\'' +
               '}';
    }

}
