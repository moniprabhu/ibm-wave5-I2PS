import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }

  login(){
    this.router.navigateByUrl('/login')
  }

  onSubmitServiceProvider(serviceprovider) {
      this.router.navigateByUrl("/serviceprovider");
    }

    onSubmitInnovator(innovator) {
      this.router.navigateByUrl("/innovatorprofile");
    }

}