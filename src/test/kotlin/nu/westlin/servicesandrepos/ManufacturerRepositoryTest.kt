package nu.westlin.servicesandrepos

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.simple.SimpleJdbcInsert

class ManufacturerRepositoryTest : JdbcTest() {

    private lateinit var manufacturerRepository: ManufacturerRepository

    @BeforeAll
    fun init() {
        manufacturerRepository = ManufacturerRepository(jdbcTemplate)
    }

    @Test
    fun `get all`() {
        val manufacturers = listOf(
            ManufacurerWithIds(manufacturer = Manufacturer(name = "Volvo", country = "Sweden"), id = 1, carId = 1),
            ManufacurerWithIds(manufacturer = Manufacturer(name = "Ford", country = "USA"), id = 2, carId = 2),
            ManufacurerWithIds(manufacturer = Manufacturer(name = "Peugeot", country = "France"), id = 3, carId = 3)
        )

        save(manufacturers)

        assertThat(manufacturerRepository.all()).containsExactlyInAnyOrderElementsOf(manufacturers.map { it.manufacturer })
    }

    @Test
    fun `by car id`() {
        val manufacurerWithIds =
            ManufacurerWithIds(manufacturer = Manufacturer(name = "Volvo", country = "Sweden"), id = 1, carId = 1)

        save(manufacurerWithIds)

        assertThat(manufacturerRepository.byCarId(manufacurerWithIds.carId)).isEqualTo(manufacurerWithIds.manufacturer)
    }

    private fun save(manufacurerWithIds: ManufacurerWithIds) {
        save(listOf(manufacurerWithIds))
    }

    private fun save(manufacurerWithIdsList: List<ManufacurerWithIds>) {
        SimpleJdbcInsert(jdbcTemplate)
            .withTableName("manufacturer")
            .executeBatch(
                *manufacurerWithIdsList.map { manufacurerWithIds ->
                    mapOf(
                        "id" to manufacurerWithIds.id,
                        "name" to manufacurerWithIds.manufacturer.name,
                        "country" to manufacurerWithIds.manufacturer.country
                    )
                }.toTypedArray()
            )

        SimpleJdbcInsert(jdbcTemplate)
            .withTableName("manufaturer_car")
            .executeBatch(
                *manufacurerWithIdsList.map { manufacurerWithIds ->
                    mapOf(
                        "manufacturerid" to manufacurerWithIds.id,
                        "carid" to manufacurerWithIds.carId,
                    )
                }.toTypedArray()
            )
    }

    private data class ManufacurerWithIds(val manufacturer: Manufacturer, val id: Int, val carId: Int)
}