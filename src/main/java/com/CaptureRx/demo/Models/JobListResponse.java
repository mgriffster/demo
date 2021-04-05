package com.CaptureRx.demo.Models;

import com.CaptureRx.demo.Entities.Job;

import java.util.List;

public class JobListResponse {
    private boolean success;
    private String errorMessage;
    private List<Job> jobs;

    public JobListResponse(){
        this.success = true;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJob(List<Job> jobs) {
        this.jobs = jobs;
    }
}
