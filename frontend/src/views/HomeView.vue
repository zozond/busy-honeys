<template>
    <div class="containRoute">
        <h1>요약정보</h1>
        <Summary />
        <Notice />
    </div>
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

import Summary from './Summary.vue'
import Notice from './Notice.vue'



  


</script>

<style lang="scss" scoped>


</style>