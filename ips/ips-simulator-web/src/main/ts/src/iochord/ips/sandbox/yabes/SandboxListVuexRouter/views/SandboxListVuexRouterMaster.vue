<template>
  <div class="sandbox list vuex router master view">
    <div class="ui basic segment">
      <h1>Model {{ $route.params.model_id }}</h1>
    </div>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Component } from 'vue-property-decorator';
import PageLayout from '@/iochord/ips/common/ui/layout/class/PageLayout';

@Component
export default class SandboxListVuexRouterMaster extends PageLayout {

  /** @Override */
  public overrideBrowserProperties(): void {
    this.setDocumentTitle(`Sandbox List Vuex Router: Model ${this.$route.params.model_id}`);
  }

  public mounted(): void {
    //
  }
}
</script>
