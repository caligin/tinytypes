# TinyTypes

[![Build Status](https://travis-ci.org/caligin/tinytypes.svg?branch=master)](https://travis-ci.org/caligin/tinytypes)

## What

Type safe wrappers on native types && support for commonly used frameworks.

See [my slides 'bout it](http://slides.com/caligin/tinytypes#/).

## Status

Supported libraries/frameworks:

- Jackson
- Jersey

this implies:
- Dropwizard (resources only, only JSON mediaType)

## Use with Jackson

Add jitpack repository (Maven):
```
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

Add jackson dep (Maven):
```
<dependency>
    <groupId>com.github.caligin.tinytypes</groupId>
    <artifactId>tinytypes-jackson</artifactId>
    <version>1.0.1</version>
</dependency>
```

Register module on ObjectMapper:
```
new ObjectMapper().registerModule(new TinyTypesModule())
```

Profit!

## Use with Jersey

Add jitpack repository (Maven):
```
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

Add jersey dep (Maven):
```
<dependency>
    <groupId>com.github.caligin.tinytypes</groupId>
    <artifactId>tinytypes-jersey</artifactId>
    <version>1.0.1</version>
</dependency>
```

Register module on ObjectMapper:
```
new ObjectMapper().registerModule(new TinyTypesModule())
```

Register the ParamProvider:
```
.addProvider(TinyTypesParamProvider.class)
```

Register the HeaderDelegates:
```
JerseyResponseSupport.registerTinyTypes(
        Samples.Str.class,
        ... (other TTs here)
);

```

Profit!

## Planned for the future

- Android Parchelable
- Hibernate
- Spring
- Xstream

I won't prioritize until I have a real necessity to use, PRs are welcome.
