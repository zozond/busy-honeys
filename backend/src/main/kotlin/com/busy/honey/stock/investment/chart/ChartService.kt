//package com.busy.honey.stock.investment.chart
//
//import jakarta.websocket.OnClose
//import jakarta.websocket.OnMessage
//import jakarta.websocket.OnOpen
//import jakarta.websocket.Session
//import jakarta.websocket.server.ServerEndpoint
//import org.springframework.stereotype.Service
//import java.util.*
//
//
//@Service
//@ServerEndpoint(value = " /quote/{:stockId}")
//class ChartService {
//    private val clients: Set<Session> = Collections.synchronizedSet(HashSet<Session>())
//
//    @OnMessage
//    @Throws(Exception::class)
//    fun onMessage(msg: String?, session: Session?) {
//    }
//
//    @OnOpen
//    fun onOpen(s: Session?) {
//    }
//
//    @OnClose
//    fun onClose(s: Session?) {
//    }
//}