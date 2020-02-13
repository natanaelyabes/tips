<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div id="ips-app">
    <router-view></router-view>
  </div>
</template>

<style>
#ips-app {
  height: 100%;
  zoom: 90%;
}

::-webkit-scrollbar {
  width: .2em!important;
  height: .2em!important;
}

::-webkit-scrollbar-thumb {
  background: #a5b1c2!important;
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue  } from 'vue-property-decorator';

// Interfaces
import IndexLayout from '@/iochord/ips/common/ui/layout/class/IndexLayout';

@Component

/**
 * Index entry for IPS root view.
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class IpsApp extends IndexLayout {
  //
}
</script>
