package com.example.spring_batch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @PostMapping("/importCustomers")
    public ResponseEntity<String> importCsvToDBJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(job, jobParameters);
            return ResponseEntity.ok("Job started successfully");
        } catch (JobExecutionAlreadyRunningException e) {
            logger.error("Job is already running", e);
            return ResponseEntity.status(409).body("Job is already running");
        } catch (JobRestartException e) {
            logger.error("Job restart failed", e);
            return ResponseEntity.status(500).body("Job restart failed");
        } catch (JobInstanceAlreadyCompleteException e) {
            logger.error("Job is already completed", e);
            return ResponseEntity.status(409).body("Job is already completed");
        } catch (JobParametersInvalidException e) {
            logger.error("Invalid job parameters", e);
            return ResponseEntity.status(400).body("Invalid job parameters");
        }
    }
}