~~~
javac ProgramOptimizer; java ProgramOptimizer prog3 out1
~~~

prog3 :
~~~
x = 1
w = 101
y = x - x
x = y * w
~~~
out1 :
~~~ 
x = 0
~~~

<br>
<br>

~~~
javac ProgramOptimizer; java ProgramOptimizer prog3bis out2
~~~

prog3bis :
~~~
x = 101
w = 101
y = w - x
x = y * w
~~~
out2 :
~~~
Pas de resultat : 
	pour x : valeur indéterminée
~~~


