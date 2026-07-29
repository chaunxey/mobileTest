package com.aisenz.moblietest.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseMviViewModel<STATE : BaseMviUiState, INTENT : BaseMviIntent, EFFECT : BaseMviSideEffect>(
    initialState: STATE
) :
    ViewModel() {

    //状态管理：单一数据源
    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<STATE> = _uiState.asStateFlow()

    //用户意图 统一入口 view->intent
    abstract fun processIntent(intent: INTENT)

    // 副作用管理
    private val _sideEffect = MutableSharedFlow<EFFECT>()

    val sideEffect: SharedFlow<EFFECT> = _sideEffect.asSharedFlow()

    //使用纯函数更新状态 Reducer
    protected fun updateUiState(reducer: STATE.() -> STATE) {
        _uiState.update(reducer)
    }

    //发送副作用->view
    fun sendSideEffect(effect: EFFECT) {
        viewModelScope.launch {
            _sideEffect.emit(effect)
        }
    }


}