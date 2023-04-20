target:
	mkdir -p target

target/phosphor-icons.zip: target
	wget -O target/phosphor-icons.zip https://phosphoricons.com/assets/phosphor-icons.zip

target/phosphor-icons: target/phosphor-icons.zip
	unzip -o target/phosphor-icons.zip -d target/phosphor-icons

update-icons: target/phosphor-icons
	clojure -M:dev -m phosphor.icons target/phosphor-icons/2.0.0/SVGs

phosphor.jar: src/phosphor/*
	rm -f phosphor.jar && clojure -A:dev -M:jar

deploy: phosphor.jar
	mvn deploy:deploy-file -Dfile=phosphor.jar -DrepositoryId=clojars -Durl=https://clojars.org/repo -DpomFile=pom.xml

.PHONY: update-icons deploy
