package com.ruoyi.student.domain.vo;

import com.ruoyi.student.domain.TargetPosition;

import java.util.Objects;

public class TargetPositionVO extends TargetPosition {

    /**总体完成率*/
    private String completionRate;

    /**本年度1-6月项目数*/
    private String CompletedQuantity1;


    /**本年度1-6月完成率*/
    private String completionRate1;


    /**本年度7-12月项目数*/
    private String CompletedQuantity2;

    /**本年度7-12月完成率*/
    private String completionRate2;

    public String getCompletionRate() {
        return completionRate;
    }

    public String getCompletedQuantity1() {
        return CompletedQuantity1;
    }

    public String getCompletionRate1() {
        return completionRate1;
    }

    public String getCompletedQuantity2() {
        return CompletedQuantity2;
    }

    public String getCompletionRate2() {
        return completionRate2;
    }

    public void setCompletionRate(String completionRate) {
        this.completionRate = completionRate;
    }

    public void setCompletedQuantity1(String completedQuantity1) {
        CompletedQuantity1 = completedQuantity1;
    }

    public void setCompletionRate1(String completionRate1) {
        this.completionRate1 = completionRate1;
    }

    public void setCompletedQuantity2(String completedQuantity2) {
        CompletedQuantity2 = completedQuantity2;
    }

    public void setCompletionRate2(String completionRate2) {
        this.completionRate2 = completionRate2;
    }

    @Override
    public String toString() {
        return "TargetPositionVO{" +
                "completionRate='" + completionRate + '\'' +
                ", CompletedQuantity1='" + CompletedQuantity1 + '\'' +
                ", completionRate1='" + completionRate1 + '\'' +
                ", CompletedQuantity2='" + CompletedQuantity2 + '\'' +
                ", completionRate2='" + completionRate2 + '\'' +
                '}';
    }

    public  TargetPositionVO(){

    }

    public TargetPositionVO(String completionRate, String completedQuantity1, String completionRate1, String completedQuantity2, String completionRate2) {
        this.completionRate = completionRate;
        this.CompletedQuantity1 = completedQuantity1;
        this.completionRate1 = completionRate1;
        this.CompletedQuantity2 = completedQuantity2;
        this.completionRate2 = completionRate2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TargetPositionVO that = (TargetPositionVO) o;
        return Objects.equals(completionRate, that.completionRate) && Objects.equals(CompletedQuantity1, that.CompletedQuantity1) && Objects.equals(completionRate1, that.completionRate1) && Objects.equals(CompletedQuantity2, that.CompletedQuantity2) && Objects.equals(completionRate2, that.completionRate2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(completionRate, CompletedQuantity1, completionRate1, CompletedQuantity2, completionRate2);
    }
}
