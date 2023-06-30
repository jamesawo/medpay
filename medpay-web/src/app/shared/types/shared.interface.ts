import { PagePayload } from './shared.payload';

export interface IDropdown {
    value: string;
    id: number;
    title: string;
}

export interface IDateRange {
    startDate: string;
    endDate: string;
}

export interface PageSearchPayload<T> {
    page?: PagePayload;
    searchRequest?: T;
}

export interface PageResultPayload<T> {
    page?: PagePayload;
    result?: T[];
}
