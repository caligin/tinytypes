.PHONY: tinytypes testing meta jackson jersey

all: jackson jersey

jackson: meta
	$(MAKE) -C $@ -f ../component.mk

jersey: meta
	$(MAKE) -C $@ -f ../component.mk

meta: testing
	$(MAKE) -C $@ -f ../component.mk

testing: tinytypes
	$(MAKE) -C $@ -f ../component.mk

tinytypes:
	$(MAKE) -C $@ -f ../component.mk
