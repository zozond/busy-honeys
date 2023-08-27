import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';

export const stock = defineStore('stock', () => {
    const adressJson = ref();
    const apiUrl = `http://localhost:8080/quote`;
    axios.get(apiUrl);

    axios.get(apiUrl)
    .then(function (response) {
        // handle success
        console.log('success');
        console.log(response);
        adressJson.value = response;
    })
    .catch(function (error) {
        // handle error
        console.log(error);
    });


    return { stock, adressJson }
});