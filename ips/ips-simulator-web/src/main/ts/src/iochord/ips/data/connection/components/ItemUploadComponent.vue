<!--
  @package ts
  @author Riska Asriana Sutrisnowati <riska@iochord.com>
  @since 2019
-->
<template>
  <div class="upload component">
    <div class="ui basic segment" id="header">
      <h4>File type</h4>
    </div>
    <div class="ui divider"></div>
    <div class="ui divided items">
      <div class="item hovered">CSV</div>
      <div class="item hovered">XES</div>
      <div class="item hovered">MXML</div>
      <div class="item hovered">Simulation Model</div>
      <div class="item hovered">CPN</div>
      <div class="item hovered">Process Model</div>
    </div>
  </div>
</template>

<style scoped>
.ui.input {
  width: 100%;
}

.ui.items {
  margin: 1rem 0;
}

.ui.basic.segment {
  padding: 0 1em;
}

.ui.divided.items {
  min-width: 180px;
  margin: 0;
}

.ui.divider {
  margin: 0;
}

.ui.ui.divided.items>.item:first-child {
  padding-top: 1rem!important;
}

.ui.items .item {
  padding: 1rem 1rem;
  cursor: pointer;
}

.ui.items .item:first-child {
  margin: initial;
  /* padding: 1rem 1rem!important; */
}

.ui.items .item:last-child {
  margin: initial;
  padding: 1rem 1rem!important;
}

.ui.items .item.hovered:hover {
  background: rgba(0,0,0,.03);
}

a {
  color: black;
}
</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

@Component

/**
 * Content data upload component.
 *
 * @export
 * @class ItemUploadComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author Riska Asriana Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default class ItemUploadComponent extends BaseComponent {
  //
}
</script>
