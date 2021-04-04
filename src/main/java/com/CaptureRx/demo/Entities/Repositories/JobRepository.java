package com.CaptureRx.demo.Entities.Repositories;

import com.CaptureRx.demo.Entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

    Job findByJobId(long id);

}
