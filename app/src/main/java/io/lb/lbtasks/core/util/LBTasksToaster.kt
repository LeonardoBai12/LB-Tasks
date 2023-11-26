package io.lb.lbtasks.core.util

import android.content.Context

class LBTasksToaster(private val context: Context) : Toaster {
    override fun showToast(message: String) {
        context.showToast(message)
    }

    override fun showToast(resId: Int) {
        context.showToast(resId)
    }
}
