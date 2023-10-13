export interface User {
    name: string;
    email: string;
    phone: number;
}

export interface Employee {
    employeeId: string;
    name: string;
    email: string;
    phone: number;
}

export interface Project {
    projectId: string;
    name: string;
    desc: string;
    start_date: Date;
    due_date: Date;
    poId: string;
    reference: string;
    billable: number;
    rate: number;
    capex_code: string;
    employeeNames: string[];
}

export interface PostProject {
    projectId: string;
    name: string;
    desc: string;
    start_date: Date;
    due_date: Date;
    poId: string;
    reference: string;
    billable: number;
    rate: number;
    capex_code: string;
    employeeIds: string[];
}


export interface Po {
    id: string;
    name: string;
    email: string;
    phone: number;
    amount: number;
    start_date: Date;
    due_date: Date;
}

export interface ChangeLog {
    id: number;
    tableName: string;
    primaryKeyValue: string;
    columnName: string;
    oldValue: number;
    newValue: number;
    createdDate: Date;
}