import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { createPdfResourceUrl } from '@shared';

@Component({
    selector: 'app-transaction-receipt-viewer',
    templateUrl: './transaction-receipt-viewer.component.html',
    styles: [

        `
            .pdf-viewer-box {
                display: flex;
                align-items: center;
                justify-content: center;
            }
            
            .iframe > iframe > #toolbar {
                display: none;
            }

        `,
    ],
})
export class TransactionReceiptViewerComponent implements OnInit {
    @ViewChild('divElement')
    public divElement?: ElementRef<HTMLDivElement>;

    @Input()
    public data?: ArrayBuffer;

    public dataUrl!: SafeResourceUrl;
    public isContentLoaded = false;

    @Input()
    public props?: {
        downloadAction: () => void,
        printAction: () => void,
        width?: number,
        height?: number
    } = {
        downloadAction: () => {
        }, printAction: () => {
        },
    };

    constructor(private sanitizer: DomSanitizer) {
    }

    ngOnInit(): void {
        this.onLoadSafeUrl();
    }

    public onClear() {
        this.isContentLoaded = false;
        this.data = undefined;
        this.dataUrl = '';
    }

    public onLoadSafeUrl(){
        if (this.data) {
            const fileURL = `${createPdfResourceUrl(this.data)}#toolbar=0`;
            this.dataUrl = this.sanitizer.bypassSecurityTrustResourceUrl(fileURL);
            this.isContentLoaded = true;
        }
    }

}
