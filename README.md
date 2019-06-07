# spring-nsfn-validation-starter

Spring实现请求参数敏感词校验,使用Aho–Corasick作为检测算法
项目采用Kotlin实现

### 外部依赖库: 
-  [aho-corasick](https://github.com/robert-bor/aho-corasick)
-  [AhoCorasickDoubleArrayTrie](https://github.com/hankcs/AhoCorasickDoubleArrayTrie)

### 使用示例：

#### 添加依赖

~~~groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}	
dependencies {
    // 低版本gradle使用
    // compile 'com.github.shenluw.spring-nsfn-validation-starter:nsfn-validation-starter:Tag'
    // 版本号查看仓库tag
    implementation 'com.github.shenluw.spring-nsfn-validation-starter:nsfn-validation-starter:Tag'
}
~~~

#### 添加注解

~~~kotlin
@RestController
@Validated
class TestController {

	@RequestMapping("/simple")
	fun test(@Sensitive text: CharSequence?): CharSequence? {
		log.info("simple: {}", text)
		return text
	}
}
~~~

#### 配置敏感词
在application.yaml中添加配置，敏感词读取优先级 **words > word-file > word-dir**
~~~yaml
nsfn:
  words: 敏感词,敏感
  word-file: filepath # 敏感词文件路径，一行表示一个词
  word-dir: filedir # 敏感词目录，会读取目录下所有文件内容
~~~

也可以自己配置WordStore作为Bean，也可以自定义实现WordStore类，添加不同敏感词的读取策略
~~~kotlin
@Bean
fun customStore(): MemoryWordStore {
    return MemoryWordStore(hashSetOf("word"))
}
~~~