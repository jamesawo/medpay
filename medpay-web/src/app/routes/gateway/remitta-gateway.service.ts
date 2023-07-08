import {Injectable} from '@angular/core';
import {environment} from "@env/environment";
import {HttpClient} from "@angular/common/http";
import {TransactionPayload} from "../transactions/_data/transaction.payload";
import {ScriptService} from "ngx-script-loader";

export type RemittaCallBack = {
    onSuccess: (arg: any) => void,
    onError: (arg: any) => void,
    onClose: () => void,
}

declare var RmPaymentEngine: any;

@Injectable({
    providedIn: 'root'
})
export class RemittaGatewayService {

    private url: string = environment.api.baseUrl + '/gateway';
    private remittaKey = environment['remittaKey'];
    private REMITTA_DEMO = 'https://remitademo.net/payment/v1/remita-pay-inline.bundle.js';

    constructor(private http: HttpClient, private scriptService: ScriptService) {
        this.scriptService.loadScript(this.REMITTA_DEMO).subscribe(() => {
        });
    }

    public generateRRR(transaction: TransactionPayload) {
        return this.http.post<any>(`${this.url}/generate-rrr`, transaction, {observe: "response"});
    }

    public handleRemittaPayment(rrr: string, callBack?: RemittaCallBack, transaction?: TransactionPayload) {
        let paymentEngine: any = RmPaymentEngine.init({
            key: this.remittaKey,
            processRrr: true,
            transactionId: Math.floor(Math.random() * 1101233), //replace in production
            extendedData: {
                customFields: [
                    {
                        name: 'rrr',
                        value: rrr
                    }
                ]
            },

            onSuccess: function (response: any) {
                callBack?.onSuccess({response, transaction})
            },

            onError: function (response: any) {
                callBack?.onError(response);
            },

            onClose: function () {
                callBack?.onClose()
            }
        });

        paymentEngine.showPaymentWidget();
    }
}
