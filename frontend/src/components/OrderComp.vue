<template>
    <div id="transaction" class="half">
        <h1>매수/매도</h1>
        <div id="chart"></div>
        <ul class="button">
            <li>
                <button class="sell" @click="order(0)">매도</button>
            </li>
            <li>
                <button class="buy" @click="order(1)">매수</button>
            </li>
            <li>
                <button class="correct" @click="order(2)">정정/취소</button>
            </li>
            <li>
                <button class="trStatus" @click="order(3)">체결/미체결</button>
            </li>
        </ul>
        <div class="order" v-show="orderState[0]">
            <h2>매도창</h2>
        </div>
        <div class="order" v-show="orderState[1]">
            <h2>매수창</h2>
        </div>
        <div class="order" v-show="orderState[2]">
            <h2>정정</h2>
        </div>
        <div class="order" v-show="orderState[3]">
            <h2>체결/미체결</h2>
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

import ApexCharts from 'apexcharts';

let orderState = ref([1, 0, 0, 0]);
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
    display: flex;
    flex-wrap: wrap;
    // gap: 10px;

    > li {
        flex: 1;
    }
    
    li:nth-of-type(2) > button,
    li:nth-of-type(3) > button, 
    li:nth-of-type(4) > button {
        border-left: none;
    }

    button {
        width: 100%;
        padding: 0.2em 1.2em;
        font-size: 18px;
        border: 1px solid #ebebeb;
        background-color: #fff;

        cursor: pointer;

        &:hover {
            background-color: #ebebeb;
            
        }
        // &.sell {background-color: rgb(246, 64, 88);}
        // &.buy {background-color: rgb(72, 59, 255);}
        // &.correct {background-color: rgb(83, 169, 82);}
        // &.trStatus {background-color: rgb(83, 169, 82);}
    }


    
}


</style>