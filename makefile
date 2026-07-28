
JAVAC = javac
JAVA  = java

SRCS = LockOneExperiment.java LockTwoExperiment.java PetersonLockExperiment.java

#Everything as a target

all: $(SRCS:.java=.class)

# Pattern rule: .java map to .class
%.class: %.java
	$(JAVAC) $<

# run the targets

run-one: LockOneExperiment.class
	$(JAVA) LockOneExperiment

run-two: LockTwoExperiment.class
	$(JAVA) LockTwoExperiment

run-peterson: PetersonLockExperiment.class
	$(JAVA) PetersonLockExperiment

run-all: all
	$(JAVA) LockOneExperiment
	$(JAVA) LockTwoExperiment
	$(JAVA) PetersonLockExperiment

# build and run individual files by name
LockOneExperiment: LockOneExperiment.class
	$(JAVA) LockOneExperiment

LockTwoExperiment: LockTwoExperiment.class
	$(JAVA) LockTwoExperiment

PetersonLockExperiment: PetersonLockExperiment.class
	$(JAVA) PetersonLockExperiment


clean:
	-@del /Q *.class 2>NUL || $(RM) *.class 2>/dev/null; true

.PHONY: all run-one run-two run-peterson run-all clean
