package com.mads.calculator

object InMemoryHistoryCache {

    private const val MAX_CACHE_SIZE = 10

    private val cache = ArrayList<Expression>(MAX_CACHE_SIZE)

    fun put(expression: Expression) {
        cache.add(0, expression)
        if (cache.size > MAX_CACHE_SIZE) {
            val recentEntries = cache.subList(0, MAX_CACHE_SIZE)
            cache.clear()
            cache.addAll(recentEntries)
        }
    }

    fun get(index: Int): Expression? {
        if (index < cache.size) {
            return cache.get(index)
        }
        return null
    }

    fun getAll(): List<Expression> {
        return cache
    }
}