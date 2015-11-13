package tech.anima.tinytypes.examples;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.setup.Environment;
import io.dropwizard.testing.junit.DropwizardClientRule;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.internal.inject.ConfiguredValidator;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import tech.anima.tinytypes.Samples;
import tech.anima.tinytypes.jackson.TinyTypesModule;

@RunWith(Enclosed.class)
public class DropwizardJerseyClientTest {

    @RunWith(Theories.class)
    public static class JsonBodySerializationTest {

        private final Environment dwEnvironment = new Environment("env", new ObjectMapper().registerModule(new TinyTypesModule()),null, new MetricRegistry(), this.getClass().getClassLoader());
        @ClassRule
        public static final DropwizardClientRule deserializedEcho = new DropwizardClientRule(new DeserializedEchoResource());
        @DataPoints
        public static final Object[] tts = new Object[]{new Samples.Boolean(true), new Samples.Str("1"), new Samples.Byte((byte) 1), new Samples.Short((byte) 1), new Samples.Integer(1), new Samples.Long(1)};

        @Theory
        public void serializedAsStringificationOnRequestBody(Object tt) throws JsonProcessingException {
            final JerseyClientConfiguration config = new JerseyClientConfiguration();
            final Client client = new JerseyClientBuilder(dwEnvironment).using(config).using(dwEnvironment).build("client");
            final String expected = dwEnvironment.getObjectMapper().writeValueAsString(tt);
            final Response response = client.target(deserializedEcho.baseUri()).path("/").request().post(Entity.entity(tt, MediaType.APPLICATION_JSON));
            Assert.assertEquals(200, response.getStatus());
            final String got = response.readEntity(String.class);
            Assert.assertEquals(expected, got);
        }

        @Path(value = "/")
        public static final class DeserializedEchoResource {

            @POST
            public String something(String got) {
                return got;
            }
        }

    }

}
