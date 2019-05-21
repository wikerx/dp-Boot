package com.scott.dp.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * InnoDB free: 23552 kB
 * @author Mr.Ñ¦<*****@163.com>
 */
public class SysFileEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 文件编号
	 */
	private Long fileId;
	
	/**
	 * 文件名称
	 */
	private String fileName;
	
	/**
	 * 文件位置
	 */
	private String fileAdd;
	
	/**
	 * 上传者编号
	 */
	private Long fileUpId;
	
	/**
	 * 上传说明
	 */
	private String remark;
	
	/**
	 * 上传时间
	 */
	private Date upTime;
	
	/**
	 * 文件类型 1:word
	 */
	private Integer fileType;
	
	/**
	 * 新文件名称
	 */
	private String fileNewName;
	
    /**
     * SysFileEntity constructor
     */
	public SysFileEntity() {
		super();
	}

    /**
     * setter for fileId
     * @param fileId
     */
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

    /**
     * getter for fileId
     */
	public Long getFileId() {
		return fileId;
	}

    /**
     * setter for fileName
     * @param fileName
     */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

    /**
     * getter for fileName
     */
	public String getFileName() {
		return fileName;
	}

    /**
     * setter for fileAdd
     * @param fileAdd
     */
	public void setFileAdd(String fileAdd) {
		this.fileAdd = fileAdd;
	}

    /**
     * getter for fileAdd
     */
	public String getFileAdd() {
		return fileAdd;
	}

    /**
     * setter for fileUpId
     * @param fileUpId
     */
	public void setFileUpId(Long fileUpId) {
		this.fileUpId = fileUpId;
	}

    /**
     * getter for fileUpId
     */
	public Long getFileUpId() {
		return fileUpId;
	}

    /**
     * setter for remark
     * @param remark
     */
	public void setRemark(String remark) {
		this.remark = remark;
	}

    /**
     * getter for remark
     */
	public String getRemark() {
		return remark;
	}

    /**
     * setter for upTime
     * @param upTime
     */
	public void setUpTime(Date upTime) {
		this.upTime = upTime;
	}

    /**
     * getter for upTime
     */
	public Date getUpTime() {
		return upTime;
	}

    /**
     * setter for fileType
     * @param fileType
     */
	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

    /**
     * getter for fileType
     */
	public Integer getFileType() {
		return fileType;
	}

    /**
     * setter for fileNewName
     * @param fileNewName
     */
	public void setFileNewName(String fileNewName) {
		this.fileNewName = fileNewName;
	}

    /**
     * getter for fileNewName
     */
	public String getFileNewName() {
		return fileNewName;
	}

    /**
     * SysFileEntity.toString()
     */
    @Override
    public String toString() {
        return "SysFileEntity{" +
               "fileId='" + fileId + '\'' +
               ", fileName='" + fileName + '\'' +
               ", fileAdd='" + fileAdd + '\'' +
               ", fileUpId='" + fileUpId + '\'' +
               ", remark='" + remark + '\'' +
               ", upTime='" + upTime + '\'' +
               ", fileType='" + fileType + '\'' +
               ", fileNewName='" + fileNewName + '\'' +
               '}';
    }

}
