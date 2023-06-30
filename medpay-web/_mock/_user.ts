import { MockRequest } from '@delon/mock';

const list: any[] = [];
const total = 50;

function genData(params: any): { total: number; list: any[] } {
    let ret = [...list];
    return { total: ret.length, list: ret };
}

function saveData(id: number, value: any): { msg: string } {
    return { msg: 'ok' };
}

export const USERS = {};
