package org.anima.tinytypes.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;

public final class TinyTypesModule extends Module {

    public TinyTypesModule(Class<?>... ttToRegister) {
    }

    @Override
    public String getModuleName() {
        return "TinyTypes";
    }

    @Override
    public Version version() {
        return new Version(1, 0, 0, "SNAPSHOT", "org.anima", "tinytypes-jackson");
    }

    @Override
    public void setupModule(SetupContext context) {
        context.addSerializers(new StringTinyTypesSerializers());
        context.addKeySerializers(new StringTinyTypesKeySerializers());
        context.addKeyDeserializers(new StringTinyTypesKeyDeserializers());
        context.addSerializers(new IntTinyTypesSerializers());
        context.addKeySerializers(new IntTinyTypesKeySerializers());
        context.addKeyDeserializers(new IntTinyTypesKeyDeserializers());
    }

}
