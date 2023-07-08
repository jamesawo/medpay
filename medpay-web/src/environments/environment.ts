
import { DelonMockModule } from '@delon/mock';
import { Environment } from '@delon/theme';

import * as MOCKDATA from '../../_mock';

export const environment = {
    // development config
    remittaKey: 'U09MRHw0MDgxOTUzOHw2ZDU4NGRhMmJhNzVlOTRiYmYyZjBlMmM1YzUyNzYwZTM0YzRjNGI4ZTgyNzJjY2NjYTBkMDM0ZDUyYjZhZWI2ODJlZTZjMjU0MDNiODBlMzI4YWNmZGY2OWQ2YjhiYzM2N2RhMmI1YWEwYTlmMTFiYWI2OWQxNTc5N2YyZDk4NA==',
    production: false,
    useHash: true,
    api: {
        baseUrl: 'http://localhost:9090/api/v1',
        refreshTokenEnabled: true,
        refreshTokenType: 'auth-refresh',
    },
    modules: [DelonMockModule.forRoot({ data: MOCKDATA })],
} as Environment;