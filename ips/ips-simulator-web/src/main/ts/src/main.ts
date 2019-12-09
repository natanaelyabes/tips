// Vue.js
import Vue from 'vue';
import VueRx from 'vue-rx';
import Rx from 'rxjs/Rx';
import IpsApp from './IpsApp.vue';
import router from '@/router';
import store from '@/store';
import '@/registerServiceWorker';

/** 3rd party libraries */

// 1. JQuery
import * as $ from 'jquery';

// 2. Semantic UI
import '../public/semantic/dist/semantic.min.css';
import '../public/semantic/dist/semantic.min.js';

import '../node_modules/jexcel/dist/jexcel.js';
import '../node_modules/jsuites/dist/jsuites.js';

import '../node_modules/jexcel/dist/jexcel.css';
import '../node_modules/jsuites/dist/jsuites.css';


Vue.use(VueRx, Rx);
Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(IpsApp),
}).$mount('#ips-app');
