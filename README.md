# EngineEnqueuerDequeuer
what this engine do?<br>
  1- read comma seprated lines from file<br>
  2- extract data from every line and used to populate Transaction model<br>
  2- enqueue in oracle advanced queue<br>
  3- dequeue and insert data in partioned table<br>
  
 db schema :<br>
 <a href="/DDL.sql">DDL.sql</a>
 
 to generate file use:
 <a href="/engines/CustomerCreator.java" >CustomerCreator.java</a>
  
