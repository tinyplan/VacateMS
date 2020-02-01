package com.finalwork.po;

/**
 * 请假记录PLUS
 */
public class VacateVO {
    private String name;
    private Vacate vacate;
    private Integer step;
    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vacate getVacate() {
        return vacate;
    }

    public void setVacate(Vacate vacate) {
        this.vacate = vacate;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
