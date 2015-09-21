package org.anima.tinytypes.examples;

import org.anima.tinytypes.jackson.TinyTypesModule;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.jackson.Jackson;
import org.anima.tinytypes.jersey.StringTTJsonBodyWriter;
import org.anima.tinytypes.jersey.IntTTJsonBodyWriter;
import org.anima.tinytypes.jersey.JerseyResponseSupport;
import org.anima.tinytypes.jersey.StringTTParamProvider;
import org.anima.tinytypes.jersey.IntTTParamProvider;
import io.dropwizard.testing.junit.ResourceTestRule;
import java.util.HashMap;
import java.util.Map;
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
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

public class DropwizardIntegrationTest {

    public static ResourceSpy spy = new ResourceSpy();

    @ClassRule
    public static final ResourceTestRule resource = ResourceTestRule.builder()
            .addResource(new TinyTypesResource(spy))
            .addProvider(StringTTParamProvider.class)
            .addProvider(StringTTJsonBodyWriter.class)
            .addProvider(IntTTParamProvider.class)
            .addProvider(IntTTJsonBodyWriter.class)
            .setMapper(Jackson.newObjectMapper().registerModule(new TinyTypesModule(
                                    Samples.Str.class,
                                    Samples.Integer.class
                            )))
            .build();

    @Before
    public void setupDelegates() throws Exception {
        JerseyResponseSupport.registerTinyTypes(
                Samples.Str.class,
                Samples.Integer.class
        );
    }

    @After
    public void cleanUpSpy() {
        spy.clear();
    }

    public static class ResourceSpy extends HashMap<Class<?>, Object> {

    }

    public static class DtoWithStringTT {

        private final Samples.Str id;
        private final String somedata;

        @JsonCreator
        public DtoWithStringTT(@JsonProperty("id") Samples.Str id, @JsonProperty("somedata") String somedata) {
            this.id = id;
            this.somedata = somedata;
        }

        public Samples.Str getId() {
            return id;
        }

        public String getSomedata() {
            return somedata;
        }

    }

    public static class DtoWithIntTT {

        private final Samples.Integer id;
        private final String somedata;

        @JsonCreator
        public DtoWithIntTT(@JsonProperty("id") Samples.Integer id, @JsonProperty("somedata") String somedata) {
            this.id = id;
            this.somedata = somedata;
        }

        public Samples.Integer getId() {
            return id;
        }

        public String getSomedata() {
            return somedata;
        }

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

        @POST
        @Path("str/nestedreqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void strNestedReqBody(DtoWithStringTT str) {
            spy.put(Samples.Str.class, str.id);
        }

        @POST
        @Path("str/mapreqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void strMapReqBody(Map<String, Samples.Str> map) {
            spy.put(Samples.Str.class, map.get("id"));
        }

        @POST
        @Path("str/keyinmapreqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void strKeyInMapReqBody(Map<Samples.Str, Object> map) {
            spy.put(Samples.Str.class, map.keySet().iterator().next());
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
            return new Samples.Str("asd");
        }

        @GET
        @Path("str/nestedrespbody")
        @Produces(MediaType.APPLICATION_JSON)
        public DtoWithStringTT strNestedRespBody() {
            return new DtoWithStringTT(new Samples.Str("asd"), "qwe");
        }

        @GET
        @Path("str/maprespbody")
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> strMapRespBody() {
            final Map<String, Object> map = new HashMap<>();
            map.put("id", new Samples.Str("asd"));
            map.put("somedata", "qwe");
            return map;
        }

        @GET
        @Path("str/keyinmaprespbody")
        @Produces(MediaType.APPLICATION_JSON)
        public Map<Samples.Str, Object> strKeyInMapRespBody() {
            final Map<Samples.Str, Object> map = new HashMap<>();
            map.put(new Samples.Str("asd"), "qwe");
            return map;
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

        @POST
        @Path("int/nestedreqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void intNestedReqBody(DtoWithIntTT dto) {
            spy.put(Samples.Integer.class, dto.id);
        }

        @POST
        @Path("int/mapreqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void intMapReqBody(Map<String, Samples.Integer> map) {
            spy.put(Samples.Integer.class, map.get("id"));
        }

        @POST
        @Path("int/keyinmapreqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void intKeyInMapReqBody(Map<Samples.Integer, Object> map) {
            spy.put(Samples.Integer.class, map.keySet().iterator().next());
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
        @Path("int/nestedrespbody")
        @Produces(MediaType.APPLICATION_JSON)
        public DtoWithIntTT intNestedRespBody() {
            return new DtoWithIntTT(new Samples.Integer(1), "qwe");
        }

        @GET
        @Path("int/maprespbody")
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> intMapRespBody() {
            final Map<String, Object> map = new HashMap<>();
            map.put("id", new Samples.Integer(1));
            map.put("somedata", "qwe");
            return map;
        }

        @GET
        @Path("int/keyinmaprespbody")
        @Produces(MediaType.APPLICATION_JSON)
        public Map<Samples.Integer, Object> intKeyInMapRespBody() {
            final Map<Samples.Integer, Object> map = new HashMap<>();
            map.put(new Samples.Integer(1), "qwe");
            return map;
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
    public void canDeserializeStringTTWhenNestedInRequestBody() {
        final String value = "{\"id\":\"asd\",\"somedata\":\"blah\"}";
        final Samples.Str expected = new Samples.Str("asd");
        resource
                .client()
                .target("/str/nestedreqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Str.class));
    }

    @Test
    public void canDeserializeStringTTWhenValueInMapRequestBody() {
        final String value = "{\"id\":\"asd\",\"somedata\":\"blah\"}";
        final Samples.Str expected = new Samples.Str("asd");
        resource
                .client()
                .target("/str/mapreqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Str.class));
    }

    @Test
    public void canDeserializeStringTTWhenKeyOfMapRequestBody() {
        final String value = "{\"asd\":\"blah\"}";
        final Samples.Str expected = new Samples.Str("asd");
        resource
                .client()
                .target("/str/keyinmapreqbody")
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
    public void canSerializeStringTTWhenNestedInRespBody() {
        final String expected = "{\"id\":\"asd\",\"somedata\":\"qwe\"}";
        final Response req = resource
                .client()
                .target("/str/nestedrespbody")
                .request()
                .get();
        final String rawBody = req.readEntity(String.class);
        Assert.assertEquals(expected, rawBody);
    }

    @Test
    public void canSerializeStringTTWhenValueInMapRespBody() {
        final String expected = "{\"somedata\":\"qwe\",\"id\":\"asd\"}";
        final Response req = resource
                .client()
                .target("/str/maprespbody")
                .request()
                .get();
        final String rawBody = req.readEntity(String.class);
        Assert.assertEquals(expected, rawBody);
    }

    @Test
    public void canSerializeStringTTWhenKeyOfMapRespBody() {
        final String expected = "{\"asd\":\"qwe\"}";
        final Response req = resource
                .client()
                .target("/str/keyinmaprespbody")
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
    @Ignore(value = "no way to provide UriBuilder with a transformation for the value, it just calls toString.")
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
                .target("/int/reqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Integer.class));
    }

    @Test
    public void canDeserializeIntTTWhenNestedInRequestBody() {
        final String value = "{\"id\":1,\"somedata\":\"blah\"}";
        final Samples.Integer expected = new Samples.Integer(1);
        resource
                .client()
                .target("/int/nestedreqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Integer.class));
    }

    @Test
    public void canDeserializeIntTTWhenValueInMapRequestBody() {
        final String value = "{\"id\":1,\"somedata\":2}";
        final Samples.Integer expected = new Samples.Integer(1);
        resource
                .client()
                .target("/int/mapreqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Integer.class));
    }

    @Test
    public void canDeserializeIntTTWhenKeyOfMapRequestBody() {
        final String value = "{\"1\":\"blah\"}";
        final Samples.Integer expected = new Samples.Integer(1);
        resource
                .client()
                .target("/int/keyinmapreqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Integer.class));
    }

    @Test
    public void canDeserializeIntegerTTAsQueryParam() {
        final Samples.Integer expected = new Samples.Integer(1);
        resource
                .client()
                .target("/int/queryparam")
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
                .target("/int/pathparam/1")
                .request()
                .get();
        Assert.assertEquals(expected, spy.get(Samples.Integer.class));
    }

    @Test
    public void canDeserializeIntegerTTAsFormParam() {
        final Samples.Integer expected = new Samples.Integer(1);
        resource
                .client()
                .target("/int/formparam")
                .request()
                .post(Entity.form(new Form("test", "1")));
        Assert.assertEquals(expected, spy.get(Samples.Integer.class));
    }

    @Test
    public void canDeserializeIntegerTTAsReqHeader() {
        final Samples.Integer expected = new Samples.Integer(1);
        resource
                .client()
                .target("/int/reqheader")
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
                .target("/int/respbody")
                .request()
                .get();
        final String rawBody = req.readEntity(String.class);
        Assert.assertEquals(expected, rawBody);
    }

    @Test
    public void canSerializeIntTTWhenNestedInRespBody() {
        final String expected = "{\"id\":1,\"somedata\":\"qwe\"}";
        final Response req = resource
                .client()
                .target("/int/nestedrespbody")
                .request()
                .get();
        final String rawBody = req.readEntity(String.class);
        Assert.assertEquals(expected, rawBody);
    }

    @Test
    public void canSerializeIntTTWhenValueInMapRespBody() {
        final String expected = "{\"somedata\":\"qwe\",\"id\":1}";
        final Response req = resource
                .client()
                .target("/int/maprespbody")
                .request()
                .get();
        final String rawBody = req.readEntity(String.class);
        Assert.assertEquals(expected, rawBody);
    }

    @Test
    public void canSerializeIntTTWhenKeyOfMapRespBody() {
        final String expected = "{\"1\":\"qwe\"}";
        final Response req = resource
                .client()
                .target("/int/keyinmaprespbody")
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
                .target("/int/respheader")
                .request()
                .get();
        final String header = req.getHeaderString("test");
        Assert.assertEquals(expected, header);
    }

    @Test
    @Ignore(value = "no way to provide UriBuilder with a transformation for the value, it just calls toString.")
    public void canSerializeIntegerTTAsPartOfUri() {
        final String expected = "/1";
        final Response req = resource
                .client()
                .property(ClientProperties.FOLLOW_REDIRECTS, false)
                .target("/int/uripath")
                .request()
                .get();
        final String path = req.getLocation().getPath();
        Assert.assertEquals(expected, path);
    }
}
