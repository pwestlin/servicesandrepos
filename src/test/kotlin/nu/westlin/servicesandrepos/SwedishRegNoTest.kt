package nu.westlin.servicesandrepos

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class SwedishRegNoTest {

    @Test
    fun `correct regnumbers`() {
        listOf(
            "ABC123",
            "ABC12D"
        ).forEach { regNo ->
            assertThat(Car.SwedishRegNo(regNo).value).isEqualTo(regNo)
        }
    }

    @Test
    fun `incorrect regnumbers`() {
        listOf(
            "123ABC",
            "AbC123",
            "AB1234",
            "ABC12¤"
        ).forEach { regNo ->
            assertThatThrownBy { Car.SwedishRegNo(regNo) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Badly formed registration number: $regNo. RegEx: ${Car.SwedishRegNo.regEx.pattern}")
        }
    }
}