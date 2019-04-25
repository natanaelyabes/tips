/**
 *
 *
 * @export
 * @interface VueHasLifeCycleInterface
 */
export interface VueHasLifeCycleInterface {
  beforeCreate(): void;
  created(): void;
  beforeMount(): void;
  mounted(): void;
  beforeUpdate(): void;
  updated(): void;
  beforeDestroy(): void;
  destroyed(): void;
}
