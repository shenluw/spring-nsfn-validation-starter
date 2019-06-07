package top.shenluw.nsfn.sensitivewords

import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*
import java.util.function.Consumer

/**
 * 敏感词数据中心，可以是内存，文件，在线获取
 *
 * @author Shenluw
 * created：2019/6/6 22:35
 */
abstract class WordStore {
	companion object;

	private val listeners = arrayListOf<Consumer<WordStore>>()

	abstract fun get(): Set<String>

	@Synchronized
	fun addListener(listener: Consumer<WordStore>) {
		this.listeners.add(listener)
	}

	@Synchronized
	fun notifyListen() {
		this.listeners.forEach { it.accept(this) }
	}

}

fun WordStore.Companion.load(reader: InputStreamReader): SortedSet<String> {
	val lines = reader.readLines()

	return lines
			.filter { !it.isBlank() }
			.map { it.trim() }
			.toSortedSet()
}

fun WordStore.Companion.load(file: File): SortedSet<String> {
	return load(InputStreamReader(FileInputStream(file)))
}

fun WordStore.Companion.load(dir: String): SortedSet<String> {
	val f = File(dir)
	if (!f.exists() || f.isFile) {
		throw NsfnException("sensitive words dir is not valid")
	}

	val set = sortedSetOf<String>()
	val files = f.listFiles()
	if (files.isNullOrEmpty()) {
		return set
	}

	files.forEach {
		if (it.isFile && it.canRead()) {
			set.addAll(load(it))
		}
	}
	return set
}