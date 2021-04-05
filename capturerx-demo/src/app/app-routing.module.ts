import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { JobsComponent } from './jobs/jobs.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { JobDetailComponent } from './job-detail/job-detail.component';
import { NewjobComponent } from './newjob/newjob.component';

const routes: Routes = [
  { path: 'detail/:id', component: JobDetailComponent },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'jobs', component: JobsComponent },
  { path: 'newjob', component: NewjobComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }