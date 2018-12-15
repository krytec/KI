# KI
KI Listenfach WS 18/19

### 1. Definition des Zustandsraums:
Alle möglichen Anordnungen von Zahlen von 0 bis 8.
S = {(0,1,2,3,4,5,6,7,8),...,(8,7,6,5,4,3,2,1,0)}
  
### 2. Beschreibung des Initialzustands:
si = (2,8,3,1,6,4,7,0,5)

### 3. Zielbeschreibung:
Sg = {(1,2,3,8,0,4,7,6,5)}
sg = (1,2,3,8,0,4,7,6,5)

### 4. Menge von Operatoren:

* UP: x-3, wo x auf einem Intervall von Position 4 bis Position 9 in der Anordnung liegt.
Mögliche Positionen von x: (...,...,...,x,x,x,x,x,x)

* DOWN: x+3, wo x auf einem Intervall von Position 1 bis Position 6 in der Anordnung liegt.
Mögliche Positionen von x: (x,x,x,x,x,x,....,...,...)

* LEFT: x-1, wo x nicht auf Positionen 1, 4, 7 in der Anordnung liegt.
Mögliche Positionen von x: (...,x,x,...,x,x,...,x,x)

* RIGHT: x+1, wo x nicht auf Positionen 3, 6, 9 in der Anordnung liegt.
Mögliche Positionen von x: (x,x,...,x,x,...,x,x,...)

### 5. Pfadkostenfunktion:
Uniforme Kosten: g(UP)=1, g(DOWN)=1, g(LEFT)=1, g(RIGHT)=1.
