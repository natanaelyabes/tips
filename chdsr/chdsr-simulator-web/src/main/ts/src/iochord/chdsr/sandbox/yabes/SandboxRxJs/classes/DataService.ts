import { Subject, Observable } from 'rxjs';

export class DataService {
  public static fetchData(): Observable<string> {
    return this.subjectData.asObservable();
  }

  public static sendData(message: string): void {
    this.subjectData.next(message);
  }

  public static clearData(): void {
    this.subjectData.next();
  }

  private static subjectData: Subject<string> = new Subject<string>();
}
