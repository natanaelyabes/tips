<template>
  <div class="sandbox-common-service ui basic segment">
    <div class="ui accordion">
      <div class="title ui segment">
        <i class="dropdown icon"></i>
        DATA SERVICE
      </div>
      <div class="content ui segment">
      </div>
      <div class="title ui segment">
        <i class="dropdown icon"></i>
        MODEL SERVICE
      </div>
      <div class="content ui segment">
      </div>
      <div class="title ui segment">
        <i class="dropdown icon"></i>
        SIMULATOR SERVICE
      </div>
      <div class="content ui segment">
      </div>
      <div class="title ui segment">
        <i class="dropdown icon"></i>
        ANALYSIS SERVICE
      </div>
      <div class="content ui segment">
      </div>
      <div class="title ui segment">
        <i class="dropdown icon"></i>
        USER SERVICE
      </div>
      <div class="content ui segment">
      </div>
    </div>
  </div>
</template>

<style>
</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import { SbpnetModelService } from '@/iochord/ips/common/service/model/SbpnetModelService';

declare const $: any;

@Component({
  components: {
   },
 })
export default class SandboxCommonService extends Vue {
  public mounted() {
    $('.ui.accordion').accordion();
  }
}
</script>
