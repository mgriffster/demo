import {Job} from './job';

export interface JobsResponse{
    success: boolean,
    errorMessage: string,
    jobs: Job[];
}