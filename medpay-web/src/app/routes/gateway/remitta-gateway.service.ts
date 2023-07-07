import {Injectable} from '@angular/core';
import {environment} from "@env/environment";
import {HttpClient} from "@angular/common/http";
import {TransactionPayload} from "../transactions/_data/transaction.payload";

declare var RmPaymentEngine: any;

@Injectable({
    providedIn: 'root'
})
export class RemittaGatewayService {

    private url: string = environment.api.baseUrl + '/gateway';

    constructor(private http: HttpClient) {
    }

    public generateRRR(transaction: TransactionPayload) {
        return this.http.post<any>(`${this.url}/generate-rrr`, transaction);
    }

    public makePayment(rrr: string) {
        let paymentEngine: any = RmPaymentEngine.init({
            key: 'U09MRHw0MDgxOTUzOHw2ZDU4NGRhMmJhNzVlOTRiYmYyZjBlMmM1YzUyNzYwZTM0YzRjNGI4ZTgyNzJjY2NjYTBkMDM0ZDUyYjZhZWI2ODJlZTZjMjU0MDNiODBlMzI4YWNmZGY2OWQ2YjhiYzM2N2RhMmI1YWEwYTlmMTFiYWI2OWQxNTc5N2YyZDk4NA==',
            processRrr: true,
            transactionId: Math.floor(Math.random() * 1101233), // Replace with a reference you generated or remove the entire field for us to auto-generate a reference for you. Note that you will be able to check the status of this transaction using this transaction Id
            extendedData: {
                customFields: [
                    {
                        name: 'rrr',
                        value: rrr
                    }
                ]
            },
            onSuccess: function(response: any) {
                console.log('callback Successful Response', response);
            },
            onError: function(response: any) {
                console.log('callback Error Response', response);
            },
            onClose: function() {
                console.log('closed');
            }
        });
        paymentEngine.showPaymentWidget();
    }
}
