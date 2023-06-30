import { Component, OnInit } from '@angular/core';
import { ModalHelper } from '@delon/theme';
import { HospitalPayload } from '../data/hospital.payload';
import { Router } from '@angular/router';

@Component({
  selector: 'app-hospital-configure-hospital',
  templateUrl: './configure-hospital.component.html',
})
export class HospitalConfigureHospitalComponent implements OnInit {
    private payload: HospitalPayload | undefined;

    constructor(private modal: ModalHelper, private router: Router) {
    }

    ngOnInit(): void {
    }

    add(): void {
    }

    onHospitalSelected(hospital: HospitalPayload) {
        this.payload = hospital;
    }

    onSearchHospital() {
        this.router.navigateByUrl('/global-settings/hospital-settings-details',
            {
                state: { data: this.payload },
            }).then();
    }

}
