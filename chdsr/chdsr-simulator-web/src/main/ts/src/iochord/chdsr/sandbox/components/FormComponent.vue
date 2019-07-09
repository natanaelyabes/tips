<!--
  @package chdsr
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="ui form">
    <div class="field">
      <label>First Name</label>
      <input type="text" name="first-name" @change="handleFirstNameChange()" v-model="fName" placeholder="First Name">
    </div>
    <div class="field">
      <label>Last Name</label>
      <input type="text" name="last-name" @change="handleLastNameChange()" v-model="lName" placeholder="Last Name">
    </div>
    <div class="field">
      <div class="ui checkbox">
        <input type="checkbox" tabindex="0" class="hidden">
        <label>I agree to the Terms and Conditions</label>
      </div>
    </div>
    <button @click="changeFirstLastName" class="ui button">Submit</button>
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
import { Component, Prop, Vue, Watch } from 'vue-property-decorator';
import BaseComponent from '@/iochord/chdsr/common/lib/vue/classes/BaseComponent';


/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component
export default class FormComponent extends BaseComponent {
  @Prop(String) private firstName!: string;
  @Prop(String) private lastName!: string;

  private fName?: string = '';
  private lName?: string = '';

  public mounted(): void {
    this.$nextTick(() => {
      this.fName = this.firstName;
      this.lName = this.lastName;
    });
  }

  @Watch('firstName')
  public onFirstNameChange(newVal: string): void {
    this.fName = newVal;
  }

  @Watch('lastName')
  public onLastNameChange(newVal: string): void {
    this.lName = newVal;
  }

  public changeFirstLastName(): void {
    if (this.firstName === 'Patrick' && this.lastName === 'Star') {
      this.fName = 'Spongebob';
      this.$emit('changeFirstName', this.fName);
      this.lName = 'Squarepants';
      this.$emit('changeLastName', this.lName);
    } else if (this.firstName === 'Spongebob' && this.lastName === 'Squarepants') {
      this.fName = 'Patrick';
      this.$emit('changeFirstName', this.fName);
      this.lName = 'Star';
      this.$emit('changeLastName', this.lName);
    }
  }

  public handleFirstNameChange(): void {
    this.$emit('changeFirstName', this.fName);
  }

  public handleLastNameChange(): void {
    this.$emit('changeLastName', this.lName);
  }
}
</script>