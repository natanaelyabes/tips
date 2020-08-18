<!--
  @package ts
  @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
  @since 2019
-->
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

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';

@Component

/**
 * Settings view to assign distribution fitting parameters or properties.
 *
 * @extends BaseComponent
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
export default class ContentSettingsComponent extends BaseComponent {

}
</script>
