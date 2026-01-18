# Sudoku Generator (Wave Function Collapse)

This project is a Java implementation of a **Sudoku board generator** using the **Wave Function Collapse (WFC)** algorithm. It was developed as an academic exercise focusing on algorithm design, correctness, concurrency, and effective use of Java collections and streams.

## Overview

- Generates valid 9×9 Sudoku **solution** grids  
- Enforces all Sudoku rules (row, column, and 3×3 block constraints)  
- Uses the Wave Function Collapse algorithm to reduce possibilities  
- Detects invalid states and regenerates boards when necessary  

## Core Classes

### SudokuGrid

- Represents a Sudoku board and its final values (facts)
- Provides methods to:
  - Set, retrieve, and clear cell values
  - Validate value placement
  - Detect row, column, and block conflicts
- A placement may be invalid for multiple reasons at once

### SudokuGenerator

- Implements the Wave Function Collapse algorithm
- Maintains possible values for each cell
- Selects the cell with the lowest entropy (fewest possibilities)
- Randomly collapses a possibility into a fact
- Propagates constraints across rows, columns, and blocks
- Restarts generation if an invalid state is reached

## Multithreaded Extension

- Generates **10,000 unique Sudoku boards**
- Uses multiple threads to:
  - Generate boards
  - Check for uniqueness
  - Save boards to disk
- Boards are considered identical under:
  - Rotation (90°, 180°, 270°)
  - Horizontal and vertical mirroring
  - Any combination of rotation and mirroring
- Boards are saved as numbered text files (`0.txt`, `1.txt`, `2.txt`, ...)

## Technologies & Concepts

- Java  
- Java Collections Framework  
- Java Streams API  
- Multithreading and synchronization  
- Algorithm design  
- File I/O  
