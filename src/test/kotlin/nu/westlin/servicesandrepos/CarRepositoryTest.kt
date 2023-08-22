package nu.westlin.servicesandrepos

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.simple.SimpleJdbcInsert

class CarRepositoryTest : JdbcTest() {

    private lateinit var repository: CarRepository

    @BeforeAll
    fun init() {
        repository = CarRepository(jdbcTemplate)
    }

    @Test
    fun `id by regno`() {
        val car = Car(
            regNo = Car.SwedishRegNo("ABC123"),
            year = 1986,
            manufacturer = Manufacturer(name = "Mable Austin", country = "Not home"),
            model = Model(name = "Preston Hood"),
            engine = Engine(cc = 3880, cylinders = 4)
        )

        val id = 1
        saveCar(id, car)

        assertThat(repository.idByRegNo(car.regNo)).isEqualTo(id)
    }

    @Test
    fun `car by regno`() {
        val car = Car(
            regNo = Car.SwedishRegNo("ABC123"),
            year = 1986,
            manufacturer = Manufacturer(name = "Mable Austin", country = "Far, far away"),
            model = Model(name = "Preston Hood"),
            engine = Engine(cc = 3880, cylinders = 4)
        )

        val id = 42
        saveCar(id, car)

        assertThat(repository.byRegNo(
            regNo = car.regNo,
            manufacturer = car.manufacturer,
            model = car.model,
            engine = car.engine
        )).isEqualTo(car)
    }

    private fun saveCar(id: Int, car: Car) {
        SimpleJdbcInsert(jdbcTemplate)
            .withTableName("car")
            .execute(
                mapOf(
                    "id" to id,
                    "regno" to car.regNo.value,
                    "year" to car.year
                )
            )
    }
}