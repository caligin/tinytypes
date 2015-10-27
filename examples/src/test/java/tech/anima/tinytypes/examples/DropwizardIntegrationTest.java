package tech.anima.tinytypes.examples;

import tech.anima.tinytypes.Samples;
import tech.anima.tinytypes.jackson.TinyTypesModule;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.jackson.Jackson;
import tech.anima.tinytypes.jersey.JerseyResponseSupport;
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
import tech.anima.tinytypes.jersey.TinyTypesParamProvider;
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
            .addProvider(TinyTypesParamProvider.class)
            .setMapper(Jackson.newObjectMapper().registerModule(new TinyTypesModule()))
            .build();

    @Before
    public void setupDelegates() throws Exception {
        JerseyResponseSupport.registerTinyTypes(
                Samples.Str.class,
                Samples.Integer.class,
                Samples.Byte.class,
                Samples.Short.class,
                Samples.Long.class,
                Samples.Boolean.class
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

    public static class DtoWithByteTT {

        private final Samples.Byte id;
        private final String somedata;

        @JsonCreator
        public DtoWithByteTT(@JsonProperty("id") Samples.Byte id, @JsonProperty("somedata") String somedata) {
            this.id = id;
            this.somedata = somedata;
        }

        public Samples.Byte getId() {
            return id;
        }

        public String getSomedata() {
            return somedata;
        }

    }

    public static class DtoWithShortTT {

        private final Samples.Short id;
        private final String somedata;

        @JsonCreator
        public DtoWithShortTT(@JsonProperty("id") Samples.Short id, @JsonProperty("somedata") String somedata) {
            this.id = id;
            this.somedata = somedata;
        }

        public Samples.Short getId() {
            return id;
        }

        public String getSomedata() {
            return somedata;
        }

    }

    public static class DtoWithLongTT {

        private final Samples.Long id;
        private final String somedata;

        @JsonCreator
        public DtoWithLongTT(@JsonProperty("id") Samples.Long id, @JsonProperty("somedata") String somedata) {
            this.id = id;
            this.somedata = somedata;
        }

        public Samples.Long getId() {
            return id;
        }

        public String getSomedata() {
            return somedata;
        }

    }

    public static class DtoWithBooleanTT {

        private final Samples.Boolean id;
        private final String somedata;

        @JsonCreator
        public DtoWithBooleanTT(@JsonProperty("id") Samples.Boolean id, @JsonProperty("somedata") String somedata) {
            this.id = id;
            this.somedata = somedata;
        }

        public Samples.Boolean getId() {
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

        @POST
        @Path("byte/reqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void byteReqBody(Samples.Byte tt) {
            spy.put(Samples.Byte.class, tt);
        }

        @POST
        @Path("byte/nestedreqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void byteNestedReqBody(DtoWithByteTT dto) {
            spy.put(Samples.Byte.class, dto.id);
        }

        @POST
        @Path("byte/mapreqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void byteMapReqBody(Map<String, Samples.Byte> map) {
            spy.put(Samples.Byte.class, map.get("id"));
        }

        @POST
        @Path("byte/keyinmapreqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void byteKeyInMapReqBody(Map<Samples.Byte, Object> map) {
            spy.put(Samples.Byte.class, map.keySet().iterator().next());
        }

        @GET
        @Path("byte/queryparam")
        public void byteQueryParam(@QueryParam("test") Samples.Byte tt) {
            spy.put(Samples.Byte.class, tt);
        }

        @GET
        @Path("byte/pathparam/{test}")
        public void bytePathParam(@PathParam("test") Samples.Byte tt) {
            spy.put(Samples.Byte.class, tt);
        }

        @POST
        @Path("byte/formparam")
        public void byteFormParam(@FormParam("test") Samples.Byte tt) {
            spy.put(Samples.Byte.class, tt);
        }

        @GET
        @Path("byte/reqheader")
        public void byteReqHeader(@HeaderParam("test") Samples.Byte tt) {
            spy.put(Samples.Byte.class, tt);
        }

        @GET
        @Path("byte/respbody")
        @Produces(MediaType.APPLICATION_JSON)
        public Samples.Byte byteRespBody() {
            return new Samples.Byte((byte) 1);
        }

        @GET
        @Path("byte/nestedrespbody")
        @Produces(MediaType.APPLICATION_JSON)
        public DtoWithByteTT byteNestedRespBody() {
            return new DtoWithByteTT(new Samples.Byte((byte) 1), "qwe");
        }

        @GET
        @Path("byte/maprespbody")
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> byteMapRespBody() {
            final Map<String, Object> map = new HashMap<>();
            map.put("id", new Samples.Byte((byte) 1));
            return map;
        }

        @GET
        @Path("byte/keyinmaprespbody")
        @Produces(MediaType.APPLICATION_JSON)
        public Map<Samples.Byte, Object> byteKeyInMapRespBody() {
            final Map<Samples.Byte, Object> map = new HashMap<>();
            map.put(new Samples.Byte((byte) 1), "qwe");
            return map;
        }

        @GET
        @Path("byte/respheader")
        public Response byteRespHeader() {
            return Response.ok().header("test", new Samples.Byte((byte) 1)).build();
        }

        @GET
        @Path("byte/uripath")
        public Response byteLocationHeaderWithUriBuilder() {
            return Response.temporaryRedirect(UriBuilder.fromPath("/{byte}").build(new Samples.Byte((byte) 1))).build();
        }

        @POST
        @Path("short/reqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void shortReqBody(Samples.Short tt) {
            spy.put(Samples.Short.class, tt);
        }

        @POST
        @Path("short/nestedreqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void shortNestedReqBody(DtoWithShortTT dto) {
            spy.put(Samples.Short.class, dto.id);
        }

        @POST
        @Path("short/mapreqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void shortMapReqBody(Map<String, Samples.Short> map) {
            spy.put(Samples.Short.class, map.get("id"));
        }

        @POST
        @Path("short/keyinmapreqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void shortKeyInMapReqBody(Map<Samples.Short, Object> map) {
            spy.put(Samples.Short.class, map.keySet().iterator().next());
        }

        @GET
        @Path("short/queryparam")
        public void shortQueryParam(@QueryParam("test") Samples.Short tt) {
            spy.put(Samples.Short.class, tt);
        }

        @GET
        @Path("short/pathparam/{test}")
        public void shortPathParam(@PathParam("test") Samples.Short tt) {
            spy.put(Samples.Short.class, tt);
        }

        @POST
        @Path("short/formparam")
        public void shortFormParam(@FormParam("test") Samples.Short tt) {
            spy.put(Samples.Short.class, tt);
        }

        @GET
        @Path("short/reqheader")
        public void shortReqHeader(@HeaderParam("test") Samples.Short tt) {
            spy.put(Samples.Short.class, tt);
        }

        @GET
        @Path("short/respbody")
        @Produces(MediaType.APPLICATION_JSON)
        public Samples.Short shortRespBody() {
            return new Samples.Short((short) 1);
        }

        @GET
        @Path("short/nestedrespbody")
        @Produces(MediaType.APPLICATION_JSON)
        public DtoWithShortTT shortNestedRespBody() {
            return new DtoWithShortTT(new Samples.Short((short) 1), "qwe");
        }

        @GET
        @Path("short/maprespbody")
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> shortMapRespBody() {
            final Map<String, Object> map = new HashMap<>();
            map.put("id", new Samples.Short((short) 1));
            return map;
        }

        @GET
        @Path("short/keyinmaprespbody")
        @Produces(MediaType.APPLICATION_JSON)
        public Map<Samples.Short, Object> shortKeyInMapRespBody() {
            final Map<Samples.Short, Object> map = new HashMap<>();
            map.put(new Samples.Short((short) 1), "qwe");
            return map;
        }

        @GET
        @Path("short/respheader")
        public Response shortRespHeader() {
            return Response.ok().header("test", new Samples.Short((short) 1)).build();
        }

        @GET
        @Path("short/uripath")
        public Response shortLocationHeaderWithUriBuilder() {
            return Response.temporaryRedirect(UriBuilder.fromPath("/{short}").build(new Samples.Short((short) 1))).build();
        }

        @POST
        @Path("long/reqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void longReqBody(Samples.Long tt) {
            spy.put(Samples.Long.class, tt);
        }

        @POST
        @Path("long/nestedreqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void longNestedReqBody(DtoWithLongTT dto) {
            spy.put(Samples.Long.class, dto.id);
        }

        @POST
        @Path("long/mapreqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void longMapReqBody(Map<String, Samples.Long> map) {
            spy.put(Samples.Long.class, map.get("id"));
        }

        @POST
        @Path("long/keyinmapreqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void longKeyInMapReqBody(Map<Samples.Long, Object> map) {
            spy.put(Samples.Long.class, map.keySet().iterator().next());
        }

        @GET
        @Path("long/queryparam")
        public void longQueryParam(@QueryParam("test") Samples.Long tt) {
            spy.put(Samples.Long.class, tt);
        }

        @GET
        @Path("long/pathparam/{test}")
        public void longPathParam(@PathParam("test") Samples.Long tt) {
            spy.put(Samples.Long.class, tt);
        }

        @POST
        @Path("long/formparam")
        public void longFormParam(@FormParam("test") Samples.Long tt) {
            spy.put(Samples.Long.class, tt);
        }

        @GET
        @Path("long/reqheader")
        public void longReqHeader(@HeaderParam("test") Samples.Long tt) {
            spy.put(Samples.Long.class, tt);
        }

        @GET
        @Path("long/respbody")
        @Produces(MediaType.APPLICATION_JSON)
        public Samples.Long longRespBody() {
            return new Samples.Long((long) 1);
        }

        @GET
        @Path("long/nestedrespbody")
        @Produces(MediaType.APPLICATION_JSON)
        public DtoWithLongTT longNestedRespBody() {
            return new DtoWithLongTT(new Samples.Long((long) 1), "qwe");
        }

        @GET
        @Path("long/maprespbody")
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> longMapRespBody() {
            final Map<String, Object> map = new HashMap<>();
            map.put("id", new Samples.Long((long) 1));
            return map;
        }

        @GET
        @Path("long/keyinmaprespbody")
        @Produces(MediaType.APPLICATION_JSON)
        public Map<Samples.Long, Object> longKeyInMapRespBody() {
            final Map<Samples.Long, Object> map = new HashMap<>();
            map.put(new Samples.Long((long) 1), "qwe");
            return map;
        }

        @GET
        @Path("long/respheader")
        public Response longRespHeader() {
            return Response.ok().header("test", new Samples.Long((long) 1)).build();
        }

        @GET
        @Path("long/uripath")
        public Response longLocationHeaderWithUriBuilder() {
            return Response.temporaryRedirect(UriBuilder.fromPath("/{long}").build(new Samples.Long((long) 1))).build();
        }

        @POST
        @Path("boolean/reqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void booleanReqBody(Samples.Boolean tt) {
            spy.put(Samples.Boolean.class, tt);
        }

        @POST
        @Path("boolean/nestedreqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void booleanNestedReqBody(DtoWithBooleanTT dto) {
            spy.put(Samples.Boolean.class, dto.id);
        }

        @POST
        @Path("boolean/mapreqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void booleanMapReqBody(Map<String, Samples.Boolean> map) {
            spy.put(Samples.Boolean.class, map.get("id"));
        }

        @POST
        @Path("boolean/keyinmapreqbody")
        @Consumes(MediaType.APPLICATION_JSON)
        public void booleanKeyInMapReqBody(Map<Samples.Boolean, Object> map) {
            spy.put(Samples.Boolean.class, map.keySet().iterator().next());
        }

        @GET
        @Path("boolean/queryparam")
        public void booleanQueryParam(@QueryParam("test") Samples.Boolean tt) {
            spy.put(Samples.Boolean.class, tt);
        }

        @GET
        @Path("boolean/pathparam/{test}")
        public void booleanPathParam(@PathParam("test") Samples.Boolean tt) {
            spy.put(Samples.Boolean.class, tt);
        }

        @POST
        @Path("boolean/formparam")
        public void booleanFormParam(@FormParam("test") Samples.Boolean tt) {
            spy.put(Samples.Boolean.class, tt);
        }

        @GET
        @Path("boolean/reqheader")
        public void booleanReqHeader(@HeaderParam("test") Samples.Boolean tt) {
            spy.put(Samples.Boolean.class, tt);
        }

        @GET
        @Path("boolean/respbody")
        @Produces(MediaType.APPLICATION_JSON)
        public Samples.Boolean booleanRespBody() {
            return new Samples.Boolean(true);
        }

        @GET
        @Path("boolean/nestedrespbody")
        @Produces(MediaType.APPLICATION_JSON)
        public DtoWithBooleanTT booleanNestedRespBody() {
            return new DtoWithBooleanTT(new Samples.Boolean(true), "qwe");
        }

        @GET
        @Path("boolean/maprespbody")
        @Produces(MediaType.APPLICATION_JSON)
        public Map<String, Object> booleanMapRespBody() {
            final Map<String, Object> map = new HashMap<>();
            map.put("id", new Samples.Boolean(true));
            return map;
        }

        @GET
        @Path("boolean/keyinmaprespbody")
        @Produces(MediaType.APPLICATION_JSON)
        public Map<Samples.Boolean, Object> booleanKeyInMapRespBody() {
            final Map<Samples.Boolean, Object> map = new HashMap<>();
            map.put(new Samples.Boolean(true), "qwe");
            return map;
        }

        @GET
        @Path("boolean/respheader")
        public Response booleanRespHeader() {
            return Response.ok().header("test", new Samples.Boolean(true)).build();
        }

        @GET
        @Path("boolean/uripath")
        public Response booleanLocationHeaderWithUriBuilder() {
            return Response.temporaryRedirect(UriBuilder.fromPath("/{boolean}").build(new Samples.Boolean(true))).build();
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
        final String expected = "{\"id\":\"asd\"}";
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
        final String expected = "{\"id\":1}";
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

    @Test
    public void canDeserializeByteTTAsRequestBody() {
        final String value = "1";
        final Samples.Byte expected = new Samples.Byte((byte) 1);
        resource
                .client()
                .target("/byte/reqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Byte.class));
    }

    @Test
    public void canDeserializeByteTTWhenNestedInRequestBody() {
        final String value = "{\"id\":1,\"somedata\":\"blah\"}";
        final Samples.Byte expected = new Samples.Byte((byte) 1);
        resource
                .client()
                .target("/byte/nestedreqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Byte.class));
    }

    @Test
    public void canDeserializeByteTTWhenValueInMapRequestBody() {
        final String value = "{\"id\":1,\"somedata\":2}";
        final Samples.Byte expected = new Samples.Byte((byte) 1);
        resource
                .client()
                .target("/byte/mapreqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Byte.class));
    }

    @Test
    public void canDeserializeByteTTWhenKeyOfMapRequestBody() {
        final String value = "{\"1\":\"blah\"}";
        final Samples.Byte expected = new Samples.Byte((byte) 1);
        resource
                .client()
                .target("/byte/keyinmapreqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Byte.class));
    }

    @Test
    public void canDeserializeByteTTAsQueryParam() {
        final Samples.Byte expected = new Samples.Byte((byte) 1);
        resource
                .client()
                .target("/byte/queryparam")
                .queryParam("test", 1)
                .request()
                .get();
        Assert.assertEquals(expected, spy.get(Samples.Byte.class));
    }

    @Test
    public void canDeserializeByteTTAsPathParam() {
        final Samples.Byte expected = new Samples.Byte((byte) 1);
        resource
                .client()
                .target("/byte/pathparam/1")
                .request()
                .get();
        Assert.assertEquals(expected, spy.get(Samples.Byte.class));
    }

    @Test
    public void canDeserializeByteTTAsFormParam() {
        final Samples.Byte expected = new Samples.Byte((byte) 1);
        resource
                .client()
                .target("/byte/formparam")
                .request()
                .post(Entity.form(new Form("test", "1")));
        Assert.assertEquals(expected, spy.get(Samples.Byte.class));
    }

    @Test
    public void canDeserializeByteTTAsReqHeader() {
        final Samples.Byte expected = new Samples.Byte((byte) 1);
        resource
                .client()
                .target("/byte/reqheader")
                .request()
                .header("test", 1)
                .get();
        Assert.assertEquals(expected, spy.get(Samples.Byte.class));
    }

    @Test
    public void canSerializeByteTTAsRespBody() {
        final String expected = "1";
        final Response req = resource
                .client()
                .target("/byte/respbody")
                .request()
                .get();
        final String rawBody = req.readEntity(String.class);
        Assert.assertEquals(expected, rawBody);
    }

    @Test
    public void canSerializeByteTTWhenNestedInRespBody() {
        final String expected = "{\"id\":1,\"somedata\":\"qwe\"}";
        final Response req = resource
                .client()
                .target("/byte/nestedrespbody")
                .request()
                .get();
        final String rawBody = req.readEntity(String.class);
        Assert.assertEquals(expected, rawBody);
    }

    @Test
    public void canSerializeByteTTWhenValueInMapRespBody() {
        final String expected = "{\"id\":1}";
        final Response req = resource
                .client()
                .target("/byte/maprespbody")
                .request()
                .get();
        final String rawBody = req.readEntity(String.class);
        Assert.assertEquals(expected, rawBody);
    }

    @Test
    public void canSerializeByteTTWhenKeyOfMapRespBody() {
        final String expected = "{\"1\":\"qwe\"}";
        final Response req = resource
                .client()
                .target("/byte/keyinmaprespbody")
                .request()
                .get();
        final String rawBody = req.readEntity(String.class);
        Assert.assertEquals(expected, rawBody);
    }

    @Test
    public void canSerializeByteTTAsRespHeader() {
        final String expected = "1";
        final Response req = resource
                .client()
                .target("/byte/respheader")
                .request()
                .get();
        final String header = req.getHeaderString("test");
        Assert.assertEquals(expected, header);
    }

    @Test
    @Ignore(value = "no way to provide UriBuilder with a transformation for the value, it just calls toString.")
    public void canSerializeByteTTAsPartOfUri() {
        final String expected = "/1";
        final Response req = resource
                .client()
                .property(ClientProperties.FOLLOW_REDIRECTS, false)
                .target("/byte/uripath")
                .request()
                .get();
        final String path = req.getLocation().getPath();
        Assert.assertEquals(expected, path);
    }

    @Test
    public void canDeserializeShortTTAsRequestBody() {
        final String value = "1";
        final Samples.Short expected = new Samples.Short((short) 1);
        resource
                .client()
                .target("/short/reqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Short.class));
    }

    @Test
    public void canDeserializeShortTTWhenNestedInRequestBody() {
        final String value = "{\"id\":1,\"somedata\":\"blah\"}";
        final Samples.Short expected = new Samples.Short((short) 1);
        resource
                .client()
                .target("/short/nestedreqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Short.class));
    }

    @Test
    public void canDeserializeShortTTWhenValueInMapRequestBody() {
        final String value = "{\"id\":1,\"somedata\":2}";
        final Samples.Short expected = new Samples.Short((short) 1);
        resource
                .client()
                .target("/short/mapreqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Short.class));
    }

    @Test
    public void canDeserializeShortTTWhenKeyOfMapRequestBody() {
        final String value = "{\"1\":\"blah\"}";
        final Samples.Short expected = new Samples.Short((short) 1);
        resource
                .client()
                .target("/short/keyinmapreqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Short.class));
    }

    @Test
    public void canDeserializeShortTTAsQueryParam() {
        final Samples.Short expected = new Samples.Short((short) 1);
        resource
                .client()
                .target("/short/queryparam")
                .queryParam("test", 1)
                .request()
                .get();
        Assert.assertEquals(expected, spy.get(Samples.Short.class));
    }

    @Test
    public void canDeserializeShortTTAsPathParam() {
        final Samples.Short expected = new Samples.Short((short) 1);
        resource
                .client()
                .target("/short/pathparam/1")
                .request()
                .get();
        Assert.assertEquals(expected, spy.get(Samples.Short.class));
    }

    @Test
    public void canDeserializeShortTTAsFormParam() {
        final Samples.Short expected = new Samples.Short((short) 1);
        resource
                .client()
                .target("/short/formparam")
                .request()
                .post(Entity.form(new Form("test", "1")));
        Assert.assertEquals(expected, spy.get(Samples.Short.class));
    }

    @Test
    public void canDeserializeShortTTAsReqHeader() {
        final Samples.Short expected = new Samples.Short((short) 1);
        resource
                .client()
                .target("/short/reqheader")
                .request()
                .header("test", 1)
                .get();
        Assert.assertEquals(expected, spy.get(Samples.Short.class));
    }

    @Test
    public void canSerializeShortTTAsRespBody() {
        final String expected = "1";
        final Response req = resource
                .client()
                .target("/short/respbody")
                .request()
                .get();
        final String rawBody = req.readEntity(String.class);
        Assert.assertEquals(expected, rawBody);
    }

    @Test
    public void canSerializeShortTTWhenNestedInRespBody() {
        final String expected = "{\"id\":1,\"somedata\":\"qwe\"}";
        final Response req = resource
                .client()
                .target("/short/nestedrespbody")
                .request()
                .get();
        final String rawBody = req.readEntity(String.class);
        Assert.assertEquals(expected, rawBody);
    }

    @Test
    public void canSerializeShortTTWhenValueInMapRespBody() {
        final String expected = "{\"id\":1}";
        final Response req = resource
                .client()
                .target("/short/maprespbody")
                .request()
                .get();
        final String rawBody = req.readEntity(String.class);
        Assert.assertEquals(expected, rawBody);
    }

    @Test
    public void canSerializeShortTTWhenKeyOfMapRespBody() {
        final String expected = "{\"1\":\"qwe\"}";
        final Response req = resource
                .client()
                .target("/short/keyinmaprespbody")
                .request()
                .get();
        final String rawBody = req.readEntity(String.class);
        Assert.assertEquals(expected, rawBody);
    }

    @Test
    public void canSerializeShortTTAsRespHeader() {
        final String expected = "1";
        final Response req = resource
                .client()
                .target("/short/respheader")
                .request()
                .get();
        final String header = req.getHeaderString("test");
        Assert.assertEquals(expected, header);
    }

    @Test
    @Ignore(value = "no way to provide UriBuilder with a transformation for the value, it just calls toString.")
    public void canSerializeShortTTAsPartOfUri() {
        final String expected = "/1";
        final Response req = resource
                .client()
                .property(ClientProperties.FOLLOW_REDIRECTS, false)
                .target("/short/uripath")
                .request()
                .get();
        final String path = req.getLocation().getPath();
        Assert.assertEquals(expected, path);
    }

    @Test
    public void canDeserializeLongTTAsRequestBody() {
        final String value = "1";
        final Samples.Long expected = new Samples.Long((long) 1);
        resource
                .client()
                .target("/long/reqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Long.class));
    }

    @Test
    public void canDeserializeLongTTWhenNestedInRequestBody() {
        final String value = "{\"id\":1,\"somedata\":\"blah\"}";
        final Samples.Long expected = new Samples.Long((long) 1);
        resource
                .client()
                .target("/long/nestedreqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Long.class));
    }

    @Test
    public void canDeserializeLongTTWhenValueInMapRequestBody() {
        final String value = "{\"id\":1,\"somedata\":2}";
        final Samples.Long expected = new Samples.Long((long) 1);
        resource
                .client()
                .target("/long/mapreqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Long.class));
    }

    @Test
    public void canDeserializeLongTTWhenKeyOfMapRequestBody() {
        final String value = "{\"1\":\"blah\"}";
        final Samples.Long expected = new Samples.Long((long) 1);
        resource
                .client()
                .target("/long/keyinmapreqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Long.class));
    }

    @Test
    public void canDeserializeLongTTAsQueryParam() {
        final Samples.Long expected = new Samples.Long((long) 1);
        resource
                .client()
                .target("/long/queryparam")
                .queryParam("test", 1)
                .request()
                .get();
        Assert.assertEquals(expected, spy.get(Samples.Long.class));
    }

    @Test
    public void canDeserializeLongTTAsPathParam() {
        final Samples.Long expected = new Samples.Long((long) 1);
        resource
                .client()
                .target("/long/pathparam/1")
                .request()
                .get();
        Assert.assertEquals(expected, spy.get(Samples.Long.class));
    }

    @Test
    public void canDeserializeLongTTAsFormParam() {
        final Samples.Long expected = new Samples.Long((long) 1);
        resource
                .client()
                .target("/long/formparam")
                .request()
                .post(Entity.form(new Form("test", "1")));
        Assert.assertEquals(expected, spy.get(Samples.Long.class));
    }

    @Test
    public void canDeserializeLongTTAsReqHeader() {
        final Samples.Long expected = new Samples.Long((long) 1);
        resource
                .client()
                .target("/long/reqheader")
                .request()
                .header("test", 1)
                .get();
        Assert.assertEquals(expected, spy.get(Samples.Long.class));
    }

    @Test
    public void canSerializeLongTTAsRespBody() {
        final String expected = "1";
        final Response req = resource
                .client()
                .target("/long/respbody")
                .request()
                .get();
        final String rawBody = req.readEntity(String.class);
        Assert.assertEquals(expected, rawBody);
    }

    @Test
    public void canSerializeLongTTWhenNestedInRespBody() {
        final String expected = "{\"id\":1,\"somedata\":\"qwe\"}";
        final Response req = resource
                .client()
                .target("/long/nestedrespbody")
                .request()
                .get();
        final String rawBody = req.readEntity(String.class);
        Assert.assertEquals(expected, rawBody);
    }

    @Test
    public void canSerializeLongTTWhenValueInMapRespBody() {
        final String expected = "{\"id\":1}";
        final Response req = resource
                .client()
                .target("/long/maprespbody")
                .request()
                .get();
        final String rawBody = req.readEntity(String.class);
        Assert.assertEquals(expected, rawBody);
    }

    @Test
    public void canSerializeLongTTWhenKeyOfMapRespBody() {
        final String expected = "{\"1\":\"qwe\"}";
        final Response req = resource
                .client()
                .target("/long/keyinmaprespbody")
                .request()
                .get();
        final String rawBody = req.readEntity(String.class);
        Assert.assertEquals(expected, rawBody);
    }

    @Test
    public void canSerializeLongTTAsRespHeader() {
        final String expected = "1";
        final Response req = resource
                .client()
                .target("/long/respheader")
                .request()
                .get();
        final String header = req.getHeaderString("test");
        Assert.assertEquals(expected, header);
    }

    @Test
    @Ignore(value = "no way to provide UriBuilder with a transformation for the value, it just calls toString.")
    public void canSerializeLongTTAsPartOfUri() {
        final String expected = "/1";
        final Response req = resource
                .client()
                .property(ClientProperties.FOLLOW_REDIRECTS, false)
                .target("/long/uripath")
                .request()
                .get();
        final String path = req.getLocation().getPath();
        Assert.assertEquals(expected, path);
    }

    @Test
    public void canDeserializeBooleanTTAsRequestBody() {
        final String value = "true";
        final Samples.Boolean expected = new Samples.Boolean(true);
        resource
                .client()
                .target("/boolean/reqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Boolean.class));
    }

    @Test
    public void canDeserializeBooleanTTWhenNestedInRequestBody() {
        final String value = "{\"id\":true,\"somedata\":\"blah\"}";
        final Samples.Boolean expected = new Samples.Boolean(true);
        resource
                .client()
                .target("/boolean/nestedreqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Boolean.class));
    }

    @Test
    public void canDeserializeBooleanTTWhenValueInMapRequestBody() {
        final String value = "{\"id\":true,\"somedata\":false}";
        final Samples.Boolean expected = new Samples.Boolean(true);
        resource
                .client()
                .target("/boolean/mapreqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Boolean.class));
    }

    @Test
    public void canDeserializeBooleanTTWhenKeyOfMapRequestBody() {
        final String value = "{\"true\":\"blah\"}";
        final Samples.Boolean expected = new Samples.Boolean(true);
        resource
                .client()
                .target("/boolean/keyinmapreqbody")
                .request()
                .post(Entity.entity(value, MediaType.APPLICATION_JSON));
        Assert.assertEquals(expected, spy.get(Samples.Boolean.class));
    }

    @Test
    public void canDeserializeBooleanTTAsQueryParam() {
        final Samples.Boolean expected = new Samples.Boolean(true);
        resource
                .client()
                .target("/boolean/queryparam")
                .queryParam("test", true)
                .request()
                .get();
        Assert.assertEquals(expected, spy.get(Samples.Boolean.class));
    }

    @Test
    public void canDeserializeBooleanTTAsPathParam() {
        final Samples.Boolean expected = new Samples.Boolean(true);
        resource
                .client()
                .target("/boolean/pathparam/true")
                .request()
                .get();
        Assert.assertEquals(expected, spy.get(Samples.Boolean.class));
    }

    @Test
    public void canDeserializeBooleanTTAsFormParam() {
        final Samples.Boolean expected = new Samples.Boolean(true);
        resource
                .client()
                .target("/boolean/formparam")
                .request()
                .post(Entity.form(new Form("test", "true")));
        Assert.assertEquals(expected, spy.get(Samples.Boolean.class));
    }

    @Test
    public void canDeserializeBooleanTTAsReqHeader() {
        final Samples.Boolean expected = new Samples.Boolean(true);
        resource
                .client()
                .target("/boolean/reqheader")
                .request()
                .header("test", true)
                .get();
        Assert.assertEquals(expected, spy.get(Samples.Boolean.class));
    }

    @Test
    public void canSerializeBooleanTTAsRespBody() {
        final String expected = "true";
        final Response req = resource
                .client()
                .target("/boolean/respbody")
                .request()
                .get();
        final String rawBody = req.readEntity(String.class);
        Assert.assertEquals(expected, rawBody);
    }

    @Test
    public void canSerializeBooleanTTWhenNestedInRespBody() {
        final String expected = "{\"id\":true,\"somedata\":\"qwe\"}";
        final Response req = resource
                .client()
                .target("/boolean/nestedrespbody")
                .request()
                .get();
        final String rawBody = req.readEntity(String.class);
        Assert.assertEquals(expected, rawBody);
    }

    @Test
    public void canSerializeBooleanTTWhenValueInMapRespBody() {
        final String expected = "{\"id\":true}";
        final Response req = resource
                .client()
                .target("/boolean/maprespbody")
                .request()
                .get();
        final String rawBody = req.readEntity(String.class);
        Assert.assertEquals(expected, rawBody);
    }

    @Test
    public void canSerializeBooleanTTWhenKeyOfMapRespBody() {
        final String expected = "{\"true\":\"qwe\"}";
        final Response req = resource
                .client()
                .target("/boolean/keyinmaprespbody")
                .request()
                .get();
        final String rawBody = req.readEntity(String.class);
        Assert.assertEquals(expected, rawBody);
    }

    @Test
    public void canSerializeBooleanTTAsRespHeader() {
        final String expected = "true";
        final Response req = resource
                .client()
                .target("/boolean/respheader")
                .request()
                .get();
        final String header = req.getHeaderString("test");
        Assert.assertEquals(expected, header);
    }

    @Test
    @Ignore(value = "no way to provide UriBuilder with a transformation for the value, it just calls toString.")
    public void canSerializeBooleanTTAsPartOfUri() {
        final String expected = "/true";
        final Response req = resource
                .client()
                .property(ClientProperties.FOLLOW_REDIRECTS, false)
                .target("/boolean/uripath")
                .request()
                .get();
        final String path = req.getLocation().getPath();
        Assert.assertEquals(expected, path);
    }
}
