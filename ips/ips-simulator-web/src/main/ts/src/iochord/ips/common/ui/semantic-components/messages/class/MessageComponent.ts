import { Component, Prop } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';

// jQuery Symbol Handler
declare const $: any;


/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component

/**
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
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
