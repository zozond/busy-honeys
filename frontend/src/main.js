import './assets/main.css';
import App from './App.vue';
import router from './router';
import { createApp } from 'vue';
import { createPinia } from 'pinia';
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import '@mdi/font/css/materialdesignicons.css'

const vuetify = createVuetify({
    components,
    directives,
})

createApp(App).use({
    icons: {
        defaultSet: 'mdi'
    }
}).use(createPinia()).use(router).use(vuetify).mount('#app')
