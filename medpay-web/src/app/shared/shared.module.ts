import { CommonModule } from '@angular/common';
import { NgModule, Type } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { IconDefinition } from '@ant-design/icons-angular';
import {
    CaretLeftOutline,
    EyeInvisibleOutline,
    EyeOutline,
    SettingOutline,
    StepBackwardOutline,
} from '@ant-design/icons-angular/icons';
import { SEModule } from '@delon/abc/se';
import { STModule } from '@delon/abc/st';
import { SVModule } from '@delon/abc/sv';
import { DelonACLModule } from '@delon/acl';
import { DelonFormModule } from '@delon/form';
import { AlainThemeModule } from '@delon/theme';
import { NzBreadCrumbModule } from 'ng-zorro-antd/breadcrumb';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';
import { NzEmptyModule } from 'ng-zorro-antd/empty';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzPageHeaderModule } from 'ng-zorro-antd/page-header';
import { NzStepsModule } from 'ng-zorro-antd/steps';
import { NzToolTipModule } from 'ng-zorro-antd/tooltip';
import { NzRadioModule } from 'ng-zorro-antd/radio';
import { NzTabsModule } from 'ng-zorro-antd/tabs';
import { NzTransferModule } from 'ng-zorro-antd/transfer';
import { NzSwitchModule } from 'ng-zorro-antd/switch';
import { NzTagModule } from 'ng-zorro-antd/tag';
import { NzCollapseModule } from 'ng-zorro-antd/collapse';
import { NzAutocompleteModule } from 'ng-zorro-antd/auto-complete';
import { NzPopconfirmModule } from 'ng-zorro-antd/popconfirm';
import { NzBadgeModule } from 'ng-zorro-antd/badge';
import { NzModalModule } from 'ng-zorro-antd/modal';
import { NzImageModule } from 'ng-zorro-antd/image';
import { NzSkeletonModule } from 'ng-zorro-antd/skeleton';
import { NzListModule } from 'ng-zorro-antd/list';
import { NzTypographyModule } from 'ng-zorro-antd/typography';
import { NzPaginationModule } from 'ng-zorro-antd/pagination';
import { NzResultModule } from 'ng-zorro-antd/result';
import { NzDescriptionsModule } from 'ng-zorro-antd/descriptions';


import { SHARED_DELON_MODULES } from './shared-delon.module';
import { SHARED_ZORRO_MODULES } from './shared-zorro.module';
import { HospitalSearchComponent } from '../routes/hospital/components/hospital-search/hospital-search.component';
import { TransactionRefAndSerialOrBillInputComponent } from '../routes/transactions/components/transaction-search/transaction-ref-and-serial-or-bill-input.component';
import { DatePickerComponent } from './components/date-picker/date-picker.component';
import { RangeDatePickerComponent } from './components/range-date-picker/range-date-picker.component';
import { HospitalConfigureHospitalComponent } from '../routes/hospital/configure-hospital/configure-hospital.component';
import { HospitalSupportChannelDropdownComponent } from '../routes/hospital/components/hospital-support-channel-dropdown/hospital-support-channel-dropdown.component';
import { HospitalAuthTypeDropdownComponent } from '../routes/hospital/components/hospital-auth-type-dropdown/hospital-auth-type-dropdown.component';
import { HospitalEnvironmentDropdownComponent } from '../routes/hospital/components/hospital-environment-dropdown/hospital-environment-dropdown.component';
import { HospitalCollectionModeDropdownComponent } from '../routes/hospital/components/hospital-collection-mode-dropdown/hospital-collection-mode-dropdown.component';
import { SwitchToggleComponent } from './components/switch-toggle/switch-toggle.component';
import { UserTypeDropdownComponent } from '../routes/users/components/user-type-dropdown/user-type-dropdown.component';
import { UserPasswordComponent } from '../routes/users/components/user-password/user-password.component';
import { ExpandCollapseButtonComponent } from './components/expand-collapse-button/expand-collapse-button.component';
import { ExportUploadButtonComponent } from './components/export-upload-button/export-upload-button.component';
import { SearchResetButtonComponent } from './components/search-reset-button/search-reset-button.component';
import { StatusBadgeComponent } from './components/status-badge/status-badge.component';
import { SearchResultTableComponent } from './components/search-result-table/search-result-table.component';
import { RevenueHeadCategoryDropdownComponent } from '../routes/service-group/components/revenue-head-category-dropdown/revenue-head-category-dropdown.component';
import { UserSearchDropdownComponent } from '../routes/users/components/user-search-dropdown/user-search-dropdown.component';
import { RoleMultipleSearchDropdownComponent } from '../routes/authorization/components/role-multiple-search-dropdown/role-multiple-search-dropdown.component';
import { TransactionReceiptViewerComponent } from './components/transaction-receipt-viewer/transaction-receipt-viewer.component';
import { TransactionStatusPreviewComponent } from '../routes/transactions/components/transaction-status-preview/transaction-status-preview.component';

const icons: IconDefinition[] = [EyeInvisibleOutline, EyeOutline, StepBackwardOutline, CaretLeftOutline, SettingOutline];

const THIRDMODULES: Array<Type<void>> = [
    STModule,
    SVModule,
    SEModule,
    DelonFormModule,
    NzToolTipModule,
    NzPageHeaderModule,
    NzBreadCrumbModule,
    NzDatePickerModule,
    NzEmptyModule,
    NzStepsModule,
    NzRadioModule,
    NzTabsModule,
    NzTransferModule,
    NzSwitchModule, NzTagModule,
    NzCollapseModule,
    NzAutocompleteModule,
    NzPopconfirmModule,
    NzBadgeModule,
    NzSkeletonModule,
    NzImageModule,
    NzModalModule,
    NzListModule,
    NzTypographyModule,
    NzPaginationModule,
    NzResultModule,
    NzDescriptionsModule
];

const COMPONENTS: Array<Type<void>> = [
    HospitalSearchComponent,
    TransactionRefAndSerialOrBillInputComponent,
    DatePickerComponent,
    RangeDatePickerComponent,
    HospitalConfigureHospitalComponent,
    HospitalSupportChannelDropdownComponent,
    HospitalAuthTypeDropdownComponent,
    HospitalEnvironmentDropdownComponent,
    HospitalCollectionModeDropdownComponent,
    SwitchToggleComponent,UserTypeDropdownComponent,
    UserPasswordComponent,
    ExpandCollapseButtonComponent,
    ExportUploadButtonComponent,
    SearchResetButtonComponent,
    StatusBadgeComponent,
    SearchResultTableComponent,
    RevenueHeadCategoryDropdownComponent,
    UserSearchDropdownComponent,
    RoleMultipleSearchDropdownComponent,
    TransactionReceiptViewerComponent,
    TransactionStatusPreviewComponent
];
const DIRECTIVES: Array<Type<void>> = [];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        ReactiveFormsModule,
        AlainThemeModule.forChild(),
        DelonACLModule,
        ...SHARED_DELON_MODULES,
        ...SHARED_ZORRO_MODULES,
        // third libs
        ...THIRDMODULES,
        NzIconModule.forChild(icons),
    ],
    declarations: [
        // your components
        ...COMPONENTS,
        ...DIRECTIVES,
    ],
    exports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        RouterModule,
        AlainThemeModule,
        DelonACLModule,
        ...SHARED_DELON_MODULES,
        ...SHARED_ZORRO_MODULES,
        // third libs
        ...THIRDMODULES,
        // your components
        ...COMPONENTS,
        ...DIRECTIVES
    ],
})
export class SharedModule {}
