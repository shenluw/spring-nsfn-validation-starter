package top.shenluw.nsfn.sensitivewords

/**
 * @author Shenluw
 * created：2019/6/9 20:57
 */
interface DesensitizeStrategy {
	fun desensitize(begin: Int, end: Int, value: CharSequence)

	fun getResult(): CharSequence
}

open class PaddingToDesensitizeStrategy : DesensitizeStrategy {
	private val sb: StringBuilder
	private val escape: String

	constructor(source: CharSequence, escape: Char) {
		this.escape = escape.toString()
		this.sb = StringBuilder(source)
	}

	override fun desensitize(begin: Int, end: Int, value: CharSequence) {
		val escape = this.escape
		for (i in 0 until value.length) {
			sb.replace(begin + i, begin + i + 1, escape)
		}
	}

	override fun getResult(): CharSequence {
		return sb.toString()
	}

}

interface DesensitizeStrategyFactory {
	fun create(text: CharSequence, escape: String): DesensitizeStrategy
}

class DefaultDesensitizeStrategyFactory : DesensitizeStrategyFactory {
	override fun create(text: CharSequence, escape: String): DesensitizeStrategy {
		return PaddingToDesensitizeStrategy(text, escape[0])
	}

}