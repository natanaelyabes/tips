<template>
  <div class="item connection component">
    <div class="item">
      <div class="ui transparent icon input">
        <input type="text" placeholder="Search connection...">
        <i class="search icon"></i>
      </div>
    </div>
    <a class="item">Oracle</a>
    <a class="item">MSSQL Server</a>
    <a class="item">MySQL</a>
    <a class="item">Hadoop FS</a>
  </div>
</template>

<style>

</style>


<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';

@Component
export default class ItemConnectionComponent extends BaseComponent {

}
</script>
>