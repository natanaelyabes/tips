// import {} from 'dotenv';
// Vue.js
import Vue from 'vue';
import ChdsrApp from './ChdsrApp.vue';
import router from '@/router';
import store from '@/store';
import '@/registerServiceWorker';

// 3rd party libraries

// 1. JQuery
import * as $ from 'jquery';

// 2. Semantic UI
import '../public/semantic/dist/semantic.min.css';
import '../public/semantic/dist/semantic.min.js';

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(ChdsrApp),
}).$mount('#chdsr-app');
