package com.kim.log.bean;

/**
 * 错误日志
 */
public class AppErrorLog {
    public String getErrorBrief() {
        return errorBrief;
    }

    public void setErrorBrief(String errorBrief) {
        this.errorBrief = errorBrief;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    private String errorBrief; //错误摘要
    private String errorDetail; //错误详情


}
