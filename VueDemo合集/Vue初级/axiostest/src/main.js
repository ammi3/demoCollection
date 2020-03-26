import Vue from 'vue'
import App from './App'

import axios from 'axios'

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  render: h => h(App)
})

axios({
  url: 'http://123.207.32.32:8000/home/multidata',
  method: "GET"
}).then(res => {
  console.log(res);
})

axios.defaults.baseURL = 'http://123.207.32.32:8000'
axios.defaults.timeout = 5000

//2.axios发送并发请求
axios.all([axios({
  url: '/home/multidata'
}), axios({
  url: '/home/data',
  params: {
    type: 'sell',
    page: 5
  }
})]).then(results => {
  console.log(results);
})

import { request } from "./network/request";

// request({
//   url: '/home/multidata'
// }, res => {
//   console.log(res);
// }, err => {
//   console.log(err);
// })

// request({
//   baseConfig: {

//   },
//   success: function(res) {
//     console.log(res);
//   },
//   failure: function(err) {
//     console.log(err);
//   }
// })

request({
  url: '/home/multidata'
}).then(res => {
  console.log(res.data);
}).catch(err => {
  console.log(err);
})
