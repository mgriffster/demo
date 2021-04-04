package com.CaptureRx.demo;

import com.CaptureRx.demo.Entities.Job;
import com.CaptureRx.demo.Entities.Repositories.JobRepository;
import com.CaptureRx.demo.Entities.Repositories.StatusRepository;
import com.CaptureRx.demo.Entities.Status;
import com.CaptureRx.demo.Models.CreateJobResponse;
import com.CaptureRx.demo.Models.JobStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EntityScan("com/CaptureRx/demo/Entities")
public class JobProcessor {
    private JobRepository jobRepository;

    private StatusRepository statusRepository;


    @Autowired
    public JobProcessor(JobRepository jobRepository, StatusRepository statusRepository){
        this.jobRepository = jobRepository;
        this.statusRepository = statusRepository;
    }


    @RequestMapping(value = "/createnewjob")
    public CreateJobResponse createNewJob(){
        CreateJobResponse response = new CreateJobResponse();
        try{
            //Create and insert new job record
            Status status = statusRepository.findByStatusId(1);
            Job job = new Job(status);
            jobRepository.save(job);

            //Add to Kafka for processing

            response.setJobId(job.getJobId());
        }catch(Exception e){
            response.setErrorMessage(e.toString());
            response.setSuccess(false);
        }

        return response;
    }

    @RequestMapping(value = "/getjobstatus")
    public JobStatusResponse getJobStatus(@RequestParam long jobId){
        JobStatusResponse response = new JobStatusResponse();
        try{
            Job job = jobRepository.findByJobId(jobId);
            response.setJobId(job.getJobId());
            response.setStatus(job.getStatus());
        }catch(Exception e){
            response.setSuccess(false);
            response.setErrorMessage(e.toString());
        }

        return response;
    }

}
