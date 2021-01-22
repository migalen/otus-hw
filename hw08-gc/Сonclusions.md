### VM options: -Xms512m -Xmx512m -XX:+UseSerialGC

Для size = 5 * 1000 (loopCounter = 20000).

Worked time: 214 216 ms

Для size = 500 * 1000 (loopCounter = 10000)

Execution time: 153 631 ms

Total cleaning count: 559

Average cleaning time: 10 (ms)

Max cleaning time: 29 (ms)

### VM options: -Xms512m -Xmx512m -XX:+UseParallelGC

Небольшой размер рабочего листа size = 5 * 1000 (loopCounter = 20000)

Worked time: 214 113 ms

Большой размер рабочего листа size = 500 * 1000 (loopCounter = 10000)

Execution time: 142 101 (ms)

Total cleaning count: 509

Average cleaning time: 17 (ms)

Max cleaning time: 46 (ms)

### VM options: -Xms512m -Xmx512m -XX:+UseG1GC

Небольшой размер рабочего листа size = 5 * 1000 (loopCounter = 20000)

Worked time: 238 743 ms

Большой размер рабочего листа size = 500 * 1000 (loopCounter = 10000)

Execution time: 171 245 (ms)

Total cleaning count: 253

Average cleaning time: 3 (ms) 

Max cleaning time: 13 (ms)

### Делаем выводы:

Сравнение результатов при малых нагрузках (для size = 5 * 1000 (loopCounter = 20000))
 
 Результаты работы GC не сильно отличаются.
 
 SerialGC: 214 216 ms 
 
 ParallelGC: 214 113 ms
 
 G1GC: 238 743 ms 
 
 Лучше всех показал себя ParallelGC, но он также дает значительную нагрузку на процессор (до 16%).

Сравнение результатов при высоких нагрузках (для size = 5 * 1000 (loopCounter = 20000))
 
 GC показали уже значительную разницу во времени выполнения программы.
 
 SerialGC: 153 631 ms 

 ParallelGC: 142 101 ms 
 
 G1GC: 171 245 ms 
 
 ParallelGC показал лучшие результаты.
 
 Поэтому для данной программы на тестируемом ПК лучшим выбором будет ParallelGC.