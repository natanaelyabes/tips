<template>
  <div class="sandbox list vuex router detail view">
    <div class="ui basic segment">
      <h1>Model {{$route.params.model_id}} - Page {{$route.params.detail_id}}</h1>
    </div>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Component } from 'vue-property-decorator';
import PageLayout from '@/iochord/ips/common/ui/layout/classes/PageLayout';

@Component
export default class SandboxListVuexRouterDetail extends PageLayout {

  /** @Override */
  public overrideBrowserProperties(): void {
    this.setDocumentTitle(`Sandbox List Vuex Router: Model ${this.$route.params.model_id} - Page ${this.$route.params.detail_id}`);
  }
}
</script>
