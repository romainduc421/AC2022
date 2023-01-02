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
Valeur variables à la fin :
w = 2
x = 0
y = 0
Optimisation :
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
Valeur variables à la fin :
w = 2
x = 2
y = 2
Pas d'optimisation : 
	pour x : valeur indéterminée
~~~

<br><br>
des simplifications qu'il est possible d'implementer : <br>

- Si la dernière ligne possède une variable correcte alors on simplifie le programme par juste cette ligne (implementee ici)
- Si suite d'affectation d'une meme variable, ne conserver que la derniere
- Si variable temporaire inutile (valeur deja connue) : x=4; y=x; ---> y=4;
- Si quelque chose fois zéro alors remplacer par zéro : y=1; x=y*0; ---> x=0;
- Si variable non utilisée, la supprimer


