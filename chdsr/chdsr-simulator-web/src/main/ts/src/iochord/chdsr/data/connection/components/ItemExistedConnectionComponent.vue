<template>
  <div class="item existed connection component">
    <div class="item">
      <div class="ui transparent icon input">
        <input type="text" placeholder="Browse...">
        <i class="search icon"></i>
      </div>
    </div>
    <a class="item">Data 1</a>
    <a class="item">Data 2</a>
    <!-- <template slot="existed-connection">
    </template> -->
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';

@Component
export default class ItemExistedConnectionComponent extends BaseComponent {

}
</script>
