

### 4.1 Algemene eisen (0 punten)

| ID   | Omschrijving                                                                                                                                                                                                                                                                                               | Prio | Punten | Competentie VT | Voldaan |
| ---- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---- | ------ | -------------- |---------|
| AL01 | De code behoudt de packagestructuur van de aangeleverde startcode. Toegevoegde code bevindt zich in de relevante packages.                                                                                                                                                                                 | Must | 0      | APP-6          | Ja      |
| AL02 | Alle code compileert en is te bouwen met Maven 3.6 of hoger, onder OpenJDK 13. Tip: controleer dit door **eerst** `mvn clean` uit te voeren alvorens te compileren en in te leveren, hierop een onvoldoende halen is echt zonde. **Gebruik van Oracle versies van Java is uitdrukkelijk niet toegestaan**. | Must | 0      | n.v.t.         | Ja      |
| AL03 | De code is goed geformatteerd, zo nodig voorzien van commentaar, correcte variabelenamen gebruikt, bevat geen onnodig ingewikkelde constructies en is zo onderhoudbaar mogelijk opgesteld. (naar oordeel van docent)                                                                                       | Must | 0      | n.v.t.         | Ja      |
| AL04 | De docent heeft vastgesteld (tijdens les, assessment of op een andere manier) dat de compiler eigen werk is en dat je voldoet aan de beoordelingscriteria van APP-6, te weten: - Kent de standaardarchitectuur van compilers; - Kent de basisbegrippen over programmeertalen (zoals syntaxis, semantiek).  | Must | 0      | APP-6          | Ja      |

### 4.2 Parseren (40 punten)


| ID   | Omschrijving                                                                                                                                                                                                                                                                                                                                                                                                                                          | Prio | Punten | Competentie VT | Voldaan |
| ---- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---- | ------ | -------------- |---------|
| PA00 | De parser dient zinvol gebruik te maken van **jouw** eigen implementatie van een stack generic voor `ASTNode` (VT: zie huiswerk `IHANStack<ASTNode>`)                                                                                                                                                                                                                                                                                                 | Must | 0      | APP-1, APP-9   | Ja      |
| PA01 | Implementeer een grammatica plus listener die AST’s kan maken voor ICSS documenten die “eenvoudige opmaak” kan parseren, zoals beschreven in de taalbeschrijving. In `level0.icss` vind je een voorbeeld van ICSS code die je moet kunnen parseren. `testParseLevel0()` slaagt.                                                                                                                                                                       | Must | 10     | APP-6, APP-7   | Ja      |
| PA02 | Breid je grammatica en listener uit zodat nu ook assignments van variabelen en het gebruik ervan geparseerd kunnen worden. In `level1.icss` vind je voorbeeldcode die je nu zou moeten kunnen parseren. `testParseLevel1()` slaagt.                                                                                                                                                                                                                   | Must | 10     | APP-6, APP-7   | Ja      |
| PA03 | Breid je grammatica en listener uit zodat je nu ook optellen en aftrekken en vermenigvuldigen kunt parseren. In `level2.icss` vind je voorbeeld- code die je nu ook zou moeten kunnen parseren. Houd hierbij rekening met de rekenregels (vermenigvuldigen gaat voor optellen en aftrekken, optellen en aftrekken gaan van links naar rechts; zie ook [deze site](https://www.beterrekenen.nl/website/index.php?pag=217).”`testParseLevel2()` slaagt. | Must | 10     | APP-6, APP-7   |         |
| PA04 | Breid je grammatica en listener uit zodat je if/else-statements aankunt. In `level3.icss` vind je voorbeeldcode die je nu ook zou moeten kunnen parseren. `testParseLevel3()` slaagt.                                                                                                                                                                                                                                                                 | Must | 10     | APP-6, APP-7   | Ja      |
| PA05 | PA01 t/m PA04 leveren minimaal 30 punten op                                                                                                                                                                                                                                                                                                                                                                                                           | Must | 0      | nvt            | Ja      |

### 4.3 Checken (30 punten)


| ID   | Omschrijving                                                                                                                                                                                                                                                                         | Prio   | Punten | Voldaan | Competentie VT       |
| ---- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ------ | ------ |---------| -------------------- |
| CH00 | Minimaal vier van onderstaande checks **moeten** zijn geïmplementeerd                                                                                                                                                                                                                | Must   | 0      | Ja      | APP-2, APP-6, APP-7, |
| CH01 | Controleer of er geen variabelen worden gebruikt die niet gedefinieerd zijn.                                                                                                                                                                                                         | Should | 5      | Ja      |
| CH02 | Controleer of de operanden van de operaties plus en min van gelijk type zijn. Je mag geen pixels bij percentages optellen bijvoorbeeld. Controleer dat bij vermenigvuldigen minimaal een operand een scalaire waarde is. Zo mag `20% * 3` en `4 * 5` wel, maar mag `2px * 3px` niet. | Should | 5      | Ja      |
| CH03 | Controleer of er geen kleuren worden gebruikt in operaties (plus, min en keer).                                                                                                                                                                                                      | Should | 5      | Ja      |
| CH04 | Controleer of bij declaraties het type van de value klopt met de property. Declaraties zoals `width: #ff0000` of `color: 12px` zijn natuurlijk onzin.                                                                                                                                | Should | 5      | Ja      |
| CH05 | Controleer of de conditie bij een if-statement van het type boolean is (zowel bij een variabele-referentie als een boolean literal)                                                                                                                                                  | Should | 5      | Ja      |
| CH06 | Controleer of variabelen enkel binnen hun scope gebruikt worden                                                                                                                                                                                                                      | Must   | 5      | Ja      |


## CH01/06
```
HighlightColor := #ff0000;
AnswerToEverything := AnswerToEverything;

p {
    TextBlockWidth := 20px;
    background-color: HighlightColor;
    width: TextBlockWidth;
}

#menu {
    width: TextBlockWidth;
}

```

## CH02
```
SumSuccess := 15px + 25px;
SumMismatch := 30px + 15%;
MultiplyValid := 25% * 3;
MultiplyInvalid := 5px * 8px;
```

## CH03
```
ColorAddInvalid := #808080 + #808080;
ColorSubInvalid := #808080 - #808080;
ColorMulInvalid := #808080 * 2;
```

## CH04
```
AccentColor := #ff0000;

#menu {
    color: 20px; // Error
    height: 50 * 20; // Error
    width: 2 * 30%;
}

p {
    width: AccentColor; // Error
    background-color: AccentColor;
}
```

## CH05
```
BoolValue := TRUE;
NotBoolValue := 10px;

.container {
    if [NotBoolValue] {
        width: 25%;
    }
    if [BoolValue] {
        color: #abc123;
    }
    if [TRUE] {
        height: 100px;
    }
}
```

### 4.4 Transformeren (20 punten)


| ID   | Omschrijving                                                                                                                                                                                                                                                                                                                                                                                                      | Prio | Punten | Competentie VT      | Voldaan |
| ---- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---- | ------ | ------------------- |---------|
| TR01 | Evalueer expressies. Schrijf een transformatie in ```Evaluator``` die alle `Expression` knopen in de AST door een `Literal` knoop met de berekende waarde vervangt.                                                                                                                                                                                                                                                 | Must | 10     | APP-2, APP-6, APP-7 | Ja      |
| TR02 | Evalueer if/else expressies. Schrijf een transformatie in ```Evaluator``` die alle `IfClause`s uit de AST verwijdert. Wanneer de conditie van de `IfClause` `TRUE` is wordt deze vervangen door de body van het if-statement. Als de conditie `FALSE` is dan vervang je de `IfClause` door de body van de `ElseClause`. Als er geen `ElseClause` is bij een negatieve conditie dan verwijder je de `IfClause` volledig uit de AST. | Must | 10     | APP-2, APP-6, APP-7 | Ja      |

### 4.5 Genereren (10 punten)

| ID   | Omschrijving                                                                                                        | Prio | Punten | Competentie VT      | Voldaan |
| ---- | ------------------------------------------------------------------------------------------------------------------- | ---- | ------ | ------------------- |---------|
| GE01 | Implementeer de generator in `nl.han.ica.icss.generator.Generator` die de AST naar een CSS2-compliant string omzet. | Must | 5      | APP-2, APP-6, APP-7 | Ja      |
| GE02 | Zorg dat de CSS met twee spaties inspringing per scopeniveau gegenereerd wordt.                                     | Must | 5      | APP-2, APP-6, APP-7 | Ja      |

### 4.6 Eigen uitbreidingen (20 punten)

| ID | Omschrijving                                                                                                                                                     |
|----|------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1  | Implementeren van booleaanse expressies zoals `3<5`, `Value==5`, `!AdjustWidth` etc.                                                                             |
| 2  | Iedere variabele mag alleen een vast type hebben. Dan mag `Var := 10px;` en daarna `Var := 5%;` niet voorkomen.                                                  |
| 3  | Bij het gebruik van `<` moet alle waardes van hetzelfde type zijn en px, % of scalair getal bevatten. Verder kan `!` en `&&` alleen gebruikt worden op booleans. |
| 4  | Line- en blockcomments                                                                                                                                           |
| 5  | Bij het gebruik van `+` `-` `*` wordt er gecontroleerd of er geen booleans worden gebruikt.                                                                      |
| 6  | Operator precedence                                                                                                                                              |

## 1
```
AdjustWidth := TRUE;
Value := 5;
Pre := 3 < Value; // True
NotValue := !AdjustWidth; // False
Same := Value == 5; // True
Combined := Pre && Same; // True

p {
    if[Combined] {
        background-color: #00ff00;
    }
    if[NotValue] {
        height: 100px;
    } else {
        height: 200px;
    }
}

#content {
    if[Pre] {
        width: 300px;
    }
    if[!Same] {
        color: #000000;
    } else {
        color: #ffffff;
    }
}

```

## 2
```
Color := #ff0000;

p {
    Color := #00ff00;
    Color := 30px; // Error
    background-color: Color;
}
```

## 3
```
LogicTest := 25 < 10 && 5 == 3px; 

ComparisonValue := 4px;
InvalidCompare := 2 < ComparisonValue; 

ColorCompare := #abc123 < #def456; 

WrongLogic := 99 && 100; 

NegationFail := !42;
```

## 4
```
.banner {
    // height: #123456; 
    height: 100px;
}

/* 
.footer {
    color: 123456789px; 
} 
*/
```

## 5
```
CombineBooleans := FALSE + TRUE; 

Flag := FALSE;

InvalidMultiply := Flag * Flag; 

MixedOps := 1 * 1 * TRUE * TRUE; 
```

## 6
```
div {
  width: 50px + 2 * 10px - 2px; // dit is dus 68px
} 
```