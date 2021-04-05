package com.CaptureRx.demo.Models;


import com.CaptureRx.demo.Entities.Job;

public class SingleJobResponse {

    private String errorMessage;
    private boolean success;
    private Job job;

    public SingleJobResponse(){
        this.success = true;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
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

}
