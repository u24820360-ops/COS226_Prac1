# COS226 Practical 1

## Prerequisites

Verify you have both java(version 8+) and make installed:

```bash
java -version
make --version
```

## Setup

### Option 1 — From the zip file

```bash
unzip Prac1.zip -d COS226_Prac1
cd COS226_Prac1
```

### Option 2 — From the repository

```bash
git clone https://github.com/u24820360-ops/COS226_Prac1.git
cd COS226_Prac1
```

## Run

```bash
# Build and run all experiments
make all

# Run specific experiments
make LockOneExperiment
make LockTwoExperiment
make PetersonLockExperiment

# Clean compiled class files
make clean
```
