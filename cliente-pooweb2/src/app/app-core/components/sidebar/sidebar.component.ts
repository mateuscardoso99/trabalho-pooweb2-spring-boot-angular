import { Component, Input } from "@angular/core";

@Component({
    selector: 'app-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.css'],
    host: {'class': 'col-12 col-sm-3 col-xl-2 px-sm-2 px-0 bg-dark d-flex sticky-top'}
})
export class SidebarComponent {
  @Input() opcoes: any
}