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
import { Component } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/chdsr/common/ui/semantic/SemanticComponent';

@Component
export default class BreadcrumbComponent extends SemanticComponent {
  //
}
</script>
