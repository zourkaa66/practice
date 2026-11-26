.PHONY: help run build test lint format check clean

help:  ## Show all commands
	@grep -E '^[a-zA-Z_-]+:.*?## ' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "  \033[36m%-10s\033[0m %s\n", $$1, $$2}'

run:  ## Start the server on port 8080
	./gradlew run

build:  ## Compile and package
	./gradlew build -x test

test:  ## Run all tests
	./gradlew test

lint:  ## Check style (ktlint + detekt)
	./gradlew lintKotlin detekt

format:  ## Auto-fix style
	./gradlew formatKotlin

check:  ## Lint + detekt + tests (run before a PR)
	./gradlew check

clean:  ## Delete build output
	./gradlew clean