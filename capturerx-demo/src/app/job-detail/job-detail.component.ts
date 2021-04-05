import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { JobService } from '../job.service';
import { JobResponse } from '../jobResponse';

@Component({
  selector: 'app-job-detail',
  templateUrl: './job-detail.component.html',
  styleUrls: ['./job-detail.component.css']
})
export class JobDetailComponent implements OnInit {
  job: JobResponse

  constructor(
    private route: ActivatedRoute,
    private jobService: JobService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getJob();
  }

  goBack(): void {
    this.location.back();
  }

  getJob(): void{
    const id = +this.route.snapshot.paramMap.get('id');
    this.jobService.getJob(id).subscribe(job => this.job = job);
  }

}
