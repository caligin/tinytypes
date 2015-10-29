.PHONY: tinytypes testing meta jackson jersey

all: jackson jersey

jackson: meta
	$(MAKE) -C $@

jersey: meta
	$(MAKE) -C $@

meta: testing
	$(MAKE) -C $@

testing: tinytypes
	$(MAKE) -C $@

tinytypes:
	$(MAKE) -C $@
