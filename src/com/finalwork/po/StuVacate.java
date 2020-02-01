package com.finalwork.po;

public class StuVacate {
    private String stu_id;
    private String vacate_id;
    private Integer status;

    public StuVacate() {
        status = 0;
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getVacate_id() {
        return vacate_id;
    }

    public void setVacate_id(String vacate_id) {
        this.vacate_id = vacate_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "StuVacate{" +
                "stu_id='" + stu_id + '\'' +
                ", vacate_id='" + vacate_id + '\'' +
                ", status=" + status +
                '}';
    }
}
