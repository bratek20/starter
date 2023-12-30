package pl.bratek20.commons.script;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

class TestScript extends Script<TestScript.TestApi, TestScript.Args> {
    static class TestApi { }
    record Args() {}

    @Override
    protected void addOptions(Options options) {

    }

    @Override
    protected Args createArgs(CommandLine cmd) throws CreateArgsException {
        return null;
    }

    @Override
    protected String run(TestApi testApi, Args args) {
        return "Hello World!";
    }

    public TestScript(TestApi api) {
        super(api);
    }

    public static void main(String[] args) {
        new TestScript(new TestApi()).run(args);
    }
}