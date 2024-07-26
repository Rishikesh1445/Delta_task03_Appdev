package com.example.meritmatch.retrofit

import com.example.meritmatch.retrofit.dataclass.GameSpin
import com.example.meritmatch.retrofit.dataclass.Task
import com.example.meritmatch.retrofit.dataclass.TransactionResponse
import com.example.meritmatch.retrofit.dataclass.UserId
import com.example.meritmatch.retrofit.dataclass.Userdetail
import com.example.meritmatch.retrofit.dataclass.UsersName
import com.example.meritmatch.retrofit.dataclass.addtask
import com.example.meritmatch.retrofit.dataclass.logIn
import com.example.meritmatch.retrofit.dataclass.notify
import com.example.meritmatch.retrofit.dataclass.rate
import com.example.meritmatch.retrofit.dataclass.response
import com.example.meritmatch.retrofit.dataclass.transactionRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface meritMatchAPI {

    @POST("/register")
    suspend fun signUp(@Body signup: logIn): Response<response>

    @POST("/login")
    suspend fun signIn(@Body signIn: logIn): Response<UserId>

    @POST("/userdetails")
    suspend fun userdetails(@Body user: UserId): Response<Userdetail>

    @POST("/newtask")
    suspend fun addtask(@Body task: addtask): Response<response>

    @POST("/tasks")
    suspend fun fetchTasksAll(@Body user: UserId): Response<List<Task>>

    @POST("/taskreserved")
    suspend fun fetchReservedTask(@Body user: UserId): Response<List<Task>>

    @POST("/taskcompleted")
    suspend fun fetchCompletedTask(@Body user: UserId): Response<List<Task>>

    @POST("/taskposted")
    suspend fun fetchPostedTask(@Body user: UserId): Response<List<Task>>

    @POST("/reserve")
    suspend fun reserve(@Body user: UserId): Response<response>

    @POST("/complete")
    suspend fun complete(@Body user: UserId): Response<response>

    @POST("/edit")
    suspend fun editTask(@Body task:Task): Response<response>

    @POST("/reputation")
    suspend fun rate(@Body rating: rate): Response<response>

    @POST("/usersList")
    suspend fun userNames(@Body currentUser: UserId): Response<List<UsersName>>

    @POST("/transactions")
    suspend fun transaction(@Body transaction: transactionRequest) : Response<TransactionResponse>

    @POST("/extra")
    suspend fun extras(@Body detail:GameSpin): Response<response>

    @POST("/notify")
    suspend fun notify(@Body ids: List<notify>): Response<response>
}