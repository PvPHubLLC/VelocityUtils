package co.pvphub.velocity.example

import co.pvphub.velocity.dsl.colored
import co.pvphub.velocity.plugin.VelocityPlugin
import co.pvphub.velocity.scheduling.async
import co.pvphub.velocity.scheduling.asyncRepeat
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import java.nio.file.Path
import java.util.logging.Logger
import javax.inject.Inject

@Plugin(
    id = "velocityutil",
    name = "VelocityUtil",
    version = "1.0",
    description = "Development utilities for Velocity!",
    authors = ["MattMX"]
)
class ExamplePlugin @Inject constructor(
    server: ProxyServer,
    logger: Logger,
    @DataDirectory
    dataDirectory: Path
) : VelocityPlugin(server, logger, dataDirectory) {

    init {
        async(this) {
            logger.info("This message is asynchronous thanks to VelocityUtils!")
        }
        asyncRepeat(this, 1000) {
            logger.info("Repeated ${it.iteration} times with 1s delay!")
            if (it.iteration >= 5) it.cancel()
        }
    }

}