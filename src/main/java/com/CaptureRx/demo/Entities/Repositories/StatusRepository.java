package com.CaptureRx.demo.Entities.Repositories;

import com.CaptureRx.demo.Entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {

    Status findByStatusId(long id);

}
