package nu.westlin.servicesandrepos

import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CarServiceTest {

    private val carRepository: CarRepository = mockk()
    private val manufacturerRepository: ManufacturerRepository = mockk()

    private val service = CarService(
        carRepository = carRepository,
        manufacturerRepository = manufacturerRepository
    )

    @Test
    fun `car by regno`() {
        val car = Car(
            regNo = Car.SwedishRegNo("JZP728"),
            year = 1977,
            manufacturer = Manufacturer(name = "Opel", country = "Germany"),
            model = Model(name = "Ascona B"),
            engine = Engine(cc = 1900, cylinders = 4)
        )

        val carId = 42
        every { carRepository.idByRegNo(car.regNo) } returns carId
        every { manufacturerRepository.byCarId(carId) } returns car.manufacturer
        every {
            carRepository.byRegNo(
                regNo = car.regNo,
                manufacturer = car.manufacturer,
                model = car.model,
                engine = car.engine
            )
        } returns car

        assertThat(service.getCarByRegNo(car.regNo)).isEqualTo(car)

        verifySequence {
            carRepository.idByRegNo(car.regNo)
            manufacturerRepository.byCarId(carId)
        }
    }
}