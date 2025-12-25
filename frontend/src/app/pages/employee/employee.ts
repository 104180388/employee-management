import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from '../../layout/navbar/navbar';
import { EmployeeService } from '../../services/employee.service';
import { LanguageService } from '../../services/language.service';
import { CertificateService } from '../../services/certificate.service';

@Component({
  selector: 'app-employee',
  standalone: true,
  imports: [NavbarComponent, FormsModule, CommonModule],
  templateUrl: './employee.html',
  styleUrls: ['./employee.css']
})
export class EmployeeComponent implements OnInit {

  employees: any[] = [];
  languages: any[] = [];
  certificates: any[] = [];
  
  showEditModal = false;
  isEditMode = false; // true = chỉnh sửa, false = thêm mới
  editingEmployee: any = {
    id: null,
    name: '',
    dob: '',
    address: '',
    phone: '',
    selectedLanguages: [] as number[],
    selectedCertificates: [] as number[]
  };
  phoneError = '';

  constructor(
    private employeeService: EmployeeService,
    private languageService: LanguageService,
    private certificateService: CertificateService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.loadEmployees();
    this.loadLanguages();
    this.loadCertificates();
  }

  loadEmployees() {
    this.employeeService.getAll()
      .subscribe(data => {
        this.employees = data;
        this.cdr.detectChanges();
      });
  }

  loadLanguages() {
    this.languageService.getAll()
      .subscribe(data => this.languages = data);
  }

  loadCertificates() {
    this.certificateService.getAll()
      .subscribe(data => this.certificates = data);
  }

  openEditModal(employee: any) {
    this.isEditMode = true;
    this.editingEmployee = {
      id: employee.id,
      name: employee.name || '',
      dob: employee.dob ? employee.dob.split('T')[0] : '',
      address: employee.address || '',
      phone: employee.phone || '',
      selectedLanguages: employee.languages ? employee.languages.map((el: any) => el.language.id) : [],
      selectedCertificates: employee.certificates ? employee.certificates.map((ec: any) => ec.certificate.id) : []
    };
    this.showEditModal = true;
    this.phoneError = '';
    this.cdr.detectChanges();
  }

  openAddModal() {
    this.isEditMode = false;
    this.editingEmployee = {
      id: null,
      name: '',
      dob: '',
      address: '',
      phone: '',
      selectedLanguages: [],
      selectedCertificates: []
    };
    this.showEditModal = true;
    this.phoneError = '';
    this.cdr.detectChanges();
  }

  closeEditModal() {
    this.showEditModal = false;
    this.isEditMode = false;
    this.editingEmployee = {
      id: null,
      name: '',
      dob: '',
      address: '',
      phone: '',
      selectedLanguages: [],
      selectedCertificates: []
    };
    this.phoneError = '';
  }

  validatePhone() {
    const phone = this.editingEmployee.phone;
    if (!phone) {
      this.phoneError = 'Số điện thoại không được để trống';
      this.cdr.detectChanges();
      return false;
    }
    if (!/^\d{10}$/.test(phone)) {
      this.phoneError = 'Số điện thoại phải có đúng 10 chữ số';
      this.cdr.detectChanges();
      return false;
    }
    this.phoneError = '';
    this.cdr.detectChanges();
    return true;
  }

  toggleLanguage(languageId: number) {
    const index = this.editingEmployee.selectedLanguages.indexOf(languageId);
    if (index > -1) {
      this.editingEmployee.selectedLanguages.splice(index, 1);
    } else {
      this.editingEmployee.selectedLanguages.push(languageId);
    }
    this.cdr.detectChanges();
  }

  toggleCertificate(certificateId: number) {
    const index = this.editingEmployee.selectedCertificates.indexOf(certificateId);
    if (index > -1) {
      this.editingEmployee.selectedCertificates.splice(index, 1);
    } else {
      this.editingEmployee.selectedCertificates.push(certificateId);
    }
    this.cdr.detectChanges();
  }

  isLanguageSelected(languageId: number): boolean {
    return this.editingEmployee.selectedLanguages.includes(languageId);
  }

  isCertificateSelected(certificateId: number): boolean {
    return this.editingEmployee.selectedCertificates.includes(certificateId);
  }

  saveEmployee() {
    if (!this.validatePhone()) {
      return;
    }

    const employeeData = {
      name: this.editingEmployee.name,
      dob: this.editingEmployee.dob,
      address: this.editingEmployee.address,
      phone: this.editingEmployee.phone,
      languages: this.editingEmployee.selectedLanguages.map((langId: number) => ({
        language: { id: langId }
      })),
      certificates: this.editingEmployee.selectedCertificates.map((certId: number) => ({
        certificate: { id: certId }
      }))
    };

    if (this.isEditMode) {
      // UPDATE employee
      this.employeeService.update(this.editingEmployee.id, employeeData)
        .subscribe({
          next: () => {
            this.loadEmployees();
            this.closeEditModal();
          },
          error: (error) => {
            console.error('Lỗi khi cập nhật nhân viên:', error);
            alert('Có lỗi xảy ra khi cập nhật nhân viên');
          }
        });
    } else {
      // ADD employee
      this.employeeService.create(employeeData)
        .subscribe({
          next: () => {
            this.loadEmployees();
            this.closeEditModal();
          },
          error: (error) => {
            console.error('Lỗi khi thêm nhân viên:', error);
            alert('Có lỗi xảy ra khi thêm nhân viên');
          }
        });
    }
  }

  deleteEmployee(id: number) {
    if (confirm('Bạn có chắc chắn muốn xóa nhân viên này?')) {
      this.employeeService.delete(id)
        .subscribe({
          next: () => {
            this.loadEmployees();
          },
          error: (error) => {
            console.error('Lỗi khi xóa nhân viên:', error);
            alert('Có lỗi xảy ra khi xóa nhân viên');
          }
        });
    }
  }
}
