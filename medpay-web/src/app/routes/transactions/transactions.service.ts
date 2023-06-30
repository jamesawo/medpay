import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '@env/environment';
import { TransactionPayload, TransactionSearchPayload } from './_data/transaction.payload';
import { PageResultPayload, PageSearchPayload } from '../../shared/types/shared.interface';

@Injectable({ providedIn: 'root' })
export class TransactionsService {
    private url: string = environment.api.baseUrl + '/transaction';

    constructor(private http: HttpClient) {
    }

    public getTransactionsBySearchPayload(payload: PageSearchPayload<TransactionSearchPayload>) {
        return this.http.post<PageResultPayload<TransactionPayload>>(`${this.url}/by-search-request`, payload);
    }

    public getTransactionBySerialAndRef(search: { serial?: string, reference?: string }) {
        return this.http.get<TransactionPayload>(`${this.url}/by-reference-and-token?reference=${search.reference}&token=${search.serial}`);
    }

    public getTransactionReceiptPdfBytes(search: {reference?: string, serial?: string}): any {
        return this.http.get(
            `${this.url}/download-pdf-receipt?reference=${search.reference}&token=${search.serial}`,
            { responseType: 'blob' as 'json' });
    }

    public createTransaction(payload: TransactionPayload){
        return this.http.post<TransactionPayload>(`${this.url}/create`, payload);
    }

}
