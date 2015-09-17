package org.anima.tinytypes.examples;

import io.dropwizard.testing.junit.ResourceTestRule;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.RuntimeDelegate;
import org.anima.tinytypes.IntTinyType;
import org.anima.tinytypes.StringTinyType;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.internal.AbstractRuntimeDelegate;
import org.glassfish.jersey.server.internal.RuntimeDelegateImpl;
import org.glassfish.jersey.spi.HeaderDelegateProvider;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

public class DropwizardIntegrationTest {

    @Provider
    public static class StringTTParamProvider implements ParamConverterProvider {

        @Override
        public <T> ParamConverter<T> getConverter(final Class<T> rawType, Type genericType, Annotation[] annotations) {
            if (rawType.getSuperclass().equals(StringTinyType.class)) {
                return new ParamConverter<T>() {

                    @Override
                    public T fromString(String value) {
                        try {
                            return rawType.getConstructor(String.class).newInstance(value);
                        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    @Override
                    public String toString(T value) {
                        return ((StringTinyType) value).value;
                    }
                };
            }
            return null;
        }
    }

    @Provider
    @Produces("application/json;charset=utf-8")
    public static class SampleStringTTJsonBodyWriter implements MessageBodyWriter<Samples.Str> {

        @Override
        public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
            return type.getSuperclass().equals(StringTinyType.class);
        }

        @Override
        public long getSize(Samples.Str t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
            return -1;
        }

        @Override
        public void writeTo(Samples.Str t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
            entityStream.write('"');
            entityStream.write(t.value.getBytes(StandardCharsets.UTF_8));
            entityStream.write('"');
        }

    }

    @Provider
    public static class StringTTHeaderDelegateProvider<T extends StringTinyType> implements HeaderDelegateProvider<T> {

        private final Class<T> concrete;

        public StringTTHeaderDelegateProvider(Class<T> concrete) {
            this.concrete = concrete;
        }

        @Override
        public boolean supports(Class<?> type) {
            return concrete.equals(type);
        }

        @Override
        public String toString(StringTinyType value) {
            return value.value;
        }

        @Override
        public T fromString(String value) {
            try {
                return concrete.getConstructor(String.class).newInstance(value);
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    @Provider
    public static class IntTTParamProvider implements ParamConverterProvider {

        @Override
        public <T> ParamConverter<T> getConverter(final Class<T> rawType, Type genericType, Annotation[] annotations) {
            if (rawType.getSuperclass().equals(IntTinyType.class)) {
                return new ParamConverter<T>() {

                    @Override
                    public T fromString(String value) {
                        try {
                            return rawType.getConstructor(int.class).newInstance(Integer.parseInt(value));
                        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    @Override
                    public String toString(T value) {
                        return Integer.toString(((IntTinyType) value).value);
                    }
                };
            }
            return null;
        }
    }

    @Provider
    @Produces("application/json;charset=utf-8")
    public static class SampleIntTTJsonBodyWriter implements MessageBodyWriter<Samples.Integer> {

        @Override
        public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
            return type.getSuperclass().equals(IntTinyType.class);
        }

        @Override
        public long getSize(Samples.Integer t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
            return -1;
        }

        @Override
        public void writeTo(Samples.Integer t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
            entityStream.write(Integer.toString(t.value).getBytes(StandardCharsets.UTF_8));
        }

    }

    @Provider
    public static class IntTTHeaderDelegateProvider<T extends IntTinyType> implements HeaderDelegateProvider<T> {

        private final Class<T> concrete;

        public IntTTHeaderDelegateProvider(Class<T> concrete) {
            this.concrete = concrete;
        }

        @Override
        public boolean supports(Class<?> type) {
            return concrete.equals(type);
        }

        @Override
        public String toString(IntTinyType value) {
            return Integer.toString(value.value);
        }

        @Override
        public T fromString(String value) {
            try {
                return concrete.getConstructor(int.class).newInstance(Integer.parseInt(value));
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public static class JerseyResponseSupport {

        public static void registerTinyTypes(Class<?> head, Class<?>... tail) {
            final Set<HeaderDelegateProvider> systemRegisteredHeaderProviders = stealAcquireRefToHeaderDelegateProviders();
            register(head, systemRegisteredHeaderProviders);
            for (Class<?> tt : tail) {
                register(tt, systemRegisteredHeaderProviders);
            }
        }

        private static void register(Class<?> tt, final Set<HeaderDelegateProvider> systemRegisteredHeaderProviders) {
            final Class<?> kind = tt.getSuperclass();
            if (StringTinyType.class.equals(kind)) {
                systemRegisteredHeaderProviders.add(new StringTTHeaderDelegateProvider(tt));
                return;
            }
            if (IntTinyType.class.equals(kind)) {
                systemRegisteredHeaderProviders.add(new IntTTHeaderDelegateProvider(tt));
                return;
            }
            throw new IllegalArgumentException(String.format("Not a supported TinyType: %s", tt.getCanonicalName()));
        }

        private static Set<HeaderDelegateProvider> stealAcquireRefToHeaderDelegateProviders() {
            try {
                final RuntimeDelegate instance = RuntimeDelegate.getInstance();
                final RuntimeDelegateImpl s = (RuntimeDelegateImpl) instance;
                final Field declaredField;
                declaredField = AbstractRuntimeDelegate.class.getDeclaredField("hps");
                declaredField.setAccessible(true);
                final Set<HeaderDelegateProvider> get = (Set<HeaderDelegateProvider>) declaredField.get(s);
                return get;
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static ResourceSpy spy = new ResourceSpy();

    @ClassRule
    public static final ResourceTestRule resource = ResourceTestRule.builder()
            .addResource(new TinyTypesResource(spy))
            .addProvider(StringTTParamProvider.class)
            .addProvider(SampleStringTTJsonBodyWriter.class)
            .addProvider(IntTTParamProvider.class)
            .addProvider(SampleIntTTJsonBodyWriter.class)
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
            return new Samples.Str("asd");
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
