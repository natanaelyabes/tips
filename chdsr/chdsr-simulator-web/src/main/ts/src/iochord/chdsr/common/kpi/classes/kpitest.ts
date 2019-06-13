abstract class Car{
    public description!:string;

    public getDescription() : string{
        return this.description;
    }

    public abstract cost(): number;
}

class TNACar extends Car{
    public description = "TNA Car";

    public cost() : number{
        return 70000;
    }
}

class ICHCar extends Car{
    public description = "Ichsan Car";

    public cost() : number{
        return 80000;
    }
}

abstract class CarOptions extends Car{
    protected decoratedCar!: Car;
    public abstract getDescription() : string;
    public abstract cost() : number;
}

class EnhancedAutoPilot extends CarOptions{
    protected decoratedCar: Car;

    constructor(car:Car){
        super();
        this.decoratedCar = car;
    }

    public getDescription(): string{
        return this.decoratedCar.getDescription() + " Add Auto pilot";
    }

    public cost() : number{
        return this.decoratedCar.cost() + 777;
    }
}

class RacingSeat extends CarOptions{
    protected decoratedCar: Car;

    constructor(car:Car){
        super();
        this.decoratedCar = car;
    }

    public getDescription(): string{
        return this.decoratedCar.getDescription() + " Add Racing Seat";
    }

    public cost() : number{
        return this.decoratedCar.cost() + 888;
    }
}

class CarTestDrive{
    static main(): void{
        let myTNACar = new TNACar();
        console.log(myTNACar.getDescription());
        console.log(myTNACar.cost());

        myTNACar = new EnhancedAutoPilot(myTNACar);
        console.log(myTNACar.getDescription());
        console.log(myTNACar.cost());

        myTNACar = new RacingSeat(myTNACar);
        console.log(myTNACar.getDescription());
        console.log(myTNACar.cost());
    }
}

CarTestDrive.main()