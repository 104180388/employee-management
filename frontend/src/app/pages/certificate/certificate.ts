import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { NavbarComponent } from '../../layout/navbar/navbar';
import { CertificateService } from '../../services/certificate.service';

@Component({
  selector: 'app-certificate',
  standalone: true,
  imports: [NavbarComponent],
  templateUrl: './certificate.html',
  styleUrls: ['./certificate.css']
})
export class CertificateComponent implements OnInit {

  certificates: any[] = [];

  constructor(
    private certificateService: CertificateService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.certificateService.getAll()
      .subscribe(data => {
        this.certificates = data;
        this.cdr.detectChanges();
      });
  }
}
