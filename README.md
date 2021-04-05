# demo
 Spring/Kafka/Angular demo

This is the requested demo from CaptureRx as part of the application process.

Spring Boot application:
	Creates new jobs that are inserted into a Postgres database (locally hosted DB) ( /createnewjob )
	Fetch data for a specific job using the job ID ( /getjobstatus?jobId=<JOB_ID> )
	Fetch data for all existing jobs ( /getalljobstatus )
	Should use Zookeeper+Kafka to post new jobs when they are created (with status=New), then consume them to update the status (In Progress, then Done once "work" is complete)
	
Angular application:
	Display all job details ( /jobs )
	Fetch specific job details ( /details/<JOB_ID> )
	Create new jobs ( /newjob )
	