<template>
  <div class="containRoute">
    <h1>로그인</h1>

    <div style="width: 500px">
      <v-text-field
        label="E-mail"
        type="email"
        hide-details="auto"
        :rules="rules"
        style="margin: 4px"
        v-model="email"
      ></v-text-field>

      <v-text-field
        label="Password"
        type="password"
        :rules="rules"
        hide-details="true"
        style="margin: 4px"
        v-model="password"
      ></v-text-field>
      <div style="display: flex; justify-content: space-between">
        <v-btn color="light-blue" variant="outlined" @click="sendLoginApi()">
          로그인
        </v-btn>
        <v-btn color="blue" variant="text" @click="toRegistrationPage()">
          회원가입
        </v-btn>
      </div>
    </div>
  </div>
</template>

<script>
import { doLogin } from "../api/ApiService.js";
export default {
  data: () => ({
    email: "",
    password: "",
    rules: [
      (value) => (!value ? "Required." : true),
      (value) => (value && value.length >= 6) || "Min 8 characters",
    ],
  }),
  methods: {
    toRegistrationPage() {
      window.location.href = "/registration";
    },
    sendLoginApi() {
      doLogin(this.email, this.password)
        .then((res) => {
          console.log(res.data.data);
          if (res.data.status == "OK") {
            sessionStorage.setItem("user", res.data.data.token);
            sessionStorage.setItem("userId", res.data.data.userId);
            window.location.href = "/";
          } else {
            alert("로그인 실패");
          }
        })
        .catch((err) => {
          console.log(err);
          alert("서버와의 연결이 정상이 아닙니다.");
        });
    },
  },
};
</script>

<style></style>
