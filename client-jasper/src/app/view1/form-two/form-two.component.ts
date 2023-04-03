import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FormOne } from 'src/app/models/Models';
import { SessionService } from 'src/app/session.service';
import { UploadService } from 'src/app/upload.service';

@Component({
  selector: 'app-form-two',
  templateUrl: './form-two.component.html',
  styleUrls: ['./form-two.component.css']
})
export class FormTwoComponent implements OnInit{

  @ViewChild("imageFile") 
  imageFile!: ElementRef;
  formOneData!: FormOne;

  imageForm!: FormGroup;

  constructor( private fb: FormBuilder,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private uploadService: UploadService,
              private sessionService: SessionService ) {}

  ngOnInit(): void {
    this.imageForm = this.createForm();
  }

  createForm() : FormGroup {
    const grp = this.fb.group({
      description: this.fb.control<String>("hello")
    })
    return grp;
  }

  processForm() {
    // console.log(this.imageForm.value);

    this.activatedRoute.queryParams.subscribe(x => {this.formOneData = x as FormOne});
    
    //@ts-ignore
    const formOne = JSON.parse(this.sessionService.getItem("formOne")) as FormOne;
    
    //@ts-ignore
    console.log(formOne.comment);
    

    // console.log(this.formOneData);

    // console.log(Object.assign({"one": "hello", "two": "world"}, {"three": "goodbye", "four": "world"}));

    const formData = new FormData();
    formData.set("imageFile", this.imageFile.nativeElement.files[0])
    formData.set("description", this.imageForm.get("description")?.value)
    formData.set("username", this.formOneData.username)
    formData.set("commment", this.formOneData.comment)
    
    // this.uploadService.postFormData(formData).subscribe(x => console.log(x));

    // console.log("viewchild", this.imageFile.nativeElement)
    // http here

    // this.router.navigate(['/next', 'confirm']);

  }

}
