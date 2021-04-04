package com.CaptureRx.demo.Models;


import com.CaptureRx.demo.Entities.Status;

public class JobStatusResponse {

    private String errorMessage;
    private boolean success;
    private long jobId = -1;
    private Status status;

    public JobStatusResponse(){
        this.success = true;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }
}
