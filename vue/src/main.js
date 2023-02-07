import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import ja from 'element-plus/dist/locale/ja.mjs'


createApp(App).use(router).use(ElementPlus,{locale: ja}).mount('#app')
