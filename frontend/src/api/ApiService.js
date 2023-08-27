import axios from 'axios';

const API_BACKEND_SERVER = "http://localhost:8080"

export function doLogin(email, password) {
    return axios.post(`${API_BACKEND_SERVER}/login`, { email: email, password: password })
}

export function doUserRegister(email, username, password) {
    return axios.post(`${API_BACKEND_SERVER}/users`, { email: email, password: password, username: username })
}

export function doGetUserInfo(userId) {
    return axios.get(`${API_BACKEND_SERVER}/users/${userId}`)
}