import { GroupReportEnum, ReportFormatEnum } from './reports.enum';
import { HospitalPayload } from '../../hospital/data/hospital.payload';
import { RevenueHeadPayload } from '../../service-group/_data/service-group.payload';
import { IDateRange } from '../../../shared/types/shared.interface';
import { UserPayload } from '../../users/_data/user.payload';
import { TransactionStatusEnum } from '../../transactions/_data/transaction.enum';

export class ReportsSearchPayload {
    groupBy: GroupReportEnum = GroupReportEnum.HOSPITAL;
    hospital?: HospitalPayload;
    revenueHead?: RevenueHeadPayload;
    dateRange?: IDateRange;
    user?: UserPayload;
    format = ReportFormatEnum.PDF;
    status = TransactionStatusEnum.SUCCESSFUL;
}