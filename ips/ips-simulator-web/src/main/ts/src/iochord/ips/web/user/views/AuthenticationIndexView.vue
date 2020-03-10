<!--
  @package ts
  @author N. Y. Wirawan <ny4tips@gmail.com>
  @since 2019
-->
<template>
  <div class="index view">
    <router-view></router-view>
  </div>
</template>

<style>
.index.view {
  height: 100%;
}
</style>

<script lang="ts">
import { Component } from 'vue-property-decorator';
import IndexLayout from '@/iochord/ips/common/ui/layout/class/IndexLayout';

@Component

/**
 * @export
 * @class AuthenticationIndexView
 * @extends {IndexLayout}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 */
export default class AuthenticationIndexView extends IndexLayout {
  //
}
</script>
