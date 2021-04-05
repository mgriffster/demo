import { Component, OnInit } from '@angular/core';
import { JobService } from '../job.service';
import { JobResponse } from '../jobResponse';

@Component({
  selector: 'app-newjob',
  templateUrl: './newjob.component.html',
  styleUrls: ['./newjob.component.css']
})
export class NewjobComponent implements OnInit {

  job: JobResponse

  constructor(
    private jobService: JobService,
  ) {}

  ngOnInit(): void {
    this.createJob();
  }

  createJob(): void{
    this.jobService.createNewJob().subscribe(job => this.job = job);
  }

}
