# TwoLevelCache

Create a configurable two-level main.java.cache (for caching Objects). Level 1 is
memory, level 2 is filesystem. Config params should let one specify the 
main.java.cache strategies and max sizes of level 1 and 2.

запуск
java -jar TwoLevelCache.jar

может принимать 3 аргумента:
1 - int - max sizes of level 1
2 - int - max sizes of level 2
3 - String - file path (example, C:/temp)

для всех параметров заданы значения по умолчанию, можно запускать без параметров