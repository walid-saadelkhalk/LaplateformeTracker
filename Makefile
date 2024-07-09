# Chemins
SRC_DIR = src
OUT_DIR = out
LIB_DIR = lib
LIB_JAR = $(LIB_DIR)/mysql-connector-j-9.0.0.jar

# Fichiers source et classes
SRC_FILES = Main.java \
            $(SRC_DIR)/Modele/Database.java \
            $(SRC_DIR)/controller/Authentication.java

MAIN_CLASS = Main

# Commandes
JAVAC = javac
JAVA = java
CP_SEP = $(if $(filter $(OS),Windows_NT),;,:)
RM = rm -rf

# Compilation
all: $(OUT_DIR)/$(MAIN_CLASS).class

$(OUT_DIR)/$(MAIN_CLASS).class: $(SRC_FILES)
	@mkdir -p $(OUT_DIR)
	$(JAVAC) -cp $(LIB_JAR) -d $(OUT_DIR) $(SRC_FILES)

# Exécution
run: all
	$(JAVA) -cp "$(LIB_JAR)$(CP_SEP)$(OUT_DIR)" $(MAIN_CLASS)

# Nettoyage
clean:
	$(RM) $(OUT_DIR)

# Règles par défaut
.PHONY: all run clean
