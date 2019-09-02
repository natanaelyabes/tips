<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="header component">
    <div class="ui basic segment">
      <h1 v-if="hasHeaderTitle"><slot name="header-title" /></h1>
      <div v-if="hasHeaderTitle === hasBreadcrumbComponent && hasHeaderTitle === true" class="ui divider"></div>

      <!-- BreadcrumbComponent -->
      <BreadcrumbComponent v-if="hasBreadcrumbComponent">
        <template slot="sections">
          <slot name="header-breadcrumb"/>
        </template>
      </BreadcrumbComponent>

    </div>
  </div>
</template>

<style scoped>
/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
.header.component .ui.basic.segment {
  margin: 0;
  border-bottom: 1px solid rgba(0,0,0,.2);
  background-color: #81b0c5;
}

.breadcrumb.component {
  color: white;
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

// Components
import BreadcrumbComponent from '@/iochord/ips/common/ui/semantic-components/breadcrumbs/components/BreadcrumbComponent.vue';
import BaseComponent from '@/iochord/ips/common/ui/layout/classes/BaseComponent';


/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component({
  components: {
    BreadcrumbComponent,
  },
})
export default class HeaderComponent extends BaseComponent {
  private get hasHeaderTitle(): boolean {
    return !!this.$slots['header-title'];
  }

  private get hasBreadcrumbComponent(): boolean {
    return !!this.$slots['header-breadcrumb'];
  }
}
</script>
