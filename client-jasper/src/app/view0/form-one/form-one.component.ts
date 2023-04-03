import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { FormOne } from 'src/app/models/Models';
import { SessionService } from 'src/app/session.service';

@Component({
  selector: 'app-form-one',
  templateUrl: './form-one.component.html',
  styleUrls: ['./form-one.component.css']
})
export class FormOneComponent implements OnInit {

  formOne!: FormGroup;
  
  constructor( private fb: FormBuilder,
              private router: Router,
              private sessionService: SessionService ) {};
  
  ngOnInit(): void {
    this.formOne = this.createForm();

    if(this.sessionService.hasKey("formOne")){
      //@ts-ignore
      const formOne = JSON.parse(this.sessionService.getItem("formOne")) as FormOne
      this.formOne.setValue(formOne);
    }

  }

  createForm() : FormGroup {

    const grp = this.fb.group({
      username: this.fb.control<string>("default"),
      comment: this.fb.control<string>("default")
    })

    return grp;
  }
  
  processForm() {
    console.log(this.formOne.value);
    let formOneData = this.formOne.value as FormOne;
    this.sessionService.setItem("formOne", JSON.stringify(formOneData));
    this.router.navigate(['/next'], {queryParams: formOneData});
  }
}
