version=$(shell mvn help:evaluate -Dexpression="project.version" | grep -v INFO)
build_final_name=$(shell mvn help:evaluate -Dexpression="project.build.finalName" | grep -v INFO)
sources=$(shell find src/main/ -name '*.java')
tests=$(shell find src/test/ -name '*.java')
pom=$(build_final_name).pom
artifacts=$(addprefix target/, $(addsuffix .jar, $(build_final_name) $(build_final_name)-sources $(build_final_name)-javadoc) $(pom))
signatures=$(addsuffix .asc, $(artifacts))

.PHONY: clean install sign sources javadoc pom bundle
all: bundle

target/:
	mkdir -p target

target/.versionok: pom.xml | target/
	echo $(version) | grep -v SNAPSHOT
	touch $@

install: target/$(build_final_name).jar
target/$(build_final_name).jar: pom.xml $(sources) $(tests) target/.versionok
	mvn install

sources: target/$(build_final_name)-sources.jar
target/$(build_final_name)-sources.jar: pom.xml $(sources)
	mvn source:jar

javadoc: target/$(build_final_name)-javadoc.jar
target/$(build_final_name)-javadoc.jar: pom.xml $(sources)
	mvn javadoc:jar

pom: target/$(pom.xml)
target/$(pom): pom.xml
	cp pom.xml $@

%.asc: %
	gpg -ab $<

sign: $(signatures)
$(signatures):

bundle: target/bundle.jar
target/bundle.jar: $(signatures) $(artifacts)
	jar -cvf target/bundle.jar $(addprefix -C target/ , $(notdir $^))

clean:
	mvn clean
