import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { NavbarComponent } from '../../layout/navbar/navbar';
import { LanguageService } from '../../services/language.service';

@Component({
  selector: 'app-language',
  standalone: true,
  imports: [NavbarComponent],
  templateUrl: './language.html',
  styleUrls: ['./language.css']
})
export class LanguageComponent implements OnInit {

  languages: any[] = [];

  constructor(
    private languageService: LanguageService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.languageService.getAll()
      .subscribe(data => {
        this.languages = data;
        this.cdr.detectChanges();
      });
  }
}
