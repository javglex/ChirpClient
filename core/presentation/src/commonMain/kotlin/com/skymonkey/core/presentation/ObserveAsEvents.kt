package com.skymonkey.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Observes a [Flow] as composable events and triggers the specified [onEvent] lambda for each emitted value.
 * Keeps observing as long as the [LifecycleOwner] is in the [Lifecycle.State.STARTED] state.
 *
 * @param flow The [Flow] to be observed for events.
 * @param key1 An optional key to determine when to restart the observation. Defaults to null.
 * @param key2 A second optional key to determine when to restart the observation. Defaults to null.
 * @param onEvent A lambda invoked with each emitted value from the [flow].
 */
@Composable
fun <T> ObserveAsEvents(
    flow: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (T) -> Unit
) {
    val lifecyleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecyleOwner, key1, key2) {
        lifecyleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect { onEvent(it) }
            }
        }
    }
}