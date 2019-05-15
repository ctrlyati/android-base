package app.ctrlyati.base.presentation

open class UiModel<T, S>(
    val model: T?,
    var state: S
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UiModel<*, *>

        if (model != other.model) return false
        if (state != other.state) return false

        return true
    }

    override fun hashCode(): Int {
        var result = model?.hashCode() ?: 0
        result = 31 * result + (state?.hashCode() ?: 0)
        return result
    }
}

