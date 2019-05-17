package app.ctrlyati.base.usecase

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.Disposable

abstract class SingleUseCase<I, O>(
    private val preThreadScheduler: Scheduler,
    private val postThreadScheduler: Scheduler
) : LifecycleObserver {

    private val disposables: ArrayList<Disposable> = arrayListOf()
    protected abstract fun create(input: I): Single<O>

    fun execute(lifecycle: Lifecycle, input: I, onSuccess: (O) -> Unit, onError: (Throwable) -> Unit) {
        disposables += create(input)
            .subscribeOn(preThreadScheduler)
            .observeOn(postThreadScheduler)
            .subscribe(onSuccess, onError)
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        disposables.forEach { it.dispose() }
    }
}