<template>
  <div id="chdsr-app">
    <router-view></router-view>
  </div>
</template>

<style>
#chdsr-app {
  height: 100%;
}
</style>


<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

// Interfaces
import { VueHasLifeCycle } from '@/iochord/chdsr/common/lib/vue/interfaces/VueHasLifeCycle';

@Component
export default class ChdsrApp extends Vue implements VueHasLifeCycle {
  public beforeCreate(): void {
    // throw new Error("Method not implemented.");
  }

  public created(): void {
    // throw new Error("Method not implemented.");
  }

  public beforeMount(): void {
    // throw new Error("Method not implemented.");
  }

  public mounted(): void {
    // throw new Error("Method not implemented.");
  }

  public beforeUpdate(): void {
    // throw new Error("Method not implemented.");
  }

  public updated(): void {
    // throw new Error("Method not implemented.");
  }

  public beforeDestroy(): void {
    // throw new Error("Method not implemented.");
  }

  public destroyed(): void {
    // throw new Error("Method not implemented.");
  }
}
</script>
