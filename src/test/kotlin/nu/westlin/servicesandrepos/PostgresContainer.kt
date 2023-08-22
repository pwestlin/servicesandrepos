package nu.westlin.servicesandrepos

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import org.testcontainers.utility.TestcontainersConfiguration

/**
 * Postgres Docker container för Testcontainers.
 */
object PostgresContainer {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    val instance: PostgreSQLContainer<Nothing> by lazy { startContainer() }

    private fun startContainer(): PostgreSQLContainer<Nothing> {
        val reuse = System.getProperty("containers.reuse").toBoolean()
        logger.info("reuse containers = $reuse")
        TestcontainersConfiguration.getInstance().updateUserConfig("testcontainers.reuse.enable", reuse.toString())
        return PostgreSQLContainer<Nothing>(
            //DockerImageName.parse("postgres").withTag(PostgreSQLContainer.DEFAULT_TAG)
            DockerImageName.parse("postgres").withTag("15")
        ).apply {
            //withImagePullPolicy(PullPolicy.alwaysPull())
            withReuse(reuse)
            /*
            withUsername("user")
            withPassword("password")
            withDatabaseName("database")
            waitingFor(Wait.forLogMessage(".*databassystemet är redo att ta emot anslutningar.*", 2))
*/
            start()
        }
    }
}
