<template>
  <main>
    <router-link to="/">TraidingView</router-link>
    <router-link to="/D3chart">D3.js</router-link>
    <router-link to="/apex">Apexchart</router-link>
    <router-view></router-view>

  </main>
</template>

<script setup>
import { 
  ref,
  onMounted,
  onUpdated,
  defineAsyncComponent,
  inject,
  computed,
  watch,
  reactive,
} from 'vue';
import { RouterLink, RouterView } from 'vue-router'


</script>

<style lang="scss" scoped>
	main {
		> a {margin-right: 20px; font-size: 18px;}

		div.containRoute {margin: 20px 0; padding: 8px 12px; border-radius: 12px; border: 1px solid #eee; min-height: 300px;}
	}




</style>
