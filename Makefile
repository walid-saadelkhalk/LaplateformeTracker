# Chemins
SRC_DIR = src
OUT_DIR = out
LIB_DIR = lib
LIB_JAR = $(LIB_DIR)/mysql-connector-j-9.0.0.jar
JAVAFX_LIB_DIR = "C:/Program Files/openjfx-22.0.1_windows-x64_bin-sdk/javafx-sdk-22.0.1/lib"

# Fichiers source et classes
SRC_FILES = Main.java \
            $(SRC_DIR)/Mainloop.java \
            $(SRC_DIR)/MainloopGraphique.java \
            $(SRC_DIR)/model/Database.java \
            $(SRC_DIR)/model/AdminRepository.java \
            $(SRC_DIR)/model/StudentRepository.java \
            $(SRC_DIR)/model/Connecting.java \
            $(SRC_DIR)/controler/Authentication.java \
            $(SRC_DIR)/menu/MenuAuthentication.java \
            $(SRC_DIR)/menu/MenuAuthenticationGraphique.java \
            $(SRC_DIR)/menu/MenuAdmin.java \
            $(SRC_DIR)/menu/MenuAdminGraphique.java \
            $(SRC_DIR)/menu/MenuStudent.java \
            $(SRC_DIR)/menu/MenuStudentGraphique.java 

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
	cp src/menu/styles.css $(OUT_DIR)
	$(JAVAC) --module-path $(JAVAFX_LIB_DIR) --add-modules javafx.controls,javafx.fxml -cp "$(LIB_JAR)$(CP_SEP)$(OUT_DIR)" -d $(OUT_DIR) $(SRC_FILES)

# Exécution
run: all
	$(JAVA) --module-path $(JAVAFX_LIB_DIR) --add-modules javafx.controls,javafx.fxml -cp "$(LIB_JAR)$(CP_SEP)$(OUT_DIR)" $(MAIN_CLASS)


# Nettoyage
clean:
	$(RM) $(OUT_DIR)

# Règles par défaut
.PHONY: all run clean
