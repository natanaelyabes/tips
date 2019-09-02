<template>
  <div class="content settings component">
        <div class="ui basic segment">
      <form class="ui form">
        <div class="field">
          <button class="ui primary button">
            Calculate
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/classes/BaseComponent';


@Component
export default class ContentSettingsComponent extends BaseComponent {

}
</script>
