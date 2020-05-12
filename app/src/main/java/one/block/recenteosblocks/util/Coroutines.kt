package one.block.recenteosblocks.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException

object Coroutines {

    fun<T: Any> ioThenMain(work: suspend (() -> T?), callback: ((T?) -> Unit), onError: ((String) -> Unit)) =
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val data = CoroutineScope(Dispatchers.IO).async rt@ {
                    return@rt work()
                }.await()
                callback(data)
            } catch (e: IOException) {
                onError(e.toString())
            }
        }

    fun<T: Any> main(work: suspend (() -> T?)) =
        CoroutineScope(Dispatchers.Main).launch {
            work()
        }
}