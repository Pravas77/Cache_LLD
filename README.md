# LRU Cache with TTL – Low Level Design

A generic, thread-safe in-memory cache in Java with **LRU (Least Recently Used) eviction** and **per-entry TTL (time to live)**. Both `get` and `put` run in **O(1)**.

## Features
- Generic keys and values: `Cache<K, V>`
- Fixed capacity; when full, the least recently used entry is evicted
- Each entry has its own TTL (in seconds); an expired entry is removed when it is next read
- Updating an existing key refreshes its value, TTL and position
- Thread-safe `get` and `put`
- Eviction policy is pluggable

## Design

| Class | Responsibility |
|---|---|
| `Cache<K, V>` | Client-facing cache that delegates to a `CacheStrategy` |
| `CacheStrategy<K, V>` | Interface for an eviction policy: `get(key)`, `put(key, value, ttl)` |
| `LruCacheStrategy<K, V>` | LRU implementation using a `HashMap` plus a doubly linked list |
| `LruNode<K, V>` | List node holding key, value, TTL and insertion timestamp |

### How LRU works in O(1)
```
HashMap<K, Node>  ──▶ direct access to any node

head ⇄ [most recent] ⇄ ... ⇄ [least recent] ⇄ tail
```
- **get:** look up the node in the map. If its TTL has passed, remove it and return `null`. Otherwise move it to the front and return the value.
- **put:** if the key exists, update it and move it to the front. If the cache is full, remove the node just before `tail` (least recently used), then insert the new node right after `head`.
- Dummy `head` and `tail` nodes remove edge cases for empty or single-element lists.

## Design patterns
- **Strategy** – `Cache` depends only on `CacheStrategy`, so LFU or FIFO can be added without changing client code.

## Concurrency
- `get` and `put` on `LruCacheStrategy` are `synchronized`. Even a `get` changes the list order, so both operations need exclusive access to keep the map and the list consistent.

## How to run
Requires Java 17+.
```bash
javac -d out src/*.java
java -cp out Main
```
`Main` creates a cache of capacity 3 and inserts `A`, `B`, `C` (TTL 2 s) and `D`. `A` is evicted as least recently used. After waiting 2 seconds, `C` has also expired, while `B` and `D` are still returned.

## Possible extensions
- LFU eviction strategy
- Background cleanup of expired entries
- Lock striping or `ReadWriteLock` for higher read throughput
