import axios, { AxiosResponse } from 'axios';

export class BaseService {
  protected url: string;
  protected response: any;

  constructor(url: string) {
    this.url = url;
  }
  public async fetchResponse(): Promise<BaseService> {
    try {
      this.response = await axios.get(this.url as string);
    } catch (e) {
      console.log(e);
    }
    return this;
  }
}
