<template>
  <main>
    <router-link to="/">Home</router-link>
    <router-link to="/stock">주식주문</router-link>
    <router-link to="/account">자산현황</router-link>
    <router-link to="/setting">환경설정</router-link>

    <router-link v-if="!isLogin" to="/login">로그인</router-link>
    <router-link v-else @click="logout()" to="/">로그아웃</router-link>
    <router-link v-if="isLogin" to="/info">유저정보</router-link>

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
} from "vue";
import { RouterLink, RouterView } from "vue-router";

// 아이콘
// https://fonts.google.com/icons?selected=Material+Icons

const isLogin = ref(false);

onMounted(() => {
  let user = sessionStorage.getItem("user");
  if (user) {
    isLogin.value = true;
  }
});

const logout = () => {
  sessionStorage.removeItem("user");
  sessionStorage.removeItem("userId");
  location.reload();
};
</script>

<style lang="scss">
main {
  > a {
    margin-right: 20px;
    font-size: 18px;
  }

  div.containRoute {
    margin: 20px 0;
    padding: 8px 12px;
    border-radius: 12px;
    border: 1px solid #eee;
    min-height: 300px;
  }
}

.contain {
  margin: 50px 0;
  padding: 20px 0;
  background: #ebebeb;
}
</style>
