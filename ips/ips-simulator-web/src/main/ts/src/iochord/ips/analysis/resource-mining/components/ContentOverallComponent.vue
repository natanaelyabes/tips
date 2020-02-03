<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
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
import { Component, Vue } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';


@Component

/**
 * Overall page to provide a side by side
 * view between process model and its fitted distribution.
 *
 * @extends BaseComponent
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class ContentOverallComponent extends BaseComponent {

}
</script>
