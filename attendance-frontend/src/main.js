import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import { getEmployeeName } from './api';
import { createRouter, createWebHistory } from 'vue-router';
import AdminPanel from './components/AdminPanel.vue';
import EmployeeList from './components/EmployeeList.vue';

const routes = [
  { path: '/admin', component: AdminPanel },
  { path: '/attendance', component: EmployeeList },
  { path: '/', redirect: '/attendance' }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

const app = createApp(App)
app.config.globalProperties.$getEmployeeName = getEmployeeName;
app.use(ElementPlus)
app.use(router);

// 防抖处理ResizeObserver，防止loop completed with undelivered notifications报错
const debounce = (fn, delay) => {
  let timer;
  return (...args) => {
    if (timer) {
      clearTimeout(timer);
    }
    timer = setTimeout(() => {
      fn(...args);
    }, delay);
  };
};

if (window.ResizeObserver) {
  const _ResizeObserver = window.ResizeObserver;
  window.ResizeObserver = class ResizeObserver extends _ResizeObserver {
    constructor(callback) {
      super(debounce(callback, 200));
    }
  };
}

app.mount('#app')
