package top.shenluw.nsfn.sensitivewords.ahocorasick

import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie
import top.shenluw.nsfn.sensitivewords.DesensitizeStrategyFactory
import top.shenluw.nsfn.sensitivewords.SensitizeProcessor
import java.util.*

/**
 * @author Shenluw
 * 创建日期：2019/6/8 21:18
 */
class AhoCorasickDoubleArrayTrieSensitizeProcessor(words: Set<String>, factory: DesensitizeStrategyFactory)
	: SensitizeProcessor(words, factory) {
	private var trie: AhoCorasickDoubleArrayTrie<String>? = null

	override fun desensitize(text: CharSequence): CharSequence? {
		val strategy = factory.create(text, this.escape)
		trie?.parseText(text, AhoCorasickDoubleArrayTrie.IHit { begin, end, value ->
			strategy.desensitize(begin, end, value)
		})
		return strategy.getResult()
	}


	override fun containsMatch(text: CharSequence): Boolean {
		return this.trie?.matches(text.toString()) ?: false
	}

	override fun updateWords(words: Set<String>) {
		val treeMap = TreeMap<String, String>()
		words.forEach {
			treeMap[it] = it
		}
		trie = AhoCorasickDoubleArrayTrie()
		trie?.build(treeMap)
	}
}