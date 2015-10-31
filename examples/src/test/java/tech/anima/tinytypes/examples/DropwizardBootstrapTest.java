package tech.anima.tinytypes.examples;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.junit.Assert;
import org.junit.Test;
import tech.anima.tinytypes.Samples;
import tech.anima.tinytypes.jackson.TinyTypesModule;

public class DropwizardBootstrapTest {

    @Test
    public void canLoadYamlDWConfigFileWithTinyTypes() throws Exception {

        new TestApplication().run(new String[]{"server", ClassLoader.getSystemClassLoader().getResource("tts.yml").getPath().toString()});
    }
    

    public static class TestApplication extends Application<ConfigurationWithTinyTypes> {

        @Override
        public void initialize(Bootstrap<ConfigurationWithTinyTypes> bootstrap) {
            bootstrap.getObjectMapper().registerModule(new TinyTypesModule());
        }

        @Override
        public void run(ConfigurationWithTinyTypes configuration, Environment environment) throws Exception {
            Assert.assertEquals("1", configuration.stringValue.value);
            Assert.assertEquals(true, configuration.booleanValue.value);
            Assert.assertEquals(1, configuration.byteValue.value);
            Assert.assertEquals(1, configuration.shortValue.value);
            Assert.assertEquals(1, configuration.integerValue.value);
            Assert.assertEquals(1, configuration.longValue.value);
        }
    }

    public static class ConfigurationWithTinyTypes extends Configuration {

        private final Samples.Str stringValue;
        private final Samples.Boolean booleanValue;
        private final Samples.Byte byteValue;
        private final Samples.Short shortValue;
        private final Samples.Integer integerValue;
        private final Samples.Long longValue;

        @JsonCreator
        public ConfigurationWithTinyTypes(
                @JsonProperty("stringValue") Samples.Str stringValue,
                @JsonProperty("booleanValue") Samples.Boolean booleanValue,
                @JsonProperty("byteValue") Samples.Byte byteValue,
                @JsonProperty("shortValue") Samples.Short shortValue,
                @JsonProperty("integerValue") Samples.Integer integerValue,
                @JsonProperty("longValue") Samples.Long longValue) {
            this.stringValue = stringValue;
            this.booleanValue = booleanValue;
            this.byteValue = byteValue;
            this.shortValue = shortValue;
            this.integerValue = integerValue;
            this.longValue = longValue;
        }

        public Samples.Str getStringValue() {
            return stringValue;
        }

        public Samples.Boolean getBooleanValue() {
            return booleanValue;
        }

        public Samples.Byte getByteValue() {
            return byteValue;
        }

        public Samples.Short getShortValue() {
            return shortValue;
        }

        public Samples.Integer getIntegerValue() {
            return integerValue;
        }

        public Samples.Long getLongValue() {
            return longValue;
        }

    }
}
