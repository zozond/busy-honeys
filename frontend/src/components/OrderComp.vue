<template>
    <div>
      <h1>매수/매도</h1>
      <ul class="button">
        <li><button class="sell" @click="order(0)">매도</button></li>
        <li><button class="buy" @click="order(1)">매수</button></li>
      </ul>
      <div class="order" v-show="orderState[0]">
        <h2>매도창</h2>
      </div>
      <div class="order" v-show="orderState[1]">
        <h2>매수창</h2>
      </div>
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

let orderState = ref([1,0]);
const order =index=> {
orderState.value = orderState.value.map((v,k)=>{
    return k == index ? true : false;
});
}

</script>

<style lang="scss" scoped>
.order {
    margin: 20px 0;
    background: pink;
    height: 200px;
    border-radius: 5px;
}

.button {

    > li {display: inline-block; padding: 0 4px;}

    button {
        padding: 0.2em 1.2em;
        font-size: 18px;
        border: none;
        border-radius: 5px;
        color: #fff;

        &.sell {background-color: rgb(246, 64, 88);}
        &.buy {background-color: rgb(72, 59, 255);}
    }
    
}
</style>