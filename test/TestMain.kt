import com.vitorblog.json.Json
import com.vitorblog.json.model.exception.JsonValueNotFoundException

object TestMain {

    @JvmStatic
    fun main(args: Array<String>) {
        val json = Json()
            .set("string", "hello")
            .set("int", 50)
            .set("boolean", true)
            .set("list", arrayListOf("Hello", "World!"))
            .set("json", Json()
                .set("hello", "World!")
                .set("json2", Json()
                    .set("hello2", "World!2")
                    .set("json3", Json()
                        .set("message", "you find me!")
                    )
                )
            )
        println("get: ${json["string"].asString()}")
        println("smartGet: ${json["json.json2.json3.message"].asString()}")
        println("getOrNull: ${json.getOrNull("fake key")}")
        try {
            println("get Exception: ${json["fake key"]}")
        } catch (exception:JsonValueNotFoundException) {
            println("get Exception: ${exception.message}")
        }
        println(json.toString())
    }

}