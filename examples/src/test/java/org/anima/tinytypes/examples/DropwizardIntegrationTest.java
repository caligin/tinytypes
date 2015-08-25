package org.anima.tinytypes.examples;

import io.dropwizard.testing.junit.ResourceTestRule;
import java.net.URI;
import java.util.HashMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientProperties;
import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

public class DropwizardIntegrationTest {

    public static ResourceSpy spy = new ResourceSpy();

    @ClassRule
    public static final ResourceTestRule resource = ResourceTestRule.builder()
            .addResource(new TinyTypesResource(spy))
            .build();

    @After
    public void cleanUpSpy() {
        spy.clear();
    }

    public static class ResourceSpy extends HashMap<Class<?>, Object> {

    }

    @Path("/")
    public static class TinyTypesResource {

        private final ResourceSpy spy;

        public TinyTypesResource(ResourceSpy spy) {
            this.spy = spy;
        }

        @POST
        @Path("str/reqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void strReqBody(Samples.Str str) {
            spy.put(Samples.Str.class, str);
        }

        @GET
        @Path("str/queryparam")
        public void strQueryParam(@QueryParam("test") Samples.Str str) {
            spy.put(Samples.Str.class, str);
        }

        @GET
        @Path("str/pathparam/{test}")
        public void strPathParam(@PathParam("test") Samples.Str str) {
            spy.put(Samples.Str.class, str);
        }

        @POST
        @Path("str/formparam")
        public void strFormParam(@FormParam("test") Samples.Str str) {
            spy.put(Samples.Str.class, str);
        }

        @GET
        @Path("str/reqheader")
        public void strReqHeader(@HeaderParam("test") Samples.Str str) {
            spy.put(Samples.Str.class, str);
        }

        @GET
        @Path("str/respbody")
        @Produces(MediaType.APPLICATION_JSON)
        public Samples.Str strRespBody() {
            return new Samples.Str("asd`");
        }

        @GET
        @Path("str/respheader")
        public Response strRespHeader() {
            return Response.ok().header("test", new Samples.Str("asd")).build();
        }

        @GET
        @Path("str/uripath")
        public Response strLocationHeaderWithUriBuilder() {
            return Response.temporaryRedirect(UriBuilder.fromPath("/{str}").build(new Samples.Str("asd"))).build();
        }

        @POST
        @Path("int/reqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void intReqBody(Samples.Integer tt) {
            spy.put(Samples.Integer.class, tt);
        }

        @GET
        @Path("int/queryparam")
        public void intQueryParam(@QueryParam("test") Samples.Integer tt) {
            spy.put(Samples.Integer.class, tt);
        }

        @GET
        @Path("int/pathparam/{test}")
        public void intPathParam(@PathParam("test") Samples.Integer tt) {
            spy.put(Samples.Integer.class, tt);
        }

        @POST
        @Path("int/formparam")
        public void intFormParam(@FormParam("test") Samples.Integer tt) {
            spy.put(Samples.Integer.class, tt);
        }

        @GET
        @Path("int/reqheader")
        public void intReqHeader(@HeaderParam("test") Samples.Integer tt) {
            spy.put(Samples.Integer.class, tt);
        }

        @GET
        @Path("int/respbody")
        @Produces(MediaType.APPLICATION_JSON)
        public Samples.Integer intRespBody() {
            return new Samples.Integer(1);
        }

        @GET
        @Path("int/respheader")
        public Response intRespHeader() {
            return Response.ok().header("test", new Samples.Integer(1)).build();
        }

        @GET
        @Path("int/uripath")
        public Response intLocationHeaderWithUriBuilder() {
            return Response.temporaryRedirect(UriBuilder.fromPath("/{int}").build(new Samples.Integer(1))).build();
        }
    }

    @Test
    public void canDeserializeStringTTAsRequestBody() {
        final String value = "\"asd\"";
        final Samples.Str expected = new Samples.Str("asd");
        resource
                .client()
                .target("/str/reqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Str.class));
    }

    @Test
    public void canDeserializeStringTTAsQueryParam() {
        final Samples.Str expected = new Samples.Str("asd");
        resource
                .client()
                .target("/str/queryparam")
                .queryParam("test", "asd")
                .request()
                .get();
        Assert.assertEquals(expected, spy.get(Samples.Str.class));
    }

    @Test
    public void canDeserializeStringTTAsPathParam() {
        final Samples.Str expected = new Samples.Str("asd");
        resource
                .client()
                .target("/str/pathparam/asd")
                .request()
                .get();
        Assert.assertEquals(expected, spy.get(Samples.Str.class));
    }

    @Test
    public void canDeserializeStringTTAsFormParam() {
        final Samples.Str expected = new Samples.Str("asd");
        resource
                .client()
                .target("/str/formparam")
                .request()
                .post(Entity.form(new Form("test", "asd")));
        Assert.assertEquals(expected, spy.get(Samples.Str.class));
    }

    @Test
    public void canDeserializeStringTTAsReqHeader() {
        final Samples.Str expected = new Samples.Str("asd");
        resource
                .client()
                .target("/str/reqheader")
                .request()
                .header("test", "asd")
                .get();
        Assert.assertEquals(expected, spy.get(Samples.Str.class));
    }

    @Test
    public void canSerializeStringTTAsRespBody() {
        final String expected = "\"asd\"";
        final Response req = resource
                .client()
                .target("/str/respbody")
                .request()
                .get();
        final String rawBody = req.readEntity(String.class);
        Assert.assertEquals(expected, rawBody);
    }

    @Test
    public void canSerializeStringTTAsRespHeader() {
        final String expected = "asd";
        final Response req = resource
                .client()
                .target("/str/respheader")
                .request()
                .get();
        final String header = req.getHeaderString("test");
        Assert.assertEquals(expected, header);
    }

    @Test
    public void canSerializeStringTTAsPartOfUri() {
        final String expected = "/asd";
        final Response req = resource
                .client()
                .property(ClientProperties.FOLLOW_REDIRECTS, false)
                .target("/str/uripath")
                .request()
                .get();
        final String path = req.getLocation().getPath();
        Assert.assertEquals(expected, path);
    }

    @Test
    public void canDeserializeIntegerTTAsRequestBody() {
        final String value = "1";
        final Samples.Integer expected = new Samples.Integer(1);
        resource
                .client()
                .target("/str/reqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Integer.class));
    }

    @Test
    public void canDeserializeIntegerTTAsQueryParam() {
        final Samples.Integer expected = new Samples.Integer(1);
        resource
                .client()
                .target("/str/queryparam")
                .queryParam("test", 1)
                .request()
                .get();
        Assert.assertEquals(expected, spy.get(Samples.Integer.class));
    }

    @Test
    public void canDeserializeIntegerTTAsPathParam() {
        final Samples.Integer expected = new Samples.Integer(1);
        resource
                .client()
                .target("/str/pathparam/1")
                .request()
                .get();
        Assert.assertEquals(expected, spy.get(Samples.Integer.class));
    }

    @Test
    public void canDeserializeIntegerTTAsFormParam() {
        final Samples.Integer expected = new Samples.Integer(1);
        resource
                .client()
                .target("/str/formparam")
                .request()
                .post(Entity.form(new Form("test", "1")));
        Assert.assertEquals(expected, spy.get(Samples.Integer.class));
    }

    @Test
    public void canDeserializeIntegerTTAsReqHeader() {
        final Samples.Integer expected = new Samples.Integer(1);
        resource
                .client()
                .target("/str/reqheader")
                .request()
                .header("test", 1)
                .get();
        Assert.assertEquals(expected, spy.get(Samples.Integer.class));
    }

    @Test
    public void canSerializeIntegerTTAsRespBody() {
        final String expected = "1";
        final Response req = resource
                .client()
                .target("/str/respbody")
                .request()
                .get();
        final String rawBody = req.readEntity(String.class);
        Assert.assertEquals(expected, rawBody);
    }

    @Test
    public void canSerializeIntegerTTAsRespHeader() {
        final String expected = "1";
        final Response req = resource
                .client()
                .target("/str/respheader")
                .request()
                .get();
        final String header = req.getHeaderString("test");
        Assert.assertEquals(expected, header);
    }

    @Test
    public void canSerializeIntegerTTAsPartOfUri() {
        final String expected = "/1";
        final Response req = resource
                .client()
                .property(ClientProperties.FOLLOW_REDIRECTS, false)
                .target("/str/uripath")
                .request()
                .get();
        final String path = req.getLocation().getPath();
        Assert.assertEquals(expected, path);
    }

}
