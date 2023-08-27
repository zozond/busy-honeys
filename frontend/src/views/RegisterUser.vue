<template>
  <div class="containRoute">
    <h1>회원가입</h1>

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
        label="Username"
        type="text"
        hide-details="true"
        style="margin: 4px"
        v-model="username"
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
        <v-btn
          color="light-blue"
          variant="outlined"
          @click="sendRegisterForm()"
        >
          제출
        </v-btn>
        <v-btn color="blue" variant="text"> 홈으로 </v-btn>
      </div>
    </div>
  </div>
</template>

<script>
import { doUserRegister } from "../api/ApiService.js";
export default {
  data: () => ({
    email: "",
    password: "",
    username: "",
    rules: [
      (value) => (!value ? "Required." : true),
      (value) => (value && value.length >= 6) || "Min 8 characters",
    ],
  }),
  methods: {
    sendRegisterForm() {
      doUserRegister(this.email, this.username, this.password)
        .then((res) => {
          if (res.status == 200) {
            window.location.href = "/";
          } else {
            alert("회원가입 실패");
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
