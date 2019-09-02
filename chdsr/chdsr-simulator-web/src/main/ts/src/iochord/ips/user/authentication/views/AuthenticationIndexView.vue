<!--
  @package ips
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
 * @package ips
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
import IndexLayout from '@/iochord/ips/common/ui/layout/classes/IndexLayout';

/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component
export default class AuthenticationIndexView extends IndexLayout {
  //
}
</script>
