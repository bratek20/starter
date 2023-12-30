package pl.bratek20.commons.script;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;

@Slf4j
public abstract class Script<TApi, TArgs> {
    protected abstract void addOptions(Options options);

    protected abstract TArgs createArgs(CommandLine cmd) throws CreateArgsException;

    protected abstract String run(TApi api, TArgs args);

    private final TApi api;

    protected Script(TApi api) {
        this.api = api;
    }

    public String run(String[] rawArgs) {
        Options options = new Options();
        addOptions(options);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, rawArgs);
            var args = createArgs(cmd);
            var result = run(api, args);
            log.info(result);
            return result;
        } catch (ParseException e) {
            log.error("Parsing arguments failed: " + e.getMessage());
            printHelp(options);
        } catch (CreateArgsException e) {
            log.error("Creating arguments failed: " + e.getMessage());
            printHelp(options);
        }
        return null;
    }

    private void printHelp(Options options) {
        new HelpFormatter().printHelp("Script", options);
    }
}
