package com.finalwork.po;

import com.finalwork.utils.format.DataFormat;

public class Vacate {
    private String vacate_id;
    private String stu_id;
    private String term;
    private String submit_time;
    private String start_time;
    private String end_time;
    private Integer period;
    private String type;
    private String result;
    private String course_list;

    public String getVacate_id() {
        return vacate_id;
    }

    public void setVacate_id(String vacate_id) {
        this.vacate_id = vacate_id;
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(String submit_time) {
        this.submit_time = submit_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCourse_list() {
        return course_list;
    }

    public void setCourse_list(String course_list) {
        this.course_list = course_list;
    }

    public void formatDate(){
        this.setSubmit_time(DataFormat.parseToDate("yyyy-MM-dd HH:mm:ss",this.submit_time));
        this.setStart_time(DataFormat.parseToDate(this.start_time));
        this.setEnd_time(DataFormat.parseToDate(this.end_time));
    }
}
