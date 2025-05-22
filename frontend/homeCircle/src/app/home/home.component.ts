import { Component } from '@angular/core';

import { ActivatedRoute } from '@angular/router';
import { ViewportScroller } from '@angular/common';
import { OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  standalone: false,
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  
constructor(
  private route: ActivatedRoute,
  private viewportScroller: ViewportScroller ) {}
  
  ngOnInit(): void {
  this.route.fragment.subscribe(fragment => {
  if (fragment) {
  setTimeout(() => {
  this.viewportScroller.scrollToAnchor(fragment);
  }, 100);
  }
  });
  }
  }
  
