package nu.westlin.servicesandrepos

import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet

data class Manufacturer(
    val name: String,
    val country: String
) {

    companion object
}

@Repository
class ManufacturerRepository(
    private val jdbcOperations: JdbcOperations
) {

    fun all(): List<Manufacturer> {
        return jdbcOperations.query(
            "select * from manufacturer order by id asc",
            ManufacturerRowMapper()
        )
    }

    fun byCarId(carId: Int): Manufacturer {
        return jdbcOperations.queryForObject(
            "select * from manufacturer where id in (select manufacturerid from manufaturer_car where carid = ?)",
            ManufacturerRowMapper(),
            carId
        )!!
    }

    private class ManufacturerRowMapper : RowMapper<Manufacturer> {
        override fun mapRow(rs: ResultSet, rowNum: Int): Manufacturer {
            return Manufacturer(
                name = rs.getString("name"),
                country = rs.getString("country")
            )
        }

    }
}