import { Module, Mutation, MutationAction, VuexModule } from 'vuex-module-decorators';
import Vuex from 'vuex';

interface StoreType {
  branchModule: BranchMiningModule;
}

const store = new Vuex.Store<StoreType>({});

@Module({ dynamic: true, store, namespaced: true, name: 'BranchMiningModule' })

class BranchMiningModule extends VuexModule {
  public branches: any = {};

  @Mutation
  public addBranch(branch: any): void {
    const merged = { ...this.branches, ...branch };
    this.branches = merged;
  }

  @MutationAction({ mutate: ['branches'] })
  public async updateBranch(branch: any) {
    const merged = { ...this.branches, ...branch };
    return { branches: merged };
  }

  @MutationAction({ mutate: ['branches'] })
  public async clearBranch() {
    const merged = {};
    return { branches: merged };
  }
}

export default BranchMiningModule;
