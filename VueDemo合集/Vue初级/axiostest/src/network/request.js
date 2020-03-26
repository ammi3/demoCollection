import axios from 'axios'

export function request(config) {
  return new Promise((resolve, reject) => {
    //创建axios实例
    const instance = axios.create({
      baseURL: 'http://123.207.32.32:8000',
      timeout: 5000
    })

    //axios的拦截器
    instance.interceptors.request.use(config => {
      console.log(config);
      return config;
    }, err => {
      console.log(err);
    });

    instance.interceptors.response.use(res => {
      console.log(res);
    },err => {
      console.log(err);
    })

    //发送真正的网络请求
    // instance(config).then(res => {
    //   //console.log(res);
    //   // config.success(res);
    //   resolve(res);
    // }).catch(err => {
    //   //console.log(err);
    //   //config.failure(err)
    //   reject(err);
    // })
    return instance(config);
  })
}