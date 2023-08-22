package nu.westlin.servicesandrepos

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class ManufacturerRepositoryJdbcTest : JdbcTest() {

    private lateinit var manufacturerRepository: ManufacturerRepository

    @BeforeAll
    fun init() {
        manufacturerRepository = ManufacturerRepository(jdbcTemplate)
    }

    @Test
    fun `get all`() {
        assertThat(manufacturerRepository.all()).containsExactly(
            Manufacturer(id = 1, name = "Volvo"),
            Manufacturer(id = 2, name = "Ford"),
            Manufacturer(id = 3, name = "Peugeot"),
        )
    }
}