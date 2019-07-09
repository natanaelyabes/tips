import { Component, Prop } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/chdsr/common/ui/semantic/SemanticComponent';
import { SemanticModulesIsUsed } from '@/iochord/chdsr/common/ui/semantic/SemanticModulesIsUsed';

// jQuery Symbol Handler
declare const $: any;


/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component
export default class MessageComponent extends SemanticComponent {
  @Prop() private size!: string;  // mini|tiny|small|large|big|huge|massive
  @Prop() private dismissible!: boolean;

  /** @Override */
  public declareSemanticModules(): void {
    this.declareMessage();
  }

  private declareMessage(): void {
    this.onMessageClose();
  }

  private onMessageClose(): void {
    $('.message .close')
      .on('click', function(this: any) {
        $(this)
          .closest('.message')
          .transition('fade')
        ;
      })
    ;
  }
}
