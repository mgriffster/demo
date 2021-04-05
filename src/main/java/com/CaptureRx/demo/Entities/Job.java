package com.CaptureRx.demo.Entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "job")
public class Job {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="job_id_sequence")
    @SequenceGenerator(name="job_id_sequence", sequenceName = "job_job_id_seq", allocationSize = 1)
    private long jobId;

    @ManyToOne
    @JoinColumn(name="status_id")
    private Status status;

    private LocalDate dateCreated;

    public Job(){}

    public Job(Status status){
        this.status = status;
        this.dateCreated = LocalDateTime.now().toLocalDate();
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString(){
        return "ID:" + this.jobId + " Status:" + this.status.getName() + " Created:"+dateCreated.toString();
    }
}
