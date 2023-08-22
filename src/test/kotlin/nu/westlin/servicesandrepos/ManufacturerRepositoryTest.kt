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
        val manufacturers = mapOf(
            1 to Manufacturer(name = "Volvo", country = "Sweden"),
            2 to Manufacturer(name = "Ford", country = "USA"),
            3 to Manufacturer(name = "Peugeot", country = "France")
        )

        saveManufacturers(manufacturers)

        assertThat(manufacturerRepository.all()).containsExactlyInAnyOrderElementsOf(
            manufacturers.values
        )
    }

    private fun saveManufacturers(manufacturers: Map<Int, Manufacturer>) {
        // TODO petves: Utan list?
        val list = manufacturers.map { ObjectWithId(id = it.key, theObject = it.value) }
        SimpleJdbcInsert(jdbcTemplate)
            .withTableName("manufacturer")
            .executeBatch(
                *list.map { manufacturer ->
                    mapOf(
                        "id" to manufacturer.id,
                        "name" to manufacturer.theObject.name,
                        "country" to manufacturer.theObject.country
                    )
                }.toTypedArray()
            )
    }
}