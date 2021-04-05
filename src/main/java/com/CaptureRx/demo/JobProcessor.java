package com.CaptureRx.demo;

import com.CaptureRx.demo.Entities.Job;
import com.CaptureRx.demo.Entities.Repositories.JobRepository;
import com.CaptureRx.demo.Entities.Repositories.StatusRepository;
import com.CaptureRx.demo.Entities.Status;
import com.CaptureRx.demo.Models.JobListResponse;
import com.CaptureRx.demo.Models.SingleJobResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@EntityScan("com/CaptureRx/demo/Entities")
//All logic is in this class to make it easier to review
public class JobProcessor {
    private JobRepository jobRepository;

    private StatusRepository statusRepository;

    private KafkaTemplate<String, Long> kafkaTemplate;


    @Autowired
    public JobProcessor(JobRepository jobRepository, StatusRepository statusRepository, KafkaTemplate<String, Long> kafkaTemplate){
        this.jobRepository = jobRepository;
        this.statusRepository = statusRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    //Listener for Kafka messages
    @KafkaListener(topics = "JobsTopic")
    public void listen(Long message) throws InterruptedException{

        //Move to in processing status when the message is received by the consumer
        Status nextStatus = statusRepository.findByStatusId(Status.IN_PROCESSING_STATUS);
        Job job = jobRepository.findByJobId(message);
        job.setStatus(nextStatus);
        jobRepository.saveAndFlush(job);
        System.out.println("PROCESSING JOB: " + message);

        //Sleep to emulate work being done
        Thread.sleep(5000);

        //Now that work is complete move the job to Done
        nextStatus = statusRepository.findByStatusId(Status.DONE_STATUS);
        job.setStatus(nextStatus);
        jobRepository.saveAndFlush(job);
        System.out.println("JOB COMPLETE:"+message);

    }


    /**
     * Generate a new job and persist it, then send the job to the Kafka Topic for consumption
     * Job should always be in "New" status (status_id 1) when created.
     * @return - New job ID
     */
    @RequestMapping(value = "/createnewjob")
    public SingleJobResponse createNewJob(){
        SingleJobResponse response = new SingleJobResponse();
        try{
            //Create and insert new job record
            Status status = statusRepository.findByStatusId(Status.NEW_STATUS);
            Job job = new Job(status);
            jobRepository.save(job);

            //Add to Kafka for processing
            kafkaTemplate.send("JobsTopic", job.getJobId());

            response.setJob(job);
        }catch(Exception e){
            response.setErrorMessage(e.toString());
            response.setSuccess(false);
        }

        return response;
    }

    /**
     * Returns the status of a job specified in the parameters
     * @param jobId - Job ID of existing job
     * @return - Job ID and status of the associated job
     */
    @RequestMapping(value = "/getjobstatus")
    public SingleJobResponse getJobStatus(@RequestParam long jobId){
        SingleJobResponse response = new SingleJobResponse();
        try{
            response.setJob(jobRepository.findByJobId(jobId));
        }catch(Exception e){
            response.setSuccess(false);
            response.setErrorMessage(e.toString());
        }

        return response;
    }

    /**
     * Gets the information for all jobs that have been created
     * @return - List of all jobs in the DB
     */
    @RequestMapping(value = "/getalljobstatus")
    public JobListResponse getAllJobStatus(){
        JobListResponse response = new JobListResponse();
        try{
            response.setJob(jobRepository.findAll(Sort.by(Sort.Direction.DESC, "jobId")));
        }catch(Exception e){
            response.setSuccess(false);
            response.setErrorMessage(e.toString());
        }

        return response;
    }

}
