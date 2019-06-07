package top.shenluw.nsfn.sensitivewords

/**
 * 敏感词文本处理工具
 *
 * @author Shenluw
 * 创建日期：2019/6/6 17:16
 */
abstract class SensitizeProcessor(words: Set<String>) {
	/* 敏感词替换字符 */
	var escape: String = "*"

	init {
		this.updateWords(words)
	}

	abstract fun desensitize(text: CharSequence): CharSequence?

	abstract fun containsMatch(text: CharSequence): Boolean

	abstract fun updateWords(words: Set<String>)
}