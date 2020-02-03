import { VuexModule, Module, Mutation } from 'vuex-module-decorators';

@Module({ namespaced: true })
/**
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class Page extends VuexModule {
  public name: string = '';
  public author: string = '';
  public created: string = '';
  public modified: string = '';

  @Mutation
  public setName(name: string): void {
    this.name = name;
  }

  @Mutation
  public setAuthor(author: string): void {
    this.author = author;
  }

  @Mutation
  public setCreated(created: string): void {
    this.created = created;
  }

  @Mutation
  public setModified(modified: string): void {
    this.modified = modified;
  }
}
