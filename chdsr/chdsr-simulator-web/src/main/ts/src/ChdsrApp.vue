<template>
  <div id="chdsr-app">
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
#chdsr-app {
  height: 100%;
  zoom: 90%;
}

</style>


<script lang="ts">
// Vue & Libraries
import { Component, Vue  } from 'vue-property-decorator';

// Interfaces
import IndexLayout from '@/iochord/chdsr/common/ui/layout/classes/IndexLayout';


/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component
export default class ChdsrApp extends IndexLayout {
  //
}
</script>
