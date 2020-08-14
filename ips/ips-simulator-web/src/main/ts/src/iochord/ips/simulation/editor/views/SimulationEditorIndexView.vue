<!--
  @package ts
  @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
  @since 2019
-->
<template>
  <div class="index view">
    <router-view></router-view>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import IndexLayout from '@/iochord/ips/common/ui/layout/class/IndexLayout';

/**
 * The simulation editor index view.
 *
 * @export
 * @class SimulationEditorIndexView
 * @extends {IndexLayout}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 */
export default class SimulationEditorIndexView extends IndexLayout {
  //
}
</script>
