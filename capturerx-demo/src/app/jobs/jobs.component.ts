import { Component, OnInit } from '@angular/core';

import { JobsResponse } from '../jobsResponse';
import { JobService } from '../job.service';

@Component({
  selector: 'app-jobs',
  templateUrl: './jobs.component.html',
  styleUrls: ['./jobs.component.css']
})
export class JobsComponent implements OnInit {

  jobs: JobsResponse;

  constructor(private jobService: JobService) { }

  ngOnInit(): void {
    this.getJobs();
  }

  refresh(): void{
    this.jobService.getJobs().subscribe(jobs => this.jobs = jobs);
  }

  getJobs(): void{
    this.jobService.getJobs().subscribe(jobs => this.jobs = jobs);
  }

}
