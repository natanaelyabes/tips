<!--
  @package ts
  @author N. Y. Wirawan <ny4tips@gmail.com>
  @since 2019
-->
<template>
  <div class="breadcrumb component">
    <div class="ui breadcrumb">
      <slot name="sections"></slot>
    </div>
  </div>
</template>

<script lang="ts">
import { Component } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';

@Component

/**
 * Breadcrumb component.
 *
 * @export
 * @class BreadcrumbComponent
 * @extends {SemanticComponent}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 */
export default class BreadcrumbComponent extends SemanticComponent {
  //
}
</script>
