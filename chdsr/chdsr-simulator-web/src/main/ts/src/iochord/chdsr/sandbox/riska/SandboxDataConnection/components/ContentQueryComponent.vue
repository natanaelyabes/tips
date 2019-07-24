<template>
  <div class="content query component">
    <form class="ui form">
      <h4 class="ui dividing header">Filter Query</h4>
        <div class="field">
          <textarea></textarea>
        </div>
        <div class="inline fields">
          <div class="eight wide field">

          </div>
          <div class="eight wide field">
            <button class="ui primary button">
              Execute & Preview
            </button>
            <button class="ui button">
              Reset Query
            </button>
          </div>
        </div>
    </form>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';

@Component
export default class ContentQueryComponent extends BaseComponent {

}
</script>
