package top.shenluw.nsfn.sensitivewords.store

import top.shenluw.nsfn.sensitivewords.WordStore

/**
 * @author Shenluw
 * created：2019/6/6 22:37
 */
class MemoryWordStore(val words: MutableSet<String>) : WordStore() {

	override fun get(): Set<String> {
		return words
	}
}