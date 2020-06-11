import com.vitorblog.json.Json
import com.vitorblog.json.model.JsonValue
import com.vitorblog.json.util.TypeUtils

object TestMain {

    @JvmStatic
    fun main(args: Array<String>) {
        val model = Model()
        model.id = 555
        val json = Json.toJson(model)!!

        println(json.toFormattedString())

        val otherModel = Json.parse("{\n" +
                "    \"name\": \"alo\",\n" +
                "    \"inUse\": true,\n" +
                "    \"id\": 2222,\n" +
                "    \"iamInUse\": false,\n" +
                "}")!!.toClass(Model::class.java) as Model
        println(otherModel)

    }

}