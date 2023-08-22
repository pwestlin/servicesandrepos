package nu.westlin.servicesandrepos

import org.springframework.jdbc.core.JdbcOperations
import org.springframework.stereotype.Repository
import org.springframework.jdbc.core.queryForObject

data class Car(
    val regNo: RegNo,
    val year: Int,
    val manufacturer: Manufacturer,
    val model: Model,
    val engine: Engine
) {


    sealed interface RegNo {
        val value: String
    }

    @JvmInline
    value class SwedishRegNo(override val value: String) : RegNo {

        init {
            require(regEx.matches(value)) { "Badly formed registration number: $value. RegEx: ${regEx.pattern}" }
        }

        companion object {
            val regEx: Regex
                get() = Regex("""^([A-Z]{3}\d{3}|[A-Z]{3}\d{2}[A-Z])$""")
        }
    }

    companion object
}

@Repository
class CarRepository(
    private val jdbcOperations: JdbcOperations
) {

    fun idByRegNo(
        regNo: Car.RegNo
    ): Int {
        return jdbcOperations.queryForObject("select id from car where regno = ?", Int::class.java, regNo.value)!!
    }

    fun byRegNo(
        regNo: Car.RegNo,
        manufacturer: Manufacturer,
        model: Model,
        engine: Engine
    ): Car {
        return jdbcOperations.queryForObject("select * from car where regno = ? ", regNo.value) { rs, _ ->
            Car(
                regNo = regNo,
                year = rs.getInt("year"),
                manufacturer = manufacturer,
                model = model,
                engine = engine
            )
        }
    }

    companion object
}