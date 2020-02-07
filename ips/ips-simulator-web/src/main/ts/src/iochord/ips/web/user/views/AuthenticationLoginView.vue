<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="login view">
    <div class="ui middle aligned center aligned grid">
      <div class="column">
        <form class="ui large form">
          <div class="ui teal inverted top attached segment">
            <div class="logo">
              <img class="ui medium image centered" id="ips-logo" src="@/assets/images/logos/iochord.png" />
            </div>
            <h2 class="ui image header">
              <div class="content">
                Log-in to your account
              </div>
            </h2>
          </div>
          <div class="ui bottom attached segment">
            <div class="ui segment">
              <div class="field">
                <div class="ui left icon input">
                  <i class="user icon"></i>
                  <input type="text" name="email" v-model="email" placeholder="E-mail address">
                </div>
              </div>
              <div class="field">
                <div class="ui left icon input">
                  <i class="lock icon"></i>
                  <input type="password" name="password" v-model="password" placeholder="Password">
                </div>
              </div>
              <a v-on:click="login()" class="ui fluid large raised black submit button">Login</a>
            </div>
          </div>
          <div v-if="error" class="ui negative message">{{ errorMessage }}</div>
        </form>
        <div class="ui teal message">
          New to us? <a href="#">Sign Up</a>
        </div>
        <footer>
          Copyright &copy; IOChord 2019
        </footer>
        <div class="ui small images centered">
          <a href="http://pusan.ac.kr/kor/Main.do" target="_blank" rel="noopener noreferrer"><img class="ui image" src="@/assets/images/logos/pnu_logo_transparent_dark.png" /></a>
          <a href="http://www.iochord.com/" target="_blank" rel="noopener noreferrer"><img class="ui image" src="@/assets/images/logos/iochord.png" /></a>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login.view {
  background: #03628c;
  background-size: cover;
  height: 100%;
  top: 1em;
  position: relative;
}

.login.view #ips-logo {
  padding: 2em 1em 0;
}

.login.view > .grid {
  height: 100%;
}

.login.view .ui.header {
  margin-bottom: 0.5em;
}

.login.view .ui.attached.segment {
  border: 0;
}

.login.view .ui.basic.segment {
  display: flex;
  flex-direction: row;
  padding: 0;
}

.login.view .ui.basic.segment .box{
  width: 25%;
  height: 1em;
}

.green.box {
  background: #5ebc77;
}

.slate.box {
  background: #2e313d;
}

.orange.box {
  background: #ef6245;
}

.yellow.box {
  background: #faa95a;
}

.blue.button:hover {
  background: #03628c!important;
}

.column {
  max-width: 450px;
}

h2.ui.white.header{
	color: white;
}

.login.view footer{
  color: white;
  margin: 2em 0 1em 0;
}

.login.view .ui.form input[type]{
	background-color: rgba(255,255,255,0.2);
	/* color: #2185d0; */
}

.login.view .ui.form input[type]:focus{
  background: #dff0ff;  /* fallback for old browsers */
}

.login.view .ui.form .field.field input:-webkit-autofill{
	box-shadow: 0 0 0 100px #dff0ff inset!important;
    border-color: #2185d0!important;
    color: #2185d0;
}

.login.view .logo img {
  margin-top: auto;
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component } from 'vue-property-decorator';
import { BaseUrlEnum, ApplicationEnum } from '@/iochord/ips/common/enums';

import PageView from '@/iochord/ips/common/ui/layout/class/PageLayout';

@Component

/**
 * The authentication login view.
 *
 * @export
 * @class AuthenticationLoginView
 * @extends {PageView}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 */
export default class AuthenticationLoginView extends PageView {

  /**
   * The email of the user.
   *
   * @private
   * @type {string}
   * @memberof AuthenticationLoginView
   */
  private email: string = 'admin@iochord.com';

  /**
   * The password for the user.
   *
   * @private
   * @type {string}
   * @memberof AuthenticationLoginView
   */
  private password: string = 'secret123';

  /**
   * Indicates whether a login error is exists. False otherwise.
   *
   * @private
   * @type {boolean}
   * @memberof AuthenticationLoginView
   */
  private error: boolean = false;

  /**
   * The login error message.
   *
   * @private
   * @type {string}
   * @memberof AuthenticationLoginView
   */
  private errorMessage: string = '';

  /**
   * Override the browser properties.
   *
   * @override
   * @memberof AuthenticationLoginView
   */
  public overrideBrowserProperties(): void {
    this.setDocumentTitle('Login');
  }

  /**
   * Handles the user login.
   *
   * @memberof Index
   */
  private login(): void {
    if (this.email === 'admin@iochord.com' && this.password === 'secret123') {
      this.$router.push('/iochord/ips/home');
    } else {
      this.error = true;
      this.errorMessage = 'Wrong email or password!';
    }
  }
}
</script>
