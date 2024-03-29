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

## Defining a TinyType

Add tinytypes dep (Maven):
```
<dependency>
    <groupId>tech.anima</groupId>
    <artifactId>tinytypes</artifactId>
    <version>1.1.1</version>
</dependency>
```

- Extend one of the base TinyTypes classes
- It must not be `abstract`
- Provide a constructor matching the one in the base class

Example:
```
public class SomeKindOfId extends StringTinyType {
    public SomeKindOfId(String value) {
        super(value);
        // ... additional validation or restrictions here
    }
}
```

## Use with Jackson

Add jackson dep (Maven):
```
<dependency>
    <groupId>tech.anima</groupId>
    <artifactId>jackson-datatype-tinytypes</artifactId>
    <version>1.1.1</version>
</dependency>
```

Register module on ObjectMapper:
```
new ObjectMapper().registerModule(new TinyTypesModule())
```

Profit!
```
objectMapper.writeValueAsString(new SomeKindOfId("ag7weq")); // "ag7weq"

objectMapper.readValue("ag7weq", SomeKindOfId.class); // SomeKindOfId#ag7weq
```

## Use with Jersey

Add jersey dep (Maven):
```
<dependency>
    <groupId>tech.anima</groupId>
    <artifactId>tinytypes-jersey</artifactId>
    <version>1.1.1</version>
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

(See tinytypes-examples test cases for more on how to use them with Jersey)

## Planned for the future

- Android Parchelable
- Hibernate
- Spring
- Xstream

I won't prioritize until I have a real necessity to use, PRs are welcome.
