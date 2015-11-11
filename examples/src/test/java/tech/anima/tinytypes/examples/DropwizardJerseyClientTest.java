package tech.anima.tinytypes.examples;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.setup.Environment;
import io.dropwizard.testing.junit.DropwizardAppRule;
import io.dropwizard.testing.junit.DropwizardClientRule;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

        @ClassRule
        public static final DropwizardAppRule<Configuration> applicationWithClient = new DropwizardAppRule<>(AppWithClient.class, null);
        @ClassRule
        public static final DropwizardClientRule deserializedEcho = new DropwizardClientRule(new DeserializedEchoResource());
        @DataPoints
        public static final Object[] tts = new Object[]{new Samples.Boolean(true), new Samples.Str("1"), new Samples.Byte((byte) 1), new Samples.Short((byte) 1), new Samples.Integer(1), new Samples.Long(1)};

        @Theory
        public void serializedAsStringificationOnRequestBody(Object tt) throws JsonProcessingException {
            final String expected = applicationWithClient.getObjectMapper().writeValueAsString(tt);
            final Client client = ((AppWithClient) applicationWithClient.getApplication()).client;
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

        public static class AppWithClient extends Application<Configuration> {

            public Client client;

            @Override
            public void run(Configuration configuration, Environment environment) throws Exception {
                environment.getObjectMapper().registerModule(new TinyTypesModule());
                JerseyClientConfiguration config = new JerseyClientConfiguration();
                client = new JerseyClientBuilder(environment).using(config).using(environment).build("client");
            }
        }
    }

}
