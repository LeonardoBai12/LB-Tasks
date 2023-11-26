package io.lb.lbtasks.core.util

class FakeToaster : Toaster {
    override fun showToast(message: String) {
        pretendToShowAToast(message)
    }

    override fun showToast(resId: Int) {
        pretendToShowAToastWithResId(resId)
    }
}

fun pretendToShowAToast(message: String) {
    println(message)
}

fun pretendToShowAToastWithResId(resId: Int) {
    println(resId)
}
