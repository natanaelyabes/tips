<!--
  @package chdsr
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="index view">
    <router-view></router-view>
  </div>
</template>

<style>
/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
.index.view {
  height: 100%;
}
</style>

<script lang="ts">
import { Component } from 'vue-property-decorator';
import IndexView from '@/iochord/chdsr/common/lib/vue/classes/IndexView';

/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component
export default class AuthenticationIndexView extends IndexView {
  //
}
</script>
