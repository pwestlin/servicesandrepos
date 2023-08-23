package nu.westlin.servicesandrepos

import org.springframework.stereotype.Service

@Service
class CarService(
    private val carRepository: CarRepository,
    private val manufacturerRepository: ManufacturerRepository
) {

    fun getCarByRegNo(regNo: Car.RegNo): Car {

        val carId = carRepository.idByRegNo(regNo)


        return carRepository.byRegNo(
            regNo = regNo,
            manufacturer = manufacturerRepository.byCarId(carId),
            model = Model(name = "TODO"),
            engine = Engine(cc = 0, cylinders = 0)
        )
    }
}