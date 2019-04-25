<template>
  <div class="breadcrumb component">
    <div class="ui breadcrumb">
      <slot name="sections"></slot>
    </div>
  </div>
</template>

<style scoped>

</style>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class BreadcrumbComponent extends Vue {
  private mounted(): void {
    //
  }
}
</script>
