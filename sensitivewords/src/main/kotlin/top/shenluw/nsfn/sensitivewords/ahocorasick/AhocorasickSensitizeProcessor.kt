package top.shenluw.nsfn.sensitivewords.ahocorasick

import org.ahocorasick.trie.Trie
import top.shenluw.nsfn.sensitivewords.SensitizeProcessor

/**
 * @author Shenluw
 * 创建日期：2019/6/6 17:24
 */
class AhocorasickSensitizeProcessor(words: Set<String>) : SensitizeProcessor(words) {
	private var trie: Trie? = null

	override fun desensitize(text: CharSequence): CharSequence? {
		val sb = StringBuilder(text)
		var offset = 0
		var len: Int
		val escapeLen = this.escape.length

		trie?.parseText(text)?.forEach {
			len = it.end - it.start + 1
			sb.replace(it.start - offset, it.end + 1 - offset, this.escape)
			offset += len - escapeLen
		}
		return sb.toString()
	}

	override fun containsMatch(text: CharSequence): Boolean {
		return this.trie?.containsMatch(text) ?: false
	}

	override fun updateWords(words: Set<String>) {
		trie = Trie.builder().addKeywords(words)
				.build()
	}
}