export interface User {
    name: string;
    email: string;
    phone: number;
}

export interface Employee {
    id: string;
    name: string;
    email: string;
    phone: number;
}

export interface Project {
    id: string;
    name: string;
    description: string;
    startDate: Date;
    dueDate: Date;
    purchaseOrderId: string;
    reference: string;
    billable: number;
    rate: number;
    capexCode: string;
    employeeNames: string[];
}

export interface PostProject {
    id: string;
    name: string;
    description: string;
    startDate: Date;
    dueDate: Date;
    purchaseOrderId: string;
    reference: string;
    billable: number;
    rate: number;
    capexCode: string;
    employeeIds: string[];
}


export interface Po {
    id: string;
    name: string;
    email: string;
    phone: number;
    amount: number;
    startDate: Date;
    dueDate: Date;
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