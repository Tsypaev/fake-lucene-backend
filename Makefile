VERSION := $(shell mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
SERVICE := fake_lucene_backend

.PHONY: create_image
create_image:
	docker build --build-arg VERSION=$(VERSION) --build-arg SERVICENAME=$(SERVICE) -t tsypaev/$(SERVICE):$(VERSION) -f Dockerfile . 
	docker push tsypaev/$(SERVICE):$(VERSION)
