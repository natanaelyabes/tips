<template>
  <div class="content file query component">
    <form class="ui form">
      <h4 class="ui dividing header">Select File</h4>
         <div class="grouped fields">
            <div class="field">
              <div class="ui radio checkbox">
                <input type="radio" name="fruit" tabindex="0" class="hidden">
                <label>Apples</label>
              </div>
            </div>
            <div class="field">
              <div class="ui radio checkbox">
                <input type="radio" name="fruit" tabindex="0" class="hidden">
                <label>Oranges</label>
              </div>
            </div>
            <div class="field">
              <div class="ui radio checkbox">
                <input type="radio" name="fruit" tabindex="0" class="hidden">
                <label>Pears</label>
              </div>
            </div>
            <div class="field">
              <div class="ui radio checkbox">
                <input type="radio" name="fruit" tabindex="0" class="hidden">
                <label>Grapefruit</label>
              </div>
            </div>
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
import BaseComponent from '@/iochord/ips/common/ui/layout/classes/BaseComponent';

@Component
export default class ContentFileQueryComponent extends BaseComponent {

}
</script>
