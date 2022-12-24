~~~
$ javac Toto.java
$ java Toto
-2147483648
~~~
au lieu de ```2147483648```<br>
```Integer.MAX_VALUE = pow(2,31) - 1 =   2 147 483 647```<br>
```[-2147483648, 2147483648] ```

prog2 :
~~~
x = 2147483644;x = x + 4
~~~
Le resultat est trop grand car toute variable entière ne pourra stocker une valeur au-delà de 2^31-1.<br>
Ce faisant, la memoire debordera et la valeur deviendra negative.<br>

~~~
Integer.MAX_VALUE + 1
N = -2147483648
Integer.MIN_VALUE - 1
N = 2147483647
~~~

En Python, avec le programme,
~~~
x=2147483647
y=1
x=x+y
print(x)
~~~
On obtient : 2147483648, ce qui est le bon résultat