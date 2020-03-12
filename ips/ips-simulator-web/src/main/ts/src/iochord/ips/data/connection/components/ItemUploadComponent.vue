<!--
  @package ts
  @author Riska Asriana Sutrisnowati <riska@iochord.com>
  @since 2019
-->
<template>
  <div class="upload component">
    <div class="ui divided items">
      <div class="item">
        <div class="ui transparent icon input">
          <input type="text" placeholder="Browse...">
          <i class="search icon"></i>
        </div>
      </div>
      <div class="item hovered">Import CSV</div>
      <div class="item hovered">Import XES</div>
      <div class="item hovered">Import MXML</div>
      <div class="item hovered">Import Simulation Model</div>
      <div class="item hovered">Import CPN</div>
      <div class="item hovered">Import Process Model</div>
    </div>
  </div>
</template>

<style scoped>
.ui.items {
  margin: 1rem 0;
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
