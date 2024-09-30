# GraphitDB: A Distributed Graph Database

GraphitDB is a highly scalable distributed graph database designed for efficient graph storage and retrieval. It is built using Java, Spring Boot, gRPC, Redis, and a custom partitioning strategy to manage and query graph data distributed across multiple nodes.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Installation](#installation)
- [Usage](#usage)
- [Components](#components)
- [Contributing](#contributing)
- [License](#license)

## Overview
GraphitDB is designed to handle large-scale graph databases distributed across a cluster of servers. It supports:
- **Nodes and Edges**: Stores graph structures as nodes and edges with support for properties and labels.
- **Partitioning**: Distributes graph data across multiple servers using a hash-based partitioning strategy.
- **Parallel Execution**: Leverages multi-threaded and parallel execution for querying and managing graph data across distributed partitions.
- **gRPC Integration**: Uses gRPC for inter-service communication, allowing fast and efficient remote procedure calls between distributed nodes.
- **Redis Caching**: Utilizes Redis for indexing and caching frequently accessed graph data.

## Features
- **Distributed Graph Storage**: Store nodes and edges distributed across multiple partitions.
- **gRPC Communication**: Efficient cross-node communication using gRPC for fetching, storing, and deleting graph data.
- **Custom Partitioning**: Implements custom partition management to distribute graph data across nodes based on the node/edge IDs.
- **Edge and Node Operations**: CRUD operations for both nodes and edges, with support for outgoing and incoming edges.
- **Asynchronous Execution**: Supports parallel and asynchronous execution of tasks, ensuring high throughput and responsiveness.
- **Resilient**: Built-in error handling for node and edge not found exceptions.

## Architecture
The system follows a microservice architecture, with each server responsible for managing a sub-graph of the overall graph stored in the system.

- **Node Service**: Manages CRUD operations for nodes in the graph.
- **Edge Service**: Manages CRUD operations for edges between nodes.
- **Partition Manager**: Handles the logic for distributing nodes and edges across the available partitions (servers).
- **gRPC Clients and Servers**: Each server instance includes a gRPC server and client for inter-node communication.
- **Redis**: Used as a caching layer for fast retrieval of graph data and to manage indices.


## Installation

### Prerequisites
- **Java 17+** (JDK 22 recommended)
- **Maven** (for building the project)
- **Redis** (for indexing and caching)
- **Docker** (optional, for containerization)
- **gRPC** (for communication between servers)

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/GraphitDB.git
   cd GraphitDB
2. **Build the project**
   ./gradlew build
3. **Configure Redis** Ensure Redis is running locally or configure its connection details in application.properties
   ```
   spring.redis.host=localhost
   spring.redis.port=6379

