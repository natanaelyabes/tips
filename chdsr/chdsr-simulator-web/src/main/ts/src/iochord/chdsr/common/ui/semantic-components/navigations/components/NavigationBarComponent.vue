<!--
  @package chdsr
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="navigation-bar component">
    <div v-bind:class="[type, color, size]" class="ui menu">
      <slot name="menu-item"></slot>
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
.ui.menu {
  width: 100%;
}
</style>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/chdsr/common/ui/semantic-components/SemanticComponent';


/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component
export default class NavigationBarComponent extends SemanticComponent {
  @Prop() private type!: string;
  @Prop() private color!: string;
  @Prop() private size!: string;
}
</script>

