import {Job} from './job';

export interface JobResponse{
    success: boolean,
    errorMessage: string,
    job: Job;
}