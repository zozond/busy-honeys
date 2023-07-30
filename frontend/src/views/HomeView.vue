<template>
  <div class="containRoute">
    <h1>D3 테스트중</h1>
    <svg :width="500" :height="300">
      <path fill="none" stroke="#76BF8A" stroke-width="3" :d="pathData"></path>
    </svg>
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

import * as d3 from "d3";

const data = [90, 72, 75, 25, 10, 92];

const xScale = d3
  .scaleLinear()
  .range([20, 480])
  .domain(d3.extent(data, (d, i) => i));

const yScale = d3.scaleLinear().range([280, 20]).domain([0, 100]);

const line = d3
  .line()
  .x((d, i) => xScale(i))
  .y((d) => yScale(d));

const pathData = line(data);

</script>


<style scoped>

</style>
