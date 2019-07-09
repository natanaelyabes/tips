<!--
  @package chdsr
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="breadcrumb component">
    <div class="ui breadcrumb">
      <slot name="sections"></slot>
    </div>
  </div>
</template>

<style scoped>
/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
</style>

<script lang="ts">
import { Component } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/chdsr/common/ui/semantic/SemanticComponent';


/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component
export default class BreadcrumbComponent extends SemanticComponent {
  //
}
</script>
