package com.isa.app.library

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject


data class PageModel<out T>(
        val pageIndex: Int,
        val total: Int,
        val retrieved: List<T> = listOf(),
        val data: List<T> = listOf()
)

/**
 * 分页模型
 * 对应可能返回的数据类型包括: 总数目, 当前记录, 当前页码(可有可无)
 */
fun <T, P> pagination(
        reload: Observable<P>,
        loadMore: Observable<P>,
        data: (Pair<Int, P>) -> Observable<Triple<Int, Int, List<T>>>): Observable<Triple<Int, Int, List<T>>> {
    val subject = BehaviorSubject.createDefault(true)
    val can = Observable.combineLatest(loadMore, subject, BiFunction<P, Boolean, Pair<Int, P>> { t0, t1 -> Pair(if (t1) 1 else 0, t0) })
    return Observable.merge(reload.map { Pair(0, it) }, can)
            .scan { origin, info ->
                val page = if (info.first != 0) origin.first + 1 else 1
                Pair(page, info.second)
            }
            .flatMap(data)
}