import { Component, OnInit } from '@angular/core';
import { JobsResponse } from '../jobsResponse';
import { JobService } from '../job.service';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  jobs: JobsResponse;

  constructor(private jobService: JobService) { }

  ngOnInit() {
    this.getJobs();
  }

  getJobs(): void {
    this.jobService.getJobs()
      .subscribe(jobs => this.jobs = jobs);
  }
}