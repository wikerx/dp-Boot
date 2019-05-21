package com.scott.dp.modules.sys.entity;

import java.util.Date;

/**
 * @CLASSNAME :ISysFileEntity
 * @Description :DOTO
 * @Author :Mr.薛
 * @Data :2019/5/21  16:42
 * @Version :V1.0
 * @Status : 编写
 **/
public class ISysFileEntity {
    private Long file_id;
    private String file_name;
    private String file_add;
    private Long file_up_id;
    private String remark;
    private Date up_time;
    private int file_type;
    private String file_new_name;

    public Long getFile_id() {
        return file_id;
    }

    public void setFile_id(Long file_id) {
        this.file_id = file_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_add() {
        return file_add;
    }

    public void setFile_add(String file_add) {
        this.file_add = file_add;
    }

    public Long getFile_up_id() {
        return file_up_id;
    }

    public void setFile_up_id(Long file_up_id) {
        this.file_up_id = file_up_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getUp_time() {
        return up_time;
    }

    public void setUp_time(Date up_time) {
        this.up_time = up_time;
    }

    public int getFile_type() {
        return file_type;
    }

    public void setFile_type(int file_type) {
        this.file_type = file_type;
    }

    public String getFile_new_name() {
        return file_new_name;
    }

    public void setFile_new_name(String file_new_name) {
        this.file_new_name = file_new_name;
    }
}
