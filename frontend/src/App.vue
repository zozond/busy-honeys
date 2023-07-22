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
import testChart from './components/testChart.vue'


</script>

<template>
  <testChart />
</template>

<style scoped>

</style>
