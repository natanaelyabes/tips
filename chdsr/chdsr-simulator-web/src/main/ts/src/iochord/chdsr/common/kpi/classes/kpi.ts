import { KPIInterface } from "../intefaces/kpi";


class ParentKPI {
    public test: string = '';
    public abac: string = '';
    
    constructor(test: string, abac: string) {
        this.test = test;
        this.abac = abac;
    }

    public getTest(): string {
        return this.test;
    }
}
 
class KPI extends ParentKPI implements KPIInterface {
    test: string;
    public getTest(): string {
        return this.test + ' called from child';
    }
    setTest(test: string): void {
        throw new Error("Method not implemented.");
    }
    // public test?: string | undefined;
    public arah?: 'utara' | 'barat' | 'selatan' | 'timur';

    constructor(test: string, abac: string) {
        super(test, abac);
        this.test = test;
    }

    // public getTest(): string | undefined {
    //     return this.test;
    // }

    // public setTest(test: string | undefined): void {
    //     this.test = test;
    // }

    public setArah(arah?: 'utara' | 'barat' | 'selatan' | 'timur') {
        this.arah = arah;
    }

    public getArah(): string | undefined {
        return this.arah;
    }
}

const kpi = new KPI('test', 'abac');
kpi.setTest('test');
kpi.setArah('utara');

console.log(kpi.getTest());