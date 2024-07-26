package com.example.meritmatch

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.meritmatch.sealedClass.LoginScreen
import com.example.meritmatch.dataClass.CurrentUser
import com.example.meritmatch.retrofit.dataclass.Task
import com.example.meritmatch.retrofit.dataclass.UserId
import com.example.meritmatch.retrofit.dataclass.addtask
import com.example.meritmatch.retrofit.dataclass.logIn
import com.example.meritmatch.retrofit.dataclass.rate
import com.example.meritmatch.retrofit.dataclass.transactionRequest
import com.example.meritmatch.retrofit.retroFitInstance
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel:ViewModel(){
    private val _user = mutableStateOf(CurrentUser())
    val user: State<CurrentUser> =_user

    fun signUp(username:String, password:String, loginNav:NavController, context: Context){
        val data = logIn(username, password)
        viewModelScope.launch {
            try {
                val response = retroFitInstance.api.signUp(data)
                response.body()?.message?.let {
                    Log.d("loginPage", it)
                    Toast.makeText(context, "Sign In to continue", Toast.LENGTH_SHORT).show()
                    loginNav.navigate(LoginScreen.SignIn.route)
                }
            }catch (e:Exception){
                e.message?.let { Log.d("loginPage", "error : $it") }
            }
        }
    }

    fun signIn(username: String, password: String, mainNav: NavController, context: Context){
        val data = logIn(username, password)
        viewModelScope.launch{
            try{
                val response = retroFitInstance.api.signIn(data)
                if(response.code() == 200){
                    Log.d("loginPage", "Login Successfully")
                    mainNav.navigate(ScreenMain.userScreen.route)
                    _user.value=_user.value.copy(
                        userid = response.body()?.userid,
                    )
                }else{
                    Toast.makeText(context, "Invalid Username or Password", Toast.LENGTH_SHORT).show()
                }
            }catch (e:Exception){
                e.message?.let { Log.d("loginPage" , "error : $it") }
            }
        }
    }

    fun userdetails(){
        val data = user.value.userid?.let { UserId(it, 0) }
        viewModelScope.launch {
            try {
                val response = data?.let { retroFitInstance.api.userdetails(it) }
                if (response != null) {
                    if(response.code() == 200){
                        Log.d("loadTask", "Fetched Successfully")
                        _user.value = _user.value.copy(
                            username = response.body()?.username,
                            karmaPoint = response.body()?.karmaPoints,
                            reserved = response.body()?.reserved,
                            completed = response.body()?.completed,
                            reputation = response.body()?.reputation
                        )
                    }
                }
            }catch (e:Exception){
                e.message?.let { Log.d("loadTask" , "error : $it") }
            }
        }
    }

    fun fetchTask(){
        val data = user.value.userid?.let { UserId(it,0) }
        viewModelScope.launch {
            try {
                val response = data?.let { retroFitInstance.api.fetchTasksAll(it) }
                if (response != null) {
                    if (response.code() == 200){
                        _user.value = response.body()?.let { _user.value.copy(tasks = it) }!!
                    }
                }
            }catch (e:Exception){
                e.message?.let { Log.d("loadTask", "error: $it") }
            }
        }
    }

    fun fetchReservedTask(){
        val data = user.value.userid?.let { UserId(it,0) }
        viewModelScope.launch{
            try {
                val response = data?.let{ retroFitInstance.api.fetchReservedTask(it)}
                if (response != null) {
                    if (response.code() == 200){
                        _user.value = response.body()?.let { _user.value.copy(rtasks = it) }!!
                    }
                }
            }catch (e:Exception){
                e.message?.let { Log.d("loadTask", "error: $it") }
            }
        }
    }

    fun fetchCompletedTask(){
        val data = user.value.userid?.let { UserId(it,0) }
        viewModelScope.launch{
            try {
                val response = data?.let{ retroFitInstance.api.fetchCompletedTask(it)}
                if (response != null) {
                    if (response.code() == 200){
                        _user.value = response.body()?.let { _user.value.copy(ctasks = it) }!!
                    }
                }
            }catch (e:Exception){
                e.message?.let { Log.d("loadTask", "error: $it") }
            }
        }
    }

    fun fetchPostedTask(){
        val data = user.value.userid?.let { UserId(it,0) }
        viewModelScope.launch{
            try {
                val response = data?.let{ retroFitInstance.api.fetchPostedTask(it)}
                if (response != null) {
                    if (response.code() == 200){
                        _user.value = response.body()?.let { _user.value.copy(ptasks = it) }!!
                    }
                }
            }catch (e:Exception){
                e.message?.let { Log.d("loadTask", "error: $it") }
            }
        }
    }

    fun postTask(title: String, description: String,location: String, karmaPoints: Double){
        val data = user.value.userid?.let { addtask(title, description,location, karmaPoints, it) }
        viewModelScope.launch {
            try {
                val response = data?.let { retroFitInstance.api.addtask(it) }
                if (response != null) {
                    if(response.code() == 200){
                        response.body()?.let { Log.d("loadTask", it.message) }
                    }
                }
            }catch (e:Exception){
                e.message?.let { Log.d("loadTask", "error: $it") }
            }
        }
    }

    fun reserve(taskId:Int){
        val data = user.value.userid?.let { UserId(it, taskId) }
        viewModelScope.launch {
            try {
                val response = data?.let { retroFitInstance.api.reserve(it) }
                if (response != null) {
                    if (response.code() == 200){
                        response.body()?.let { Log.d("taskStatus", it.message) }
                    }
                }
            }catch (e:Exception){
                e.message?.let { Log.d("loadTask", "error: $it") }
            }
        }
    }

    fun complete(taskId:Int){
        val data = user.value.userid?.let { UserId(it, taskId) }
        viewModelScope.launch {
            try {
                val response = data?.let { retroFitInstance.api.complete(it) }
                if (response != null) {
                    if (response.code() == 200){
                        response.body()?.let { Log.d("taskStatus", it.message) }
                    }
                }
            }catch (e:Exception){
                e.message?.let { Log.d("loadTask", "error: $it") }
            }
        }
    }

    fun editTask(id:Int, title: String,location: String, description: String, karmaPoints: Double){
        val data = Task(id, title, description,location, karmaPoints, false, false)
        viewModelScope.launch {
            try {
                val response = retroFitInstance.api.editTask(data)
                if(response.code() == 200){
                    response.body()?.let { Log.d("loadTask", it.message) }
                }
            }catch (e:Exception){
                e.message?.let { Log.d("loadTask", "error: $it") }
            }
        }
    }

    fun rating(id: Int, who: Boolean, reputation:Double){
        val data = rate(id, who, reputation)
        viewModelScope.launch{
            try {
                val response = retroFitInstance.api.rate(data)
                if(response.code() == 200){
                    response.body()?.let { Log.d("reputation", it.message) }
                }
            }catch (e:Exception){
                e.message?.let { Log.d("loadTask", "error: $it") }
            }
        }
    }

    fun usersNames(){
        val data = user.value.userid?.let { UserId(it, 0) }
        viewModelScope.launch {
            try {
                val response = data?.let { retroFitInstance.api.userNames(it) }
                if (response != null) {
                    if(response.code() == 200){
                        Log.d("loadtrans", "Success")
                        _user.value = response.body()?.let { _user.value.copy(otherUsers = it) }!!
                    }
                }
            }catch (e:Exception){
                e.message?.let { Log.d("loadTrans", "error: $it") }
            }
        }
    }

    fun transaction(user2:Int){
        val data = user.value.userid?.let { transactionRequest(it, user2) }
        viewModelScope.launch {
            try {
                val response = data?.let { retroFitInstance.api.transaction(it) }
                if (response != null) {
                    if(response.code() == 200){
                        Log.d("loadtrans", "Transaction Success")
                        _user.value = _user.value.copy(transactionsUser = response.body())
                    }
                }
            }catch (e:Exception){
                e.message?.let { Log.d("loadTrans", "error: $it") }
            }
        }
    }

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private var _tasks = MutableStateFlow(listOf(Task(0,"Summa","Summa","",0.0,false,false)))
    fun stateFLowTasks(){
        val combined = user.value.ctasks + user.value.rtasks + user.value.ptasks + user.value.tasks
        val locationlist = combined.map { it.location }

        _user.value = _user.value.copy(locations = locationlist)
        if(combined != null) {
            _tasks.value = combined
        }
        Log.d("searchFunction", locationlist.toString())
    }

    val taskstoshow = searchText
        .debounce(100L)
        .onEach { _isSearching.update { true } }
        .combine(_tasks){ text, task ->
            if(text.isBlank()){
                task
            }else{
                delay(100L)
                task.filter{
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _tasks.value
        )

    fun onSearchTextChange(text: String){
        _searchText.value = text
    }

    fun changeKarmaFilter(a:Int?, b:Int?){
        _user.value = _user.value.copy(karmaFilter = Pair(a,b))
    }

    fun logout(){
        _user.value = CurrentUser()
    }
}