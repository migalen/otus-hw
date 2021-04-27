### VM options: -Xms64m -Xmx64m -XX:+UseSerialGC

Execution time:      1096944 (ms)

Total cleaning count:      12

Average cleaning time:      80 (ms)

Max cleaning time:      1096947 (ms)

### VM options: -Xms64m -Xmx64m -XX:+UseParallelGC

Execution time:      531900 (ms)

Total cleaning count:      11

Average cleaning time:      82 (ms)

Max cleaning time:      531909 (ms)

### VM options: -Xms64m -Xmx64m -XX:+UseG1GC

Execution time:      1317460 (ms)

Total cleaning count:      63

Average cleaning time:      24 (ms)

Max cleaning time:      1317464 (ms)

### Делаем выводы:

G1 работает дольше. Сборки у G1 осуществляются часто и равномерно, о чем говорит количество запусков GC. G1 делает кратковременные сборки мусора и позволяет приложению дольше функционировать (до падения), также меньше времени занимают сборки мусора. Parallel, в начале работы приложения, работал эффективно, так же как и G1. Но с увеличением дефицита памяти, GC работает все дольше и дольше. G1 работает эффективнее чем Parallel, позволяя приложению работать с хорошей производительностью за счет того что тратится меньше времени на сборки мусора. Для данной задачи на данном ПК желательно по-умолчанию использовать именно G1.