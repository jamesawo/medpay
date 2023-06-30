import { Menu } from '@delon/theme';

export const agentMenu: Menu[] = [
    {
        text: '',
        group: true,
        children: [
            {
                text: 'Agents ',
                icon: { type: 'icon', value: 'user' },
                children: [
                    { text: 'Make Payment', link: '/agent/make-payment' },
                    { text: 'Shift Report', link: '/agent/shift-report' },
                    { text: 'Reprint Receipt', link: '/agent/reprint-receipt' },
                ],
            },
        ],
    },
];

export const allMenu: Menu[] = [
    {
        text: 'Main',
        group: false,
        children: [
            {
                text: 'Dashboard',
                link: '/dashboard',
                icon: { type: 'icon', value: 'appstore' },
            },
        ],
    },

    {
        text: '',
        group: true,
        children: [
            {
                text: 'Hospitals',
                icon: { type: 'icon', value: 'medicine-box' },
                children: [
                    { text: 'Create Hospital', link: '/hospital/create-hospital' },
                    { text: 'Manage Hospitals', link: '/hospital/view-hospitals' },
                    { text: 'Configure Hospital', link: '/hospital/configure-hospital' },
                ],
            },
        ],
    },

    {
        text: '',
        group: true,
        children: [
            {
                text: 'Users',
                icon: { type: 'icon', value: 'team' },
                children: [
                    { text: 'Create User', link: '/users/create-users' },
                    { text: 'Manage Users', link: '/users/view-users' },
                ],
            },
        ],
    },

    {
        text: '',
        group: true,
        children: [
            {
                text: 'Transactions',
                icon: { type: 'icon', value: 'dollar-circle' },
                children: [
                    { text: 'Manage Transactions', link: '/transactions/view-transactions' },
                    { text: 'Transaction Status', link: '/transactions/check-payment-status' },
                    { text: 'Transaction Details', link: '/transactions/payment-details' },
                    // { text: 'Reconcile Transaction', link: '/transactions/reconcile-payment' },
                    // { text: 'Transaction Settings', link: '/global-settings/transaction-settings' },
                    { text: 'Reprint Receipt', link: '/transactions/reprint-receipt' },
                ],
            },
        ],
    },

    {
        text: '',
        group: true,
        children: [
            {
                text: 'Service Groups',
                icon: { type: 'icon', value: 'folder-open' },
                children: [
                    { text: 'Create Service Group', link: '/service-group/group/create' },
                    { text: 'Manage Service Group', link: '/service-group/group/manage' },
                ],
            },
        ],
    },

    {
        text: '',
        group: true,
        children: [
            {
                text: 'Revenue Heads',
                icon: { type: 'icon', value: 'folder-add' },
                badgeDot: true,
                children: [
                    { text: 'Create Revenue Head', link: '/service-group/revenue-head/create' },
                    { text: 'Manage Revenue Head', link: '/service-group/revenue-head/manage' },
                ],
            },
        ],
    },

    {
        text: '',
        group: true,
        children: [
            {
                text: 'Services',
                icon: { type: 'icon', value: 'tags' },
                children: [
                    { text: 'Create Service', link: '/service-group/services/create' },
                    { text: 'Manage Service', link: '/service-group/services/manage' },
                ],
            },
        ],
    },

    {
        text: '',
        group: true,
        children: [
            {
                text: 'Authorization ',
                icon: { type: 'icon', value: 'lock' },
                children: [
                    {
                        text: 'Users',
                        group: true,
                        children: [
                            { text: 'Assign Role', link: '/authorization/users/assign-role' },
                            { text: 'Reset Password', link: '/authorization/users/password' },
                        ],
                    },
                    {
                        text: 'Roles',
                        group: true,
                        children: [
                            { text: 'Create Role', link: '/authorization/roles/create' },
                            { text: 'Manage Role', link: '/authorization/roles/manage' },
                            { text: 'Permission', link: '/authorization/roles/permissions' },
                        ],
                    },
                ],
            },
        ],
    },

    {
        text: '',
        group: true,
        children: [
            {
                text: 'Reports ',
                icon: { type: 'icon', value: 'folder' },
                children: [
                    {
                        text: 'Transaction Report',
                        group: true,
                        children: [
                            { text: 'Daily Collection', link: '/reports/daily-collection' },
                            { text: 'Agent Collection', link: '/reports/agent-collection' },
                        ],
                    },
                ],
            },
        ],
    },

    ...agentMenu,
];
