package one.block.recenteosblocks.util

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    fun hasBeenHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun getContent(): T = content
}