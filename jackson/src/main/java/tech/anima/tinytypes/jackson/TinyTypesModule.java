package tech.anima.tinytypes.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;

public final class TinyTypesModule extends Module {

    @Override
    public String getModuleName() {
        return "TinyTypes";
    }

    @Override
    public Version version() {
        return new Version(1, 0, 0, "SNAPSHOT", "tech.anima", "tinytypes-jackson");
    }

    @Override
    public void setupModule(SetupContext context) {
        context.addKeySerializers(new TinyTypesKeySerializers());
        context.addSerializers(new TinyTypesSerializers());
        context.addDeserializers(new TinyTypesDeserializers());
        context.addKeyDeserializers(new TinyTypesKeyDeserializers());
    }

}
