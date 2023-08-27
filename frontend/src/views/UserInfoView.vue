<template>
  <div class="containRoute">
    <h1>유저 정보</h1>
    <v-table theme="dark" density="compact">
      <tbody>
        <tr>
          <td>유저이름</td>
          <td>{{ userName }}</td>
        </tr>
        <tr>
          <td>이메일</td>
          <td>{{ email }}</td>
        </tr>
        <tr>
          <td>계좌에 남은 금액</td>
          <td>{{ accountPrice }}</td>
        </tr>
        <tr>
          <td>수익률</td>
          <td>{{ totalEarningRate }}</td>
        </tr>
        <tr>
          <td>구매한 주식들</td>
          <td>{{ stocksInfo.join(",") }}</td>
        </tr>
      </tbody>
    </v-table>
  </div>
</template>

<script>
import { doGetUserInfo } from "../api/ApiService.js";
export default {
  data: () => ({
    accountPrice: 0,
    email: "",
    stocksInfo: [],
    totalEarningRate: 0,
    userName: "",
  }),
  created() {
    this.getUserInfo();
  },
  methods: {
    getUserInfo() {
      let userId = sessionStorage.getItem("userId");

      doGetUserInfo(userId)
        .then((res) => {
          this.accountPrice = res.data.data.accountPrice;
          this.email = res.data.data.email;
          this.stocksInfo = res.data.data.stocksInfo.map((item) => {
            return item.stocksName;
          });
          this.totalEarningRate = res.data.data.totalEarningRate;
          this.userName = res.data.data.userName;
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style></style>
