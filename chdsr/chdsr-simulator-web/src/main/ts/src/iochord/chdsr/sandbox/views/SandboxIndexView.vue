<template>
  <div class="index view">
    <router-view></router-view>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

// Classes
import IndexView from '@/iochord/chdsr/common/lib/vue/classes/IndexView';

export default class SandboxIndexView extends IndexView {
  public mounted(): void {
    //
  }
}
</script>
