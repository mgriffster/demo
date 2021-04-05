import { Injectable } from '@angular/core';
import { JobsResponse } from './jobsResponse';
import { JobResponse } from './jobResponse';
import { Observable } from 'rxjs';
import { MessageService } from './message.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class JobService {

  private getJobUrl = "/api/getjobstatus?jobId=";
  private getJobsUrl = "/api/getalljobstatus";
  private createNewJobUrl = "/api/createnewjob"

  constructor(private http: HttpClient, private messageService: MessageService) { }

  getJobs(): Observable<JobsResponse>{
    return this.http.get<JobsResponse>(this.getJobsUrl);
  }

  getJob(id: number): Observable<JobResponse> {
    return this.http.get<JobResponse>(this.getJobUrl+id);
  }

  createNewJob(): Observable<JobResponse>{
    return this.http.get<JobResponse>(this.createNewJobUrl);
  }

}
