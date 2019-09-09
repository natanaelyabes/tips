<template>
  <div class="item upload component">
    <div class="item">
      <div class="ui transparent icon input">
        <input type="text" placeholder="Browse...">
        <i class="search icon"></i>
      </div>
    </div>
    <a class="item">Import CSV</a>
    <a class="item">Import XES</a>
    <a class="item">Import MXML</a>
    <a class="item">Import Simulation Model</a>
    <a class="item">Import CPN</a>
    <a class="item">Import Process Model</a>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

@Component
export default class ItemUploadComponent extends BaseComponent {

}
</script>
