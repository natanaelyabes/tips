/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface VueHasLifeCycle {
  beforeCreate(): void;
  created(): void;
  beforeMount(): void;
  mounted(): void;
  beforeUpdate(): void;
  updated(): void;
  beforeDestroy(): void;
  destroyed(): void;
}
