import axios from "axios";

// 环境的切换
if (process.env.NODE_ENV == 'development') {
  axios.defaults.baseURL = '/api';
} else if (process.env.NODE_ENV == 'production') {
  // axios.defaults.baseURL = "http://192.168.71.52:35210";
  axios.defaults.baseURL = window.location.origin;
}
// 请求拦截器
// axios.interceptors.request.use((config) => {
//   const conf = config;
  
//   return conf;
// }, (error) => Promise.reject(error));

// 响应连接器
axios.interceptors.response.use((response) => {
  
  return response
}, function (error) {
  return Promise.reject(error)
});

export default axios;