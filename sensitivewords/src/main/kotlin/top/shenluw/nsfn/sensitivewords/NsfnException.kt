package top.shenluw.nsfn.sensitivewords

/**
 * @author Shenluw
 * created：2019/6/7 21:53
 */
class NsfnException : RuntimeException {
	constructor() : super()
	constructor(message: String?) : super(message)
	constructor(message: String?, cause: Throwable?) : super(message, cause)
	constructor(cause: Throwable?) : super(cause)
	constructor(message: String?, cause: Throwable?, enableSuppression: Boolean, writableStackTrace: Boolean) : super(message, cause, enableSuppression, writableStackTrace)
}