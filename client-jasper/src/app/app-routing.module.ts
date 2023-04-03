import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormOneComponent } from './view0/form-one/form-one.component';
import { FormTwoComponent } from './view1/form-two/form-two.component';
import { PageConfirmComponent } from './view2/page-confirm/page-confirm.component';

const routes: Routes = [
  {path: "" , component: FormOneComponent}, 
  {path: "next", component: FormTwoComponent},
  {path: "next/confirm", component: PageConfirmComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
