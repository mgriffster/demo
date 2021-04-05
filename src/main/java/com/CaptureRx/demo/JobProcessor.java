package com.CaptureRx.demo;

import com.CaptureRx.demo.Entities.Job;
import com.CaptureRx.demo.Entities.Repositories.JobRepository;
import com.CaptureRx.demo.Entities.Repositories.StatusRepository;
import com.CaptureRx.demo.Entities.Status;
import com.CaptureRx.demo.Models.CreateJobResponse;
import com.CaptureRx.demo.Models.JobStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EntityScan("com/CaptureRx/demo/Entities")
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

    //Listener for Kafka
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
    public CreateJobResponse createNewJob(){
        CreateJobResponse response = new CreateJobResponse();
        try{
            //Create and insert new job record
            Status status = statusRepository.findByStatusId(1);
            Job job = new Job(status);
            jobRepository.save(job);

            //Add to Kafka for processing
            kafkaTemplate.send("JobsTopic", job.getJobId());

            response.setJobId(job.getJobId());
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
