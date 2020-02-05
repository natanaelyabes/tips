import { Module, VuexModule, Mutation } from 'vuex-module-decorators';
import Page from './Page';

@Module({namespaced: true})
/**
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class Model extends VuexModule {
  public name: string = '';
  public pages: Page[] = new Array<Page>();

  @Mutation
  public setName(name: string): void {
    this.name = name;
  }

  @Mutation
  public setPages(pages: Page[]): void {
    this.pages = pages;
  }

  public get nameUpper(): string {
    return this.name.toUpperCase();
  }
}
