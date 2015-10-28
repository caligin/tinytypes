package tech.anima.tinytypes.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;

/**
 * Jackson module for TinyTypes support. This will provide serialization of
 * TinyTypes as the wrapped value and deserialization of the native values as
 * TinyTypes.
 */
public final class TinyTypesModule extends Module {

    @Override
    public String getModuleName() {
        return "TinyTypes";
    }

    @Override
    public Version version() {
        return new Version(1, 1, 0, "SNAPSHOT", "tech.anima", "jackson-datatype-tinytypes");
    }

    @Override
    public void setupModule(SetupContext context) {
        context.addKeySerializers(new TinyTypesKeySerializers());
        context.addSerializers(new TinyTypesSerializers());
        context.addDeserializers(new TinyTypesDeserializers());
        context.addKeyDeserializers(new TinyTypesKeyDeserializers());
    }

}
