<template>
  <div id="ips-app">
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
import IndexLayout from '@/iochord/ips/common/ui/layout/classes/IndexLayout';


/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component
export default class IpsApp extends IndexLayout {
  //
}
</script>
