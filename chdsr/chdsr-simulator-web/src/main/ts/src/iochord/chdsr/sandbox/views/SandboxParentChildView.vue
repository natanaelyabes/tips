<!--
  @package chdsr
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="sandbox parent child view">
    <div class="ui basic segment">
      <h1>Parent-Child Data Binding</h1>
      <FormComponent
        :firstName="firstName"
        :lastName="lastName"
        @changeFirstName="firstNameFromChild($event)"
        @changeLastName="lastNameFromChild($event)"
      />
      <br />
      <button class="ui button" @click="changeFromParent()">Change from parent</button>
    </div>
  </div>
</template>

<style>
/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
</style>


<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import BaseComponent from '@/iochord/chdsr/common/lib/vue/classes/BaseComponent';
import FormComponent from '@/iochord/chdsr/sandbox/components/FormComponent.vue';


/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component({
  components: {
    FormComponent,
  },
})
export default class SandboxParentChildView extends BaseComponent {
  private firstName: string = '';
  private lastName: string = '';

  public mounted(): void {
    this.firstName = 'Patrick';
    this.lastName = 'Star';
  }

  public firstNameFromChild(e: any) {
    this.firstName = e;
    console.log('From child: ', this.firstName);
  }

  public lastNameFromChild(e: any) {
    this.lastName = e;
    console.log('From child: ', this.lastName);
  }

  public changeFromParent(): void {
    this.firstName = 'Natanael';
    this.lastName = 'Yabes';
    console.log('From parent: ', this.firstName);
    console.log('From parent: ', this.lastName);
  }
}
</script>
