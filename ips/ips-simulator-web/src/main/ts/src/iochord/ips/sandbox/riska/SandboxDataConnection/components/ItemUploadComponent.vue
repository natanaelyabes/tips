<template>
  <div class="item upload component">
    <div class="item">
      <div class="ui transparent icon input">
        <input type="text" placeholder="Browse...">
        <i class="search icon"></i>
      </div>
    </div>
    <a class="item">Upload CSV</a>
    <a class="item">Upload XES</a>
    <a class="item">Upload MXML</a>
    <a class="item">Upload Simulation Model</a>
    <a class="item">Upload CPN</a>
    <a class="item">Upload Process Model</a>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/classes/BaseComponent';

@Component
export default class ItemUploadComponent extends BaseComponent {

}
</script>
