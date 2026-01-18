# Sudoku Generator (Wave Function Collapse)

This project is a Java implementation of a Sudoku board generator using the Wave Function Collapse (WFC) algorithm. It was developed as an academic exercise focusing on algorithms, concurrency, and efficient use of Java collections and streams.

## Overview

Generates valid 9×9 Sudoku solution grids

Enforces all Sudoku rules (row, column, and block constraints)

Uses Wave Function Collapse to progressively reduce possibilities

Displays generated boards and reports invalid placements

## Core Classes
SudokuGrid

Represents a Sudoku board

Stores final values (facts) for each cell

Provides methods to:

Set, retrieve, and clear values

Validate placements

Detect row, column, and block conflicts (individually or combined)

SudokuGenerator

Implements the Wave Function Collapse algorithm

Maintains possible values for each cell

Selects cells with the lowest entropy (fewest possibilities)

Randomly collapses possibilities into facts

Regenerates the board if an invalid state is reached

## Multithreaded Extension

The generator was extended to:

Produce 10,000 unique Sudoku boards

Use multiple threads to:

Generate boards

Check for uniqueness

Save valid boards to disk

Treat boards as identical under:

Rotation (90°, 180°, 270°)

Horizontal and vertical mirroring

Save boards as numbered text files (0.txt, 1.txt, …)

## Technologies & Concepts

Java

Java Collections Framework

Java Streams API

Multithreading & synchronization

Algorithm design and optimization

File I/O
