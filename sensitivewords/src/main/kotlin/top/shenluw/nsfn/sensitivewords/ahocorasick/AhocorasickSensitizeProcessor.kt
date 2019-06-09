package top.shenluw.nsfn.sensitivewords.ahocorasick

import org.ahocorasick.trie.Trie
import top.shenluw.nsfn.sensitivewords.DesensitizeStrategyFactory
import top.shenluw.nsfn.sensitivewords.SensitizeProcessor

/**
 * @author Shenluw
 * 创建日期：2019/6/6 17:24
 */
class AhocorasickSensitizeProcessor(words: Set<String>, factory: DesensitizeStrategyFactory)
	: SensitizeProcessor(words, factory) {
	private var trie: Trie? = null

	override fun desensitize(text: CharSequence): CharSequence? {
		val strategy = factory.create(text, this.escape)
		trie?.parseText(text)?.forEach {
			strategy.desensitize(it.start, it.end, it.keyword)
		}
		return strategy.getResult()
	}

	override fun containsMatch(text: CharSequence): Boolean {
		return this.trie?.containsMatch(text) ?: false
	}

	override fun updateWords(words: Set<String>) {
		trie = Trie.builder().addKeywords(words)
				.build()
	}
}