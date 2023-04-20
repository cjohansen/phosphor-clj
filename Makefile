target:
	mkdir -p target

target/phosphor-icons.zip: target
	wget -O target/phosphor-icons.zip https://phosphoricons.com/assets/phosphor-icons.zip

target/phosphor-icons: target/phosphor-icons.zip
	unzip -o target/phosphor-icons.zip -d target/phosphor-icons

update-icons: target/phosphor-icons
	clojure -M:dev -m phosphor.icons target/phosphor-icons/2.0.0/SVGs

.PHONY: update-icons
