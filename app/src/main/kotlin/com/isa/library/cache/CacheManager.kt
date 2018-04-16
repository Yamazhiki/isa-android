package com.isa.library.cache

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.ConcurrentHashMap

@Suppress("EXPERIMENTAL_FEATURE_WARNING")
class CacheManager<in K, out V>(private val computable: Computable<K, V>) {
    private val cache = ConcurrentHashMap<K, Deferred<V>>()


    fun get(k: K, completion: (V) -> Unit) {
        var task = cache[k]
        if (task == null) {
            task = async {
                computable.get(k)
            }
            cache.putIfAbsent(k, task)
        }
        launch(CommonPool) {
            while (true) {
                if (task.isActive) continue
                if (task.isCompleted) break
            }
            completion(task.await())
        }
    }
}