package co.pvphub.velocity.example;

import co.pvphub.velocity.plugin.VelocityPlugin;
import co.pvphub.velocity.scheduling.AsyncTask;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;

import java.net.ProxySelector;
import java.nio.file.Path;
import java.time.Duration;
import java.util.logging.Logger;

@Plugin(
        id = "velocityutil",
        name = "VelocityUtil",
        version = "1.0",
        description = "Development utilities for Velocity!",
        authors = { "MattMX" }
)
public class JavaExamplePlugin extends VelocityPlugin {

    public JavaExamplePlugin(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        super(server, logger, dataDirectory);

        new AsyncTask(this, (asyncTask -> {
            logger.info("This message is asynchronous thanks to VelocityUtils!");
            return null;
        })).schedule();

        new AsyncTask(this, (asyncTask -> {
            logger.info("Repeated ${it.iteration} times with 1s delay!");
            if (asyncTask.getIteration() >= 5) asyncTask.cancel();
            return null;
        })).repeat(Duration.ofSeconds(1)).schedule();
    }

}
