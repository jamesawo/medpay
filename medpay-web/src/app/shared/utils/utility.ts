import { ToastrService } from 'ngx-toastr';
import { AbstractControl, FormGroup } from '@angular/forms';
import {
    TransactionPayerDetailPayload,
    TransactionPaymentDetailPayload,
} from '../../routes/transactions/_data/transaction.payload';
import { UserPayload } from '../../routes/users/_data/user.payload';
import { formatCurrency } from '@angular/common';

/**
 * Replaces " " with _ in a given string input
 *
 * @param value
 */
export function stripJoin(value: string): string {
    return value.indexOf(' ') >= 0 ? value.split(' ').join('_') : value;
}


/**
 * Removes _ from enums and replace with ' '
 *
 * @param value
 */
export function splitEnum(value: string): string {
    return value.indexOf('_') >= 0 ? value.split('_').join(' ') : value;
}

export function displayError(
    erObj: any,
    opts?: { toastService?: ToastrService, toastTitle?: string },
    errorMessage?: string,
    errorList?: string[],
): void {

    errorMessage = erObj?.error?.message ?? 'An Error Occurred';
    errorList = erObj?.error?.error ?? [];

    if (opts && opts.toastService) {
        let title = opts.toastTitle ?? errorMessage;
        let message = '';

        if (errorList instanceof Array) {
            errorList?.forEach(value => {
                message += `${value} <br>`;
            });
        }
        else {
            errorList = ['ACTION FAILED'];
            if (erObj.error.error && erObj.status) {
                message += `${erObj.status}: ${erObj.error.path ?? ''} ${erObj.error.error} <br>`;
                errorList.push(message);
                errorList.push(erObj.message ?? '');
            }
        }

        if (erObj?.status != null ) {
            title +=  ` :${erObj.status}`;
        }

        opts.toastService.error(message, title, {
            progressBar: true,
            enableHtml: true,
            progressAnimation: 'decreasing',
            timeOut: 8000,
        });
    }
}

export function validateFormControls(form: FormGroup): boolean {
    if (form && form.controls) {
        let isFormInvalid = form.invalid;
        if (isFormInvalid) {
            Object.keys(form.controls).forEach(key => {
                if (key) {
                    form.get(`${key}`)?.markAsDirty();
                    form.get(`${key}`)?.updateValueAndValidity();
                }
            });
        }
        return isFormInvalid;
    }
    return false;
}

export function isFormControlInvalid(controlName: string, form: FormGroup): boolean {
    if (form && form.controls) {
        if (form.controls[controlName]) {
            return form.controls[controlName].invalid;
        }
        return true;
    }
    return true;
}

export function compareValidator(controlOne: AbstractControl, controlTwo: AbstractControl) {
    return () => {
        if (controlOne.value !== controlTwo.value) {
            return { matchError: 'Value does not match' };
        }
        return null;
    };

}

export function formatTime(time?: string): string {
    if (time) {
        let strings = time.split(':');
        const hour = strings[0];
        const min = strings[1];
        const sec = strings[2];
        let date = new Date();
        date.setHours(Number.parseInt(hour));
        date.setMinutes(Number.parseInt(min));
        date.setSeconds(Number.parseInt(sec));
        return date.toLocaleString('en-US', { hour: 'numeric', minute: 'numeric', hour12: true });
    }
    return '';
}

export function getTransactionPayerDetail(payerDetail?: TransactionPayerDetailPayload) {
    if (payerDetail && payerDetail.patientNumber) {
        return ` ${payerDetail.patientNumber}`;
    } else if (payerDetail && payerDetail.fullName) {
        return `${payerDetail?.fullName}`;
    } else {
        return '';
    }
}

export function getTransactionPaymentDetail(paymentDetail?: TransactionPaymentDetailPayload): string {
    if (paymentDetail?.billNumber) {
        return `Bill#:  ${paymentDetail.billNumber}`;
    } else if (paymentDetail?.services && paymentDetail?.services?.length > 0) {
        return `${paymentDetail?.services?.length} Service Items`;
    } else {
        return 'N/A';
    }
}

export function concatUserFullName(user?: UserPayload): string {
    const basic = user?.basicDetails;
    return `${basic?.firstName ?? ''} ${basic?.lastName ?? ''} ${basic?.otherName ?? ''}`;
}

export function currencyFormatter(value?: number): string{
    if (value){
        return formatCurrency(value, 'en-Us', '₦', '₦');
    }
    return '';
}

export function createPdfResourceUrl(blobFile: ArrayBuffer) : string {
    let file = new Blob([blobFile], { type: 'application/pdf' });
    return URL.createObjectURL(file);
}