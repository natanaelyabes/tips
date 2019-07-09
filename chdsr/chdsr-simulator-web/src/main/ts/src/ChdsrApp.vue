<template>
  <div id="chdsr-app">
    <router-view></router-view>
  </div>
</template>

<style>
#chdsr-app {
  height: 100%;
}
</style>


<script lang="ts">
// Vue & Libraries
import { Component, Vue  } from 'vue-property-decorator';

// Interfaces
import { VueHasLifeCycle } from '@/iochord/chdsr/common/lib/vue/interfaces/VueHasLifeCycle';
import IndexView from '@/iochord/chdsr/common/lib/vue/classes/IndexView';


/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */

@Component
export default class ChdsrApp extends IndexView {
  //
}
</script>
