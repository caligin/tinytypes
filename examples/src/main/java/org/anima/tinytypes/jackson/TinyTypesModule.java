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
        context.addKeySerializers(new TinyTypesKeySerializers());
        context.addSerializers(new TinyTypesSerializers());
        context.addDeserializers(new TinyTypesDeserializers());
        context.addKeyDeserializers(new StringTinyTypesKeyDeserializers());
        context.addKeyDeserializers(new IntTinyTypesKeyDeserializers());
        context.addKeyDeserializers(new ByteTinyTypesKeyDeserializers());
        context.addKeyDeserializers(new ShortTinyTypesKeyDeserializers());
        context.addKeyDeserializers(new LongTinyTypesKeyDeserializers());
        context.addKeyDeserializers(new BooleanTinyTypesKeyDeserializers());
    }

}
